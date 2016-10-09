package com.dodopal.portal.business.bean;

import com.dodopal.common.model.BaseEntity;
/**
 * 账户余额
 * @author hxc
 *
 */
public class AccountChildMerchantBean extends BaseEntity{
	private static final long serialVersionUID = 2861534273986437905L;
	
	/*商户编号*/
    private String merCode;
    /*商户名称*/
    private String merName;
 
    /*商户类型*/
    private String merType;

    /*所属上级商户编码*/
    private String merParentCode;

    /*商户 可用余额*/
    private double merMoney;
    /*商户 总余额*/
    private double merMoneySum;
    
    /*商户 冻结金额*/
    private double frozenAmount;
    
    public double getFrozenAmount() {
		return frozenAmount;
	}
	public void setFrozenAmount(double frozenAmount) {
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
    public double getMerMoney() {
        return merMoney;
    }
    public void setMerMoney(double merMoney) {
        this.merMoney = merMoney;
    }
    public double getMerMoneySum() {
        return merMoneySum;
    }
    public void setMerMoneySum(double merMoneySum) {
        this.merMoneySum = merMoneySum;
    }
    
    
}
