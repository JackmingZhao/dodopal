package com.dodopal.product.business.service;

import java.util.List;



import org.apache.ibatis.annotations.Param;

import com.dodopal.api.product.dto.RechargeStatisticsYktOrderDTO;
import com.dodopal.api.product.dto.YktCardConsumStatisticsDTO;
import com.dodopal.api.product.dto.query.RechargeStatisticsYktQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.business.model.ProductOrder;
import com.dodopal.product.business.model.YktCardConsumStatistics;
import com.dodopal.product.business.model.YktCardConsumTradeDetail;
import com.dodopal.product.business.model.query.ProductOrderQuery;
import com.dodopal.product.business.model.query.YktCardConsumTradeDetailQuery;

public interface ManagementForSupplierService {

	/**
	 * 一卡通充值统计查询
	 * @param query
	 * @return 
	 */
	public DodopalResponse<DodopalDataPage<RechargeStatisticsYktOrderDTO>>  queryCardRechargeForSupplier(RechargeStatisticsYktQueryDTO query);
	
	/**
	 * 一卡通充值交易详细查询
	 * @param query
	 * @return 
	 */
	public DodopalDataPage<ProductOrder> queryCardRechargeDetails(ProductOrderQuery query);
	
	/**
	 * 启用停用
	 * @param bind
	 * @param codes
	 * @return
	 */
	public int startOrStopMerSupplier(String bind, List<String> codes);

	/**
     * 一卡通消费统计查询
     * @param query
     * @return 
     */
    public DodopalResponse<DodopalDataPage<YktCardConsumStatisticsDTO>> queryCardConsumForSupplier(RechargeStatisticsYktQueryDTO query);
    /**
     * 一卡通消费交易详细查询
     * @param query
     * @return 
     */
    public DodopalDataPage<YktCardConsumTradeDetail> queryCardConsumDetails(YktCardConsumTradeDetailQuery query);

    public DodopalResponse<DodopalDataPage<YktCardConsumStatisticsDTO>> exportCardConsumForSupp(RechargeStatisticsYktQueryDTO query);
    
   // public DodopalDataPage<YktCardConsumTradeDetail> exportCardConsumDetailsForSupp(YktCardConsumTradeDetailQuery query);
    
    /**
     * 一卡通消费业务订单汇总
     * @param query
     * @return
     */
    public DodopalResponse<DodopalDataPage<YktCardConsumStatisticsDTO>> findCardConsumCollectByPage(RechargeStatisticsYktQueryDTO query);
    
    /**
     * 一卡通消费业务订单汇总详细查询
     * @param query
     * @return
     */
    public DodopalDataPage<YktCardConsumTradeDetail> queryCardConsumCollectDetailsByPage(YktCardConsumTradeDetailQuery query);
}
