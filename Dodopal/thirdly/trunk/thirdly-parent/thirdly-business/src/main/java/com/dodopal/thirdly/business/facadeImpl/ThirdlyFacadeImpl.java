package com.dodopal.thirdly.business.facadeImpl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.dodopal.api.thirdly.facade.ThirdlyFacade;
import com.dodopal.common.constant.RequestKey;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalResponse;
@Service("thirdlyFacade")
public class ThirdlyFacadeImpl implements ThirdlyFacade {

    @Override
    public DodopalResponse<Map<String, String>> sayHello(String name) {
        DodopalResponse<Map<String, String>> response = new DodopalResponse<Map<String, String>>();
        Map<String, String> resMap = new HashMap<String, String>();
        resMap.put(RequestKey.HELLO, "Hello " + name + ", welcome to call card project.");
        response.setResponseEntity(resMap);
        response.setCode(ResponseCode.SUCCESS);
        return response;
    }

}
