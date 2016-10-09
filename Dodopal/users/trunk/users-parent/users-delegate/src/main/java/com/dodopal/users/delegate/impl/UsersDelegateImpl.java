package com.dodopal.users.delegate.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.dodopal.api.users.facade.UsersFacade;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.hessian.RemotingCallUtil;
import com.dodopal.users.delegate.UsersDelegate;

@Service("usersDelegate")
public class UsersDelegateImpl implements UsersDelegate {

    @Override
    public DodopalResponse<Map<String, String>> sayHello(String name) {
        UsersFacade ossService = RemotingCallUtil.getHessianService("http://localhost:81/users-web/hessian/index.do?id=", UsersFacade.class);
        return ossService.sayHello(name);
    }

}
