package com.dodopal.portal.business.bean;

import com.dodopal.common.model.BaseEntity;

public class PayWayBean extends BaseEntity {


    /**
     * 
     */
    private static final long serialVersionUID = 3141994048213986493L;

    /**
     * PAY_ACT：账户支付
     * PAY_CARD：一卡通支付
     * PAY_ONLINE：在线支付
     * PAY_BANK：银行支付
     * 具体code 参照枚举 PayTypeEnum
     */
    private String payType;
    
    /**
     * 支付方式名称
     */
    private String payName;
    
    /**
     * 服务费率：千分比
     */
    private double payServiceRate;
    
    /**
     * 支付方式图标名称
     */
    private String payLogo;
    
    
    /**
     * 支付网关类型
     */
    private String bankGatewayType;
    
    

    public String getBankGatewayType() {
        return bankGatewayType;
    }

    public void setBankGatewayType(String bankGatewayType) {
        this.bankGatewayType = bankGatewayType;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }

    public double getPayServiceRate() {
        return payServiceRate;
    }

    public void setPayServiceRate(double payServiceRate) {
        this.payServiceRate = payServiceRate;
    }

    public String getPayLogo() {
        return payLogo;
    }

    public void setPayLogo(String payLogo) {
        this.payLogo = payLogo;
    }
    
    
}
