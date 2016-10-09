package com.dodopal.users.business.model;

import java.util.Date;
import java.util.List;

import com.dodopal.common.model.BaseEntity;

public class MerchantUser extends BaseEntity {

	private static final long serialVersionUID = -7849670524247244675L;

	// 用户标志
	private String merUserFlag;

	// 用户类型
	private String merUserType;

	// 商户编码
	private String merCode;

	// 商户用户登录账号
	private String merUserName;

	// 商户用户登录密码
	private String merUserPWD;

	// 商户用户证件类型
	private String merUserIdentityType;

	// 商户用户证件号码
	private String merUserIdentityNumber;

	// 商户用户昵称（真实姓名）
	private String merUserNickName;

	// 用户性别
	private String merUserSex;

	// 用户手机号
	private String merUserMobile;

	// 用户固定电话
	private String merUserTelephone;

	// 用户地址
	private String merUserAdds;

	// 用户生日
	private Date merUserBirthday;

	// 用户邮箱
	private String merUserEmail;

	// 用户入职时间
	private Date merUserEmployeeDate;

	// 用户最后一次登录时间
	private Date merUserLastLoginDate;

	// 用户最后一次登录IP
	private String merUserLastLoginIp;

	// 用户连续登陆次数
	private int merUserLoginFaiCount;

	// 锁定用户时间
	private Date merUserLockedDate;

	// 备注
	private String merUserRemark;

	// 启用标志
	private String activate;

	// 删除标志
	private String delFlag;

	// 用户编号
	private String userCode;

	// 用户注册来源
	private String merUserSource;

	// 支付确认信息
	private String payInfoFlg;

	// 默认开通一卡通编号
	private String cityCode;

	// 审核状态
	private String merUserState;

	/* 学历 */
	private String education;
	/* 收入 */
	private Long income;
	/* 生日，格式：1990-09-10 */
	private String birthday;
	/* 是否已婚，0是，1否 */
	private String isMarried;
	/* 商圈 */
	private String tradeArea;

	/** ------------------------------------------------ */
	// 开户时间起始
	private Date createDateStart;
	// 开户结束
	private Date createDateEnd;

	// 管辖部门列表id
	private List<String> merGroupDeptList;
	// 管辖部门列表名称
	private List<String> merGroupDeptNameList;
	// 所属商户名称
	private String merchantName;

	// 所属商户类型
	private String merchantType;
	/* 审核人 */
	private String merchantStateUser;
	/* 审核日期 */
	private Date merchantStateDate;
	/* 审核状态 */
	private String merchantState;
	/* 用户业务城市名称 */
	private String cityName;
	
	//-------------新增省份，城市   Time:2016-01-13 Name: Joe -----------
	/* 用户所属省份 */
	private String merUserPro;
	/* 用户所属城市 */
	private String merUserCity;
    /* 用户所属省份名称 */
    private String merUserProName;
    /* 用户所属城市名称 */
    private String merUserCityName;
    
    //-------------通用注册----------
    //微信号
    private String wechatId;
    //活动key
    private String paramKey;
    //卡号
    private String cardNo;

	public String getMerUserProName() {
        return merUserProName;
    }

    public void setMerUserProName(String merUserProName) {
        this.merUserProName = merUserProName;
    }

    public String getMerUserCityName() {
        return merUserCityName;
    }

    public void setMerUserCityName(String merUserCityName) {
        this.merUserCityName = merUserCityName;
    }

    public String getMerUserPro() {
        return merUserPro;
    }

    public void setMerUserPro(String merUserPro) {
        this.merUserPro = merUserPro;
    }

    public String getMerUserCity() {
        return merUserCity;
    }

    public void setMerUserCity(String merUserCity) {
        this.merUserCity = merUserCity;
    }

    /** ------------------------------------------------ */

	public String getMerchantName() {
		return merchantName;
	}

	public List<String> getMerGroupDeptNameList() {
		return merGroupDeptNameList;
	}

	public void setMerGroupDeptNameList(List<String> merGroupDeptNameList) {
		this.merGroupDeptNameList = merGroupDeptNameList;
	}

	public String getMerchantStateUser() {
		return merchantStateUser;
	}

	public void setMerchantStateUser(String merchantStateUser) {
		this.merchantStateUser = merchantStateUser;
	}

	public Date getMerchantStateDate() {
		return merchantStateDate;
	}

