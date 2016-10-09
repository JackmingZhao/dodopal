package com.dodopal.logservice.delegate;

import java.util.Map;

import com.dodopal.common.model.DodopalResponse;

public interface LogServiceDelegate {

    DodopalResponse<Map<String, String>> sayHello(String name);

}
