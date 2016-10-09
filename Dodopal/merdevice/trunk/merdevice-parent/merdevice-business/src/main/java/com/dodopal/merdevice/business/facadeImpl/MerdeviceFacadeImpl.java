package com.dodopal.merdevice.business.facadeImpl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.dodopal.api.merdevice.facade.MerdeviceFacade;
import com.dodopal.common.constant.RequestKey;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalResponse;

@Service("merdeviceFacade")
public class MerdeviceFacadeImpl implements MerdeviceFacade {
    @Override
    public DodopalResponse<Map<String, String>> sayHello(String name) {
        DodopalResponse<Map<String, String>> response = new DodopalResponse<Map<String, String>>();
        Map<String, String> resMap = new HashMap<String, String>();
        resMap.put(RequestKey.HELLO, "Hello " + name + ", welcome to call merdevice project.");
        response.setResponseEntity(resMap);
        response.setCode(ResponseCode.SUCCESS);
        return response;
    }

}
