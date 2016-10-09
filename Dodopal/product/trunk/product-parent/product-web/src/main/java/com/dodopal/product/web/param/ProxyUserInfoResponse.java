package com.dodopal.product.web.param;

import java.util.List;

import com.dodopal.product.web.mobileBean.UserInfoModel;

/**
 * @author lifeng@dodopal.com
 */

public class ProxyUserInfoResponse extends BaseResponse {
	private List<UserInfoModel> userlist;

	public List<UserInfoModel> getUserlist() {
		return userlist;
	}

	public void setUserlist(List<UserInfoModel> userlist) {
		this.userlist = userlist;
	}

}
