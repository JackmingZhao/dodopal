package com.dodopal.api.users.dto;

import com.dodopal.common.model.BaseEntity;

/**
 * @author lifeng@dodopal.com
 */

public class MobileTypeWhiteListDTO extends BaseEntity {
	private static final long serialVersionUID = -597172497261885916L;
	/* 手机型号 */
	private String mobileType;
	/* 手机名称 */
	private String mobileName;
	/* 备注 */
	private String remark;

	private String activate;

	public String getMobileType() {
		return mobileType;
	}

	public void setMobileType(String mobileType) {
		this.mobileType = mobileType;
	}

	public String getMobileName() {
		return mobileName;
	}

	public void setMobileName(String mobileName) {
		this.mobileName = mobileName;
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
