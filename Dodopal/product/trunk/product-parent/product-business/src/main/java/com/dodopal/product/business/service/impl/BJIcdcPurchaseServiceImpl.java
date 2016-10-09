package com.dodopal.product.business.service.impl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.caucho.hessian.client.HessianRuntimeException;
import com.dodopal.api.card.dto.BJCrdSysOrderDTO;
import com.dodopal.api.card.dto.ReslutData;
import com.dodopal.api.product.dto.IcdcPurchaseDTO;
import com.dodopal.api.card.dto.ReslutDataParameter;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.PurchaseOrderResultEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.Area;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.service.AreaService;
import com.dodopal.product.business.bean.ConsumeOrder;
import com.dodopal.product.business.model.ProductYKT;
import com.dodopal.product.business.service.BJIcdcPurchaseService;
import com.dodopal.product.business.service.IcdcPurchaseOrderService;
import com.dodopal.product.business.service.ProductYKTService;
import com.dodopal.product.delegate.BJIcdcPurchaseDelegate;

/**
 * 北京城市一卡通消费业务流程接口实现类
 * @author dodopal
 */
@Service
public class BJIcdcPurchaseServiceImpl implements BJIcdcPurchaseService {

    private final static Logger log = LoggerFactory.getLogger(BJIcdcPurchaseServiceImpl.class);
    @Autowired
    private BJIcdcPurchaseDelegate bjIcdcPurchaseDelegate;
    @Autowired
    private IcdcPurchaseOrderService icdcPurchaseOrderService;
    @Autowired
    private ProductYKTService productYKTService;
    @Autowired
    private AreaService areaService;

