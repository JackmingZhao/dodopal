package com.dodopal.transfer.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the BISALEDISCOUNT database table.
 * 
 */
//@Entity
//@NamedQuery(name="Bisalediscount.findAll", query="SELECT b FROM Bisalediscount b")
public class Bisalediscount implements Serializable {
	private static final long serialVersionUID = 1L;

//	@Id
	private long saleid;

	private String enddate;

	private String endtime;

	private String mchnitid;

	private BigDecimal sale;

	private BigDecimal setsale;

	private String startdate;

	private String starttime;

//	@Column(name="\"WEEK\"")
	private String week;

	public Bisalediscount() {
	}

	public long getSaleid() {
		return this.saleid;
	}

	public void setSaleid(long saleid) {
		this.saleid = saleid;
	}

	public String getEnddate() {
		return this.enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public String getEndtime() {
		return this.endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getMchnitid() {
		return this.mchnitid;
	}

	public void setMchnitid(String mchnitid) {
		this.mchnitid = mchnitid;
	}

	public BigDecimal getSale() {
		return this.sale;
	}

	public void setSale(BigDecimal sale) {
		this.sale = sale;
	}

	public BigDecimal getSetsale() {
		return this.setsale;
	}

	public void setSetsale(BigDecimal setsale) {
		this.setsale = setsale;
	}

	public String getStartdate() {
		return this.startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getStarttime() {
		return this.starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getWeek() {
		return this.week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

}