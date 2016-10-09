package com.dodopal.transfernew.business.dao;

import com.dodopal.transfernew.business.model.transfer.TranDiscountDate;

public interface TranDiscountDateMapper {
    public TranDiscountDate findTranDiscountDate(String merCode);
    public void addTranDiscountDate(TranDiscountDate tranDiscountDate);
}
