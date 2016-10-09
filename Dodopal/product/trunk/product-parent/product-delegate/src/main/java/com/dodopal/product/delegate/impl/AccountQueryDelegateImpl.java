package com.dodopal.product.delegate.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dodopal.api.account.dto.AccountBeanDTO;
import com.dodopal.api.account.dto.AccountChangeDTO;
import com.dodopal.api.account.dto.AccountFundDTO;
import com.dodopal.api.account.dto.query.AccountChangeRequestDTO;
import com.dodopal.api.account.facade.AccountQueryFacade;
import com.dodopal.api.users.facade.MerchantUserFacade;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.delegate.AccountQueryDelegate;
import com.dodopal.product.delegate.BaseDelegate;
import com.dodopal.product.delegate.constant.DelegateConstant;
@Service("accountQueryDelegate")
public class AccountQueryDelegateImpl extends BaseDelegate implements AccountQueryDelegate{

	@Override
	public DodopalResponse<AccountFundDTO> findAccountBalance(String aType,String custNum) {
		AccountQueryFacade accountQueryFacade = getFacade(AccountQueryFacade.class,  DelegateConstant.FACADE_ACCOUNT_URL);
		return accountQueryFacade.findAccountBalance(aType, custNum);
	}

    @Override
    public DodopalResponse<List<AccountChangeDTO>> queryAccountChange(AccountChangeRequestDTO requestDto) {
        AccountQueryFacade accountQueryFacade = getFacade(AccountQueryFacade.class,  DelegateConstant.FACADE_ACCOUNT_URL);
        return accountQueryFacade.queryAccountChangeByPhone(requestDto);
    }

    @Override
    public DodopalResponse<AccountBeanDTO> queryAccountBalance(String custtype, String custcode) {
        AccountQueryFacade accountQueryFacade = getFacade(AccountQueryFacade.class,  DelegateConstant.FACADE_ACCOUNT_URL);
        return accountQueryFacade.queryAccountBalance(custtype, custcode);
    }
}
