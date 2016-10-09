package com.dodopal.transfer.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the BIPOSPOINTS database table.
 * 
 */
//@Entity
//@Table(name="BIPOSPOINTS")
//@NamedQuery(name="Bipospoint.findAll", query="SELECT b FROM Bipospoint b")
public class Bipospoint implements Serializable {
	private static final long serialVersionUID = 1L;

//	@Id
	private long pointsid;

	private BigDecimal applytime;

	private BigDecimal flag;

	private String pointsdes;

	private String pointsname;

	private String pointspara;

	private BigDecimal pointstxn;

	public Bipospoint() {
	}

	public long getPointsid() {
		return this.pointsid;
	}

	public void setPointsid(long pointsid) {
		this.pointsid = pointsid;
	}

	public BigDecimal getApplytime() {
		return this.applytime;
	}

	public void setApplytime(BigDecimal applytime) {
		this.applytime = applytime;
	}

	public BigDecimal getFlag() {
		return this.flag;
	}

	public void setFlag(BigDecimal flag) {
		this.flag = flag;
	}

	public String getPointsdes() {
		return this.pointsdes;
	}

	public void setPointsdes(String pointsdes) {
		this.pointsdes = pointsdes;
	}

	public String getPointsname() {
		return this.pointsname;
	}

	public void setPointsname(String pointsname) {
		this.pointsname = pointsname;
	}

	public String getPointspara() {
		return this.pointspara;
	}

	public void setPointspara(String pointspara) {
		this.pointspara = pointspara;
	}

	public BigDecimal getPointstxn() {
		return this.pointstxn;
	}

	public void setPointstxn(BigDecimal pointstxn) {
		this.pointstxn = pointstxn;
	}

}