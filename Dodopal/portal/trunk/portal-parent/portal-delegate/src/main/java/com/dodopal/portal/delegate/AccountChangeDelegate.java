package com.dodopal.portal.delegate;

import java.util.List;

import com.dodopal.api.account.dto.FundChangeDTO;
import com.dodopal.api.account.dto.query.FundChangeQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

public interface AccountChangeDelegate {

	/**
	 * 
	 * 查询资金变更记录(分页)
	 * @param queryDTO
	 * @return DodopalResponse<DodopalDataPage<FundChangeDTO>>
	 */
	public DodopalResponse<DodopalDataPage<FundChangeDTO>> findFundsChangeRecordsByPage(FundChangeQueryDTO queryDTO);
	
	/**
	 * 查询资金变更记录
	 * @return DodopalResponse<List<FundChangeDTO>>
	 */
	public DodopalResponse<List<FundChangeDTO>> findFundsChangeRecordsAll(FundChangeQueryDTO fundChangeQueryDTO);
}
