package com.dodopal.transfernew.business.model.transfer;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the MER_DDP_INFO database table.
 * 
 */
//@Entity
//@Table(name="MER_DDP_INFO")
//@NamedQuery(name="MerDdpInfo.findAll", query="SELECT m FROM MerDdpInfo m")
public class MerDdpInfo implements Serializable {
	private static final long serialVersionUID = 1L;

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

//	@Temporal(TemporalType.DATE)
//	@Column(name="CREATE_DATE")
	private Date createDate;

//	@Column(name="CREATE_USER")
	private String createUser;

	private String id;

//	@Column(name="IS_AUTO_DISTRIBUTE")
	private String isAutoDistribute;

//	@Column(name="LIMIT_SOURCE")
	private String limitSource;

//	@Column(name="MER_CODE")
	private String merCode;

//	@Column(name="MER_DDP_LINK_IP")
	private String merDdpLinkIp;

//	@Column(name="MER_DDP_LINK_USER")
	private String merDdpLinkUser;

//	@Column(name="MER_DDP_LINK_USER_MOBILE")
	private String merDdpLinkUserMobile;

//	@Column(name="SETTLEMENT_THRESHOLD")
	private BigDecimal settlementThreshold;

//	@Column(name="SETTLEMENT_TYPE")
	private String settlementType;

//	@Column(name="TRADE_AREA")
	private String tradeArea;

//	@Temporal(TemporalType.DATE)
//	@Column(name="UPDATE_DATE")
	private Date updateDate;

//	@Column(name="UPDATE_USER")
	private String updateUser;

	public MerDdpInfo() {
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

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIsAutoDistribute() {
		return this.isAutoDistribute;
	}

	public void setIsAutoDistribute(String isAutoDistribute) {
		this.isAutoDistribute = isAutoDistribute;
	}

	public String getLimitSource() {
		return this.limitSource;
	}

	public void setLimitSource(String limitSource) {
		this.limitSource = limitSource;
	}

	public String getMerCode() {
		return this.merCode;
	}

	public void setMerCode(String merCode) {
		this.merCode = merCode;
	}

	public String getMerDdpLinkIp() {
		return this.merDdpLinkIp;
	}

	public void setMerDdpLinkIp(String merDdpLinkIp) {
		this.merDdpLinkIp = merDdpLinkIp;
	}

	public String getMerDdpLinkUser() {
		return this.merDdpLinkUser;
	}

	public void setMerDdpLinkUser(String merDdpLinkUser) {
		this.merDdpLinkUser = merDdpLinkUser;
	}

	public String getMerDdpLinkUserMobile() {
		return this.merDdpLinkUserMobile;
	}

	public void setMerDdpLinkUserMobile(String merDdpLinkUserMobile) {
		this.merDdpLinkUserMobile = merDdpLinkUserMobile;
	}

	public BigDecimal getSettlementThreshold() {
		return this.settlementThreshold;
	}

	public void setSettlementThreshold(BigDecimal settlementThreshold) {
		this.settlementThreshold = settlementThreshold;
	}

	public String getSettlementType() {
		return this.settlementType;
	}

	public void setSettlementType(String settlementType) {
		this.settlementType = settlementType;
	}

	public String getTradeArea() {
		return this.tradeArea;
	}

	public void setTradeArea(String tradeArea) {
		this.tradeArea = tradeArea;
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