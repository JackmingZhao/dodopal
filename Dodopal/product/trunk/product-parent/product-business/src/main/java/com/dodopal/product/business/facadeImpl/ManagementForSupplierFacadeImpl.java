package com.dodopal.product.business.facadeImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.product.dto.ProductOrderDTO;
import com.dodopal.api.product.dto.RechargeStatisticsYktOrderDTO;
import com.dodopal.api.product.dto.YktCardConsumStatisticsDTO;
import com.dodopal.api.product.dto.YktCardConsumTradeDetailDTO;
import com.dodopal.api.product.dto.query.ProductOrderQueryDTO;
import com.dodopal.api.product.dto.query.RechargeStatisticsYktQueryDTO;
import com.dodopal.api.product.dto.query.YktCardConsumTradeDetailQueryDTO;
import com.dodopal.api.product.facade.ManagementForSupplierFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.product.business.model.ProductOrder;
import com.dodopal.product.business.model.YktCardConsumTradeDetail;
import com.dodopal.product.business.model.query.ProductOrderQuery;
import com.dodopal.product.business.model.query.YktCardConsumTradeDetailQuery;
import com.dodopal.product.business.service.ManagementForSupplierService;

/**
 * 供应商(通卡)查询统计
 * @author hxc
 *
 */
@Service("managementForSupplierFacade")
public class ManagementForSupplierFacadeImpl implements ManagementForSupplierFacade{

	private Logger logger = LoggerFactory.getLogger(ManagementForSupplierFacadeImpl.class);
	
	@Autowired
	private ManagementForSupplierService supplierService;
	
	/**
	 * 一卡通充值统计查询
	 * @param query
	 * @return
	 */
	public DodopalResponse<DodopalDataPage<RechargeStatisticsYktOrderDTO>> queryCardRechargeForSupplier(
			RechargeStatisticsYktQueryDTO query) {
		DodopalResponse<DodopalDataPage<RechargeStatisticsYktOrderDTO>> response = supplierService.queryCardRechargeForSupplier(query);
		return response;
	}

	/**
	 * 一卡通充值交易详细查询
	 * @param queryDTO
	 * @return
	 */
	public DodopalResponse<DodopalDataPage<ProductOrderDTO>> queryCardRechargeDetails(ProductOrderQueryDTO queryDTO) {
		DodopalResponse<DodopalDataPage<ProductOrderDTO>> response = new DodopalResponse<DodopalDataPage<ProductOrderDTO>>();
		ProductOrderQuery query = new ProductOrderQuery();
		try {
			PropertyUtils.copyProperties(query, queryDTO);
			List<ProductOrderDTO> orderList = new ArrayList<ProductOrderDTO>();
			DodopalDataPage<ProductOrder> rechargeDetails = supplierService.queryCardRechargeDetails(query);
			List<ProductOrder> list = rechargeDetails.getRecords();
			if(CollectionUtils.isNotEmpty(list)){
				for(ProductOrder order : list){
					ProductOrderDTO orderDTO = new ProductOrderDTO();
					PropertyUtils.copyProperties(orderDTO, order);
					orderList.add(orderDTO);
				}
			}
			PageParameter page = DodopalDataPageUtil.convertPageInfo(rechargeDetails);
            DodopalDataPage<ProductOrderDTO> pages = new DodopalDataPage<ProductOrderDTO>(page, orderList);
			response.setCode(ResponseCode.SUCCESS);
			response.setResponseEntity(pages);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("产品库中一卡通充值交易详细查询出错", e);
            response.setCode(ResponseCode.PRODUCT_ORDER_QUERY_ERROR);
		}
		return response;
	}
	
	 /**
     * 一卡通消费统计查询
     * @param query
     * @return
     */
    public DodopalResponse<DodopalDataPage<YktCardConsumStatisticsDTO>> queryCardConsumForSupplier(RechargeStatisticsYktQueryDTO query) {
        DodopalResponse<DodopalDataPage<YktCardConsumStatisticsDTO>> response = supplierService.queryCardConsumForSupplier(query);
        return response;
    }
    
    public DodopalResponse<DodopalDataPage<YktCardConsumStatisticsDTO>> exportCardConsumForSupp(RechargeStatisticsYktQueryDTO query) {
        DodopalResponse<DodopalDataPage<YktCardConsumStatisticsDTO>> response = supplierService.exportCardConsumForSupp(query);
        return response;
    }

