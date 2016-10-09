package com.dodopal.transfer.business.model.target;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the CLEARING_BASIC_DATA database table.
 * 
 */
//@Entity
//@Table(name="CLEARING_BASIC_DATA")
//@NamedQuery(name="ClearingBasicData.findAll", query="SELECT c FROM ClearingBasicData c")
public class ClearingBasicData implements Serializable {
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

//	@Column(name="BANK_CLEARING_FLAG")
	private String bankClearingFlag;

//	@Temporal(TemporalType.DATE)
//	@Column(name="BANK_CLEARING_TIME")
	private Date bankClearingTime;

//	@Column(name="BANK_SETTLEMENT_FLAG")
	private String bankSettlementFlag;

//	@Temporal(TemporalType.DATE)
//	@Column(name="BANK_SETTLEMENT_TIME")
	private Date bankSettlementTime;

//	@Column(name="BUSINESS_TYPE")
	private String businessType;

//	@Column(name="CITY_CODE")
	private String cityCode;

//	@Column(name="CITY_NAME")
	private String cityName;

//	@Column(name="CUSTOER_REAL_PAY_AMOUNT")
	private BigDecimal custoerRealPayAmount;

//	@Column(name="CUSTOMER_ACCOUNT_REAL_AMOUNT")
	private BigDecimal customerAccountRealAmount;

//	@Column(name="CUSTOMER_ACCOUNT_SHOULD_AMOUNT")
	private BigDecimal customerAccountShouldAmount;

//	@Column(name="CUSTOMER_CLEARING_FLAG")
	private String customerClearingFlag;

//	@Temporal(TemporalType.DATE)
//	@Column(name="CUSTOMER_CLEARING_TIME")
	private Date customerClearingTime;

//	@Column(name="CUSTOMER_NAME")
	private String customerName;

//	@Column(name="CUSTOMER_NO")
	private String customerNo;

//	@Column(name="CUSTOMER_RATE")
	private BigDecimal customerRate;

//	@Column(name="CUSTOMER_RATE_TYPE")
	private String customerRateType;

//	@Column(name="CUSTOMER_REAL_PROFIT")
	private BigDecimal customerRealProfit;

//	@Column(name="CUSTOMER_SETTLEMENT_FLAG")
	private String customerSettlementFlag;

//	@Temporal(TemporalType.DATE)
//	@Column(name="CUSTOMER_SETTLEMENT_TIME")
	private Date customerSettlementTime;

//	@Column(name="CUSTOMER_SHOULD_PROFIT")
	private BigDecimal customerShouldProfit;

//	@Column(name="CUSTOMER_TYPE")
	private String customerType;

//	@Column(name="DATA_FLAG")
	private String dataFlag;

//	@Column(name="DDP_BANK_RATE")
	private BigDecimal ddpBankRate;

//	@Column(name="DDP_FROM_BANK_REAL_FEE")
	private BigDecimal ddpFromBankRealFee;

//	@Column(name="DDP_FROM_BANK_SHOULD_FEE")
	private BigDecimal ddpFromBankShouldFee;

//	@Column(name="DDP_GET_MERCHANT_PAY_FEE")
	private BigDecimal ddpGetMerchantPayFee;

//	@Column(name="DDP_SUPPLIER_RATE")
	private BigDecimal ddpSupplierRate;

//	@Column(name="DDP_TO_BANK_FEE")
	private BigDecimal ddpToBankFee;

//	@Column(name="DDP_TO_CUSTOMER_REAL_FEE")
	private BigDecimal ddpToCustomerRealFee;

//	@Column(name="DDP_TO_SUPPLIER_REAL_AMOUNT")
	private BigDecimal ddpToSupplierRealAmount;

//	@Column(name="DDP_TO_SUPPLIER_SHOULD_AMOUNT")
	private BigDecimal ddpToSupplierShouldAmount;

//	@Column(name="ORDER_AMOUNT")
	private BigDecimal orderAmount;

//	@Column(name="ORDER_CIRCLE")
	private String orderCircle;

//	@Temporal(TemporalType.DATE)
//	@Column(name="ORDER_DATE")
	private Date orderDate;

//	@Column(name="ORDER_DAY")
	private String orderDay;

//	@Column(name="ORDER_FROM")
	private String orderFrom;

//	@Column(name="ORDER_NO")
	private String orderNo;

//	@Column(name="PAY_GATEWAY")
	private String payGateway;

//	@Column(name="PAY_TYPE")
	private String payType;

//	@Column(name="PAY_WAY")
	private String payWay;

//	@Column(name="PAY_WAY_NAME")
	private String payWayName;

//	@Column(name="SERVICE_RATE")
	private BigDecimal serviceRate;

//	@Column(name="SERVICE_RATE_TYPE")
	private String serviceRateType;

//	@Column(name="SUB_MERCHANT_CODE")
	private String subMerchantCode;

//	@Column(name="SUB_MERCHANT_NAME")
	private String subMerchantName;

//	@Column(name="SUB_MERCHANT_SHOULD_PROFIT")
	private BigDecimal subMerchantShouldProfit;

//	@Column(name="SUPPLIER_CLEARING_FLAG")
	private String supplierClearingFlag;

//	@Temporal(TemporalType.DATE)
//	@Column(name="SUPPLIER_CLEARING_TIME")
	private Date supplierClearingTime;

//	@Column(name="SUPPLIER_CODE")
	private String supplierCode;

//	@Column(name="SUPPLIER_NAME")
	private String supplierName;

//	@Column(name="SUPPLIER_SETTLEMENT_FLAG")
	private String supplierSettlementFlag;

//	@Temporal(TemporalType.DATE)
//	@Column(name="SUPPLIER_SETTLEMENT_TIME")
	private Date supplierSettlementTime;

//	@Column(name="SUPPLIER_TO_DDP_REAL_REBATE")
	private BigDecimal supplierToDdpRealRebate;

//	@Column(name="SUPPLIER_TO_DDP_SHOULD_REBATE")
	private BigDecimal supplierToDdpShouldRebate;

//	@Column(name="TOP_MERCHANT_PROFIT_FLAG")
	private String topMerchantProfitFlag;

