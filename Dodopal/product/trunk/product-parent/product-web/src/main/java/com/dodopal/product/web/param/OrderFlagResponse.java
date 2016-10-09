package com.dodopal.product.web.param;

public class OrderFlagResponse {
	// 响应码
	private String respcode; 
	
	//错误信息
	private String errorreason;
	
	//交易标示
	private String flag;

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

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	
}