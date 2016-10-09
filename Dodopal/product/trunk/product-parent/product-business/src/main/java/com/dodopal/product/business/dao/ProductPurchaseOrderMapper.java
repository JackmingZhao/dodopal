package com.dodopal.product.business.dao;

import org.apache.ibatis.annotations.Param;

import com.dodopal.product.business.model.ProductPurchaseOrder;

public interface ProductPurchaseOrderMapper {
    
    /**
     * 取得产品库公交卡消费主订单编号后五位:五位数据库cycle sequence（循环使用） 
     * @return
     */
    public String getPrdPurchaseOrderNumSeq();
    
    /**
     * 创建公交卡消费主订单
     * @param purchaseOrder
     * @return
     */
    public int addProductPurchaseOrder(ProductPurchaseOrder purchaseOrder);
    
    /**
     * 根据主订单编号获取消费主订单信息
     * @param purchaseOrderNum 主订单编号
     * @return
     */
    public ProductPurchaseOrder getPurchaseOrderByOrderNum(String purchaseOrderNum);
    
    /**
     * 更新产品库收单状态
     * @param order 主订单信息
     * @return
     */
    public int updatePurchaseOrder(ProductPurchaseOrder order);
    
    /**
     * 北京V71更新产品库收单状态
     * @param order 主订单信息
     * @return
     */
    public int updatePurchaseOrderForV71(ProductPurchaseOrder order);

    /**
     * 交易标识查询
     * @param cardno 卡号
     * @param queryDate  交易日期
     * @return
     */
    public int queryOrderFlag(@Param("cardno") String cardno,@Param("queryDate") String queryDate);
}
