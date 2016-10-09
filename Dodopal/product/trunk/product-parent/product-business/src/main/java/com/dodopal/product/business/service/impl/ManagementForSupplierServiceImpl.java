package com.dodopal.product.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.product.dto.RechargeStatisticsYktOrderDTO;
import com.dodopal.api.product.dto.YktCardConsumStatisticsDTO;
import com.dodopal.api.product.dto.query.RechargeStatisticsYktQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.business.dao.ManagementForSupplierMapper;
import com.dodopal.product.business.model.ProductOrder;
import com.dodopal.product.business.model.RechargeStatisticsYktOrder;
import com.dodopal.product.business.model.YktCardConsumStatistics;
import com.dodopal.product.business.model.YktCardConsumTradeDetail;
import com.dodopal.product.business.model.query.ProductOrderQuery;
import com.dodopal.product.business.model.query.YktCardConsumStatisticsQuery;
import com.dodopal.product.business.model.query.YktCardConsumTradeDetailQuery;
import com.dodopal.product.business.service.ManagementForSupplierService;

@Service
public class ManagementForSupplierServiceImpl implements ManagementForSupplierService{

	@Autowired
	private ManagementForSupplierMapper supplierMapper;
	
	private Logger logger = LoggerFactory.getLogger(ManagementForSupplierServiceImpl.class);
	/**
	 * 一卡通充值统计查询
	 * @param query
	 * @return
	 */
	public DodopalResponse<DodopalDataPage<RechargeStatisticsYktOrderDTO>> queryCardRechargeForSupplier(RechargeStatisticsYktQueryDTO query) {
		DodopalResponse<DodopalDataPage<RechargeStatisticsYktOrderDTO>> response = new DodopalResponse<DodopalDataPage<RechargeStatisticsYktOrderDTO>>();
		List<RechargeStatisticsYktOrder> rechargeForSupplier = supplierMapper.queryCardRechargeForSupplierByPage(query);
		try {
			ArrayList<RechargeStatisticsYktOrderDTO> list = new ArrayList<RechargeStatisticsYktOrderDTO>();
			if(CollectionUtils.isNotEmpty(rechargeForSupplier)){
					for(RechargeStatisticsYktOrder order : rechargeForSupplier){
						RechargeStatisticsYktOrderDTO orderDTO = new RechargeStatisticsYktOrderDTO();
							PropertyUtils.copyProperties(orderDTO, order);
							list.add(orderDTO);
						}
			}
			DodopalDataPage<RechargeStatisticsYktOrderDTO> pages = new DodopalDataPage<RechargeStatisticsYktOrderDTO>(query.getPage(), list);
			response.setCode(ResponseCode.SUCCESS);
			response.setResponseEntity(pages);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("产品库中一卡通充值统计查询出错", e);
            response.setCode(ResponseCode.PRODUCT_ORDER_QUERY_ERROR);
		}
		return response;
	}
	
	/**
	 * 一卡通充值交易详细查询
	 * @param query
	 * @return 
	 */
	public DodopalDataPage<ProductOrder> queryCardRechargeDetails(ProductOrderQuery query) {
		List<ProductOrder> list = supplierMapper.queryCardRechargeDetailsByPage(query);
		DodopalDataPage<ProductOrder> pages = new DodopalDataPage<ProductOrder>(query.getPage(), list);
		return pages;
	}

	/**
	 * 启用停用
	 * @param bind
	 * @param codes
	 * @return
	 */
	public int startOrStopMerSupplier(String bind, List<String> codes) {
		return supplierMapper.startOrStopMerSupplier(bind, codes);
	}
	/**
     * 一卡通消费统计查询
     * @param query
     * @return
     */
    public DodopalResponse<DodopalDataPage<YktCardConsumStatisticsDTO>> queryCardConsumForSupplier(RechargeStatisticsYktQueryDTO query) {
        DodopalResponse<DodopalDataPage<YktCardConsumStatisticsDTO>> response = new DodopalResponse<DodopalDataPage<YktCardConsumStatisticsDTO>>();
        List<YktCardConsumStatistics> cardconsumeList = supplierMapper.queryCardConsumForSupplierByPage(query);        
        try {
            ArrayList<YktCardConsumStatisticsDTO> list = new ArrayList<YktCardConsumStatisticsDTO>();
            if(CollectionUtils.isNotEmpty(cardconsumeList)){
                    for(YktCardConsumStatistics order : cardconsumeList){
                        YktCardConsumStatisticsDTO orderDTO = new YktCardConsumStatisticsDTO();
                            PropertyUtils.copyProperties(orderDTO, order);
                            list.add(orderDTO);
                        }
            }
            DodopalDataPage<YktCardConsumStatisticsDTO> pages = new DodopalDataPage<YktCardConsumStatisticsDTO>(query.getPage(), list);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(pages);
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error("产品库中一卡通消费统计查询出错", e);
            response.setCode(ResponseCode.PRODUCT_ORDER_QUERY_ERROR);
        }
        return response;
        
        
    }
    
