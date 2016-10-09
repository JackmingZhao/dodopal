package com.dodopal.product.web.param;

public class RegisterComUserResponse {
	// 响应码
	private String respcode; 
	
	//错误信息
	private String errorreason;
	
	//用户ID
	private String userid;

	public String getRespcode() {
		return respcode;
	}

	public void setRespcode(String respcode) {
		this.respcode = respcode;
	}

	public String getErrorreason() {
		return errorreason;
	}

	public void setErrorreason(String errorreason) {
		this.errorreason = errorreason;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	
}