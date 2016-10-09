package com.dodopal.api.product.dto.query;

import com.dodopal.common.model.QueryBase;

public class ProductYKTQueryDTO extends QueryBase {

	private static final long serialVersionUID = -3330346763699042960L;

	private String yktName;

	private String activate;

	public String getYktName() {
		return yktName;
	}

	public void setYktName(String yktName) {
		this.yktName = yktName;
	}

	public String getActivate() {
		return activate;
	}

	public void setActivate(String activate) {
		this.activate = activate;
	}

}
