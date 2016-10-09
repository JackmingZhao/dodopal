package com.dodopal.transfer.business.model.target;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the ACCOUNT_CONTROLLER_DEFAULT database table.
 * 
 */
//@Entity
//@Table(name="ACCOUNT_CONTROLLER_DEFAULT")
//@NamedQuery(name="AccountControllerDefault.findAll", query="SELECT a FROM AccountControllerDefault a")
public class AccountControllerDefault implements Serializable {
	private static final long serialVersionUID = 1L;

//	@Id
	private String id;

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

//	@Temporal(TemporalType.DATE)
//	@Column(name="CREATE_DATE")
	private Date createDate;

//	@Column(name="CREATE_USER")
	private String createUser;

//	@Column(name="CUSTOMER_TYPE")
	private String customerType;

//	@Column(name="DAY_CONSUME_SINGLE_LIMIT")
	private BigDecimal dayConsumeSingleLimit;

//	@Column(name="DAY_CONSUME_SUM_LIMIT")
	private BigDecimal dayConsumeSumLimit;

//	@Column(name="DAY_RECHARGE_SINGLE_LIMIT")
	private BigDecimal dayRechargeSingleLimit;

//	@Column(name="DAY_RECHARGE_SUM_LIMIT")
	private BigDecimal dayRechargeSumLimit;

//	@Column(name="DAY_TRANSFER_MAX")
	private BigDecimal dayTransferMax;

//	@Column(name="DAY_TRANSFER_SINGLE_LIMIT")
	private BigDecimal dayTransferSingleLimit;

//	@Column(name="DAY_TRANSFER_SUM_LIMIT")
	private BigDecimal dayTransferSumLimit;

//	@Temporal(TemporalType.DATE)
//	@Column(name="UPDATE_DATE")
	private Date updateDate;

//	@Column(name="UPDATE_USER")
	private String updateUser;

	public AccountControllerDefault() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getCustomerType() {
		return this.customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public BigDecimal getDayConsumeSingleLimit() {
		return this.dayConsumeSingleLimit;
	}

	public void setDayConsumeSingleLimit(BigDecimal dayConsumeSingleLimit) {
		this.dayConsumeSingleLimit = dayConsumeSingleLimit;
	}

	public BigDecimal getDayConsumeSumLimit() {
		return this.dayConsumeSumLimit;
	}

	public void setDayConsumeSumLimit(BigDecimal dayConsumeSumLimit) {
		this.dayConsumeSumLimit = dayConsumeSumLimit;
	}

	public BigDecimal getDayRechargeSingleLimit() {
		return this.dayRechargeSingleLimit;
	}

	public void setDayRechargeSingleLimit(BigDecimal dayRechargeSingleLimit) {
		this.dayRechargeSingleLimit = dayRechargeSingleLimit;
	}

	public BigDecimal getDayRechargeSumLimit() {
		return this.dayRechargeSumLimit;
	}

	public void setDayRechargeSumLimit(BigDecimal dayRechargeSumLimit) {
		this.dayRechargeSumLimit = dayRechargeSumLimit;
	}

	public BigDecimal getDayTransferMax() {
		return this.dayTransferMax;
	}

	public void setDayTransferMax(BigDecimal dayTransferMax) {
		this.dayTransferMax = dayTransferMax;
	}

	public BigDecimal getDayTransferSingleLimit() {
		return this.dayTransferSingleLimit;
	}

	public void setDayTransferSingleLimit(BigDecimal dayTransferSingleLimit) {
		this.dayTransferSingleLimit = dayTransferSingleLimit;
	}

	public BigDecimal getDayTransferSumLimit() {
		return this.dayTransferSumLimit;
	}

	public void setDayTransferSumLimit(BigDecimal dayTransferSumLimit) {
		this.dayTransferSumLimit = dayTransferSumLimit;
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

}