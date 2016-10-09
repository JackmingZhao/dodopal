package com.dodopal.product.web.param;

import java.util.List;

import com.dodopal.product.business.bean.PayWayResultBean;

public class PayWayResponse extends BaseResponse{

	List<PayWayResultBean> payWayList;

	public List<PayWayResultBean> getPayWayList() {
		return payWayList;
	}

	public void setPayWayList(List<PayWayResultBean> payWayList) {
		this.payWayList = payWayList;
	}
	
	
}
