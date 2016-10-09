package com.dodopal.transfernew.business.model.transfer;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the PAY_CONFIG database table.
 * 
 */
//@Entity
//@Table(name="PAY_CONFIG")
//@NamedQuery(name="PayConfig.findAll", query="SELECT p FROM PayConfig p")
public class PayConfig implements Serializable {
	private static final long serialVersionUID = 1L;

//	@Id
	private String id;

//	@Column(name="\"ACTIVATE\"")
	private String activate;

//	@Column(name="AFTER_PROCE_RATE")
	private BigDecimal afterProceRate;

//	@Temporal(TemporalType.DATE)
//	@Column(name="AFTER_PROCE_RATE_DATE")
	private Date afterProceRateDate;

//	@Column(name="ANOTHER_ACCOUNT_CODE")
	private String anotherAccountCode;

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

//	@Column(name="BANK_GATEWAY_NAME")
	private String bankGatewayName;

//	@Column(name="BANK_GATEWAY_TYPE")
	private String bankGatewayType;

//	@Temporal(TemporalType.DATE)
//	@Column(name="CREATE_DATE")
	private Date createDate;

//	@Column(name="CREATE_USER")
	private String createUser;

//	@Column(name="DEFAULT_BANK")
	private String defaultBank;

//	@Column(name="GATEWAY_NUMBER")
	private String gatewayNumber;

//	@Column(name="IMAGE_NAME")
	private String imageName;

//	@Column(name="PAY_CHANNEL_MARK")
	private String payChannelMark;

//	@Column(name="PAY_KEY")
	private String payKey;

//	@Column(name="PAY_TYPE")
	private String payType;

//	@Column(name="PAY_TYPE_NAME")
	private String payTypeName;

//	@Column(name="PAY_WAY_NAME")
	private String payWayName;

//	@Column(name="PROCE_RATE")
	private BigDecimal proceRate;

//	@Temporal(TemporalType.DATE)
//	@Column(name="UPDATE_DATE")
	private Date updateDate;

//	@Column(name="UPDATE_USER")
	private String updateUser;

	public PayConfig() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getActivate() {
		return this.activate;
	}

	public void setActivate(String activate) {
		this.activate = activate;
	}

	public BigDecimal getAfterProceRate() {
		return this.afterProceRate;
	}

	public void setAfterProceRate(BigDecimal afterProceRate) {
		this.afterProceRate = afterProceRate;
	}

	public Date getAfterProceRateDate() {
		return this.afterProceRateDate;
	}

	public void setAfterProceRateDate(Date afterProceRateDate) {
		this.afterProceRateDate = afterProceRateDate;
	}

	public String getAnotherAccountCode() {
		return this.anotherAccountCode;
	}

	public void setAnotherAccountCode(String anotherAccountCode) {
		this.anotherAccountCode = anotherAccountCode;
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

	public String getBankGatewayName() {
		return this.bankGatewayName;
	}

	public void setBankGatewayName(String bankGatewayName) {
		this.bankGatewayName = bankGatewayName;
	}

	public String getBankGatewayType() {
		return this.bankGatewayType;
	}

	public void setBankGatewayType(String bankGatewayType) {
		this.bankGatewayType = bankGatewayType;
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

	public String getDefaultBank() {
		return this.defaultBank;
	}

	public void setDefaultBank(String defaultBank) {
		this.defaultBank = defaultBank;
	}

	public String getGatewayNumber() {
		return this.gatewayNumber;
	}

	public void setGatewayNumber(String gatewayNumber) {
		this.gatewayNumber = gatewayNumber;
	}

	public String getImageName() {
		return this.imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getPayChannelMark() {
		return this.payChannelMark;
	}

	public void setPayChannelMark(String payChannelMark) {
		this.payChannelMark = payChannelMark;
	}

	public String getPayKey() {
		return this.payKey;
	}

	public void setPayKey(String payKey) {
		this.payKey = payKey;
	}

	public String getPayType() {
		return this.payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getPayTypeName() {
		return this.payTypeName;
	}

	public void setPayTypeName(String payTypeName) {
		this.payTypeName = payTypeName;
	}

	public String getPayWayName() {
		return this.payWayName;
	}

	public void setPayWayName(String payWayName) {
		this.payWayName = payWayName;
	}

	public BigDecimal getProceRate() {
		return this.proceRate;
	}

	public void setProceRate(BigDecimal proceRate) {
		this.proceRate = proceRate;
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