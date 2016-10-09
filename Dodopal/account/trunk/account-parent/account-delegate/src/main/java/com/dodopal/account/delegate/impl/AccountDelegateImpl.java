package com.dodopal.account.delegate.impl;

import com.dodopal.account.delegate.AccountDelegate;
import com.dodopal.api.account.facade.AccountManagementFacade;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.hessian.RemotingCallUtil;
import org.springframework.stereotype.Service;

@Service("accountDelegate")
public class AccountDelegateImpl implements AccountDelegate {
    AccountManagementFacade facade = RemotingCallUtil.getHessianService("http://localhost:8087/account-web/hessian/index.do?id=", AccountManagementFacade.class);
    @Override
    public DodopalResponse createAccount(String aType,String custNum,String accType,String merType,String operateId) {
        return facade.createAccount(aType,custNum,accType,merType,operateId);
    }

    @Override
    public DodopalResponse accountRecharge(String custType, String custNum, String tradeNum, long amount,String operateId) {
        return facade.accountRecharge(custType,custNum,tradeNum,amount,operateId);
    }

    @Override
    public DodopalResponse accountFreeze(String custType, String custNum, String tradeNum, long amount,String operateId) {
        return facade.accountFreeze(custType, custNum, tradeNum, amount,operateId);
    }

    @Override
    public DodopalResponse accountUnfreeze(String custType, String custNum, String tradeNum, long amount,String operateId) {
        return facade.accountUnfreeze(custType,custNum,tradeNum,amount,operateId);
    }

    @Override
    public DodopalResponse accountDeduct(String custType, String custNum, String tradeNum, long amount,String operateId) {
        return facade.accountDeduct(custType, custNum, tradeNum, amount,operateId);
    }

}
