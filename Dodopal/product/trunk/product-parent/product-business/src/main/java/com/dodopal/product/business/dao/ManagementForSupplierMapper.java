package com.dodopal.product.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.api.product.dto.query.RechargeStatisticsYktQueryDTO;
import com.dodopal.product.business.model.ProductOrder;
import com.dodopal.product.business.model.RechargeStatisticsYktOrder;
import com.dodopal.product.business.model.YktCardConsumStatistics;
import com.dodopal.product.business.model.YktCardConsumTradeDetail;
import com.dodopal.product.business.model.query.ProductOrderQuery;
import com.dodopal.product.business.model.query.YktCardConsumStatisticsQuery;
import com.dodopal.product.business.model.query.YktCardConsumTradeDetailQuery;
import com.dodopal.product.business.model.query.ProductOrderQuery;

public interface ManagementForSupplierMapper {

	/**
	 * 一卡通充值统计查询
	 * @param query
	 * @return List<RechargeStatisticsYktOrder>
	 */
	public List<RechargeStatisticsYktOrder> queryCardRechargeForSupplierByPage(RechargeStatisticsYktQueryDTO query);
	
	/**
	 * 一卡通充值交易详细查询
	 * @param query
	 * @return List<ProductOrder>
	 */
	public List<ProductOrder> queryCardRechargeDetailsByPage(ProductOrderQuery query);
	
	/**
	 * 启用停用
	 * @param bind
	 * @param codes
	 * @return
	 */
	public int startOrStopMerSupplier(@Param("bind") String bind, @Param("codes") List<String> codes);
    /**
     * 一卡通消费统计查询
     * @param query
     * @return List<YktCardConsumStatistics>
     */
    public List<YktCardConsumStatistics> queryCardConsumForSupplierByPage(RechargeStatisticsYktQueryDTO query);
    /**
     * 一卡通消费交易详细查询
     * @param query
     * @return List<YktCardConsumTradeDetail>
     */
    public List<YktCardConsumTradeDetail> queryCardConsumDetailsByPage(YktCardConsumTradeDetailQuery query);

    public List<YktCardConsumStatistics> exportCardConsumForSupp(RechargeStatisticsYktQueryDTO query);
    
    /**
     * 一卡通消费业务订单汇总
     * @param query
     * @return
     */
    public List<YktCardConsumStatistics> findCardConsumCollectByPage(RechargeStatisticsYktQueryDTO query);
    
    /**
     * 一卡通消费业务订单汇总详细查询
     * @param query
     * @return
     */
    public List<YktCardConsumTradeDetail> queryCardConsumCollectDetailsByPage(YktCardConsumTradeDetailQuery query);
}
