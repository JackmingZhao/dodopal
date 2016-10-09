package com.dodopal.transfernew.business.model.transfer;

import java.io.Serializable;
//import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the MER_TYPE_DISABLE_FUN database table.
 * 
 */
//@Entity
//@Table(name="MER_TYPE_DISABLE_FUN")
//@NamedQuery(name="MerTypeDisableFun.findAll", query="SELECT m FROM MerTypeDisableFun m")
public class MerTypeDisableFun implements Serializable {
	private static final long serialVersionUID = 1L;

//	@Id
	private String id;

//	@Column(name="\"ACTIVATE\"")
	private String activate;

//	@Temporal(TemporalType.DATE)
//	@Column(name="CREATE_DATE")
	private Date createDate;

//	@Column(name="CREATE_USER")
	private String createUser;

//	@Column(name="MER_FUN_CODE")
	private String merFunCode;

//	@Column(name="MER_FUN_NAME")
	private String merFunName;

//	@Column(name="MER_TYPE")
	private String merType;

//	@Temporal(TemporalType.DATE)
//	@Column(name="UPDATE_DATE")
	private Date updateDate;

//	@Column(name="UPDATE_USER")
	private String updateUser;

	public MerTypeDisableFun() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getActivate() {
		return this.activate;
	}

	public void setActivate(String activate) {
		this.activate = activate;
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

	public String getMerFunCode() {
		return this.merFunCode;
	}

	public void setMerFunCode(String merFunCode) {
		this.merFunCode = merFunCode;
	}

	public String getMerFunName() {
		return this.merFunName;
	}

	public void setMerFunName(String merFunName) {
		this.merFunName = merFunName;
	}

	public String getMerType() {
		return this.merType;
	}

	public void setMerType(String merType) {
		this.merType = merType;
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

}