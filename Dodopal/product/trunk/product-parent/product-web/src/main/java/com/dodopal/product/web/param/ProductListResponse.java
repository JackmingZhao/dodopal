package com.dodopal.product.web.param;

import java.util.List;

public class ProductListResponse<T> extends BaseResponse {
	
	private String proversion;
	
	private List<T> list;

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public String getProversion() {
		return proversion;
	}

	public void setProversion(String proversion) {
		this.proversion = proversion;
	}

}
