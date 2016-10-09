package com.dodopal.transfer.business.model.target;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the PRD_ORDER database table.
 * 
 */
//@Entity
//@Table(name="PRD_ORDER")
//@NamedQuery(name="PrdOrder.findAll", query="SELECT p FROM PrdOrder p")
public class PrdOrder implements Serializable {
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

//	@Column(name="BLACK_AMT")
	private BigDecimal blackAmt;

//	@Column(name="CARD_FACENO")
	private String cardFaceno;

//	@Column(name="CITY_CODE")
	private String cityCode;

//	@Column(name="CITY_NAME")
	private String cityName;

//	@Column(name="CLEARING_MARK")
	private String clearingMark;

	private String comments;

//	@Temporal(TemporalType.DATE)
//	@Column(name="CREATE_DATE")
	private Date createDate;

//	@Column(name="CREATE_USER")
	private String createUser;

//	@Column(name="DEP_ID")
	private String depId;

//	@Column(name="EXT_USER_CODE")
	private String extUserCode;

//	@Column(name="FUND_PROCESS_RESULT")
	private String fundProcessResult;

//	@Column(name="LOAD_FLAG")
	private String loadFlag;

//	@Column(name="LOAD_ORDER_NUM")
	private String loadOrderNum;

//	@Column(name="MER_CODE")
	private String merCode;

//	@Column(name="MER_GAIN")
	private BigDecimal merGain;

//	@Column(name="MER_NAME")
	private String merName;

//	@Column(name="MER_ORDER_NUM")
	private String merOrderNum;

//	@Column(name="MER_PURCHAASE_PRICE")
	private BigDecimal merPurchaasePrice;

//	@Column(name="MER_RATE")
	private BigDecimal merRate;

//	@Column(name="MER_RATE_TYPE")
	private String merRateType;

//	@Column(name="MER_USER_TYPE")
	private String merUserType;

//	@Column(name="ORDER_CARDNO")
	private String orderCardno;

//	@Column(name="PAGE_CALLBACK_URL")
	private String pageCallbackUrl;

//	@Column(name="PAY_SERVICE_RATE")
	private BigDecimal payServiceRate;

//	@Column(name="PAY_SERVICE_TYPE")
	private String payServiceType;

//	@Column(name="PAY_TYPE")
	private String payType;

//	@Column(name="PAY_WAY")
	private String payWay;

//	@Column(name="POS_CODE")
	private String posCode;

//	@Temporal(TemporalType.DATE)
//	@Column(name="PRD_ORDER_DATE")
	private Date prdOrderDate;

//	@Column(name="PRD_ORDER_DAY")
	private String prdOrderDay;

//	@Column(name="PRO_BEFOREINNER_STATES")
	private String proBeforeinnerStates;

//	@Column(name="PRO_CODE")
	private String proCode;

//	@Column(name="PRO_INNER_STATES")
	private String proInnerStates;

//	@Column(name="PRO_NAME")
	private String proName;

//	@Column(name="PRO_ORDER_NUM")
	private String proOrderNum;

//	@Column(name="PRO_ORDER_STATES")
	private String proOrderStates;

//	@Column(name="PRO_SUSPICIOUS_EXPLAIN")
	private String proSuspiciousExplain;

//	@Column(name="PRO_SUSPICIOUS_STATES")
	private String proSuspiciousStates;

//	@Column(name="RECEIVED_PRICE")
	private BigDecimal receivedPrice;

//	@Column(name="RESEND_SIGN")
	private String resendSign;

//	@Column(name="SERVICE_NOTICE_URL")
	private String serviceNoticeUrl;

	private String source;

//	@Column(name="TXN_AMT")
	private BigDecimal txnAmt;

//	@Temporal(TemporalType.DATE)
//	@Column(name="UPDATE_DATE")
	private Date updateDate;

//	@Column(name="UPDATE_USER")
	private String updateUser;

//	@Column(name="USER_CODE")
	private String userCode;

//	@Column(name="USER_ID")
	private String userId;

//	@Column(name="USER_NAME")
	private String userName;

//	@Column(name="YKT_CODE")
	private String yktCode;

//	@Column(name="YKT_NAME")
	private String yktName;

//	@Column(name="YKT_RECHARGE_RATE")
	private BigDecimal yktRechargeRate;

