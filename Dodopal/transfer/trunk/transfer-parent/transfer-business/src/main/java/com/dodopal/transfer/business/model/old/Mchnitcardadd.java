package com.dodopal.transfer.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the MCHNITCARDADD database table.
 * 
 */
//@Entity
//@NamedQuery(name="Mchnitcardadd.findAll", query="SELECT m FROM Mchnitcardadd m")
public class Mchnitcardadd implements Serializable {
	private static final long serialVersionUID = 1L;

	private BigDecimal aftsurpluslimit;

	private String backpaypwd;

	private BigDecimal befsurpluslimit;

	private String cardno;

	private BigDecimal cityid;

	private BigDecimal dzstatus;

	private String mchnitid;

	private String mchnitorderno;

	private BigDecimal posid;

	private String posseq;

	private String readcommand;

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

	public Mchnitcardadd() {
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