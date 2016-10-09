package com.dodopal.oss.business.dao;

import com.dodopal.oss.business.model.ProductOrder;

public interface ProductOrderMapper {

    /**
     * 查询产品库订单详情by订单号
     * @param proOrderNum
     * @return
     */
    public ProductOrder getProductOrderByProOrderNum(String proOrderNum);

    /**
     * 异常充值订单，处理完异常，更改订单状态
     * @param order
     * @return
     */
    public int updateOrderStatusAfterHandleException(ProductOrder order);
    

}
