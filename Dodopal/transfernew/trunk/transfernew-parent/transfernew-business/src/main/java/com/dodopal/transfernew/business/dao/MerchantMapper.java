package com.dodopal.transfernew.business.dao;

import com.dodopal.transfernew.business.model.transfer.Merchant;

public interface MerchantMapper {
	// 商户号序列
	public String getMerCodeSeq();

	// 添加商户信息
	public int addMerchant(Merchant merchant);

}
