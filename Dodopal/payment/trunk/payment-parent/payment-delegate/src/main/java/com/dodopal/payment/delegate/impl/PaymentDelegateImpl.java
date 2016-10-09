package com.dodopal.payment.delegate.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.dodopal.api.account.dto.AccountFundDTO;
import com.dodopal.api.account.facade.AccountQueryFacade;
import com.dodopal.api.users.facade.MerchantFacade;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.payment.delegate.BaseDelegate;
import com.dodopal.payment.delegate.PaymentDelegate;
import com.dodopal.payment.delegate.constant.DelegateConstant;

@Service("paymentDelegate")
public class PaymentDelegateImpl extends BaseDelegate implements PaymentDelegate {

	@Override
	public DodopalResponse<Map<String, String>> checkMerInfo(String merCode) {
		MerchantFacade facade = getFacade(MerchantFacade.class, DelegateConstant.FACADE_USERS_URL);
        return facade.checkMerInfo(merCode);
	}

	@Override
	public DodopalResponse<AccountFundDTO> findAccountBalance(String aType, String custNum) {
		AccountQueryFacade accountQueryFacade =  getFacade(AccountQueryFacade.class, DelegateConstant.FACADE_ACCOUNT_URL);
		return accountQueryFacade.findAccountBalance(aType, custNum);
	}

	@Override
	public DodopalResponse<String> getIsAuto(String merCode) {
		MerchantFacade facade = getFacade(MerchantFacade.class, DelegateConstant.FACADE_USERS_URL);
		return facade.getIsAuto(merCode);
	}

	@Override
	public DodopalResponse<String> getParentId(String merCode) {
		MerchantFacade facade = getFacade(MerchantFacade.class, DelegateConstant.FACADE_USERS_URL);
		return facade.getParentid(merCode);
	}


//    @Override
//    public DodopalResponse<Map<String, String>> sayHello(String name) {
//        PaymentFacade facade = RemotingCallUtil.getHessianService("http://localhost:8085/payment-web/hessian/index.do?id=", PaymentFacade.class);
//        return facade.sayHello(name);
//    }

}
