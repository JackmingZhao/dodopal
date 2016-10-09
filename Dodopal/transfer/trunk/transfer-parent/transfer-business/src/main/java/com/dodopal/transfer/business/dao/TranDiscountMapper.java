package com.dodopal.transfer.business.dao;

import com.dodopal.transfer.business.model.target.TranDiscount;

public interface TranDiscountMapper {
    public int addTranDiscount(TranDiscount tranDiscount);
    public TranDiscount findTranDiscountCheck(TranDiscount tranDiscount);
    
    public TranDiscount findTranDiscountByOldSaleId(String oldSaleId);
}
 