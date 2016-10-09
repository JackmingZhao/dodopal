package com.dodopal.common.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dodopal.common.service.ResponseMessageService;
import com.dodopal.common.service.SysDdicService;
import com.dodopal.common.util.SpringBeanUtil;

public class MapRefreshTask {

    private final static Logger logger = LoggerFactory.getLogger(MapRefreshTask.class);
    
    /**
     * 业务逻辑处理
     */
    public void doReresh() {
        logger.info("start to do refresh....");
        SysDdicService sysDdic = (SysDdicService) SpringBeanUtil.getBean(SysDdicService.BEAN_NAME);
        sysDdic.doReload();
        ResponseMessageService responseMessageService = (ResponseMessageService) SpringBeanUtil.getBean(ResponseMessageService.BEAN_NAME);
        responseMessageService.doReload();
        logger.info("end to do refresh....");
    }
}
