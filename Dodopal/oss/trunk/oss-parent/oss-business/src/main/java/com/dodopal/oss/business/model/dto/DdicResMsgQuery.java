package com.dodopal.oss.business.model.dto;

import com.dodopal.common.model.QueryBase;

public class DdicResMsgQuery extends QueryBase {

	private static final long serialVersionUID = -7155628208923046729L;
	
	private String msgCode;
	private String msgType;
	
	public String getMsgCode() {
		return msgCode;
	}
	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	
	
	
}
