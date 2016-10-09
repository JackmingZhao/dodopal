package com.dodopal.portal.api;

import java.util.Map;

import com.dodopal.common.model.DodopalResponse;

public interface PortalFacade {

    DodopalResponse<Map<String, String>> sayHello(String name);

}