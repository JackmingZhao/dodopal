package com.dodopal.transfer.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the TLMCHNTORDER20160114 database table.
 * 
 */
//@Entity
//@NamedQuery(name="Tlmchntorder20160114.findAll", query="SELECT t FROM Tlmchntorder20160114 t")
public class Tlmchntorder20160114 implements Serializable {
	private static final long serialVersionUID = 1L;

//	@Id
	private String orderid;

	private BigDecimal accountoffsets;

	private String aftercardnotes;

	private BigDecimal amountpay;

	private BigDecimal amt;

	private String beforecardnotes;

	private String beforeorderstates;

	private BigDecimal blackamt;

	private String cardcounter;

	private BigDecimal cardkahao;

	private String cardkfhao;

	private BigDecimal cardtype;

	private BigDecimal checkcarddate;

	private String checkcardkey;

	private String checkcardno;

	private BigDecimal checkcardposid;

	private BigDecimal checkcardtime;

	private BigDecimal cityid;

	private BigDecimal commoditycount;

	private String commodityname;

	private BigDecimal couponamount;

	private BigDecimal couponoffsets;

	private String cutpaybfkey;

	private BigDecimal cutpaydate;

	private String cutpaykey;

	private BigDecimal cutpaytime;

	private BigDecimal dounknowflag;

	private BigDecimal facevalue;

	private String mchnitid;

	private String mchnitorderid;

	private String orderkey;

	private String orderserror;

	private String orderstates;

//	@Temporal(TemporalType.DATE)
	private Date ordertime;

	private BigDecimal paidamt;

	private String paypwd;

	private BigDecimal pointvalue;

	private String posremark;

	private String postype;

	private BigDecimal proamt;

	private BigDecimal proxyid;

	private String readcardkey;

	private BigDecimal rebatesamt;

	private String returnurl;

	private BigDecimal sale;

	private String serverurl;

	private BigDecimal setamt;

	private BigDecimal setdate;

	private BigDecimal setfee;

	private BigDecimal setsale;

	private BigDecimal sysupdate;

	private BigDecimal sysuptime;

	private BigDecimal userid;

	private BigDecimal yktid;

	private BigDecimal yktsale;

	public Tlmchntorder20160114() {
	}