	public PrdOrder() {
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

	public String getDepId() {
		return this.depId;
	}

	public void setDepId(String depId) {
		this.depId = depId;
	}

	public String getExtUserCode() {
		return this.extUserCode;
	}

	public void setExtUserCode(String extUserCode) {
		this.extUserCode = extUserCode;
	}

	public String getFundProcessResult() {
		return this.fundProcessResult;
	}

	public void setFundProcessResult(String fundProcessResult) {
		this.fundProcessResult = fundProcessResult;
	}

	public String getLoadFlag() {
		return this.loadFlag;
	}

	public void setLoadFlag(String loadFlag) {
		this.loadFlag = loadFlag;
	}

	public String getLoadOrderNum() {
		return this.loadOrderNum;
	}

	public void setLoadOrderNum(String loadOrderNum) {
		this.loadOrderNum = loadOrderNum;
	}

	public String getMerCode() {
		return this.merCode;
	}

	public void setMerCode(String merCode) {
		this.merCode = merCode;
	}

	public BigDecimal getMerGain() {
		return this.merGain;
	}

	public void setMerGain(BigDecimal merGain) {
		this.merGain = merGain;
	}

	public String getMerName() {
		return this.merName;
	}

	public void setMerName(String merName) {
		this.merName = merName;
	}

	public String getMerOrderNum() {
		return this.merOrderNum;
	}

	public void setMerOrderNum(String merOrderNum) {
		this.merOrderNum = merOrderNum;
	}

	public BigDecimal getMerPurchaasePrice() {
		return this.merPurchaasePrice;
	}

	public void setMerPurchaasePrice(BigDecimal merPurchaasePrice) {
		this.merPurchaasePrice = merPurchaasePrice;
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

	public String getMerUserType() {
		return this.merUserType;
	}

	public void setMerUserType(String merUserType) {
		this.merUserType = merUserType;
	}

	public String getOrderCardno() {
		return this.orderCardno;
	}

	public void setOrderCardno(String orderCardno) {
		this.orderCardno = orderCardno;
	}

	public String getPageCallbackUrl() {
		return this.pageCallbackUrl;
	}

	public void setPageCallbackUrl(String pageCallbackUrl) {
		this.pageCallbackUrl = pageCallbackUrl;
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

	public String getPayWay() {
		return this.payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	public String getPosCode() {
		return this.posCode;
	}

	public void setPosCode(String posCode) {
		this.posCode = posCode;
	}

	public Date getPrdOrderDate() {
		return this.prdOrderDate;
	}

	public void setPrdOrderDate(Date prdOrderDate) {
		this.prdOrderDate = prdOrderDate;
	}

	public String getPrdOrderDay() {
		return this.prdOrderDay;
	}

	public void setPrdOrderDay(String prdOrderDay) {
		this.prdOrderDay = prdOrderDay;
	}

	public String getProBeforeinnerStates() {
		return this.proBeforeinnerStates;
	}

	public void setProBeforeinnerStates(String proBeforeinnerStates) {
		this.proBeforeinnerStates = proBeforeinnerStates;
	}

	public String getProCode() {
		return this.proCode;
	}

	public void setProCode(String proCode) {
		this.proCode = proCode;
	}

	public String getProInnerStates() {
		return this.proInnerStates;
	}

	public void setProInnerStates(String proInnerStates) {
		this.proInnerStates = proInnerStates;
	}

	public String getProName() {
		return this.proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getProOrderNum() {
		return this.proOrderNum;
	}

	public void setProOrderNum(String proOrderNum) {
		this.proOrderNum = proOrderNum;
	}

	public String getProOrderStates() {
		return this.proOrderStates;
	}

	public void setProOrderStates(String proOrderStates) {
		this.proOrderStates = proOrderStates;
	}

	public String getProSuspiciousExplain() {
		return this.proSuspiciousExplain;
	}

	public void setProSuspiciousExplain(String proSuspiciousExplain) {
		this.proSuspiciousExplain = proSuspiciousExplain;
	}

	public String getProSuspiciousStates() {
		return this.proSuspiciousStates;
	}

	public void setProSuspiciousStates(String proSuspiciousStates) {
		this.proSuspiciousStates = proSuspiciousStates;
	}

	public BigDecimal getReceivedPrice() {
		return this.receivedPrice;
	}

	public void setReceivedPrice(BigDecimal receivedPrice) {
		this.receivedPrice = receivedPrice;
	}

	public String getResendSign() {
		return this.resendSign;
	}

	public void setResendSign(String resendSign) {
		this.resendSign = resendSign;
	}

	public String getServiceNoticeUrl() {
		return this.serviceNoticeUrl;
	}

	public void setServiceNoticeUrl(String serviceNoticeUrl) {
		this.serviceNoticeUrl = serviceNoticeUrl;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public BigDecimal getTxnAmt() {
		return this.txnAmt;
	}

	public void setTxnAmt(BigDecimal txnAmt) {
		this.txnAmt = txnAmt;
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

	public String getUserCode() {
		return this.userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public BigDecimal getYktRechargeRate() {
		return this.yktRechargeRate;
	}

	public void setYktRechargeRate(BigDecimal yktRechargeRate) {
		this.yktRechargeRate = yktRechargeRate;
	}

}