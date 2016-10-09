package com.dodopal.transfernew.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the BATMCHNITCYCLESETTB database table.
 * 
 */
//@Entity
//@NamedQuery(name="Batmchnitcyclesettb.findAll", query="SELECT b FROM Batmchnitcyclesettb b")
public class Batmchnitcyclesettb implements Serializable {
	private static final long serialVersionUID = 1L;

//	@EmbeddedId
	private BatmchnitcyclesettbPK id;

	private BigDecimal beginsetdate;

	private BigDecimal endsetdate;

	private BigDecimal hnd;

	private BigDecimal incomeamt;

	private BigDecimal prehndret;

	private String remitflag;

	private BigDecimal txnamt;

	private BigDecimal txnnum;

	private BigDecimal ykthnd;

	public Batmchnitcyclesettb() {
	}

	public BatmchnitcyclesettbPK getId() {
		return this.id;
	}

	public void setId(BatmchnitcyclesettbPK id) {
		this.id = id;
	}

	public BigDecimal getBeginsetdate() {
		return this.beginsetdate;
	}

	public void setBeginsetdate(BigDecimal beginsetdate) {
		this.beginsetdate = beginsetdate;
	}

	public BigDecimal getEndsetdate() {
		return this.endsetdate;
	}

	public void setEndsetdate(BigDecimal endsetdate) {
		this.endsetdate = endsetdate;
	}

	public BigDecimal getHnd() {
		return this.hnd;
	}

	public void setHnd(BigDecimal hnd) {
		this.hnd = hnd;
	}

	public BigDecimal getIncomeamt() {
		return this.incomeamt;
	}

	public void setIncomeamt(BigDecimal incomeamt) {
		this.incomeamt = incomeamt;
	}

	public BigDecimal getPrehndret() {
		return this.prehndret;
	}

	public void setPrehndret(BigDecimal prehndret) {
		this.prehndret = prehndret;
	}

	public String getRemitflag() {
		return this.remitflag;
	}

	public void setRemitflag(String remitflag) {
		this.remitflag = remitflag;
	}

	public BigDecimal getTxnamt() {
		return this.txnamt;
	}

	public void setTxnamt(BigDecimal txnamt) {
		this.txnamt = txnamt;
	}

	public BigDecimal getTxnnum() {
		return this.txnnum;
	}

	public void setTxnnum(BigDecimal txnnum) {
		this.txnnum = txnnum;
	}

	public BigDecimal getYkthnd() {
		return this.ykthnd;
	}

	public void setYkthnd(BigDecimal ykthnd) {
		this.ykthnd = ykthnd;
	}

}