package com.dodopal.transfer.delegate;

import java.util.Map;

import com.dodopal.common.model.DodopalResponse;

public interface TransferDelegate {
    DodopalResponse<Map<String, String>> sayHello(String name);
}
