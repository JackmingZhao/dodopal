package com.dodopal.product.delegate.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.dodopal.api.card.dto.BJCrdSysOrderDTO;
import com.dodopal.api.card.facade.BJNfcRechargeFacade;
import com.dodopal.api.payment.dto.PayTranDTO;
import com.dodopal.api.payment.facade.PayFacade;
import com.dodopal.api.users.facade.MerchantFacade;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.delegate.BJnfcMobileRechargeDelegate;
import com.dodopal.product.delegate.BaseDelegate;
import com.dodopal.product.delegate.constant.DelegateConstant;

/**
 * 北京NFC手机端一卡通充值业务delegate接口实现类
 * @author dodopal
 *
 */
@Service("BJnfcMobileRechargeDelegate")
public class BJnfcMobileRechargeDelegateImpl extends BaseDelegate implements BJnfcMobileRechargeDelegate{

    /**
     * 用户终端信息登记
     */
    @Override
    public DodopalResponse<BJCrdSysOrderDTO> userTeminalRegister(BJCrdSysOrderDTO crdDTO) {
        BJNfcRechargeFacade facade = getFacade(BJNfcRechargeFacade.class,DelegateConstant.FACADE_CARD_URL);
        DodopalResponse<BJCrdSysOrderDTO> resultResponse = facade.userTeminalRegister(crdDTO);
        return resultResponse;
    }

    /**
     * 充值验卡接口
     */
    @Override
    public DodopalResponse<BJCrdSysOrderDTO> chargeValidateCard(BJCrdSysOrderDTO crdDTO) {
        BJNfcRechargeFacade facade = getFacade(BJNfcRechargeFacade.class,DelegateConstant.FACADE_CARD_URL);
        DodopalResponse<BJCrdSysOrderDTO> resultResponse = facade.chargeValidateCard(crdDTO);
        return resultResponse;
    }

    /**
     * 充值申请起始接口
     */
    @Override
    public DodopalResponse<BJCrdSysOrderDTO> chargeStart(BJCrdSysOrderDTO crdDTO) {
        BJNfcRechargeFacade facade = getFacade(BJNfcRechargeFacade.class,DelegateConstant.FACADE_CARD_URL);
        DodopalResponse<BJCrdSysOrderDTO> resultResponse = facade.chargeStart(crdDTO);
        return resultResponse;
    }

    /**
     * 充值申请后续接口
     */
    @Override
    public DodopalResponse<BJCrdSysOrderDTO> chargeFollow(BJCrdSysOrderDTO crdDTO) {
        BJNfcRechargeFacade facade = getFacade(BJNfcRechargeFacade.class,DelegateConstant.FACADE_CARD_URL);
        DodopalResponse<BJCrdSysOrderDTO> resultResponse = facade.chargeFollow(crdDTO);
        return resultResponse;
    }

    /**
     * 充值结果上传
     */
    @Override
    public DodopalResponse<BJCrdSysOrderDTO> chargeResultUp(BJCrdSysOrderDTO crdDTO) {
        BJNfcRechargeFacade facade = getFacade(BJNfcRechargeFacade.class,DelegateConstant.FACADE_CARD_URL);
        DodopalResponse<BJCrdSysOrderDTO> resultResponse = facade.chargeResultUp(crdDTO);
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
     * 用户系统：检验个人用户相关合法性接口
     */
    @Override
    public DodopalResponse<Map<String, String>> validatePersonalUserForNfcRecharge(String userId) {
        MerchantFacade facade = getFacade(MerchantFacade.class,DelegateConstant.FACADE_USERS_URL);
        DodopalResponse<Map<String, String>> resultResponse = facade.validatePersonalUserForNfcRecharge(userId);
        return resultResponse;
    }
    
}
