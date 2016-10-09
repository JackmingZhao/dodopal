package com.dodopal.api.users.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.dodopal.common.enums.FundTypeEnum;

/**
 * 类说明 ：门户登录成功后返回信息
 * 
 * @author lifeng
 */

public class PortalUserDTO implements Serializable {
	private static final long serialVersionUID = 1954820290832565769L;
	// 用户ID
	private String id;
	// 登录账号
	private String merUserName;
	// 商户用户登录密码
	private String merUserPWD;
	// 商户用户昵称（真实姓名）--可修改，门户修改注意更新session
	private String merUserNickName;
	// 用户最后一次登录时间
	private Date merUserLastLoginDate;
	// 用户最后一次登录IP
	private String merUserLastLoginIp;
	// 用户编号
	private String userCode;
	// 用户手机号--可修改，门户修改注意更新session
	private String merUserMobile;

	/* 商户编码 */
	private String merCode;
	/* 商户名称 */
	private String merName;
	/* 商户类型 ，个人为99 */
	private String merType;
	/* 商户或个人业务城市 */
	private String cityName;
	/* 商户分类 */
	private String merClassify;

	// 权限合集
	private List<MerFunctionInfoDTO> merFunInfoList;

	// 额度来源
	private String limitSource;

	// 一卡通编码（供应商）
	private String yktCode;

	// --------------------以下参数非登录时获取
	/* 主账户数据库id */
	private String acid;
	/* 主账户资金类别 */
	private String fundType;
	/* 主账户账户编号 */
	private String accountCode;

	public String getAcid() {
		return acid;
	}

	public void setAcid(String acid) {
		this.acid = acid;
	}

	public String getFundType() {
		if (StringUtils.isBlank(this.fundType)) {
			return null;
		}
		return FundTypeEnum.getFundTypeByCode(this.fundType).getName();
	}

	public void setFundType(String fundType) {
		this.fundType = fundType;
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

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

	public String getMerUserPWD() {
		return merUserPWD;
	}

	public void setMerUserPWD(String merUserPWD) {
		this.merUserPWD = merUserPWD;
	}

	public String getMerUserNickName() {
		return merUserNickName;
	}

	public void setMerUserNickName(String merUserNickName) {
		this.merUserNickName = merUserNickName;
	}

	public Date getMerUserLastLoginDate() {
		return merUserLastLoginDate;
	}

	public void setMerUserLastLoginDate(Date merUserLastLoginDate) {
		this.merUserLastLoginDate = merUserLastLoginDate;
	}

	public String getMerUserLastLoginIp() {
		return merUserLastLoginIp;
	}

	public void setMerUserLastLoginIp(String merUserLastLoginIp) {
		this.merUserLastLoginIp = merUserLastLoginIp;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
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

	public List<MerFunctionInfoDTO> getMerFunInfoList() {
		return merFunInfoList;
	}

	public void setMerFunInfoList(List<MerFunctionInfoDTO> merFunInfoList) {
		this.merFunInfoList = merFunInfoList;
	}

	public String getMerType() {
		return merType;
	}

	public void setMerType(String merType) {
		this.merType = merType;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getMerClassify() {
		return merClassify;
	}

	public void setMerClassify(String merClassify) {
		this.merClassify = merClassify;
	}

	public String getMerUserMobile() {
		return merUserMobile;
	}

	public void setMerUserMobile(String merUserMobile) {
		this.merUserMobile = merUserMobile;
	}

	public String getLimitSource() {
		return limitSource;
	}

	public void setLimitSource(String limitSource) {
		this.limitSource = limitSource;
	}

	public String getYktCode() {
		return yktCode;
	}

	public void setYktCode(String yktCode) {
		this.yktCode = yktCode;
	}

}
