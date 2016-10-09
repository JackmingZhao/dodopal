package com.dodopal.transfer.business.dao;

import com.dodopal.transfer.business.model.target.TranDiscountDate;

public interface TranDiscountDateMapper {
    public TranDiscountDate findTranDiscountDate(String merCode);
    public void addTranDiscountDate(TranDiscountDate tranDiscountDate);
}
