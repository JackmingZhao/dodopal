package com.dodopal.api.product.facade;

import java.util.List;

import com.dodopal.api.product.dto.ProductOrderDTO;
import com.dodopal.api.product.dto.RechargeStatisticsYktOrderDTO;
import com.dodopal.api.product.dto.YktCardConsumStatisticsDTO;
import com.dodopal.api.product.dto.YktCardConsumTradeDetailDTO;
import com.dodopal.api.product.dto.query.ProductOrderQueryDTO;
import com.dodopal.api.product.dto.query.RechargeStatisticsYktQueryDTO;
import com.dodopal.api.product.dto.query.YktCardConsumTradeDetailQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

public interface ManagementForSupplierFacade {

	/**
	 * 一卡通充值统计查询
	 * @param query
	 * @return
	 */
	public DodopalResponse<DodopalDataPage<RechargeStatisticsYktOrderDTO>> queryCardRechargeForSupplier(RechargeStatisticsYktQueryDTO query);
	
	/**
	 * 一卡通充值交易详细查询
	 * @param queryDTO
	 * @return
	 */
	public DodopalResponse<DodopalDataPage<ProductOrderDTO>> queryCardRechargeDetails(ProductOrderQueryDTO queryDTO);
	
	/**
	 * 启用停用
	 * @param bind
	 * @param codes
	 * @return
	 */
	public DodopalResponse<Integer> startOrStopMerSupplier(String bind, List<String> codes);
	/**
     * 一卡通消费统计查询
     * @param query
     * @return
     */
    public DodopalResponse<DodopalDataPage<YktCardConsumStatisticsDTO>> queryCardConsumForSupplier(RechargeStatisticsYktQueryDTO query);
    
    /**
     * 一卡通消费交易详细查询
     * @param queryDTO
     * @return
     */
    public DodopalResponse<DodopalDataPage<YktCardConsumTradeDetailDTO>> queryCardConsumDetails(YktCardConsumTradeDetailQueryDTO queryDTO);
    
    public DodopalResponse<DodopalDataPage<YktCardConsumStatisticsDTO>> exportCardConsumForSupp(RechargeStatisticsYktQueryDTO query);

   // public DodopalResponse<DodopalDataPage<YktCardConsumTradeDetailDTO>> exportCardConsumDetailsForSupp(YktCardConsumTradeDetailQueryDTO query);
    
    /**
     * 一卡通消费业务订单汇总
     * @param query
     * @return
     */
    public DodopalResponse<DodopalDataPage<YktCardConsumStatisticsDTO>> findCardConsumCollectByPage(RechargeStatisticsYktQueryDTO query);
    
    
    /**
     * 一卡通消费业务订单汇总详细查询
     * @param queryDTO
     * @return
     */
    public DodopalResponse<DodopalDataPage<YktCardConsumTradeDetailDTO>> queryCardConsumCollectDetailsByPage(YktCardConsumTradeDetailQueryDTO queryDTO);
}
