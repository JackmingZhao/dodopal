package com.dodopal.transfernew.business.model.transfer;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the CRD_SYS_CONS_ORDER database table.
 * 
 */
//@Entity
//@Table(name="CRD_SYS_CONS_ORDER")
//@NamedQuery(name="CrdSysConsOrder.findAll", query="SELECT c FROM CrdSysConsOrder c")
public class CrdSysConsOrder implements Serializable {
	private static final long serialVersionUID = 1L;

//	@Id
	private String id;

//	@Column(name="AFTER_CARD_NOTES")
	private String afterCardNotes;

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

//	@Column(name="BEFORE_CARD_NOTES")
	private String beforeCardNotes;

//	@Column(name="BLACK_AMT")
	private BigDecimal blackAmt;

//	@Column(name="CARD_COUNTER")
	private String cardCounter;

//	@Column(name="CARD_FACE_NO")
	private String cardFaceNo;

//	@Column(name="CARD_INNER_NO")
	private String cardInnerNo;

//	@Column(name="CARD_LIMIT")
	private BigDecimal cardLimit;

//	@Column(name="CARD_TAC")
	private String cardTac;

//	@Column(name="CARD_TYPE")
	private BigDecimal cardType;

//	@Column(name="CHARGE_TYPE")
	private BigDecimal chargeType;

//	@Column(name="CHECK_CARD_NO")
	private String checkCardNo;

//	@Column(name="CHECK_CARD_POS_CODE")
	private String checkCardPosCode;

//	@Temporal(TemporalType.DATE)
//	@Column(name="CHECK_RES_CARD_DATE")
	private Date checkResCardDate;

//	@Temporal(TemporalType.DATE)
//	@Column(name="CHECK_SEND_CARD_DATE")
	private Date checkSendCardDate;

//	@Column(name="CITY_CODE")
	private String cityCode;

//	@Column(name="CONSUME_CARD_NO")
	private String consumeCardNo;

//	@Column(name="CONSUME_CARD_POS_CODE")
	private String consumeCardPosCode;

//	@Temporal(TemporalType.DATE)
//	@Column(name="CONSUME_RES_CARD_DATE")
	private Date consumeResCardDate;

//	@Temporal(TemporalType.DATE)
//	@Column(name="CONSUME_SEND_CARD_DATE")
	private Date consumeSendCardDate;

//	@Column(name="CRD_BEFOREORDER_STATES")
	private String crdBeforeorderStates;

//	@Column(name="CRD_ORDER_NUM")
	private String crdOrderNum;

//	@Column(name="CRD_ORDER_STATES")
	private String crdOrderStates;

//	@Temporal(TemporalType.DATE)
//	@Column(name="CREATE_DATE")
	private Date createDate;

//	@Column(name="CREATE_USER")
	private String createUser;

//	@Column(name="DOUNKNOW_FLAG")
	private BigDecimal dounknowFlag;

//	@Column(name="MER_CODE")
	private String merCode;

//	@Column(name="MER_ORDER_CODE")
	private String merOrderCode;

//	@Temporal(TemporalType.DATE)
//	@Column(name="METROPASS_CHARGE_END_DATE")
	private Date metropassChargeEndDate;

//	@Temporal(TemporalType.DATE)
//	@Column(name="METROPASS_CHARGE_START_DATE")
	private Date metropassChargeStartDate;

//	@Column(name="METROPASS_TYPE")
	private BigDecimal metropassType;

//	@Column(name="ORDER_CARD_NO")
	private String orderCardNo;

//	@Column(name="POS_CODE")
	private String posCode;

//	@Column(name="POS_SEQ")
	private BigDecimal posSeq;

//	@Column(name="POS_TYPE")
	private BigDecimal posType;

//	@Column(name="PRO_CODE")
	private String proCode;

//	@Column(name="PRO_ORDER_NUM")
	private String proOrderNum;

//	@Column(name="RESP_CODE")
	private String respCode;

//	@Temporal(TemporalType.DATE)
//	@Column(name="RESULT_RES_CARD_DATE")
	private Date resultResCardDate;

//	@Temporal(TemporalType.DATE)
//	@Column(name="RESULT_SEND_CARD_DATE")
	private Date resultSendCardDate;

	private String secmac;

