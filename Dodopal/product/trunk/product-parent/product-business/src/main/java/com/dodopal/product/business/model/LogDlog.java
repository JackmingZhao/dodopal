package com.dodopal.product.business.model;

import com.dodopal.common.model.BaseEntity;

/**
 * DLL交易日志
 * 
 * @author lifeng@dodopal.com
 */

public class LogDlog extends BaseEntity {
	private static final long serialVersionUID = -4263101167437483912L;
	// 日志所处阶段
	private String dlogStage;
	// 时间
	private String dlogSystemdatetime;
	// 错误码
	private String dlogCode;
	// 错误信息
	private String dlogMessage;
	// 产品库主订单号
	private String dlogPrdordernum;
	// 卡号
	private String dlogTradecard;
	// 城市前置返回APDU指令
	private String dlogApdu;
	// 执行APDU指令响应数据
	private String dlogApdudata;
	// 执行APDU指令响应码
	private String dlogStatecode;

	public String getDlogStage() {
		return dlogStage;
	}

	public void setDlogStage(String dlogStage) {
		this.dlogStage = dlogStage;
	}

	public String getDlogSystemdatetime() {
		return dlogSystemdatetime;
	}

	public void setDlogSystemdatetime(String dlogSystemdatetime) {
		this.dlogSystemdatetime = dlogSystemdatetime;
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

	public String getDlogPrdordernum() {
		return dlogPrdordernum;
	}

	public void setDlogPrdordernum(String dlogPrdordernum) {
		this.dlogPrdordernum = dlogPrdordernum;
	}

	public String getDlogTradecard() {
		return dlogTradecard;
	}

	public void setDlogTradecard(String dlogTradecard) {
		this.dlogTradecard = dlogTradecard;
	}

	public String getDlogApdu() {
		return dlogApdu;
	}

	public void setDlogApdu(String dlogApdu) {
		this.dlogApdu = dlogApdu;
	}

	public String getDlogApdudata() {
		return dlogApdudata;
	}

	public void setDlogApdudata(String dlogApdudata) {
		this.dlogApdudata = dlogApdudata;
	}

	public String getDlogStatecode() {
		return dlogStatecode;
	}

	public void setDlogStatecode(String dlogStatecode) {
		this.dlogStatecode = dlogStatecode;
	}

}
