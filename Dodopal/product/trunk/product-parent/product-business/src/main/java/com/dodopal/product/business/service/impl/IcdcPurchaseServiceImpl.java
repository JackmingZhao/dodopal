package com.dodopal.product.business.service.impl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.caucho.hessian.client.HessianRuntimeException;
import com.dodopal.api.card.dto.CrdSysOrderDTO;
import com.dodopal.api.product.dto.IcdcPurchaseDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.business.bean.ConsumeOrder;
import com.dodopal.product.business.model.ProductYKT;
import com.dodopal.product.business.service.IcdcPurchaseOrderService;
import com.dodopal.product.business.service.IcdcPurchaseService;
import com.dodopal.product.business.service.ProductYKTService;
import com.dodopal.product.delegate.IcdcPurchaseDelegate;

@Service
public class IcdcPurchaseServiceImpl implements IcdcPurchaseService{
    
    private final static Logger log = LoggerFactory.getLogger(IcdcPurchaseServiceImpl.class);
    @Autowired
    private IcdcPurchaseDelegate icdcPurchaseDelegate;
    @Autowired
    private IcdcPurchaseOrderService icdcPurchaseOrderService;
    @Autowired
    private ProductYKTService productYKTService;
    
    /**
     * 生单接口
     */
    @Override
    @Transactional
    public DodopalResponse<IcdcPurchaseDTO> createIcdcPurchaseOrder(IcdcPurchaseDTO purchaseDto) {
        
        DodopalResponse<IcdcPurchaseDTO> response = new DodopalResponse<IcdcPurchaseDTO>();
        if(log.isInfoEnabled()){
            log.info("生单接口,第一步,调用用户系统,验证当前商户/操作人员是否合法,merchantcode="+purchaseDto.getMercode()+",operatorid="+purchaseDto.getUserid()+",posid="+purchaseDto.getPosid()+",yktcode="+purchaseDto.getYktcode());
        }
        //1.调用用户系统,验证当前商户/操作人员是否合法
        DodopalResponse<Map<String, String>> merCheckResponse = null;
        try {
            merCheckResponse = icdcPurchaseDelegate.validateMerchantForIcdcPurchase(purchaseDto.getMercode(), purchaseDto.getUserid(), purchaseDto.getPosid(), purchaseDto.getYktcode(), purchaseDto.getSource());
        }
        catch (HessianRuntimeException e) {
            throw new DDPException(ResponseCode.PRODUCT_CALL_USERS_ERROR, "createIcdcPurchaseOrder:生单接口,第一步,调用用户系统校验商户合法性失败:Hessian异常");
        }
        if (!ResponseCode.SUCCESS.equals(merCheckResponse.getCode())) {
            throw new DDPException(merCheckResponse.getCode(), "createIcdcPurchaseOrder:生单接口,第一步,商户合法性校验未通过:" + merCheckResponse.getMessage());
        }
        Map<String, String> merInfoMap = merCheckResponse.getResponseEntity();
        
        
        // ***********   检验通卡公司的合法性  add by shenXiang 2015-12-01 start   ***********//
        DodopalResponse<ProductYKT> yktResponse = productYKTService.validateYktServiceNormalForIcdcConsume(purchaseDto.getYktcode());
        if (!ResponseCode.SUCCESS.equals(yktResponse.getCode())) {
            response.setCode(yktResponse.getCode());
            response.setMessage(yktResponse.getMessage());
            response.setResponseEntity(purchaseDto);
            return response;
        }
        ProductYKT productYKT = yktResponse.getResponseEntity();
        // ***********   检验通卡公司的合法性  add by shenXiang 2015-12-01 end   ***********//
        
        
        if(log.isInfoEnabled()){
            log.info("生单接口,第二步,调用产品库创建订单接口");
        }
        //2.调用产品库创建订单接口
        DodopalResponse<String> bookOrderResponse = icdcPurchaseOrderService.bookIcdcPurchaseOrder(purchaseDto,merInfoMap,productYKT);
        if (!ResponseCode.SUCCESS.equals(bookOrderResponse.getCode()) || StringUtils.isBlank(bookOrderResponse.getResponseEntity())) {
            throw new DDPException(bookOrderResponse.getCode(), "生单接口,第二步,产品库订单创建失败:" + bookOrderResponse.getMessage());
        }
        purchaseDto.setPrdordernum(bookOrderResponse.getResponseEntity());
        response.setCode(bookOrderResponse.getCode());
        response.setResponseEntity(purchaseDto);
        return response;
    }
    
    
    /**
     * 申请扣款初始化指令
     */
    @Override
    @Transactional
    public DodopalResponse<CrdSysOrderDTO> checkCard(CrdSysOrderDTO crdDTO){
        DodopalResponse<CrdSysOrderDTO> response = new DodopalResponse<CrdSysOrderDTO>();
        if(log.isInfoEnabled()){
            log.info("申请扣款初始化指令,第一步,验证订单的合法性,产品库订单:"+crdDTO.getPrdordernum());
        }
        //1.验证订单的合法性
        DodopalResponse<ConsumeOrder> validOrderResponse = icdcPurchaseOrderService.validateIcdcPurchaseOrderWhenCheckCard(crdDTO.getPrdordernum());
        if (!ResponseCode.SUCCESS.equals(validOrderResponse.getCode())) {
            throw new DDPException(validOrderResponse.getCode(), "申请扣款初始化指令,第一步,验证产品库订单失败:" + validOrderResponse.getMessage());
        }
        crdDTO.setCitycode(validOrderResponse.getResponseEntity().getCityCode());
        
        if(log.isInfoEnabled()){
            log.info("申请扣款初始化指令,第二步,调用卡服务,申请扣款初始化指令,产品库订单:"+crdDTO.getPrdordernum());
        }
        
        //2.调用卡服务提供的申请扣款初始化指令
        DodopalResponse<CrdSysOrderDTO> cardResponse = null;
        try{
            cardResponse = icdcPurchaseDelegate.queryCheckCardConsFun(crdDTO);
        } catch (HessianRuntimeException e) {
            throw new DDPException(ResponseCode.PRODUCT_CALL_CARD_ERROR, "申请扣款初始化指令,第二步,调用卡服务,申请扣款初始化指令失败:Hessian异常");
        }
        
        // 验卡过后，获取卡片的卡面号
        String cardFaceno = cardResponse.getResponseEntity().getCardfaceno();
        crdDTO.setCardfaceno(cardFaceno);
        
        if(log.isInfoEnabled()){
            log.info("申请扣款初始化指令,第二步,调用卡服务,申请扣款初始化指令,卡服务返回响应码:"+cardResponse.getCode()+",响应消息:"+ cardResponse.getMessage()+",tradeendflag:"+cardResponse.getResponseEntity().getTradeendflag()+",产品库订单:"+crdDTO.getPrdordernum());
        }
        if("0".equals(cardResponse.getResponseEntity().getTradeendflag())){//如果tradeendflag=0，则直接将结果返回给调用方
            if(log.isInfoEnabled()){
                log.info("申请扣款初始化指令,第三步,调用卡服务,申请扣款初始化指令,卡服务返回响应码:"+cardResponse.getCode()+",响应消息:"+ cardResponse.getMessage()+",产品库订单:"+crdDTO.getPrdordernum()+",直接返回给调用方.");
            }
        }else{//如果tradeendflag=1，调用产品库的方法，更新产品库订单状态
            DodopalResponse<Integer> updateResponse = icdcPurchaseOrderService.updateIcdcPurchaseOrderWhenCheckCard(crdDTO.getPrdordernum(), crdDTO.getTradecard(), crdDTO.getCardfaceno(), crdDTO.getPosid(), Integer.parseInt(crdDTO.getBefbal()), cardResponse.getCode());
            if(log.isInfoEnabled()){
                log.info("申请扣款初始化指令,第三步,调用卡服务,申请扣款初始化指令,卡服务返回响应码:"+cardResponse.getCode()+",响应消息:"+ cardResponse.getMessage()+",产品库订单:"+crdDTO.getPrdordernum()+",调用产品库方法修改产品库订单状态,修改订单返回应答码为:"+updateResponse.getCode());
            }
            if (!ResponseCode.SUCCESS.equals(cardResponse.getCode()) && updateResponse.getResponseEntity() == 0) {
                //申请扣款指令失败，产品库订单状态更新失败
                response.setCode(cardResponse.getCode());
                response.setMessage("申请扣款初始化指令失败:响应码:"+cardResponse.getCode()+",响应消息:"+ cardResponse.getMessage()+";更新订单状态也失败:响应码:"+updateResponse.getCode() + "响应消息:" + updateResponse.getMessage() + ";产品库订单:" + crdDTO.getPrdordernum());
                response.setResponseEntity(crdDTO);
                return response;
            }else if (ResponseCode.SUCCESS.equals(cardResponse.getCode()) && updateResponse.getResponseEntity() == 0) {
                //申请扣款指令成功，产品库订单状态更新失败
                response.setCode(updateResponse.getCode());
                response.setMessage("申请扣款初始化指令成功,但更新订单状态失败:响应码:"+updateResponse.getCode() +"响应消息:"+ updateResponse.getMessage()+ ";产品库订单:" + crdDTO.getPrdordernum());
                response.setResponseEntity(crdDTO);
                return response;
            }
        }
        response.setCode(cardResponse.getCode());
        response.setResponseEntity(cardResponse.getResponseEntity());
        return response;
    }
    
    
    
