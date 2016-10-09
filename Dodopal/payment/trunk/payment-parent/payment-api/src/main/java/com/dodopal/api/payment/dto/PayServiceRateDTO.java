package com.dodopal.api.payment.dto;

import java.io.Serializable;
/**
 * 支付服务费率
 * @author xiongzhijing@dodopal.com
 *
 */
public class PayServiceRateDTO implements Serializable {
    
    private static final long serialVersionUID = 6432685949634993651L;
    /**
     * 0：费率（千分比）；1：单笔（元）
     */
    private String rateType;
    
    /**
     * rateType = 0千分比 数值
       rateType = 1单笔金额（元） 数值

     */
    private double rate;
    
    public String getRateType() {
        return rateType;
    }
    public void setRateType(String rateType) {
        this.rateType = rateType;
    }
    public double getRate() {
        return rate;
    }
    public void setRate(double rate) {
        this.rate = rate;
    }

    

}
