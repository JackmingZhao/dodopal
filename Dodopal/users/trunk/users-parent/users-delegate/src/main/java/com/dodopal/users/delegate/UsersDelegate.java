package com.dodopal.users.delegate;

import java.util.Map;

import com.dodopal.common.model.DodopalResponse;

public interface UsersDelegate {

    DodopalResponse<Map<String, String>> sayHello(String name);

}
