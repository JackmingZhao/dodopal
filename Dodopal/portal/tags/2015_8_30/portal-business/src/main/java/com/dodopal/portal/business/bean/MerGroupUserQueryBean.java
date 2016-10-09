package com.dodopal.portal.business.bean;

import com.dodopal.common.model.QueryBase;

public class MerGroupUserQueryBean extends QueryBase {

	private static final long serialVersionUID = 17166847800646241L;

	/** 商户编码 */
	private String merCode;

	/** 员工姓名 */
	private String gpUserName;

	/** 所在部门ID */
	private String depId;

	/** 公交卡号 */
	private String cardCode;

	/** 在职状态: 0:在职，1：离职 */
	private String empType;

	public String getMerCode() {
		return merCode;
	}

	public void setMerCode(String merCode) {
		this.merCode = merCode;
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

	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public String getEmpType() {
		return empType;
	}

	public void setEmpType(String empType) {
		this.empType = empType;
	}

}
