package com.dodopal.portal.business.bean;

import java.util.Date;

import com.dodopal.common.model.BaseEntity;

public class MerGroupUserBean extends BaseEntity {

	private static final long serialVersionUID = -683077460761340112L;

	/** 商户编码 */
	private String merCode;

	/** 商户名称 */
	private String merName;

	/** 员工姓名 */
	private String gpUserName;

	/** 所在部门ID */
	private String depId;

	/** 所在部门名称 */
	private String depName;

	/** 身份证号 */
	private String identityNum;

	/** 入职时间 */
	private Date employeeDate;

	/** 公交卡号 */
	private String cardCode;

	/** 卡类型 */
	private String cardType;

	/** 充值金额 */
	private long rechargeAmount;

	/** 充值方式 */
	private String rechargeWay;

	/** 手机号 */
	private String mobiltle;

	/** 固定电话 */
	private String phone;

	/** 备注 */
	private String remark;

	/** 在职状态: 0:在职，1：离职 */
	private String empType;

	/** 启用标识，0启用，1不启用 */
	private String activate;

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

	public String getGpUserName() {
		return gpUserName;
	}

	public void setGpUserName(String gpUserName) {
		this.gpUserName = gpUserName;
	}

	public String getDepId() {
		return depId;
	}

	public void setDepId(String depId) {
		this.depId = depId;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public String getIdentityNum() {
		return identityNum;
	}

	public void setIdentityNum(String identityNum) {
		this.identityNum = identityNum;
	}

	public Date getEmployeeDate() {
		return employeeDate;
	}

	public void setEmployeeDate(Date employeeDate) {
		this.employeeDate = employeeDate;
	}

	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public long getRechargeAmount() {
		return rechargeAmount;
	}

	public void setRechargeAmount(long rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}

	public String getRechargeWay() {
		return rechargeWay;
	}

	public void setRechargeWay(String rechargeWay) {
		this.rechargeWay = rechargeWay;
	}

	public String getMobiltle() {
		return mobiltle;
	}

	public void setMobiltle(String mobiltle) {
		this.mobiltle = mobiltle;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getEmpType() {
		return empType;
	}

	public void setEmpType(String empType) {
		this.empType = empType;
	}

	public String getActivate() {
		return activate;
	}

	public void setActivate(String activate) {
		this.activate = activate;
	}

}
