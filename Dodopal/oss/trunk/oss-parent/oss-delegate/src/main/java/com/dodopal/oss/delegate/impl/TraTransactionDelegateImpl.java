package com.dodopal.oss.delegate.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dodopal.api.payment.dto.PayTraTransactionDTO;
import com.dodopal.api.payment.dto.query.PayTraTransactionQueryDTO;
import com.dodopal.api.payment.facade.PayTraTransactionFacade;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.delegate.BaseDelegate;
import com.dodopal.oss.delegate.TraTransactionDelegate;
import com.dodopal.oss.delegate.constant.DelegateConstant;


@Service("traTransactionDelegate")
public class TraTransactionDelegateImpl extends BaseDelegate implements TraTransactionDelegate {

    public DodopalResponse<DodopalDataPage<PayTraTransactionDTO>> findPayTraTransactionByPage(PayTraTransactionQueryDTO traDTO) {
        PayTraTransactionFacade facade = getFacade(PayTraTransactionFacade.class, DelegateConstant.FACADE_PAYMENT_URL);
        return  facade.findPayTraTransactionByPage(traDTO);
    }

    @Override
    public DodopalResponse<PayTraTransactionDTO> findTraTransaction(String id) {
        PayTraTransactionFacade facade = getFacade(PayTraTransactionFacade.class, DelegateConstant.FACADE_PAYMENT_URL);
        // TODO Auto-generated method stub
        return facade.findPayTraTransactionInfoById(id);
    }

    @Override
    public DodopalResponse<List<PayTraTransactionDTO>> findTrasactionExport(PayTraTransactionQueryDTO queryDTO) {
        PayTraTransactionFacade facade = getFacade(PayTraTransactionFacade.class, DelegateConstant.FACADE_PAYMENT_URL);
        return facade.findTrasactionExport(queryDTO);
    }

    @Override
    public DodopalResponse<DodopalDataPage<PayTraTransactionDTO>> findMerCreditsByPage(PayTraTransactionQueryDTO traDTO) {
        PayTraTransactionFacade facade = getFacade(PayTraTransactionFacade.class, DelegateConstant.FACADE_PAYMENT_URL);
        return  facade.findMerCreditsByPage(traDTO);
    }

    @Override
    public DodopalResponse<List<PayTraTransactionDTO>> findMerCreditsExport(PayTraTransactionQueryDTO queryDTO) {
        PayTraTransactionFacade facade = getFacade(PayTraTransactionFacade.class, DelegateConstant.FACADE_PAYMENT_URL);
        return  facade.findMerCreditsExport(queryDTO);
    }

}
