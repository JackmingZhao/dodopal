package com.dodopal.product.web.param;

import java.util.List;

import com.dodopal.product.web.mobileBean.PosModel;

/**
 * @author lifeng@dodopal.com
 */

public class PosResponse extends BaseResponse {
	private List<PosModel> poslist;

	public List<PosModel> getPoslist() {
		return poslist;
	}

	public void setPoslist(List<PosModel> poslist) {
		this.poslist = poslist;
	}

}
