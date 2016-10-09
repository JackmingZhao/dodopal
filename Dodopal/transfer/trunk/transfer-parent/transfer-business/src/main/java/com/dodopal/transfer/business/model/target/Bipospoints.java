package com.dodopal.transfer.business.model.target;

import java.io.Serializable;
import java.math.BigDecimal;

public class Bipospoints implements Serializable{

	private static final long serialVersionUID = -4404922773429633301L;

	private long pointsid;

	private BigDecimal applytime;

	private BigDecimal flag;

	private String pointsdes;

	private String pointsname;

	private String pointspara;

	private BigDecimal pointstxn;

	public long getPointsid() {
		return pointsid;
	}

	public void setPointsid(long pointsid) {
		this.pointsid = pointsid;
	}

	public BigDecimal getApplytime() {
		return applytime;
	}

	public void setApplytime(BigDecimal applytime) {
		this.applytime = applytime;
	}

	public BigDecimal getFlag() {
		return flag;
	}

	public void setFlag(BigDecimal flag) {
		this.flag = flag;
	}

	public String getPointsdes() {
		return pointsdes;
	}

	public void setPointsdes(String pointsdes) {
		this.pointsdes = pointsdes;
	}

	public String getPointsname() {
		return pointsname;
	}

	public void setPointsname(String pointsname) {
		this.pointsname = pointsname;
	}

	public String getPointspara() {
		return pointspara;
	}

	public void setPointspara(String pointspara) {
		this.pointspara = pointspara;
	}

	public BigDecimal getPointstxn() {
		return pointstxn;
	}

	public void setPointstxn(BigDecimal pointstxn) {
		this.pointstxn = pointstxn;
	}
	
}
