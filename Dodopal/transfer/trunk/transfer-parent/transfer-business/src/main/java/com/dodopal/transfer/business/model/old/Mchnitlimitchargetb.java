package com.dodopal.transfer.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the MCHNITLIMITCHARGETB database table.
 * 
 */
//@Entity
//@NamedQuery(name="Mchnitlimitchargetb.findAll", query="SELECT m FROM Mchnitlimitchargetb m")
public class Mchnitlimitchargetb implements Serializable {
	private static final long serialVersionUID = 1L;

	private String adddate;

	private String addmember;

	private String addtime;

	private BigDecimal aftsurpluslimit;

	private String appdate;

	private String applyorderid;

	private String appmember;

	private String apptime;

	private String backdate;

	private String backmember;

	private String backtime;

	private BigDecimal batch;

	private BigDecimal befsurpluslimit;

	private BigDecimal flag;

	private BigDecimal limitamt;

	private String mchnitid;

	private BigDecimal paymoney;

	private String remarks;

	public Mchnitlimitchargetb() {
	}

	public String getAdddate() {
		return this.adddate;
	}

	public void setAdddate(String adddate) {
		this.adddate = adddate;
	}

	public String getAddmember() {
		return this.addmember;
	}

	public void setAddmember(String addmember) {
		this.addmember = addmember;
	}

	public String getAddtime() {
		return this.addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public BigDecimal getAftsurpluslimit() {
		return this.aftsurpluslimit;
	}

	public void setAftsurpluslimit(BigDecimal aftsurpluslimit) {
		this.aftsurpluslimit = aftsurpluslimit;
	}

	public String getAppdate() {
		return this.appdate;
	}

	public void setAppdate(String appdate) {
		this.appdate = appdate;
	}

	public String getApplyorderid() {
		return this.applyorderid;
	}

	public void setApplyorderid(String applyorderid) {
		this.applyorderid = applyorderid;
	}

	public String getAppmember() {
		return this.appmember;
	}

	public void setAppmember(String appmember) {
		this.appmember = appmember;
	}

	public String getApptime() {
		return this.apptime;
	}

	public void setApptime(String apptime) {
		this.apptime = apptime;
	}

	public String getBackdate() {
		return this.backdate;
	}

	public void setBackdate(String backdate) {
		this.backdate = backdate;
	}

	public String getBackmember() {
		return this.backmember;
	}

	public void setBackmember(String backmember) {
		this.backmember = backmember;
	}

	public String getBacktime() {
		return this.backtime;
	}

	public void setBacktime(String backtime) {
		this.backtime = backtime;
	}

	public BigDecimal getBatch() {
		return this.batch;
	}

	public void setBatch(BigDecimal batch) {
		this.batch = batch;
	}

	public BigDecimal getBefsurpluslimit() {
		return this.befsurpluslimit;
	}

	public void setBefsurpluslimit(BigDecimal befsurpluslimit) {
		this.befsurpluslimit = befsurpluslimit;
	}

	public BigDecimal getFlag() {
		return this.flag;
	}

	public void setFlag(BigDecimal flag) {
		this.flag = flag;
	}

	public BigDecimal getLimitamt() {
		return this.limitamt;
	}

	public void setLimitamt(BigDecimal limitamt) {
		this.limitamt = limitamt;
	}

	public String getMchnitid() {
		return this.mchnitid;
	}

	public void setMchnitid(String mchnitid) {
		this.mchnitid = mchnitid;
	}

	public BigDecimal getPaymoney() {
		return this.paymoney;
	}

	public void setPaymoney(BigDecimal paymoney) {
		this.paymoney = paymoney;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}