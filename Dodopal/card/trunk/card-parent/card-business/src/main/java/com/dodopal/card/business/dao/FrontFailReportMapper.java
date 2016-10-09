package com.dodopal.card.business.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface FrontFailReportMapper {
	/**
     * @Title: queryCrdOrderCountByPrdOrderId 
     * @Description: 通过产品库订单号查询卡服务订单记录
     * @param prdOrderNo 产品库订单号
     * @return 订单数量
     */
	public Integer queryCrdOrderCountByPrdOrderId(@Param("PRO_ORDER_NUM")String prdOrderNo);
	/**
     * @Title: queryCardSysOrderStatus 
     * @Description: 通过产品库订单号查询卡服务订单记录
     * @param prdOrderNo 产品库订单号
     * @return 卡系统订单信息 键值对（根据需求设值）
     */
	public String queryCardSysOrderStatus(@Param("PRO_ORDER_NUM")String prdOrderNo);

	/**
     * @Title: updateCardSysOrderByPrdOrderNum 
     * @Description: 更新卡服务信息：交易应答码，卡服务订单状态，前卡服务订单状态
     * @param value RESPCODE:交易应答码，ORDERSTATES:卡服务订单状态
     * @return 更新条数
     */
	public int updateCardSysOrderByPrdOrderNum(Map<String, Object> value);
	
	/**
     * @Title: queryCardSysOrderNumByPrdOrderNum 
     * @Description: 通过产品库订单号查询卡服务订单号
     * @param prdOrderNo 
     * @return 更新条数
     */
    public String queryCardSysOrderNumByPrdOrderNum(String prdOrderNo);
	
}
