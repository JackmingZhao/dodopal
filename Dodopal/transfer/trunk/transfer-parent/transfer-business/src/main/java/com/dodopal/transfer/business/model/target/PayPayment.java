package com.dodopal.transfer.business.model.target;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the PAY_PAYMENT database table.
 * 
 */
//@Entity
//@Table(name="PAY_PAYMENT")
//@NamedQuery(name="PayPayment.findAll", query="SELECT p FROM PayPayment p")
public class PayPayment implements Serializable {
	private static final long serialVersionUID = 1L;

//	@Id
	private String id;

//	@Column(name="ACCEPT_CIPHERTEXT")
	private String acceptCiphertext;

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

//	@Temporal(TemporalType.DATE)
//	@Column(name="PAGE_CALLBACK_DATE")
	private Date pageCallbackDate;

//	@Column(name="PAGE_CALLBACK_STATUS")
	private String pageCallbackStatus;

//	@Column(name="PAY_MONEY")
	private BigDecimal payMoney;

//	@Column(name="PAY_SERVICE_FEE")
	private BigDecimal payServiceFee;

//	@Column(name="PAY_SERVICE_RATE")
	private BigDecimal payServiceRate;

//	@Column(name="PAY_STATUS")
	private String payStatus;

//	@Column(name="PAY_TYPE")
	private String payType;

//	@Column(name="PAY_WAY_ID")
	private String payWayId;

//	@Column(name="PAY_WAY_KIND")
	private String payWayKind;

//	@Column(name="SEND_CIPHERTEXT")
	private String sendCiphertext;

//	@Temporal(TemporalType.DATE)
//	@Column(name="SERVICE_NOTICE_DATE")
	private Date serviceNoticeDate;

//	@Column(name="SERVICE_NOTICE_STATUS")
	private String serviceNoticeStatus;

//	@Column(name="TRAN_CODE")
	private String tranCode;

//	@Temporal(TemporalType.DATE)
//	@Column(name="UPDATE_DATE")
	private Date updateDate;

//	@Column(name="UPDATE_USER")
	private String updateUser;

	public PayPayment() {
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

	public Date getPageCallbackDate() {
		return this.pageCallbackDate;
	}

	public void setPageCallbackDate(Date pageCallbackDate) {
		this.pageCallbackDate = pageCallbackDate;
	}

	public String getPageCallbackStatus() {
		return this.pageCallbackStatus;
	}

	public void setPageCallbackStatus(String pageCallbackStatus) {
		this.pageCallbackStatus = pageCallbackStatus;
	}

	public BigDecimal getPayMoney() {
		return this.payMoney;
	}

	public void setPayMoney(BigDecimal payMoney) {
		this.payMoney = payMoney;
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

	public String getPayStatus() {
		return this.payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getPayType() {
		return this.payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getPayWayId() {
		return this.payWayId;
	}

	public void setPayWayId(String payWayId) {
		this.payWayId = payWayId;
	}

	public String getPayWayKind() {
		return this.payWayKind;
	}

	public void setPayWayKind(String payWayKind) {
		this.payWayKind = payWayKind;
	}

	public String getSendCiphertext() {
		return this.sendCiphertext;
	}

	public void setSendCiphertext(String sendCiphertext) {
		this.sendCiphertext = sendCiphertext;
	}

	public Date getServiceNoticeDate() {
		return this.serviceNoticeDate;
	}

	public void setServiceNoticeDate(Date serviceNoticeDate) {
		this.serviceNoticeDate = serviceNoticeDate;
	}

	public String getServiceNoticeStatus() {
		return this.serviceNoticeStatus;
	}

	public void setServiceNoticeStatus(String serviceNoticeStatus) {
		this.serviceNoticeStatus = serviceNoticeStatus;
	}

	public String getTranCode() {
		return this.tranCode;
	}

	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
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