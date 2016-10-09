package com.dodopal.transfernew.business.model.transfer;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the CLEARING_BANK_TXN database table.
 * 
 */
//@Entity
//@Table(name="CLEARING_BANK_TXN")
//@NamedQuery(name="ClearingBankTxn.findAll", query="SELECT c FROM ClearingBankTxn c")
public class ClearingBankTxn implements Serializable {
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

//	@Column(name="BANK_FEE")
	private BigDecimal bankFee;

//	@Temporal(TemporalType.DATE)
//	@Column(name="CLEARLING_DATE")
	private Date clearlingDate;

//	@Column(name="CLEARLING_DAY")
	private String clearlingDay;

//	@Temporal(TemporalType.DATE)
//	@Column(name="CREATE_DATE")
	private Date createDate;

//	@Column(name="PAY_GATEWAY")
	private String payGateway;

//	@Column(name="TRADE_AMOUNT")
	private BigDecimal tradeAmount;

//	@Column(name="TRADE_COUNT")
	private BigDecimal tradeCount;

//	@Column(name="TRANSFER_AMOUNT")
	private BigDecimal transferAmount;

//	@Column(name="TXN_TYPE")
	private String txnType;

	public ClearingBankTxn() {
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

	public BigDecimal getBankFee() {
		return this.bankFee;
	}

	public void setBankFee(BigDecimal bankFee) {
		this.bankFee = bankFee;
	}

	public Date getClearlingDate() {
		return this.clearlingDate;
	}

	public void setClearlingDate(Date clearlingDate) {
		this.clearlingDate = clearlingDate;
	}

	public String getClearlingDay() {
		return this.clearlingDay;
	}

	public void setClearlingDay(String clearlingDay) {
		this.clearlingDay = clearlingDay;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getPayGateway() {
		return this.payGateway;
	}

	public void setPayGateway(String payGateway) {
		this.payGateway = payGateway;
	}

	public BigDecimal getTradeAmount() {
		return this.tradeAmount;
	}

	public void setTradeAmount(BigDecimal tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	public BigDecimal getTradeCount() {
		return this.tradeCount;
	}

	public void setTradeCount(BigDecimal tradeCount) {
		this.tradeCount = tradeCount;
	}

	public BigDecimal getTransferAmount() {
		return this.transferAmount;
	}

	public void setTransferAmount(BigDecimal transferAmount) {
		this.transferAmount = transferAmount;
	}

	public String getTxnType() {
		return this.txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

}