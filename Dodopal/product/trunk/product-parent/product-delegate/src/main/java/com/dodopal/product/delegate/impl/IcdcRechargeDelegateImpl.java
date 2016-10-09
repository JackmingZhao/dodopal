package com.dodopal.product.delegate.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.dodopal.api.card.dto.CrdSysOrderDTO;
import com.dodopal.api.card.facade.FrontFailReportCardFacade;
import com.dodopal.api.card.facade.IcdcRechargeCardFacade;
import com.dodopal.api.card.facade.QueryCheckCardFacade;
import com.dodopal.api.users.facade.MerchantFacade;
import com.dodopal.api.payment.dto.PayAwayDTO;
import com.dodopal.api.payment.dto.PayServiceRateDTO;
import com.dodopal.api.payment.dto.PayTranDTO;
import com.dodopal.api.payment.facade.PayFacade;
import com.dodopal.api.payment.facade.PayServiceRateFacade;
import com.dodopal.api.payment.facade.PayWayExternalFacade;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.delegate.BaseDelegate;
import com.dodopal.product.delegate.IcdcRechargeDelegate;
import com.dodopal.product.delegate.constant.DelegateConstant;

@Service("icdcRechargeDelegate")
public class IcdcRechargeDelegateImpl extends BaseDelegate implements IcdcRechargeDelegate{

    
    /**
     * 产品库透传参数给卡服务进行验卡查询接口
     */
    @Override
    public DodopalResponse<CrdSysOrderDTO> queryCheckCardFun(CrdSysOrderDTO crdDTO){
        QueryCheckCardFacade facade =getFacade(QueryCheckCardFacade.class,DelegateConstant.FACADE_CARD_URL);
        return facade.queryCheckCardFun(crdDTO);
    }
    
    /**
     * 检验商户合法性
     */
    @Override
    public DodopalResponse<Map<String, String>> validateMerchantForIcdcRecharge(String merchantNum, String userId, String posId, String providerCode, String source) {
        MerchantFacade facade = getFacade(MerchantFacade.class,DelegateConstant.FACADE_USERS_URL);
        return facade.validateMerchantForIcdcRecharge(merchantNum, userId, posId, providerCode, source);
    }

    /**
     * 获取通用支付方式：支付服务费和费率类型
     */
    @Override
    public DodopalResponse<PayServiceRateDTO> findPayServiceRate(String payWayId, String busType, long amount) {
        PayServiceRateFacade facade = getFacade(PayServiceRateFacade.class,DelegateConstant.FACADE_PAYMENT_URL);
        return facade.findPayServiceRate(payWayId, busType, amount);
    }
    
    /**
     * 获取外接支付方式：支付服务费和费率类型
     */
    @Override
    public DodopalResponse<PayAwayDTO> findPayExternalById(String payWayId) {
        PayWayExternalFacade facade = getFacade(PayWayExternalFacade.class,DelegateConstant.FACADE_PAYMENT_URL);
        return facade.findPayExternalByPayId(payWayId);
    }
    
    /**
     * 来源：移动端，生单接口调用支付交易生成交易流水
     */
    @Override
    public DodopalResponse<String> mobilepay(PayTranDTO payTranDTO) {
        PayFacade facade = getFacade(PayFacade.class,DelegateConstant.FACADE_PAYMENT_URL);
        return facade.mobilepay(payTranDTO);
    }

    /**
     * 调用卡服务订单创建
     */
    @Override
    public DodopalResponse<CrdSysOrderDTO> createCrdOrderCardFun(CrdSysOrderDTO crdDTO) {
        QueryCheckCardFacade facade =getFacade(QueryCheckCardFacade.class,DelegateConstant.FACADE_CARD_URL);
        return facade.createCrdOrderCardFun(crdDTO);
    }
    
    /**
     * 产品库调用卡服务提供的充值申请接口
     */
    @Override
    public DodopalResponse<CrdSysOrderDTO> getLoadOrderFun(CrdSysOrderDTO crdDTO){
        IcdcRechargeCardFacade facade =getFacade(IcdcRechargeCardFacade.class,DelegateConstant.FACADE_CARD_URL);
        return facade.getLoadOrderFun(crdDTO);
    }
    
    
    /**
     * 产品库调用支付交易提供的资金解冻接口
     */
    @Override
    public DodopalResponse<Boolean> unfreezeAccountAmt(PayTranDTO transactionDTO){
        PayFacade facade = getFacade(PayFacade.class,DelegateConstant.FACADE_PAYMENT_URL);
        return facade.unfreezeAccountAmt(transactionDTO);
    }
    
    
    /**
     * 产品库调用卡服务提供的充值校验更新接口
     */
    @Override
    public DodopalResponse<CrdSysOrderDTO> uplodResultChkUdpOrderFun(CrdSysOrderDTO crdDTO){
        IcdcRechargeCardFacade facade =getFacade(IcdcRechargeCardFacade.class,DelegateConstant.FACADE_CARD_URL);
        return facade.uploadResultChkUdpOrderFun(crdDTO);
    }
    
    /**
     * 产品库调用卡服务提供的充值结果接口
     */
    @Override
    public DodopalResponse<CrdSysOrderDTO> uploadResultFun(CrdSysOrderDTO crdDTO){
        IcdcRechargeCardFacade facade =getFacade(IcdcRechargeCardFacade.class,DelegateConstant.FACADE_CARD_URL);
        return facade.uploadResultFun(crdDTO);
    }
    
    /**
     * 产品库调用支付交易提供的资金扣款接口
     */
    @Override
    public DodopalResponse<Boolean> deductAccountAmt(PayTranDTO transactionDTO){
        PayFacade facade = getFacade(PayFacade.class,DelegateConstant.FACADE_PAYMENT_URL);
        return facade.deductAccountAmt(transactionDTO);
    }
    
    /**
     * 产品库调用卡服务验证订单接口
     */
    @Override
    public DodopalResponse<CrdSysOrderDTO> frontFailReportFun(CrdSysOrderDTO crdDTO){
        FrontFailReportCardFacade facade =getFacade(FrontFailReportCardFacade.class,DelegateConstant.FACADE_CARD_URL);
        return facade.frontFailReportFun(crdDTO);
    }

    /**
     * 产品库调用支付交易提供的资金冻结接口
     */
    @Override
	public DodopalResponse<Boolean> freezeAccountAmt(PayTranDTO transactionDTO) {
		PayFacade facade = getFacade(PayFacade.class,DelegateConstant.FACADE_PAYMENT_URL);
        return facade.freezeAccountAmt(transactionDTO);
	}

    /**
     * 产品库调用卡服务提供的接口来获取APDU信息
     */
	@Override
	public DodopalResponse<CrdSysOrderDTO> getLoadInitFun(CrdSysOrderDTO crdDTO) {
		IcdcRechargeCardFacade facade = getFacade(IcdcRechargeCardFacade.class,DelegateConstant.FACADE_CARD_URL);
		return facade.getLoadInitFun(crdDTO);
	}

}
