package com.dodopal.portal.delegate;

import com.dodopal.api.product.dto.ProductOrderDTO;
import com.dodopal.api.product.dto.YktCardConsumTradeDetailDTO;
import com.dodopal.api.product.dto.query.ProductOrderQueryDTO;
import com.dodopal.api.product.dto.query.YktCardConsumTradeDetailQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

public interface ManagementForSupplierDelegate {

	
	/**
	 * 一卡通充值交易详细查询
	 * @param queryDTO
	 * @return
	 */
	public DodopalResponse<DodopalDataPage<ProductOrderDTO>> queryCardRechargeDetails(ProductOrderQueryDTO queryDTO);
	
	/**
     * 一卡通消费交易详细查询
     * @param queryDTO
     * @return
     */
    public DodopalResponse<DodopalDataPage<YktCardConsumTradeDetailDTO>> queryCardConsumDetails(YktCardConsumTradeDetailQueryDTO query);
    
    /**
     * 一卡通消费业务订单汇总详细查询
     * @param queryDTO
     * @return
     */
    public DodopalResponse<DodopalDataPage<YktCardConsumTradeDetailDTO>> queryCardConsumCollectDetailsByPage(YktCardConsumTradeDetailQueryDTO query);
}
