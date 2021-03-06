package com.dodopal.transfer.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the GROUPEDUORDERINFOTB database table.
 * 
 */
//@Entity
//@NamedQuery(name="Groupeduorderinfotb.findAll", query="SELECT g FROM Groupeduorderinfotb g")
public class Groupeduorderinfotb implements Serializable {
	private static final long serialVersionUID = 1L;

//	@Id
	private String chargeorderid;

	private BigDecimal aftsurpluslimit;

	private String applicant;

	private String applicantdate;

	private String applicanttime;

	private String approval;

	private String approvaldate;

	private String approvaltime;

	private BigDecimal befsurpluslimit;

	private BigDecimal extralimitamt;

	private BigDecimal groupid;

	private BigDecimal limitamt;

	private BigDecimal paymoney;

	private String proxychargeorderid;

	private String remarks;

	private BigDecimal status;

	private String taobaoorderid;

	private BigDecimal typeid;

	private BigDecimal zjtype;

	public Groupeduorderinfotb() {
	}

	public String getChargeorderid() {
		return this.chargeorderid;
	}

	public void setChargeorderid(String chargeorderid) {
		this.chargeorderid = chargeorderid;
	}

	public BigDecimal getAftsurpluslimit() {
		return this.aftsurpluslimit;
	}

	public void setAftsurpluslimit(BigDecimal aftsurpluslimit) {
		this.aftsurpluslimit = aftsurpluslimit;
	}

	public String getApplicant() {
		return this.applicant;
	}

	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}

	public String getApplicantdate() {
		return this.applicantdate;
	}

	public void setApplicantdate(String applicantdate) {
		this.applicantdate = applicantdate;
	}

	public String getApplicanttime() {
		return this.applicanttime;
	}

	public void setApplicanttime(String applicanttime) {
		this.applicanttime = applicanttime;
	}

	public String getApproval() {
		return this.approval;
	}

	public void setApproval(String approval) {
		this.approval = approval;
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

	public BigDecimal getBefsurpluslimit() {
		return this.befsurpluslimit;
	}

	public void setBefsurpluslimit(BigDecimal befsurpluslimit) {
		this.befsurpluslimit = befsurpluslimit;
	}

	public BigDecimal getExtralimitamt() {
		return this.extralimitamt;
	}

	public void setExtralimitamt(BigDecimal extralimitamt) {
		this.extralimitamt = extralimitamt;
	}

	public BigDecimal getGroupid() {
		return this.groupid;
	}

	public void setGroupid(BigDecimal groupid) {
		this.groupid = groupid;
	}

	public BigDecimal getLimitamt() {
		return this.limitamt;
	}

	public void setLimitamt(BigDecimal limitamt) {
		this.limitamt = limitamt;
	}

	public BigDecimal getPaymoney() {
		return this.paymoney;
	}

	public void setPaymoney(BigDecimal paymoney) {
		this.paymoney = paymoney;
	}

	public String getProxychargeorderid() {
		return this.proxychargeorderid;
	}

	public void setProxychargeorderid(String proxychargeorderid) {
		this.proxychargeorderid = proxychargeorderid;
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

	public String getTaobaoorderid() {
		return this.taobaoorderid;
	}

	public void setTaobaoorderid(String taobaoorderid) {
		this.taobaoorderid = taobaoorderid;
	}

	public BigDecimal getTypeid() {
		return this.typeid;
	}

	public void setTypeid(BigDecimal typeid) {
		this.typeid = typeid;
	}

	public BigDecimal getZjtype() {
		return this.zjtype;
	}

	public void setZjtype(BigDecimal zjtype) {
		this.zjtype = zjtype;
	}

}