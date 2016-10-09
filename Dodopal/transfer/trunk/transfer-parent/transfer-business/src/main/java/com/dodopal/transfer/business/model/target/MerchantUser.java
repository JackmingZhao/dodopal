package com.dodopal.transfer.business.model.target;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the MERCHANT_USER database table.
 * 
 */
//@Entity
//@Table(name="MERCHANT_USER")
//@NamedQuery(name="MerchantUser.findAll", query="SELECT m FROM MerchantUser m")
public class MerchantUser implements Serializable {
	private static final long serialVersionUID = 1L;

//	@Id
	private String id;

//	@Column(name="\"ACTIVATE\"")
	private String activate;

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

	private String birthday;

//	@Column(name="CITY_CODE")
	private String cityCode;

//	@Temporal(TemporalType.DATE)
//	@Column(name="CREATE_DATE")
	private Date createDate;

//	@Column(name="CREATE_USER")
	private String createUser;

//	@Column(name="DEL_FLG")
	private String delFlg;

	private String education;

	private BigDecimal income;

//	@Column(name="IS_MARRIED")
	private String isMarried;

//	@Column(name="MER_CODE")
	private String merCode;

//	@Column(name="MER_USER_ADDS")
	private String merUserAdds;

//	@Temporal(TemporalType.DATE)
//	@Column(name="MER_USER_BIRTHDAY")
	private Date merUserBirthday;

//	@Column(name="MER_USER_CITY")
	private String merUserCity;

//	@Column(name="MER_USER_EMALL")
	private String merUserEmall;

//	@Temporal(TemporalType.DATE)
//	@Column(name="MER_USER_EMPLOYEE_DATE")
	private Date merUserEmployeeDate;

//	@Column(name="MER_USER_FLG")
	private String merUserFlg;

//	@Column(name="MER_USER_IDENTITY_NUM")
	private String merUserIdentityNum;

//	@Column(name="MER_USER_IDENTITY_TYPE")
	private String merUserIdentityType;

//	@Temporal(TemporalType.DATE)
//	@Column(name="MER_USER_LAST_LOGIN_DATE")
	private Date merUserLastLoginDate;

//	@Column(name="MER_USER_LAST_LOGIN_IP")
	private String merUserLastLoginIp;

//	@Temporal(TemporalType.DATE)
//	@Column(name="MER_USER_LOCKED_DATE")
	private Date merUserLockedDate;

//	@Column(name="MER_USER_LOGIN_FAI_COUNT")
	private BigDecimal merUserLoginFaiCount;

//	@Column(name="MER_USER_MOBILE")
	private String merUserMobile;

//	@Column(name="MER_USER_NAME")
	private String merUserName;

//	@Column(name="MER_USER_NICKNAME")
	private String merUserNickname;

//	@Column(name="MER_USER_PRO")
	private String merUserPro;

//	@Column(name="MER_USER_PWD")
	private String merUserPwd;

//	@Column(name="MER_USER_REMARK")
	private String merUserRemark;

//	@Column(name="MER_USER_SEX")
	private String merUserSex;

//	@Column(name="MER_USER_STATE")
	private String merUserState;

//	@Column(name="MER_USER_TELEPHONE")
	private String merUserTelephone;

//	@Column(name="MER_USER_TYPE")
	private String merUserType;

//	@Column(name="PAYINFO_FLG")
	private String payinfoFlg;

	private String source;

//	@Column(name="TRADE_AREA")
	private String tradeArea;

//	@Temporal(TemporalType.DATE)
//	@Column(name="UPDATE_DATE")
	private Date updateDate;

//	@Column(name="UPDATE_USER")
	private String updateUser;

//	@Column(name="USER_CODE")
	private String userCode;

