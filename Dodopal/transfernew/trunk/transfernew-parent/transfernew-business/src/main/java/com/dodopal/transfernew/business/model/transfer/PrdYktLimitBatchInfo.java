package com.dodopal.transfernew.business.model.transfer;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the PRD_YKT_LIMIT_BATCH_INFO database table.
 * 
 */
//@Entity
//@Table(name="PRD_YKT_LIMIT_BATCH_INFO")
//@NamedQuery(name="PrdYktLimitBatchInfo.findAll", query="SELECT p FROM PrdYktLimitBatchInfo p")
public class PrdYktLimitBatchInfo implements Serializable {
	private static final long serialVersionUID = 1L;

//	@Id
	private String id;

//	@Column(name="APPLY_AMT_LIMIT")
	private BigDecimal applyAmtLimit;

//	@Temporal(TemporalType.DATE)
//	@Column(name="APPLY_DATE")
	private Date applyDate;

//	@Column(name="APPLY_EXPLAINATION")
	private String applyExplaination;

//	@Column(name="APPLY_USER")
	private String applyUser;

//	@Column(name="APPLY_USERNAME")
	private String applyUsername;

//	@Temporal(TemporalType.DATE)
//	@Column(name="AUDIT_DATE")
	private Date auditDate;

//	@Column(name="AUDIT_EXPLAINATION")
	private String auditExplaination;

//	@Column(name="AUDIT_STATE")
	private String auditState;

//	@Column(name="AUDIT_USER")
	private String auditUser;

//	@Column(name="AUDIT_USERNAME")
	private String auditUsername;

//	@Column(name="BATCH_CODE")
	private BigDecimal batchCode;

//	@Temporal(TemporalType.DATE)
//	@Column(name="CHECK_DATE")
	private Date checkDate;

//	@Column(name="CHECK_EXPLAINATION")
	private String checkExplaination;

//	@Column(name="CHECK_STATE")
	private String checkState;

//	@Column(name="CHECK_USER")
	private String checkUser;

//	@Column(name="CHECK_USERNAME")
	private String checkUsername;

//	@Temporal(TemporalType.DATE)
//	@Column(name="CREATE_DATE")
	private Date createDate;

//	@Column(name="CREATE_USER")
	private String createUser;

//	@Column(name="FINANCIAL_PAY_AMT")
	private BigDecimal financialPayAmt;

//	@Temporal(TemporalType.DATE)
//	@Column(name="FINANCIAL_PAY_DATE")
	private Date financialPayDate;

//	@Column(name="FINANCIAL_PAY_FEE")
	private BigDecimal financialPayFee;

//	@Column(name="PAYMENT_CHANNEL")
	private String paymentChannel;

	private String remark;

//	@Temporal(TemporalType.DATE)
//	@Column(name="UPDATE_DATE")
	private Date updateDate;

//	@Column(name="UPDATE_USER")
	private String updateUser;

//	@Column(name="YKT_ADD_LIMIT")
	private BigDecimal yktAddLimit;

//	@Temporal(TemporalType.DATE)
//	@Column(name="YKT_ADD_LIMIT_DATE")
	private Date yktAddLimitDate;

//	@Column(name="YKT_CODE")
	private String yktCode;

//	@Column(name="YKT_NAME")
	private String yktName;

	public PrdYktLimitBatchInfo() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getApplyAmtLimit() {
		return this.applyAmtLimit;
	}

	public void setApplyAmtLimit(BigDecimal applyAmtLimit) {
		this.applyAmtLimit = applyAmtLimit;
	}

	public Date getApplyDate() {
		return this.applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public String getApplyExplaination() {
		return this.applyExplaination;
	}

	public void setApplyExplaination(String applyExplaination) {
		this.applyExplaination = applyExplaination;
	}

	public String getApplyUser() {
		return this.applyUser;
	}

	public void setApplyUser(String applyUser) {
		this.applyUser = applyUser;
	}

	public String getApplyUsername() {
		return this.applyUsername;
	}

	public void setApplyUsername(String applyUsername) {
		this.applyUsername = applyUsername;
	}

	public Date getAuditDate() {
		return this.auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public String getAuditExplaination() {
		return this.auditExplaination;
	}

	public void setAuditExplaination(String auditExplaination) {
		this.auditExplaination = auditExplaination;
	}

	public String getAuditState() {
		return this.auditState;
	}

	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}

	public String getAuditUser() {
		return this.auditUser;
	}

	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}

	public String getAuditUsername() {
		return this.auditUsername;
	}

	public void setAuditUsername(String auditUsername) {
		this.auditUsername = auditUsername;
	}

	public BigDecimal getBatchCode() {
		return this.batchCode;
	}

	public void setBatchCode(BigDecimal batchCode) {
		this.batchCode = batchCode;
	}

	public Date getCheckDate() {
		return this.checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public String getCheckExplaination() {
		return this.checkExplaination;
	}

	public void setCheckExplaination(String checkExplaination) {
		this.checkExplaination = checkExplaination;
	}

	public String getCheckState() {
		return this.checkState;
	}

	public void setCheckState(String checkState) {
		this.checkState = checkState;
	}

	public String getCheckUser() {
		return this.checkUser;
	}

	public void setCheckUser(String checkUser) {
		this.checkUser = checkUser;
	}

	public String getCheckUsername() {
		return this.checkUsername;
	}

	public void setCheckUsername(String checkUsername) {
		this.checkUsername = checkUsername;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public BigDecimal getFinancialPayAmt() {
		return this.financialPayAmt;
	}

	public void setFinancialPayAmt(BigDecimal financialPayAmt) {
		this.financialPayAmt = financialPayAmt;
	}

	public Date getFinancialPayDate() {
		return this.financialPayDate;
	}

	public void setFinancialPayDate(Date financialPayDate) {
		this.financialPayDate = financialPayDate;
	}

	public BigDecimal getFinancialPayFee() {
		return this.financialPayFee;
	}

	public void setFinancialPayFee(BigDecimal financialPayFee) {
		this.financialPayFee = financialPayFee;
	}

	public String getPaymentChannel() {
		return this.paymentChannel;
	}

	public void setPaymentChannel(String paymentChannel) {
		this.paymentChannel = paymentChannel;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public BigDecimal getYktAddLimit() {
		return this.yktAddLimit;
	}

	public void setYktAddLimit(BigDecimal yktAddLimit) {
		this.yktAddLimit = yktAddLimit;
	}

	public Date getYktAddLimitDate() {
		return this.yktAddLimitDate;
	}

	public void setYktAddLimitDate(Date yktAddLimitDate) {
		this.yktAddLimitDate = yktAddLimitDate;
	}

	public String getYktCode() {
		return this.yktCode;
	}

	public void setYktCode(String yktCode) {
		this.yktCode = yktCode;
	}

	public String getYktName() {
		return this.yktName;
	}

	public void setYktName(String yktName) {
		this.yktName = yktName;
	}

}