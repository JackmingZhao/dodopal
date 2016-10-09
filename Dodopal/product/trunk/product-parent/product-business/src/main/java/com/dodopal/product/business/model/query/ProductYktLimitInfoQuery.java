package com.dodopal.product.business.model.query;

import com.dodopal.common.model.QueryBase;

public class ProductYktLimitInfoQuery extends QueryBase {

	private static final long serialVersionUID = -3330346763699042960L;

	private String yktName;

	public String getYktName() {
		return yktName;
	}

	public void setYktName(String yktName) {
		this.yktName = yktName;
	}
}
