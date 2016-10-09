package com.dodopal.merdevice.delegate;

import java.util.Map;

import com.dodopal.common.model.DodopalResponse;

public interface MerdeviceDelegate {

    DodopalResponse<Map<String, String>> sayHello(String name);

}
