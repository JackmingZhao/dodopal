package com.dodopal.payment.delegate;

import com.dodopal.common.util.DodopalAppVarPropsUtil;
import com.dodopal.hessian.RemotingCallUtil;

public class BaseDelegate {

    public <V> V getFacade(Class<V> serviceInterface, String facadeUrlCon) {
        String facadeUrl = DodopalAppVarPropsUtil.getStringProp(facadeUrlCon);
        return RemotingCallUtil.getHessianService(facadeUrl, serviceInterface);
    }

}
