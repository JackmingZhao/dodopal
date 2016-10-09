package com.dodopal.transfer.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the BIPOSINUSERSTB database table.
 * 
 */
//@Entity
//@NamedQuery(name="Biposinuserstb.findAll", query="SELECT b FROM Biposinuserstb b")
public class Biposinuserstb implements Serializable {
	private static final long serialVersionUID = 1L;

//	@Id
	private long posid;

	private String applytime;

	private BigDecimal dmpamt;

	private String selltype;

	private BigDecimal userid;

	private String valperiod;

	public Biposinuserstb() {
	}

	public long getPosid() {
		return this.posid;
	}

	public void setPosid(long posid) {
		this.posid = posid;
	}

	public String getApplytime() {
		return this.applytime;
	}

	public void setApplytime(String applytime) {
		this.applytime = applytime;
	}

	public BigDecimal getDmpamt() {
		return this.dmpamt;
	}

	public void setDmpamt(BigDecimal dmpamt) {
		this.dmpamt = dmpamt;
	}

	public String getSelltype() {
		return this.selltype;
	}

	public void setSelltype(String selltype) {
		this.selltype = selltype;
	}

	public BigDecimal getUserid() {
		return this.userid;
	}

	public void setUserid(BigDecimal userid) {
		this.userid = userid;
	}

	public String getValperiod() {
		return this.valperiod;
	}

	public void setValperiod(String valperiod) {
		this.valperiod = valperiod;
	}

}