    /**
     * 消费验卡生单（V61、V71联机/脱机消费）
     */
    @Override
    @Transactional
    public DodopalResponse<BJCrdSysOrderDTO> checkCardCreateOrder(BJCrdSysOrderDTO crdDTO, String posType) {

        DodopalResponse<BJCrdSysOrderDTO> response = new DodopalResponse<BJCrdSysOrderDTO>();

        ProductYKT productYKT = null;
        
        if ("V71".equals(posType)) {
            
            // 根据城市编号查询通卡编号
            productYKT = productYKTService.getYktInfoByBusinessCityCode(crdDTO.getCitycode());
            if (productYKT != null) {
                crdDTO.setYktcode(productYKT.getYktCode());
            }
        }

        //  1.检验通卡公司的合法性
        DodopalResponse<ProductYKT> yktResponse = productYKTService.validateYktServiceNormalForIcdcConsume(crdDTO.getYktcode());
        if (!ResponseCode.SUCCESS.equals(yktResponse.getCode())) {
            response.setCode(yktResponse.getCode());
            response.setMessage(yktResponse.getMessage());
            response.setResponseEntity(crdDTO);
            return response;
        }
        productYKT = yktResponse.getResponseEntity();

        
        //  2.调用用户系统,验证当前商户/操作人员是否合法
        DodopalResponse<Map<String, String>> merCheckResponse = null;
        try {
            if ("V71".equals(posType)) {
                merCheckResponse = bjIcdcPurchaseDelegate.validateMerchantForIcdcPurchase(crdDTO.getMercode(), "", crdDTO.getPosid(), crdDTO.getYktcode(), crdDTO.getSource());
            } else if ("V61".equals(posType)) {
                merCheckResponse = bjIcdcPurchaseDelegate.validateMerchantForIcdcPurchase(crdDTO.getMercode(), crdDTO.getUserid(), crdDTO.getPosid(), crdDTO.getYktcode(), crdDTO.getSource());
            }
            
        }
        catch (HessianRuntimeException e) {
            throw new DDPException(ResponseCode.PRODUCT_CALL_USERS_ERROR, "调用用户系统校验商户合法性失败:Hessian异常");
        }
        if (!ResponseCode.SUCCESS.equals(merCheckResponse.getCode())) {
            throw new DDPException(merCheckResponse.getCode(), "商户合法性校验未通过:" + merCheckResponse.getMessage());
        }
        Map<String, String> merInfoMap = merCheckResponse.getResponseEntity();
        String merCode = merInfoMap.containsKey("merCode") ? merInfoMap.get("merCode") : "";
        if (StringUtils.isEmpty(crdDTO.getMercode().replaceAll("0", ""))) {
            crdDTO.setMercode(merCode);
        }
        
        crdDTO.setCreateUser(crdDTO.getUserid());//v71操作员ID
        
        if ("V71".equals(posType)) {
            String merUserId = merInfoMap.containsKey("userId") ? merInfoMap.get("userId") : "";//商户管理员ID
            String merUserCode = merInfoMap.containsKey("userCode") ? merInfoMap.get("userCode") : "";//商户管理员编号
            crdDTO.setUserid(merUserId);
            crdDTO.setUsercode(merUserCode);
        }
        
        //  3.调用产品库创建订单接口
        IcdcPurchaseDTO purchaseDto = new IcdcPurchaseDTO();
        purchaseDto.setCitycode(crdDTO.getCitycode());
        purchaseDto.setMercode(crdDTO.getMercode());
        purchaseDto.setMerdiscount(crdDTO.getMerdiscount());
        purchaseDto.setPaytype(crdDTO.getPaytype());
        purchaseDto.setPayway(crdDTO.getPayway());
        purchaseDto.setPosid(crdDTO.getPosid());
        purchaseDto.setReceivableAmt(crdDTO.getReceivableAmt());
        purchaseDto.setReceivedAmt(crdDTO.getReceivedAmt());
        purchaseDto.setSource(crdDTO.getSource());
        purchaseDto.setTxntype(crdDTO.getTxntype());
        purchaseDto.setUserid(crdDTO.getUserid());
        purchaseDto.setYktcode(crdDTO.getYktcode());
        
        String userdiscount = crdDTO.getUserdiscount();// 用户折扣
        String settldiscount = crdDTO.getSettldiscount();// 结算折扣
        
        DodopalResponse<String> bookOrderResponse = null;
        
        if ("V71".equals(posType)) {
            purchaseDto.setCreateUser(crdDTO.getCreateUser());//v71操作员ID
            bookOrderResponse = icdcPurchaseOrderService.bookBJPurchaseOrderForV71(purchaseDto, merInfoMap, productYKT);
        } else if ("V61".equals(posType)) {
            bookOrderResponse = icdcPurchaseOrderService.bookBJPurchaseOrderForV61(purchaseDto, merInfoMap, productYKT, userdiscount, settldiscount);
        }
        
        if (!ResponseCode.SUCCESS.equals(bookOrderResponse.getCode()) || StringUtils.isBlank(bookOrderResponse.getResponseEntity())) {
            throw new DDPException(bookOrderResponse.getCode(), "产品库订单创建失败:" + bookOrderResponse.getMessage());
        }
        crdDTO.setPrdordernum(bookOrderResponse.getResponseEntity());

        //  4.卡服务验卡生单
        DodopalResponse<BJCrdSysOrderDTO> cardResponse = null;
        try {
            if ("V71".equals(posType)) {
                cardResponse = bjIcdcPurchaseDelegate.queryCheckCardConsByV71(crdDTO);
            } else if ("V61".equals(posType)) {
                cardResponse = bjIcdcPurchaseDelegate.queryCheckCardConsByV61(crdDTO);
            }
        }
        catch (HessianRuntimeException e) {
            throw new DDPException(ResponseCode.PRODUCT_CALL_CARD_ERROR, "卡服务验卡生单接口失败:Hessian异常");
        }

        // 验卡过后，获取卡片的卡面号
        String cardFaceno = cardResponse.getResponseEntity().getCardfaceno();
        crdDTO.setCardfaceno(cardFaceno);

        // 5.产品库更新订单状态
        if ("0".equals(cardResponse.getResponseEntity().getTradeendflag())) {
            if (log.isInfoEnabled()) {
                log.info("卡服务验卡生单接口失败,卡服务返回响应码:" + cardResponse.getCode() + ",响应消息:" + cardResponse.getMessage() + ",产品库订单:" + crdDTO.getPrdordernum() + ",直接返回给调用方.");
            }
        } else {
            DodopalResponse<Integer> updateResponse = icdcPurchaseOrderService.updateIcdcPurchaseOrderWhenCheckCard(crdDTO.getPrdordernum(), crdDTO.getTradecard(), crdDTO.getCardfaceno(), crdDTO.getPosid(), Integer.parseInt(crdDTO.getBefbal()), cardResponse.getCode());

            if (!ResponseCode.SUCCESS.equals(cardResponse.getCode()) && updateResponse.getResponseEntity() == 0) {
                //卡服务验卡生单接口失败，产品库订单状态更新失败
                response.setCode(cardResponse.getCode());
                response.setMessage("卡服务验卡生单接口失败:响应码:" + cardResponse.getCode() + ",响应消息:" + cardResponse.getMessage() + ";更新订单状态也失败:响应码:" + updateResponse.getCode() + "响应消息:" + updateResponse.getMessage() + ";产品库订单:" + crdDTO.getPrdordernum());
                response.setResponseEntity(crdDTO);
                return response;
            } else if (ResponseCode.SUCCESS.equals(cardResponse.getCode()) && updateResponse.getResponseEntity() == 0) {
                //卡服务验卡生单接口成功，产品库订单状态更新失败
                response.setCode(updateResponse.getCode());
                response.setMessage("卡服务验卡生单接口成功,但更新订单状态失败:响应码:" + updateResponse.getCode() + "响应消息:" + updateResponse.getMessage() + ";产品库订单:" + crdDTO.getPrdordernum());
                response.setResponseEntity(crdDTO);
                return response;
            }
        }

        response.setCode(cardResponse.getCode());
        response.setResponseEntity(cardResponse.getResponseEntity());
        return response;
    }

