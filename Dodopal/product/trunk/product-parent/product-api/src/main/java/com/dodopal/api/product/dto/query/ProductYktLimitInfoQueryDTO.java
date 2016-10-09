package com.dodopal.api.product.dto.query;

import com.dodopal.common.model.QueryBase;

public class ProductYktLimitInfoQueryDTO extends QueryBase {

	private static final long serialVersionUID = -7361835197318621370L;

	private String yktName;

	public String getYktName() {
		return yktName;
	}

	public void setYktName(String yktName) {
		this.yktName = yktName;
	}

}
