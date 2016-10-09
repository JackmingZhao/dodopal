package com.dodopal.running.business.service;

import com.dodopal.running.business.model.SysTimeThreshold;

public interface SysTimeThresholdService {

    /**
     * 根据业务编码查询之间阀值
     * @param code 业务编码
     * @return SysTimeThreshold
     */
    public SysTimeThreshold findSysTimeThresholdByCode(String code);
}
