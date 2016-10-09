package com.dodopal.oss.business.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.oss.business.dao.VUserInfoMapper;
import com.dodopal.oss.business.model.VUserInfo;
import com.dodopal.oss.business.service.VUserInfoService;
@Service
public class VUserServiceImpl implements VUserInfoService {
    private final static Logger log = LoggerFactory.getLogger(VUserServiceImpl.class);
    
    @Autowired
    VUserInfoMapper vuserInfoMapper;
    @Override
    public VUserInfo findVUserInfoById(String id) {
        log.info("userid:"+id);
        return vuserInfoMapper.findVuserInfoById(id);
    }

}
