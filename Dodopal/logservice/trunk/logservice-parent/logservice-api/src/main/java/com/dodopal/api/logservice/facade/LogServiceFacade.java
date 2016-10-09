package com.dodopal.api.logservice.facade;

import java.util.Map;

import com.dodopal.common.model.DodopalResponse;

public interface LogServiceFacade {

    DodopalResponse<Map<String, String>> sayHello(String name);

}