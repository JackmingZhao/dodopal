package com.dodopal.product.web.param;

/**
 * @author lifeng@dodopal.com
 */

public class RegisterMerchantRequest extends BaseRequest {
	// 网点名称
	String mername;
	// 网点地址
	String meradds;
	// 终端ID
	String posid;
	// 业务所在城市
	String citycode;
	// 注册邮箱
	String email;
	// 真实姓名
	String nickname;
	// 登录用户名
	String username;
	// 性别
	String sex;
	// 密码
	String password;
	// 证件类型
	String identitytype;
	// 证件号码
	String identitynumber;
	// 手机号码
	String mobile;

	// 来源
	String source;

	public String getMername() {
		return mername;
	}

	public void setMername(String mername) {
		this.mername = mername;
	}

	public String getMeradds() {
		return meradds;
	}

	public void setMeradds(String meradds) {
		this.meradds = meradds;
	}

	public String getPosid() {
		return posid;
	}

	public void setPosid(String posid) {
		this.posid = posid;
	}

	public String getCitycode() {
		return citycode;
	}

	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIdentitytype() {
		return identitytype;
	}

	public void setIdentitytype(String identitytype) {
		this.identitytype = identitytype;
	}

	public String getIdentitynumber() {
		return identitynumber;
	}

	public void setIdentitynumber(String identitynumber) {
		this.identitynumber = identitynumber;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

}
