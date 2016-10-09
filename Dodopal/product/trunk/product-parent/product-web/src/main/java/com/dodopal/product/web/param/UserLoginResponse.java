package com.dodopal.product.web.param;

/**
 * @author lifeng@dodopal.com
 */

public class UserLoginResponse extends BaseResponse {
	// 用户名
	private String username;
	// 用户id
	private String userid;
	// 用户号
	private String usercode;
	// 手机号
	private String merusermobile;
	// 商户号
	private String mercode;
	// 商户名称
	private String mername;
	// 用户类型
	private String merusertype;
	/* 性别 */
	private String sex;
	/* 业务城市编码 */
	private String cityno;
	/* 业务城市名称 */
	private String cityname;
	/* 上次登录名 */
	private String lastloginname;
	/* 上次登录时间 */
	private String lastlogintime;
	/* 可用余额 */
	private String surpluslimit;
	/* 权限code列表，格式111,222,333(英文逗号分隔) */
	private String modelrules;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getMerusermobile() {
		return merusermobile;
	}

	public void setMerusermobile(String merusermobile) {
		this.merusermobile = merusermobile;
	}

	public String getMercode() {
		return mercode;
	}

	public void setMercode(String mercode) {
		this.mercode = mercode;
	}

	public String getMername() {
		return mername;
	}

	public void setMername(String mername) {
		this.mername = mername;
	}

	public String getMerusertype() {
		return merusertype;
	}

	public void setMerusertype(String merusertype) {
		this.merusertype = merusertype;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getCityno() {
		return cityno;
	}

	public void setCityno(String cityno) {
		this.cityno = cityno;
	}

	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public String getLastloginname() {
		return lastloginname;
	}

	public void setLastloginname(String lastloginname) {
		this.lastloginname = lastloginname;
	}

	public String getLastlogintime() {
		return lastlogintime;
	}

	public void setLastlogintime(String lastlogintime) {
		this.lastlogintime = lastlogintime;
	}

	public String getSurpluslimit() {
		return surpluslimit;
	}

	public void setSurpluslimit(String surpluslimit) {
		this.surpluslimit = surpluslimit;
	}

	public String getModelrules() {
		return modelrules;
	}

	public void setModelrules(String modelrules) {
		this.modelrules = modelrules;
	}

}
