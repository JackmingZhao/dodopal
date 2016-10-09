package com.dodopal.oss.business.model;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.dodopal.common.model.BaseEntity;
import com.dodopal.oss.business.util.CustomDateSerializer;

public class DdicResMsg extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7488838813104615080L;
	
	
	/**
	 * 消息编码
	 */
	private String msgCode;
	
	/**
	 * 消息类型
	 */
	private String msgType;
	
	/**
	 * 消息内容
	 */
	private String message;

	
	public String getDdicResMsgId() {
		return getId();
	}
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getViewCreateDate() {
		return getCreateDate();
	}
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getViewUpdateDate() {
		return getUpdateDate();
	}
	
	
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
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	


	
}
