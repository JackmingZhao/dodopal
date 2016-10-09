package com.dodopal.transfer.business.facadeImpl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.dodopal.api.transfer.TransferFacade;
import com.dodopal.common.constant.RequestKey;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalResponse;
@Service("transferFacade")
public class TransferFacadeImpl implements TransferFacade {
    @Override
    public DodopalResponse<Map<String, String>> sayHello(String name) {
        DodopalResponse<Map<String, String>> response = new DodopalResponse<Map<String, String>>();
        Map<String, String> resMap = new HashMap<String, String>();
        resMap.put(RequestKey.HELLO, "Hello " + name + ", welcome to call transfer project.");
        response.setResponseEntity(resMap);
        response.setCode(ResponseCode.SUCCESS);
        return response;
    }

}
