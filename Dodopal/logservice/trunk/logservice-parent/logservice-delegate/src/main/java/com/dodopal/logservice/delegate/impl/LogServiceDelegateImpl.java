package com.dodopal.logservice.delegate.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.dodopal.api.logservice.facade.LogServiceFacade;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.hessian.RemotingCallUtil;
import com.dodopal.logservice.delegate.LogServiceDelegate;

@Service("logServiceDelegate")
public class LogServiceDelegateImpl implements LogServiceDelegate {

    @Override
    public DodopalResponse<Map<String, String>> sayHello(String name) {
        LogServiceFacade facade = RemotingCallUtil.getHessianService("http://localhost:8089/logservice-web/hessian/index.do?id=", LogServiceFacade.class);
        return facade.sayHello(name);
    }

}
