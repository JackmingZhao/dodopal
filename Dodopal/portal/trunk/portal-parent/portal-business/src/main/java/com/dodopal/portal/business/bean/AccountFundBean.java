package com.dodopal.portal.business.bean;

import com.dodopal.common.model.BaseEntity;

public class AccountFundBean extends BaseEntity{
    private static final long serialVersionUID = 8722915517330664976L;
    
    /**
     * 可用余额
     */
    private double availableBalance;
    
    /**
     * 冻结金额
     */
    private double frozenAmount;
    
    /**
     * 主账户编号
     */
    private String accountCode;
    
    /**
     * 资金类别
     */
    private String fundType;
    


    /**
     * 资金账户 可用余额
     */
    private double accountMoney;
    
    /**
     * 资金账户 冻结金额
     */
    private double accountFrozenAmount;

    /**
     *授信账户 可用余额    
     */
    private double accountFuntMoney ;
    
    /**
     *授信账户 冻结金额
     */
    private double accountFundFrozenAmount ;
    
    public double getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(double availableBalance) {
        this.availableBalance = availableBalance;
    }

    public double getFrozenAmount() {
        return frozenAmount;
    }

    public void setFrozenAmount(double frozenAmount) {
        this.frozenAmount = frozenAmount;
    }

    public double getAccountMoney() {
        return accountMoney;
    }

    public void setAccountMoney(double accountMoney) {
        this.accountMoney = accountMoney;
    }

    public double getAccountFuntMoney() {
        return accountFuntMoney;
    }

    public void setAccountFuntMoney(double accountFuntMoney) {
        this.accountFuntMoney = accountFuntMoney;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getFundType() {
        return fundType;
    }

    public void setFundType(String fundType) {
        this.fundType = fundType;
    }

    public double getAccountFrozenAmount() {
        return accountFrozenAmount;
    }

    public void setAccountFrozenAmount(double accountFrozenAmount) {
        this.accountFrozenAmount = accountFrozenAmount;
    }

    public double getAccountFundFrozenAmount() {
        return accountFundFrozenAmount;
    }

    public void setAccountFundFrozenAmount(double accountFundFrozenAmount) {
        this.accountFundFrozenAmount = accountFundFrozenAmount;
    }
    
    

}
