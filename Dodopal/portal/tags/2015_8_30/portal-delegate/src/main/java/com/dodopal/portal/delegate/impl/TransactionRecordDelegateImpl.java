package com.dodopal.portal.delegate.impl;

import org.springframework.stereotype.Service;

import com.dodopal.api.payment.dto.PayTraTransactionDTO;
import com.dodopal.api.payment.dto.query.PayTraTransactionQueryDTO;
import com.dodopal.api.payment.facade.PayTraTransactionFacade;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.delegate.TransactionRecordDelegate;
import com.dodopal.portal.delegate.BaseDelegate;
import com.dodopal.portal.delegate.constant.DelegateConstant;

@Service("transactionRecordDelegate")
public class TransactionRecordDelegateImpl extends BaseDelegate implements TransactionRecordDelegate {

	//查询
	public DodopalResponse<DodopalDataPage<PayTraTransactionDTO>> findPayTraTransactionByPage(
			PayTraTransactionQueryDTO queryDTO) {
		PayTraTransactionFacade facade = getFacade(PayTraTransactionFacade.class, DelegateConstant.FACADE_PAYMENT_URL);
		DodopalResponse<DodopalDataPage<PayTraTransactionDTO>> response = facade.findPayTraTransactionByPage(queryDTO); 
		return response;
	}

	
	//产看详情
	public DodopalResponse<PayTraTransactionDTO> findTranInfoByTranCode(String tranCode){
		PayTraTransactionFacade facade = getFacade(PayTraTransactionFacade.class, DelegateConstant.FACADE_PAYMENT_URL);
		DodopalResponse<PayTraTransactionDTO> response = facade.findTranInfoByTranCode(tranCode);
		return response;
	}
}
