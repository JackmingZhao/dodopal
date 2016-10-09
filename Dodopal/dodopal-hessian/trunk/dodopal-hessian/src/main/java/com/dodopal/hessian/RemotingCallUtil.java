package com.dodopal.hessian;

import java.net.MalformedURLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caucho.hessian.client.HessianProxyFactory;

public class RemotingCallUtil {

    private final static Logger log = LoggerFactory.getLogger(RemotingCallUtil.class);

    /**
     * @param <V>
     * @param connectionUrl
     * @param serviceInterface
     * @return
     */
    public static <V> V getHessianService(String connectionUrl, Class<V> serviceInterface) {
        String url = connectionUrl + serviceInterface.getCanonicalName();
        return getHessianServiceNormal(url, serviceInterface);
    }

    /**
     * @param url
     * @param serviceInterface
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <V> V getHessianServiceNormal(String url, Class<V> serviceInterface) {

        HessianProxyFactory factory = new HessianProxyFactory();
        factory.setChunkedPost(false);
        factory.setOverloadEnabled(true);
        try {
            Object stub = factory.create(serviceInterface, url, RemotingCallUtil.class.getClassLoader());
            return (V) stub;
        }
        catch (MalformedURLException e) {
            log.error("Remote service call error", e);
            throw new RuntimeException(e);
        }

    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <V extends Object> V getHessianServiceByModule(RemotingModuleEnum module, Class serviceInterface) {
        return (V) getHessianService(module.getUniqueUrl(), serviceInterface);
    }
}
