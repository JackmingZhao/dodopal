package com.dodopal.portal.business.bean;

import com.dodopal.common.model.BaseEntity;


public class DirectMerChantBean extends BaseEntity{
    private static final long serialVersionUID = -9059861549488135596L;
    /*商户编号*/
    private String merCode;
    /*商户名称*/
    private String merName;
 
    /*商户类型  0是个人，1是企业*/ 
    private String merType;

    /*所属上级商户编码*/
    private String merParentCode;

    /*商户 可用余额*/
    private String merMoney;
    public String getMerCode() {
        return merCode;
    }
    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }
    public String getMerName() {
        return merName;
    }
    public void setMerName(String merName) {
        this.merName = merName;
    }
    public String getMerType() {
        return merType;
    }
    public void setMerType(String merType) {
        this.merType = merType;
    }
    public String getMerParentCode() {
        return merParentCode;
    }
    public void setMerParentCode(String merParentCode) {
        this.merParentCode = merParentCode;
    }

    public String getMerMoney() {
        return merMoney;
    }
    public void setMerMoney(String merMoney) {
        this.merMoney = merMoney;
    }
    
    
 
}
