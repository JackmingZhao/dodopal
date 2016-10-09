package com.dodopal.product.web.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.dodopal.api.card.dto.CrdSysOrderDTO;
import com.dodopal.api.product.dto.IcdcPurchaseDTO;
import com.dodopal.common.constant.CommonConstants;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.RechargeOrderResultEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.log.ActivemqLogPublisher;
import com.dodopal.common.md5.SignUtils;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.model.SysLog;
import com.dodopal.common.util.DateUtils;
import com.dodopal.common.util.DodopalAppVarPropsUtil;
import com.dodopal.product.business.service.IcdcPurchaseService;

/**
 * 一卡通收单控制流程类
 */
@Controller
public class IcdcPurchaseController {

    private Logger logger = LoggerFactory.getLogger(IcdcPurchaseController.class);

    @Autowired
    IcdcPurchaseService icdcPurchaseService;

    /**
     * 一卡通收单生单接口
     */
    @RequestMapping("/createIcdcPurchaseOrder")
    public @ResponseBody DodopalResponse<IcdcPurchaseDTO> createIcdcPurchaseOrder(HttpServletRequest request) {
        DodopalResponse<IcdcPurchaseDTO> returnResonse = new DodopalResponse<IcdcPurchaseDTO>();
        
        String jsondata = request.getParameter("jsondata");
        if (logger.isInfoEnabled()) {
            logger.info("进入一卡通收单生单接口:接收到的json参数:" + jsondata);
        }
        
        SysLog log = new SysLog();
        log.setTradeStart(Long.parseLong(DateUtils.dateToString(new Date(), DateUtils.DATE_FORMAT_YYMMDDHHMMSSSSS_STR)));

        IcdcPurchaseDTO purchaseDto = null;
        try {
            
            //客户端传参jsondata转化为IcdcPurchaseDTO对象
            purchaseDto = this.jsondataConvertDtoForCreateOrder(jsondata);
            
            // 参数验证
            this.checkJsonParmError(purchaseDto, null, "1");
            
            // 产品库验签
            this.createOrderCheckDSIGN(purchaseDto, jsondata);
            
            //执行生单
            returnResonse = icdcPurchaseService.createIcdcPurchaseOrder(purchaseDto);

            if (ResponseCode.SUCCESS.equals(returnResonse.getCode())) {
                
                //成功则需要返回客户端签名值进行验签 
                String p_sign = this.getBackDSIGNForCreateOrder(returnResonse.getResponseEntity());
                returnResonse.getResponseEntity().setP_sign(p_sign);
                
            } else {
                
                //失败打印错误日志
                if (logger.isInfoEnabled()) {
                    logger.error("一卡通收单生单接口,失败响应码:" + returnResonse.getCode() + ",响应消息:" + returnResonse.getMessage());
                }
            }
        }
        catch (DDPException ddpexception) {
            if (logger.isInfoEnabled()) {
                logger.error("一卡通收单生单接口,异常响应码:" + ddpexception.getCode() + ",响应消息:" + ddpexception.getMessage());
            }
            ddpexception.printStackTrace();
            returnResonse.setCode(ddpexception.getCode());
            returnResonse.setResponseEntity(purchaseDto);
            log.setStatckTrace(ddpexception.getStackTrace().toString());
        }
        catch (Exception e) {
            if (logger.isInfoEnabled()) {
                logger.error("一卡通收单生单接口,系统错误:respcode:" + ResponseCode.SYSTEM_ERROR + e.getMessage(), e);
            }
            e.printStackTrace();
            returnResonse.setCode(ResponseCode.SYSTEM_ERROR);
            returnResonse.setResponseEntity(purchaseDto);
            log.setStatckTrace(e.getStackTrace().toString());
        }
        finally {
            log.setServerName(CommonConstants.SYSTEM_NAME_PRODUCT);
            if (returnResonse.getResponseEntity() != null){
                log.setOrderNum(returnResonse.getResponseEntity().getPrdordernum());
                log.setSource(returnResonse.getResponseEntity().getSource());
            }
            log.setTranNum("");
            if (ResponseCode.SUCCESS.equals(returnResonse.getCode())) {
                log.setTradeType(0);
            } else {
                log.setTradeType(1);
            }
            log.setClassName(IcdcPurchaseController.class.toString());
            log.setMethodName("createIcdcPurchaseOrder");
            log.setInParas(jsondata);
            log.setOutParas(JSONObject.toJSONString(returnResonse));
            log.setRespCode(returnResonse.getCode());
            log.setRespExplain(returnResonse.getMessage());
            log.setDescription("公交卡消费__生单接口");
            log.setIpAddress("");
            log.setLogDate(new Date());
            log.setTradeEnd(Long.parseLong(DateUtils.dateToString(new Date(), DateUtils.DATE_FORMAT_YYMMDDHHMMSSSSS_STR)));
            log.setTradeRrack(log.getTradeEnd() - log.getTradeStart());

            // 记录日志
            ActivemqLogPublisher.publishLog2Queue(log, DodopalAppVarPropsUtil.getStringProp(CommonConstants.SERVER_LOG_NAME), DodopalAppVarPropsUtil.getStringProp(CommonConstants.SERVER_LOG_URL));
        }

        // 清空message,让页面弹出消息取自数据库消息
        returnResonse.setCode(returnResonse.getCode());
        // 错误码统一转换
        if(!ResponseCode.SUCCESS.equals(returnResonse.getCode())){
            returnResonse.setNewMessage(ResponseCode.PRODUCT_PURCHASE_ERROR);
        }
        
        if (logger.isInfoEnabled()) {
            logger.info("一卡通收单生单接口,返回客户端的报文:响应码:" + returnResonse.getCode() + ",完整响应体:" + JSONObject.toJSONString(returnResonse) + ".");
        }
       
        return returnResonse;
    }

