package com.dodopal.running.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.running.business.dao.SysTimeThresholdMapper;
import com.dodopal.running.business.model.SysTimeThreshold;
import com.dodopal.running.business.service.SysTimeThresholdService;

@Service
public class SysTimeThresholdServiceImpl implements SysTimeThresholdService {

    @Autowired
    SysTimeThresholdMapper sysTimeThresholdMapper;
    
    @Override
    @Transactional(readOnly = true)
    public SysTimeThreshold findSysTimeThresholdByCode(String code) {
        return  sysTimeThresholdMapper.findSysTimeThresholdByCode(code);
    }

}
