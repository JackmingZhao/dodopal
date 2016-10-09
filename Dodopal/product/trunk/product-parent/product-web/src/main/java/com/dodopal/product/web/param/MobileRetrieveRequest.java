package com.dodopal.product.web.param;

public class MobileRetrieveRequest extends BaseRequest {
	
	/**
	 * 手机号
	 */
	private String merusermobile;//
	
	/**
	 * 新密码
	 */
	private String userpwd;//

	public String getMerusermobile() {
		return merusermobile;
	}

	public void setMerusermobile(String merusermobile) {
		this.merusermobile = merusermobile;
	}

	public String getUserpwd() {
		return userpwd;
	}

	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}
	
	
}
