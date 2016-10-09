package com.dodopal.common.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.common.dao.PrdYktInfoMapper;
import com.dodopal.common.model.PrdYktInfo;
import com.dodopal.common.service.PrdYktInfoService;
@Service("prdYktInfoService")
public class PrdYktInfoServiceImpl implements PrdYktInfoService{
    private static final Map<String, PrdYktInfo> prdYktInfoMap = new HashMap<String, PrdYktInfo>();
    
    @Autowired
    PrdYktInfoMapper prdmapper;
    
    @Override
    @Transactional(readOnly = true)
    public PrdYktInfo findPrdYktInfoYktCode(String yktCode) {
        if (prdYktInfoMap.containsKey(yktCode)) {
            return prdYktInfoMap.get(yktCode);
        }
        PrdYktInfo prdYktInfo = prdmapper.findPrdYktInfoYktCode(yktCode);
        if (prdYktInfo != null) {
            prdYktInfoMap.put(prdYktInfo.getYktCode(), prdYktInfo);
            return prdYktInfo;
        }
        return null;
    }
}