    /**
     * 一卡通收单申请扣款初始化指令接口
     */
    @RequestMapping("/checkCard")
    public @ResponseBody DodopalResponse<CrdSysOrderDTO> checkCard(HttpServletRequest request) {
        DodopalResponse<CrdSysOrderDTO> returnResonse = new DodopalResponse<CrdSysOrderDTO>();

        String jsondata = request.getParameter("jsondata");
        if (logger.isInfoEnabled()) {
            logger.info("进入申请扣款初始化指令接口:接收到的json参数:" + jsondata);
        }
        
        SysLog log = new SysLog();
        log.setTradeStart(Long.parseLong(DateUtils.dateToString(new Date(), DateUtils.DATE_FORMAT_YYMMDDHHMMSSSSS_STR)));

        CrdSysOrderDTO crdDTO = null;
        try {
            
            //客户端传参jsondata转化为IcdcPurchaseDTO对象
            crdDTO = this.jsondataConvertDto(jsondata);
            
            // 参数验证
            this.checkJsonParmError(null, crdDTO, "2");
            
            // 产品库验签
            this.checkDSIGN(crdDTO, jsondata);
            
            //执行申请扣款初始化指令接口
            returnResonse = icdcPurchaseService.checkCard(crdDTO);

            
            // ********  针对城市前置验卡成功，但返回的响应码为特殊的响应码（例如：验卡为黑名单卡等等）非“000000”， 产品库需要签名处理  add by shenXiang 2015-11-25 start  ********//
            
            String p_sign = this.getBackDSIGN(returnResonse.getResponseEntity());
            returnResonse.getResponseEntity().setP_sign(p_sign);
            
            // ********  针对城市前置验卡成功，但返回的响应码为特殊的响应码（例如：验卡为黑名单卡等等）非“000000”， 产品库需要签名处理  add by shenXiang 2015-11-25 end  ********//
            
//            if (ResponseCode.SUCCESS.equals(returnResonse.getCode())) {
//                //成功则需要返回客户端签名值进行验签 
//                String p_sign = this.getBackDSIGN(returnResonse.getResponseEntity());
//                returnResonse.getResponseEntity().setP_sign(p_sign);
//                
//            } else {
//                //失败打印错误日志
//                if (logger.isInfoEnabled()) {
//                    logger.error("申请扣款初始化指令接口,失败响应码:" + returnResonse.getCode() + ",响应消息:" + returnResonse.getMessage());
//                }
//            }
        }
        catch (DDPException ddpexception) {
            if (logger.isInfoEnabled()) {
                logger.error("申请扣款初始化指令接口,异常响应码:" + ddpexception.getCode() + ",响应消息:" + ddpexception.getMessage());
            }
            ddpexception.printStackTrace();
            returnResonse.setCode(ddpexception.getCode());
            returnResonse.setResponseEntity(crdDTO);
            log.setStatckTrace(ddpexception.getStackTrace().toString());
        }
        catch (Exception e) {
            if (logger.isInfoEnabled()) {
                logger.error("申请扣款初始化指令接口,系统错误:respcode:" + ResponseCode.SYSTEM_ERROR + e.getMessage(), e);
            }
            e.printStackTrace();
            returnResonse.setCode(ResponseCode.SYSTEM_ERROR);
            returnResonse.setResponseEntity(crdDTO);
            log.setStatckTrace(e.getStackTrace().toString());
        }
        finally {
            log.setServerName(CommonConstants.SYSTEM_NAME_PRODUCT);
            if (returnResonse.getResponseEntity() != null){
                log.setOrderNum(returnResonse.getResponseEntity().getPrdordernum());
                log.setSource(returnResonse.getResponseEntity().getSource());
            }
            log.setTranNum("");
            if (ResponseCode.SUCCESS.equals(returnResonse.getCode())) {
                log.setTradeType(0);
            } else {
                log.setTradeType(1);
            }
            log.setClassName(IcdcPurchaseController.class.toString());
            log.setMethodName("checkCard");
            log.setInParas(jsondata);
            log.setOutParas(JSONObject.toJSONString(returnResonse));
            log.setRespCode(returnResonse.getCode());
            log.setRespExplain(returnResonse.getMessage());
            log.setDescription("公交卡消费__申请扣款初始化指令接口");
            log.setIpAddress("");
            log.setLogDate(new Date());
            log.setTradeEnd(Long.parseLong(DateUtils.dateToString(new Date(), DateUtils.DATE_FORMAT_YYMMDDHHMMSSSSS_STR)));
            log.setTradeRrack(log.getTradeEnd() - log.getTradeStart());

            // 记录日志
            ActivemqLogPublisher.publishLog2Queue(log, DodopalAppVarPropsUtil.getStringProp(CommonConstants.SERVER_LOG_NAME), DodopalAppVarPropsUtil.getStringProp(CommonConstants.SERVER_LOG_URL));
        }
        // 清空message,让页面弹出消息取自数据库消息
        returnResonse.setCode(returnResonse.getCode());
        // 错误码统一转换
        if(!ResponseCode.SUCCESS.equals(returnResonse.getCode())){
            returnResonse.setNewMessage(ResponseCode.PRODUCT_PURCHASE_ERROR);
        }
        
       
        if (logger.isInfoEnabled()) {
            logger.info("申请扣款初始化指令接口,返回客户端的报文:响应码:" + returnResonse.getCode() + ",完整响应体:" + JSONObject.toJSONString(returnResonse) + ".");
        }
        
        return returnResonse;
    }

