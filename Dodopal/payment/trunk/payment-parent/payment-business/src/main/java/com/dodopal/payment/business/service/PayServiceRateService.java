package com.dodopal.payment.business.service;

import java.util.List;

import com.dodopal.payment.business.model.PayServiceRate;
import com.dodopal.payment.business.model.PayWayGeneral;

/**
 * 支付服务费率
 * @author xiongzhijing@dodopal.com
 * @version 2015年10月10日
 *
 */
public interface PayServiceRateService {
    /**
     * 查询支付服务费
     * @param payConfigId （通用）支付方式id
     * @param business 业务类型
     * @param amount 金额
     * @return List<PayServiceRate>
     */
    public List<PayServiceRate> findPayServiceRate(String payConfigId, String business);

    public PayServiceRate getServiceTypeAndRate(PayWayGeneral payWayGeneral,String bussinessType,long amount);
    
}
