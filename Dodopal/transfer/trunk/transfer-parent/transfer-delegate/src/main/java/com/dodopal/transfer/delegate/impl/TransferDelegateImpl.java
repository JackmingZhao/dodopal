package com.dodopal.transfer.delegate.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.dodopal.api.transfer.TransferFacade;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.hessian.RemotingCallUtil;
import com.dodopal.transfer.delegate.TransferDelegate;
@Service("transferDelegate")
public class TransferDelegateImpl implements TransferDelegate {

    @Override
    public DodopalResponse<Map<String, String>> sayHello(String name) {
     TransferFacade cardService = RemotingCallUtil.getHessianService("http://localhost:8090/TransferFacade-web/hessian/index.do?id=", TransferFacade.class);
        return cardService.sayHello(name);
    }

}