    /**
     * 申请扣款指令
     */
    @Override
    @Transactional
    public DodopalResponse<CrdSysOrderDTO> applyDeductInstruction(CrdSysOrderDTO crdDTO){
        DodopalResponse<CrdSysOrderDTO> response = new DodopalResponse<CrdSysOrderDTO>();
        if(log.isInfoEnabled()){
            log.info("申请扣款指令,第一步,验证订单的合法性,产品库订单:"+crdDTO.getPrdordernum());
        }
        //1.验证订单的合法性
        DodopalResponse<ConsumeOrder> validOrderResponse = icdcPurchaseOrderService.validateIcdcPurchaseOrderWhenDeductCard(crdDTO.getPrdordernum());
        if (!ResponseCode.SUCCESS.equals(validOrderResponse.getCode())) {
            throw new DDPException(validOrderResponse.getCode(), "申请扣款指令,第一步,验证产品库订单失败:" + validOrderResponse.getMessage());
        }
        
        crdDTO.setCitycode(validOrderResponse.getResponseEntity().getCityCode());
        
        
        // ******************  产品库接收到DLL请求时，获取重发标志位（0：第一次；1：重发） add by shenXiang 2015-11-10  START    ********************//
        // 重发业务判断标志位（默认为第一次请求）
        boolean requestAgain = false;
        if ("0".equals(validOrderResponse.getResponseEntity().getResendSign())) {
            
            // 判断产品库接到DLL第一次请求，更新订单重发标志位：置为1
            DodopalResponse<Integer> updateResponse= icdcPurchaseOrderService.updateResendSignByOrderNum(crdDTO.getPrdordernum());
            if (!ResponseCode.SUCCESS.equals(updateResponse.getCode()) || updateResponse.getResponseEntity() == 0) {
                throw new DDPException(ResponseCode.PRODUCT_PURCHASE_ORDER_ERROR, "非法订单:订单不存在。订单编号:" + crdDTO.getPrdordernum());
            }
        } else if ("1".equals(validOrderResponse.getResponseEntity().getResendSign())){
            
            // 重发标志位为1，则认为本次请求为DLL重发请求
            log.info("申请扣款指令,dll第一次请求产品库超时,产品库重发标志位为1,判断本次请求为DLL重发请求.产品库透传卡服务结果,不修改订单状态。订单编号:" + crdDTO.getPrdordernum());
            requestAgain = true;
        }
        // ******************  产品库接收到DLL请求时，获取重发标志位（0：第一次；1：重发）add by shenXiang 2015-11-10   END    ********************//
        
        
        if(log.isInfoEnabled()){
            log.info("申请扣款指令,第二步,调用卡服务,申请扣款指令,产品库订单="+crdDTO.getPrdordernum());
        }
        
        //2.调用卡服务提供的申请扣款指令
        DodopalResponse<CrdSysOrderDTO> cardResponse = null;
        try{
            cardResponse = icdcPurchaseDelegate.getDeductOrderConsFun(crdDTO);
        } catch (HessianRuntimeException e) {
            throw new DDPException(ResponseCode.PRODUCT_CALL_CARD_ERROR, "申请扣款指令,第二步,调用卡服务,申请扣款指令失败:Hessian异常");
        }
        
        if(log.isInfoEnabled()){
            log.info("申请扣款指令,第二步,调用卡服务,申请扣款指令,卡服务返回响应码:"+cardResponse.getCode()+",响应消息:"+ cardResponse.getMessage()+",tradeendflag:"+cardResponse.getResponseEntity().getTradeendflag()+",产品库订单:"+crdDTO.getPrdordernum());
        }
        
        
        // 处理dll第一次请求
        if (!requestAgain) {
            if("0".equals(cardResponse.getResponseEntity().getTradeendflag())){//如果tradeendflag=0，则直接将结果返回给调用方
                if(log.isInfoEnabled()){
                    log.info("申请扣款指令,第三步,调用卡服务,申请扣款指令,卡服务返回响应码:"+cardResponse.getCode()+",响应消息:"+ cardResponse.getMessage()+",产品库订单:"+crdDTO.getPrdordernum()+",直接返回给调用方.");
                }
            }else if("1".equals(cardResponse.getResponseEntity().getTradeendflag())){//如果tradeendflag=1，调用产品库的方法，更新产品库订单状态
                DodopalResponse<Integer> updateResponse = icdcPurchaseOrderService.updateIcdcPurchaseOrderWhenDeductCard(crdDTO.getPrdordernum(), cardResponse.getCode());
                if(log.isInfoEnabled()){
                    log.info("申请扣款指令,第三步,调用卡服务,申请扣款指令,卡服务返回响应码:"+cardResponse.getCode()+",响应消息:"+ cardResponse.getMessage()+",产品库订单:"+crdDTO.getPrdordernum()+",调用产品库方法修改产品库订单状态,修改订单返回应答码为:"+updateResponse.getCode());
                }
                if (!ResponseCode.SUCCESS.equals(cardResponse.getCode()) && updateResponse.getResponseEntity() == 0) {
                    //申请扣款指令失败，产品库订单状态更新失败
                    response.setCode(cardResponse.getCode());
                    response.setMessage("申请扣款指令失败:响应码:"+cardResponse.getCode()+",响应消息:"+ cardResponse.getMessage()+";更新订单状态也失败:响应码:"+updateResponse.getCode() + "响应消息:" + updateResponse.getMessage() + ";产品库订单:" + crdDTO.getPrdordernum());
                    response.setResponseEntity(crdDTO);
                    return response;
                }else if (ResponseCode.SUCCESS.equals(cardResponse.getCode()) && updateResponse.getResponseEntity() == 0) {
                    //申请扣款指令成功，产品库订单状态更新失败
                    response.setCode(updateResponse.getCode());
                    response.setMessage("申请扣款指令成功,但更新订单状态失败:响应码:"+updateResponse.getCode() +"响应消息:"+ updateResponse.getMessage()+ ";产品库订单:" + crdDTO.getPrdordernum());
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
     * 上传收单结果
     */
    @Override
    @Transactional
    public DodopalResponse<CrdSysOrderDTO> uploadResult(CrdSysOrderDTO crdDTO) {
        DodopalResponse<CrdSysOrderDTO> response = new DodopalResponse<CrdSysOrderDTO>();
        String txnstat = crdDTO.getTxnstat();// 结果上传交易状态0：成功 1：失败 2：未知
        if(log.isInfoEnabled()){
            log.info("上传收单结果,第一步,验证订单的合法性,产品库订单="+crdDTO.getPrdordernum()+",txnstat="+txnstat);
        }
        //1.验证订单的合法性
        DodopalResponse<ConsumeOrder> validOrderResponse = icdcPurchaseOrderService.validateIcdcPurchaseOrderWhenUploadResult(crdDTO.getPrdordernum(), txnstat,crdDTO.getBlackamt());
        if(ResponseCode.PRODUCT_UPLOAD_PURCHASE_RESULT_ORDER_STATE_SUCCESS.equals(validOrderResponse.getCode())){
            //DLL重发请求,产品库响应成功
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(crdDTO);
            return response;
        }else if(ResponseCode.PRODUCT_UPLOAD_RECHARGE_RESULT_ORDER_STATE_DIFFERENT.equals(validOrderResponse.getCode())){
            //DLL重发请求,产品库响应可疑
            response.setCode(ResponseCode.PRODUCT_UPLOAD_RECHARGE_RESULT_ORDER_STATE_DIFFERENT);
            response.setResponseEntity(crdDTO);
            return response;
        }else if(!ResponseCode.SUCCESS.equals(validOrderResponse.getCode())) {
            throw new DDPException(validOrderResponse.getCode(), "上传收单结果,第一步,验证产品库订单失败:" + validOrderResponse.getMessage());
        }
        crdDTO.setCitycode(validOrderResponse.getResponseEntity().getCityCode());
        
        if(log.isInfoEnabled()){
            log.info("上传收单结果,第二步,调用卡服务,上传收单结果,产品库订单="+crdDTO.getPrdordernum()+",txnstat="+txnstat);
        }
        //2.调用卡服务提供的消费结果上传
        DodopalResponse<CrdSysOrderDTO> cardResponse = null;
        try{
            cardResponse = icdcPurchaseDelegate.uploadResultConsFun(crdDTO);
        } catch (HessianRuntimeException e) {
            throw new DDPException(ResponseCode.PRODUCT_CALL_CARD_ERROR, "上传收单结果,第二步,调用卡服务,上传收单结果失败:Hessian异常");
        }
        response.setCode(cardResponse.getCode());
        response.setResponseEntity(cardResponse.getResponseEntity());
        return response;
    }
}
