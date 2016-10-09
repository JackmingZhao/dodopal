package com.dodopal.account.business.bean;

import com.dodopal.common.model.BaseEntity;

/**
 * 资金授信账户及调账信息
 * @author 
 */
public class AccountFundAdjustInfoDTO extends BaseEntity {

    private static final long serialVersionUID = 8405724035785748805L;
    /*
     * 编号(“F” + 账户类别 + 主账户编号)
     */
    private String fundAccountCode;

    /*
     * 资金类别:(枚举：资金、授信)
     */
    private String fundType;

    /*
     * 主账户编号(表明这个资金账户从属于哪个主账户)
     */
    private String accountCode;

    /*
     * 累计总金额(单位：分。默认为0。该账户历次充值累计相加的总金额。)
     */
    private long sumTotalAmount;

    /*
     * 总余额(单位：分。默认为0。表示归属于这个资金账户的所有金额，包括冻结资金。)
     */
    private long totalBalance;

    /*
     * 可用金额(单位：分。默认为0。表示该账户中可以自由使用的金额)
     */
    private long availableBalance;

    /*
     * 冻结金额(单位：分。默认为0。表示这部分金额虽然归属于该账户，但是当前状态下不能自由使用比如在进行公交卡充值流程中，相应的充值费用被冻结。)
     */
    private long frozenAmount;

    /*
     * 最近一次变动金额（单位：分。默认为0。最近一次针对账户操作的金额（包括资金冻结和资金解冻））
     */
    private long lastChangeAmount;

    /*
     * 状态(默认为正常)
     */
    private String state;

    /**
     * 枚举：个人、企业
     */
    private String customerType;
    
    /**
     * 类型是商户：商户号；类型是个人：用户编号
     */
    private String customerNo;
    
    /**
     * 账户交易类型：正调账、反调账
     */
    private String accountAdjustType;
    
    /**
     * 调账金额，单位为分。必须为正整数
     */
    private long accountAdjustAmount;

    public String getFundAccountCode() {
        return fundAccountCode;
    }

    public void setFundAccountCode(String fundAccountCode) {
        this.fundAccountCode = fundAccountCode;
    }

    public String getFundType() {
        return fundType;
    }

    public void setFundType(String fundType) {
        this.fundType = fundType;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public long getSumTotalAmount() {
        return sumTotalAmount;
    }

    public void setSumTotalAmount(long sumTotalAmount) {
        this.sumTotalAmount = sumTotalAmount;
    }

    public long getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(long totalBalance) {
        this.totalBalance = totalBalance;
    }

    public long getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(long availableBalance) {
        this.availableBalance = availableBalance;
    }

    public long getFrozenAmount() {
        return frozenAmount;
    }

    public void setFrozenAmount(long frozenAmount) {
        this.frozenAmount = frozenAmount;
    }

    public long getLastChangeAmount() {
        return lastChangeAmount;
    }

    public void setLastChangeAmount(long lastChangeAmount) {
        this.lastChangeAmount = lastChangeAmount;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getAccountAdjustType() {
        return accountAdjustType;
    }

    public void setAccountAdjustType(String accountAdjustType) {
        this.accountAdjustType = accountAdjustType;
    }

    public long getAccountAdjustAmount() {
        return accountAdjustAmount;
    }

    public void setAccountAdjustAmount(long accountAdjustAmount) {
        this.accountAdjustAmount = accountAdjustAmount;
    }
   
}
