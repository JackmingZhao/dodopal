package com.dodopal.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.common.dao.SysOperationMapper;
import com.dodopal.common.model.SysOperation;
import com.dodopal.common.service.SysOperationService;

@Service("sysOperationService")
public class SysOperationServiceImpl implements SysOperationService, InitializingBean {

    @Autowired
    private SysOperationMapper sysOperationMapper;

    /**
     * 由于每个系统都是独立部署的节点，对于同一个jvm 中的map 任何一个线程对其进行更新都是生效的
     */
    Map<String, Integer> sysOperationMap = new HashMap<String, Integer>();

    @Override
    public boolean isUpdated(String code) {
        int version = sysOperationMap.get(code);
        SysOperation operation = sysOperationMapper.findOperation(code);
        if (operation != null && operation.getVersion() > version) {
            sysOperationMap.put(operation.getCode(), operation.getVersion());
            return true;
        }
        return false;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        List<SysOperation> operations = sysOperationMapper.findOperations();
        if (CollectionUtils.isNotEmpty(operations)) {
            for (SysOperation operation : operations) {
                sysOperationMap.put(operation.getCode(), operation.getVersion());
            }
        }
    }
}
