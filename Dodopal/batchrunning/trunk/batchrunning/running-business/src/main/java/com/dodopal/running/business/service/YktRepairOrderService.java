package com.dodopal.running.business.service;

import java.util.List;

import com.dodopal.running.business.model.ProductOrder;

public interface YktRepairOrderService {

    /**
     * 查询支付异常订单
     * @return
     */
    public List<ProductOrder> queryPayExp(int thresholdTime);

    /**
     * 查询资金解冻订单
     * @return
     */
    public List<ProductOrder> queryDeblockFund(int thresholdTime);

    /**
     * 查询资金扣款
     * @return
     */
    public List<ProductOrder> queryDeduct(int thresholdTime);

    /**
     * 处理支付异常数据
     */
    public void repairPayExp(List<ProductOrder> list, String userId);

    /*
     * 处理资金解冻数据
     */
    public void repairDeblockFund(List<ProductOrder> list, String userId);

    /*
     * 处理资金扣款数据
     */
    public void repairDeduct(List<ProductOrder> list, String userId);

}
