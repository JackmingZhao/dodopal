package com.dodopal.transfer.business.model.target;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the ACCOUNT_FUND database table.
 * 
 */
//@Entity
//@Table(name="ACCOUNT_FUND")
//@NamedQuery(name="AccountFund.findAll", query="SELECT a FROM AccountFund a")
public class AccountFund implements Serializable {
	private static final long serialVersionUID = 1L;

//	@Id
	private String id;

//	@Column(name="ACCOUNT_CODE")
	private String accountCode;

//	@Column(name="AVAILABLE_BALANCE")
	private BigDecimal availableBalance;

	private String bak1;

	private String bak10;

	private String bak2;

	private String bak3;

	private String bak4;

	private String bak5;

	private String bak6;

	private String bak7;

	private String bak8;

	private String bak9;

//	@Column(name="BEFORE_CHANGE_AVAILABLE_AMOUNT")
	private BigDecimal beforeChangeAvailableAmount;

//	@Column(name="BEFORE_CHANGE_FROZEN_AMOUNT")
	private BigDecimal beforeChangeFrozenAmount;

//	@Column(name="BEFORE_CHANGE_TOTAL_AMOUNT")
	private BigDecimal beforeChangeTotalAmount;

	private String ciphertext;

//	@Temporal(TemporalType.DATE)
//	@Column(name="CREATE_DATE")
	private Date createDate;

//	@Column(name="CREATE_USER")
	private String createUser;

//	@Column(name="FROZEN_AMOUNT")
	private BigDecimal frozenAmount;

//	@Column(name="FUND_ACCOUNT_CODE")
	private String fundAccountCode;

//	@Column(name="FUND_TYPE")
	private String fundType;

//	@Column(name="LAST_CHANGE_AMOUNT")
	private BigDecimal lastChangeAmount;

//	@Column(name="PAY_PASSWORD")
	private String payPassword;

//	@Column(name="\"STATE\"")
	private String state;

//	@Column(name="SUM_TOTAL_AMOUNT")
	private BigDecimal sumTotalAmount;

//	@Column(name="TOTAL_BALANCE")
	private BigDecimal totalBalance;

//	@Temporal(TemporalType.DATE)
//	@Column(name="UPDATE_DATE")
	private Date updateDate;

//	@Column(name="UPDATE_USER")
	private String updateUser;

	private String useable;

	public AccountFund() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccountCode() {
		return this.accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public BigDecimal getAvailableBalance() {
		return this.availableBalance;
	}

	public void setAvailableBalance(BigDecimal availableBalance) {
		this.availableBalance = availableBalance;
	}

	public String getBak1() {
		return this.bak1;
	}

	public void setBak1(String bak1) {
		this.bak1 = bak1;
	}

	public String getBak10() {
		return this.bak10;
	}

	public void setBak10(String bak10) {
		this.bak10 = bak10;
	}

	public String getBak2() {
		return this.bak2;
	}

	public void setBak2(String bak2) {
		this.bak2 = bak2;
	}

	public String getBak3() {
		return this.bak3;
	}

	public void setBak3(String bak3) {
		this.bak3 = bak3;
	}

	public String getBak4() {
		return this.bak4;
	}

	public void setBak4(String bak4) {
		this.bak4 = bak4;
	}

	public String getBak5() {
		return this.bak5;
	}

	public void setBak5(String bak5) {
		this.bak5 = bak5;
	}

	public String getBak6() {
		return this.bak6;
	}

	public void setBak6(String bak6) {
		this.bak6 = bak6;
	}

	public String getBak7() {
		return this.bak7;
	}

	public void setBak7(String bak7) {
		this.bak7 = bak7;
	}

	public String getBak8() {
		return this.bak8;
	}

	public void setBak8(String bak8) {
		this.bak8 = bak8;
	}

	public String getBak9() {
		return this.bak9;
	}

	public void setBak9(String bak9) {
		this.bak9 = bak9;
	}

	public BigDecimal getBeforeChangeAvailableAmount() {
		return this.beforeChangeAvailableAmount;
	}

	public void setBeforeChangeAvailableAmount(BigDecimal beforeChangeAvailableAmount) {
		this.beforeChangeAvailableAmount = beforeChangeAvailableAmount;
	}

	public BigDecimal getBeforeChangeFrozenAmount() {
		return this.beforeChangeFrozenAmount;
	}

	public void setBeforeChangeFrozenAmount(BigDecimal beforeChangeFrozenAmount) {
		this.beforeChangeFrozenAmount = beforeChangeFrozenAmount;
	}

	public BigDecimal getBeforeChangeTotalAmount() {
		return this.beforeChangeTotalAmount;
	}

	public void setBeforeChangeTotalAmount(BigDecimal beforeChangeTotalAmount) {
		this.beforeChangeTotalAmount = beforeChangeTotalAmount;
	}

	public String getCiphertext() {
		return this.ciphertext;
	}

	public void setCiphertext(String ciphertext) {
		this.ciphertext = ciphertext;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public BigDecimal getFrozenAmount() {
		return this.frozenAmount;
	}

	public void setFrozenAmount(BigDecimal frozenAmount) {
		this.frozenAmount = frozenAmount;
	}

	public String getFundAccountCode() {
		return this.fundAccountCode;
	}

	public void setFundAccountCode(String fundAccountCode) {
		this.fundAccountCode = fundAccountCode;
	}

	public String getFundType() {
		return this.fundType;
	}

	public void setFundType(String fundType) {
		this.fundType = fundType;
	}

	public BigDecimal getLastChangeAmount() {
		return this.lastChangeAmount;
	}

	public void setLastChangeAmount(BigDecimal lastChangeAmount) {
		this.lastChangeAmount = lastChangeAmount;
	}

	public String getPayPassword() {
		return this.payPassword;
	}

	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public BigDecimal getSumTotalAmount() {
		return this.sumTotalAmount;
	}

	public void setSumTotalAmount(BigDecimal sumTotalAmount) {
		this.sumTotalAmount = sumTotalAmount;
	}

	public BigDecimal getTotalBalance() {
		return this.totalBalance;
	}

	public void setTotalBalance(BigDecimal totalBalance) {
		this.totalBalance = totalBalance;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getUseable() {
		return this.useable;
	}

	public void setUseable(String useable) {
		this.useable = useable;
	}

}