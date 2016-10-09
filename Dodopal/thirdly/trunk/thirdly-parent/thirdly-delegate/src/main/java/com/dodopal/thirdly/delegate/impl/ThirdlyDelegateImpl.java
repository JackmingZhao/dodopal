package com.dodopal.thirdly.delegate.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.dodopal.api.thirdly.facade.ThirdlyFacade;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.hessian.RemotingCallUtil;
import com.dodopal.thirdly.delegate.ThirdlyDelegate;

@Service("thirdlyDelegate")
public class ThirdlyDelegateImpl implements ThirdlyDelegate {

    @Override
    public DodopalResponse<Map<String, String>> sayHello(String name) {
        ThirdlyFacade cardService = RemotingCallUtil.getHessianService("http://localhost:8086/card-web/hessian/index.do?id=", ThirdlyFacade.class);
        return cardService.sayHello(name);
    }

}
