package com.dodopal.oss.business.dao;

import com.dodopal.oss.business.model.SysTimeThreshold;

public interface SysTimeThresholdMapper {
    /**
     * 根据业务编码查询之间阀值
     * @param code 业务编码
     * @return SysTimeThreshold
     */
    public SysTimeThreshold findSysTimeThresholdByCode(String code);

}
