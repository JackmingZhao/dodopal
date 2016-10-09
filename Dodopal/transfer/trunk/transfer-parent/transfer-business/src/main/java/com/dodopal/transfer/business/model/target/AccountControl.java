package com.dodopal.transfer.business.model.target;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the ACCOUNT_CONTROL database table.
 * 
 */
//@Entity
//@Table(name="ACCOUNT_CONTROL")
//@NamedQuery(name="AccountControl.findAll", query="SELECT a FROM AccountControl a")
public class AccountControl implements Serializable {
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

	private Date createDate;

	private String createUser;

	private BigDecimal dayConsumeSingleLimit;

	private BigDecimal dayConsumeSumLimit;

	private BigDecimal dayRechargeSingleLimit;

	private BigDecimal dayRechargeSumLimit;

	private BigDecimal dayTransferMax;

	private BigDecimal dayTransferSingleLimit;

	private BigDecimal dayTransferSumLimit;

	private String fundAccountCode;

	private Date updateDate;

	private String updateUser;

	public AccountControl() {
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

	public String getFundAccountCode() {
		return this.fundAccountCode;
	}

	public void setFundAccountCode(String fundAccountCode) {
		this.fundAccountCode = fundAccountCode;
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