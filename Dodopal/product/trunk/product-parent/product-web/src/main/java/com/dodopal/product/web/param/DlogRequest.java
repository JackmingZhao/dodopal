package com.dodopal.product.web.param;

/**
 * @author lifeng@dodopal.com
 */

public class DlogRequest {
	// dll签名
	private String d_sign;
	// 日志所处阶段
	private String dlog_stage;
	// 时间格式：YYYYMMDDhhmmss
	private String dlog_systemdatetime;
	// 错误码
	private String dlog_code;
	// 错误信息
	private String dlog_message;
	// 产品库主订单号
	private String dlog_prdordernum;
	// 卡号
	private String dlog_tradecard;
	// 城市前置返回APDU指令
	private String dlog_apdu;
	// 执行APDU指令响应数据
	private String dlog_apdudata;
	// 执行APDU指令响应码
	private String dlog_statecode;

	public String getD_sign() {
		return d_sign;
	}

	public void setD_sign(String d_sign) {
		this.d_sign = d_sign;
	}

	public String getDlog_stage() {
		return dlog_stage;
	}

	public void setDlog_stage(String dlog_stage) {
		this.dlog_stage = dlog_stage;
	}

	public String getDlog_systemdatetime() {
		return dlog_systemdatetime;
	}

	public void setDlog_systemdatetime(String dlog_systemdatetime) {
		this.dlog_systemdatetime = dlog_systemdatetime;
	}

	public String getDlog_code() {
		return dlog_code;
	}

	public void setDlog_code(String dlog_code) {
		this.dlog_code = dlog_code;
	}

	public String getDlog_message() {
		return dlog_message;
	}

	public void setDlog_message(String dlog_message) {
		this.dlog_message = dlog_message;
	}

	public String getDlog_prdordernum() {
		return dlog_prdordernum;
	}

	public void setDlog_prdordernum(String dlog_prdordernum) {
		this.dlog_prdordernum = dlog_prdordernum;
	}

	public String getDlog_tradecard() {
		return dlog_tradecard;
	}

	public void setDlog_tradecard(String dlog_tradecard) {
		this.dlog_tradecard = dlog_tradecard;
	}

	public String getDlog_apdu() {
		return dlog_apdu;
	}

	public void setDlog_apdu(String dlog_apdu) {
		this.dlog_apdu = dlog_apdu;
	}

	public String getDlog_apdudata() {
		return dlog_apdudata;
	}

	public void setDlog_apdudata(String dlog_apdudata) {
		this.dlog_apdudata = dlog_apdudata;
	}

	public String getDlog_statecode() {
		return dlog_statecode;
	}

	public void setDlog_statecode(String dlog_statecode) {
		this.dlog_statecode = dlog_statecode;
	}

}