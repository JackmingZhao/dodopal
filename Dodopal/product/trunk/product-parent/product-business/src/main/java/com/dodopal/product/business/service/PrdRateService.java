package com.dodopal.product.business.service;

import java.util.List;

import com.dodopal.product.business.model.PrdRate;

public interface PrdRateService {
    /**
     * 查询业务类型
     * @return
     */
    public List<PrdRate> findPrdRate();
}
