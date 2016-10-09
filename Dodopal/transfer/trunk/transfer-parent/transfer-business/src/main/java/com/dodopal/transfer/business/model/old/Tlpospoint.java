package com.dodopal.transfer.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the TLPOSPOINTS database table.
 * 
 */
//@Entity
//@Table(name="TLPOSPOINTS")
//@NamedQuery(name="Tlpospoint.findAll", query="SELECT t FROM Tlpospoint t")
public class Tlpospoint implements Serializable {
	private static final long serialVersionUID = 1L;

	private BigDecimal clearyear;

	private BigDecimal pointvalue;

	private BigDecimal posid;

	private BigDecimal userid;

	public Tlpospoint() {
	}

	public BigDecimal getClearyear() {
		return this.clearyear;
	}

	public void setClearyear(BigDecimal clearyear) {
		this.clearyear = clearyear;
	}

	public BigDecimal getPointvalue() {
		return this.pointvalue;
	}

	public void setPointvalue(BigDecimal pointvalue) {
		this.pointvalue = pointvalue;
	}

	public BigDecimal getPosid() {
		return this.posid;
	}

	public void setPosid(BigDecimal posid) {
		this.posid = posid;
	}

	public BigDecimal getUserid() {
		return this.userid;
	}

	public void setUserid(BigDecimal userid) {
		this.userid = userid;
	}

}