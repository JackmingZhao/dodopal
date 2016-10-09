package com.dodopal.transfernew.business.dao;

import com.dodopal.transfernew.business.model.TransferProxy;

/**
 * @author lifeng@dodopal.com
 */

public interface TransferProxyMapper {
	public TransferProxy findTransferProxyByProxyId(String proxyId);
}
