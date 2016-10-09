package com.dodopal.card.business.service;

import java.util.Map;

public interface FrontFailPeportService {
	/**
     * @Title: queryCrdOrderCountByPrdOrderId 
     * @Description: 检查卡服务订单是否存在
     * @param prdOrderNo 产品库订单号
     * @return 卡服务订单数量
     */
	public Integer queryCrdOrderCountByPrdOrderId(String prdOrderNo);
	
	/**
     * @Title: queryCardSysOrderStatus 
     * @Description: 通过产品库订单号查询卡服务订单状态
     * @param prdOrderNo 产品库订单号
     * @return 卡系统订单状态
     */
	public String queryCardSysOrderStatus(String prdOrderNo);
	
	/**
     * @Title: queryCardSysOrderNumByPrdOrderNum 
     * @Description: 通过产品库订单号查询卡服务订单号
     * @param prdOrderNo 产品库订单号
     * @return 卡系统订单状态
     */
    public String queryCardSysOrderNumByPrdOrderNum(String prdOrderNo);
	
	/**
     * @Title: updateCardSysOrderByPrdOrderNum 
     * @Description: 更新卡服务信息：交易应答码，卡服务订单状态，前卡服务订单状态
     * @param value 要更新的数据，键：字段名，值字段值
     * @return 更新条数
     */
	public int updateCardSysOrderByPrdOrderNum(Map<String, Object> value);
}
