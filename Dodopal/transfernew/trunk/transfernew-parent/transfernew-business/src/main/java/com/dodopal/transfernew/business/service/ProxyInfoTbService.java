package com.dodopal.transfernew.business.service;

import com.dodopal.common.model.DodopalResponse;
import com.dodopal.transfernew.business.model.old.Proxyinfotb;

public interface ProxyInfoTbService {

	public DodopalResponse<String> insertDataByProxyId(String cityCode);
	
	public DodopalResponse<String> stepsInsertDate(Proxyinfotb proxyinfotb, String batchId);
}
