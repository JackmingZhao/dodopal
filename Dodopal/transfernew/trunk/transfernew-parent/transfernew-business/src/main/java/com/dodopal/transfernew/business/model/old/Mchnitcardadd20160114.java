package com.dodopal.transfernew.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the MCHNITCARDADD20160114 database table.
 * 
 */
//@Entity
//@NamedQuery(name="Mchnitcardadd20160114.findAll", query="SELECT m FROM Mchnitcardadd20160114 m")
public class Mchnitcardadd20160114 implements Serializable {
	private static final long serialVersionUID = 1L;

	private BigDecimal aftsurpluslimit;

	private String backpaypwd;

	private BigDecimal balanceproamt;

	private BigDecimal befsurpluslimit;

	private String cardno;

	private String checkurl;

	private BigDecimal cityid;

	private BigDecimal dzstatus;

	private String mchnitid;

	private String mchnitorderno;

	private BigDecimal paidamt;

	private BigDecimal posid;

	private String posseq;

	private String readcommand;

	private BigDecimal rebatesamt;

	private String returnurl;

	private String serverurl;

	private BigDecimal status;

	private String termid;

//	@Column(name="TOTAL_FEE")
	private BigDecimal totalFee;

//	@Column(name="TRANSACTION_ID")
	private String transactionId;

	private BigDecimal txnamt;

	private String txndate;

	private String txntime;

	private BigDecimal userid;

	private String verifymd5;

	private BigDecimal yktid;

	public Mchnitcardadd20160114() {
	}

	public BigDecimal getAftsurpluslimit() {
		return this.aftsurpluslimit;
	}

	public void setAftsurpluslimit(BigDecimal aftsurpluslimit) {
		this.aftsurpluslimit = aftsurpluslimit;
	}

	public String getBackpaypwd() {
		return this.backpaypwd;
	}

	public void setBackpaypwd(String backpaypwd) {
		this.backpaypwd = backpaypwd;
	}

	public BigDecimal getBalanceproamt() {
		return this.balanceproamt;
	}

	public void setBalanceproamt(BigDecimal balanceproamt) {
		this.balanceproamt = balanceproamt;
	}

	public BigDecimal getBefsurpluslimit() {
		return this.befsurpluslimit;
	}

	public void setBefsurpluslimit(BigDecimal befsurpluslimit) {
		this.befsurpluslimit = befsurpluslimit;
	}

	public String getCardno() {
		return this.cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public String getCheckurl() {
		return this.checkurl;
	}

	public void setCheckurl(String checkurl) {
		this.checkurl = checkurl;
	}

	public BigDecimal getCityid() {
		return this.cityid;
	}

	public void setCityid(BigDecimal cityid) {
		this.cityid = cityid;
	}

	public BigDecimal getDzstatus() {
		return this.dzstatus;
	}

	public void setDzstatus(BigDecimal dzstatus) {
		this.dzstatus = dzstatus;
	}

	public String getMchnitid() {
		return this.mchnitid;
	}

	public void setMchnitid(String mchnitid) {
		this.mchnitid = mchnitid;
	}

	public String getMchnitorderno() {
		return this.mchnitorderno;
	}

	public void setMchnitorderno(String mchnitorderno) {
		this.mchnitorderno = mchnitorderno;
	}

	public BigDecimal getPaidamt() {
		return this.paidamt;
	}

	public void setPaidamt(BigDecimal paidamt) {
		this.paidamt = paidamt;
	}

	public BigDecimal getPosid() {
		return this.posid;
	}

	public void setPosid(BigDecimal posid) {
		this.posid = posid;
	}

	public String getPosseq() {
		return this.posseq;
	}

	public void setPosseq(String posseq) {
		this.posseq = posseq;
	}

	public String getReadcommand() {
		return this.readcommand;
	}

	public void setReadcommand(String readcommand) {
		this.readcommand = readcommand;
	}

	public BigDecimal getRebatesamt() {
		return this.rebatesamt;
	}

	public void setRebatesamt(BigDecimal rebatesamt) {
		this.rebatesamt = rebatesamt;
	}

	public String getReturnurl() {
		return this.returnurl;
	}

	public void setReturnurl(String returnurl) {
		this.returnurl = returnurl;
	}

	public String getServerurl() {
		return this.serverurl;
	}

	public void setServerurl(String serverurl) {
		this.serverurl = serverurl;
	}

	public BigDecimal getStatus() {
		return this.status;
	}

	public void setStatus(BigDecimal status) {
		this.status = status;
	}

	public String getTermid() {
		return this.termid;
	}

	public void setTermid(String termid) {
		this.termid = termid;
	}

	public BigDecimal getTotalFee() {
		return this.totalFee;
	}

	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}

	public String getTransactionId() {
		return this.transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public BigDecimal getTxnamt() {
		return this.txnamt;
	}

	public void setTxnamt(BigDecimal txnamt) {
		this.txnamt = txnamt;
	}

	public String getTxndate() {
		return this.txndate;
	}

	public void setTxndate(String txndate) {
		this.txndate = txndate;
	}

	public String getTxntime() {
		return this.txntime;
	}

	public void setTxntime(String txntime) {
		this.txntime = txntime;
	}

	public BigDecimal getUserid() {
		return this.userid;
	}

	public void setUserid(BigDecimal userid) {
		this.userid = userid;
	}

	public String getVerifymd5() {
		return this.verifymd5;
	}

	public void setVerifymd5(String verifymd5) {
		this.verifymd5 = verifymd5;
	}

	public BigDecimal getYktid() {
		return this.yktid;
	}

	public void setYktid(BigDecimal yktid) {
		this.yktid = yktid;
	}

}