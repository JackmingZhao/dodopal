package com.dodopal.transfer.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the BICOSTDISDETAIL database table.
 * 
 */
//@Entity
//@NamedQuery(name="Bicostdisdetail.findAll", query="SELECT b FROM Bicostdisdetail b")
public class Bicostdisdetail implements Serializable {
	private static final long serialVersionUID = 1L;

	private String endtime;

	private String mchnitid;

	private BigDecimal sale;

	private String saledate;

	private BigDecimal saleid;

	private BigDecimal setsale;

	private String starttime;
	
	private String posid;
	

	public String getPosid() {
        return posid;
    }

    public void setPosid(String posid) {
        this.posid = posid;
    }

    public Bicostdisdetail() {
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

	public String getSaledate() {
		return this.saledate;
	}

	public void setSaledate(String saledate) {
		this.saledate = saledate;
	}

	public BigDecimal getSaleid() {
		return this.saleid;
	}

	public void setSaleid(BigDecimal saleid) {
		this.saleid = saleid;
	}

	public BigDecimal getSetsale() {
		return this.setsale;
	}

	public void setSetsale(BigDecimal setsale) {
		this.setsale = setsale;
	}

	public String getStarttime() {
		return this.starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

}