package com.dodopal.transfernew.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the GROUPINFOTB database table.
 * 
 */
//@Entity
//@NamedQuery(name="Groupinfotb.findAll", query="SELECT g FROM Groupinfotb g")
public class Groupinfotb implements Serializable {
	private static final long serialVersionUID = 1L;

//	@Id
	private long groupid;

	private String address;

//	@Column(name="AUTO_ADD_ARRIVE_MAX_LIMIT")
	private BigDecimal autoAddArriveMaxLimit;

//	@Column(name="AUTO_SUM_PROXY_MAX_LIMIT")
	private BigDecimal autoSumProxyMaxLimit;

	private BigDecimal bal;

	private String cityno;

	private BigDecimal czzbs;

	private String edittime;

	private String edituser;

	private String groupname;

	private BigDecimal grouptypeid;

	private String identityid;

	private BigDecimal isinfo;

	private String lastipaddreess;

	private String lastlogintime;

	private BigDecimal lastloginuserid;

	private String lxmobile;

	private String lxperson;

	private String lxtel;

	private BigDecimal point;

	private BigDecimal rateamt;

	private String registtime;

	private String remarks;

	private String starttime;

	private BigDecimal status;

	private String topayedupwd;

	private BigDecimal xfzbs;

	private String zh;

	private String zipcode;

	public Groupinfotb() {
	}

	public long getGroupid() {
		return this.groupid;
	}

	public void setGroupid(long groupid) {
		this.groupid = groupid;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BigDecimal getAutoAddArriveMaxLimit() {
		return this.autoAddArriveMaxLimit;
	}

	public void setAutoAddArriveMaxLimit(BigDecimal autoAddArriveMaxLimit) {
		this.autoAddArriveMaxLimit = autoAddArriveMaxLimit;
	}

	public BigDecimal getAutoSumProxyMaxLimit() {
		return this.autoSumProxyMaxLimit;
	}

	public void setAutoSumProxyMaxLimit(BigDecimal autoSumProxyMaxLimit) {
		this.autoSumProxyMaxLimit = autoSumProxyMaxLimit;
	}

	public BigDecimal getBal() {
		return this.bal;
	}

	public void setBal(BigDecimal bal) {
		this.bal = bal;
	}

	public String getCityno() {
		return this.cityno;
	}

	public void setCityno(String cityno) {
		this.cityno = cityno;
	}

	public BigDecimal getCzzbs() {
		return this.czzbs;
	}

	public void setCzzbs(BigDecimal czzbs) {
		this.czzbs = czzbs;
	}

	public String getEdittime() {
		return this.edittime;
	}

	public void setEdittime(String edittime) {
		this.edittime = edittime;
	}

	public String getEdituser() {
		return this.edituser;
	}

	public void setEdituser(String edituser) {
		this.edituser = edituser;
	}

	public String getGroupname() {
		return this.groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public BigDecimal getGrouptypeid() {
		return this.grouptypeid;
	}

	public void setGrouptypeid(BigDecimal grouptypeid) {
		this.grouptypeid = grouptypeid;
	}

	public String getIdentityid() {
		return this.identityid;
	}

	public void setIdentityid(String identityid) {
		this.identityid = identityid;
	}

	public BigDecimal getIsinfo() {
		return this.isinfo;
	}

	public void setIsinfo(BigDecimal isinfo) {
		this.isinfo = isinfo;
	}

	public String getLastipaddreess() {
		return this.lastipaddreess;
	}

	public void setLastipaddreess(String lastipaddreess) {
		this.lastipaddreess = lastipaddreess;
	}

	public String getLastlogintime() {
		return this.lastlogintime;
	}

	public void setLastlogintime(String lastlogintime) {
		this.lastlogintime = lastlogintime;
	}

	public BigDecimal getLastloginuserid() {
		return this.lastloginuserid;
	}

	public void setLastloginuserid(BigDecimal lastloginuserid) {
		this.lastloginuserid = lastloginuserid;
	}

	public String getLxmobile() {
		return this.lxmobile;
	}

	public void setLxmobile(String lxmobile) {
		this.lxmobile = lxmobile;
	}

	public String getLxperson() {
		return this.lxperson;
	}

	public void setLxperson(String lxperson) {
		this.lxperson = lxperson;
	}

	public String getLxtel() {
		return this.lxtel;
	}

	public void setLxtel(String lxtel) {
		this.lxtel = lxtel;
	}

	public BigDecimal getPoint() {
		return this.point;
	}

	public void setPoint(BigDecimal point) {
		this.point = point;
	}

	public BigDecimal getRateamt() {
		return this.rateamt;
	}

	public void setRateamt(BigDecimal rateamt) {
		this.rateamt = rateamt;
	}

	public String getRegisttime() {
		return this.registtime;
	}

	public void setRegisttime(String registtime) {
		this.registtime = registtime;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getStarttime() {
		return this.starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public BigDecimal getStatus() {
		return this.status;
	}

	public void setStatus(BigDecimal status) {
		this.status = status;
	}

	public String getTopayedupwd() {
		return this.topayedupwd;
	}

	public void setTopayedupwd(String topayedupwd) {
		this.topayedupwd = topayedupwd;
	}

	public BigDecimal getXfzbs() {
		return this.xfzbs;
	}

	public void setXfzbs(BigDecimal xfzbs) {
		this.xfzbs = xfzbs;
	}

	public String getZh() {
		return this.zh;
	}

	public void setZh(String zh) {
		this.zh = zh;
	}

	public String getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

}