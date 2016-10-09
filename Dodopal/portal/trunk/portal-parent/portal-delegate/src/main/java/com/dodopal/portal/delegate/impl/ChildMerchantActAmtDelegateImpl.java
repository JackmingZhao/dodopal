package com.dodopal.portal.delegate.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dodopal.api.account.dto.AccountChildMerchantDTO;
import com.dodopal.api.account.dto.query.AccountChildMerchantQueryDTO;
import com.dodopal.api.account.facade.AccountChildMerchantFacade;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.delegate.BaseDelegate;
import com.dodopal.portal.delegate.ChildMerchantActAmtDelegate;
import com.dodopal.portal.delegate.constant.DelegateConstant;

@Service("childMerchantActAmtDelegate")
public class ChildMerchantActAmtDelegateImpl extends BaseDelegate implements ChildMerchantActAmtDelegate{

	//查询（分页）
	public DodopalResponse<DodopalDataPage<AccountChildMerchantDTO>> findAccountChildMerByPage(AccountChildMerchantQueryDTO queryDTO) {
		AccountChildMerchantFacade facade = getFacade(AccountChildMerchantFacade.class, DelegateConstant.FACADE_ACCOUNT_URL);
		DodopalResponse<DodopalDataPage<AccountChildMerchantDTO>> response = facade.findAccountChildMerByPage(queryDTO);
		return response;
	}

	//查询
	public DodopalResponse<List<AccountChildMerchantDTO>> findAccountChildMerByList(
			AccountChildMerchantQueryDTO query) {
		AccountChildMerchantFacade facade = getFacade(AccountChildMerchantFacade.class, DelegateConstant.FACADE_ACCOUNT_URL);
		DodopalResponse<List<AccountChildMerchantDTO>> response = facade.findAccountChildMerByList(query);
		return response;
	}

}
