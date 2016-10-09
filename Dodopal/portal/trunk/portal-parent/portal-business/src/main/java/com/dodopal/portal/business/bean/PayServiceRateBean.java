package com.dodopal.portal.business.bean;

import java.io.Serializable;
/**
 * 支付服务费率和支付服务费率类型
 * @author xiongzhijing@dodopal.com
 *
 */
public class PayServiceRateBean implements Serializable {
 
    private static final long serialVersionUID = 7418139447672572683L;

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
