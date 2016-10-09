package com.dodopal.product.web.param;

import java.util.List;

import com.dodopal.product.web.mobileBean.MobileTypeWhiteListModel;

/**
 * @author lifeng@dodopal.com
 */

public class MobileTypeWhiteListResponse extends BaseResponse {
	private List<MobileTypeWhiteListModel> list;

	public List<MobileTypeWhiteListModel> getList() {
		return list;
	}

	public void setList(List<MobileTypeWhiteListModel> list) {
		this.list = list;
	}

}
