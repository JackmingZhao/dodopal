package com.dodopal.product.web.param;

/**
 * @author lifeng@dodopal.com
 */

public class BaseResponse {
	// 追踪号(用于日志追踪，不参与验签)
	// private String track_id;
	// 签名方式(不参与签名)
	private String sign_type;
	// 签名(不参与签名)
	private String sign;
	// 编码字符集
	private String input_charset;
	// 响应码
	private String respcode;

	public String getSign_type() {
		return sign_type;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getInput_charset() {
		return input_charset;
	}

	public void setInput_charset(String input_charset) {
		this.input_charset = input_charset;
	}

	public String getRespcode() {
		return respcode;
	}

	public void setRespcode(String respcode) {
		this.respcode = respcode;
	}

}
