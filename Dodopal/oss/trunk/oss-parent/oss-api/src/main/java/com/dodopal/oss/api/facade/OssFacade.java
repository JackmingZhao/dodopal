package com.dodopal.oss.api.facade;

import java.util.Map;

import com.dodopal.common.model.DodopalResponse;

public interface OssFacade {

    DodopalResponse<Map<String, String>> sayHello(String name);

}