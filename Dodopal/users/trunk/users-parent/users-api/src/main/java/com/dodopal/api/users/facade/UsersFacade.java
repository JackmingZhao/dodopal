package com.dodopal.api.users.facade;

import java.util.Map;

import com.dodopal.common.model.DodopalResponse;

public interface UsersFacade {

    DodopalResponse<Map<String, String>> sayHello(String name);

}