	public MerchantUser() {
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

	public String getBirthday() {
		return this.birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getCityCode() {
		return this.cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
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

	public String getDelFlg() {
		return this.delFlg;
	}

	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}

	public String getEducation() {
		return this.education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public BigDecimal getIncome() {
		return this.income;
	}

	public void setIncome(BigDecimal income) {
		this.income = income;
	}

	public String getIsMarried() {
		return this.isMarried;
	}

	public void setIsMarried(String isMarried) {
		this.isMarried = isMarried;
	}

	public String getMerCode() {
		return this.merCode;
	}

	public void setMerCode(String merCode) {
		this.merCode = merCode;
	}

	public String getMerUserAdds() {
		return this.merUserAdds;
	}

	public void setMerUserAdds(String merUserAdds) {
		this.merUserAdds = merUserAdds;
	}

	public Date getMerUserBirthday() {
		return this.merUserBirthday;
	}

	public void setMerUserBirthday(Date merUserBirthday) {
		this.merUserBirthday = merUserBirthday;
	}

	public String getMerUserCity() {
		return this.merUserCity;
	}

	public void setMerUserCity(String merUserCity) {
		this.merUserCity = merUserCity;
	}

	public String getMerUserEmall() {
		return this.merUserEmall;
	}

	public void setMerUserEmall(String merUserEmall) {
		this.merUserEmall = merUserEmall;
	}

	public Date getMerUserEmployeeDate() {
		return this.merUserEmployeeDate;
	}

	public void setMerUserEmployeeDate(Date merUserEmployeeDate) {
		this.merUserEmployeeDate = merUserEmployeeDate;
	}

	public String getMerUserFlg() {
		return this.merUserFlg;
	}

	public void setMerUserFlg(String merUserFlg) {
		this.merUserFlg = merUserFlg;
	}

	public String getMerUserIdentityNum() {
		return this.merUserIdentityNum;
	}

	public void setMerUserIdentityNum(String merUserIdentityNum) {
		this.merUserIdentityNum = merUserIdentityNum;
	}

	public String getMerUserIdentityType() {
		return this.merUserIdentityType;
	}

	public void setMerUserIdentityType(String merUserIdentityType) {
		this.merUserIdentityType = merUserIdentityType;
	}

	public Date getMerUserLastLoginDate() {
		return this.merUserLastLoginDate;
	}

	public void setMerUserLastLoginDate(Date merUserLastLoginDate) {
		this.merUserLastLoginDate = merUserLastLoginDate;
	}

	public String getMerUserLastLoginIp() {
		return this.merUserLastLoginIp;
	}

	public void setMerUserLastLoginIp(String merUserLastLoginIp) {
		this.merUserLastLoginIp = merUserLastLoginIp;
	}

	public Date getMerUserLockedDate() {
		return this.merUserLockedDate;
	}

	public void setMerUserLockedDate(Date merUserLockedDate) {
		this.merUserLockedDate = merUserLockedDate;
	}

	public BigDecimal getMerUserLoginFaiCount() {
		return this.merUserLoginFaiCount;
	}

	public void setMerUserLoginFaiCount(BigDecimal merUserLoginFaiCount) {
		this.merUserLoginFaiCount = merUserLoginFaiCount;
	}

	public String getMerUserMobile() {
		return this.merUserMobile;
	}

	public void setMerUserMobile(String merUserMobile) {
		this.merUserMobile = merUserMobile;
	}

	public String getMerUserName() {
		return this.merUserName;
	}

	public void setMerUserName(String merUserName) {
		this.merUserName = merUserName;
	}

	public String getMerUserNickname() {
		return this.merUserNickname;
	}

	public void setMerUserNickname(String merUserNickname) {
		this.merUserNickname = merUserNickname;
	}

	public String getMerUserPro() {
		return this.merUserPro;
	}

	public void setMerUserPro(String merUserPro) {
		this.merUserPro = merUserPro;
	}

	public String getMerUserPwd() {
		return this.merUserPwd;
	}

	public void setMerUserPwd(String merUserPwd) {
		this.merUserPwd = merUserPwd;
	}

	public String getMerUserRemark() {
		return this.merUserRemark;
	}

	public void setMerUserRemark(String merUserRemark) {
		this.merUserRemark = merUserRemark;
	}

	public String getMerUserSex() {
		return this.merUserSex;
	}

	public void setMerUserSex(String merUserSex) {
		this.merUserSex = merUserSex;
	}

	public String getMerUserState() {
		return this.merUserState;
	}

	public void setMerUserState(String merUserState) {
		this.merUserState = merUserState;
	}

	public String getMerUserTelephone() {
		return this.merUserTelephone;
	}

	public void setMerUserTelephone(String merUserTelephone) {
		this.merUserTelephone = merUserTelephone;
	}

	public String getMerUserType() {
		return this.merUserType;
	}

	public void setMerUserType(String merUserType) {
		this.merUserType = merUserType;
	}

	public String getPayinfoFlg() {
		return this.payinfoFlg;
	}

	public void setPayinfoFlg(String payinfoFlg) {
		this.payinfoFlg = payinfoFlg;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTradeArea() {
		return this.tradeArea;
	}

	public void setTradeArea(String tradeArea) {
		this.tradeArea = tradeArea;
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

	public String getUserCode() {
		return this.userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

}