    /****************************** V61 START 北京城市一卡通消费业务流程接口 ******************************/

    /**
     * 申请消费密钥（V61联机消费）
     */
    @Override
    @Transactional
    public DodopalResponse<BJCrdSysOrderDTO> applyConsumeByV61(BJCrdSysOrderDTO crdDTO) {
        
        DodopalResponse<BJCrdSysOrderDTO> response = new DodopalResponse<BJCrdSysOrderDTO>();

        //1.验证订单的合法性
        DodopalResponse<ConsumeOrder> validOrderResponse = icdcPurchaseOrderService.validateIcdcPurchaseOrderWhenDeductCard(crdDTO.getPrdordernum());
        if (!ResponseCode.SUCCESS.equals(validOrderResponse.getCode())) {
            throw new DDPException(validOrderResponse.getCode(), "申请扣款指令,第一步,验证产品库订单失败:" + validOrderResponse.getMessage());
        }
        crdDTO.setCitycode(validOrderResponse.getResponseEntity().getCityCode());

        // 重发业务判断标志位（默认为第一次请求）
        boolean requestAgain = false;
        if ("0".equals(validOrderResponse.getResponseEntity().getResendSign())) {

            // 判断产品库接到DLL第一次请求，更新订单重发标志位：置为1
            DodopalResponse<Integer> updateResponse = icdcPurchaseOrderService.updateResendSignByOrderNum(crdDTO.getPrdordernum());
            if (!ResponseCode.SUCCESS.equals(updateResponse.getCode()) || updateResponse.getResponseEntity() == 0) {
                throw new DDPException(ResponseCode.PRODUCT_PURCHASE_ORDER_ERROR, "非法订单:订单不存在。订单编号:" + crdDTO.getPrdordernum());
            }
            
        } else if ("1".equals(validOrderResponse.getResponseEntity().getResendSign())) {

            // 重发标志位为1，则认为本次请求为DLL重发请求
            log.info("申请扣款指令,产品库重发标志位为1,判断本次请求为DLL重发请求.产品库透传卡服务结果,不修改订单状态。订单编号:" + crdDTO.getPrdordernum());
            requestAgain = true;
        }

        //2.调用卡服务提供的申请扣款指令
        DodopalResponse<BJCrdSysOrderDTO> cardResponse = null;
        try {
            cardResponse = bjIcdcPurchaseDelegate.getDeductOrderCons(crdDTO);
        }
        catch (HessianRuntimeException e) {
            throw new DDPException(ResponseCode.PRODUCT_CALL_CARD_ERROR, "申请扣款指令,第二步,调用卡服务,申请扣款指令失败:Hessian异常");
        }

        // 处理dll第一次请求
        if (!requestAgain) {
            
            if ("0".equals(cardResponse.getResponseEntity().getTradeendflag())) {
                
                //如果tradeendflag=0，则直接将结果返回给调用方
                if (log.isInfoEnabled()) {
                    log.info("申请扣款指令,第三步,调用卡服务,申请扣款指令,卡服务返回响应码:" + cardResponse.getCode() + ",响应消息:" + cardResponse.getMessage() + ",产品库订单:" + crdDTO.getPrdordernum() + ",直接返回给调用方.");
                }
                
            } else if ("1".equals(cardResponse.getResponseEntity().getTradeendflag())) {
                
                //如果tradeendflag=1，调用产品库的方法，更新产品库订单状态
                DodopalResponse<Integer> updateResponse = icdcPurchaseOrderService.updateIcdcPurchaseOrderWhenDeductCard(crdDTO.getPrdordernum(), cardResponse.getCode());
                
                if (!ResponseCode.SUCCESS.equals(cardResponse.getCode()) && updateResponse.getResponseEntity() == 0) {
                    
                    //申请扣款指令失败，产品库订单状态更新失败
                    response.setCode(cardResponse.getCode());
                    response.setMessage("申请扣款指令失败:响应码:" + cardResponse.getCode() + ",响应消息:" + cardResponse.getMessage() + ";更新订单状态也失败:响应码:" + updateResponse.getCode() + "响应消息:" + updateResponse.getMessage() + ";产品库订单:" + crdDTO.getPrdordernum());
                    response.setResponseEntity(crdDTO);
                    return response;
                    
                } else if (ResponseCode.SUCCESS.equals(cardResponse.getCode()) && updateResponse.getResponseEntity() == 0) {
                    
                    //申请扣款指令成功，产品库订单状态更新失败
                    response.setCode(updateResponse.getCode());
                    response.setMessage("申请扣款指令成功,但更新订单状态失败:响应码:" + updateResponse.getCode() + "响应消息:" + updateResponse.getMessage() + ";产品库订单:" + crdDTO.getPrdordernum());
                    response.setResponseEntity(crdDTO);
                    return response;
                }
            }
        }
        
        response.setCode(cardResponse.getCode());
        response.setResponseEntity(cardResponse.getResponseEntity());
        return response;
    }

