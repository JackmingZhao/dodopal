package com.dodopal.transfernew.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the GROUPUSERINFOTB database table.
 * 
 */
//@Entity
//@NamedQuery(name="Groupuserinfotb.findAll", query="SELECT g FROM Groupuserinfotb g")
public class Groupuserinfotb implements Serializable {
	private static final long serialVersionUID = 1L;

//	@Id
	private long loginuserid;

	private String answer;

	private String createtime;

	private String edittime;

	private String edituser;

	private BigDecimal edituserid;

	private String email;

	private BigDecimal groupid;

	private String loginname;

	private String lxmobile;

	private String lxperson;

	private String lxtel;

	private BigDecimal operaction;

	private String password;

	private BigDecimal question;

	private String remarks;

	private BigDecimal status;

	private BigDecimal userqx;

	public Groupuserinfotb() {
	}

	public long getLoginuserid() {
		return this.loginuserid;
	}

	public void setLoginuserid(long loginuserid) {
		this.loginuserid = loginuserid;
	}

	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
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

	public BigDecimal getEdituserid() {
		return this.edituserid;
	}

	public void setEdituserid(BigDecimal edituserid) {
		this.edituserid = edituserid;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public BigDecimal getGroupid() {
		return this.groupid;
	}

	public void setGroupid(BigDecimal groupid) {
		this.groupid = groupid;
	}

	public String getLoginname() {
		return this.loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getLxmobile() {
		return this.lxmobile;
	}

	public void setLxmobile(String lxmobile) {
		this.lxmobile = lxmobile;
	}

	public String getLxperson() {
		return this.lxperson;
	}

	public void setLxperson(String lxperson) {
		this.lxperson = lxperson;
	}

	public String getLxtel() {
		return this.lxtel;
	}

	public void setLxtel(String lxtel) {
		this.lxtel = lxtel;
	}

	public BigDecimal getOperaction() {
		return this.operaction;
	}

	public void setOperaction(BigDecimal operaction) {
		this.operaction = operaction;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public BigDecimal getQuestion() {
		return this.question;
	}

	public void setQuestion(BigDecimal question) {
		this.question = question;
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

	public BigDecimal getUserqx() {
		return this.userqx;
	}

	public void setUserqx(BigDecimal userqx) {
		this.userqx = userqx;
	}

}