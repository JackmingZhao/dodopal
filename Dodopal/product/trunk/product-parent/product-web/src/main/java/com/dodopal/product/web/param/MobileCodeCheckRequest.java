package com.dodopal.product.web.param;

/**
 * @author lifeng@dodopal.com
 */

public class MobileCodeCheckRequest extends BaseRequest {
	// 手机号
	private String merusermobile;
	// 验证码
	private String dypwd;
	// 序号
	private String pwdseq;

	public String getMerusermobile() {
		return merusermobile;
	}

	public void setMerusermobile(String merusermobile) {
		this.merusermobile = merusermobile;
	}

	public String getDypwd() {
		return dypwd;
	}

	public void setDypwd(String dypwd) {
		this.dypwd = dypwd;
	}

	public String getPwdseq() {
		return pwdseq;
	}

	public void setPwdseq(String pwdseq) {
		this.pwdseq = pwdseq;
	}

}
