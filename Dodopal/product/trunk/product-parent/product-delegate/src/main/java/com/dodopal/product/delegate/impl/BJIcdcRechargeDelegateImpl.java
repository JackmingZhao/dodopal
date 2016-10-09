package com.dodopal.product.delegate.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.dodopal.api.card.dto.BJCrdSysOrderDTO;
import com.dodopal.api.card.facade.BJIcdcRechargeCardFacade;
import com.dodopal.api.card.facade.BJIcdcRechargeCardV71Facade;
import com.dodopal.api.payment.dto.PayAwayDTO;
import com.dodopal.api.payment.dto.PayServiceRateDTO;
import com.dodopal.api.payment.dto.PayTranDTO;
import com.dodopal.api.payment.facade.PayFacade;
import com.dodopal.api.payment.facade.PayServiceRateFacade;
import com.dodopal.api.payment.facade.PayWayExternalFacade;
import com.dodopal.api.users.facade.MerchantFacade;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.delegate.BJIcdcRechargeDelegate;
import com.dodopal.product.delegate.BaseDelegate;
import com.dodopal.product.delegate.constant.DelegateConstant;

/**
 * 北京城市一卡通充值业务delegate接口实现类
 * @author dodopal
 *
 */
@Service("BJIcdcRechargeDelegate")
public class BJIcdcRechargeDelegateImpl extends BaseDelegate implements BJIcdcRechargeDelegate{

    /****************************************** 卡系统相关接口 start ********************************************/
    /**
     * 卡服务: 验卡查询接口（V61充值接口）
     * 
     */
    @Override
    public DodopalResponse<BJCrdSysOrderDTO> queryCheckCardByV61(BJCrdSysOrderDTO crdDTO) { 
        BJIcdcRechargeCardFacade v61Facade = getFacade(BJIcdcRechargeCardFacade.class,DelegateConstant.FACADE_CARD_URL);
        DodopalResponse<BJCrdSysOrderDTO> resultResponse = v61Facade.queryCheckCard(crdDTO);
        return resultResponse;
    }
    
    /**
     * 卡服务: 订单创建接口（V61充值接口）
     * 
     */
    @Override
    public DodopalResponse<BJCrdSysOrderDTO> createCrdOrderCardByV61(BJCrdSysOrderDTO crdDTO) {
        BJIcdcRechargeCardFacade v61Facade = getFacade(BJIcdcRechargeCardFacade.class,DelegateConstant.FACADE_CARD_URL);
        DodopalResponse<BJCrdSysOrderDTO> resultResponse = v61Facade.createCrdOrderCard(crdDTO);
        return resultResponse;
    }
    
    /**
     * 卡服务: 获取APDU接口（V61充值接口）
     * 
     */
    @Override
    public DodopalResponse<BJCrdSysOrderDTO> getApduByV61(BJCrdSysOrderDTO crdDTO) {
        BJIcdcRechargeCardFacade v61Facade = getFacade(BJIcdcRechargeCardFacade.class,DelegateConstant.FACADE_CARD_URL);
        DodopalResponse<BJCrdSysOrderDTO> resultResponse = v61Facade.getApdu(crdDTO);
        return resultResponse;
    }
    
    /**
     * 卡服务: 充值申请接口（V61充值接口）
     * 
     */
    @Override
    public DodopalResponse<BJCrdSysOrderDTO> getLoadOrderByV61(BJCrdSysOrderDTO crdDTO) {
        BJIcdcRechargeCardFacade v61Facade = getFacade(BJIcdcRechargeCardFacade.class,DelegateConstant.FACADE_CARD_URL);
        DodopalResponse<BJCrdSysOrderDTO> resultResponse = v61Facade.getLoadOrder(crdDTO);
        return resultResponse;
    }
   
    /**
     * 卡服务：结果上传校验更新订单接口（V61充值接口）
     * 
     */
    @Override
    public DodopalResponse<BJCrdSysOrderDTO> uploadResultChkUdpOrderByV61(BJCrdSysOrderDTO crdDTO) {
        BJIcdcRechargeCardFacade v61Facade = getFacade(BJIcdcRechargeCardFacade.class,DelegateConstant.FACADE_CARD_URL);
        DodopalResponse<BJCrdSysOrderDTO> resultResponse = v61Facade.uploadResultChkUdpOrder(crdDTO);
        return resultResponse;
    }

    /**
     * 卡服务：充值结果上传接口（V61充值接口）
     *  
     */
    @Override
    public DodopalResponse<BJCrdSysOrderDTO> uploadResultByV61(BJCrdSysOrderDTO crdDTO) {
        BJIcdcRechargeCardFacade v61Facade = getFacade(BJIcdcRechargeCardFacade.class,DelegateConstant.FACADE_CARD_URL);
        DodopalResponse<BJCrdSysOrderDTO> resultResponse = v61Facade.uploadResult(crdDTO);
        return resultResponse;
    }
    
    /**
     * 卡服务: 验卡查询接口（V71充值接口）
     * 
     */
    @Override
    public DodopalResponse<BJCrdSysOrderDTO> queryCheckCardByV71(BJCrdSysOrderDTO crdDTO) { 
        BJIcdcRechargeCardV71Facade v71Facade = getFacade(BJIcdcRechargeCardV71Facade.class,DelegateConstant.FACADE_CARD_URL);
        DodopalResponse<BJCrdSysOrderDTO> resultResponse = v71Facade.queryCheckCard(crdDTO);
        return resultResponse;
    }
    
