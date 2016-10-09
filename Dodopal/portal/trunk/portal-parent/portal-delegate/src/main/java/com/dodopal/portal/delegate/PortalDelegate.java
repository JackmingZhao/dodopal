package com.dodopal.portal.delegate;

import java.util.Map;

import com.dodopal.common.model.DodopalResponse;

public interface PortalDelegate {

    DodopalResponse<Map<String, String>> sayHello(String name);

}