	private String source;

//	@Column(name="TRADE_END_FLAG")
	private BigDecimal tradeEndFlag;

//	@Column(name="TRADE_STEP")
	private String tradeStep;

//	@Column(name="TRADE_STEP_VER")
	private String tradeStepVer;

//	@Column(name="TXN_AMT")
	private BigDecimal txnAmt;

//	@Column(name="TXN_DATE")
	private String txnDate;

//	@Column(name="TXN_SEQ")
	private BigDecimal txnSeq;

//	@Column(name="TXN_TIME")
	private String txnTime;

//	@Column(name="TXN_TYPE")
	private BigDecimal txnType;

//	@Temporal(TemporalType.DATE)
//	@Column(name="UPDATE_DATE")
	private Date updateDate;

//	@Column(name="UPDATE_USER")
	private String updateUser;

//	@Column(name="USER_CODE")
	private String userCode;

//	@Column(name="USER_ID")
	private String userId;

//	@Column(name="YKT_CODE")
	private String yktCode;

	public CrdSysConsOrder() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAfterCardNotes() {
		return this.afterCardNotes;
	}

	public void setAfterCardNotes(String afterCardNotes) {
		this.afterCardNotes = afterCardNotes;
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

	public String getBeforeCardNotes() {
		return this.beforeCardNotes;
	}

	public void setBeforeCardNotes(String beforeCardNotes) {
		this.beforeCardNotes = beforeCardNotes;
	}

	public BigDecimal getBlackAmt() {
		return this.blackAmt;
	}

	public void setBlackAmt(BigDecimal blackAmt) {
		this.blackAmt = blackAmt;
	}

	public String getCardCounter() {
		return this.cardCounter;
	}

	public void setCardCounter(String cardCounter) {
		this.cardCounter = cardCounter;
	}

	public String getCardFaceNo() {
		return this.cardFaceNo;
	}

	public void setCardFaceNo(String cardFaceNo) {
		this.cardFaceNo = cardFaceNo;
	}

	public String getCardInnerNo() {
		return this.cardInnerNo;
	}

	public void setCardInnerNo(String cardInnerNo) {
		this.cardInnerNo = cardInnerNo;
	}

	public BigDecimal getCardLimit() {
		return this.cardLimit;
	}

	public void setCardLimit(BigDecimal cardLimit) {
		this.cardLimit = cardLimit;
	}

	public String getCardTac() {
		return this.cardTac;
	}

	public void setCardTac(String cardTac) {
		this.cardTac = cardTac;
	}

	public BigDecimal getCardType() {
		return this.cardType;
	}

	public void setCardType(BigDecimal cardType) {
		this.cardType = cardType;
	}

	public BigDecimal getChargeType() {
		return this.chargeType;
	}

	public void setChargeType(BigDecimal chargeType) {
		this.chargeType = chargeType;
	}

	public String getCheckCardNo() {
		return this.checkCardNo;
	}

	public void setCheckCardNo(String checkCardNo) {
		this.checkCardNo = checkCardNo;
	}

	public String getCheckCardPosCode() {
		return this.checkCardPosCode;
	}

	public void setCheckCardPosCode(String checkCardPosCode) {
		this.checkCardPosCode = checkCardPosCode;
	}

	public Date getCheckResCardDate() {
		return this.checkResCardDate;
	}

	public void setCheckResCardDate(Date checkResCardDate) {
		this.checkResCardDate = checkResCardDate;
	}

	public Date getCheckSendCardDate() {
		return this.checkSendCardDate;
	}

	public void setCheckSendCardDate(Date checkSendCardDate) {
		this.checkSendCardDate = checkSendCardDate;
	}

	public String getCityCode() {
		return this.cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getConsumeCardNo() {
		return this.consumeCardNo;
	}

	public void setConsumeCardNo(String consumeCardNo) {
		this.consumeCardNo = consumeCardNo;
	}

	public String getConsumeCardPosCode() {
		return this.consumeCardPosCode;
	}

	public void setConsumeCardPosCode(String consumeCardPosCode) {
		this.consumeCardPosCode = consumeCardPosCode;
	}

	public Date getConsumeResCardDate() {
		return this.consumeResCardDate;
	}

	public void setConsumeResCardDate(Date consumeResCardDate) {
		this.consumeResCardDate = consumeResCardDate;
	}

	public Date getConsumeSendCardDate() {
		return this.consumeSendCardDate;
	}

	public void setConsumeSendCardDate(Date consumeSendCardDate) {
		this.consumeSendCardDate = consumeSendCardDate;
	}

	public String getCrdBeforeorderStates() {
		return this.crdBeforeorderStates;
	}

	public void setCrdBeforeorderStates(String crdBeforeorderStates) {
		this.crdBeforeorderStates = crdBeforeorderStates;
	}

	public String getCrdOrderNum() {
		return this.crdOrderNum;
	}

	public void setCrdOrderNum(String crdOrderNum) {
		this.crdOrderNum = crdOrderNum;
	}

	public String getCrdOrderStates() {
		return this.crdOrderStates;
	}

	public void setCrdOrderStates(String crdOrderStates) {
		this.crdOrderStates = crdOrderStates;
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

	public BigDecimal getDounknowFlag() {
		return this.dounknowFlag;
	}

	public void setDounknowFlag(BigDecimal dounknowFlag) {
		this.dounknowFlag = dounknowFlag;
	}

	public String getMerCode() {
		return this.merCode;
	}

	public void setMerCode(String merCode) {
		this.merCode = merCode;
	}

	public String getMerOrderCode() {
		return this.merOrderCode;
	}

	public void setMerOrderCode(String merOrderCode) {
		this.merOrderCode = merOrderCode;
	}

	public Date getMetropassChargeEndDate() {
		return this.metropassChargeEndDate;
	}

	public void setMetropassChargeEndDate(Date metropassChargeEndDate) {
		this.metropassChargeEndDate = metropassChargeEndDate;
	}

	public Date getMetropassChargeStartDate() {
		return this.metropassChargeStartDate;
	}

	public void setMetropassChargeStartDate(Date metropassChargeStartDate) {
		this.metropassChargeStartDate = metropassChargeStartDate;
	}

	public BigDecimal getMetropassType() {
		return this.metropassType;
	}

	public void setMetropassType(BigDecimal metropassType) {
		this.metropassType = metropassType;
	}

	public String getOrderCardNo() {
		return this.orderCardNo;
	}

	public void setOrderCardNo(String orderCardNo) {
		this.orderCardNo = orderCardNo;
	}

	public String getPosCode() {
		return this.posCode;
	}

	public void setPosCode(String posCode) {
		this.posCode = posCode;
	}

	public BigDecimal getPosSeq() {
		return this.posSeq;
	}

	public void setPosSeq(BigDecimal posSeq) {
		this.posSeq = posSeq;
	}

	public BigDecimal getPosType() {
		return this.posType;
	}

	public void setPosType(BigDecimal posType) {
		this.posType = posType;
	}

	public String getProCode() {
		return this.proCode;
	}

	public void setProCode(String proCode) {
		this.proCode = proCode;
	}

	public String getProOrderNum() {
		return this.proOrderNum;
	}

	public void setProOrderNum(String proOrderNum) {
		this.proOrderNum = proOrderNum;
	}

	public String getRespCode() {
		return this.respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public Date getResultResCardDate() {
		return this.resultResCardDate;
	}

	public void setResultResCardDate(Date resultResCardDate) {
		this.resultResCardDate = resultResCardDate;
	}

	public Date getResultSendCardDate() {
		return this.resultSendCardDate;
	}

	public void setResultSendCardDate(Date resultSendCardDate) {
		this.resultSendCardDate = resultSendCardDate;
	}

	public String getSecmac() {
		return this.secmac;
	}

	public void setSecmac(String secmac) {
		this.secmac = secmac;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public BigDecimal getTradeEndFlag() {
		return this.tradeEndFlag;
	}

	public void setTradeEndFlag(BigDecimal tradeEndFlag) {
		this.tradeEndFlag = tradeEndFlag;
	}

	public String getTradeStep() {
		return this.tradeStep;
	}

	public void setTradeStep(String tradeStep) {
		this.tradeStep = tradeStep;
	}

	public String getTradeStepVer() {
		return this.tradeStepVer;
	}

	public void setTradeStepVer(String tradeStepVer) {
		this.tradeStepVer = tradeStepVer;
	}

	public BigDecimal getTxnAmt() {
		return this.txnAmt;
	}

	public void setTxnAmt(BigDecimal txnAmt) {
		this.txnAmt = txnAmt;
	}

	public String getTxnDate() {
		return this.txnDate;
	}

	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}

	public BigDecimal getTxnSeq() {
		return this.txnSeq;
	}

	public void setTxnSeq(BigDecimal txnSeq) {
		this.txnSeq = txnSeq;
	}

	public String getTxnTime() {
		return this.txnTime;
	}

	public void setTxnTime(String txnTime) {
		this.txnTime = txnTime;
	}

	public BigDecimal getTxnType() {
		return this.txnType;
	}

	public void setTxnType(BigDecimal txnType) {
		this.txnType = txnType;
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

	public String getYktCode() {
		return this.yktCode;
	}

	public void setYktCode(String yktCode) {
		this.yktCode = yktCode;
	}

}