package com.dodopal.oss.business.model;

import com.dodopal.common.model.BaseEntity;

public class LogDLog extends BaseEntity {
	private static final long serialVersionUID = 1503038175945155694L;
	// 日志所处阶段
	private String dlogStage;
	//时间
	private String dlogSystemDateTime;
	//错误码
	private String dlogCode;
	//错误信息
	private String dlogMessage;
	//产品库主订单号
	private String dlogPrdorderNum;
	//卡号
	private String dlogTradeCard;
	//城市前置返回APDU指令
	private String dlogApdu;
	//执行APDU指令响应数据
	private String dlogApduData;
	//执行APDU指令响应码
	private String dlogStateCode;
	
	public String getDlogStage() {
		return dlogStage;
	}
	public void setDlogStage(String dlogStage) {
		this.dlogStage = dlogStage;
	}
	public String getDlogSystemDateTime() {
		return dlogSystemDateTime;
	}
	public void setDlogSystemDateTime(String dlogSystemDateTime) {
		this.dlogSystemDateTime = dlogSystemDateTime;
	}
	public String getDlogCode() {
		return dlogCode;
	}
	public void setDlogCode(String dlogCode) {
		this.dlogCode = dlogCode;
	}
	public String getDlogMessage() {
		return dlogMessage;
	}
	public void setDlogMessage(String dlogMessage) {
		this.dlogMessage = dlogMessage;
	}
	public String getDlogPrdorderNum() {
		return dlogPrdorderNum;
	}
	public void setDlogPrdorderNum(String dlogPrdorderNum) {
		this.dlogPrdorderNum = dlogPrdorderNum;
	}
	public String getDlogTradeCard() {
		return dlogTradeCard;
	}
	public void setDlogTradeCard(String dlogTradeCard) {
		this.dlogTradeCard = dlogTradeCard;
	}
	public String getDlogApdu() {
		return dlogApdu;
	}
	public void setDlogApdu(String dlogApdu) {
		this.dlogApdu = dlogApdu;
	}
	public String getDlogApduData() {
		return dlogApduData;
	}
	public void setDlogApduData(String dlogApduData) {
		this.dlogApduData = dlogApduData;
	}
	public String getDlogStateCode() {
		return dlogStateCode;
	}
	public void setDlogStateCode(String dlogStateCode) {
		this.dlogStateCode = dlogStateCode;
	}
}
