package com.dodopal.product.delegate.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dodopal.api.payment.dto.PayWayDTO;
import com.dodopal.api.payment.facade.PayFacade;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.delegate.AccountPaywayDelegate;
import com.dodopal.product.delegate.BaseDelegate;
import com.dodopal.product.delegate.constant.DelegateConstant;

@Service("accountPaywayDelegate")
public class AccountPaywayDelegateImpl extends BaseDelegate implements AccountPaywayDelegate{

	@Override
	public DodopalResponse<List<PayWayDTO>> findCommonPayWay(boolean ext,String custcode) {
		PayFacade facade = getFacade(PayFacade.class, DelegateConstant.FACADE_PAYMENT_URL);
		return facade.findPayWay(ext, custcode);
	}

}