    /**
     * 消费结果上传（V61联机、脱机消费）
     */
    @Override
    @Transactional
    public DodopalResponse<BJCrdSysOrderDTO> uploadConsumeResultByV61(BJCrdSysOrderDTO crdDTO) {
        
        DodopalResponse<BJCrdSysOrderDTO> response = new DodopalResponse<BJCrdSysOrderDTO>();
        
        // 判断锁卡标识
        String txnType = "";
        if (null != crdDTO.getSpecdata() && null != crdDTO.getSpecdata().getOfflinedata() 
            && null !=crdDTO.getSpecdata().getOfflinedata().getFilerecords() 
            && null != crdDTO.getSpecdata().getOfflinedata().getFilerecords().get(0)) {
            
            ReslutData reslutData = crdDTO.getSpecdata().getOfflinedata().getFilerecords().get(0);
            log.info("锁卡标示:" + reslutData.getTxntype());
            txnType = reslutData.getTxntype().toUpperCase();
        }
        if (!"0A".equals(txnType)) {

            String txnstat = crdDTO.getTxnstat();// 结果上传交易状态0：成功 1：失败 2：未知
            
            if (log.isInfoEnabled()) {
                log.info("上传收单结果,第一步,验证订单的合法性,产品库订单=" + crdDTO.getPrdordernum() + ",txnstat=" + txnstat);
            }

            //1.验证订单的合法性
            DodopalResponse<ConsumeOrder> validOrderResponse = icdcPurchaseOrderService.validateBJOrderWhenUploadResultByV61(crdDTO.getPrdordernum(), txnstat, crdDTO.getBlackamt());
            
            if (!ResponseCode.SUCCESS.equals(validOrderResponse.getCode())) {
                
                throw new DDPException(validOrderResponse.getCode(), "上传收单结果,第一步,验证产品库订单失败:" + validOrderResponse.getMessage());
            }
            
            crdDTO.setCitycode(validOrderResponse.getResponseEntity().getCityCode());

            if (log.isInfoEnabled()) {
                log.info("上传收单结果,第二步,调用卡服务,上传收单结果,产品库订单=" + crdDTO.getPrdordernum() + ",txnstat=" + txnstat);
            }
        }
     
        //2.调用卡服务提供的消费结果上传
        DodopalResponse<BJCrdSysOrderDTO> cardResponse = null;
        try {
            if ("0".equals(crdDTO.getOfflineflag())) {

                // 联机消费，执行上传收单结果接口
                cardResponse = bjIcdcPurchaseDelegate.uploadResultConsByOnlineV61(crdDTO);
                
            } else if ("1".equals(crdDTO.getOfflineflag())) {

                // 脱机消费，执行上传收单结果接口
                cardResponse = bjIcdcPurchaseDelegate.uploadResultConsByOfflineV61(crdDTO);
            }

        }
        catch (HessianRuntimeException e) {
            throw new DDPException(ResponseCode.PRODUCT_CALL_CARD_ERROR, "上传收单结果,第二步,调用卡服务,上传收单结果失败:Hessian异常");
        }
        
        response.setCode(cardResponse.getCode());
        response.setResponseEntity(cardResponse.getResponseEntity());
        return response;
    }

