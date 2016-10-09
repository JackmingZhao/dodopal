package com.dodopal.transfernew.business.dao;

import java.util.List;

import com.dodopal.transfernew.business.model.transfer.MerchantTranDiscount;

public interface MerchantTranDiscountMapper {
	public void addMerchantTranDiscount(MerchantTranDiscount merchantTranDiscount);

	public int batchAddMerchantTranDiscount(List<MerchantTranDiscount> list);
}
