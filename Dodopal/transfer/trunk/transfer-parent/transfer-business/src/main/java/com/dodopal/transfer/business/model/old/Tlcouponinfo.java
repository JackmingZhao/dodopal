package com.dodopal.transfer.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the TLCOUPONINFO database table.
 * 
 */
//@Entity
//@NamedQuery(name="Tlcouponinfo.findAll", query="SELECT t FROM Tlcouponinfo t")
public class Tlcouponinfo implements Serializable {
	private static final long serialVersionUID = 1L;

//	@Id
	private String couponid;

	private String activeid;

	private BigDecimal amt;

	private BigDecimal borndate;

	private BigDecimal borntime;

	private String corderid;

	private String corderstates;

	private String couponcode;

	private BigDecimal finishdate;

	private BigDecimal finishtime;

	private String mchnitid;

	private String overduestatus;

	private BigDecimal overduetime;

	private String remarks;

	private String reservecha;

//	@Temporal(TemporalType.DATE)
	private Date reservedate;

	private BigDecimal reservendate;

	private BigDecimal reserventime;

	private BigDecimal reservenum;

	private String reservevar;

	private String sorderid;

	private String sorderstates;

	private String status;

	private BigDecimal updatedate;

	private BigDecimal updatetime;

	private String updateuser;

	private String userid;
	//优惠券总数
	private int couponAccountCount;
	//已使用优惠券
	private int useAccountCount;
	//剩余优惠券
	private int surplusAccountCount;
	
	
	public int getCouponAccountCount() {
		return couponAccountCount;
	}

	public void setCouponAccountCount(int couponAccountCount) {
		this.couponAccountCount = couponAccountCount;
	}

	public int getUseAccountCount() {
		return useAccountCount;
	}

	public void setUseAccountCount(int useAccountCount) {
		this.useAccountCount = useAccountCount;
	}

	public int getSurplusAccountCount() {
		return surplusAccountCount;
	}

	public void setSurplusAccountCount(int surplusAccountCount) {
		this.surplusAccountCount = surplusAccountCount;
	}

	public Tlcouponinfo() {
	}

	public String getCouponid() {
		return this.couponid;
	}

	public void setCouponid(String couponid) {
		this.couponid = couponid;
	}

	public String getActiveid() {
		return this.activeid;
	}

	public void setActiveid(String activeid) {
		this.activeid = activeid;
	}

	public BigDecimal getAmt() {
		return this.amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	public BigDecimal getBorndate() {
		return this.borndate;
	}

	public void setBorndate(BigDecimal borndate) {
		this.borndate = borndate;
	}

	public BigDecimal getBorntime() {
		return this.borntime;
	}

	public void setBorntime(BigDecimal borntime) {
		this.borntime = borntime;
	}

	public String getCorderid() {
		return this.corderid;
	}

	public void setCorderid(String corderid) {
		this.corderid = corderid;
	}

	public String getCorderstates() {
		return this.corderstates;
	}

	public void setCorderstates(String corderstates) {
		this.corderstates = corderstates;
	}

	public String getCouponcode() {
		return this.couponcode;
	}

	public void setCouponcode(String couponcode) {
		this.couponcode = couponcode;
	}

	public BigDecimal getFinishdate() {
		return this.finishdate;
	}

	public void setFinishdate(BigDecimal finishdate) {
		this.finishdate = finishdate;
	}

	public BigDecimal getFinishtime() {
		return this.finishtime;
	}

	public void setFinishtime(BigDecimal finishtime) {
		this.finishtime = finishtime;
	}

	public String getMchnitid() {
		return this.mchnitid;
	}

	public void setMchnitid(String mchnitid) {
		this.mchnitid = mchnitid;
	}

	public String getOverduestatus() {
		return this.overduestatus;
	}

	public void setOverduestatus(String overduestatus) {
		this.overduestatus = overduestatus;
	}

	public BigDecimal getOverduetime() {
		return this.overduetime;
	}

	public void setOverduetime(BigDecimal overduetime) {
		this.overduetime = overduetime;
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

	public String getSorderid() {
		return this.sorderid;
	}

	public void setSorderid(String sorderid) {
		this.sorderid = sorderid;
	}

	public String getSorderstates() {
		return this.sorderstates;
	}

	public void setSorderstates(String sorderstates) {
		this.sorderstates = sorderstates;
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

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

}