package com.dodopal.transfernew.business.model.transfer;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the PRD_PURCHASE_ORDER database table.
 * 
 */
//@Entity
//@Table(name="PRD_PURCHASE_ORDER")
//@NamedQuery(name="PrdPurchaseOrder.findAll", query="SELECT p FROM PrdPurchaseOrder p")
public class PrdPurchaseOrder implements Serializable {
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

//	@Column(name="BUSINESS_TYPE")
	private String businessType;

//	@Column(name="CLEARING_MARK")
	private String clearingMark;

	private String comments;

//	@Temporal(TemporalType.DATE)
//	@Column(name="CREATE_DATE")
	private Date createDate;

//	@Column(name="CREATE_USER")
	private String createUser;

//	@Column(name="CUSTOMER_CODE")
	private String customerCode;

//	@Column(name="CUSTOMER_NAME")
	private String customerName;

//	@Column(name="CUSTOMER_TYPE")
	private String customerType;

//	@Column(name="FUND_PROCESS_RESULT")
	private String fundProcessResult;

//	@Column(name="MER_GAIN")
	private BigDecimal merGain;

//	@Column(name="MER_RATE")
	private BigDecimal merRate;

//	@Column(name="MER_RATE_TYPE")
	private String merRateType;

//	@Temporal(TemporalType.DATE)
//	@Column(name="ORDER_DATE")
	private Date orderDate;

//	@Column(name="ORDER_DAY")
	private String orderDay;

//	@Column(name="ORDER_NUM")
	private String orderNum;

//	@Column(name="ORIGINAL_PRICE")
	private BigDecimal originalPrice;

//	@Column(name="PAY_GATEWAY")
	private String payGateway;

//	@Column(name="PAY_TYPE")
	private String payType;

//	@Column(name="PAY_WAY")
	private String payWay;

//	@Column(name="RECEIVED_PRICE")
	private BigDecimal receivedPrice;

//	@Column(name="SERVICE_RATE")
	private BigDecimal serviceRate;

//	@Column(name="SERVICE_RATE_TYPE")
	private String serviceRateType;

	private String source;

	private String states;

//	@Temporal(TemporalType.DATE)
//	@Column(name="UPDATE_DATE")
	private Date updateDate;

//	@Column(name="UPDATE_USER")
	private String updateUser;

//	@Column(name="USER_ID")
	private String userId;

	public PrdPurchaseOrder() {
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

	public String getBusinessType() {
		return this.businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getClearingMark() {
		return this.clearingMark;
	}

	public void setClearingMark(String clearingMark) {
		this.clearingMark = clearingMark;
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

	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerType() {
		return this.customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getFundProcessResult() {
		return this.fundProcessResult;
	}

	public void setFundProcessResult(String fundProcessResult) {
		this.fundProcessResult = fundProcessResult;
	}

	public BigDecimal getMerGain() {
		return this.merGain;
	}

	public void setMerGain(BigDecimal merGain) {
		this.merGain = merGain;
	}

	public BigDecimal getMerRate() {
		return this.merRate;
	}

	public void setMerRate(BigDecimal merRate) {
		this.merRate = merRate;
	}

	public String getMerRateType() {
		return this.merRateType;
	}

	public void setMerRateType(String merRateType) {
		this.merRateType = merRateType;
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

	public BigDecimal getOriginalPrice() {
		return this.originalPrice;
	}

	public void setOriginalPrice(BigDecimal originalPrice) {
		this.originalPrice = originalPrice;
	}

	public String getPayGateway() {
		return this.payGateway;
	}

	public void setPayGateway(String payGateway) {
		this.payGateway = payGateway;
	}

	public String getPayType() {
		return this.payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getPayWay() {
		return this.payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	public BigDecimal getReceivedPrice() {
		return this.receivedPrice;
	}

	public void setReceivedPrice(BigDecimal receivedPrice) {
		this.receivedPrice = receivedPrice;
	}

	public BigDecimal getServiceRate() {
		return this.serviceRate;
	}

	public void setServiceRate(BigDecimal serviceRate) {
		this.serviceRate = serviceRate;
	}

	public String getServiceRateType() {
		return this.serviceRateType;
	}

	public void setServiceRateType(String serviceRateType) {
		this.serviceRateType = serviceRateType;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getStates() {
		return this.states;
	}

	public void setStates(String states) {
		this.states = states;
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

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}