package com.dodopal.account.business.model;

import com.dodopal.common.model.BaseEntity;

public class AccountChildMerchant extends BaseEntity{
    private static final long serialVersionUID = 1240998863435765289L;
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
    /*商户 总余额*/
    private long merMoneySum;
    
    /*商户 冻结金额*/
    private long frozenAmount;
    
    
    public long getFrozenAmount() {
        return frozenAmount;
    }
    public void setFrozenAmount(long frozenAmount) {
        this.frozenAmount = frozenAmount;
    }
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
    public long getMerMoneySum() {
        return merMoneySum;
    }
    public void setMerMoneySum(long merMoneySum) {
        this.merMoneySum = merMoneySum;
    }
    
    
}
