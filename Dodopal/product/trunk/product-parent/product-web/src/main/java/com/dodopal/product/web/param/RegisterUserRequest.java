package com.dodopal.product.web.param;

/**
 * @author lifeng@dodopal.com
 */

public class RegisterUserRequest extends BaseRequest {
	private String source;
	private String merusermobile;
	private String username;
	private String userpwd;
	private String citycode;

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getMerusermobile() {
		return merusermobile;
	}

	public void setMerusermobile(String merusermobile) {
		this.merusermobile = merusermobile;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserpwd() {
		return userpwd;
	}

	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}

	public String getCitycode() {
		return citycode;
	}

	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}

}
