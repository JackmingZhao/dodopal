package com.dodopal.transfer.business.model.target;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the PRD_PURCHASE_ORDER_RECORD database table.
 * 
 */
//@Entity
//@Table(name="PRD_PURCHASE_ORDER_RECORD")
//@NamedQuery(name="PrdPurchaseOrderRecord.findAll", query="SELECT p FROM PrdPurchaseOrderRecord p")
public class PrdPurchaseOrderRecord implements Serializable {
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

	private BigDecimal befbal;

//	@Column(name="BEFOREINNER_STATES")
	private String beforeinnerStates;

//	@Column(name="BLACK_AMT")
	private BigDecimal blackAmt;

//	@Column(name="CARD_FACENO")
	private String cardFaceno;

//	@Column(name="CARD_NUM")
	private String cardNum;

//	@Column(name="CITY_CODE")
	private String cityCode;

//	@Column(name="CITY_NAME")
	private String cityName;

//	@Temporal(TemporalType.DATE)
//	@Column(name="CREATE_DATE")
	private Date createDate;

//	@Column(name="CREATE_USER")
	private String createUser;

//	@Column(name="INNER_STATES")
	private String innerStates;

//	@Column(name="MER_DISCOUNT")
	private BigDecimal merDiscount;

//	@Temporal(TemporalType.DATE)
//	@Column(name="ORDER_DATE")
	private Date orderDate;

//	@Column(name="ORDER_DAY")
	private String orderDay;

//	@Column(name="ORDER_NUM")
	private String orderNum;

//	@Column(name="POS_CODE")
	private String posCode;

//	@Column(name="RESEND_SIGN")
	private String resendSign;

//	@Column(name="SUSPICIOUS_EXPLAIN")
	private String suspiciousExplain;

//	@Column(name="SUSPICIOUS_STATES")
	private String suspiciousStates;

//	@Temporal(TemporalType.DATE)
//	@Column(name="UPDATE_DATE")
	private Date updateDate;

//	@Column(name="UPDATE_USER")
	private String updateUser;

//	@Column(name="YKT_CODE")
	private String yktCode;

//	@Column(name="YKT_NAME")
	private String yktName;

//	@Column(name="YKT_PAY_RATE")
	private BigDecimal yktPayRate;

	public PrdPurchaseOrderRecord() {
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

	public BigDecimal getBefbal() {
		return this.befbal;
	}

	public void setBefbal(BigDecimal befbal) {
		this.befbal = befbal;
	}

	public String getBeforeinnerStates() {
		return this.beforeinnerStates;
	}

	public void setBeforeinnerStates(String beforeinnerStates) {
		this.beforeinnerStates = beforeinnerStates;
	}

	public BigDecimal getBlackAmt() {
		return this.blackAmt;
	}

	public void setBlackAmt(BigDecimal blackAmt) {
		this.blackAmt = blackAmt;
	}

	public String getCardFaceno() {
		return this.cardFaceno;
	}

	public void setCardFaceno(String cardFaceno) {
		this.cardFaceno = cardFaceno;
	}

	public String getCardNum() {
		return this.cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public String getCityCode() {
		return this.cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return this.cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
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

	public String getInnerStates() {
		return this.innerStates;
	}

	public void setInnerStates(String innerStates) {
		this.innerStates = innerStates;
	}

	public BigDecimal getMerDiscount() {
		return this.merDiscount;
	}

	public void setMerDiscount(BigDecimal merDiscount) {
		this.merDiscount = merDiscount;
	}

	public Date getOrderDate() {
		return this.orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderDay() {
		return this.orderDay;
	}

	public void setOrderDay(String orderDay) {
		this.orderDay = orderDay;
	}

	public String getOrderNum() {
		return this.orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getPosCode() {
		return this.posCode;
	}

	public void setPosCode(String posCode) {
		this.posCode = posCode;
	}

	public String getResendSign() {
		return this.resendSign;
	}

	public void setResendSign(String resendSign) {
		this.resendSign = resendSign;
	}

	public String getSuspiciousExplain() {
		return this.suspiciousExplain;
	}

	public void setSuspiciousExplain(String suspiciousExplain) {
		this.suspiciousExplain = suspiciousExplain;
	}

	public String getSuspiciousStates() {
		return this.suspiciousStates;
	}

	public void setSuspiciousStates(String suspiciousStates) {
		this.suspiciousStates = suspiciousStates;
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

	public String getYktCode() {
		return this.yktCode;
	}

	public void setYktCode(String yktCode) {
		this.yktCode = yktCode;
	}

	public String getYktName() {
		return this.yktName;
	}

	public void setYktName(String yktName) {
		this.yktName = yktName;
	}

	public BigDecimal getYktPayRate() {
		return this.yktPayRate;
	}

	public void setYktPayRate(BigDecimal yktPayRate) {
		this.yktPayRate = yktPayRate;
	}

}