    /**
     * 一卡通收单申请扣款指令接口
     */
    @RequestMapping("/applyDeductInstruction")
    public @ResponseBody DodopalResponse<CrdSysOrderDTO> applyDeductInstruction(HttpServletRequest request) {
        DodopalResponse<CrdSysOrderDTO> returnResonse = new DodopalResponse<CrdSysOrderDTO>();

        String jsondata = request.getParameter("jsondata");
        if (logger.isInfoEnabled()) {
            logger.info("进入申请扣款指令接口:接收到的json参数:" + jsondata);
        }
        
        SysLog log = new SysLog();
        log.setTradeStart(Long.parseLong(DateUtils.dateToString(new Date(), DateUtils.DATE_FORMAT_YYMMDDHHMMSSSSS_STR)));

        CrdSysOrderDTO crdDTO = null;
        try {
            
            //客户端传参jsondata转化为IcdcPurchaseDTO对象
            crdDTO = this.jsondataConvertDto(jsondata);
            
            // 参数验证
            this.checkJsonParmError(null, crdDTO, "3");
            
            // 产品库验签
            this.checkDSIGN(crdDTO, jsondata);
            
            //执行申请扣款指令接口
            returnResonse = icdcPurchaseService.applyDeductInstruction(crdDTO);

            if (ResponseCode.SUCCESS.equals(returnResonse.getCode())) {
                
                //成功则需要返回客户端签名值进行验签 
                String p_sign = this.getBackDSIGN(returnResonse.getResponseEntity());
                returnResonse.getResponseEntity().setP_sign(p_sign);
                
            } else {
                
                //失败打印错误日志
                if (logger.isInfoEnabled()) {
                    logger.error("申请扣款指令接口,失败响应码:" + returnResonse.getCode() + ",响应消息:" + returnResonse.getMessage());
                }
            }
        }
        catch (DDPException ddpexception) {
            if (logger.isInfoEnabled()) {
                logger.error("申请扣款指令接口,异常响应码:" + ddpexception.getCode() + ",响应消息:" + ddpexception.getMessage());
            }
            ddpexception.printStackTrace();
            returnResonse.setCode(ddpexception.getCode());
            returnResonse.setResponseEntity(crdDTO);
            log.setStatckTrace(ddpexception.getStackTrace().toString());
        }
        catch (Exception e) {
            if (logger.isInfoEnabled()) {
                logger.error("申请扣款指令接口,系统错误:respcode:" + ResponseCode.SYSTEM_ERROR + e.getMessage(), e);
            }
            e.printStackTrace();
            returnResonse.setCode(ResponseCode.SYSTEM_ERROR);
            returnResonse.setResponseEntity(crdDTO);
            log.setStatckTrace(e.getStackTrace().toString());
        }
        finally {
            log.setServerName(CommonConstants.SYSTEM_NAME_PRODUCT);
            if (returnResonse.getResponseEntity() != null){
                log.setOrderNum(returnResonse.getResponseEntity().getPrdordernum());
                log.setSource(returnResonse.getResponseEntity().getSource());
            }
            log.setTranNum("");
            if (ResponseCode.SUCCESS.equals(returnResonse.getCode())) {
                log.setTradeType(0);
            } else {
                log.setTradeType(1);
            }
            log.setClassName(IcdcPurchaseController.class.toString());
            log.setMethodName("applyDeductInstruction");
            log.setInParas(jsondata);
            log.setOutParas(JSONObject.toJSONString(returnResonse));
            log.setRespCode(returnResonse.getCode());
            log.setRespExplain(returnResonse.getMessage());
            log.setDescription("公交卡消费__申请扣款指令接口");
            log.setIpAddress("");
            log.setLogDate(new Date());
            log.setTradeEnd(Long.parseLong(DateUtils.dateToString(new Date(), DateUtils.DATE_FORMAT_YYMMDDHHMMSSSSS_STR)));
            log.setTradeRrack(log.getTradeEnd() - log.getTradeStart());

            // 记录日志
            ActivemqLogPublisher.publishLog2Queue(log, DodopalAppVarPropsUtil.getStringProp(CommonConstants.SERVER_LOG_NAME), DodopalAppVarPropsUtil.getStringProp(CommonConstants.SERVER_LOG_URL));
        }
        // 清空message,让页面弹出消息取自数据库消息
        returnResonse.setCode(returnResonse.getCode());
        // 错误码统一转换
        if(!ResponseCode.SUCCESS.equals(returnResonse.getCode())){
            returnResonse.setNewMessage(ResponseCode.PRODUCT_PURCHASE_ERROR);
        }
       
        if (logger.isInfoEnabled()) {
            logger.info("申请扣款指令接口,返回客户端的报文:响应码:" + returnResonse.getCode() + ",完整响应体:" + JSONObject.toJSONString(returnResonse) + ".");
        }
        
        return returnResonse;
    }

