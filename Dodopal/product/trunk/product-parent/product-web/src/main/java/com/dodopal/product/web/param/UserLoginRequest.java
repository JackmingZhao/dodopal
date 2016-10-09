package com.dodopal.product.web.param;

/**
 * @author lifeng@dodopal.com
 */

public class UserLoginRequest extends BaseRequest {
	// 来源:手机端2,VC端：3,安卓：6,IOS：7
	private String source;
	// 用户名
	private String username;
	// 密码
	private String userpwd;
	// 老系统loginid
	private String loginid;
	// 老系统usertype，个人0，集团1，网点2，商户3
	private String usertype;

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
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

	public String getLoginid() {
		return loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

}
