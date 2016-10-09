package com.dodopal.api.merdevice.facade;

import java.util.Map;

import com.dodopal.common.model.DodopalResponse;

public interface MerdeviceFacade {

    DodopalResponse<Map<String, String>> sayHello(String name);

}