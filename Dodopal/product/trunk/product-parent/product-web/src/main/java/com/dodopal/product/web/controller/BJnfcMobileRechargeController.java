package com.dodopal.product.web.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.CharEncoding;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.dodopal.api.card.dto.BJCrdSysOrderDTO;
import com.dodopal.common.constant.CommonConstants;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.PayStatusEnum;
import com.dodopal.common.enums.RateCodeEnum;
import com.dodopal.common.enums.TranInStatusEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.log.ActivemqLogPublisher;
import com.dodopal.common.md5.SignUtils;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.model.SysLog;
import com.dodopal.common.util.DateUtils;
import com.dodopal.common.util.DodopalAppVarPropsUtil;
import com.dodopal.product.business.service.BJnfcMobileRechargeService;
import com.dodopal.product.business.service.LoadOrderService;
import com.dodopal.product.business.service.ProductOrderService;

/**
 * 北京NFC手机端一卡通充值业务流程类
 */
@RequestMapping("/NFC")
@Controller
public class BJnfcMobileRechargeController {

    private Logger logger = LoggerFactory.getLogger(BJnfcMobileRechargeController.class);

    @Autowired
    BJnfcMobileRechargeService bjnfcMobileRechargeService;
    @Autowired
    ProductOrderService productOrderService;
    @Autowired
    LoadOrderService loadOrderService;
    
    /**
     * 用户终端信息登记
     */
    @RequestMapping("/userTeminalRegister")
    public @ResponseBody DodopalResponse<BJCrdSysOrderDTO> userTeminalRegister(HttpServletRequest request) {

        DodopalResponse<BJCrdSysOrderDTO> returnResonse = new DodopalResponse<BJCrdSysOrderDTO>();
        String jsondata = request.getParameter("jsondata");
        logger.info("进入NFC手机端:用户终端信息登记接口:接收到的json参数:" + jsondata);
        
        SysLog log = new SysLog();
        log.setTradeStart(Long.parseLong(DateUtils.dateToString(new Date(), DateUtils.DATE_FORMAT_YYMMDDHHMMSSSSS_STR)));
        
        BJCrdSysOrderDTO crdDTO = null;
        try {
            
            // 客户端传参转化为BJCrdSysOrderDTO对象
            crdDTO = this.jsondataConvertDto(jsondata);

            // 参数验证
            this.checkJsonParmError(crdDTO, "1");

            // 产品库验签
            this.checkDSIGN(crdDTO,jsondata);

            // 执行用户终端信息登记
            returnResonse = bjnfcMobileRechargeService.userTeminalRegister(crdDTO);
            
            // 记录失败原因
            if (!ResponseCode.SUCCESS.equals(returnResonse.getCode())) {
                logger.error("NFC手机端:用户终端信息登记接口失败,失败响应码:" + returnResonse.getCode() + ",响应消息:" + returnResonse.getMessage());
            }
            
            // 产品库签名
            String p_sign = this.getBackDSIGN(returnResonse.getResponseEntity());
            returnResonse.getResponseEntity().setP_sign(p_sign);

        }
        catch (DDPException ddpexception) {
            logger.error("NFC手机端:用户终端信息登记接口失败,异常响应码:" + ddpexception.getCode() + ",响应消息:" + ddpexception.getMessage());
            ddpexception.printStackTrace();
            returnResonse.setCode(ddpexception.getCode());
            returnResonse.setNewMessage(ddpexception.getCode());
            returnResonse.setResponseEntity(crdDTO);
            log.setStatckTrace(ddpexception.getStackTrace().toString());
        }
        catch (Exception e) {
            logger.error("NFC手机端:用户终端信息登记接口失败,系统错误:respcode:" + ResponseCode.SYSTEM_ERROR + "," + e.getMessage(), e);
            e.printStackTrace();
            returnResonse.setCode(ResponseCode.SYSTEM_ERROR);
            returnResonse.setNewMessage(ResponseCode.SYSTEM_ERROR);
            returnResonse.setResponseEntity(crdDTO);
            log.setStatckTrace(e.getStackTrace().toString());
        }
        finally{
            log.setServerName(CommonConstants.SYSTEM_NAME_PRODUCT);
            if (returnResonse.getResponseEntity() != null){
                log.setOrderNum(returnResonse.getResponseEntity().getPrdordernum());
                log.setSource(returnResonse.getResponseEntity().getSource());
            }
            log.setTranNum("");
            if(ResponseCode.SUCCESS.equals(returnResonse.getCode())){
                log.setTradeType(0);
            }else{
                log.setTradeType(1);
            }
            log.setClassName(BJnfcMobileRechargeController.class.toString());
            log.setMethodName("userTeminalRegister");
            log.setInParas(jsondata);
            log.setOutParas(JSONObject.toJSONString(returnResonse));
            log.setRespCode(returnResonse.getCode());
            log.setRespExplain(returnResonse.getMessage());
            log.setDescription("NFC手机端:用户终端信息登记接口");
            log.setIpAddress("");
            log.setLogDate(new Date());
            log.setTradeEnd(Long.parseLong(DateUtils.dateToString(new Date(), DateUtils.DATE_FORMAT_YYMMDDHHMMSSSSS_STR)));
            log.setTradeRrack(log.getTradeEnd()-log.getTradeStart());
            // 记录日志
            ActivemqLogPublisher.publishLog2Queue(log, DodopalAppVarPropsUtil.getStringProp(CommonConstants.SERVER_LOG_NAME), DodopalAppVarPropsUtil.getStringProp(CommonConstants.SERVER_LOG_URL));
        }
        
        // 清空message,让页面弹出消息取自数据库消息
        String message = returnResonse.getMessage();
        returnResonse.setCode(returnResonse.getCode());
        
        // 错误码统一转换
        if(!ResponseCode.SUCCESS.equals(returnResonse.getCode())){
            returnResonse.setBJNewMessage(ResponseCode.PRODUCT_RESCHARGE_ERROR, message);
        }
        
        if (ResponseCode.SUCCESS.equals(returnResonse.getCode())) {
            logger.info("NFC手机端:用户终端信息登记接口结束,返回客户端:成功响应码:" + returnResonse.getCode() + ",完整报文体：" + JSONObject.toJSONString(returnResonse)); 
        } else {
            logger.error("NFC手机端:用户终端信息登记接口结束,返回客户端:失败响应码:" + returnResonse.getCode() + ",完整报文体：" + JSONObject.toJSONString(returnResonse));
        }
        
        return returnResonse;
    }
    
