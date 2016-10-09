package com.dodopal.portal.delegate;

import java.util.List;

import com.dodopal.api.payment.dto.PayTraTransactionDTO;
import com.dodopal.api.payment.dto.query.PayTraTransactionQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

public interface ChildMerChantRecordDelegate {

	/**
	 * 查询子商户
	 * @return
	 */
	DodopalResponse<DodopalDataPage<PayTraTransactionDTO>> findTraRecordByPage(PayTraTransactionQueryDTO queryDTO);
	
	/**
	 * 查询子商户交易记录
	 * @param merParentCode
	 * @return  DodopalResponse<List<PayTraTransactionDTO>>
	 */
	DodopalResponse<List<PayTraTransactionDTO>> findTraRecordByMerParentCode(PayTraTransactionQueryDTO queryDTO);
}
