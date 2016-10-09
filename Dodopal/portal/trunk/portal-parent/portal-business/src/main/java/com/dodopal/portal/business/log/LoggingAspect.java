package com.dodopal.portal.business.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dodopal.common.util.DDPStringBuilder;

@Aspect
@Component
public class LoggingAspect {

    private Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(public * com.dodopal.portal.business.*.*.*(..)) || execution(public * com.dodopal.portal.web.controller.*.*.*(..)) || execution(public * com.dodopal.portal.delegate.*.*.*(..))")
    public void allMethods() {
    }

    @Around("allMethods()")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        long time = System.currentTimeMillis();
        if (logger.isInfoEnabled()) {
            StringBuffer log = new StringBuffer("进入 方法" + joinPoint.getSignature().toShortString());
            Object[] args = joinPoint.getArgs();
            if (args != null && args.length > 0) {
                log.append("; 参数: 【");
                for (Object obj : args) {
                    log.append(DDPStringBuilder.toString(obj) + ";");
                }
                log.append("】");
            }
            logger.info(log.toString());
        }

        Object value = joinPoint.proceed();
        if (logger.isInfoEnabled()) {
            logger.info("退出方法 " + joinPoint.getSignature().toShortString() + " time cost : " + (System.currentTimeMillis() - time) + "ms");
        }
        return value;
    }

}