    /**
     * 充值验卡接口
     */
    @RequestMapping("/chargeValidateCard")
    public @ResponseBody DodopalResponse<BJCrdSysOrderDTO> chargeValidateCard(HttpServletRequest request) {

        DodopalResponse<BJCrdSysOrderDTO> returnResonse = new DodopalResponse<BJCrdSysOrderDTO>();
        String jsondata = request.getParameter("jsondata");
        logger.info("进入NFC手机端:充值验卡接口接口:接收到的json参数:" + jsondata);
        
        SysLog log = new SysLog();
        log.setTradeStart(Long.parseLong(DateUtils.dateToString(new Date(), DateUtils.DATE_FORMAT_YYMMDDHHMMSSSSS_STR)));
        
        BJCrdSysOrderDTO crdDTO = null;
        try {
            
            // 客户端传参转化为BJCrdSysOrderDTO对象
            crdDTO = this.jsondataConvertDto(jsondata);

            // 参数验证
            this.checkJsonParmError(crdDTO, "2");

            // 产品库验签
            this.checkDSIGN(crdDTO,jsondata);

            // 执行充值验卡接口
            returnResonse = bjnfcMobileRechargeService.chargeValidateCard(crdDTO);
            
            // 记录失败原因
            if (!ResponseCode.SUCCESS.equals(returnResonse.getCode())) {
                logger.error("NFC手机端:充值验卡接口失败,失败响应码:" + returnResonse.getCode() + ",响应消息:" + returnResonse.getMessage());
            }
            
            // 产品库签名（对于黑名单引起的验卡失败，同样需要签名）
            String p_sign = this.getBackDSIGN(returnResonse.getResponseEntity());
            returnResonse.getResponseEntity().setP_sign(p_sign);

        }
        catch (DDPException ddpexception) {
            logger.error("NFC手机端:充值验卡接口失败,异常响应码:" + ddpexception.getCode() + ",响应消息:" + ddpexception.getMessage());
            ddpexception.printStackTrace();
            returnResonse.setCode(ddpexception.getCode());
            returnResonse.setNewMessage(ddpexception.getCode());
            returnResonse.setResponseEntity(crdDTO);
            log.setStatckTrace(ddpexception.getStackTrace().toString());
        }
        catch (Exception e) {
            logger.error("NFC手机端:充值验卡接口失败,系统错误:respcode:" + ResponseCode.SYSTEM_ERROR + "," + e.getMessage(), e);
            e.printStackTrace();
            returnResonse.setCode(ResponseCode.SYSTEM_ERROR);
            returnResonse.setNewMessage(ResponseCode.SYSTEM_ERROR);
            returnResonse.setResponseEntity(crdDTO);
            log.setStatckTrace(e.getStackTrace().toString());
        }
        finally{
            log.setServerName(CommonConstants.SYSTEM_NAME_PRODUCT);
            if (returnResonse.getResponseEntity() != null){
                log.setOrderNum(returnResonse.getResponseEntity().getPrdordernum());
                log.setSource(returnResonse.getResponseEntity().getSource());
            }
            log.setTranNum("");
            if(ResponseCode.SUCCESS.equals(returnResonse.getCode())){
                log.setTradeType(0);
            }else{
                log.setTradeType(1);
            }
            log.setClassName(BJnfcMobileRechargeController.class.toString());
            log.setMethodName("chargeValidateCard");
            log.setInParas(jsondata);
            log.setOutParas(JSONObject.toJSONString(returnResonse));
            log.setRespCode(returnResonse.getCode());
            log.setRespExplain(returnResonse.getMessage());
            log.setDescription("NFC手机端充值验卡接口");
            log.setIpAddress("");
            log.setLogDate(new Date());
            log.setTradeEnd(Long.parseLong(DateUtils.dateToString(new Date(), DateUtils.DATE_FORMAT_YYMMDDHHMMSSSSS_STR)));
            log.setTradeRrack(log.getTradeEnd()-log.getTradeStart());
            // 记录日志
            ActivemqLogPublisher.publishLog2Queue(log, DodopalAppVarPropsUtil.getStringProp(CommonConstants.SERVER_LOG_NAME), DodopalAppVarPropsUtil.getStringProp(CommonConstants.SERVER_LOG_URL));
        }
        
        // 清空message,让页面弹出消息取自数据库消息
        String message = returnResonse.getMessage();
        returnResonse.setCode(returnResonse.getCode());
        
        // 错误码统一转换
        if(!ResponseCode.SUCCESS.equals(returnResonse.getCode())){
            returnResonse.setBJNewMessage(ResponseCode.PRODUCT_RESCHARGE_ERROR, message);
        }
        
        if (ResponseCode.SUCCESS.equals(returnResonse.getCode())) {
            logger.info("NFC手机端:充值验卡接口结束,返回客户端:成功响应码:" + returnResonse.getCode() + ",完整报文体：" + JSONObject.toJSONString(returnResonse)); 
        } else {
            logger.error("NFC手机端:充值验卡接口结束,返回客户端:失败响应码:" + returnResonse.getCode() + ",完整报文体：" + JSONObject.toJSONString(returnResonse));
        }
        
        return returnResonse;
    }
    
    
    /**
     * 充值申请起始接口
     */
    @RequestMapping("/chargeStart")
    public @ResponseBody DodopalResponse<BJCrdSysOrderDTO> chargeStart(HttpServletRequest request) {

        DodopalResponse<BJCrdSysOrderDTO> returnResonse = new DodopalResponse<BJCrdSysOrderDTO>();
        String jsondata = request.getParameter("jsondata");
        logger.info("进入NFC手机端:充值申请起始接口:接收到的json参数:" + jsondata);
        
        SysLog log = new SysLog();
        log.setTradeStart(Long.parseLong(DateUtils.dateToString(new Date(), DateUtils.DATE_FORMAT_YYMMDDHHMMSSSSS_STR)));
        
        BJCrdSysOrderDTO crdDTO = null;
        try {
            
            // 客户端传参转化为BJCrdSysOrderDTO对象
            crdDTO = this.jsondataConvertDto(jsondata);

            // 参数验证
            this.checkJsonParmError(crdDTO, "3");

            // 产品库验签
            this.checkDSIGN(crdDTO,jsondata);

            // 执行充值申请起始
            returnResonse = bjnfcMobileRechargeService.chargeStart(crdDTO);
            
            // 记录失败原因
            if (!ResponseCode.SUCCESS.equals(returnResonse.getCode())) {
                logger.error("NFC手机端:充值申请起始接口失败,失败响应码:" + returnResonse.getCode() + ",响应消息:" + returnResonse.getMessage());
            }
            
            // 产品库签名（对于黑名单引起的验卡失败，同样需要签名）
            String p_sign = this.getBackDSIGN(returnResonse.getResponseEntity());
            returnResonse.getResponseEntity().setP_sign(p_sign);

        }
        catch (DDPException ddpexception) {
            logger.error("NFC手机端:充值申请起始接口失败,异常响应码:" + ddpexception.getCode() + ",响应消息:" + ddpexception.getMessage());
            ddpexception.printStackTrace();
            returnResonse.setCode(ddpexception.getCode());
            returnResonse.setNewMessage(ddpexception.getCode());
            returnResonse.setResponseEntity(crdDTO);
            log.setStatckTrace(ddpexception.getStackTrace().toString());
        }
        catch (Exception e) {
            logger.error("NFC手机端:充值申请起始接口失败,系统错误:respcode:" + ResponseCode.SYSTEM_ERROR + "," + e.getMessage(), e);
            e.printStackTrace();
            returnResonse.setCode(ResponseCode.SYSTEM_ERROR);
            returnResonse.setNewMessage(ResponseCode.SYSTEM_ERROR);
            returnResonse.setResponseEntity(crdDTO);
            log.setStatckTrace(e.getStackTrace().toString());
        }
        finally{
            log.setServerName(CommonConstants.SYSTEM_NAME_PRODUCT);
            if (returnResonse.getResponseEntity() != null){
                log.setOrderNum(returnResonse.getResponseEntity().getPrdordernum());
                log.setSource(returnResonse.getResponseEntity().getSource());
            }
            log.setTranNum("");
            if(ResponseCode.SUCCESS.equals(returnResonse.getCode())){
                log.setTradeType(0);
            }else{
                log.setTradeType(1);
            }
            log.setClassName(BJnfcMobileRechargeController.class.toString());
            log.setMethodName("chargeStart");
            log.setInParas(jsondata);
            log.setOutParas(JSONObject.toJSONString(returnResonse));
            log.setRespCode(returnResonse.getCode());
            log.setRespExplain(returnResonse.getMessage());
            log.setDescription("NFC手机端充值申请起始接口");
            log.setIpAddress("");
            log.setLogDate(new Date());
            log.setTradeEnd(Long.parseLong(DateUtils.dateToString(new Date(), DateUtils.DATE_FORMAT_YYMMDDHHMMSSSSS_STR)));
            log.setTradeRrack(log.getTradeEnd()-log.getTradeStart());
            // 记录日志
            ActivemqLogPublisher.publishLog2Queue(log, DodopalAppVarPropsUtil.getStringProp(CommonConstants.SERVER_LOG_NAME), DodopalAppVarPropsUtil.getStringProp(CommonConstants.SERVER_LOG_URL));
        }
        
        // 清空message,让页面弹出消息取自数据库消息
        String message = returnResonse.getMessage();
        returnResonse.setCode(returnResonse.getCode());
        
        // 错误码统一转换
        if(!ResponseCode.SUCCESS.equals(returnResonse.getCode())){
            returnResonse.setBJNewMessage(ResponseCode.PRODUCT_RESCHARGE_ERROR, message);
        }
        
        if (ResponseCode.SUCCESS.equals(returnResonse.getCode())) {
            logger.info("NFC手机端:充值申请起始接口结束,返回客户端:成功响应码:" + returnResonse.getCode() + ",完整报文体：" + JSONObject.toJSONString(returnResonse)); 
        } else {
            logger.error("NFC手机端:充值申请起始接口结束,返回客户端:失败响应码:" + returnResonse.getCode() + ",完整报文体：" + JSONObject.toJSONString(returnResonse));
        }
        
        return returnResonse;
    }
    
