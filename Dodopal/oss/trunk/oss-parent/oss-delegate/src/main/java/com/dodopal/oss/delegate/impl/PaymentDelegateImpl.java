package com.dodopal.oss.delegate.impl;

import org.springframework.stereotype.Service;

import com.dodopal.api.payment.dto.PaymentDTO;
import com.dodopal.api.payment.dto.query.PaymentQueryDTO;
import com.dodopal.api.payment.facade.PayFacade;
import com.dodopal.api.payment.facade.PaymentFacade;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.delegate.BaseDelegate;
import com.dodopal.oss.delegate.PaymentDelegate;
import com.dodopal.oss.delegate.constant.DelegateConstant;

/**
 * 
 * @author hxc
 *
 */
@Service("paymentDelegate")
public class PaymentDelegateImpl extends BaseDelegate implements PaymentDelegate{

    public DodopalResponse<DodopalDataPage<PaymentDTO>> findPaymentListByPage(PaymentQueryDTO queryDTO) {
        PaymentFacade facade = getFacade(PaymentFacade.class, DelegateConstant.FACADE_PAYMENT_URL);
        DodopalResponse<DodopalDataPage<PaymentDTO>> response = facade.findPayMentByPage(queryDTO);
        return response;
    }

    @Override
    public DodopalResponse<PaymentDTO> findPayment(String id) {
        PaymentFacade facade = getFacade(PaymentFacade.class, DelegateConstant.FACADE_PAYMENT_URL);
        DodopalResponse<PaymentDTO> response = facade.findPaymentInfoById(id);
        return response;
    }

    @Override
    public DodopalResponse<Boolean> autoTransfer(String merCode) {
        PayFacade facade = getFacade(PayFacade.class, DelegateConstant.FACADE_PAYMENT_URL);
        DodopalResponse<Boolean> response = facade.autoTransfer(merCode);
        return response;
    }

}
