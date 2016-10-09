package com.dodopal.oss.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.oss.business.dao.SysTimeThresholdMapper;
import com.dodopal.oss.business.model.SysTimeThreshold;
import com.dodopal.oss.business.service.SysTimeThresholdService;

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
