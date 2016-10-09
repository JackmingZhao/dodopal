package com.dodopal.transfernew.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the TLCOUPONDEFINE database table.
 * 
 */
//@Entity
//@NamedQuery(name="Tlcoupondefine.findAll", query="SELECT t FROM Tlcoupondefine t")
public class Tlcoupondefine implements Serializable {
	private static final long serialVersionUID = 1L;

//	@Id
	private String activeid;

	private String activename;

	private BigDecimal activestartdate;

	private BigDecimal activestarttime;

	private BigDecimal activestopdate;

	private BigDecimal activestoptime;

	private BigDecimal amount;

	private BigDecimal amt;

	private BigDecimal factamount;

	private BigDecimal factprice;

//	@Column(name="\"LIMIT\"")
	private BigDecimal limit;

	private BigDecimal price;

	private String remarks;

	private String reservecha;

//	@Temporal(TemporalType.DATE)
	private Date reservedate;

	private BigDecimal reservendate;

	private BigDecimal reserventime;

	private BigDecimal reservenum;

	private String reservevar;

	private String status;

	private BigDecimal updatedate;

	private BigDecimal updatetime;

	private String updateuser;

	public Tlcoupondefine() {
	}

	public String getActiveid() {
		return this.activeid;
	}

	public void setActiveid(String activeid) {
		this.activeid = activeid;
	}

	public String getActivename() {
		return this.activename;
	}

	public void setActivename(String activename) {
		this.activename = activename;
	}

	public BigDecimal getActivestartdate() {
		return this.activestartdate;
	}

	public void setActivestartdate(BigDecimal activestartdate) {
		this.activestartdate = activestartdate;
	}

	public BigDecimal getActivestarttime() {
		return this.activestarttime;
	}

	public void setActivestarttime(BigDecimal activestarttime) {
		this.activestarttime = activestarttime;
	}

	public BigDecimal getActivestopdate() {
		return this.activestopdate;
	}

	public void setActivestopdate(BigDecimal activestopdate) {
		this.activestopdate = activestopdate;
	}

	public BigDecimal getActivestoptime() {
		return this.activestoptime;
	}

	public void setActivestoptime(BigDecimal activestoptime) {
		this.activestoptime = activestoptime;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getAmt() {
		return this.amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	public BigDecimal getFactamount() {
		return this.factamount;
	}

	public void setFactamount(BigDecimal factamount) {
		this.factamount = factamount;
	}

	public BigDecimal getFactprice() {
		return this.factprice;
	}

	public void setFactprice(BigDecimal factprice) {
		this.factprice = factprice;
	}

	public BigDecimal getLimit() {
		return this.limit;
	}

	public void setLimit(BigDecimal limit) {
		this.limit = limit;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getReservecha() {
		return this.reservecha;
	}

	public void setReservecha(String reservecha) {
		this.reservecha = reservecha;
	}

	public Date getReservedate() {
		return this.reservedate;
	}

	public void setReservedate(Date reservedate) {
		this.reservedate = reservedate;
	}

	public BigDecimal getReservendate() {
		return this.reservendate;
	}

	public void setReservendate(BigDecimal reservendate) {
		this.reservendate = reservendate;
	}

	public BigDecimal getReserventime() {
		return this.reserventime;
	}

	public void setReserventime(BigDecimal reserventime) {
		this.reserventime = reserventime;
	}

	public BigDecimal getReservenum() {
		return this.reservenum;
	}

	public void setReservenum(BigDecimal reservenum) {
		this.reservenum = reservenum;
	}

	public String getReservevar() {
		return this.reservevar;
	}

	public void setReservevar(String reservevar) {
		this.reservevar = reservevar;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getUpdatedate() {
		return this.updatedate;
	}

	public void setUpdatedate(BigDecimal updatedate) {
		this.updatedate = updatedate;
	}

	public BigDecimal getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(BigDecimal updatetime) {
		this.updatetime = updatetime;
	}

	public String getUpdateuser() {
		return this.updateuser;
	}

	public void setUpdateuser(String updateuser) {
		this.updateuser = updateuser;
	}

}