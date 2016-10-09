package com.dodopal.transfer.business.model.target;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the PRD_YKT_LIMITINFO database table.
 * 
 */
//@Entity
//@Table(name="PRD_YKT_LIMITINFO")
//@NamedQuery(name="PrdYktLimitinfo.findAll", query="SELECT p FROM PrdYktLimitinfo p")
public class PrdYktLimitinfo implements Serializable {
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

//	@Temporal(TemporalType.DATE)
//	@Column(name="CREATE_DATE")
	private Date createDate;

//	@Column(name="CREATE_USER")
	private String createUser;

	private String remark;

//	@Temporal(TemporalType.DATE)
//	@Column(name="UPDATE_DATE")
	private Date updateDate;

//	@Column(name="UPDATE_USER")
	private String updateUser;

//	@Column(name="YKT_CODE")
	private String yktCode;

//	@Temporal(TemporalType.DATE)
//	@Column(name="YKT_EXPIRE_DATE")
	private Date yktExpireDate;

//	@Column(name="YKT_STOP_LIMIT")
	private BigDecimal yktStopLimit;

//	@Column(name="YKT_SURPLUS_LIMIT")
	private BigDecimal yktSurplusLimit;

//	@Column(name="YKT_TOTAL_AMT_LIMIT")
	private BigDecimal yktTotalAmtLimit;

//	@Column(name="YKT_WARN_LIMIT")
	private BigDecimal yktWarnLimit;

	public PrdYktLimitinfo() {
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

	public String getYktCode() {
		return this.yktCode;
	}

	public void setYktCode(String yktCode) {
		this.yktCode = yktCode;
	}

	public Date getYktExpireDate() {
		return this.yktExpireDate;
	}

	public void setYktExpireDate(Date yktExpireDate) {
		this.yktExpireDate = yktExpireDate;
	}

	public BigDecimal getYktStopLimit() {
		return this.yktStopLimit;
	}

	public void setYktStopLimit(BigDecimal yktStopLimit) {
		this.yktStopLimit = yktStopLimit;
	}

	public BigDecimal getYktSurplusLimit() {
		return this.yktSurplusLimit;
	}

	public void setYktSurplusLimit(BigDecimal yktSurplusLimit) {
		this.yktSurplusLimit = yktSurplusLimit;
	}

	public BigDecimal getYktTotalAmtLimit() {
		return this.yktTotalAmtLimit;
	}

	public void setYktTotalAmtLimit(BigDecimal yktTotalAmtLimit) {
		this.yktTotalAmtLimit = yktTotalAmtLimit;
	}

	public BigDecimal getYktWarnLimit() {
		return this.yktWarnLimit;
	}

	public void setYktWarnLimit(BigDecimal yktWarnLimit) {
		this.yktWarnLimit = yktWarnLimit;
	}

}