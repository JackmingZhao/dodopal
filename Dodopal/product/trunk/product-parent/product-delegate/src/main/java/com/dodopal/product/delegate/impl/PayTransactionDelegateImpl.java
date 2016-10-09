package com.dodopal.product.delegate.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dodopal.api.payment.dto.CreateTranDTO;
import com.dodopal.api.payment.dto.PayTraTransactionDTO;
import com.dodopal.api.payment.dto.TranscationListResultDTO;
import com.dodopal.api.payment.dto.query.PayTraTransactionQueryDTO;
import com.dodopal.api.payment.dto.query.TranscationRequestDTO;
import com.dodopal.api.payment.facade.PayRetundFacade;
import com.dodopal.api.payment.facade.PayTraTransactionFacade;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.delegate.BaseDelegate;
import com.dodopal.product.delegate.PayTransactionDelegate;
import com.dodopal.product.delegate.constant.DelegateConstant;
/**
 * 3.20 查询交易记录 （手机端和VC端接入）  
 * @author xiongzhijing@dodopal.com
 * @version 2015年11月11日
 */
@Service("payTransactionDelegate")
public class PayTransactionDelegateImpl extends BaseDelegate implements PayTransactionDelegate {

    @Override
    public DodopalResponse<List<TranscationListResultDTO>> queryPayTranscation(TranscationRequestDTO requestDto) {
        PayTraTransactionFacade facade = getFacade(PayTraTransactionFacade.class, DelegateConstant.FACADE_PAYMENT_URL);
        return facade.queryPayTranscation(requestDto);
    }

    @Override
    public DodopalResponse<PayTraTransactionDTO> findTranInfoByTranCode(String tranCode) {
        PayTraTransactionFacade facade = getFacade(PayTraTransactionFacade.class, DelegateConstant.FACADE_PAYMENT_URL);
        return facade.findTranInfoByTranCode(tranCode);
    }

    @Override
    public DodopalResponse<String> createTranscation(CreateTranDTO createTranDTO) {
        PayRetundFacade facade = getFacade(PayRetundFacade.class, DelegateConstant.FACADE_PAYMENT_URL);
        return facade.createPayTraTransaction(createTranDTO);
    }

    @Override
    public DodopalResponse<List<PayTraTransactionDTO>> findPayTraTransactionList(PayTraTransactionQueryDTO queryDTO) {
        PayTraTransactionFacade facade = getFacade(PayTraTransactionFacade.class, DelegateConstant.FACADE_PAYMENT_URL);
        return facade.findPayTraTransactionList(queryDTO);
    }
    
    
}