    /**
     * 一卡通收单上传收单结果接口
     */
    @RequestMapping("/uploadResult")
    public @ResponseBody DodopalResponse<CrdSysOrderDTO> uploadResult(HttpServletRequest request) {
        DodopalResponse<CrdSysOrderDTO> returnResonse = new DodopalResponse<CrdSysOrderDTO>();

        String jsondata = request.getParameter("jsondata");
        if (logger.isInfoEnabled()) {
            logger.info("进入上传收单结果接口:接收到的json参数:" + jsondata);
        }

        SysLog log = new SysLog();
        log.setTradeStart(Long.parseLong(DateUtils.dateToString(new Date(), DateUtils.DATE_FORMAT_YYMMDDHHMMSSSSS_STR)));

        CrdSysOrderDTO crdDTO = null;
        try {
            
            //客户端传参jsondata转化为IcdcPurchaseDTO对象
            crdDTO = this.jsondataConvertDto(jsondata);
            
            // 参数验证
            this.checkJsonParmError(null, crdDTO, "4");
            
            // 产品库验签
            this.checkDSIGN(crdDTO, jsondata);
            
            //执行上传收单结果接口
            returnResonse = icdcPurchaseService.uploadResult(crdDTO);

            if (ResponseCode.SUCCESS.equals(returnResonse.getCode())) {
                
                //成功则需要返回客户端签名值进行验签
                String p_sign = this.getBackDSIGN(returnResonse.getResponseEntity());
                returnResonse.getResponseEntity().setP_sign(p_sign);
                
            } else {
                
                //失败打印错误日志
                if (logger.isInfoEnabled()) {
                    logger.error("上传收单结果接口,失败响应码:" + returnResonse.getCode() + ",响应消息:" + returnResonse.getMessage());
                }
            }
        }
        catch (DDPException ddpexception) {
            if (logger.isInfoEnabled()) {
                logger.error("上传收单结果接口,异常响应码:" + ddpexception.getCode() + ",响应消息:" + ddpexception.getMessage());
            }
            ddpexception.printStackTrace();
            returnResonse.setCode(ddpexception.getCode());
            returnResonse.setResponseEntity(crdDTO);
            log.setStatckTrace(ddpexception.getStackTrace().toString());
        }
        catch (Exception e) {
            if (logger.isInfoEnabled()) {
                logger.error("上传收单结果接口,系统错误:respcode:" + ResponseCode.SYSTEM_ERROR + e.getMessage(), e);
            }
            e.printStackTrace();
            returnResonse.setCode(ResponseCode.SYSTEM_ERROR);
            returnResonse.setResponseEntity(crdDTO);
            log.setStatckTrace(e.getStackTrace().toString());
        }
        finally {
            log.setServerName(CommonConstants.SYSTEM_NAME_PRODUCT);
            if (returnResonse.getResponseEntity() != null){
                log.setOrderNum(returnResonse.getResponseEntity().getPrdordernum());
                log.setSource(returnResonse.getResponseEntity().getSource());
            }
            log.setTranNum("");
            if (ResponseCode.SUCCESS.equals(returnResonse.getCode())) {
                log.setTradeType(0);
            } else {
                log.setTradeType(1);
            }
            log.setClassName(IcdcPurchaseController.class.toString());
            log.setMethodName("uploadResult");
            log.setInParas(jsondata);
            log.setOutParas(JSONObject.toJSONString(returnResonse));
            log.setRespCode(returnResonse.getCode());
            log.setRespExplain(returnResonse.getMessage());
            log.setDescription("公交卡消费__上传收单结果接口");
            log.setIpAddress("");
            log.setLogDate(new Date());
            log.setTradeEnd(Long.parseLong(DateUtils.dateToString(new Date(), DateUtils.DATE_FORMAT_YYMMDDHHMMSSSSS_STR)));
            log.setTradeRrack(log.getTradeEnd() - log.getTradeStart());

            // 记录日志
            ActivemqLogPublisher.publishLog2Queue(log, DodopalAppVarPropsUtil.getStringProp(CommonConstants.SERVER_LOG_NAME), DodopalAppVarPropsUtil.getStringProp(CommonConstants.SERVER_LOG_URL));
        }
        // 清空message,让页面弹出消息取自数据库消息
        returnResonse.setCode(returnResonse.getCode());
        // 错误码统一转换
        if(!ResponseCode.SUCCESS.equals(returnResonse.getCode())){
            returnResonse.setNewMessage(ResponseCode.PRODUCT_PURCHASE_ERROR);
        }
        
       
        if (logger.isInfoEnabled()) {
            logger.info("上传收单结果接口,返回客户端的报文:响应码:" + returnResonse.getCode() + ",完整响应体:" + JSONObject.toJSONString(returnResonse) + ".");
        }
       
        return returnResonse;
    }

