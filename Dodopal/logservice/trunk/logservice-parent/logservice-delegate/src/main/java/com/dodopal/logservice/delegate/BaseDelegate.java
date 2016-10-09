package com.dodopal.logservice.delegate;

import com.dodopal.common.util.DodopalAppVarPropsUtil;
import com.dodopal.hessian.RemotingCallUtil;

public class BaseDelegate {

    /**
     * 取得hessian接口
     * @param serviceInterface
     * @return
     */
    public <V> V getFacade(Class<V> serviceInterface, String facadeUrlCon) {
        String facadeUrl = DodopalAppVarPropsUtil.getStringProp(facadeUrlCon);
        return RemotingCallUtil.getHessianService(facadeUrl, serviceInterface);
    }

}
