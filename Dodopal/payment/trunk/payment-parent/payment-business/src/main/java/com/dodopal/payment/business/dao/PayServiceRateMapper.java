package com.dodopal.payment.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.payment.business.model.PayServiceRate;

public interface PayServiceRateMapper {
    
    /**
     * 查询支付服务费
     * @param payConfigId （通用）支付方式id
     * @param business 业务类型
     * @param amount 金额
     * @return List<PayServiceRate>
     */
    public List<PayServiceRate> findPayServiceRate(@Param("payConfigId")String payConfigId, @Param("business")String business);
    
}
