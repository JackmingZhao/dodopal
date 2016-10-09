package com.dodopal.product.delegate;

import java.util.Map;

import com.dodopal.common.model.DodopalResponse;

public interface ProductDelegate {

    DodopalResponse<Map<String, String>> sayHello(String name);

}
