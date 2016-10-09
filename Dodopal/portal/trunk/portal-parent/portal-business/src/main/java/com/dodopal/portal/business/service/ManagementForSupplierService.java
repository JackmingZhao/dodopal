package com.dodopal.portal.business.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dodopal.api.product.dto.query.ProductOrderQueryDTO;
import com.dodopal.api.product.dto.query.YktCardConsumTradeDetailQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.TransactionDetailsBean;

public interface ManagementForSupplierService {
	/**
	 * 一卡通充值交易详细查询
	 * @param queryDTO
	 * @return
	 */
	public DodopalResponse<DodopalDataPage<TransactionDetailsBean>> queryCardRechargeDetails(ProductOrderQueryDTO queryDTO);
	
	/**
     * 一卡通消费交易详细查询
     * @param queryDTO
     * @return
     */
    public DodopalResponse<DodopalDataPage<TransactionDetailsBean>> queryCardConsumDetails(YktCardConsumTradeDetailQueryDTO query);
    
    
    /**
     * 一卡通充值导出
     * @param request
     * @param response
     * @param queryDTO
     * @return
     */
    public DodopalResponse<String> exportCardRechargeDetails(HttpServletRequest request, HttpServletResponse response,ProductOrderQueryDTO queryDTO);
    /**
     * 一卡通消费导出
     * @param request
     * @param response
     * @param queryDTO
     * @return
     */
    public DodopalResponse<String> exportCardConsumDetails(HttpServletRequest request,HttpServletResponse response,YktCardConsumTradeDetailQueryDTO queryDTO);
    
    /**
     * 业务订单汇总一卡通消费详细导出
     * @param request
     * @param response
     * @param queryDTO
     * @return
     */
    public DodopalResponse<String> exportCardConsumCollectDetails(HttpServletRequest request,HttpServletResponse response,
			YktCardConsumTradeDetailQueryDTO queryDTO);
    
    
    /**
     * 一卡通消费业务订单汇总详细查询
     * @param query
     * @return
     */
    public DodopalResponse<DodopalDataPage<TransactionDetailsBean>> queryCardConsumCollectDetails(YktCardConsumTradeDetailQueryDTO query);
    
}
