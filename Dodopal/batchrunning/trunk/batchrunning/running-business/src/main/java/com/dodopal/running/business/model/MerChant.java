/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.running.business.model;

/**
 * Created by lenovo on 2015/10/29.
 */
public class MerChant {
    private String merCode;
    private String merParentCode;
    private String merRateType;
    private String merType;
    private double merRate;

    public String getMerType() {
        return merType;
    }

    public void setMerType(String merType) {
        this.merType = merType;
    }

    public String getMerCode() {
        return merCode;
    }

    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }

    public String getMerParentCode() {
        return merParentCode;
    }

    public void setMerParentCode(String merParentCode) {
        this.merParentCode = merParentCode;
    }

    public String getMerRateType() {
        return merRateType;
    }

    public void setMerRateType(String merRateType) {
        this.merRateType = merRateType;
    }

    public double getMerRate() {
        return merRate;
    }

    public void setMerRate(double merRate) {
        this.merRate = merRate;
    }
}
