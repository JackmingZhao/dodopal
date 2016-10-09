package com.dodopal.api.product.facade;

import java.util.Map;

import com.dodopal.common.model.DodopalResponse;

public interface ProductFacade {

    DodopalResponse<Map<String, String>> sayHello(String name);

}