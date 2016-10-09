package com.dodopal.hessian;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.stereotype.Service;

@Service("hessianServiceManager")
public class HessianServiceManager implements IHessianServiceManager {

    private final static Logger log = LoggerFactory.getLogger(HessianServiceManager.class);

    /**
     * 所有注册的hessian服务.
     */
    private Map<String, HessianService> services = new HashMap<String, HessianService>();

    /**
     * HttpInvokerServiceExporter的缓存,避免每次访问都实例化exporter.
     */
    private Map<String, HessianServiceExporter> exporters = new HashMap<String, HessianServiceExporter>();

    public HessianServiceManager() {
        //默认：将用于测试可连接性的NoOperationService注入：
        this.registerService(new NoOperationServiceImpl());
    }

    /* (non-Javadoc)
     * @see com.allinpay.rcf.hessian.server.IHessianServiceManager#registerService(java.lang.Object)
     */
    public void registerService(Object obj) {
        //使用第一个接口作为发布出去的hessian服务接口.
        Class[] clazz = obj.getClass().getInterfaces();
        //如果在当前类上没有实现接口,则取父类实现的第一个接口.
        //契约:要发布为hessian服务的类或父类必须要实现一个服务接口.
        if (null == clazz || clazz.length == 0) {
            clazz = obj.getClass().getSuperclass().getInterfaces();
        }
        Class inf = clazz[0];
        HessianService service = new HessianService(inf, obj);
        services.put(inf.getName(), service);
    }

    /* (non-Javadoc)
     * @see com.allinpay.rcf.hessian.server.IHessianServiceManager#registerServices(java.util.List)
     */
    public void registerServices(List objects) {
        if (null != objects) {
            Iterator it = objects.iterator();
            while (it.hasNext()) {
                registerService(it.next());
            }
        }
    }

    /* (non-Javadoc)
     * @see com.allinpay.bundles.common.hessianservicebundle.service.IHessianServiceManager#unregisterService(com.cbireport.bundles.common.hessianservicebundle.service.HessianService)
     */
    public void unregisterService(HessianService service) {
        String name = service.getServiceInterface().getName();
        exporters.remove(name);
        services.remove(name);
        //		System.out.println("unregisted hessian service:" + name);
    }

    /* (non-Javadoc)
     * @see com.allinpay.bundles.common.hessianservicebundle.service.IHessianServiceManager#findService(java.lang.String)
     */
    public HessianServiceExporter findService(String serviceId) {
        log.debug("Hession Service");
        for (Map.Entry<String, HessianService> entry : services.entrySet()) {
            log.debug("Hession Services[" + entry.getKey() + "]");
        }
        HessianServiceExporter exporter = exporters.get(serviceId);
        //取缓存,没有则创建,这样可以减少启动时间.
        if (null == exporter) {
            exporter = createExporter(serviceId);
        }
        return exporter;
    }

    /**
     * 根据参数serviceId从services容器中取到hessian服务,然后创建一个HessianServiceExporter
     * 并把创建的对象放到exporter容器中,然后返回这个对象.
     * @param serviceId
     * @return
     */
    protected HessianServiceExporter createExporter(String serviceId) {
        HessianServiceExporter exporter = new HessianServiceExporter();
        HessianService service = services.get(serviceId);
        if (null == service) {
            return null;
        }
        exporter.setService(service.getServiceBean());
        exporter.setServiceInterface(service.getServiceInterface());
        exporter.afterPropertiesSet();
        exporters.put(serviceId, exporter);
        return exporter;
    }
}