    /**
     * 充值申请后续接口
     */
    @RequestMapping("/chargeFollow")
    public @ResponseBody DodopalResponse<BJCrdSysOrderDTO> chargeFollow(HttpServletRequest request) {

        DodopalResponse<BJCrdSysOrderDTO> returnResonse = new DodopalResponse<BJCrdSysOrderDTO>();
        String jsondata = request.getParameter("jsondata");
        logger.info("进入NFC手机端:充值申请后续接口:接收到的json参数:" + jsondata);
        
        SysLog log = new SysLog();
        log.setTradeStart(Long.parseLong(DateUtils.dateToString(new Date(), DateUtils.DATE_FORMAT_YYMMDDHHMMSSSSS_STR)));
        
        BJCrdSysOrderDTO crdDTO = null;
        try {
            
            // 客户端传参转化为BJCrdSysOrderDTO对象
            crdDTO = this.jsondataConvertDto(jsondata);

            // 参数验证
            this.checkJsonParmError(crdDTO, "3");

            // 产品库验签
            this.checkDSIGN(crdDTO,jsondata);

            // 执行充值申请后续
            returnResonse = bjnfcMobileRechargeService.chargeFollow(crdDTO);
            
            // 记录失败原因
            if (!ResponseCode.SUCCESS.equals(returnResonse.getCode())) {
                logger.error("NFC手机端:充值申请后续接口失败,失败响应码:" + returnResonse.getCode() + ",响应消息:" + returnResonse.getMessage());
            }
            
            // 产品库签名（对于黑名单引起的验卡失败，同样需要签名）
            String p_sign = this.getBackDSIGN(returnResonse.getResponseEntity());
            returnResonse.getResponseEntity().setP_sign(p_sign);

        }
        catch (DDPException ddpexception) {
            logger.error("NFC手机端:充值申请后续接口失败,异常响应码:" + ddpexception.getCode() + ",响应消息:" + ddpexception.getMessage());
            ddpexception.printStackTrace();
            returnResonse.setCode(ddpexception.getCode());
            returnResonse.setNewMessage(ddpexception.getCode());
            returnResonse.setResponseEntity(crdDTO);
            log.setStatckTrace(ddpexception.getStackTrace().toString());
        }
        catch (Exception e) {
            logger.error("NFC手机端:充值申请后续接口失败,系统错误:respcode:" + ResponseCode.SYSTEM_ERROR + "," + e.getMessage(), e);
            e.printStackTrace();
            returnResonse.setCode(ResponseCode.SYSTEM_ERROR);
            returnResonse.setNewMessage(ResponseCode.SYSTEM_ERROR);
            returnResonse.setResponseEntity(crdDTO);
            log.setStatckTrace(e.getStackTrace().toString());
        }
        finally{
            log.setServerName(CommonConstants.SYSTEM_NAME_PRODUCT);
            if (returnResonse.getResponseEntity() != null){
                log.setOrderNum(returnResonse.getResponseEntity().getPrdordernum());
                log.setSource(returnResonse.getResponseEntity().getSource());
            }
            log.setTranNum("");
            if(ResponseCode.SUCCESS.equals(returnResonse.getCode())){
                log.setTradeType(0);
            }else{
                log.setTradeType(1);
            }
            log.setClassName(BJnfcMobileRechargeController.class.toString());
            log.setMethodName("chargeFollow");
            log.setInParas(jsondata);
            log.setOutParas(JSONObject.toJSONString(returnResonse));
            log.setRespCode(returnResonse.getCode());
            log.setRespExplain(returnResonse.getMessage());
            log.setDescription("NFC手机端充值申请后续接口");
            log.setIpAddress("");
            log.setLogDate(new Date());
            log.setTradeEnd(Long.parseLong(DateUtils.dateToString(new Date(), DateUtils.DATE_FORMAT_YYMMDDHHMMSSSSS_STR)));
            log.setTradeRrack(log.getTradeEnd()-log.getTradeStart());
            // 记录日志
            ActivemqLogPublisher.publishLog2Queue(log, DodopalAppVarPropsUtil.getStringProp(CommonConstants.SERVER_LOG_NAME), DodopalAppVarPropsUtil.getStringProp(CommonConstants.SERVER_LOG_URL));
        }
        
        // 清空message,让页面弹出消息取自数据库消息
        String message = returnResonse.getMessage();
        returnResonse.setCode(returnResonse.getCode());
        
        // 错误码统一转换
        if(!ResponseCode.SUCCESS.equals(returnResonse.getCode())){
            returnResonse.setBJNewMessage(ResponseCode.PRODUCT_RESCHARGE_ERROR, message);
        }
        
        if (ResponseCode.SUCCESS.equals(returnResonse.getCode())) {
            logger.info("NFC手机端:充值申请后续接口结束,返回客户端:成功响应码:" + returnResonse.getCode() + ",完整报文体：" + JSONObject.toJSONString(returnResonse)); 
        } else {
            logger.error("NFC手机端:充值申请后续接口结束,返回客户端:失败响应码:" + returnResonse.getCode() + ",完整报文体：" + JSONObject.toJSONString(returnResonse));
        }
        
        return returnResonse;
    }
    
    
    /**
     * 充值结果上传
     */
    @RequestMapping("/chargeResultUp")
    public @ResponseBody DodopalResponse<BJCrdSysOrderDTO> chargeResultUp(HttpServletRequest request) {

        DodopalResponse<BJCrdSysOrderDTO> returnResonse = new DodopalResponse<BJCrdSysOrderDTO>();
        String jsondata = request.getParameter("jsondata");
        logger.info("进入NFC手机端:充值结果上传接口:接收到的json参数:" + jsondata);
        
        SysLog log = new SysLog();
        log.setTradeStart(Long.parseLong(DateUtils.dateToString(new Date(), DateUtils.DATE_FORMAT_YYMMDDHHMMSSSSS_STR)));
        
        BJCrdSysOrderDTO crdDTO = null;
        try {
            
            // 客户端传参转化为BJCrdSysOrderDTO对象
            crdDTO = this.jsondataConvertDto(jsondata);

            // 参数验证
            this.checkJsonParmError(crdDTO, "3");

            // 产品库验签
            this.checkDSIGN(crdDTO,jsondata);

            // 执行充值结果上传
            returnResonse = bjnfcMobileRechargeService.chargeResultUp(crdDTO);
            
            // 记录失败原因
            if (!ResponseCode.SUCCESS.equals(returnResonse.getCode())) {
                logger.error("NFC手机端:充值结果上传接口失败,失败响应码:" + returnResonse.getCode() + ",响应消息:" + returnResonse.getMessage());
            }
            
            // 产品库签名（对于黑名单引起的验卡失败，同样需要签名）
            String p_sign = this.getBackDSIGN(returnResonse.getResponseEntity());
            returnResonse.getResponseEntity().setP_sign(p_sign);

        }
        catch (DDPException ddpexception) {
            logger.error("NFC手机端:充值结果上传接口失败,异常响应码:" + ddpexception.getCode() + ",响应消息:" + ddpexception.getMessage());
            ddpexception.printStackTrace();
            returnResonse.setCode(ddpexception.getCode());
            returnResonse.setNewMessage(ddpexception.getCode());
            returnResonse.setResponseEntity(crdDTO);
            log.setStatckTrace(ddpexception.getStackTrace().toString());
        }
        catch (Exception e) {
            logger.error("NFC手机端:充值结果上传接口失败,系统错误:respcode:" + ResponseCode.SYSTEM_ERROR + "," + e.getMessage(), e);
            e.printStackTrace();
            returnResonse.setCode(ResponseCode.SYSTEM_ERROR);
            returnResonse.setNewMessage(ResponseCode.SYSTEM_ERROR);
            returnResonse.setResponseEntity(crdDTO);
            log.setStatckTrace(e.getStackTrace().toString());
        }
        finally{
            log.setServerName(CommonConstants.SYSTEM_NAME_PRODUCT);
            if (returnResonse.getResponseEntity() != null){
                log.setOrderNum(returnResonse.getResponseEntity().getPrdordernum());
                log.setSource(returnResonse.getResponseEntity().getSource());
            }
            log.setTranNum("");
            if(ResponseCode.SUCCESS.equals(returnResonse.getCode())){
                log.setTradeType(0);
            }else{
                log.setTradeType(1);
            }
            log.setClassName(BJnfcMobileRechargeController.class.toString());
            log.setMethodName("chargeResultUp");
            log.setInParas(jsondata);
            log.setOutParas(JSONObject.toJSONString(returnResonse));
            log.setRespCode(returnResonse.getCode());
            log.setRespExplain(returnResonse.getMessage());
            log.setDescription("NFC手机端充值结果上传接口");
            log.setIpAddress("");
            log.setLogDate(new Date());
            log.setTradeEnd(Long.parseLong(DateUtils.dateToString(new Date(), DateUtils.DATE_FORMAT_YYMMDDHHMMSSSSS_STR)));
            log.setTradeRrack(log.getTradeEnd()-log.getTradeStart());
            // 记录日志
            ActivemqLogPublisher.publishLog2Queue(log, DodopalAppVarPropsUtil.getStringProp(CommonConstants.SERVER_LOG_NAME), DodopalAppVarPropsUtil.getStringProp(CommonConstants.SERVER_LOG_URL));
        }
        
        // 清空message,让页面弹出消息取自数据库消息
        String message = returnResonse.getMessage();
        returnResonse.setCode(returnResonse.getCode());
        
        // 错误码统一转换
        if(!ResponseCode.SUCCESS.equals(returnResonse.getCode())){
            returnResonse.setBJNewMessage(ResponseCode.PRODUCT_RESCHARGE_ERROR, message);
        }
        
        if (ResponseCode.SUCCESS.equals(returnResonse.getCode())) {
            logger.info("NFC手机端:充值结果上传接口结束,返回客户端:成功响应码:" + returnResonse.getCode() + ",完整报文体：" + JSONObject.toJSONString(returnResonse)); 
        } else {
            logger.error("NFC手机端:充值结果上传接口结束,返回客户端:失败响应码:" + returnResonse.getCode() + ",完整报文体：" + JSONObject.toJSONString(returnResonse));
        }
        
        return returnResonse;
    }
    
    
    /**
     * 通知产品库网银支付结果，作更新公交卡充值订单资金变动状态(一卡通充值选择非账户支付方式)
     */
    @RequestMapping("/callBackOrder")
    public @ResponseBody void callBackOrder(HttpServletRequest request, 
            @RequestParam(value="orderNum",required=false) String orderNum, 
            @RequestParam(value="ResponseCode",required=false)String responseCode,
            @RequestParam(value="sign",required=false)String sign,
            @RequestParam(value="notify_time",required=false)String notifyTime) {
        logger.info("callBackOrder进入网关支付完成后通知回调处理,订单号orderNum:"+orderNum+"ResponseCode:"+responseCode
                +"notify_time"+notifyTime);
        Map<String, String> signMap = new HashMap<String, String>();
        signMap.put("orderNum", orderNum);
        signMap.put("notify_time", notifyTime);
        signMap.put("ResponseCode", responseCode);
        String newSign = SignUtils.sign(SignUtils.createLinkString(SignUtils.removeNull(signMap)), CommonConstants.KEY, CharEncoding.UTF_8);
        logger.info("通知url延签开始原始签名为："+sign+"新签名为："+newSign);
        if(newSign.equals(sign)){
            logger.info("验签通过");
            String bcode = RateCodeEnum.getRateTypeByCode(request.getParameter("businessType")).getCode();
            if(RateCodeEnum.IC_LOAD.getCode().equals(bcode)){
                //一卡通圈存的订单状态修改
                logger.info("进行一卡通圈存的订单状态修改");
//              loadOrderService.updateloadOrderStateAfterAccountDeduct(orderNum);
            }else if(RateCodeEnum.YKT_RECHARGE.getCode().equals(bcode)){
                //一卡通充值的订单状态修改
                if(PayStatusEnum.PAID_FAIL.getCode().equals(responseCode)){
                    logger.info("支付失败修改订单状态");
                    productOrderService.updateOrderStatusWhenOnlinePayment(orderNum);
                }else if(TranInStatusEnum.ACCOUNT_VALUE_ADDED_SUCCESS.getCode().equals(responseCode)){
                    logger.info("账户加值成功修改订单状态");
                    productOrderService.updateOrderStatusWhenAccountRecharge(orderNum, ResponseCode.SUCCESS);
                }else if(TranInStatusEnum.ACCOUNT_VALUE_ADDED_FAIL.getCode().equals(responseCode)){
                    logger.info("账户加值失败修改订单状态");
                    productOrderService.updateOrderStatusWhenAccountRecharge(orderNum, ResponseCode.UNKNOWN_ERROR);
                }
            }
        }else{
            logger.info("验签不通过");
        }
    }

