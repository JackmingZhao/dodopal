package com.dodopal.product.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.product.business.dao.PrdRateMapper;
import com.dodopal.product.business.model.PrdRate;
import com.dodopal.product.business.service.PrdRateService;
@Service
public class PrdRateServiceImpl implements PrdRateService {

    @Autowired
    PrdRateMapper prdRateMapper;
    @Override
    public List<PrdRate> findPrdRate() {
        return prdRateMapper.findPrdRate();
    }

}
