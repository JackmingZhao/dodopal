package com.dodopal.api.payment.dto.query;

import com.dodopal.common.model.QueryBase;

public class PayCommonQueryDTO extends QueryBase{
    

    private static final long serialVersionUID = -3083564551201182923L;

    /**
     * 用户/商户 编号
     */
    private String userCode;
    
    /**
     * 用户/商户　名称
     */
    private String userName;
    
    /**
     *   支付类型
     */
    private String payType;
    
    /**
     * 支付方式名称
     */
    private String payWayName;
    
    /**
     * 启用标志
     */
    private String activate;
    
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

    public String getActivate() {
        return activate;
    }

    public void setActivate(String activate) {
        this.activate = activate;
    }

    
}
