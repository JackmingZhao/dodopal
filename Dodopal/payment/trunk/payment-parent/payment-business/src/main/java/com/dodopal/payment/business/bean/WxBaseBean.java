package com.dodopal.payment.business.bean;


/**
 * 微信退款 数据封装 xml对象
 * 
 * @author 
 *
 */
public class WxBaseBean {
	
	private String return_code = "";// 返回状态码
	private String return_msg = "";// 返回信息: 如非空，为错误原因-签名失败、参数格式校验错误
	private String result_code = "";// 业务结果
	private String err_code = "";// 错误代码
	private String err_code_des = "";// 错误代码描述
	
	
	private String appid = "";// 公众账号ID
	private String mch_id = "";// 商户号
	private String device_info = "";// 设备号
	private String nonce_str = "";// 随机字符串
	private String sign = "";// 签名

	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getMch_id() {
		return mch_id;
	}
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}
	public String getDevice_info() {
		return device_info;
	}
	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}
	public String getNonce_str() {
		return nonce_str;
	}
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getReturn_code() {
		return return_code;
	}
	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}
	public String getReturn_msg() {
		return return_msg;
	}
	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}
	public String getResult_code() {
		return result_code;
	}
	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}
	public String getErr_code() {
		return err_code;
	}
	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}
	public String getErr_code_des() {
		return err_code_des;
	}
	public void setErr_code_des(String err_code_des) {
		this.err_code_des = err_code_des;
	}
	
	
}
