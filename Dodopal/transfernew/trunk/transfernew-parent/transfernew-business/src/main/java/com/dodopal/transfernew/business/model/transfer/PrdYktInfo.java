package com.dodopal.transfernew.business.model.transfer;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the PRD_YKT_INFO database table.
 * 
 */
//@Entity
//@Table(name="PRD_YKT_INFO")
//@NamedQuery(name="PrdYktInfo.findAll", query="SELECT p FROM PrdYktInfo p")
public class PrdYktInfo implements Serializable {
	private static final long serialVersionUID = 1L;

//	@Id
	private String id;

//	@Column(name="\"ACTIVATE\"")
	private String activate;

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

	private String cityid;

//	@Temporal(TemporalType.DATE)
//	@Column(name="CREATE_DATE")
	private Date createDate;

//	@Column(name="CREATE_USER")
	private String createUser;

//	@Column(name="MER_CODE")
	private String merCode;

	private String provinceid;

	private String remark;

//	@Temporal(TemporalType.DATE)
//	@Column(name="UPDATE_DATE")
	private Date updateDate;

//	@Column(name="UPDATE_USER")
	private String updateUser;

//	@Column(name="YKT_ADDRESS")
	private String yktAddress;

//	@Column(name="YKT_CARD_MAX_LIMIT")
	private BigDecimal yktCardMaxLimit;

//	@Column(name="YKT_CODE")
	private String yktCode;

//	@Column(name="YKT_CONSUME_LIMIT_END_TIME")
	private String yktConsumeLimitEndTime;

//	@Column(name="YKT_CONSUME_LIMIT_START_TIME")
	private String yktConsumeLimitStartTime;

//	@Column(name="YKT_IPADDRESS")
	private String yktIpaddress;

//	@Column(name="YKT_IS_PAY")
	private String yktIsPay;

//	@Column(name="YKT_IS_RECHARGE")
	private String yktIsRecharge;

//	@Column(name="YKT_LINK_USER")
	private String yktLinkUser;

//	@Column(name="YKT_MOBILPHONE")
	private String yktMobilphone;

//	@Column(name="YKT_NAME")
	private String yktName;

//	@Column(name="YKT_PAY_FLAG")
	private String yktPayFlag;

//	@Column(name="YKT_PAY_RATE")
	private BigDecimal yktPayRate;

//	@Column(name="YKT_PAY_SETPARA")
	private BigDecimal yktPaySetpara;

//	@Column(name="YKT_PAY_SETTYPE")
	private String yktPaySettype;

//	@Column(name="YKT_PORT")
	private BigDecimal yktPort;

//	@Column(name="YKT_RECHARGE_LIMIT_END_TIME")
	private String yktRechargeLimitEndTime;

//	@Column(name="YKT_RECHARGE_LIMIT_START_TIME")
	private String yktRechargeLimitStartTime;

//	@Column(name="YKT_RECHARGE_RATE")
	private BigDecimal yktRechargeRate;

//	@Column(name="YKT_RECHARGE_SETPARA")
	private BigDecimal yktRechargeSetpara;

//	@Column(name="YKT_RECHARGE_SETTYPE")
	private String yktRechargeSettype;

//	@Column(name="YKT_TELEPHONE")
	private String yktTelephone;

