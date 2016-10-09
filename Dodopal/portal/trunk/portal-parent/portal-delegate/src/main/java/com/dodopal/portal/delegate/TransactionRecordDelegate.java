package com.dodopal.portal.delegate;

import java.util.List;

import com.dodopal.api.payment.dto.PayTraTransactionDTO;
import com.dodopal.api.payment.dto.query.PayTraTransactionQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

public interface TransactionRecordDelegate {

	/**
	 * 查询商户历史记录
	 * @param queryDTO
	 * @return DodopalResponse<DodopalDataPage<PayTraTransactionDTO>>
	 */
	DodopalResponse<DodopalDataPage<PayTraTransactionDTO>> findPayTraTransactionByPage(PayTraTransactionQueryDTO queryDTO);
	
	/**
	 * 查询所有交易记录
	 * @return
	 */
	DodopalResponse<List<PayTraTransactionDTO>> findPayTraTransactionAll(PayTraTransactionQueryDTO queryDTO);
	
	/**
	 * 根据tranCode查询交易记录信息
	 * @param tranCode
	 * @return  DodopalResponse<PayTraTransaction>
	 */
	DodopalResponse<PayTraTransactionDTO> findTranInfoByTranCode(String tranCode);
}
