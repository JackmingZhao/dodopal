package com.dodopal.oss.business.model.dto;

import java.util.Date;

import com.dodopal.common.model.QueryBase;

public class MerCreditsQuery extends QueryBase {
    private static final long serialVersionUID = 6007231431005207201L;

    /**
     * 用户名称/商户名称
     */
    private String merOrUserName;
    
    /**
     * 创建时间结束
     */
    private Date createDateEnd;
    
    /**
     * 创建时间开始
     */
    private Date createDateStart;
    
    /**
     * 用户类型
     */
    private String userType;
    
    /**
     * 支付类型
     */
    private String payType;

    public String getMerOrUserName() {
        return merOrUserName;
    }

    public void setMerOrUserName(String merOrUserName) {
        this.merOrUserName = merOrUserName;
    }

    public Date getCreateDateEnd() {
        return createDateEnd;
    }

    public void setCreateDateEnd(Date createDateEnd) {
        this.createDateEnd = createDateEnd;
    }

    public Date getCreateDateStart() {
        return createDateStart;
    }

    public void setCreateDateStart(Date createDateStart) {
        this.createDateStart = createDateStart;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }
}
