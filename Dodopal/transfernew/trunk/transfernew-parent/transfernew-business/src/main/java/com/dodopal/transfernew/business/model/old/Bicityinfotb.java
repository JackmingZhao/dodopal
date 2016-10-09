package com.dodopal.transfernew.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the BICITYINFOTB database table.
 * 
 */
//@Entity
//@NamedQuery(name="Bicityinfotb.findAll", query="SELECT b FROM Bicityinfotb b")
public class Bicityinfotb implements Serializable {
	private static final long serialVersionUID = 1L;

//	@Id
	private long cityid;

	private BigDecimal area;

	private String bzcityid;

	private String citycode;

	private String cityname;

	private BigDecimal parentid;

	public Bicityinfotb() {
	}

	public long getCityid() {
		return this.cityid;
	}

	public void setCityid(long cityid) {
		this.cityid = cityid;
	}

	public BigDecimal getArea() {
		return this.area;
	}

	public void setArea(BigDecimal area) {
		this.area = area;
	}

	public String getBzcityid() {
		return this.bzcityid;
	}

	public void setBzcityid(String bzcityid) {
		this.bzcityid = bzcityid;
	}

	public String getCitycode() {
		return this.citycode;
	}

	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}

	public String getCityname() {
		return this.cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public BigDecimal getParentid() {
		return this.parentid;
	}

	public void setParentid(BigDecimal parentid) {
		this.parentid = parentid;
	}

}