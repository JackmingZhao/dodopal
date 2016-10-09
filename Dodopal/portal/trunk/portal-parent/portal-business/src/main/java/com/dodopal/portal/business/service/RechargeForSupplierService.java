package com.dodopal.portal.business.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dodopal.api.product.dto.query.ProductOrderQueryDTO;
import com.dodopal.api.product.dto.query.RechargeStatisticsYktQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.RechargeStatisticsYktBean;
import com.dodopal.portal.business.bean.TransactionDetailsBean;
import com.dodopal.portal.business.bean.query.ProductOrderQueryBean;
import com.dodopal.portal.business.bean.query.RechargeStatisticsYktQuery;

/**
 * 供应商(通卡)查询统计
 * @author hxc
 *
 */
public interface RechargeForSupplierService {

	/**
	 * 一卡通充值统计查询
	 * @param query
	 * @return
	 */
	public DodopalResponse<DodopalDataPage<RechargeStatisticsYktBean>> queryCardRechargeForSupplier(RechargeStatisticsYktQuery query);
	
	/**
	 * 一卡通充值交易详细查询
	 * @param queryDTO
	 * @return
	 */
	public DodopalResponse<DodopalDataPage<TransactionDetailsBean>> queryCardRechargeDetails(ProductOrderQueryBean query);
	
	/**
	 * 启用停用
	 * @param bind
	 * @param codes
	 * @return
	 */
	public DodopalResponse<Integer> startOrStopMerSupplier(String bind, List<String> codes);
	
	/**
	 * 一卡通充值统计导出
	 * @param response
	 * @param merCountQuery
	 * @return
	 */
	public DodopalResponse<String> exportRechargeForSupplier(HttpServletRequest request,HttpServletResponse response, RechargeStatisticsYktQueryDTO merCountQuery);
	
	/**
	 * 一卡通充值详情导出
	 * @param response
	 * @param merCountQuery
	 * @return
	 */
	public DodopalResponse<String> exportCardRechargeDetails(HttpServletRequest request,HttpServletResponse response, ProductOrderQueryDTO merCountQuery);
}