    //*********************************************************************************************************************************//
    //*********************************************             私有方法                           **************************************************//
    //*********************************************************************************************************************************//
    
    /**
     * 各环节jsondata共同处理转换CrdSysOrderDTO对象
     */
    private BJCrdSysOrderDTO jsondataConvertDto(String jsondata){
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
     * 充值各个环节接口入参验证
     * 
     * @param crdDTO  充值环节各个接口入参
     * @param link  充值环节标志位
     *              
     * @return
     */
    private void checkJsonParmError(BJCrdSysOrderDTO crdDTO, String link) {
        switch (link) {
            case "1":
//                if (StringUtils.isBlank(crdDTO.getYktcode())) {
//                    throw new DDPException(ResponseCode.PRODUCT_CARD_RECHARGE_PARAMETER_ERROR, "参数验证:一卡通编号为空");
//                }
                break;
            case "2":
//                if (StringUtils.isBlank(crdDTO.getTradecard())) {
//                    throw new DDPException(ResponseCode.PRODUCT_CARD_RECHARGE_PARAMETER_ERROR, "参数验证:卡号(前端读出)为空");
//                } 
//                if (StringUtils.isBlank(crdDTO.getPosid())) {
//                    throw new DDPException(ResponseCode.PRODUCT_CARD_RECHARGE_PARAMETER_ERROR, "参数验证:POS设备编号(posId)为空");
//                }
//                if (StringUtils.isBlank(crdDTO.getBefbal())) {
//                    throw new DDPException(ResponseCode.PRODUCT_CARD_RECHARGE_PARAMETER_ERROR, "参数验证:交易前卡余额为空");
//                }
//                if (StringUtils.isBlank(crdDTO.getSource())) {
//                    throw new DDPException(ResponseCode.PRODUCT_CARD_RECHARGE_PARAMETER_ERROR, "参数验证:来源为空");
//                }
                break;
            case "3":
//                if (StringUtils.isBlank(crdDTO.getTradecard())) {
//                    throw new DDPException(ResponseCode.PRODUCT_CARD_RECHARGE_PARAMETER_ERROR, "参数验证:卡号为空");
//                }
//                if (StringUtils.isBlank(crdDTO.getPrdordernum())) {
//                    throw new DDPException(ResponseCode.PRODUCT_CARD_RECHARGE_PARAMETER_ERROR, "参数验证:一产品库订单编号为空");
//                }
                break;
            case "4":
//                if (StringUtils.isBlank(crdDTO.getPrdordernum())) {
//                    throw new DDPException(ResponseCode.PRODUCT_CARD_RECHARGE_PARAMETER_ERROR, "参数验证:一产品库订单编号为空");
//                }
                break;
            case "5":
//                if (StringUtils.isBlank(crdDTO.getPrdordernum())) {
//                    throw new DDPException(ResponseCode.PRODUCT_CARD_RECHARGE_PARAMETER_ERROR, "参数验证:产品库订单编号为空");
//                }
//                if (!RechargeOrderResultEnum.checkCodeExist(crdDTO.getTxnstat())) {
//                    throw new DDPException(ResponseCode.PRODUCT_CARD_RECHARGE_PARAMETER_ERROR, "参数验证:结果上传交易状态（0：成功 1：失败 2：未知）错误：txnstat=" + crdDTO.getTxnstat());
//                }
//                if (StringUtils.isNotBlank(crdDTO.getBlackamt())) {
//                    try {
//                        Long.parseLong(crdDTO.getBlackamt());
//                    }
//                    catch (Exception e) {
//                        throw new DDPException(ResponseCode.PRODUCT_CARD_RECHARGE_PARAMETER_ERROR, "参数验证:充值后卡内余额" + crdDTO.getBlackamt());
//                    }
//                } else if (RechargeOrderResultEnum.RECHARGE_SUCCESS.getCode().equals(crdDTO.getTxnstat()) 
//                    || RechargeOrderResultEnum.RECHARGE_FAILURE.getCode().equals(crdDTO.getTxnstat())) {
//                    throw new DDPException(ResponseCode.PRODUCT_CARD_RECHARGE_PARAMETER_ERROR, "参数验证：上传充值结果(0:成功；1:失败)"+crdDTO.getTxnstat()+",但参数:充值后卡内余额为空");
//                }
                break;
            case "6":
//                if (StringUtils.isBlank(crdDTO.getPrdordernum())) {
//                    throw new DDPException(ResponseCode.PRODUCT_CARD_RECHARGE_PARAMETER_ERROR, "参数验证:产品库订单编号为空");
//                }
//                if (StringUtils.isBlank(crdDTO.getRespcode())) {
//                    throw new DDPException(ResponseCode.PRODUCT_CARD_RECHARGE_PARAMETER_ERROR, "参数验证:交易应答码为空");
//                }
//                if (StringUtils.isBlank(crdDTO.getUserid())) {
//                    throw new DDPException(ResponseCode.PRODUCT_CARD_RECHARGE_PARAMETER_ERROR, "参数验证:用户id为空");
//                }
                break;
            default:
                break;
        }
        
        // 产品库与DLL签名验签的参数验证
        if (StringUtils.isBlank(crdDTO.getD_sign())) {
            throw new DDPException(ResponseCode.PRODUCT_CARD_RECHARGE_PARAMETER_ERROR, "参数验证:d_sign为空");
        }
    }

    
    /**
     * 产品库验签
     */
    private void checkDSIGN(BJCrdSysOrderDTO crdDTO, String jsondata) {
        String signkey = DodopalAppVarPropsUtil.getStringProp("dll_paypwd");
        if (StringUtils.isBlank(signkey)) {
            throw new DDPException(ResponseCode.PRODUCT_GET_DSIGNKEY_ERROR, "产品库验签失败：获取产品库验签密钥失败");
        }
        String dsignstr = getSignStr(jsondata, signkey);
        if (!crdDTO.getD_sign().equals(dsignstr)) {
            throw new DDPException(ResponseCode.PRODUCT_DSIGN_CHEKC_ERROR, "产品库验签失败：加密后的MD5值为:" + dsignstr + ",客户端传过来的值为:" + crdDTO.getD_sign());
        }
    }

    /**
     * 产品库签名
     */
    private String getBackDSIGN(BJCrdSysOrderDTO crdDTO) {
        String signkey = DodopalAppVarPropsUtil.getStringProp("dll_backpwd");
        if (StringUtils.isBlank(signkey)) {
            throw new DDPException(ResponseCode.PRODUCT_GET_BACKDSIGNKEY_ERROR, "产品库签名失败：获取产品库签名密钥失败");
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
            if (k!=null && v !=null && (StringUtils.isNotBlank(v.toString())) && (!"m_sign".equals(k)) && (!"d_sign".equals(k)) && (!"p_sign".equals(k))) {
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

}