    /**
     * 生单时,jsondata转换IcdcPurchaseDTO对象
     */
    private IcdcPurchaseDTO jsondataConvertDtoForCreateOrder(String jsondata) {
        IcdcPurchaseDTO purchaseDto = null;
        if (StringUtils.isBlank(jsondata)) {
            throw new DDPException(ResponseCode.JSON_ERROR, "接收到的json报文为空");
        }
        try {
            purchaseDto = JSONObject.parseObject(jsondata, IcdcPurchaseDTO.class);
        }
        catch (Exception e) {
            throw new DDPException(ResponseCode.JSON_ERROR, "客户端传参jsondata转化为IcdcPurchaseDTO对象出错");
        }
        return purchaseDto;
    }

    /**
     * 各环节jsondata共同处理转换CrdSysOrderDTO对象
     */
    private CrdSysOrderDTO jsondataConvertDto(String jsondata) {
        CrdSysOrderDTO crdDTO = null;
        if (StringUtils.isBlank(jsondata)) {
            throw new DDPException(ResponseCode.JSON_ERROR, "接收到的json报文为空");
        }
        try {
            crdDTO = JSONObject.parseObject(jsondata, CrdSysOrderDTO.class);
        }
        catch (Exception e) {
            throw new DDPException(ResponseCode.JSON_ERROR, "客户端传参jsondata转化为CrdSysOrderDTO对象出错");
        }
        return crdDTO;
    }