	public void setMerchantStateDate(Date merchantStateDate) {
		this.merchantStateDate = merchantStateDate;
	}

	public String getMerchantState() {
		return merchantState;
	}

	public void setMerchantState(String merchantState) {
		this.merchantState = merchantState;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMerchantType() {
		return merchantType;
	}

	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}

	public Date getCreateDateStart() {
		return createDateStart;
	}

	public void setCreateDateStart(Date createDateStart) {
		this.createDateStart = createDateStart;
	}

	public Date getCreateDateEnd() {
		return createDateEnd;
	}

	public void setCreateDateEnd(Date createDateEnd) {
		this.createDateEnd = createDateEnd;
	}

	private List<MerRole> merUserRoleList;

	public String getMerUserFlag() {
		return merUserFlag;
	}

	public void setMerUserFlag(String merUserFlag) {
		this.merUserFlag = merUserFlag;
	}

	public String getMerUserType() {
		return merUserType;
	}

	public void setMerUserType(String merUserType) {
		this.merUserType = merUserType;
	}

	public String getMerCode() {
		return merCode;
	}

	public void setMerCode(String merCode) {
		this.merCode = merCode;
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

	public String getMerUserIdentityType() {
		return merUserIdentityType;
	}

	public void setMerUserIdentityType(String merUserIdentityType) {
		this.merUserIdentityType = merUserIdentityType;
	}

	public String getMerUserIdentityNumber() {
		return merUserIdentityNumber;
	}

	public void setMerUserIdentityNumber(String merUserIdentityNumber) {
		this.merUserIdentityNumber = merUserIdentityNumber;
	}

	public String getMerUserNickName() {
		return merUserNickName;
	}

	public void setMerUserNickName(String merUserNickName) {
		this.merUserNickName = merUserNickName;
	}

	public String getMerUserSex() {
		return merUserSex;
	}

	public void setMerUserSex(String merUserSex) {
		this.merUserSex = merUserSex;
	}

	public String getMerUserMobile() {
		return merUserMobile;
	}

	public void setMerUserMobile(String merUserMobile) {
		this.merUserMobile = merUserMobile;
	}

	public String getMerUserTelephone() {
		return merUserTelephone;
	}

	public void setMerUserTelephone(String merUserTelephone) {
		this.merUserTelephone = merUserTelephone;
	}

	public String getMerUserAdds() {
		return merUserAdds;
	}

	public void setMerUserAdds(String merUserAdds) {
		this.merUserAdds = merUserAdds;
	}

	public Date getMerUserBirthday() {
		return merUserBirthday;
	}

	public void setMerUserBirthday(Date merUserBirthday) {
		this.merUserBirthday = merUserBirthday;
	}

	public String getMerUserEmail() {
		return merUserEmail;
	}

	public void setMerUserEmail(String merUserEmail) {
		this.merUserEmail = merUserEmail;
	}

	public Date getMerUserEmployeeDate() {
		return merUserEmployeeDate;
	}

	public void setMerUserEmployeeDate(Date merUserEmployeeDate) {
		this.merUserEmployeeDate = merUserEmployeeDate;
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

	public int getMerUserLoginFaiCount() {
		return merUserLoginFaiCount;
	}

	public void setMerUserLoginFaiCount(int merUserLoginFaiCount) {
		this.merUserLoginFaiCount = merUserLoginFaiCount;
	}

	public Date getMerUserLockedDate() {
		return merUserLockedDate;
	}

	public void setMerUserLockedDate(Date merUserLockedDate) {
		this.merUserLockedDate = merUserLockedDate;
	}

	public String getMerUserRemark() {
		return merUserRemark;
	}

	public void setMerUserRemark(String merUserRemark) {
		this.merUserRemark = merUserRemark;
	}

	public String getActivate() {
		return activate;
	}

	public void setActivate(String activate) {
		this.activate = activate;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getMerUserSource() {
		return merUserSource;
	}

	public void setMerUserSource(String merUserSource) {
		this.merUserSource = merUserSource;
	}

	public String getPayInfoFlg() {
		return payInfoFlg;
	}

	public void setPayInfoFlg(String payInfoFlg) {
		this.payInfoFlg = payInfoFlg;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public List<MerRole> getMerUserRoleList() {
		return merUserRoleList;
	}

	public void setMerUserRoleList(List<MerRole> merUserRoleList) {
		this.merUserRoleList = merUserRoleList;
	}

	public String getMerUserState() {
		return merUserState;
	}

	public void setMerUserState(String merUserState) {
		this.merUserState = merUserState;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public MerchantUser() {
		super();
	}

	public MerchantUser(String merUserFlag, String merUserType, String merCode, String merUserName, String merUserPWD, String merUserIdentityType, String merUserIdentityNumber, String merUserNickName, String merUserSex, String merUserMobile, String merUserTelephone, String merUserAdds, Date merUserBirthday, String merUserEmail, Date merUserEmployeeDate, Date merUserLastLoginDate, String merUserLastLoginIp, int merUserLoginFaiCount, Date merUserLockedDate, String merUserRemark, String activate, String delFlag, String userCode, String merUserSource, String payInfoFlg, String cityCode, Date createDateStart, Date createDateEnd, List<MerRole> merUserRoleList) {
		super();
		this.merUserFlag = merUserFlag;
		this.merUserType = merUserType;
		this.merCode = merCode;
		this.merUserName = merUserName;
		this.merUserPWD = merUserPWD;
		this.merUserIdentityType = merUserIdentityType;
		this.merUserIdentityNumber = merUserIdentityNumber;
		this.merUserNickName = merUserNickName;
		this.merUserSex = merUserSex;
		this.merUserMobile = merUserMobile;
		this.merUserTelephone = merUserTelephone;
		this.merUserAdds = merUserAdds;
		this.merUserBirthday = merUserBirthday;
		this.merUserEmail = merUserEmail;
		this.merUserEmployeeDate = merUserEmployeeDate;
		this.merUserLastLoginDate = merUserLastLoginDate;
		this.merUserLastLoginIp = merUserLastLoginIp;
		this.merUserLoginFaiCount = merUserLoginFaiCount;
		this.merUserLockedDate = merUserLockedDate;
		this.merUserRemark = merUserRemark;
		this.activate = activate;
		this.delFlag = delFlag;
		this.userCode = userCode;
		this.merUserSource = merUserSource;
		this.payInfoFlg = payInfoFlg;
		this.cityCode = cityCode;
		this.createDateStart = createDateStart;
		this.createDateEnd = createDateEnd;
		this.merUserRoleList = merUserRoleList;
	}

	@Override
	public String toString() {
		return "MerchantUser [merUserFlag=" + merUserFlag + ", merUserType=" + merUserType + ", merCode=" + merCode + ", merUserName=" + merUserName + ", merUserPWD=" + merUserPWD + ", merUserIdentityType=" + merUserIdentityType + ", merUserIdentityNumber=" + merUserIdentityNumber + ", merUserNickName=" + merUserNickName + ", merUserSex=" + merUserSex + ", merUserMobile=" + merUserMobile + ", merUserTelephone=" + merUserTelephone + ", merUserAdds=" + merUserAdds + ", merUserBirthday=" + merUserBirthday + ", merUserEmail=" + merUserEmail + ", merUserEmployeeDate=" + merUserEmployeeDate + ", merUserLastLoginDate=" + merUserLastLoginDate + ", merUserLastLoginIp=" + merUserLastLoginIp + ", merUserLoginFaiCount=" + merUserLoginFaiCount + ", merUserLockedDate=" + merUserLockedDate
				+ ", merUserRemark=" + merUserRemark + ", activate=" + activate + ", delFlag=" + delFlag + ", userCode=" + userCode + ", merUserSource=" + merUserSource + ", payInfoFlg=" + payInfoFlg + ", cityCode=" + cityCode + ", createDateStart=" + createDateStart + ", createDateEnd=" + createDateEnd + ", merUserRoleList=" + merUserRoleList + "]";
	}

	public List<String> getMerGroupDeptList() {
		return merGroupDeptList;
	}

	public void setMerGroupDeptList(List<String> merGroupDeptList) {
		this.merGroupDeptList = merGroupDeptList;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public Long getIncome() {
		return income;
	}

	public void setIncome(Long income) {
		this.income = income;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getIsMarried() {
		return isMarried;
	}

	public void setIsMarried(String isMarried) {
		this.isMarried = isMarried;
	}

	public String getTradeArea() {
		return tradeArea;
	}

	public void setTradeArea(String tradeArea) {
		this.tradeArea = tradeArea;
	}
	
	public String getWechatId() {
		return wechatId;
	}

	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}

	public String getParamKey() {
		return paramKey;
	}

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}


}
