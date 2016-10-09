package com.dodopal.transfernew.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the PROXYUSERINFOTB database table.
 * 
 */
//@Entity
//@NamedQuery(name="Proxyuserinfotb.findAll", query="SELECT p FROM Proxyuserinfotb p")
public class Proxyuserinfotb implements Serializable {
	private static final long serialVersionUID = 1L;

//	@Id
	private long userid;

	private String address;

	private String answer;

	private BigDecimal cityid;

	private String email;

	private String exceloption;

	private String identityid;

	private BigDecimal identitytypeid;

	private String lastchoosecity;

	private String loginname;

	private String mobiltel;

	private BigDecimal provinceid;

	private BigDecimal proxyid;

	private String pwd;

	private String question;

	private String remarks;

	private BigDecimal sex;

	private BigDecimal status;

	private String tel;

	private String username;

	private BigDecimal usertypeid;

	private String zipcode;

	public Proxyuserinfotb() {
	}

	public long getUserid() {
		return this.userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public BigDecimal getCityid() {
		return this.cityid;
	}

	public void setCityid(BigDecimal cityid) {
		this.cityid = cityid;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getExceloption() {
		return this.exceloption;
	}

	public void setExceloption(String exceloption) {
		this.exceloption = exceloption;
	}

	public String getIdentityid() {
		return this.identityid;
	}

	public void setIdentityid(String identityid) {
		this.identityid = identityid;
	}

	public BigDecimal getIdentitytypeid() {
		return this.identitytypeid;
	}

	public void setIdentitytypeid(BigDecimal identitytypeid) {
		this.identitytypeid = identitytypeid;
	}

	public String getLastchoosecity() {
		return this.lastchoosecity;
	}

	public void setLastchoosecity(String lastchoosecity) {
		this.lastchoosecity = lastchoosecity;
	}

	public String getLoginname() {
		return this.loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getMobiltel() {
		return this.mobiltel;
	}

	public void setMobiltel(String mobiltel) {
		this.mobiltel = mobiltel;
	}

	public BigDecimal getProvinceid() {
		return this.provinceid;
	}

	public void setProvinceid(BigDecimal provinceid) {
		this.provinceid = provinceid;
	}

	public BigDecimal getProxyid() {
		return this.proxyid;
	}

	public void setProxyid(BigDecimal proxyid) {
		this.proxyid = proxyid;
	}

	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getQuestion() {
		return this.question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public BigDecimal getSex() {
		return this.sex;
	}

	public void setSex(BigDecimal sex) {
		this.sex = sex;
	}

	public BigDecimal getStatus() {
		return this.status;
	}

	public void setStatus(BigDecimal status) {
		this.status = status;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public BigDecimal getUsertypeid() {
		return this.usertypeid;
	}

	public void setUsertypeid(BigDecimal usertypeid) {
		this.usertypeid = usertypeid;
	}

	public String getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

}