	public ClearingBasicData() {
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

	public String getBankClearingFlag() {
		return this.bankClearingFlag;
	}

	public void setBankClearingFlag(String bankClearingFlag) {
		this.bankClearingFlag = bankClearingFlag;
	}

	public Date getBankClearingTime() {
		return this.bankClearingTime;
	}

	public void setBankClearingTime(Date bankClearingTime) {
		this.bankClearingTime = bankClearingTime;
	}

	public String getBankSettlementFlag() {
		return this.bankSettlementFlag;
	}

	public void setBankSettlementFlag(String bankSettlementFlag) {
		this.bankSettlementFlag = bankSettlementFlag;
	}

	public Date getBankSettlementTime() {
		return this.bankSettlementTime;
	}

	public void setBankSettlementTime(Date bankSettlementTime) {
		this.bankSettlementTime = bankSettlementTime;
	}

	public String getBusinessType() {
		return this.businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
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

	public BigDecimal getCustoerRealPayAmount() {
		return this.custoerRealPayAmount;
	}

	public void setCustoerRealPayAmount(BigDecimal custoerRealPayAmount) {
		this.custoerRealPayAmount = custoerRealPayAmount;
	}

	public BigDecimal getCustomerAccountRealAmount() {
		return this.customerAccountRealAmount;
	}

	public void setCustomerAccountRealAmount(BigDecimal customerAccountRealAmount) {
		this.customerAccountRealAmount = customerAccountRealAmount;
	}

	public BigDecimal getCustomerAccountShouldAmount() {
		return this.customerAccountShouldAmount;
	}

	public void setCustomerAccountShouldAmount(BigDecimal customerAccountShouldAmount) {
		this.customerAccountShouldAmount = customerAccountShouldAmount;
	}

	public String getCustomerClearingFlag() {
		return this.customerClearingFlag;
	}

	public void setCustomerClearingFlag(String customerClearingFlag) {
		this.customerClearingFlag = customerClearingFlag;
	}

	public Date getCustomerClearingTime() {
		return this.customerClearingTime;
	}

	public void setCustomerClearingTime(Date customerClearingTime) {
		this.customerClearingTime = customerClearingTime;
	}

	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerNo() {
		return this.customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
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

	public String getCustomerSettlementFlag() {
		return this.customerSettlementFlag;
	}

	public void setCustomerSettlementFlag(String customerSettlementFlag) {
		this.customerSettlementFlag = customerSettlementFlag;
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

	public String getDataFlag() {
		return this.dataFlag;
	}

	public void setDataFlag(String dataFlag) {
		this.dataFlag = dataFlag;
	}

	public BigDecimal getDdpBankRate() {
		return this.ddpBankRate;
	}

	public void setDdpBankRate(BigDecimal ddpBankRate) {
		this.ddpBankRate = ddpBankRate;
	}

	public BigDecimal getDdpFromBankRealFee() {
		return this.ddpFromBankRealFee;
	}

	public void setDdpFromBankRealFee(BigDecimal ddpFromBankRealFee) {
		this.ddpFromBankRealFee = ddpFromBankRealFee;
	}

	public BigDecimal getDdpFromBankShouldFee() {
		return this.ddpFromBankShouldFee;
	}

	public void setDdpFromBankShouldFee(BigDecimal ddpFromBankShouldFee) {
		this.ddpFromBankShouldFee = ddpFromBankShouldFee;
	}

	public BigDecimal getDdpGetMerchantPayFee() {
		return this.ddpGetMerchantPayFee;
	}

	public void setDdpGetMerchantPayFee(BigDecimal ddpGetMerchantPayFee) {
		this.ddpGetMerchantPayFee = ddpGetMerchantPayFee;
	}

	public BigDecimal getDdpSupplierRate() {
		return this.ddpSupplierRate;
	}

	public void setDdpSupplierRate(BigDecimal ddpSupplierRate) {
		this.ddpSupplierRate = ddpSupplierRate;
	}

	public BigDecimal getDdpToBankFee() {
		return this.ddpToBankFee;
	}

	public void setDdpToBankFee(BigDecimal ddpToBankFee) {
		this.ddpToBankFee = ddpToBankFee;
	}

	public BigDecimal getDdpToCustomerRealFee() {
		return this.ddpToCustomerRealFee;
	}

	public void setDdpToCustomerRealFee(BigDecimal ddpToCustomerRealFee) {
		this.ddpToCustomerRealFee = ddpToCustomerRealFee;
	}

	public BigDecimal getDdpToSupplierRealAmount() {
		return this.ddpToSupplierRealAmount;
	}

	public void setDdpToSupplierRealAmount(BigDecimal ddpToSupplierRealAmount) {
		this.ddpToSupplierRealAmount = ddpToSupplierRealAmount;
	}

	public BigDecimal getDdpToSupplierShouldAmount() {
		return this.ddpToSupplierShouldAmount;
	}

	public void setDdpToSupplierShouldAmount(BigDecimal ddpToSupplierShouldAmount) {
		this.ddpToSupplierShouldAmount = ddpToSupplierShouldAmount;
	}

	public BigDecimal getOrderAmount() {
		return this.orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getOrderCircle() {
		return this.orderCircle;
	}

	public void setOrderCircle(String orderCircle) {
		this.orderCircle = orderCircle;
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

	public String getOrderFrom() {
		return this.orderFrom;
	}

	public void setOrderFrom(String orderFrom) {
		this.orderFrom = orderFrom;
	}

	public String getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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

	public String getPayWayName() {
		return this.payWayName;
	}

	public void setPayWayName(String payWayName) {
		this.payWayName = payWayName;
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

	public String getSubMerchantCode() {
		return this.subMerchantCode;
	}

	public void setSubMerchantCode(String subMerchantCode) {
		this.subMerchantCode = subMerchantCode;
	}

	public String getSubMerchantName() {
		return this.subMerchantName;
	}

	public void setSubMerchantName(String subMerchantName) {
		this.subMerchantName = subMerchantName;
	}

	public BigDecimal getSubMerchantShouldProfit() {
		return this.subMerchantShouldProfit;
	}

	public void setSubMerchantShouldProfit(BigDecimal subMerchantShouldProfit) {
		this.subMerchantShouldProfit = subMerchantShouldProfit;
	}

	public String getSupplierClearingFlag() {
		return this.supplierClearingFlag;
	}

	public void setSupplierClearingFlag(String supplierClearingFlag) {
		this.supplierClearingFlag = supplierClearingFlag;
	}

	public Date getSupplierClearingTime() {
		return this.supplierClearingTime;
	}

	public void setSupplierClearingTime(Date supplierClearingTime) {
		this.supplierClearingTime = supplierClearingTime;
	}

	public String getSupplierCode() {
		return this.supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getSupplierName() {
		return this.supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierSettlementFlag() {
		return this.supplierSettlementFlag;
	}

	public void setSupplierSettlementFlag(String supplierSettlementFlag) {
		this.supplierSettlementFlag = supplierSettlementFlag;
	}

	public Date getSupplierSettlementTime() {
		return this.supplierSettlementTime;
	}

	public void setSupplierSettlementTime(Date supplierSettlementTime) {
		this.supplierSettlementTime = supplierSettlementTime;
	}

	public BigDecimal getSupplierToDdpRealRebate() {
		return this.supplierToDdpRealRebate;
	}

	public void setSupplierToDdpRealRebate(BigDecimal supplierToDdpRealRebate) {
		this.supplierToDdpRealRebate = supplierToDdpRealRebate;
	}

	public BigDecimal getSupplierToDdpShouldRebate() {
		return this.supplierToDdpShouldRebate;
	}

	public void setSupplierToDdpShouldRebate(BigDecimal supplierToDdpShouldRebate) {
		this.supplierToDdpShouldRebate = supplierToDdpShouldRebate;
	}

	public String getTopMerchantProfitFlag() {
		return this.topMerchantProfitFlag;
	}

	public void setTopMerchantProfitFlag(String topMerchantProfitFlag) {
		this.topMerchantProfitFlag = topMerchantProfitFlag;
	}

}