    /**
     * 一卡通收单生单接口验签
     */
    private void createOrderCheckDSIGN(IcdcPurchaseDTO purchaseDto, String jsondata) {
        String signkey = DodopalAppVarPropsUtil.getStringProp("dll_paypwd");
        if (StringUtils.isBlank(signkey)) {
            throw new DDPException(ResponseCode.PRODUCT_GET_DSIGNKEY_ERROR, "一卡通收单,产品库验签失败：获取产品库验签密钥失败");
        }
        String dsignstr = getSignStr(jsondata, signkey);
        if (!purchaseDto.getD_sign().equals(dsignstr)) {
            throw new DDPException(ResponseCode.PRODUCT_DSIGN_CHEKC_ERROR, "一卡通收单,产品库验签失败：加密后的MD5值为:" + dsignstr + ",客户端传过来的值为:" + purchaseDto.getD_sign());
        }
    }

    /**
     * 一卡通收单生单接口产品库签名
     */
    private String getBackDSIGNForCreateOrder(IcdcPurchaseDTO purchaseDto) {
        String signkey = DodopalAppVarPropsUtil.getStringProp("dll_backpwd");
        if (StringUtils.isBlank(signkey)) {
            throw new DDPException(ResponseCode.PRODUCT_GET_BACKDSIGNKEY_ERROR, "一卡通收单,产品库签名失败：获取产品库签名密钥失败");
        }
        String jsondata = JSONObject.toJSONString(purchaseDto);
        String backdsignstr = getSignStr(jsondata, signkey);
        return backdsignstr;
    }

