package com.dodopal.hessian;

public class HessianService {
    /**
	 */
    private Class serviceInterface;

    /**
	 */
    private Object serviceBean;

    /**
     * @param serviceInterface
     * @param serviceBean
     */
    public HessianService(Class serviceInterface, Object serviceBean) {
        this.serviceInterface = serviceInterface;
        this.serviceBean = serviceBean;
    }

    /**
     * @return the serviceInterface
     */
    public Class getServiceInterface() {
        return serviceInterface;
    }

    /**
     * @return the serviceBean
     */
    public Object getServiceBean() {
        return serviceBean;
    }
}
