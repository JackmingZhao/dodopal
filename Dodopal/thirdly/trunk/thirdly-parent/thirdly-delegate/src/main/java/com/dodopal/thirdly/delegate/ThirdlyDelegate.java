package com.dodopal.thirdly.delegate;

import java.util.Map;

import com.dodopal.common.model.DodopalResponse;

public interface ThirdlyDelegate {
    DodopalResponse<Map<String, String>> sayHello(String name);
}
