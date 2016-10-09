package com.dodopal.api.card.facade;

import java.util.Map;

import com.dodopal.common.model.DodopalResponse;

public interface CardFacade {
    DodopalResponse<Map<String, String>> sayHello(String name);
}
