/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.running.business.model;

import com.dodopal.common.model.BaseEntity;

/**
 * Created by lenovo on 2015/10/28.
 */
public class MerchantUserInfo extends BaseEntity {
    private String userCode;
    private String merCode;
    private String merUserType;
    private String merName;//商户名称
    private String merRateType;
    private double merRate;
    private String merParentCode;

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

    public String getMerName() {
        return merName;
    }

    public void setMerName(String merName) {
        this.merName = merName;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getMerCode() {
        return merCode;
    }

    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }

    public String getMerUserType() {
        return merUserType;
    }

    public void setMerUserType(String merUserType) {
        this.merUserType = merUserType;
    }
}
