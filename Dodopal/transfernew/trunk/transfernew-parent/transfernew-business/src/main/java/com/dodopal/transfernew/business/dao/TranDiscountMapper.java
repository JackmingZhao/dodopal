package com.dodopal.transfernew.business.dao;

import java.util.List;

import com.dodopal.transfernew.business.model.transfer.TranDiscount;

public interface TranDiscountMapper {
	public int addTranDiscount(TranDiscount tranDiscount);

	public TranDiscount findTranDiscountCheck(TranDiscount tranDiscount);

	public TranDiscount findTranDiscountByOldSaleId(String oldSaleId);
	
	public int batchAddTranDiscount(List<TranDiscount> list);
}
