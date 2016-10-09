package com.dodopal.transfernew.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the PROXYINFOTB database table.
 * 
 */
//@Entity
//@NamedQuery(name="Proxyinfotb.findAll", query="SELECT p FROM Proxyinfotb p")
public class Proxyinfotb implements Serializable {
	private static final long serialVersionUID = 1L;

//	@Id
	private long proxyid;

	private BigDecimal amtfee;

	private String approvaldate;

	private String approvaltime;

	private String bankacc;

	private String bankaccname;

	private String bankaccountname;

	private String cityno;

	private String cycletype;

	private String edittime;

	private String edituser;

	private String feetype;

	private BigDecimal obaldate;

	private BigDecimal payflag;

	private BigDecimal posid;

	private String proxyaddress;

	private String proxyfax;

	private String proxyname;

	private String proxytel;

	private BigDecimal proxytypeid;

	private BigDecimal rateamt;

	private String registdate;

	private String registtime;

	private BigDecimal remitmode;

	private String setcycle;

	private BigDecimal setpara;

	private BigDecimal status;

	private String zipcode;

	public Proxyinfotb() {
	}

	public long getProxyid() {
		return this.proxyid;
	}

	public void setProxyid(long proxyid) {
		this.proxyid = proxyid;
	}

	public BigDecimal getAmtfee() {
		return this.amtfee;
	}

	public void setAmtfee(BigDecimal amtfee) {
		this.amtfee = amtfee;
	}

	public String getApprovaldate() {
		return this.approvaldate;
	}

	public void setApprovaldate(String approvaldate) {
		this.approvaldate = approvaldate;
	}

	public String getApprovaltime() {
		return this.approvaltime;
	}

	public void setApprovaltime(String approvaltime) {
		this.approvaltime = approvaltime;
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

	public String getBankaccountname() {
		return this.bankaccountname;
	}

	public void setBankaccountname(String bankaccountname) {
		this.bankaccountname = bankaccountname;
	}

	public String getCityno() {
		return this.cityno;
	}

	public void setCityno(String cityno) {
		this.cityno = cityno;
	}

	public String getCycletype() {
		return this.cycletype;
	}

	public void setCycletype(String cycletype) {
		this.cycletype = cycletype;
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

	public String getFeetype() {
		return this.feetype;
	}

	public void setFeetype(String feetype) {
		this.feetype = feetype;
	}

	public BigDecimal getObaldate() {
		return this.obaldate;
	}

	public void setObaldate(BigDecimal obaldate) {
		this.obaldate = obaldate;
	}

	public BigDecimal getPayflag() {
		return this.payflag;
	}

	public void setPayflag(BigDecimal payflag) {
		this.payflag = payflag;
	}

	public BigDecimal getPosid() {
		return this.posid;
	}

	public void setPosid(BigDecimal posid) {
		this.posid = posid;
	}

	public String getProxyaddress() {
		return this.proxyaddress;
	}

	public void setProxyaddress(String proxyaddress) {
		this.proxyaddress = proxyaddress;
	}

	public String getProxyfax() {
		return this.proxyfax;
	}

	public void setProxyfax(String proxyfax) {
		this.proxyfax = proxyfax;
	}

	public String getProxyname() {
		return this.proxyname;
	}

	public void setProxyname(String proxyname) {
		this.proxyname = proxyname;
	}

	public String getProxytel() {
		return this.proxytel;
	}

	public void setProxytel(String proxytel) {
		this.proxytel = proxytel;
	}

	public BigDecimal getProxytypeid() {
		return this.proxytypeid;
	}

	public void setProxytypeid(BigDecimal proxytypeid) {
		this.proxytypeid = proxytypeid;
	}

	public BigDecimal getRateamt() {
		return this.rateamt;
	}

	public void setRateamt(BigDecimal rateamt) {
		this.rateamt = rateamt;
	}

	public String getRegistdate() {
		return this.registdate;
	}

	public void setRegistdate(String registdate) {
		this.registdate = registdate;
	}

	public String getRegisttime() {
		return this.registtime;
	}

	public void setRegisttime(String registtime) {
		this.registtime = registtime;
	}

	public BigDecimal getRemitmode() {
		return this.remitmode;
	}

	public void setRemitmode(BigDecimal remitmode) {
		this.remitmode = remitmode;
	}

	public String getSetcycle() {
		return this.setcycle;
	}

	public void setSetcycle(String setcycle) {
		this.setcycle = setcycle;
	}

	public BigDecimal getSetpara() {
		return this.setpara;
	}

	public void setSetpara(BigDecimal setpara) {
		this.setpara = setpara;
	}

	public BigDecimal getStatus() {
		return this.status;
	}

	public void setStatus(BigDecimal status) {
		this.status = status;
	}

	public String getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

}