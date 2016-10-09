package com.dodopal.transfer.business.model.target;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the V_USER_INFO database table.
 * 
 */
//@Entity
//@Table(name="V_USER_INFO")
//@NamedQuery(name="VUserInfo.findAll", query="SELECT v FROM VUserInfo v")
public class VUserInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;

//	@Column(name="LOGIN_NAME")
	private String loginName;

//	@Column(name="NICK_NAME")
	private String nickName;

	private BigDecimal source;

	public VUserInfo() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getNickName() {
		return this.nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public BigDecimal getSource() {
		return this.source;
	}

	public void setSource(BigDecimal source) {
		this.source = source;
	}

}