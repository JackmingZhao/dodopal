package com.dodopal.portal.delegate.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dodopal.api.account.dto.AccountFundDTO;
import com.dodopal.api.account.facade.AccountQueryFacade;
import com.dodopal.api.payment.dto.PayTraTransactionDTO;
import com.dodopal.api.payment.dto.PayWayDTO;
import com.dodopal.api.payment.facade.PayFacade;
import com.dodopal.api.payment.facade.PayTraTransactionFacade;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.delegate.BaseDelegate;
import com.dodopal.portal.delegate.DdpDelegate;
import com.dodopal.portal.delegate.constant.DelegateConstant;

@Service("ddpDelegate")
public class DdpDelegateImpl extends BaseDelegate implements DdpDelegate {

    
    /**
     * 更多支付方式
     */
    public DodopalResponse<List<PayWayDTO>> findPayWay(boolean ext, String merCode) {
        PayFacade facade= getFacade(PayFacade.class, DelegateConstant.FACADE_PAYMENT_URL);
        return facade.findPayWay(ext, merCode);
    }

    
    /**
     * 用户常用支付方式
     */
    public DodopalResponse<List<PayWayDTO>> findCommonPayWay(boolean ext, String userCode) {
        PayFacade facade= getFacade(PayFacade.class, DelegateConstant.FACADE_PAYMENT_URL);
        return facade.findCommonPayWay(ext, userCode);
    }
    
    /**
     * 
     *账户首页查询可用余额 和资金授信账户信息
     */
    public DodopalResponse<AccountFundDTO> findAccountBalance(String aType,String custNum){
        AccountQueryFacade facade = getFacade(AccountQueryFacade.class, DelegateConstant.FACADE_ACCOUNT_URL);
        return facade.findAccountBalance(aType, custNum);
    }

    /**
     * 
     *账户首页查询最新的十条交易记录
     */
    public DodopalResponse<List<PayTraTransactionDTO>> findTraTransactionByCode(String ext, String merOrUserCode) {
        PayTraTransactionFacade facade = getFacade(PayTraTransactionFacade.class, DelegateConstant.FACADE_PAYMENT_URL);
        return facade.findPayTraTransactionByCode(ext, merOrUserCode);
    }

}
