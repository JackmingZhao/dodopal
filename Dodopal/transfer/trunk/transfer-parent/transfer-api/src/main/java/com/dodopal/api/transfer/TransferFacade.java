package com.dodopal.api.transfer;

import java.util.Map;

import com.dodopal.common.model.DodopalResponse;

public interface TransferFacade {
    DodopalResponse<Map<String, String>> sayHello(String name);
}
