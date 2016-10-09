package com.dodopal.product.web.param;

/**
 * 联合找回密码
 * 
 * @author lifeng@dodopal.com
 */

public class MobileUnionRetrieveRequest extends BaseRequest {
	private String userid;
	private String usertype;
	private String userpwd;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getUserpwd() {
		return userpwd;
	}

	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}

}
