package com.dodopal.running.business.dao;

import java.util.List;

import com.dodopal.running.business.model.ProductOrder;

public interface YktRepairMapper {
    public List<ProductOrder> queryPayExp(int thresholdTime);

    public List<ProductOrder> queryDeblockFund(int thresholdTime);

    public List<ProductOrder> queryDeduct(int thresholdTime);

}