    /**
     * 卡服务: 充值申请（V71充值接口）
     * 
     */
    @Override
    public DodopalResponse<BJCrdSysOrderDTO> getLoadOrderByV71(BJCrdSysOrderDTO crdDTO) {
        BJIcdcRechargeCardV71Facade v71Facade = getFacade(BJIcdcRechargeCardV71Facade.class,DelegateConstant.FACADE_CARD_URL);
        DodopalResponse<BJCrdSysOrderDTO> resultResponse = v71Facade.getLoadOrder(crdDTO);
        return resultResponse;
    }

    /**
     * 卡服务：结果上传校验更新订单接口（V71充值接口）
     * 
     */
    @Override
    public DodopalResponse<BJCrdSysOrderDTO> uploadResultChkUdpOrderByV71(BJCrdSysOrderDTO crdDTO) {
        BJIcdcRechargeCardV71Facade v71Facade = getFacade(BJIcdcRechargeCardV71Facade.class,DelegateConstant.FACADE_CARD_URL);
        DodopalResponse<BJCrdSysOrderDTO> resultResponse = v71Facade.uploadResultChkUdpOrder(crdDTO);
        return resultResponse;
    }
    
    /**
     * 卡服务:  充值结果上传（V71充值接口）
     * 
     */
    @Override
    public DodopalResponse<BJCrdSysOrderDTO> uploadResultByV71(BJCrdSysOrderDTO crdDTO) {
        BJIcdcRechargeCardV71Facade v71Facade = getFacade(BJIcdcRechargeCardV71Facade.class,DelegateConstant.FACADE_CARD_URL);
        DodopalResponse<BJCrdSysOrderDTO> resultResponse = v71Facade.uploadResult(crdDTO);
        return resultResponse;
    }

    /****************************************** 卡系统相关接口 end ********************************************/
    
    /****************************************** 用户系统相关接口 start ********************************************/
    
    /**
     * 检验商户合法性
     */
    @Override
    public DodopalResponse<Map<String, String>> validateMerchantForIcdcRecharge(String merchantNum, String userId, String posId, String providerCode, String source) {
        MerchantFacade facade = getFacade(MerchantFacade.class,DelegateConstant.FACADE_USERS_URL);
        DodopalResponse<Map<String, String>> resultResponse = facade.validateMerchantForIcdcRecharge(merchantNum, userId, posId, providerCode, source);
        return resultResponse;
    }

    /****************************************** 用户系统相关接口 end ********************************************/
    
    /****************************************** 支付交易系统相关接口 start ********************************************/
    /**
     * 获取通用支付方式：支付服务费和费率类型
     */
    @Override
    public DodopalResponse<PayServiceRateDTO> findPayServiceRate(String payWayId, String busType, long amount) {
        PayServiceRateFacade facade = getFacade(PayServiceRateFacade.class,DelegateConstant.FACADE_PAYMENT_URL);
        DodopalResponse<PayServiceRateDTO> resultResponse = facade.findPayServiceRate(payWayId, busType, amount);
        return resultResponse;
    }
    
    /**
     * 获取外接支付方式：支付服务费和费率类型
     */
    @Override
    public DodopalResponse<PayAwayDTO> findPayExternalById(String payWayId) {
        PayWayExternalFacade facade = getFacade(PayWayExternalFacade.class,DelegateConstant.FACADE_PAYMENT_URL);
        DodopalResponse<PayAwayDTO> resultResponse = facade.findPayExternalByPayId(payWayId);
        return resultResponse;
    }
    
    /**
     * 产品库调用支付交易提供的资金解冻接口
     */
    @Override
    public DodopalResponse<Boolean> unfreezeAccountAmt(PayTranDTO transactionDTO){
        PayFacade facade = getFacade(PayFacade.class,DelegateConstant.FACADE_PAYMENT_URL);
        DodopalResponse<Boolean> resultResponse = facade.unfreezeAccountAmt(transactionDTO);
        return resultResponse;
    }
    
    /**
     * 产品库调用支付交易提供的资金扣款接口
     */
    @Override
    public DodopalResponse<Boolean> deductAccountAmt(PayTranDTO transactionDTO){
        PayFacade facade = getFacade(PayFacade.class,DelegateConstant.FACADE_PAYMENT_URL);
        DodopalResponse<Boolean> resultResponse = facade.deductAccountAmt(transactionDTO);
        return resultResponse;
    }
    
    /**
     * 产品库调用支付交易提供的资金冻结接口
     */
    @Override
	public DodopalResponse<Boolean> freezeAccountAmt(PayTranDTO transactionDTO) {
		PayFacade facade = getFacade(PayFacade.class,DelegateConstant.FACADE_PAYMENT_URL);
		DodopalResponse<Boolean> resultResponse = facade.freezeAccountAmt(transactionDTO);
        return resultResponse;
	}

    /**
     * 来源：移动端，生单接口调用支付交易生成交易流水
     */
    @Override
    public DodopalResponse<String> mobilepay(PayTranDTO payTranDTO) {
        PayFacade facade = getFacade(PayFacade.class,DelegateConstant.FACADE_PAYMENT_URL);
        DodopalResponse<String> resultResponse = facade.mobilepay(payTranDTO);
        return resultResponse;
    }
    
    /****************************************** 支付交易系统相关接口 end ********************************************/

}