    /**
     * 一卡通消费交易详细查询
     * @param queryDTO
     * @return
     */
    public DodopalResponse<DodopalDataPage<YktCardConsumTradeDetailDTO>> queryCardConsumDetails(YktCardConsumTradeDetailQueryDTO queryDTO) {

        DodopalResponse<DodopalDataPage<YktCardConsumTradeDetailDTO>> response = new DodopalResponse<DodopalDataPage<YktCardConsumTradeDetailDTO>>();
        YktCardConsumTradeDetailQuery query = new YktCardConsumTradeDetailQuery();
        try {
            PropertyUtils.copyProperties(query, queryDTO);
            List<YktCardConsumTradeDetailDTO> orderList = new ArrayList<YktCardConsumTradeDetailDTO>();
            DodopalDataPage<YktCardConsumTradeDetail> cardConsumDetails = supplierService.queryCardConsumDetails(query);
            List<YktCardConsumTradeDetail> list = cardConsumDetails.getRecords();
            if(CollectionUtils.isNotEmpty(list)){
                for(YktCardConsumTradeDetail order : list){
                    YktCardConsumTradeDetailDTO orderDTO = new YktCardConsumTradeDetailDTO();
                    PropertyUtils.copyProperties(orderDTO, order);
                    orderList.add(orderDTO);
                }
            }
            PageParameter page = DodopalDataPageUtil.convertPageInfo(cardConsumDetails);
            DodopalDataPage<YktCardConsumTradeDetailDTO> pages = new DodopalDataPage<YktCardConsumTradeDetailDTO>(page, orderList);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(pages);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("产品库中一卡通消费交易详细查询出错", e);
            response.setCode(ResponseCode.PRODUCT_ORDER_QUERY_ERROR);
        }
        return response;

    }
//    public DodopalResponse<DodopalDataPage<YktCardConsumTradeDetailDTO>> exportCardConsumDetailsForSupp(YktCardConsumTradeDetailQueryDTO queryDTO) {
//
//        DodopalResponse<DodopalDataPage<YktCardConsumTradeDetailDTO>> response = new DodopalResponse<DodopalDataPage<YktCardConsumTradeDetailDTO>>();
//        YktCardConsumTradeDetailQuery query = new YktCardConsumTradeDetailQuery();
//        try {
//            PropertyUtils.copyProperties(query, queryDTO);
//            List<YktCardConsumTradeDetailDTO> orderList = new ArrayList<YktCardConsumTradeDetailDTO>();
//            DodopalDataPage<YktCardConsumTradeDetail> cardConsumDetails = supplierService.exportCardConsumDetailsForSupp(query);
//            List<YktCardConsumTradeDetail> list = cardConsumDetails.getRecords();
//            if(CollectionUtils.isNotEmpty(list)){
//                for(YktCardConsumTradeDetail order : list){
//                    YktCardConsumTradeDetailDTO orderDTO = new YktCardConsumTradeDetailDTO();
//                    PropertyUtils.copyProperties(orderDTO, order);
//                    orderList.add(orderDTO);
//                }
//            }
//            PageParameter page = DodopalDataPageUtil.convertPageInfo(cardConsumDetails);
//            DodopalDataPage<YktCardConsumTradeDetailDTO> pages = new DodopalDataPage<YktCardConsumTradeDetailDTO>(page, orderList);
//            response.setCode(ResponseCode.SUCCESS);
//            response.setResponseEntity(pages);
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error("产品库中一卡通消费交易详细导出出错", e);
//            response.setCode(ResponseCode.PRODUCT_ORDER_QUERY_ERROR);
//        }
//        return response;
//
//    }
    

	/**
	 * 启用停用
	 * @param bind
	 * @param codes
	 * @return
	 */
	public DodopalResponse<Integer> startOrStopMerSupplier(String bind,List<String> codes) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        int num = 0;
        try {
            if (bind == null) {
                response.setCode(ResponseCode.ACTIVATE_ERROR);
                return response;
            }
            if (CollectionUtils.isEmpty(codes)) {
                response.setResponseEntity(num);
                return response;
            }
            num = supplierService.startOrStopMerSupplier(bind, codes);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(num);
        }
        catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

	  /**
     * 一卡通消费业务订单汇总
     * @param query
     * @return
     */
	public DodopalResponse<DodopalDataPage<YktCardConsumStatisticsDTO>> findCardConsumCollectByPage(RechargeStatisticsYktQueryDTO query) {
		DodopalResponse<DodopalDataPage<YktCardConsumStatisticsDTO>> response = supplierService.findCardConsumCollectByPage(query);
		return response;
	}

	/**
     * 一卡通消费业务订单汇总详细查询
     * @param queryDTO
     * @return
     */
	public DodopalResponse<DodopalDataPage<YktCardConsumTradeDetailDTO>> queryCardConsumCollectDetailsByPage(YktCardConsumTradeDetailQueryDTO queryDTO) {

        DodopalResponse<DodopalDataPage<YktCardConsumTradeDetailDTO>> response = new DodopalResponse<DodopalDataPage<YktCardConsumTradeDetailDTO>>();
        YktCardConsumTradeDetailQuery query = new YktCardConsumTradeDetailQuery();
        try {
            PropertyUtils.copyProperties(query, queryDTO);
            List<YktCardConsumTradeDetailDTO> orderList = new ArrayList<YktCardConsumTradeDetailDTO>();
            DodopalDataPage<YktCardConsumTradeDetail> cardConsumDetails = supplierService.queryCardConsumCollectDetailsByPage(query);
            List<YktCardConsumTradeDetail> list = cardConsumDetails.getRecords();
            if(CollectionUtils.isNotEmpty(list)){
                for(YktCardConsumTradeDetail order : list){
                    YktCardConsumTradeDetailDTO orderDTO = new YktCardConsumTradeDetailDTO();
                    PropertyUtils.copyProperties(orderDTO, order);
                    orderList.add(orderDTO);
                }
            }
            PageParameter page = DodopalDataPageUtil.convertPageInfo(cardConsumDetails);
            DodopalDataPage<YktCardConsumTradeDetailDTO> pages = new DodopalDataPage<YktCardConsumTradeDetailDTO>(page, orderList);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(pages);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("产品库中一卡通消费交易详细查询出错", e);
            response.setCode(ResponseCode.PRODUCT_ORDER_QUERY_ERROR);
        }
        return response;

    }


}
