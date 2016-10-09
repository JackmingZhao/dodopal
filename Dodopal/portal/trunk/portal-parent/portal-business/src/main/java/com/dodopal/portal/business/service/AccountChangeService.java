package com.dodopal.portal.business.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.dodopal.api.account.dto.query.FundChangeQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.AccountChange;
import com.dodopal.portal.business.model.query.FundChangeQuery;

public interface AccountChangeService {

	/**
	 * 资金变更记录
	 * @param query
	 * @return DodopalResponse<DodopalDataPage<AccountChange>>
	 */
	public DodopalResponse<DodopalDataPage<AccountChange>> findFundsChangeRecordsByPage(FundChangeQuery query);
	
	/**
	 * 资金变更记录
	 * @return DodopalResponse<List<AccountChange>>
	 */
	public DodopalResponse<String> excelExport(HttpServletResponse response,FundChangeQueryDTO query);
}
