package com.dodopal.product.delegate.impl;

import java.util.List;

import com.dodopal.api.payment.dto.PayTranDTO;

import org.springframework.stereotype.Service;

import com.dodopal.api.payment.dto.PayWayDTO;
import com.dodopal.api.payment.facade.PayFacade;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.delegate.BaseDelegate;
import com.dodopal.product.delegate.PayDelegate;
import com.dodopal.product.delegate.constant.DelegateConstant;
@Service("payDelegate")
public class PayDelegateImpl extends BaseDelegate implements PayDelegate{

	@Override
	public DodopalResponse<List<PayWayDTO>> findPayWay(boolean ext,String ...merCode) {
		PayFacade facade = getFacade(PayFacade.class, DelegateConstant.FACADE_PAYMENT_URL);
	    DodopalResponse<List<PayWayDTO>> response = facade.findPayWay(ext, merCode);
	    return response;
	}

	@Override
	public DodopalResponse<List<PayWayDTO>> findCommonPayWay(boolean ext,String userCode) {
		PayFacade facade = getFacade(PayFacade.class, DelegateConstant.FACADE_PAYMENT_URL);
	    DodopalResponse<List<PayWayDTO>> response = facade.findCommonPayWay(ext,userCode);
		return response;
	}
	/**
	 * @description 手机支付账户充值功能
	 * @param payTranDTO
	 * @return
	 */
	@Override
	public DodopalResponse<String> mobilePay(PayTranDTO payTranDTO) {
		PayFacade facade = getFacade(PayFacade.class, DelegateConstant.FACADE_PAYMENT_URL);
		return facade.mobilepay(payTranDTO);
	}

	
    @Override
    public DodopalResponse<Boolean> loadOrderDeductAccountAmt(PayTranDTO payTranDTO) {
        PayFacade facade = getFacade(PayFacade.class, DelegateConstant.FACADE_PAYMENT_URL);
        return facade.loadOrderDeductAccountAmt(payTranDTO);
    }
}
