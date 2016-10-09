package com.dodopal.transfer.business.model.target;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the PROFIT_DETAILS database table.
 * 
 */
//@Entity
//@Table(name="PROFIT_DETAILS")
//@NamedQuery(name="ProfitDetail.findAll", query="SELECT p FROM ProfitDetail p")
public class ProfitDetail implements Serializable {
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

//	@Column(name="BUSSINESS_TYPE")
	private String bussinessType;

//	@Column(name="CUSTOMER_CODE")
	private String customerCode;

//	@Column(name="CUSTOMER_NAME")
	private String customerName;

//	@Column(name="CUSTOMER_RATE")
	private BigDecimal customerRate;

//	@Column(name="CUSTOMER_RATE_TYPE")
	private String customerRateType;

//	@Column(name="CUSTOMER_REAL_PROFIT")
	private BigDecimal customerRealProfit;

//	@Temporal(TemporalType.DATE)
//	@Column(name="CUSTOMER_SETTLEMENT_TIME")
	private Date customerSettlementTime;

//	@Column(name="CUSTOMER_SHOULD_PROFIT")
	private BigDecimal customerShouldProfit;

//	@Column(name="CUSTOMER_TYPE")
	private String customerType;

	private String iscircle;

//	@Column(name="ORDER_AMOUNT")
	private BigDecimal orderAmount;

//	@Column(name="ORDER_NO")
	private String orderNo;

//	@Temporal(TemporalType.DATE)
//	@Column(name="ORDER_TIME")
	private Date orderTime;

//	@Column(name="PROFIT_DATE")
	private String profitDate;

	private String source;

//	@Column(name="SUB_CUSTOMER_CODE")
	private String subCustomerCode;

//	@Column(name="SUB_CUSTOMER_NAME")
	private String subCustomerName;

//	@Column(name="SUB_CUSTOMER_SHOULD_PROFIT")
	private BigDecimal subCustomerShouldProfit;

	public ProfitDetail() {
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

	public String getBussinessType() {
		return this.bussinessType;
	}

	public void setBussinessType(String bussinessType) {
		this.bussinessType = bussinessType;
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

	public BigDecimal getCustomerRate() {
		return this.customerRate;
	}

	public void setCustomerRate(BigDecimal customerRate) {
		this.customerRate = customerRate;
	}

	public String getCustomerRateType() {
		return this.customerRateType;
	}

	public void setCustomerRateType(String customerRateType) {
		this.customerRateType = customerRateType;
	}

	public BigDecimal getCustomerRealProfit() {
		return this.customerRealProfit;
	}

	public void setCustomerRealProfit(BigDecimal customerRealProfit) {
		this.customerRealProfit = customerRealProfit;
	}

	public Date getCustomerSettlementTime() {
		return this.customerSettlementTime;
	}

	public void setCustomerSettlementTime(Date customerSettlementTime) {
		this.customerSettlementTime = customerSettlementTime;
	}

	public BigDecimal getCustomerShouldProfit() {
		return this.customerShouldProfit;
	}

	public void setCustomerShouldProfit(BigDecimal customerShouldProfit) {
		this.customerShouldProfit = customerShouldProfit;
	}

	public String getCustomerType() {
		return this.customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getIscircle() {
		return this.iscircle;
	}

	public void setIscircle(String iscircle) {
		this.iscircle = iscircle;
	}

	public BigDecimal getOrderAmount() {
		return this.orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Date getOrderTime() {
		return this.orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public String getProfitDate() {
		return this.profitDate;
	}

	public void setProfitDate(String profitDate) {
		this.profitDate = profitDate;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSubCustomerCode() {
		return this.subCustomerCode;
	}

	public void setSubCustomerCode(String subCustomerCode) {
		this.subCustomerCode = subCustomerCode;
	}

	public String getSubCustomerName() {
		return this.subCustomerName;
	}

	public void setSubCustomerName(String subCustomerName) {
		this.subCustomerName = subCustomerName;
	}

	public BigDecimal getSubCustomerShouldProfit() {
		return this.subCustomerShouldProfit;
	}

	public void setSubCustomerShouldProfit(BigDecimal subCustomerShouldProfit) {
		this.subCustomerShouldProfit = subCustomerShouldProfit;
	}

}