    /**
     * 其他环节产品库验签
     */
    private void checkDSIGN(CrdSysOrderDTO crdDTO, String jsondata) {
        String signkey = DodopalAppVarPropsUtil.getStringProp("dll_paypwd");
        if (StringUtils.isBlank(signkey)) {
            throw new DDPException(ResponseCode.PRODUCT_GET_DSIGNKEY_ERROR, "一卡通收单,产品库验签失败：获取产品库验签密钥失败");
        }
        String dsignstr = getSignStr(jsondata, signkey);
        if (!crdDTO.getD_sign().equals(dsignstr)) {
            throw new DDPException(ResponseCode.PRODUCT_DSIGN_CHEKC_ERROR, "一卡通收单,产品库验签失败：加密后的MD5值为:" + dsignstr + ",客户端传过来的值为:" + crdDTO.getD_sign());
        }
    }

    /**
     * 其他环节产品库签名
     */
    private String getBackDSIGN(CrdSysOrderDTO crdDTO) {
        String signkey = DodopalAppVarPropsUtil.getStringProp("dll_backpwd");
        if (StringUtils.isBlank(signkey)) {
            throw new DDPException(ResponseCode.PRODUCT_GET_BACKDSIGNKEY_ERROR, "一卡通收单,产品库签名失败：获取产品库签名密钥失败");
        }
        String jsondata = JSONObject.toJSONString(crdDTO);
        String backdsignstr = getSignStr(jsondata, signkey);
        return backdsignstr;
    }

