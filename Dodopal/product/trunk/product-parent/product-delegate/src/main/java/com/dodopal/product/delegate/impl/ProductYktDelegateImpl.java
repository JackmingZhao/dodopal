package com.dodopal.product.delegate.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.dodopal.api.payment.facade.PayConfigFacade;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.delegate.BaseDelegate;
import com.dodopal.product.delegate.ProductYktDelegate;
import com.dodopal.product.delegate.constant.DelegateConstant;
 
@Service("ProductYktDelegate")
public class ProductYktDelegateImpl extends BaseDelegate implements ProductYktDelegate {

    @Override
    public DodopalResponse<Boolean> icdcPayCreate(List<Map<String, Object>> list) {
        PayConfigFacade facade =getFacade(PayConfigFacade.class,DelegateConstant.FACADE_PAYMENT_URL);
        return facade.icdcPayCreate(list);
    }


}
