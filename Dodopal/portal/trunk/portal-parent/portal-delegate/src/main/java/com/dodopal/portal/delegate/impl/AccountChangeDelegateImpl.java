package com.dodopal.portal.delegate.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dodopal.api.account.dto.FundChangeDTO;
import com.dodopal.api.account.dto.query.FundChangeQueryDTO;
import com.dodopal.api.account.facade.AccountQueryFacade;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.delegate.AccountChangeDelegate;
import com.dodopal.portal.delegate.BaseDelegate;
import com.dodopal.portal.delegate.constant.DelegateConstant;

@Service("accountChangeDelegate")
public class AccountChangeDelegateImpl extends BaseDelegate implements AccountChangeDelegate{

	/**
	 * 资金变更记录查询（分页）
	 */
	public DodopalResponse<DodopalDataPage<FundChangeDTO>> findFundsChangeRecordsByPage(FundChangeQueryDTO queryDTO) {
		AccountQueryFacade facade = getFacade(AccountQueryFacade.class, DelegateConstant.FACADE_ACCOUNT_URL);
		DodopalResponse<DodopalDataPage<FundChangeDTO>> response = facade.findFundsChangeRecordsByPage(queryDTO);
		return response;
	}

	/**
	 * 资金变更记录查询
	 */
	public DodopalResponse<List<FundChangeDTO>> findFundsChangeRecordsAll(FundChangeQueryDTO fundChangeQueryDTO) {
		AccountQueryFacade facade = getFacade(AccountQueryFacade.class, DelegateConstant.FACADE_ACCOUNT_URL);
		DodopalResponse<List<FundChangeDTO>> response = facade.findFundsChangeRecordsAll(fundChangeQueryDTO);
		return response;
	}
	
	

}
