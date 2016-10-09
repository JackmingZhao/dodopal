package com.dodopal.transfernew.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the BATUNITTOTTB database table.
 * 
 */
//@Entity
//@NamedQuery(name="Batunittottb.findAll", query="SELECT b FROM Batunittottb b")
public class Batunittottb implements Serializable {
	private static final long serialVersionUID = 1L;

//	@EmbeddedId
	private BatunittottbPK id;

	private BigDecimal area;

	private String areaname;

	private BigDecimal hnd;

	private BigDecimal prehndret;

	private String tradeid;

	private String tradename;

	private BigDecimal txnamt;

	private BigDecimal txnnum;

	private BigDecimal ykthnd;

	public Batunittottb() {
	}

	public BatunittottbPK getId() {
		return this.id;
	}

	public void setId(BatunittottbPK id) {
		this.id = id;
	}

	public BigDecimal getArea() {
		return this.area;
	}

	public void setArea(BigDecimal area) {
		this.area = area;
	}

	public String getAreaname() {
		return this.areaname;
	}

	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}

	public BigDecimal getHnd() {
		return this.hnd;
	}

	public void setHnd(BigDecimal hnd) {
		this.hnd = hnd;
	}

	public BigDecimal getPrehndret() {
		return this.prehndret;
	}

	public void setPrehndret(BigDecimal prehndret) {
		this.prehndret = prehndret;
	}

	public String getTradeid() {
		return this.tradeid;
	}

	public void setTradeid(String tradeid) {
		this.tradeid = tradeid;
	}

	public String getTradename() {
		return this.tradename;
	}

	public void setTradename(String tradename) {
		this.tradename = tradename;
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