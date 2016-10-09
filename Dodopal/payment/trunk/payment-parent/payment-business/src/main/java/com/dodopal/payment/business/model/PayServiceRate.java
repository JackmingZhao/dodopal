package com.dodopal.payment.business.model;

import com.dodopal.common.model.BaseEntity;

/**
 * 支付服务费
 * @author xiongzhijing@dododpal.com
 * @version 2015年10月10日
 *
 */
public class PayServiceRate extends BaseEntity{

    private static final long serialVersionUID = 3777042723315901634L;
    
    //启用标识：0：启用；1：停用
    private String activite;
    
    //支付方式配置信息表ID:关联支付方式配置信息表，相当于外键
    private String payConfigId;
    
    //业务类型:01：一卡通充值；02：生活缴费；99：账户充值
    private String businessType;
    
    //支付服务费率类型：0：费率（千分比）；1：单笔（元）
    private String payServiceType;
    
    //单笔起始额度
    private long amounrStart;
    
    //单笔结束额度
    private long amounrEnd;
    
    //费率
    private double rate;

    public String getActivite() {
        return activite;
    }

    public void setActivite(String activite) {
        this.activite = activite;
    }

    public String getPayConfigId() {
        return payConfigId;
    }

    public void setPayConfigId(String payConfigId) {
        this.payConfigId = payConfigId;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getPayServiceType() {
        return payServiceType;
    }

    public void setPayServiceType(String payServiceType) {
        this.payServiceType = payServiceType;
    }


    public long getAmounrStart() {
        return amounrStart;
    }

    public void setAmounrStart(long amounrStart) {
        this.amounrStart = amounrStart;
    }

    public long getAmounrEnd() {
        return amounrEnd;
    }

    public void setAmounrEnd(long amounrEnd) {
        this.amounrEnd = amounrEnd;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

}
