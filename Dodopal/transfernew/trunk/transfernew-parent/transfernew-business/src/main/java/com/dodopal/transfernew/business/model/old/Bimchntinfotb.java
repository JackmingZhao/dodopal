package com.dodopal.transfernew.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the BIMCHNTINFOTB database table.
 * 
 */
//@Entity
//@NamedQuery(name="Bimchntinfotb.findAll", query="SELECT b FROM Bimchntinfotb b")
public class Bimchntinfotb implements Serializable {
	private static final long serialVersionUID = 1L;

//	@Id
	private String mchnitid;

	private String address;

	private BigDecimal areaid;

	private String backpaypwd;

	private String bankacc;

	private String bankaccname;

	private BigDecimal biunittbpkid;

	private BigDecimal cityid;

	private String fax;

	private String headflag;

	private String lasttime;

	private String linkman;

	private String mchnitip;

	private String mchnitname;

	private BigDecimal mchzcflag;

	private String orderurl;

	private String parentmchnitid;

	private String paypwd;

	private BigDecimal point;

	private BigDecimal posfalg;

	private BigDecimal provinceid;

	private String queryurl;

	private BigDecimal rateamt;

	private String returnurl;

	private BigDecimal sale;

	private BigDecimal salebak;

	private BigDecimal setsale;

	private BigDecimal setsalebak;

	private String status;

	private String tel;

	private String tradeid;

	private String transfermark;

	private BigDecimal transfertype;

	private String unioncode;

	private String zipcode;

	public Bimchntinfotb() {
	}

	public String getMchnitid() {
		return this.mchnitid;
	}

	public void setMchnitid(String mchnitid) {
		this.mchnitid = mchnitid;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BigDecimal getAreaid() {
		return this.areaid;
	}

	public void setAreaid(BigDecimal areaid) {
		this.areaid = areaid;
	}

	public String getBackpaypwd() {
		return this.backpaypwd;
	}

	public void setBackpaypwd(String backpaypwd) {
		this.backpaypwd = backpaypwd;
	}

	public String getBankacc() {
		return this.bankacc;
	}

	public void setBankacc(String bankacc) {
		this.bankacc = bankacc;
	}

	public String getBankaccname() {
		return this.bankaccname;
	}

	public void setBankaccname(String bankaccname) {
		this.bankaccname = bankaccname;
	}

	public BigDecimal getBiunittbpkid() {
		return this.biunittbpkid;
	}

	public void setBiunittbpkid(BigDecimal biunittbpkid) {
		this.biunittbpkid = biunittbpkid;
	}

	public BigDecimal getCityid() {
		return this.cityid;
	}

	public void setCityid(BigDecimal cityid) {
		this.cityid = cityid;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getHeadflag() {
		return this.headflag;
	}

	public void setHeadflag(String headflag) {
		this.headflag = headflag;
	}

	public String getLasttime() {
		return this.lasttime;
	}

	public void setLasttime(String lasttime) {
		this.lasttime = lasttime;
	}

	public String getLinkman() {
		return this.linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getMchnitip() {
		return this.mchnitip;
	}

	public void setMchnitip(String mchnitip) {
		this.mchnitip = mchnitip;
	}

	public String getMchnitname() {
		return this.mchnitname;
	}

	public void setMchnitname(String mchnitname) {
		this.mchnitname = mchnitname;
	}

	public BigDecimal getMchzcflag() {
		return this.mchzcflag;
	}

	public void setMchzcflag(BigDecimal mchzcflag) {
		this.mchzcflag = mchzcflag;
	}

	public String getOrderurl() {
		return this.orderurl;
	}

	public void setOrderurl(String orderurl) {
		this.orderurl = orderurl;
	}

	public String getParentmchnitid() {
		return this.parentmchnitid;
	}

	public void setParentmchnitid(String parentmchnitid) {
		this.parentmchnitid = parentmchnitid;
	}

	public String getPaypwd() {
		return this.paypwd;
	}

	public void setPaypwd(String paypwd) {
		this.paypwd = paypwd;
	}

	public BigDecimal getPoint() {
		return this.point;
	}

	public void setPoint(BigDecimal point) {
		this.point = point;
	}

	public BigDecimal getPosfalg() {
		return this.posfalg;
	}

	public void setPosfalg(BigDecimal posfalg) {
		this.posfalg = posfalg;
	}

	public BigDecimal getProvinceid() {
		return this.provinceid;
	}

	public void setProvinceid(BigDecimal provinceid) {
		this.provinceid = provinceid;
	}

	public String getQueryurl() {
		return this.queryurl;
	}

	public void setQueryurl(String queryurl) {
		this.queryurl = queryurl;
	}

	public BigDecimal getRateamt() {
		return this.rateamt;
	}

	public void setRateamt(BigDecimal rateamt) {
		this.rateamt = rateamt;
	}

	public String getReturnurl() {
		return this.returnurl;
	}

	public void setReturnurl(String returnurl) {
		this.returnurl = returnurl;
	}

	public BigDecimal getSale() {
		return this.sale;
	}

	public void setSale(BigDecimal sale) {
		this.sale = sale;
	}

	public BigDecimal getSalebak() {
		return this.salebak;
	}

	public void setSalebak(BigDecimal salebak) {
		this.salebak = salebak;
	}

	public BigDecimal getSetsale() {
		return this.setsale;
	}

	public void setSetsale(BigDecimal setsale) {
		this.setsale = setsale;
	}

	public BigDecimal getSetsalebak() {
		return this.setsalebak;
	}

	public void setSetsalebak(BigDecimal setsalebak) {
		this.setsalebak = setsalebak;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getTradeid() {
		return this.tradeid;
	}

	public void setTradeid(String tradeid) {
		this.tradeid = tradeid;
	}

	public String getTransfermark() {
		return this.transfermark;
	}

	public void setTransfermark(String transfermark) {
		this.transfermark = transfermark;
	}

	public BigDecimal getTransfertype() {
		return this.transfertype;
	}

	public void setTransfertype(BigDecimal transfertype) {
		this.transfertype = transfertype;
	}

	public String getUnioncode() {
		return this.unioncode;
	}

	public void setUnioncode(String unioncode) {
		this.unioncode = unioncode;
	}

	public String getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

}