    /**
     * 签名字符串
     */
    private String getSignStr(String jsonstr, String signkey) {
        Map<String, Object> map = new HashMap<String, Object>();
        JSONObject jsonObject = JSONObject.parseObject(jsonstr);
        for (Object k : jsonObject.keySet()) {
            Object v = jsonObject.get(k);
            if (k != null && v != null && (StringUtils.isNotBlank(v.toString())) && (!"m_sign".equals(k)) && (!"d_sign".equals(k)) && (!"p_sign".equals(k))) {
                map.put(k.toString(), v);
            }
        }
        List<String> keys = new ArrayList<String>(map.keySet());
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = (String) map.get(key).toString();
            if ((StringUtils.isNotBlank(value)) && (!"m_sign".equals(key)) && (!"d_sign".equals(key)) && (!"p_sign".equals(key))) {
                if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                    prestr = prestr + key + "=" + value;
                } else {
                    prestr = prestr + key + "=" + value + "&";
                }
            }
        }
        //logger.info("产品库记录：参与加密字符串：" + prestr + "; 加密密钥：" + signkey);
        String signstr = SignUtils.sign(prestr, signkey, "UTF-8");
        return signstr;
    }

    /**
     * 一卡通收单各个环节接口入参验证
     * @param purchaseDto 生单接口
     * @param crdDTO 其他各个环节接口入参
     * @param link 各个环节标志位 (1:生单环节；2：申请扣款初始化环节；3：申请扣款环节；4：上传收单结果环节)
     * @return
     */
    private void checkJsonParmError(IcdcPurchaseDTO purchaseDto, CrdSysOrderDTO crdDTO, String link) {
        switch (link) {
            case "1":
                if (StringUtils.isBlank(purchaseDto.getReceivableAmt())) {
                    throw new DDPException(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR, "参数验证:应收金额为空");
                }
                if (StringUtils.isBlank(purchaseDto.getReceivedAmt())) {
                    throw new DDPException(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR, "参数验证:实收金额为空");
                }
                if (StringUtils.isBlank(purchaseDto.getMercode())) {
                    throw new DDPException(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR, "参数验证:商户编号为空");
                }
                if (StringUtils.isBlank(purchaseDto.getSource())) {
                    throw new DDPException(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR, "参数验证:来源为空");
                }
                if (StringUtils.isBlank(purchaseDto.getUserid())) {
                    throw new DDPException(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR, "参数验证:操作员为空");
                }
                if (StringUtils.isBlank(purchaseDto.getYktcode())) {
                    throw new DDPException(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR, "参数验证:一卡通编号为空");
                }
                if (StringUtils.isBlank(purchaseDto.getCitycode())) {
                    throw new DDPException(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR, "参数验证:城市ID为空");
                }
                if (StringUtils.isBlank(purchaseDto.getMerdiscount())) {
                    throw new DDPException(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR, "参数验证:商户折扣为空");
                }
                if (StringUtils.isBlank(purchaseDto.getD_sign())) {
                    throw new DDPException(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR, "参数验证:d_sign为空");
                }
                break;
            case "2":
                if (StringUtils.isBlank(crdDTO.getPrdordernum())) {
                    throw new DDPException(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR, "参数验证:产品库收单订单编号为空");
                }
                if (StringUtils.isBlank(crdDTO.getTradecard())) {
                    throw new DDPException(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR, "参数验证:卡号(前端读出)为空");
                }
                if (StringUtils.isBlank(crdDTO.getPosid())) {
                    throw new DDPException(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR, "参数验证:POS设备编号(posId)为空");
                }
                if (StringUtils.isBlank(crdDTO.getBefbal())) {
                    throw new DDPException(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR, "参数验证:支付前卡余额为空");
                }
                if (StringUtils.isBlank(crdDTO.getYktcode())) {
                    throw new DDPException(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR, "参数验证:一卡通编号为空");
                }
//                if (StringUtils.isBlank(crdDTO.getCitycode())) {
//                    throw new DDPException(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR, "参数验证:城市ID为空");
//                }
                if (StringUtils.isBlank(crdDTO.getD_sign())) {
                    throw new DDPException(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR, "参数验证:d_sign为空");
                }
                break;
            case "3":
                if (StringUtils.isBlank(crdDTO.getPrdordernum())) {
                    throw new DDPException(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR, "参数验证:产品库收单订单编号为空");
                }
                if (StringUtils.isBlank(crdDTO.getYktcode())) {
                    throw new DDPException(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR, "参数验证:一卡通编号为空");
                }
//                if (StringUtils.isBlank(crdDTO.getCitycode())) {
//                    throw new DDPException(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR, "参数验证:城市ID为空");
//                }
                if (StringUtils.isBlank(crdDTO.getD_sign())) {
                    throw new DDPException(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR, "参数验证:d_sign为空");
                }
                break;
            case "4":
                if (StringUtils.isBlank(crdDTO.getPrdordernum())) {
                    throw new DDPException(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR, "参数验证:产品库收单订单编号为空");
                }
                if (!RechargeOrderResultEnum.checkCodeExist(crdDTO.getTxnstat())) {
                    throw new DDPException(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR, "参数验证:结果上传交易状态（0：成功 1：失败 2：未知）错误：txnstat=" + crdDTO.getTxnstat());
                }
                if (StringUtils.isBlank(crdDTO.getYktcode())) {
                    throw new DDPException(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR, "参数验证:一卡通编号为空");
                }
//                if (StringUtils.isBlank(crdDTO.getCitycode())) {
//                    throw new DDPException(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR, "参数验证:城市ID为空");
//                }
                if (StringUtils.isBlank(crdDTO.getD_sign())) {
                    throw new DDPException(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR, "参数验证:d_sign为空");
                }
                break;
        }
    }
}
