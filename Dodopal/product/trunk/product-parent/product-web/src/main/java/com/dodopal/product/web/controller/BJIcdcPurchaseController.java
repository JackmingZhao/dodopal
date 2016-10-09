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
import com.dodopal.api.card.dto.BJCrdSysOrderDTO;
import com.dodopal.common.constant.CommonConstants;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.log.ActivemqLogPublisher;
import com.dodopal.common.md5.SignUtils;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.model.SysLog;
import com.dodopal.common.util.DateUtils;
import com.dodopal.common.util.DodopalAppVarPropsUtil;
import com.dodopal.product.business.service.BJIcdcPurchaseService;
import com.dodopal.product.business.service.IcdcPurchaseService;

/**
 * 北京城市一卡通消费业务流程类（WEB端（POS:V61）：（OCX请求后台））
 */
@RequestMapping("/BJ")
@Controller
public class BJIcdcPurchaseController {

    private Logger logger = LoggerFactory.getLogger(BJIcdcPurchaseController.class);

    @Autowired
    IcdcPurchaseService icdcPurchaseService;
    @Autowired
    BJIcdcPurchaseService bjIcdcPurchaseService;

    /**
     * 北京V61消费_验卡生单接口
     */
    @RequestMapping("/checkCardCreateOrder")
    public @ResponseBody DodopalResponse<BJCrdSysOrderDTO> checkCardCreateOrder(HttpServletRequest request) {
        DodopalResponse<BJCrdSysOrderDTO> returnResonse = new DodopalResponse<BJCrdSysOrderDTO>();
        
        String jsondata = request.getParameter("jsondata");
        if (logger.isInfoEnabled()) {
            logger.info("进入北京消费_验卡生单接口:接收到的json参数:" + jsondata);
        }
        
        SysLog log = new SysLog();
        log.setTradeStart(Long.parseLong(DateUtils.dateToString(new Date(), DateUtils.DATE_FORMAT_YYMMDDHHMMSSSSS_STR)));

        BJCrdSysOrderDTO dto = null;
        try {
            
            //客户端传参jsondata转化为BJCrdSysOrderDTO对象
            dto = this.jsondataConvertDto(jsondata);
            
            // 参数验证
            this.checkJsonParmError(dto, "1");
            
            // 产品库验签
            this.checkDSIGN(dto, jsondata);
            
            //执行验卡生单
            returnResonse = bjIcdcPurchaseService.checkCardCreateOrder(dto, "V61");

            if (!ResponseCode.SUCCESS.equals(returnResonse.getCode())) {
                //失败打印错误日志
                if (logger.isInfoEnabled()) {
                    logger.error("北京消费_验卡生单接口,失败响应码:" + returnResonse.getCode() + ",响应消息:" + returnResonse.getMessage());
                }
            }
            
            //进行验签 
            String p_sign = this.getBackDSIGN(returnResonse.getResponseEntity());
            returnResonse.getResponseEntity().setP_sign(p_sign);
        }
        catch (DDPException ddpexception) {
            if (logger.isInfoEnabled()) {
                logger.error("北京消费_验卡生单接口,异常响应码:" + ddpexception.getCode() + ",响应消息:" + ddpexception.getMessage());
            }
            ddpexception.printStackTrace();
            returnResonse.setCode(ddpexception.getCode());
            returnResonse.setResponseEntity(dto);
            log.setStatckTrace(ddpexception.getStackTrace().toString());
        }
        catch (Exception e) {
            if (logger.isInfoEnabled()) {
                logger.error("北京消费_验卡生单接口,系统错误:respcode:" + ResponseCode.SYSTEM_ERROR + e.getMessage(), e);
            }
            e.printStackTrace();
            returnResonse.setCode(ResponseCode.SYSTEM_ERROR);
            returnResonse.setResponseEntity(dto);
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
            log.setClassName(BJIcdcPurchaseController.class.toString());
            log.setMethodName("checkCardCreateOrder");
            log.setInParas(jsondata);
            log.setOutParas(JSONObject.toJSONString(returnResonse));
            log.setRespCode(returnResonse.getCode());
            log.setRespExplain(returnResonse.getMessage());
            log.setDescription("北京消费_验卡生单接口");
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
            logger.info("北京消费_验卡生单接口,返回客户端的报文:响应码:" + returnResonse.getCode() + ",完整响应体:" + JSONObject.toJSONString(returnResonse) + ".");
        }
       
        return returnResonse;
    }
    
 
    /**
     * 北京V61联机消费_申请消费密钥接口
     */
    @RequestMapping("/applyDeductInstruction")
    public @ResponseBody DodopalResponse<BJCrdSysOrderDTO> applyConsumeByV61(HttpServletRequest request) {
        DodopalResponse<BJCrdSysOrderDTO> returnResonse = new DodopalResponse<BJCrdSysOrderDTO>();

        String jsondata = request.getParameter("jsondata");
        if (logger.isInfoEnabled()) {
            logger.info("进入北京消费_申请消费密钥接口:接收到的json参数:" + jsondata);
        }
        
        SysLog log = new SysLog();
        log.setTradeStart(Long.parseLong(DateUtils.dateToString(new Date(), DateUtils.DATE_FORMAT_YYMMDDHHMMSSSSS_STR)));

        BJCrdSysOrderDTO crdDTO = null;
        try {
            
            //客户端传参jsondata转化为BJCrdSysOrderDTO对象
            crdDTO = this.jsondataConvertDto(jsondata);
            
            // 参数验证
            this.checkJsonParmError(crdDTO, "2");
            
            // 产品库验签
            this.checkDSIGN(crdDTO, jsondata);
            
            //执行申请扣款指令接口
            returnResonse = bjIcdcPurchaseService.applyConsumeByV61(crdDTO);

            if (ResponseCode.SUCCESS.equals(returnResonse.getCode())) {
                
                //成功则需要返回客户端签名值进行验签 
                String p_sign = this.getBackDSIGN(returnResonse.getResponseEntity());
                returnResonse.getResponseEntity().setP_sign(p_sign);
                
            } else {
                
                //失败打印错误日志
                if (logger.isInfoEnabled()) {
                    logger.error("北京消费_申请消费密钥接口,失败响应码:" + returnResonse.getCode() + ",响应消息:" + returnResonse.getMessage());
                }
            }
        }
        catch (DDPException ddpexception) {
            if (logger.isInfoEnabled()) {
                logger.error("北京消费_申请消费密钥接口,异常响应码:" + ddpexception.getCode() + ",响应消息:" + ddpexception.getMessage());
            }
            ddpexception.printStackTrace();
            returnResonse.setCode(ddpexception.getCode());
            returnResonse.setResponseEntity(crdDTO);
            log.setStatckTrace(ddpexception.getStackTrace().toString());
        }
        catch (Exception e) {
            if (logger.isInfoEnabled()) {
                logger.error("北京消费_申请消费密钥接口,系统错误:respcode:" + ResponseCode.SYSTEM_ERROR + e.getMessage(), e);
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
            log.setClassName(BJIcdcPurchaseController.class.toString());
            log.setMethodName("applyConsumeByOnlineV61");
            log.setInParas(jsondata);
            log.setOutParas(JSONObject.toJSONString(returnResonse));
            log.setRespCode(returnResonse.getCode());
            log.setRespExplain(returnResonse.getMessage());
            log.setDescription("北京消费_申请消费密钥接口");
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
            logger.info("北京消费_申请消费密钥接口,返回客户端的报文:响应码:" + returnResonse.getCode() + ",完整响应体:" + JSONObject.toJSONString(returnResonse) + ".");
        }
        
        return returnResonse;
    }

    /**
     * 北京V61(联机/脱机)消费结果上传
     */
    @RequestMapping("/uploadResult")
    public @ResponseBody DodopalResponse<BJCrdSysOrderDTO> uploadConsumeResultByV61(HttpServletRequest request) {
        DodopalResponse<BJCrdSysOrderDTO> returnResonse = new DodopalResponse<BJCrdSysOrderDTO>();

        String jsondata = request.getParameter("jsondata");
        if (logger.isInfoEnabled()) {
            logger.info("进入北京消费_消费结果上传:接收到的json参数:" + jsondata);
        }

        SysLog log = new SysLog();
        log.setTradeStart(Long.parseLong(DateUtils.dateToString(new Date(), DateUtils.DATE_FORMAT_YYMMDDHHMMSSSSS_STR)));

        BJCrdSysOrderDTO crdDTO = null;
        try {
            
            //客户端传参jsondata转化为BJCrdSysOrderDTO对象
            crdDTO = this.jsondataConvertDto(jsondata);
            
            // 参数验证
            this.checkJsonParmError(crdDTO, "3");
            
            // 产品库验签
            this.checkDSIGN(crdDTO, jsondata);
            
            // 执行上传收单结果接口
            returnResonse = bjIcdcPurchaseService.uploadConsumeResultByV61(crdDTO);

            if (ResponseCode.SUCCESS.equals(returnResonse.getCode())) {
                
                //成功则需要返回客户端签名值进行验签
                String p_sign = this.getBackDSIGN(returnResonse.getResponseEntity());
                returnResonse.getResponseEntity().setP_sign(p_sign);
                
            } else {
                
                //失败打印错误日志
                if (logger.isInfoEnabled()) {
                    logger.error("北京消费_消费结果上传,失败响应码:" + returnResonse.getCode() + ",响应消息:" + returnResonse.getMessage());
                }
            }
        }
        catch (DDPException ddpexception) {
            if (logger.isInfoEnabled()) {
                logger.error("北京消费_消费结果上传,异常响应码:" + ddpexception.getCode() + ",响应消息:" + ddpexception.getMessage());
            }
            ddpexception.printStackTrace();
            returnResonse.setCode(ddpexception.getCode());
            returnResonse.setResponseEntity(crdDTO);
            log.setStatckTrace(ddpexception.getStackTrace().toString());
        }
        catch (Exception e) {
            if (logger.isInfoEnabled()) {
                logger.error("北京消费_消费结果上传,系统错误:respcode:" + ResponseCode.SYSTEM_ERROR + e.getMessage(), e);
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
            log.setClassName(BJIcdcPurchaseController.class.toString());
            log.setMethodName("uploadConsumeResultByV61");
            log.setInParas(jsondata);
            log.setOutParas(JSONObject.toJSONString(returnResonse));
            log.setRespCode(returnResonse.getCode());
            log.setRespExplain(returnResonse.getMessage());
            log.setDescription("北京消费_消费结果上传");
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
            logger.info("北京消费_消费结果上传,返回客户端的报文:响应码:" + returnResonse.getCode() + ",完整响应体:" + JSONObject.toJSONString(returnResonse) + ".");
        }
       
        return returnResonse;
    }
    
    /**
     * 各环节jsondata共同处理转换CrdSysOrderDTO对象
     */
    private BJCrdSysOrderDTO jsondataConvertDto(String jsondata) {
        BJCrdSysOrderDTO crdDTO = null;
        if (StringUtils.isBlank(jsondata)) {
            throw new DDPException(ResponseCode.JSON_ERROR, "接收到的json报文为空");
        }
        try {
            crdDTO = JSONObject.parseObject(jsondata, BJCrdSysOrderDTO.class);
        }
        catch (Exception e) {
            throw new DDPException(ResponseCode.JSON_ERROR, "客户端传参jsondata转化为BJCrdSysOrderDTO对象出错");
        }
        return crdDTO;
    }

    /**
     * 其他环节产品库验签
     */
    private void checkDSIGN(BJCrdSysOrderDTO crdDTO, String jsondata) {
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
    private String getBackDSIGN(BJCrdSysOrderDTO crdDTO) {
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
        logger.info("加密字符串："+prestr);
        String signstr = SignUtils.sign(prestr, signkey, "UTF-8");
        return signstr;
    }

    /**
     * 一卡通收单各个环节接口入参验证
     * @param crdDTO 其他各个环节接口入参
     * @param link 各个环节标志位 (1:生单环节；2：申请扣款初始化环节；3：申请扣款环节；4：上传收单结果环节)
     * @return
     */
    private void checkJsonParmError(BJCrdSysOrderDTO crdDTO, String link) {
        switch (link) {
            case "1":
                if (StringUtils.isBlank(crdDTO.getReceivableAmt())) {
                    throw new DDPException(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR, "参数验证:应收金额为空");
                }
                if (StringUtils.isBlank(crdDTO.getReceivedAmt())) {
                    throw new DDPException(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR, "参数验证:实收金额为空");
                }
                if (StringUtils.isBlank(crdDTO.getMercode())) {
                    throw new DDPException(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR, "参数验证:商户编号为空");
                }
                if (StringUtils.isBlank(crdDTO.getSource())) {
                    throw new DDPException(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR, "参数验证:来源为空");
                }
                if (StringUtils.isBlank(crdDTO.getUserid())) {
                    throw new DDPException(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR, "参数验证:操作员为空");
                }
                if (StringUtils.isBlank(crdDTO.getYktcode())) {
                    throw new DDPException(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR, "参数验证:一卡通编号为空");
                }
                if (StringUtils.isBlank(crdDTO.getCitycode())) {
                    throw new DDPException(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR, "参数验证:城市ID为空");
                }
                if (StringUtils.isBlank(crdDTO.getMerdiscount())) {
                    throw new DDPException(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR, "参数验证:商户折扣为空");
                }
                if (StringUtils.isBlank(crdDTO.getD_sign())) {
                    throw new DDPException(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR, "参数验证:d_sign为空");
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
                break;
            case "2":
                if (StringUtils.isBlank(crdDTO.getPrdordernum())) {
                    throw new DDPException(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR, "参数验证:产品库收单订单编号为空");
                }
                if (StringUtils.isBlank(crdDTO.getYktcode())) {
                    throw new DDPException(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR, "参数验证:一卡通编号为空");
                }
                if (StringUtils.isBlank(crdDTO.getD_sign())) {
                    throw new DDPException(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR, "参数验证:d_sign为空");
                }
                break;
            case "3":
                if (StringUtils.isBlank(crdDTO.getD_sign())) {
                    throw new DDPException(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR, "参数验证:d_sign为空");
                }
                if (StringUtils.isBlank(crdDTO.getOfflineflag())) {
                    throw new DDPException(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR, "参数验证:脱机标识offlineflag为空");
                }
                break;
        }
    }
}
