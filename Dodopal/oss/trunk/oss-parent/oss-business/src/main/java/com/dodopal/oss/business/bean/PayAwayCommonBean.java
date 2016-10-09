package com.dodopal.oss.business.bean;

import com.dodopal.common.model.BaseEntity;

public class PayAwayCommonBean extends BaseEntity{

    /**
     * 
     */
    private static final long serialVersionUID = -6344842894385126567L;


    /**
     * 用户/商户 编号
     */
    private String userCode;
    
    /**
     * 用户/商户 名称
     */
    private String userName;
    
    /**
     * 支付名称
     */
    private String payWayName;
    /**
     * 支付类型
     */
    private String payType;
    /**
     * 支付服务费率
     */
    private double payServiceRate;
    
    /**
     * 启用标示
     */
    private String activate;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPayWayName() {
        return payWayName;
    }

    public void setPayWayName(String payWayName) {
        this.payWayName = payWayName;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public double getPayServiceRate() {
        return payServiceRate;
    }

    public void setPayServiceRate(double payServiceRate) {
        this.payServiceRate = payServiceRate;
    }

    public String getActivate() {
        return activate;
    }

    public void setActivate(String activate) {
        this.activate = activate;
    }

  
    
}