	public PrdYktInfo() {
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

	public String getCityid() {
		return this.cityid;
	}

	public void setCityid(String cityid) {
		this.cityid = cityid;
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

	public String getMerCode() {
		return this.merCode;
	}

	public void setMerCode(String merCode) {
		this.merCode = merCode;
	}

	public String getProvinceid() {
		return this.provinceid;
	}

	public void setProvinceid(String provinceid) {
		this.provinceid = provinceid;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getYktAddress() {
		return this.yktAddress;
	}

	public void setYktAddress(String yktAddress) {
		this.yktAddress = yktAddress;
	}

	public BigDecimal getYktCardMaxLimit() {
		return this.yktCardMaxLimit;
	}

	public void setYktCardMaxLimit(BigDecimal yktCardMaxLimit) {
		this.yktCardMaxLimit = yktCardMaxLimit;
	}

	public String getYktCode() {
		return this.yktCode;
	}

	public void setYktCode(String yktCode) {
		this.yktCode = yktCode;
	}

	public String getYktConsumeLimitEndTime() {
		return this.yktConsumeLimitEndTime;
	}

	public void setYktConsumeLimitEndTime(String yktConsumeLimitEndTime) {
		this.yktConsumeLimitEndTime = yktConsumeLimitEndTime;
	}

	public String getYktConsumeLimitStartTime() {
		return this.yktConsumeLimitStartTime;
	}

	public void setYktConsumeLimitStartTime(String yktConsumeLimitStartTime) {
		this.yktConsumeLimitStartTime = yktConsumeLimitStartTime;
	}

	public String getYktIpaddress() {
		return this.yktIpaddress;
	}

	public void setYktIpaddress(String yktIpaddress) {
		this.yktIpaddress = yktIpaddress;
	}

	public String getYktIsPay() {
		return this.yktIsPay;
	}

	public void setYktIsPay(String yktIsPay) {
		this.yktIsPay = yktIsPay;
	}

	public String getYktIsRecharge() {
		return this.yktIsRecharge;
	}

	public void setYktIsRecharge(String yktIsRecharge) {
		this.yktIsRecharge = yktIsRecharge;
	}

	public String getYktLinkUser() {
		return this.yktLinkUser;
	}

	public void setYktLinkUser(String yktLinkUser) {
		this.yktLinkUser = yktLinkUser;
	}

	public String getYktMobilphone() {
		return this.yktMobilphone;
	}

	public void setYktMobilphone(String yktMobilphone) {
		this.yktMobilphone = yktMobilphone;
	}

	public String getYktName() {
		return this.yktName;
	}

	public void setYktName(String yktName) {
		this.yktName = yktName;
	}

	public String getYktPayFlag() {
		return this.yktPayFlag;
	}

	public void setYktPayFlag(String yktPayFlag) {
		this.yktPayFlag = yktPayFlag;
	}

	public BigDecimal getYktPayRate() {
		return this.yktPayRate;
	}

	public void setYktPayRate(BigDecimal yktPayRate) {
		this.yktPayRate = yktPayRate;
	}

	public BigDecimal getYktPaySetpara() {
		return this.yktPaySetpara;
	}

	public void setYktPaySetpara(BigDecimal yktPaySetpara) {
		this.yktPaySetpara = yktPaySetpara;
	}

	public String getYktPaySettype() {
		return this.yktPaySettype;
	}

	public void setYktPaySettype(String yktPaySettype) {
		this.yktPaySettype = yktPaySettype;
	}

	public BigDecimal getYktPort() {
		return this.yktPort;
	}

	public void setYktPort(BigDecimal yktPort) {
		this.yktPort = yktPort;
	}

	public String getYktRechargeLimitEndTime() {
		return this.yktRechargeLimitEndTime;
	}

	public void setYktRechargeLimitEndTime(String yktRechargeLimitEndTime) {
		this.yktRechargeLimitEndTime = yktRechargeLimitEndTime;
	}

	public String getYktRechargeLimitStartTime() {
		return this.yktRechargeLimitStartTime;
	}

	public void setYktRechargeLimitStartTime(String yktRechargeLimitStartTime) {
		this.yktRechargeLimitStartTime = yktRechargeLimitStartTime;
	}

	public BigDecimal getYktRechargeRate() {
		return this.yktRechargeRate;
	}

	public void setYktRechargeRate(BigDecimal yktRechargeRate) {
		this.yktRechargeRate = yktRechargeRate;
	}

	public BigDecimal getYktRechargeSetpara() {
		return this.yktRechargeSetpara;
	}

	public void setYktRechargeSetpara(BigDecimal yktRechargeSetpara) {
		this.yktRechargeSetpara = yktRechargeSetpara;
	}

	public String getYktRechargeSettype() {
		return this.yktRechargeSettype;
	}

	public void setYktRechargeSettype(String yktRechargeSettype) {
		this.yktRechargeSettype = yktRechargeSettype;
	}

	public String getYktTelephone() {
		return this.yktTelephone;
	}

	public void setYktTelephone(String yktTelephone) {
		this.yktTelephone = yktTelephone;
	}

}