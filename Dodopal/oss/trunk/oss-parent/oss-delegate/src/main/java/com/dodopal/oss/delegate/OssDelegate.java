package com.dodopal.oss.delegate;

import java.util.Map;

import com.dodopal.common.model.DodopalResponse;

public interface OssDelegate {

    DodopalResponse<Map<String, String>> sayHello(String name);
    
    DodopalResponse<Map<String, String>> getUsersHello(String name);

}
