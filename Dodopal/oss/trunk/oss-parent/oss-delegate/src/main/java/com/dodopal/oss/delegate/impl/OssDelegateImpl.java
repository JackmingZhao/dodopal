package com.dodopal.oss.delegate.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.dodopal.api.users.facade.UsersFacade;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.api.facade.OssFacade;
import com.dodopal.oss.delegate.OssDelegate;
import com.dodopal.hessian.RemotingCallUtil;

@Service("ossDelegate")
public class OssDelegateImpl implements OssDelegate {

    //    private Logger logger = Logger.getLogger(OssDelegateImpl.class);

    @Override
    public DodopalResponse<Map<String, String>> sayHello(String name) {
        OssFacade ossService = RemotingCallUtil.getHessianService("http://localhost:81/oss-web/hessian/index.do?id=", OssFacade.class);
        return ossService.sayHello(name);
    }

    @Override
    public DodopalResponse<Map<String, String>> getUsersHello(String name) {
        UsersFacade ossService = RemotingCallUtil.getHessianService("http://192.168.1.100:81/users-web/hessian/index.do?id=", UsersFacade.class);
        return ossService.sayHello(name);
    }

}