    /****************************** V61 END 北京城市一卡通消费业务流程接口 ******************************/

    /****************************** V71 START 北京城市一卡通消费业务流程接口 ******************************/

    /**
     * 消费申请消费密钥（V71联机消费）
     */
    @Override
    public DodopalResponse<BJCrdSysOrderDTO> applyConsumeByOnlineV71(BJCrdSysOrderDTO crdDTO) {
        DodopalResponse<BJCrdSysOrderDTO> response = new DodopalResponse<BJCrdSysOrderDTO>();

        //1.验证订单的合法性
        DodopalResponse<ConsumeOrder> validOrderResponse = icdcPurchaseOrderService.validateIcdcPurchaseOrderWhenDeductCard(crdDTO.getPrdordernum());
        
        if (!ResponseCode.SUCCESS.equals(validOrderResponse.getCode())) {
            response.setCode(validOrderResponse.getCode());
            response.setMessage("验证产品库订单失败:" + validOrderResponse.getMessage());
            response.setResponseEntity(crdDTO);
            return response;
        }
        
        crdDTO.setCitycode(validOrderResponse.getResponseEntity().getCityCode());
        crdDTO.setYktcode(validOrderResponse.getResponseEntity().getYtkCode());

        // 重发业务判断标志位（默认为第一次请求）
        boolean requestAgain = false;
        if ("0".equals(validOrderResponse.getResponseEntity().getResendSign())) {

            // 判断产品库接到DLL第一次请求，更新订单重发标志位：置为1
            DodopalResponse<Integer> updateResponse = icdcPurchaseOrderService.updateResendSignByOrderNum(crdDTO.getPrdordernum());
            
            if (!ResponseCode.SUCCESS.equals(updateResponse.getCode()) || updateResponse.getResponseEntity() == 0) {
                
                response.setCode(ResponseCode.PRODUCT_PURCHASE_ORDER_ERROR);
                response.setMessage("非法订单:订单不存在。订单编号:" + crdDTO.getPrdordernum());
                response.setResponseEntity(crdDTO);
                return response;
            }
            
        } else if ("1".equals(validOrderResponse.getResponseEntity().getResendSign())) {

            // 重发标志位为1，则认为本次请求为DLL重发请求
            log.info("申请扣款指令,产品库重发标志位为1,判断本次请求为重发请求.产品库透传卡服务结果,不修改订单状态。订单编号:" + crdDTO.getPrdordernum());
            requestAgain = true;
        }

        //2.调用卡服务提供的申请扣款指令
        DodopalResponse<BJCrdSysOrderDTO> cardResponse = null;
        try {
            cardResponse = bjIcdcPurchaseDelegate.getDeductOrderConsByOnlineV71(crdDTO);
        }
        catch (HessianRuntimeException e) {
            
            response.setCode(ResponseCode.PRODUCT_CALL_CARD_ERROR);
            response.setMessage("调用卡服务,申请扣款指令失败:Hessian异常");
            response.setResponseEntity(crdDTO);
            return response;
        }

        // 处理dll第一次请求
        if (!requestAgain) {
            
            if ("0".equals(cardResponse.getResponseEntity().getTradeendflag())) {
                
                if (log.isInfoEnabled()) {
                    log.info("申请扣款指令,第三步,调用卡服务,申请扣款指令,卡服务返回响应码:" + cardResponse.getCode() + ",响应消息:" + cardResponse.getMessage() + ",产品库订单:" + crdDTO.getPrdordernum() + ",直接返回给调用方.");
                }
                
            } else if ("1".equals(cardResponse.getResponseEntity().getTradeendflag())) {
                
                //如果tradeendflag=1，调用产品库的方法，更新产品库订单状态
                DodopalResponse<Integer> updateResponse = icdcPurchaseOrderService.updateIcdcPurchaseOrderWhenDeductCard(crdDTO.getPrdordernum(), cardResponse.getCode());
               
                if (log.isInfoEnabled()) {
                    log.info("申请扣款指令,第三步,调用卡服务,申请扣款指令,卡服务返回响应码:" + cardResponse.getCode() + ",响应消息:" + cardResponse.getMessage() + ",产品库订单:" + crdDTO.getPrdordernum() + ",调用产品库方法修改产品库订单状态,修改订单返回应答码为:" + updateResponse.getCode());
                }
                
                if (!ResponseCode.SUCCESS.equals(cardResponse.getCode()) && updateResponse.getResponseEntity() == 0) {
                    
                    //  申请扣款指令失败，产品库订单状态更新失败
                    response.setCode(cardResponse.getCode());
                    response.setMessage("申请扣款指令失败:响应码:" + cardResponse.getCode() + ",响应消息:" + cardResponse.getMessage() + ";更新订单状态也失败:响应码:" + updateResponse.getCode() + "响应消息:" + updateResponse.getMessage() + ";产品库订单:" + crdDTO.getPrdordernum());
                    response.setResponseEntity(crdDTO);
                    return response;
                    
                } else if (ResponseCode.SUCCESS.equals(cardResponse.getCode()) && updateResponse.getResponseEntity() == 0) {
                    
                    //  申请扣款指令成功，产品库订单状态更新失败
                    response.setCode(updateResponse.getCode());
                    response.setMessage("申请扣款指令成功,但更新订单状态失败:响应码:" + updateResponse.getCode() + "响应消息:" + updateResponse.getMessage() + ";产品库订单:" + crdDTO.getPrdordernum());
                    response.setResponseEntity(crdDTO);
                    return response;
                }
            }
        }
        
        response.setCode(cardResponse.getCode());
        response.setResponseEntity(cardResponse.getResponseEntity());
        return response;
    }

