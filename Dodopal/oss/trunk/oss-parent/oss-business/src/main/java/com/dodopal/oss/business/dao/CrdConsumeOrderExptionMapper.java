package com.dodopal.oss.business.dao;

import com.dodopal.oss.business.model.CrdConsumeOrderExption;

public interface CrdConsumeOrderExptionMapper {

	/**
     *  一卡通消费异常处理, 更新产品库消费主订单表  状态  ,如果选择消费成功::状态:2(2：支付成功);如果失败::状态:1(1：支付失败)
     * @param consumeOrder
     * @return
     */
    public int updatePrdPurchaseOrderAfterHandleException(CrdConsumeOrderExption consumeOrderExption);
    
    
    /**
     * 更新产品库公交卡收单记录表 状态, 如果选择消费成功::内部状态:(20:扣款成功;);如果失败::内部状态(12:扣款失败)
     * @param consumeOrder
     * @return
     */
    public int updatePrdPurchaseOrderRecordAfterHandleException(CrdConsumeOrderExption consumeOrderExption);
    
    /**
     * 根据订单号查询产品订单信息
     * @param orderNum
     * @return
     */
    public CrdConsumeOrderExption findPrdPurchaseOrderByOrderNum(String orderNum);
    
    /**
     * 根据订单号查询新产品库公交卡收单记录表
     * @param orderNum
     * @return
     */
    public CrdConsumeOrderExption  findPurchaseOrderRecordByOrdeerNum(String orderNum);
}