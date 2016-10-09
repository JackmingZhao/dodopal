package com.dodopal.users.business.model;

import com.dodopal.common.model.BaseEntity;

public class PosMerTb extends BaseEntity {

	private static final long serialVersionUID = 3387594206488609984L;

	/** POS号 */
	private String code;
	
	/** 商户编号 */
	private String merCode;	
	
	/** 备注 */
	private String comments;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMerCode() {
		return merCode;
	}

	public void setMerCode(String merCode) {
		this.merCode = merCode;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	
}