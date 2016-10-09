package com.dodopal.transfernew.business.model.transfer;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the PAY_TRATRANSACTION database table.
 * 
 */
//@Entity
//@Table(name="PAY_TRATRANSACTION")
//@NamedQuery(name="PayTratransaction.findAll", query="SELECT p FROM PayTratransaction p")
public class PayTratransaction implements Serializable {
	private static final long serialVersionUID = 1L;

//	@Id
	private String id;

//	@Column(name="ACCEPT_CIPHERTEXT")
	private String acceptCiphertext;

//	@Column(name="AMOUNT_MONEY")
	private BigDecimal amountMoney;

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

//	@Column(name="CLEAR_FLAG")
	private String clearFlag;

	private String comments;

	private String commodity;

//	@Temporal(TemporalType.DATE)
//	@Column(name="CREATE_DATE")
	private Date createDate;

//	@Column(name="CREATE_USER")
	private String createUser;

//	@Temporal(TemporalType.DATE)
//	@Column(name="FINISH_DATE")
	private Date finishDate;

	private String genid;

//	@Column(name="MER_OR_USER_CODE")
	private String merOrUserCode;

//	@Column(name="OLD_TRAN_CODE")
	private String oldTranCode;

//	@Column(name="OPERATOR_ID")
	private String operatorId;

//	@Temporal(TemporalType.DATE)
//	@Column(name="ORDER_DATE")
	private Date orderDate;

//	@Column(name="ORDER_NUMBER")
	private String orderNumber;

//	@Column(name="PAGE_CALLBACK_URL")
	private String pageCallbackUrl;

//	@Column(name="PAY_GATEWAY")
	private String payGateway;

//	@Column(name="PAY_PROCE_FEE")
	private BigDecimal payProceFee;

//	@Column(name="PAY_PROCE_RATE")
	private BigDecimal payProceRate;

//	@Column(name="PAY_SERVICE_FEE")
	private BigDecimal payServiceFee;

//	@Column(name="PAY_SERVICE_RATE")
	private BigDecimal payServiceRate;

//	@Column(name="PAY_SERVICE_TYPE")
	private String payServiceType;

//	@Column(name="PAY_TYPE")
	private String payType;

//	@Column(name="PAY_WAY")
	private String payWay;

//	@Column(name="PAY_WAY_NAME")
	private String payWayName;

//	@Column(name="REAL_TRAN_MONEY")
	private BigDecimal realTranMoney;

//	@Column(name="SEND_CIPHERTEXT")
	private String sendCiphertext;

//	@Column(name="SERVICE_NOTICE_URL")
	private String serviceNoticeUrl;

	private String source;

//	@Column(name="TRAN_CODE")
	private String tranCode;

//	@Column(name="TRAN_IN_STATUS")
	private String tranInStatus;

//	@Column(name="TRAN_NAME")
	private String tranName;

//	@Column(name="TRAN_OUT_STATUS")
	private String tranOutStatus;

//	@Column(name="TRAN_TYPE")
	private String tranType;

//	@Column(name="TURN_INTO_MER_CODE")
	private String turnIntoMerCode;

//	@Column(name="TURN_OUT_MER_CODE")
	private String turnOutMerCode;

//	@Temporal(TemporalType.DATE)
//	@Column(name="UPDATE_DATE")
	private Date updateDate;

//	@Column(name="UPDATE_USER")
	private String updateUser;

//	@Column(name="USER_TYPE")
	private String userType;

	public PayTratransaction() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAcceptCiphertext() {
		return this.acceptCiphertext;
	}

	public void setAcceptCiphertext(String acceptCiphertext) {
		this.acceptCiphertext = acceptCiphertext;
	}

	public BigDecimal getAmountMoney() {
		return this.amountMoney;
	}

	public void setAmountMoney(BigDecimal amountMoney) {
		this.amountMoney = amountMoney;
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

	public String getClearFlag() {
		return this.clearFlag;
	}

	public void setClearFlag(String clearFlag) {
		this.clearFlag = clearFlag;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getCommodity() {
		return this.commodity;
	}

	public void setCommodity(String commodity) {
		this.commodity = commodity;
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

	public Date getFinishDate() {
		return this.finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public String getGenid() {
		return this.genid;
	}

	public void setGenid(String genid) {
		this.genid = genid;
	}

	public String getMerOrUserCode() {
		return this.merOrUserCode;
	}

	public void setMerOrUserCode(String merOrUserCode) {
		this.merOrUserCode = merOrUserCode;
	}

	public String getOldTranCode() {
		return this.oldTranCode;
	}

	public void setOldTranCode(String oldTranCode) {
		this.oldTranCode = oldTranCode;
	}

	public String getOperatorId() {
		return this.operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public Date getOrderDate() {
		return this.orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderNumber() {
		return this.orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getPageCallbackUrl() {
		return this.pageCallbackUrl;
	}

	public void setPageCallbackUrl(String pageCallbackUrl) {
		this.pageCallbackUrl = pageCallbackUrl;
	}

	public String getPayGateway() {
		return this.payGateway;
	}

	public void setPayGateway(String payGateway) {
		this.payGateway = payGateway;
	}

	public BigDecimal getPayProceFee() {
		return this.payProceFee;
	}

	public void setPayProceFee(BigDecimal payProceFee) {
		this.payProceFee = payProceFee;
	}

	public BigDecimal getPayProceRate() {
		return this.payProceRate;
	}

	public void setPayProceRate(BigDecimal payProceRate) {
		this.payProceRate = payProceRate;
	}

	public BigDecimal getPayServiceFee() {
		return this.payServiceFee;
	}

	public void setPayServiceFee(BigDecimal payServiceFee) {
		this.payServiceFee = payServiceFee;
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

	public String getPayWayName() {
		return this.payWayName;
	}

	public void setPayWayName(String payWayName) {
		this.payWayName = payWayName;
	}

	public BigDecimal getRealTranMoney() {
		return this.realTranMoney;
	}

	public void setRealTranMoney(BigDecimal realTranMoney) {
		this.realTranMoney = realTranMoney;
	}

	public String getSendCiphertext() {
		return this.sendCiphertext;
	}

	public void setSendCiphertext(String sendCiphertext) {
		this.sendCiphertext = sendCiphertext;
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

	public String getTranCode() {
		return this.tranCode;
	}

	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}

	public String getTranInStatus() {
		return this.tranInStatus;
	}

	public void setTranInStatus(String tranInStatus) {
		this.tranInStatus = tranInStatus;
	}

	public String getTranName() {
		return this.tranName;
	}

	public void setTranName(String tranName) {
		this.tranName = tranName;
	}

	public String getTranOutStatus() {
		return this.tranOutStatus;
	}

	public void setTranOutStatus(String tranOutStatus) {
		this.tranOutStatus = tranOutStatus;
	}

	public String getTranType() {
		return this.tranType;
	}

	public void setTranType(String tranType) {
		this.tranType = tranType;
	}

	public String getTurnIntoMerCode() {
		return this.turnIntoMerCode;
	}

	public void setTurnIntoMerCode(String turnIntoMerCode) {
		this.turnIntoMerCode = turnIntoMerCode;
	}

	public String getTurnOutMerCode() {
		return this.turnOutMerCode;
	}

	public void setTurnOutMerCode(String turnOutMerCode) {
		this.turnOutMerCode = turnOutMerCode;
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

	public String getUserType() {
		return this.userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

}