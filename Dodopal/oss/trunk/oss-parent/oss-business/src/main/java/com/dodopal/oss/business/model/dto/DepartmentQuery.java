package com.dodopal.oss.business.model.dto;

import com.dodopal.common.model.QueryBase;

public class DepartmentQuery extends QueryBase{
	private static final long serialVersionUID = -2630025549757639661L;
	
	private String depCode;
    private String depName;
    private String chargeId;
    private String remark;
    private String activate;
	public String getDepCode() {
		return depCode;
	}
	public void setDepCode(String depCode) {
		this.depCode = depCode;
	}
	public String getDepName() {
		return depName;
	}
	public void setDepName(String depName) {
		this.depName = depName;
	}
	public String getChargeId() {
		return chargeId;
	}
	public void setChargeId(String chargeId) {
		this.chargeId = chargeId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getActivate() {
		return activate;
	}
	public void setActivate(String activate) {
		this.activate = activate;
	}
	
}