    /**
     * 消费结果上传（V71联机消费）
     */
    @Override
    public DodopalResponse<BJCrdSysOrderDTO> uploadConsumeResultByOnlineV71(BJCrdSysOrderDTO crdDTO) {

        DodopalResponse<BJCrdSysOrderDTO> response = new DodopalResponse<BJCrdSysOrderDTO>();

        String txnstat = crdDTO.getTxnstat();// 结果上传交易状态0：成功 1：失败 2：未知

        String amtreceivable = "";//应收金额
        String userdiscount = "";//用户折扣
        String settldiscount = "";//结算折扣
        if (PurchaseOrderResultEnum.SUCCESS.getCode().equals(txnstat)) {
            amtreceivable = crdDTO.getSpecdata().getOfflinedata().getFilerecords().get(0).getAmtreceivable();
            userdiscount = crdDTO.getSpecdata().getOfflinedata().getFilerecords().get(0).getUserdiscount();
            settldiscount = crdDTO.getSpecdata().getOfflinedata().getFilerecords().get(0).getSettldiscount();
        }

        //1.验证订单的合法性
        DodopalResponse<ConsumeOrder> validOrderResponse = icdcPurchaseOrderService.validateBJOrderWhenUploadResultByV71(crdDTO.getPrdordernum(), txnstat, crdDTO.getBlackamt(), amtreceivable, userdiscount, settldiscount);
        
       if (!ResponseCode.SUCCESS.equals(validOrderResponse.getCode())) {
            
           response.setCode(validOrderResponse.getCode());
           response.setMessage(validOrderResponse.getMessage());
           response.setResponseEntity(crdDTO);
           return response;
        }
        
        crdDTO.setCitycode(validOrderResponse.getResponseEntity().getCityCode());
        crdDTO.setYktcode(validOrderResponse.getResponseEntity().getYtkCode());

        //2.调用卡服务提供的消费结果上传
        DodopalResponse<BJCrdSysOrderDTO> cardResponse = null;
        try {
            cardResponse = bjIcdcPurchaseDelegate.uploadResultConsByOnlineV71(crdDTO);
        }
        catch (HessianRuntimeException e) {
            
            response.setCode(ResponseCode.PRODUCT_CALL_CARD_ERROR);
            response.setMessage("调用卡服务,上传收单结果失败:Hessian异常");
            response.setResponseEntity(crdDTO);
            return response;
        }
        
        response.setCode(cardResponse.getCode());
        response.setResponseEntity(cardResponse.getResponseEntity());
        return response;
    }

