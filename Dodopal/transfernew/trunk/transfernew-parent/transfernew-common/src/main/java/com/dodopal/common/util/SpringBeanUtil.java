package com.dodopal.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service
public class SpringBeanUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
    }

    public static ApplicationContext getApplicationContext() {
        if (null == applicationContext) {
            throw new IllegalStateException("'applicationContext' property is null,ApplicationContextHolder not yet init.");
        }
        return applicationContext;
    }

    public static Object getBean(String beanName) {
        return getApplicationContext().getBean(beanName);
    }

    public static <T> T getBean(Class<T> type) {
        return (T) getApplicationContext().getBean(type);
    }

    public static <T> T getBean(String beanName, Class<T> type) {
        return (T) getApplicationContext().getBean(beanName, type);
    }

}