    public DodopalResponse<DodopalDataPage<YktCardConsumStatisticsDTO>> exportCardConsumForSupp(RechargeStatisticsYktQueryDTO query) {
        DodopalResponse<DodopalDataPage<YktCardConsumStatisticsDTO>> response = new DodopalResponse<DodopalDataPage<YktCardConsumStatisticsDTO>>();
        List<YktCardConsumStatistics> cardconsumeList = supplierMapper.exportCardConsumForSupp(query);        
        try {
            ArrayList<YktCardConsumStatisticsDTO> list = new ArrayList<YktCardConsumStatisticsDTO>();
            if(CollectionUtils.isNotEmpty(cardconsumeList)){
                    for(YktCardConsumStatistics order : cardconsumeList){
                        YktCardConsumStatisticsDTO orderDTO = new YktCardConsumStatisticsDTO();
                            PropertyUtils.copyProperties(orderDTO, order);
                            list.add(orderDTO);
                        }
            }
            DodopalDataPage<YktCardConsumStatisticsDTO> pages = new DodopalDataPage<YktCardConsumStatisticsDTO>(query.getPage(), list);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(pages);
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error("产品库中一卡通消费统计查询出错", e);
            response.setCode(ResponseCode.PRODUCT_ORDER_QUERY_ERROR);
        }
        return response;
        
        
    }
    /**
     * 一卡通消费交易详细查询
     * @param query
     * @return
     */
    public DodopalDataPage<YktCardConsumTradeDetail> queryCardConsumDetails(YktCardConsumTradeDetailQuery query) {
        List<YktCardConsumTradeDetail> list = supplierMapper.queryCardConsumDetailsByPage(query);
        DodopalDataPage<YktCardConsumTradeDetail> pages = new DodopalDataPage<YktCardConsumTradeDetail>(query.getPage(), list);
        return pages;
    }

    /**
     * 一卡通消费业务订单汇总
     * @param query
     * @return
     */
	public DodopalResponse<DodopalDataPage<YktCardConsumStatisticsDTO>> findCardConsumCollectByPage(RechargeStatisticsYktQueryDTO query) {
        DodopalResponse<DodopalDataPage<YktCardConsumStatisticsDTO>> response = new DodopalResponse<DodopalDataPage<YktCardConsumStatisticsDTO>>();
        List<YktCardConsumStatistics> cardconsumeList = supplierMapper.findCardConsumCollectByPage(query);        
        try {
            ArrayList<YktCardConsumStatisticsDTO> list = new ArrayList<YktCardConsumStatisticsDTO>();
            if(CollectionUtils.isNotEmpty(cardconsumeList)){
                    for(YktCardConsumStatistics order : cardconsumeList){
                        YktCardConsumStatisticsDTO orderDTO = new YktCardConsumStatisticsDTO();
                            PropertyUtils.copyProperties(orderDTO, order);
                            list.add(orderDTO);
                        }
            }
            DodopalDataPage<YktCardConsumStatisticsDTO> pages = new DodopalDataPage<YktCardConsumStatisticsDTO>(query.getPage(), list);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(pages);
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error("产品库中一卡通消费统计查询出错", e);
            response.setCode(ResponseCode.PRODUCT_ORDER_QUERY_ERROR);
        }
        return response;
    }

	/**
     * 一卡通消费业务订单汇总详细查询
     * @param query
     * @return
     */
	public DodopalDataPage<YktCardConsumTradeDetail> queryCardConsumCollectDetailsByPage(YktCardConsumTradeDetailQuery query) {
        List<YktCardConsumTradeDetail> list = supplierMapper.queryCardConsumCollectDetailsByPage(query);
        DodopalDataPage<YktCardConsumTradeDetail> pages = new DodopalDataPage<YktCardConsumTradeDetail>(query.getPage(), list);
        return pages;
    }
    
//    public DodopalDataPage<YktCardConsumTradeDetail> exportCardConsumDetailsForSupp(YktCardConsumTradeDetailQuery query) {
//        List<YktCardConsumTradeDetail> list = supplierMapper.queryCardConsumDetailsByPage(query);
//        DodopalDataPage<YktCardConsumTradeDetail> pages = new DodopalDataPage<YktCardConsumTradeDetail>(query.getPage(), list);
//        return pages;
//    }
}

