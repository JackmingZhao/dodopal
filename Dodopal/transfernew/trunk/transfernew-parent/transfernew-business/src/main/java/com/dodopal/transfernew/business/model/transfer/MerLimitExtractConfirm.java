package com.dodopal.transfernew.business.model.transfer;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the MER_LIMIT_EXTRACT_CONFIRM database table.
 * 
 */
//@Entity
//@Table(name="MER_LIMIT_EXTRACT_CONFIRM")
//@NamedQuery(name="MerLimitExtractConfirm.findAll", query="SELECT m FROM MerLimitExtractConfirm m")
public class MerLimitExtractConfirm implements Serializable {
	private static final long serialVersionUID = 1L;

//	@Id
	private String id;

	private String bak1;

	private String bak10;

	private String bak2;

	private String bak3;

	private String bak4;

	private String bak5;

	private String bak6;

	private String bak7;

	private String bak8;

	private String bak9;

//	@Column(name="CHILD_MER_CODE")
	private String childMerCode;

//	@Column(name="CHILD_MER_NAME")
	private String childMerName;

//	@Temporal(TemporalType.DATE)
//	@Column(name="CONCEL_DATE")
	private Date concelDate;

//	@Column(name="CONCEL_USER")
	private String concelUser;

//	@Temporal(TemporalType.DATE)
//	@Column(name="CONFIRM_DATE")
	private Date confirmDate;

//	@Column(name="CONFIRM_USER")
	private String confirmUser;

//	@Column(name="EXTRACT_AMT")
	private BigDecimal extractAmt;

//	@Temporal(TemporalType.DATE)
//	@Column(name="EXTRACT_DATE")
	private Date extractDate;

//	@Column(name="EXTRACT_USER")
	private String extractUser;

//	@Column(name="PARENT_MER_CODE")
	private String parentMerCode;

//	@Column(name="PARENT_MER_NAME")
	private String parentMerName;

//	@Column(name="\"STATE\"")
	private String state;

	public MerLimitExtractConfirm() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBak1() {
		return this.bak1;
	}

	public void setBak1(String bak1) {
		this.bak1 = bak1;
	}

	public String getBak10() {
		return this.bak10;
	}

	public void setBak10(String bak10) {
		this.bak10 = bak10;
	}

	public String getBak2() {
		return this.bak2;
	}

	public void setBak2(String bak2) {
		this.bak2 = bak2;
	}

	public String getBak3() {
		return this.bak3;
	}

	public void setBak3(String bak3) {
		this.bak3 = bak3;
	}

	public String getBak4() {
		return this.bak4;
	}

	public void setBak4(String bak4) {
		this.bak4 = bak4;
	}

	public String getBak5() {
		return this.bak5;
	}

	public void setBak5(String bak5) {
		this.bak5 = bak5;
	}

	public String getBak6() {
		return this.bak6;
	}

	public void setBak6(String bak6) {
		this.bak6 = bak6;
	}

	public String getBak7() {
		return this.bak7;
	}

	public void setBak7(String bak7) {
		this.bak7 = bak7;
	}

	public String getBak8() {
		return this.bak8;
	}

	public void setBak8(String bak8) {
		this.bak8 = bak8;
	}

	public String getBak9() {
		return this.bak9;
	}

	public void setBak9(String bak9) {
		this.bak9 = bak9;
	}

	public String getChildMerCode() {
		return this.childMerCode;
	}

	public void setChildMerCode(String childMerCode) {
		this.childMerCode = childMerCode;
	}

	public String getChildMerName() {
		return this.childMerName;
	}

	public void setChildMerName(String childMerName) {
		this.childMerName = childMerName;
	}

	public Date getConcelDate() {
		return this.concelDate;
	}

	public void setConcelDate(Date concelDate) {
		this.concelDate = concelDate;
	}

	public String getConcelUser() {
		return this.concelUser;
	}

	public void setConcelUser(String concelUser) {
		this.concelUser = concelUser;
	}

	public Date getConfirmDate() {
		return this.confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	public String getConfirmUser() {
		return this.confirmUser;
	}

	public void setConfirmUser(String confirmUser) {
		this.confirmUser = confirmUser;
	}

	public BigDecimal getExtractAmt() {
		return this.extractAmt;
	}

	public void setExtractAmt(BigDecimal extractAmt) {
		this.extractAmt = extractAmt;
	}

	public Date getExtractDate() {
		return this.extractDate;
	}

	public void setExtractDate(Date extractDate) {
		this.extractDate = extractDate;
	}

	public String getExtractUser() {
		return this.extractUser;
	}

	public void setExtractUser(String extractUser) {
		this.extractUser = extractUser;
	}

	public String getParentMerCode() {
		return this.parentMerCode;
	}

	public void setParentMerCode(String parentMerCode) {
		this.parentMerCode = parentMerCode;
	}

	public String getParentMerName() {
		return this.parentMerName;
	}

	public void setParentMerName(String parentMerName) {
		this.parentMerName = parentMerName;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

}