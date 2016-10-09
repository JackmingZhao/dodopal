package com.dodopal.payment.business.model.query;

import com.dodopal.common.model.QueryBase;

/**
 * @author hxc
 */
public class PayCommonQuery extends QueryBase{
    private static final long serialVersionUID = -2775947462521485365L;

    /**
     * 用户/商户 编号
     */
    private String userCode;
    
    /**
     * 用户/商户 名称
     */
    private String userName;
    
    
    /**
     * 启用标示
     */
    private String activate;
    
    /**
     * 支付类型
     */
    private String payType;
    
    /**
     * 支付名称
     */
    private String payWayName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getActivate() {
        return activate;
    }

    public void setActivate(String activate) {
        this.activate = activate;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayWayName() {
        return payWayName;
    }

    public void setPayWayName(String payWayName) {
        this.payWayName = payWayName;
    }
    
    
}
