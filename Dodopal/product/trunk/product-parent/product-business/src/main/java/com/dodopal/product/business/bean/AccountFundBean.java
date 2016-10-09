package com.dodopal.product.business.bean;

import com.dodopal.common.model.BaseEntity;

public class AccountFundBean extends BaseEntity{
	private static final long serialVersionUID = -2206232198844913757L;

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
     *授信账户可用余额    
     */
    private double accountFuntMoney ;
    
    
    /**
     * 资金账户 冻结金额
     */
    private double accountFrozenAmount;
    
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
