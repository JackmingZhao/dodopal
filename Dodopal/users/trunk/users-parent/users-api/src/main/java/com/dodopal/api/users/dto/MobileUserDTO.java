package com.dodopal.api.users.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 手机端、VC端登录返回信息
 * 
 * @author lifeng@dodopal.com
 */

public class MobileUserDTO implements Serializable {
	private static final long serialVersionUID = 3840580881060640759L;
	// 用户ID
	private String id;
	// 登录账号
	private String merUserName;
	// 用户编号
	private String userCode;
	// 用户手机号
	private String merUserMobile;
	/* 商户编码 */
	private String merCode;
	/* 商户名称 */
	private String merName;
	/* 商户类型 ，个人为99 */
	private String merType;
	/* 性别 */
	private String merUserSex;
	/* 业务城市编码 */
	private String cityCode;
	/* 业务城市名称 */
	private String cityName;
	/* 上次登录名 */
	private String merUserLastLoginName;
	/* 上次登录时间 */
	private String merUserLastLoginDate;
	/* 可用余额 */
	private String availableBalance;
	/* 权限code列表 */
	private List<String> funCodeList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMerUserName() {
		return merUserName;
	}

	public void setMerUserName(String merUserName) {
		this.merUserName = merUserName;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getMerUserMobile() {
		return merUserMobile;
	}

	public void setMerUserMobile(String merUserMobile) {
		this.merUserMobile = merUserMobile;
	}

	public String getMerCode() {
		return merCode;
	}

	public void setMerCode(String merCode) {
		this.merCode = merCode;
	}

	public String getMerName() {
		return merName;
	}

	public void setMerName(String merName) {
		this.merName = merName;
	}

	public String getMerType() {
		return merType;
	}

	public void setMerType(String merType) {
		this.merType = merType;
	}

	public String getMerUserSex() {
		return merUserSex;
	}

	public void setMerUserSex(String merUserSex) {
		this.merUserSex = merUserSex;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getMerUserLastLoginName() {
		return merUserLastLoginName;
	}

	public void setMerUserLastLoginName(String merUserLastLoginName) {
		this.merUserLastLoginName = merUserLastLoginName;
	}

	public String getMerUserLastLoginDate() {
		return merUserLastLoginDate;
	}

	public void setMerUserLastLoginDate(String merUserLastLoginDate) {
		this.merUserLastLoginDate = merUserLastLoginDate;
	}

	public String getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(String availableBalance) {
		this.availableBalance = availableBalance;
	}

	public List<String> getFunCodeList() {
		return funCodeList;
	}

	public void setFunCodeList(List<String> funCodeList) {
		this.funCodeList = funCodeList;
	}

}
