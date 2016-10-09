package com.dodopal.transfernew.business.model.transfer;

import java.io.Serializable;
//import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the RESPONSE_MESSAGE database table.
 * 
 */
//@Entity
//@Table(name="RESPONSE_MESSAGE")
//@NamedQuery(name="ResponseMessage.findAll", query="SELECT r FROM ResponseMessage r")
public class ResponseMessage implements Serializable {
	private static final long serialVersionUID = 1L;

//	@Id
	private String id;

//	@Temporal(TemporalType.DATE)
//	@Column(name="CREATE_DATE")
	private Date createDate;

//	@Column(name="CREATE_USER")
	private String createUser;

//	@Column(name="\"MESSAGE\"")
	private String message;

//	@Column(name="MSG_CODE")
	private String msgCode;

//	@Column(name="MSG_TYPE")
	private String msgType;

//	@Temporal(TemporalType.DATE)
//	@Column(name="UPDATE_DATE")
	private Date updateDate;

//	@Column(name="UPDATE_USER")
	private String updateUser;

	public ResponseMessage() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMsgCode() {
		return this.msgCode;
	}

	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}

	public String getMsgType() {
		return this.msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
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