package com.dodopal.hessian;

import java.util.List;

import org.springframework.remoting.caucho.HessianServiceExporter;

public interface IHessianServiceManager {
    /**
     * @param obj
     */
    void registerService(Object obj);

    /**
     * @param objects
     */
    void registerServices(List objects);

    /**
     * @param service
     */
    void unregisterService(HessianService service);

    /**
     * @param serviceId
     * @return
     */
    HessianServiceExporter findService(String serviceId);
}
