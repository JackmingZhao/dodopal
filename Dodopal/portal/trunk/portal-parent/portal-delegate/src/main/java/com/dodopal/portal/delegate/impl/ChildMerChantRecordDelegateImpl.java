package com.dodopal.portal.delegate.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dodopal.api.payment.dto.PayTraTransactionDTO;
import com.dodopal.api.payment.dto.query.PayTraTransactionQueryDTO;
import com.dodopal.api.payment.facade.PayTraTransactionFacade;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.delegate.BaseDelegate;
import com.dodopal.portal.delegate.ChildMerChantRecordDelegate;
import com.dodopal.portal.delegate.constant.DelegateConstant;

@Service("childMerChantRecordDelegate")
public class ChildMerChantRecordDelegateImpl extends BaseDelegate implements ChildMerChantRecordDelegate{

	/**
	 * 子商户交易记录(分页)
	 */
	public DodopalResponse<DodopalDataPage<PayTraTransactionDTO>> findTraRecordByPage(PayTraTransactionQueryDTO queryDTO) {
		PayTraTransactionFacade facade = getFacade(PayTraTransactionFacade.class, DelegateConstant.FACADE_PAYMENT_URL);
		DodopalResponse<DodopalDataPage<PayTraTransactionDTO>> response = facade.findTraRecordByPage(queryDTO);
		return response;
	}

	/**
	 * 子商户交易记录
	 */
	public DodopalResponse<List<PayTraTransactionDTO>> findTraRecordByMerParentCode(PayTraTransactionQueryDTO queryDTO) {
		PayTraTransactionFacade facade = getFacade(PayTraTransactionFacade.class, DelegateConstant.FACADE_PAYMENT_URL);
		DodopalResponse<List<PayTraTransactionDTO>> response = facade.findPayTraTransactionAll(queryDTO);
		return response;
	}

}
