package com.dodopal.portal.delegate.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.dodopal.common.model.DodopalResponse;
import com.dodopal.hessian.RemotingCallUtil;
import com.dodopal.portal.api.PortalFacade;
import com.dodopal.portal.delegate.PortalDelegate;

@Service("portalDelegate")
public class PortalDelegateImpl implements PortalDelegate {

    @Override
    public DodopalResponse<Map<String, String>> sayHello(String name) {
        PortalFacade portalService = RemotingCallUtil.getHessianService("http://localhost:8083/portal-web/hessian/index.do?id=", PortalFacade.class);
        return portalService.sayHello(name);
    }

}
