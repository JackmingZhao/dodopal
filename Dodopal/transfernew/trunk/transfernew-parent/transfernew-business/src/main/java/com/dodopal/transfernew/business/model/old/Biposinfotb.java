package com.dodopal.transfernew.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the BIPOSINFOTB database table.
 * 
 */
//@Entity
//@NamedQuery(name="Biposinfotb.findAll", query="SELECT b FROM Biposinfotb b")
public class Biposinfotb implements Serializable {
	private static final long serialVersionUID = 1L;

//	@Id
	private long posid;

	private BigDecimal belongcityid;

	private BigDecimal buycost;

	private BigDecimal cityid;

	private String factorycode;

	private String lasttime;

	private BigDecimal limitnum;

	private BigDecimal maker;

	private String mchnitid;

	private String orderid;

	private String patterncode;

	private String rsvd;

	private String status;

	private BigDecimal unitid;

//	@Column(name="\"VERSION\"")
	private BigDecimal version;

	public Biposinfotb() {
	}

	public long getPosid() {
		return this.posid;
	}

	public void setPosid(long posid) {
		this.posid = posid;
	}

	public BigDecimal getBelongcityid() {
		return this.belongcityid;
	}

	public void setBelongcityid(BigDecimal belongcityid) {
		this.belongcityid = belongcityid;
	}

	public BigDecimal getBuycost() {
		return this.buycost;
	}

	public void setBuycost(BigDecimal buycost) {
		this.buycost = buycost;
	}

	public BigDecimal getCityid() {
		return this.cityid;
	}

	public void setCityid(BigDecimal cityid) {
		this.cityid = cityid;
	}

	public String getFactorycode() {
		return this.factorycode;
	}

	public void setFactorycode(String factorycode) {
		this.factorycode = factorycode;
	}

	public String getLasttime() {
		return this.lasttime;
	}

	public void setLasttime(String lasttime) {
		this.lasttime = lasttime;
	}

	public BigDecimal getLimitnum() {
		return this.limitnum;
	}

	public void setLimitnum(BigDecimal limitnum) {
		this.limitnum = limitnum;
	}

	public BigDecimal getMaker() {
		return this.maker;
	}

	public void setMaker(BigDecimal maker) {
		this.maker = maker;
	}

	public String getMchnitid() {
		return this.mchnitid;
	}

	public void setMchnitid(String mchnitid) {
		this.mchnitid = mchnitid;
	}

	public String getOrderid() {
		return this.orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getPatterncode() {
		return this.patterncode;
	}

	public void setPatterncode(String patterncode) {
		this.patterncode = patterncode;
	}

	public String getRsvd() {
		return this.rsvd;
	}

	public void setRsvd(String rsvd) {
		this.rsvd = rsvd;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getUnitid() {
		return this.unitid;
	}

	public void setUnitid(BigDecimal unitid) {
		this.unitid = unitid;
	}

	public BigDecimal getVersion() {
		return this.version;
	}

	public void setVersion(BigDecimal version) {
		this.version = version;
	}

}