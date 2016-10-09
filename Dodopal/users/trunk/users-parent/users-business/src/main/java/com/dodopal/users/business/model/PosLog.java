package com.dodopal.users.business.model;

import com.dodopal.common.model.BaseEntity;

public class PosLog extends BaseEntity {

	private static final long serialVersionUID = -8435290957808927314L;

	/** POS号 */
	private String code;

	/** POS状态 */
	private String operStatus;

	/** 商户编号 */
	private String merCode;

	/** 商户名称 */
	private String merName;

	/** 操作人名称 */
	private String operName;
	
	/** 来源*/
	private String source;

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

	public String getMerName() {
		return merName;
	}

	public void setMerName(String merName) {
		this.merName = merName;
	}

	public String getOperStatus() {
		return operStatus;
	}

	public void setOperStatus(String operStatus) {
		this.operStatus = operStatus;
	}

	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	
}