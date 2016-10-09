package com.dodopal.api.users.dto;

import com.dodopal.common.model.BaseEntity;

public class DirectMerChantDTO extends BaseEntity{
    private static final long serialVersionUID = 5568198457748681709L;
    /*商户编号*/
    private String merCode;
    /*商户名称*/
    private String merName;
 
    /*商户类型*/
    private String merType;

    /*所属上级商户编码*/
    private String merParentCode;

    /*商户 可用余额*/
    private long merMoney;
    
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

    public long getMerMoney() {
        return merMoney;
    }
    public void setMerMoney(long merMoney) {
        this.merMoney = merMoney;
    }
  
    
    
}
