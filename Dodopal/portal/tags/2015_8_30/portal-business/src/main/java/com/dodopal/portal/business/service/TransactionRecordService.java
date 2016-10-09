package com.dodopal.portal.business.service;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.PayTraTransaction;
import com.dodopal.portal.business.bean.TraTransactionBean;
import com.dodopal.portal.business.model.query.PayTraTransactionQuery;

public interface TransactionRecordService {

	/**
	 * 查询商户交易记录
	 * @param queryDTO
	 * @return  DodopalResponse<DodopalDataPage<PayTraTransaction>>
	 */
	public DodopalResponse<DodopalDataPage<PayTraTransaction>> findPayTraTransactionByPage(
			PayTraTransactionQuery query);
	
	/**
	 * 根据tranCode查询交易记录信息
	 * @param tranCode
	 * @return  DodopalResponse<PayTraTransaction>
	 */
	public DodopalResponse<TraTransactionBean> findTranInfoByTranCode(String tranCode);
}