	public String getOrderid() {
		return this.orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public BigDecimal getAccountoffsets() {
		return this.accountoffsets;
	}

	public void setAccountoffsets(BigDecimal accountoffsets) {
		this.accountoffsets = accountoffsets;
	}

	public String getAftercardnotes() {
		return this.aftercardnotes;
	}

	public void setAftercardnotes(String aftercardnotes) {
		this.aftercardnotes = aftercardnotes;
	}

	public BigDecimal getAmountpay() {
		return this.amountpay;
	}

	public void setAmountpay(BigDecimal amountpay) {
		this.amountpay = amountpay;
	}

	public BigDecimal getAmt() {
		return this.amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	public String getBeforecardnotes() {
		return this.beforecardnotes;
	}

	public void setBeforecardnotes(String beforecardnotes) {
		this.beforecardnotes = beforecardnotes;
	}

	public String getBeforeorderstates() {
		return this.beforeorderstates;
	}

	public void setBeforeorderstates(String beforeorderstates) {
		this.beforeorderstates = beforeorderstates;
	}

	public BigDecimal getBlackamt() {
		return this.blackamt;
	}

	public void setBlackamt(BigDecimal blackamt) {
		this.blackamt = blackamt;
	}

	public String getCardcounter() {
		return this.cardcounter;
	}

	public void setCardcounter(String cardcounter) {
		this.cardcounter = cardcounter;
	}

	public BigDecimal getCardkahao() {
		return this.cardkahao;
	}

	public void setCardkahao(BigDecimal cardkahao) {
		this.cardkahao = cardkahao;
	}

	public String getCardkfhao() {
		return this.cardkfhao;
	}

	public void setCardkfhao(String cardkfhao) {
		this.cardkfhao = cardkfhao;
	}

	public BigDecimal getCardtype() {
		return this.cardtype;
	}

	public void setCardtype(BigDecimal cardtype) {
		this.cardtype = cardtype;
	}

	public BigDecimal getCheckcarddate() {
		return this.checkcarddate;
	}

	public void setCheckcarddate(BigDecimal checkcarddate) {
		this.checkcarddate = checkcarddate;
	}

	public String getCheckcardkey() {
		return this.checkcardkey;
	}

	public void setCheckcardkey(String checkcardkey) {
		this.checkcardkey = checkcardkey;
	}

	public String getCheckcardno() {
		return this.checkcardno;
	}

	public void setCheckcardno(String checkcardno) {
		this.checkcardno = checkcardno;
	}

	public BigDecimal getCheckcardposid() {
		return this.checkcardposid;
	}

	public void setCheckcardposid(BigDecimal checkcardposid) {
		this.checkcardposid = checkcardposid;
	}

	public BigDecimal getCheckcardtime() {
		return this.checkcardtime;
	}

	public void setCheckcardtime(BigDecimal checkcardtime) {
		this.checkcardtime = checkcardtime;
	}

	public BigDecimal getCityid() {
		return this.cityid;
	}

	public void setCityid(BigDecimal cityid) {
		this.cityid = cityid;
	}

	public BigDecimal getCommoditycount() {
		return this.commoditycount;
	}

	public void setCommoditycount(BigDecimal commoditycount) {
		this.commoditycount = commoditycount;
	}

	public String getCommodityname() {
		return this.commodityname;
	}

	public void setCommodityname(String commodityname) {
		this.commodityname = commodityname;
	}

	public BigDecimal getCouponamount() {
		return this.couponamount;
	}

	public void setCouponamount(BigDecimal couponamount) {
		this.couponamount = couponamount;
	}

	public BigDecimal getCouponoffsets() {
		return this.couponoffsets;
	}

	public void setCouponoffsets(BigDecimal couponoffsets) {
		this.couponoffsets = couponoffsets;
	}

	public String getCutpaybfkey() {
		return this.cutpaybfkey;
	}

	public void setCutpaybfkey(String cutpaybfkey) {
		this.cutpaybfkey = cutpaybfkey;
	}

	public BigDecimal getCutpaydate() {
		return this.cutpaydate;
	}

	public void setCutpaydate(BigDecimal cutpaydate) {
		this.cutpaydate = cutpaydate;
	}

	public String getCutpaykey() {
		return this.cutpaykey;
	}

	public void setCutpaykey(String cutpaykey) {
		this.cutpaykey = cutpaykey;
	}

	public BigDecimal getCutpaytime() {
		return this.cutpaytime;
	}

	public void setCutpaytime(BigDecimal cutpaytime) {
		this.cutpaytime = cutpaytime;
	}

	public BigDecimal getDounknowflag() {
		return this.dounknowflag;
	}

	public void setDounknowflag(BigDecimal dounknowflag) {
		this.dounknowflag = dounknowflag;
	}

	public BigDecimal getFacevalue() {
		return this.facevalue;
	}

	public void setFacevalue(BigDecimal facevalue) {
		this.facevalue = facevalue;
	}

	public String getMchnitid() {
		return this.mchnitid;
	}

	public void setMchnitid(String mchnitid) {
		this.mchnitid = mchnitid;
	}

	public String getMchnitorderid() {
		return this.mchnitorderid;
	}

	public void setMchnitorderid(String mchnitorderid) {
		this.mchnitorderid = mchnitorderid;
	}

	public String getOrderkey() {
		return this.orderkey;
	}

	public void setOrderkey(String orderkey) {
		this.orderkey = orderkey;
	}

	public String getOrderserror() {
		return this.orderserror;
	}

	public void setOrderserror(String orderserror) {
		this.orderserror = orderserror;
	}

	public String getOrderstates() {
		return this.orderstates;
	}

	public void setOrderstates(String orderstates) {
		this.orderstates = orderstates;
	}

	public Date getOrdertime() {
		return this.ordertime;
	}

	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}

	public BigDecimal getPaidamt() {
		return this.paidamt;
	}

	public void setPaidamt(BigDecimal paidamt) {
		this.paidamt = paidamt;
	}

	public String getPaypwd() {
		return this.paypwd;
	}

	public void setPaypwd(String paypwd) {
		this.paypwd = paypwd;
	}

	public BigDecimal getPointvalue() {
		return this.pointvalue;
	}

	public void setPointvalue(BigDecimal pointvalue) {
		this.pointvalue = pointvalue;
	}

	public String getPosremark() {
		return this.posremark;
	}

	public void setPosremark(String posremark) {
		this.posremark = posremark;
	}

	public String getPostype() {
		return this.postype;
	}

	public void setPostype(String postype) {
		this.postype = postype;
	}

	public BigDecimal getProamt() {
		return this.proamt;
	}

	public void setProamt(BigDecimal proamt) {
		this.proamt = proamt;
	}

	public BigDecimal getProxyid() {
		return this.proxyid;
	}

	public void setProxyid(BigDecimal proxyid) {
		this.proxyid = proxyid;
	}

	public String getReadcardkey() {
		return this.readcardkey;
	}

	public void setReadcardkey(String readcardkey) {
		this.readcardkey = readcardkey;
	}

	public BigDecimal getRebatesamt() {
		return this.rebatesamt;
	}

	public void setRebatesamt(BigDecimal rebatesamt) {
		this.rebatesamt = rebatesamt;
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

	public String getServerurl() {
		return this.serverurl;
	}

	public void setServerurl(String serverurl) {
		this.serverurl = serverurl;
	}

	public BigDecimal getSetamt() {
		return this.setamt;
	}

	public void setSetamt(BigDecimal setamt) {
		this.setamt = setamt;
	}

	public BigDecimal getSetdate() {
		return this.setdate;
	}

	public void setSetdate(BigDecimal setdate) {
		this.setdate = setdate;
	}

	public BigDecimal getSetfee() {
		return this.setfee;
	}

	public void setSetfee(BigDecimal setfee) {
		this.setfee = setfee;
	}

	public BigDecimal getSetsale() {
		return this.setsale;
	}

	public void setSetsale(BigDecimal setsale) {
		this.setsale = setsale;
	}

	public BigDecimal getSysupdate() {
		return this.sysupdate;
	}

	public void setSysupdate(BigDecimal sysupdate) {
		this.sysupdate = sysupdate;
	}

	public BigDecimal getSysuptime() {
		return this.sysuptime;
	}

	public void setSysuptime(BigDecimal sysuptime) {
		this.sysuptime = sysuptime;
	}

	public BigDecimal getUserid() {
		return this.userid;
	}

	public void setUserid(BigDecimal userid) {
		this.userid = userid;
	}

	public BigDecimal getYktid() {
		return this.yktid;
	}

	public void setYktid(BigDecimal yktid) {
		this.yktid = yktid;
	}

	public BigDecimal getYktsale() {
		return this.yktsale;
	}

	public void setYktsale(BigDecimal yktsale) {
		this.yktsale = yktsale;
	}

}