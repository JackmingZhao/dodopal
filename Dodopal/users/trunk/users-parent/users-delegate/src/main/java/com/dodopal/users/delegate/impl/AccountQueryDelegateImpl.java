package com.dodopal.users.delegate.impl;

import org.springframework.stereotype.Service;

import com.dodopal.api.account.dto.AccountFundDTO;
import com.dodopal.api.account.facade.AccountQueryFacade;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.users.delegate.AccountQueryDelegate;
import com.dodopal.users.delegate.BaseDelegate;
import com.dodopal.users.delegate.constant.DelegateConstant;

/** 
 * @author lifeng@dodopal.com
 */
@Service("accountQueryDelegate")
public class AccountQueryDelegateImpl extends BaseDelegate implements AccountQueryDelegate {

	@Override
	public DodopalResponse<AccountFundDTO> findAccountBalance(String aType, String custNum) {
		AccountQueryFacade facade = getFacade(AccountQueryFacade.class, DelegateConstant.FACADE_ACCOUNT_URL);
		return facade.findAccountBalance(aType, custNum);
	}

}
