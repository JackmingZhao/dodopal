package com.dodopal.transfernew.business.model.transfer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Tlcouponinfo implements Serializable{

	private static final long serialVersionUID = -5675737744017095370L;

	// 老系统使用
	private String couponid;
	// 老系统使用
	private String mchnitid;
	// 老系统使用
	private String userid;
	// 优惠券总数
	private int couponAccountCount;
	// 已使用优惠券
	private int useAccountCount;
	// 剩余优惠券
	private int surplusAccountCount;

	private String id;

	private String activeid;

	private String couponcode;
	
	private String couponAccountcode;
	
	private String merCode;
	
	private String sorderid;
	
	private String sorderstates;
	
	private String corderid;
	
	private String corderstates;
	
	private String userCode;
	
	private String status;
	
	private BigDecimal amt;

	private BigDecimal borndate;

	private BigDecimal borntime;

	private BigDecimal finishdate;

	private BigDecimal finishtime;
	
	private BigDecimal updatedate;

	private BigDecimal updatetime;
	
	private String updateuser;
	
	private String remarks;
	
	private String reservevar;
	
	private String reservecha;
	
	private BigDecimal reservenum;
	
	private BigDecimal reservendate;
	
	private BigDecimal reserventime;

	private Date reservedate;

	private String overduestatus;

	private BigDecimal overduetime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getActiveid() {
		return activeid;
	}

	public void setActiveid(String activeid) {
		this.activeid = activeid;
	}

	public String getCouponcode() {
		return couponcode;
	}

	public void setCouponcode(String couponcode) {
		this.couponcode = couponcode;
	}

	public String getCouponAccountcode() {
		return couponAccountcode;
	}

	public void setCouponAccountcode(String couponAccountcode) {
		this.couponAccountcode = couponAccountcode;
	}

	public String getMerCode() {
		return merCode;
	}

	public void setMerCode(String merCode) {
		this.merCode = merCode;
	}

	public String getSorderid() {
		return sorderid;
	}

	public void setSorderid(String sorderid) {
		this.sorderid = sorderid;
	}

	public String getSorderstates() {
		return sorderstates;
	}

	public void setSorderstates(String sorderstates) {
		this.sorderstates = sorderstates;
	}

	public String getCorderid() {
		return corderid;
	}

	public void setCorderid(String corderid) {
		this.corderid = corderid;
	}

	public String getCorderstates() {
		return corderstates;
	}

	public void setCorderstates(String corderstates) {
		this.corderstates = corderstates;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	public BigDecimal getBorndate() {
		return borndate;
	}

	public void setBorndate(BigDecimal borndate) {
		this.borndate = borndate;
	}

	public BigDecimal getBorntime() {
		return borntime;
	}

	public void setBorntime(BigDecimal borntime) {
		this.borntime = borntime;
	}

	public BigDecimal getFinishdate() {
		return finishdate;
	}

	public void setFinishdate(BigDecimal finishdate) {
		this.finishdate = finishdate;
	}

	public BigDecimal getFinishtime() {
		return finishtime;
	}

	public void setFinishtime(BigDecimal finishtime) {
		this.finishtime = finishtime;
	}

	public BigDecimal getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(BigDecimal updatedate) {
		this.updatedate = updatedate;
	}

	public BigDecimal getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(BigDecimal updatetime) {
		this.updatetime = updatetime;
	}

	public String getUpdateuser() {
		return updateuser;
	}

	public void setUpdateuser(String updateuser) {
		this.updateuser = updateuser;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getReservevar() {
		return reservevar;
	}

	public void setReservevar(String reservevar) {
		this.reservevar = reservevar;
	}

	public String getReservecha() {
		return reservecha;
	}

	public void setReservecha(String reservecha) {
		this.reservecha = reservecha;
	}

	public BigDecimal getReservenum() {
		return reservenum;
	}

	public void setReservenum(BigDecimal reservenum) {
		this.reservenum = reservenum;
	}

	public BigDecimal getReservendate() {
		return reservendate;
	}

	public void setReservendate(BigDecimal reservendate) {
		this.reservendate = reservendate;
	}

	public BigDecimal getReserventime() {
		return reserventime;
	}

	public void setReserventime(BigDecimal reserventime) {
		this.reserventime = reserventime;
	}

	public Date getReservedate() {
		return reservedate;
	}

	public void setReservedate(Date reservedate) {
		this.reservedate = reservedate;
	}

	public String getOverduestatus() {
		return overduestatus;
	}

	public void setOverduestatus(String overduestatus) {
		this.overduestatus = overduestatus;
	}

	public BigDecimal getOverduetime() {
		return overduetime;
	}

	public void setOverduetime(BigDecimal overduetime) {
		this.overduetime = overduetime;
	}

	public String getCouponid() {
		return couponid;
	}

	public void setCouponid(String couponid) {
		this.couponid = couponid;
	}

	public String getMchnitid() {
		return mchnitid;
	}

	public void setMchnitid(String mchnitid) {
		this.mchnitid = mchnitid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

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

}
