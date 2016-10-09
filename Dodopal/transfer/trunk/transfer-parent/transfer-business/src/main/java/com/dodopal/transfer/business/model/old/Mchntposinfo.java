package com.dodopal.transfer.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the MCHNTPOSINFO database table.
 * 
 */
//@Entity
//@NamedQuery(name="Mchntposinfo.findAll", query="SELECT m FROM Mchntposinfo m")
public class Mchntposinfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String lastedittime;

	private String mchnitid;

	private BigDecimal posid;

	private BigDecimal userid;

	public Mchntposinfo() {
	}

	public String getLastedittime() {
		return this.lastedittime;
	}

	public void setLastedittime(String lastedittime) {
		this.lastedittime = lastedittime;
	}

	public String getMchnitid() {
		return this.mchnitid;
	}

	public void setMchnitid(String mchnitid) {
		this.mchnitid = mchnitid;
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