package com.dodopal.transfernew.business.model.transfer;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the PRD_LOAD_ORDER database table.
 * 
 */
//@Entity
//@Table(name="PRD_LOAD_ORDER")
//@NamedQuery(name="PrdLoadOrder.findAll", query="SELECT p FROM PrdLoadOrder p")
public class PrdLoadOrder implements Serializable {
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

//	@Column(name="CARD_FACENUM")
	private String cardFacenum;

//	@Column(name="CITY_CODE")
	private String cityCode;

//	@Column(name="CITY_NAME")
	private String cityName;

	private String comments;

//	@Temporal(TemporalType.DATE)
//	@Column(name="CREATE_DATE")
	private Date createDate;

//	@Column(name="CREATE_USER")
	private String createUser;

//	@Column(name="CUSTOMER_CODE")
	private String customerCode;

//	@Column(name="CUSTOMER_GAIN")
	private BigDecimal customerGain;

//	@Column(name="CUSTOMER_NAME")
	private String customerName;

//	@Column(name="CUSTOMER_PAY_AMT")
	private BigDecimal customerPayAmt;

//	@Column(name="CUSTOMER_TYPE")
	private String customerType;

//	@Column(name="EXCHANGED_CARD_NUM")
	private String exchangedCardNum;

//	@Column(name="EXTMER_ORDER_NUM")
	private String extmerOrderNum;

//	@Column(name="EXTMER_ORDER_TIME")
	private String extmerOrderTime;

//	@Column(name="LOAD_AMT")
	private BigDecimal loadAmt;

//	@Column(name="LOAD_RATE")
	private BigDecimal loadRate;

//	@Column(name="LOAD_RATE_TYPE")
	private String loadRateType;

//	@Column(name="ORDER_NUM")
	private String orderNum;

//	@Temporal(TemporalType.DATE)
//	@Column(name="ORDER_TIME")
	private Date orderTime;

//	@Column(name="PAY_SERVICE_RATE")
	private BigDecimal payServiceRate;

//	@Column(name="PAY_SERVICE_TYPE")
	private String payServiceType;

//	@Column(name="PAY_TYPE")
	private String payType;

//	@Column(name="PAYWAY_ID")
	private String paywayId;

//	@Column(name="PAYWAY_NAME")
	private String paywayName;

//	@Column(name="PRODUCT_CODE")
	private String productCode;

//	@Column(name="PRODUCT_NAME")
	private String productName;

	private String source;

	private String status;

//	@Temporal(TemporalType.DATE)
//	@Column(name="UPDATE_DATE")
	private Date updateDate;

//	@Column(name="UPDATE_USER")
	private String updateUser;

//	@Column(name="YKT_CODE")
	private String yktCode;

//	@Column(name="YKT_NAME")
	private String yktName;

	public PrdLoadOrder() {
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

	public String getCardFacenum() {
		return this.cardFacenum;
	}

	public void setCardFacenum(String cardFacenum) {
		this.cardFacenum = cardFacenum;
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

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
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

	public String getCustomerCode() {
		return this.customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public BigDecimal getCustomerGain() {
		return this.customerGain;
	}

	public void setCustomerGain(BigDecimal customerGain) {
		this.customerGain = customerGain;
	}

	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public BigDecimal getCustomerPayAmt() {
		return this.customerPayAmt;
	}

	public void setCustomerPayAmt(BigDecimal customerPayAmt) {
		this.customerPayAmt = customerPayAmt;
	}

	public String getCustomerType() {
		return this.customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getExchangedCardNum() {
		return this.exchangedCardNum;
	}

	public void setExchangedCardNum(String exchangedCardNum) {
		this.exchangedCardNum = exchangedCardNum;
	}

	public String getExtmerOrderNum() {
		return this.extmerOrderNum;
	}

	public void setExtmerOrderNum(String extmerOrderNum) {
		this.extmerOrderNum = extmerOrderNum;
	}

	public String getExtmerOrderTime() {
		return this.extmerOrderTime;
	}

	public void setExtmerOrderTime(String extmerOrderTime) {
		this.extmerOrderTime = extmerOrderTime;
	}

	public BigDecimal getLoadAmt() {
		return this.loadAmt;
	}

	public void setLoadAmt(BigDecimal loadAmt) {
		this.loadAmt = loadAmt;
	}

	public BigDecimal getLoadRate() {
		return this.loadRate;
	}

	public void setLoadRate(BigDecimal loadRate) {
		this.loadRate = loadRate;
	}

	public String getLoadRateType() {
		return this.loadRateType;
	}

	public void setLoadRateType(String loadRateType) {
		this.loadRateType = loadRateType;
	}

	public String getOrderNum() {
		return this.orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public Date getOrderTime() {
		return this.orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public BigDecimal getPayServiceRate() {
		return this.payServiceRate;
	}

	public void setPayServiceRate(BigDecimal payServiceRate) {
		this.payServiceRate = payServiceRate;
	}

	public String getPayServiceType() {
		return this.payServiceType;
	}

	public void setPayServiceType(String payServiceType) {
		this.payServiceType = payServiceType;
	}

	public String getPayType() {
		return this.payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getPaywayId() {
		return this.paywayId;
	}

	public void setPaywayId(String paywayId) {
		this.paywayId = paywayId;
	}

	public String getPaywayName() {
		return this.paywayName;
	}

	public void setPaywayName(String paywayName) {
		this.paywayName = paywayName;
	}

	public String getProductCode() {
		return this.productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
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

}