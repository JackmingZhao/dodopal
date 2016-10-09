package com.dodopal.transfernew.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the TLPOSPOINTSTNX database table.
 * 
 */
//@Entity
//@NamedQuery(name="Tlpospointstnx.findAll", query="SELECT t FROM Tlpospointstnx t")
public class Tlpospointstnx implements Serializable {
	private static final long serialVersionUID = 1L;

	private String applytime;

	private String orderid;

	private String pointspara;

	private BigDecimal pointtxn;

	private BigDecimal pointvalue;

	private BigDecimal posid;

	private BigDecimal tnxid;

	public Tlpospointstnx() {
	}

	public String getApplytime() {
		return this.applytime;
	}

	public void setApplytime(String applytime) {
		this.applytime = applytime;
	}

	public String getOrderid() {
		return this.orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getPointspara() {
		return this.pointspara;
	}

	public void setPointspara(String pointspara) {
		this.pointspara = pointspara;
	}

	public BigDecimal getPointtxn() {
		return this.pointtxn;
	}

	public void setPointtxn(BigDecimal pointtxn) {
		this.pointtxn = pointtxn;
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

	public BigDecimal getTnxid() {
		return this.tnxid;
	}

	public void setTnxid(BigDecimal tnxid) {
		this.tnxid = tnxid;
	}

}