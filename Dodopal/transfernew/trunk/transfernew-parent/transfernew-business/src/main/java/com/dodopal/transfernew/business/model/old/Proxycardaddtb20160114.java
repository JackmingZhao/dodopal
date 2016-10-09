package com.dodopal.transfernew.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the PROXYCARDADDTB20160114 database table.
 * 
 */
//@Entity
//@NamedQuery(name="Proxycardaddtb20160114.findAll", query="SELECT p FROM Proxycardaddtb20160114 p")
public class Proxycardaddtb20160114 implements Serializable {
	private static final long serialVersionUID = 1L;

//	@Id
	private String proxyorderno;

	private BigDecimal aftsurpluslimit;

	private BigDecimal befsurpluslimit;

	private BigDecimal blackamt;

	private String cardkfhao;

	private String cardno;

	private BigDecimal cityid;

	private BigDecimal couponamt;

	private String loginname;

	private BigDecimal paidamt;

	private BigDecimal posid;

	private BigDecimal posseq1;

	private BigDecimal proamt;

	private BigDecimal proxyid;

	private BigDecimal rebatesamt;

	private String remarks;

	private BigDecimal status;

	private BigDecimal txnamt;

	private String txndate;

	private String txntime;

	private String verifymd5;

	private BigDecimal yktid;

	public Proxycardaddtb20160114() {
	}

	public String getProxyorderno() {
		return this.proxyorderno;
	}

	public void setProxyorderno(String proxyorderno) {
		this.proxyorderno = proxyorderno;
	}

	public BigDecimal getAftsurpluslimit() {
		return this.aftsurpluslimit;
	}

	public void setAftsurpluslimit(BigDecimal aftsurpluslimit) {
		this.aftsurpluslimit = aftsurpluslimit;
	}

	public BigDecimal getBefsurpluslimit() {
		return this.befsurpluslimit;
	}

	public void setBefsurpluslimit(BigDecimal befsurpluslimit) {
		this.befsurpluslimit = befsurpluslimit;
	}

	public BigDecimal getBlackamt() {
		return this.blackamt;
	}

	public void setBlackamt(BigDecimal blackamt) {
		this.blackamt = blackamt;
	}

	public String getCardkfhao() {
		return this.cardkfhao;
	}

	public void setCardkfhao(String cardkfhao) {
		this.cardkfhao = cardkfhao;
	}

	public String getCardno() {
		return this.cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public BigDecimal getCityid() {
		return this.cityid;
	}

	public void setCityid(BigDecimal cityid) {
		this.cityid = cityid;
	}

	public BigDecimal getCouponamt() {
		return this.couponamt;
	}

	public void setCouponamt(BigDecimal couponamt) {
		this.couponamt = couponamt;
	}

	public String getLoginname() {
		return this.loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public BigDecimal getPaidamt() {
		return this.paidamt;
	}

	public void setPaidamt(BigDecimal paidamt) {
		this.paidamt = paidamt;
	}

	public BigDecimal getPosid() {
		return this.posid;
	}

	public void setPosid(BigDecimal posid) {
		this.posid = posid;
	}

	public BigDecimal getPosseq1() {
		return this.posseq1;
	}

	public void setPosseq1(BigDecimal posseq1) {
		this.posseq1 = posseq1;
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

	public BigDecimal getRebatesamt() {
		return this.rebatesamt;
	}

	public void setRebatesamt(BigDecimal rebatesamt) {
		this.rebatesamt = rebatesamt;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public BigDecimal getStatus() {
		return this.status;
	}

	public void setStatus(BigDecimal status) {
		this.status = status;
	}

	public BigDecimal getTxnamt() {
		return this.txnamt;
	}

	public void setTxnamt(BigDecimal txnamt) {
		this.txnamt = txnamt;
	}

	public String getTxndate() {
		return this.txndate;
	}

	public void setTxndate(String txndate) {
		this.txndate = txndate;
	}

	public String getTxntime() {
		return this.txntime;
	}

	public void setTxntime(String txntime) {
		this.txntime = txntime;
	}

	public String getVerifymd5() {
		return this.verifymd5;
	}

	public void setVerifymd5(String verifymd5) {
		this.verifymd5 = verifymd5;
	}

	public BigDecimal getYktid() {
		return this.yktid;
	}

	public void setYktid(BigDecimal yktid) {
		this.yktid = yktid;
	}

}