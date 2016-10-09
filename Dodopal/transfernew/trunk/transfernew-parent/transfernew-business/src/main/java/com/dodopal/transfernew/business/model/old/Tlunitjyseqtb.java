package com.dodopal.transfernew.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the TLUNITJYSEQTB database table.
 * 
 */
//@Entity
//@NamedQuery(name="Tlunitjyseqtb.findAll", query="SELECT t FROM Tlunitjyseqtb t")
public class Tlunitjyseqtb implements Serializable {
	private static final long serialVersionUID = 1L;

//	@EmbeddedId
	private TlunitjyseqtbPK id;

	private BigDecimal aftbal;

	private BigDecimal amtfee;

	private BigDecimal batchid;

	private BigDecimal befbal;

	private BigDecimal cardcnt;

	private String cardno;

	private BigDecimal cardphytype;

	private BigDecimal cardtype;

	private BigDecimal cendate;

	private BigDecimal centime;

	private BigDecimal cityid;

	private String csn;

	private BigDecimal errcode;

	private String feetype;

	private BigDecimal hnd;

	private BigDecimal posid;

	private BigDecimal posseq;

	private String resv;

	private String tac;

	private BigDecimal txnamt;

	private BigDecimal txncode;

	private BigDecimal txndate;

	private BigDecimal txntime;

	private BigDecimal yktamtfee;

	private BigDecimal ykthnd;

	private BigDecimal yktid;

	public Tlunitjyseqtb() {
	}

	public TlunitjyseqtbPK getId() {
		return this.id;
	}

	public void setId(TlunitjyseqtbPK id) {
		this.id = id;
	}

	public BigDecimal getAftbal() {
		return this.aftbal;
	}

	public void setAftbal(BigDecimal aftbal) {
		this.aftbal = aftbal;
	}

	public BigDecimal getAmtfee() {
		return this.amtfee;
	}

	public void setAmtfee(BigDecimal amtfee) {
		this.amtfee = amtfee;
	}

	public BigDecimal getBatchid() {
		return this.batchid;
	}

	public void setBatchid(BigDecimal batchid) {
		this.batchid = batchid;
	}

	public BigDecimal getBefbal() {
		return this.befbal;
	}

	public void setBefbal(BigDecimal befbal) {
		this.befbal = befbal;
	}

	public BigDecimal getCardcnt() {
		return this.cardcnt;
	}

	public void setCardcnt(BigDecimal cardcnt) {
		this.cardcnt = cardcnt;
	}

	public String getCardno() {
		return this.cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public BigDecimal getCardphytype() {
		return this.cardphytype;
	}

	public void setCardphytype(BigDecimal cardphytype) {
		this.cardphytype = cardphytype;
	}

	public BigDecimal getCardtype() {
		return this.cardtype;
	}

	public void setCardtype(BigDecimal cardtype) {
		this.cardtype = cardtype;
	}

	public BigDecimal getCendate() {
		return this.cendate;
	}

	public void setCendate(BigDecimal cendate) {
		this.cendate = cendate;
	}

	public BigDecimal getCentime() {
		return this.centime;
	}

	public void setCentime(BigDecimal centime) {
		this.centime = centime;
	}

	public BigDecimal getCityid() {
		return this.cityid;
	}

	public void setCityid(BigDecimal cityid) {
		this.cityid = cityid;
	}

	public String getCsn() {
		return this.csn;
	}

	public void setCsn(String csn) {
		this.csn = csn;
	}

	public BigDecimal getErrcode() {
		return this.errcode;
	}

	public void setErrcode(BigDecimal errcode) {
		this.errcode = errcode;
	}

	public String getFeetype() {
		return this.feetype;
	}

	public void setFeetype(String feetype) {
		this.feetype = feetype;
	}

	public BigDecimal getHnd() {
		return this.hnd;
	}

	public void setHnd(BigDecimal hnd) {
		this.hnd = hnd;
	}

	public BigDecimal getPosid() {
		return this.posid;
	}

	public void setPosid(BigDecimal posid) {
		this.posid = posid;
	}

	public BigDecimal getPosseq() {
		return this.posseq;
	}

	public void setPosseq(BigDecimal posseq) {
		this.posseq = posseq;
	}

	public String getResv() {
		return this.resv;
	}

	public void setResv(String resv) {
		this.resv = resv;
	}

	public String getTac() {
		return this.tac;
	}

	public void setTac(String tac) {
		this.tac = tac;
	}

	public BigDecimal getTxnamt() {
		return this.txnamt;
	}

	public void setTxnamt(BigDecimal txnamt) {
		this.txnamt = txnamt;
	}

	public BigDecimal getTxncode() {
		return this.txncode;
	}

	public void setTxncode(BigDecimal txncode) {
		this.txncode = txncode;
	}

	public BigDecimal getTxndate() {
		return this.txndate;
	}

	public void setTxndate(BigDecimal txndate) {
		this.txndate = txndate;
	}

	public BigDecimal getTxntime() {
		return this.txntime;
	}

	public void setTxntime(BigDecimal txntime) {
		this.txntime = txntime;
	}

	public BigDecimal getYktamtfee() {
		return this.yktamtfee;
	}

	public void setYktamtfee(BigDecimal yktamtfee) {
		this.yktamtfee = yktamtfee;
	}

	public BigDecimal getYkthnd() {
		return this.ykthnd;
	}

	public void setYkthnd(BigDecimal ykthnd) {
		this.ykthnd = ykthnd;
	}

	public BigDecimal getYktid() {
		return this.yktid;
	}

	public void setYktid(BigDecimal yktid) {
		this.yktid = yktid;
	}

}