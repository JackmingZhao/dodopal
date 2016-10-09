package com.dodopal.product.web.param;

/**
 * @author lifeng@dodopal.com
 */

public class SendRequest extends BaseRequest {
	// 手机号
	private String merusermobile;
	// 验证码类型,手机接收短信验证码类型,默认：1
	private String codetype;

	public String getMerusermobile() {
		return merusermobile;
	}

	public void setMerusermobile(String merusermobile) {
		this.merusermobile = merusermobile;
	}

	public String getCodetype() {
		return codetype;
	}

	public void setCodetype(String codetype) {
		this.codetype = codetype;
	}

}
