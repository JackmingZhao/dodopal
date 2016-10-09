package com.dodopal.portal.business.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.dodopal.api.payment.dto.PayTraTransactionDTO;
import com.dodopal.api.payment.dto.query.PayTraTransactionQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.PayTraTransaction;
import com.dodopal.portal.business.model.query.PayTraTransactionQuery;

public interface ChildMerChantRecordService {

	/**
	 * 查询子商户交易记录(分页)
	 * @param query
	 * @return DodopalResponse<DodopalDataPage<PayTraTransaction>>
	 */
	public DodopalResponse<DodopalDataPage<PayTraTransaction>> findTraRecordByPage(PayTraTransactionQuery query);
	
	/**
	 * 查询子商户交易记录
	 * @param merParentCode
	 * @param queryDTO
	 * @return  DodopalResponse<List<PayTraTransaction>>
	 */
	public DodopalResponse<String> excelExport(HttpServletResponse response,PayTraTransactionQueryDTO queryDTO);
}
