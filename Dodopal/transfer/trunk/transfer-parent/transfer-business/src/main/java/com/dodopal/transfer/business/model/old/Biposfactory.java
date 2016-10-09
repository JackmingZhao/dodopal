package com.dodopal.transfer.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the BIPOSFACTORY database table.
 * 
 */
//@Entity
//@NamedQuery(name="Biposfactory.findAll", query="SELECT b FROM Biposfactory b")
public class Biposfactory implements Serializable {
	private static final long serialVersionUID = 1L;

	private String applytime;

	private String blame;

	private String factoryadd;

	private String factorycode;

	private BigDecimal factoryid;

	private String factoryname;

	private String factorytel;

	private String lasttime;

	public Biposfactory() {
	}

	public String getApplytime() {
		return this.applytime;
	}

	public void setApplytime(String applytime) {
		this.applytime = applytime;
	}

	public String getBlame() {
		return this.blame;
	}

	public void setBlame(String blame) {
		this.blame = blame;
	}

	public String getFactoryadd() {
		return this.factoryadd;
	}

	public void setFactoryadd(String factoryadd) {
		this.factoryadd = factoryadd;
	}

	public String getFactorycode() {
		return this.factorycode;
	}

	public void setFactorycode(String factorycode) {
		this.factorycode = factorycode;
	}

	public BigDecimal getFactoryid() {
		return this.factoryid;
	}

	public void setFactoryid(BigDecimal factoryid) {
		this.factoryid = factoryid;
	}

	public String getFactoryname() {
		return this.factoryname;
	}

	public void setFactoryname(String factoryname) {
		this.factoryname = factoryname;
	}

	public String getFactorytel() {
		return this.factorytel;
	}

	public void setFactorytel(String factorytel) {
		this.factorytel = factorytel;
	}

	public String getLasttime() {
		return this.lasttime;
	}

	public void setLasttime(String lasttime) {
		this.lasttime = lasttime;
	}

}