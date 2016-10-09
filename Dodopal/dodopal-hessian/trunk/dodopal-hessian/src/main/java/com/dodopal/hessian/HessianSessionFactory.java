/*
 * Sdo.com Inc.
 * Copyright (c) 2009 All Rights Reserved.
 */
package com.dodopal.hessian;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mingxu
 */
public class HessianSessionFactory {
    @SuppressWarnings("unchecked")
    private Map<String, HessianSession> sessions = new HashMap<String, HessianSession>();

    private Map<RemotingModuleEnum, AtomicInteger> offsets = new HashMap<RemotingModuleEnum, AtomicInteger>();

    private static HessianSessionFactory instance = new HessianSessionFactory();

    private final static Logger log = LoggerFactory.getLogger(HessianSessionFactory.class);

    private AtomicLong sessionIdCounter = new AtomicLong(0L);

    private HessianSessionFactory() {
    }

    public static HessianSessionFactory getInstance() {
        return instance;
    }

    /**
     * @param <T>
     * @param module
     * @param serviceInterface
     * @return
     */
    public synchronized <T> T getServiceWithoutSession(RemotingModuleEnum module, Class<T> serviceInterface) {
        return this.createSession(module, serviceInterface).getHessianService();
    }

    /**
     * @param <T>
     * @param module
     * @param serviceInterface
     * @return
     */
    public synchronized <T> HessianSession<T> createSession(RemotingModuleEnum module, Class<T> serviceInterface) {
        if (!offsets.containsKey(module)) {
            offsets.put(module, new AtomicInteger(0));
        }

        AtomicInteger offset = this.offsets.get(module);
        List<String> urls = module.getAllUrls();

        int tryTimes = 0;
        while (true) {
            String connUrl = urls.get(offset.get());
            offset.set((offset.get() + 1) % urls.size());
            NoOperationService nopTest = RemotingCallUtil.getHessianService(connUrl, NoOperationService.class);

            try {
                //作为NOOP(No operation)调用，测试调用是否成功（对方是否可用）
                nopTest.nop();

                //测试OK
                return this.exeCreateSession(connUrl, serviceInterface, RemotingCallUtil.getHessianService(connUrl, serviceInterface));
            }
            catch (Exception ex) {
                log.warn("Connection url test failed." + connUrl, ex);

                if (++tryTimes > urls.size()) {
                    //轮询一遍以后所有的Session均不可用。
                    log.error("No availble service for module:" + module.getName());
                    throw new RuntimeException("No available connections exists!");
                }

                continue;
            }
        }
    }

    /**
     * @param <T>
     * @param sessionId
     * @param serviceInterface
     * @return
     */
    @SuppressWarnings("unchecked")
    public synchronized <T> HessianSession<T> getSession(long sessionId, Class<T> serviceInterface) {
        for (HessianSession v : this.sessions.values()) {
            if (v.getSessionId() == sessionId) {
                HessianSession<T> target = (HessianSession) v.clone();
                T obj = RemotingCallUtil.getHessianService(v.getConnectionUrl(), serviceInterface);
                target.setHessianService(obj);
                return target;
            }
        }

        log.error("Session id :" + sessionId + " not found.");
        return null;
    }

    /**
     * @param <T>
     * @param hessianSession
     * @param serviceInterface
     * @return
     */
    public synchronized <T> T getServiceBySession(HessianSession<T> hessianSession, Class<T> serviceInterface) {
        return RemotingCallUtil.getHessianService(hessianSession.getConnectionUrl(), serviceInterface);
    }

    @SuppressWarnings("unchecked")
    private <T> HessianSession<T> exeCreateSession(String connUrl, Class<T> cls, T service) {
        if (this.sessions.containsKey(connUrl)) {
            HessianSession prototype = this.sessions.get(connUrl);
            HessianSession<T> target = (HessianSession) prototype.clone();

            target.setHessianService(service);
            return target;
        } else {
            HessianSession<T> session = new HessianSession<T>(service, this.sessionIdCounter.incrementAndGet(), connUrl);
            this.sessions.put(connUrl, session);
            return session;
        }
    }

}
