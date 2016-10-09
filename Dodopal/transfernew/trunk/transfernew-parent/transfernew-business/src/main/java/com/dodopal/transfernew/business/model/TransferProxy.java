package com.dodopal.transfernew.business.model;

import java.io.Serializable;

/**
 * @author lifeng@dodopal.com
 */

public class TransferProxy implements Serializable {
	private static final long serialVersionUID = -6975051192447820859L;
	// 城市编码
	private String cityNo;
	// 老平台网点id
	private String proxyId;
	// 迁移类型，0：pos迁为商户，1,：网点迁为商户
	private String transferType;
	// 新平台商户类型
	private String transferMerType;

	public String getCityNo() {
		return cityNo;
	}

	public void setCityNo(String cityNo) {
		this.cityNo = cityNo;
	}

	public String getProxyId() {
		return proxyId;
	}

	public void setProxyId(String proxyId) {
		this.proxyId = proxyId;
	}

	public String getTransferType() {
		return transferType;
	}

	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}

	public String getTransferMerType() {
		return transferMerType;
	}

	public void setTransferMerType(String transferMerType) {
		this.transferMerType = transferMerType;
	}

}
