package com.dodopal.product.business.facadeImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.product.dto.IcdcLoadOrderRequestDTO;
import com.dodopal.api.product.dto.LoadOrderDTO;
import com.dodopal.api.product.dto.LoadOrderForPaySysDTO;
import com.dodopal.api.product.dto.query.LoadOrderQueryDTO;
import com.dodopal.api.product.facade.LoadOrderFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.enums.LoadOrderStatusEnum;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.enums.OpenSignEnum;
import com.dodopal.common.enums.PayTypeEnum;
import com.dodopal.common.enums.ProductStatusEnum;
import com.dodopal.common.enums.ServiceRateTypeEnum;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.Area;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.product.business.model.LoadOrder;
import com.dodopal.product.business.model.PrdProductYktInfoForIcdcRecharge;
import com.dodopal.product.business.model.ProductYKT;
import com.dodopal.product.business.service.LoadOrderService;

@Service("loadOrderFacade")
public class LoadOrderFacadeImpl implements LoadOrderFacade {
    private static Logger logger = Logger.getLogger(LoadOrderFacadeImpl.class);
    @Autowired
    private LoadOrderService loadOrderService;
    

    /**
     * 6.4  查询公交卡充值圈存订单
     */
    @Override
    public DodopalResponse<DodopalDataPage<LoadOrderDTO>> findLoadOrders(LoadOrderQueryDTO queryDto) {
        DodopalResponse<DodopalDataPage<LoadOrderDTO>> response = null;
        try {
            if (queryDto == null) {
                queryDto = new LoadOrderQueryDTO();
            }
            if (queryDto.getPage() == null) {
                queryDto.setPage(new PageParameter());
            }
            response = loadOrderService.findLoadOrdersByPage(queryDto);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            response = new DodopalResponse<DodopalDataPage<LoadOrderDTO>>();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }


    @Override
    public DodopalResponse<LoadOrderDTO> findLoadOrderByOrderNum(String orderNum) {
        DodopalResponse<LoadOrderDTO> dodopalResponse = new DodopalResponse<LoadOrderDTO>();
        try{
            if(StringUtils.isBlank(orderNum)){
                logger.info("查询圈存订单详情时查询参数订单号为空");
                dodopalResponse.setCode(ResponseCode.PRODUCT_LOADORDER_REQUEST_ERROR);
                return dodopalResponse;
            }
            LoadOrder loadOrder = loadOrderService.viewLoadOrderByLoadOrderNum(orderNum);
            LoadOrderDTO orderDTO = new LoadOrderDTO();
            PropertyUtils.copyProperties(orderDTO, loadOrder);
            dodopalResponse.setResponseEntity(orderDTO);
            dodopalResponse.setCode(ResponseCode.SUCCESS);
        } catch(Exception e){
            e.printStackTrace();
            dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
            logger.error("findLoadOrderByOrderNum call an error",e);
        }
        return dodopalResponse;
    }


    @Override
    public DodopalResponse<List<LoadOrderDTO>> findLoadOrdersExport(LoadOrderQueryDTO queryDto) {
        DodopalResponse<List<LoadOrderDTO>> response = null;
        try {
            if (queryDto == null) {
                queryDto = new LoadOrderQueryDTO();
            }
            if (queryDto.getPage() == null) {
                queryDto.setPage(new PageParameter());
            }
            List<LoadOrder> orderList = loadOrderService.findLoadOrdersExport(queryDto);
            List<LoadOrderDTO> dtoList = new ArrayList<LoadOrderDTO>();
            for(LoadOrder order:orderList){
                LoadOrderDTO dto = new LoadOrderDTO();
                PropertyUtils.copyProperties(dto, order);
                dtoList.add(dto);
            }
            response = new DodopalResponse<List<LoadOrderDTO>>();
            response.setResponseEntity(dtoList);
            response.setCode(ResponseCode.SUCCESS);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            response = new DodopalResponse<List<LoadOrderDTO>>();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }


    @Override
    public DodopalResponse<LoadOrderDTO> bookLoadOrderForPaySys(LoadOrderForPaySysDTO orderDto) {
        DodopalResponse<LoadOrderDTO> response = new DodopalResponse<LoadOrderDTO>();
        IcdcLoadOrderRequestDTO requestDto = new IcdcLoadOrderRequestDTO();
        try{
            requestDto.setCardNum(orderDto.getCardNum());
            requestDto.setProductNum(orderDto.getProductNum());
            requestDto.setCustomerCode(orderDto.getCustomerNum());
            requestDto.setSource(orderDto.getSource());
            DodopalResponse<LoadOrder> dodopalResult = loadOrderService.bookLoadOrderForPaySys(requestDto);
            if(dodopalResult.isSuccessCode()){
                if(null!=dodopalResult.getResponseEntity()){
                    LoadOrderDTO loadOrderDTO = new LoadOrderDTO();
                    PropertyUtils.copyProperties(loadOrderDTO, dodopalResult.getResponseEntity());
                    response.setResponseEntity(loadOrderDTO);
                }
            }
            response.setCode(dodopalResult.getCode());
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }
    
    
}
