package com.dodopal.api.thirdly.facade;

import java.util.Map;

import com.dodopal.common.model.DodopalResponse;

public interface ThirdlyFacade {
    DodopalResponse<Map<String, String>> sayHello(String name);
}
