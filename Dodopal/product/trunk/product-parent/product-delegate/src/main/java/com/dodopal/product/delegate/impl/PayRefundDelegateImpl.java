package com.dodopal.product.delegate.impl;


import org.springframework.stereotype.Service;
import com.dodopal.api.payment.facade.PayRetundFacade;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.delegate.BaseDelegate;
import com.dodopal.product.delegate.PayRefundDelegate;
import com.dodopal.product.delegate.constant.DelegateConstant;

/**
 * @author lifeng@dodopal.com
 */
@Service("payRetundFacade")
public class PayRefundDelegateImpl extends BaseDelegate implements PayRefundDelegate {

	
	@Override
	public DodopalResponse<String> sendRefund(String tranCode, String source,String userid){
			PayRetundFacade facade = getFacade(PayRetundFacade.class, DelegateConstant.FACADE_PAYMENT_URL);
    return facade.sendRefund(tranCode, source, userid);
	}



}