    /**
     * 消费结果上传（V71脱机消费批量上传）
     */
    @Override
    public DodopalResponse<ReslutDataParameter> uploadConsumeResultByOfflineV71(ReslutDataParameter crdDTO) {

        log.info("消费结果上传（V71脱机消费批量上传）,入参：" + JSONObject.toJSONString(crdDTO));

        DodopalResponse<ReslutDataParameter> cardResponse = new DodopalResponse<ReslutDataParameter>();
        try {
            // 根据POS编号查询绑定的商户信息
            DodopalResponse<Map<String, String>> merInfoMapResponse = bjIcdcPurchaseDelegate.getMerchantByPosCode(crdDTO.getPosid());

            if (ResponseCode.SUCCESS.equals(merInfoMapResponse.getCode())) {
                Map<String, String> merInfoMap = merInfoMapResponse.getResponseEntity();
                String merCode = merInfoMap.containsKey("merCode") ? merInfoMap.get("merCode") : "";
                String mername = merInfoMap.containsKey("merName") ? merInfoMap.get("merName") : "";
                String merUserid = merInfoMap.containsKey("merUserid") ? merInfoMap.get("merUserid") : "";
                String merUserCode = merInfoMap.containsKey("merUserCode") ? merInfoMap.get("merUserCode") : "";
                String merType = merInfoMap.containsKey("merType") ? merInfoMap.get("merType") : "";
                crdDTO.setMercode(merCode);// 商户号
                crdDTO.setMername(mername);// 商户名称
                crdDTO.setUserid(merUserid);// 商户管理员用户ID
                crdDTO.setUsercode(merUserCode);// 商户管理员用户编号
                crdDTO.setMertype(merType);// 商户类型
            }
        }
        catch (HessianRuntimeException e) {
            cardResponse.setCode(ResponseCode.PRODUCT_CALL_USERS_ERROR);
            cardResponse.setMessage("调用用户服务,根据POS查询商户信息失败:Hessian异常");
            cardResponse.setResponseEntity(crdDTO);
            return cardResponse;
        }

        if (StringUtils.isNotBlank(crdDTO.getCitycode())) {
            // 查询业务城市信息
            Area area = areaService.findCityByCityCode(crdDTO.getCitycode());
            if (area != null) {
                crdDTO.setCityname(area.getCityName());// 城市名称
            }
            // 根据业务城市编号查询通卡信息
            ProductYKT productYKT = productYKTService.getYktInfoByBusinessCityCode(crdDTO.getCitycode());
            if (productYKT != null) {
                crdDTO.setYktcode(productYKT.getYktCode());// 一卡通code
                crdDTO.setYktname(productYKT.getYktName());// 通卡名称
                crdDTO.setYktpayrate(String.valueOf(productYKT.getYktPayRate()));// 通卡支付费率
            }
        }

        try {
            // 调用卡服务批量生单
            cardResponse = bjIcdcPurchaseDelegate.batchUploadResultByOfflineV71(crdDTO);
        }
        catch (HessianRuntimeException e) {
            cardResponse.setCode(ResponseCode.PRODUCT_CALL_CARD_ERROR);
            cardResponse.setMessage("调用卡服务,上传收单结果失败:Hessian异常");
            cardResponse.setResponseEntity(crdDTO);
            return cardResponse;
        }
        return cardResponse;
    }
    /****************************** V71 END 北京城市一卡通消费业务流程接口 ******************************/

}
