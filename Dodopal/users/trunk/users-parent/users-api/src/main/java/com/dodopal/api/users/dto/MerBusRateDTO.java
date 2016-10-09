package com.dodopal.api.users.dto;

import com.dodopal.common.model.BaseEntity;

/**
 * 类说明 ：商户业务费率
 * @author lifeng
 */

public class MerBusRateDTO extends BaseEntity {
    private static final long serialVersionUID = -827941134479162255L;
    /*启用标示*/
    private String activate;
    /*商户编码*/
    private String merCode;
    /*业务编码*/
    private String rateCode;
    /*业务所在一卡通编码*/
    private String providerCode;
    /*数值*/
    private double rate;
    /*费率类型*/
    private String rateType;

    public String getActivate() {
        return activate;
    }

    public void setActivate(String activate) {
        this.activate = activate;
    }

    public String getMerCode() {
        return merCode;
    }

    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }

    public String getRateCode() {
        return rateCode;
    }

    public void setRateCode(String rateCode) {
        this.rateCode = rateCode;
    }

    public String getProviderCode() {
        return providerCode;
    }

    public void setProviderCode(String providerCode) {
        this.providerCode = providerCode;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getRateType() {
        return rateType;
    }

    public void setRateType(String rateType) {
        this.rateType = rateType;
    }

}
