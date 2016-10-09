package com.dodopal.product.delegate.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.dodopal.api.product.facade.ProductFacade;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.hessian.RemotingCallUtil;
import com.dodopal.product.delegate.ProductDelegate;

@Service("productDelegate")
public class ProductDelegateImpl implements ProductDelegate {

    @Override
    public DodopalResponse<Map<String, String>> sayHello(String name) {
        ProductFacade facade = RemotingCallUtil.getHessianService("http://localhost:8084/product-web/hessian/index.do?id=", ProductFacade.class);
        return facade.sayHello(name);
    }

}
