package com.dodopal.oss.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianRuntimeException;
import com.dodopal.api.payment.dto.PayTraTransactionDTO;
import com.dodopal.api.payment.dto.query.PayTraTransactionQueryDTO;
import com.dodopal.api.product.dto.LoadOrderDTO;
import com.dodopal.api.product.dto.LoadOrderRequestDTO;
import com.dodopal.api.product.dto.query.LoadOrderQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.LoadOrderStatusEnum;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.oss.business.bean.LoadOrderBean;
import com.dodopal.oss.business.bean.TraTransactionBean;
import com.dodopal.oss.business.service.LoadOrderService;
import com.dodopal.oss.delegate.LoadOrderDelegate;

@Service
public class LoadOrderServiceImpl implements LoadOrderService {
    private final static Logger log = LoggerFactory.getLogger(LoadOrderServiceImpl.class);
    @Autowired
    LoadOrderDelegate  loadOrderDelegate;
    
    
    @Override
    public DodopalResponse<DodopalDataPage<LoadOrderBean>> findLoadOrders(LoadOrderQueryDTO queryDto) {
        DodopalResponse<DodopalDataPage<LoadOrderBean>> dodopalResponse = new DodopalResponse<DodopalDataPage<LoadOrderBean>>();
        try{
            DodopalResponse<DodopalDataPage<LoadOrderDTO>> getResponse = loadOrderDelegate.findLoadOrders(queryDto);
            if (ResponseCode.SUCCESS.equals(getResponse.getCode())) {
                List<LoadOrderBean> resResult = new ArrayList<LoadOrderBean>();
                LoadOrderBean orderBean = null;
                for (LoadOrderDTO retDTO : getResponse.getResponseEntity().getRecords()) {
                    orderBean = new LoadOrderBean();
                    PropertyUtils.copyProperties(orderBean, retDTO);
                    resResult.add(orderBean);
                }
                PageParameter page = DodopalDataPageUtil.convertPageInfo(getResponse.getResponseEntity());
                DodopalDataPage<LoadOrderBean> pages = new DodopalDataPage<LoadOrderBean>(page, resResult);
                dodopalResponse.setCode(getResponse.getCode());
                dodopalResponse.setResponseEntity(pages);
            } else {
                dodopalResponse.setCode(getResponse.getCode());
            }
        }catch(HessianRuntimeException e){
            log.debug("LoadOrderServiceImpl findLoadOrders call error", e);
            e.printStackTrace();
            dodopalResponse.setCode(ResponseCode.HESSIAN_ERROR);
        }catch (Exception e) {
            log.debug("LoadOrderServiceImpl findLoadOrders call error", e);
            e.printStackTrace();
            dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
            // throw new DDPException(ResponseCode.SYSTEM_ERROR);
        }
        return dodopalResponse;
    }
    

    @Override
    public DodopalResponse<LoadOrderBean> findLoadOrderByOrderNum(String orderNum) {
        DodopalResponse<LoadOrderBean> dodopalResponse = new DodopalResponse<LoadOrderBean>();
        try{
            DodopalResponse<LoadOrderDTO> getResponse = loadOrderDelegate.findLoadOrderByOrderNum(orderNum);
            LoadOrderBean orderBean = new LoadOrderBean();
            if (ResponseCode.SUCCESS.equals(getResponse.getCode())) {
                PropertyUtils.copyProperties(orderBean,  getResponse.getResponseEntity());
                dodopalResponse.setCode(getResponse.getCode());
                dodopalResponse.setResponseEntity(orderBean);
            }else {
                dodopalResponse.setCode(getResponse.getCode());
            }
        }catch(HessianRuntimeException e){
            log.debug("LoadOrderServiceImpl findLoadOrderByOrderNum call error", e);
            e.printStackTrace();
            dodopalResponse.setCode(ResponseCode.HESSIAN_ERROR);
        }catch (Exception e) {
            log.debug("LoadOrderServiceImpl findLoadOrderByOrderNum call error", e);
            e.printStackTrace();
            dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return dodopalResponse;
        
    }

    //////////////////////////////////以下是测试代码////////////////////////////
    /**
     * 测试圈存订单
     */
    @Override
    public DodopalResponse<String> bookLoadOrder(LoadOrderRequestDTO loadOrderRequestDTO) {
        return loadOrderDelegate.bookLoadOrder(loadOrderRequestDTO);
    }

    @Override
    public DodopalResponse<String> findLoadOrder() {
        return loadOrderDelegate.findLoadOrder();
    }

    @Override
    public DodopalResponse<String> findLoadOrderStatus(LoadOrderRequestDTO loadOrderRequestDTO) {
        return loadOrderDelegate.findLoadOrderStatus(loadOrderRequestDTO);
    }


    @Override
    public DodopalResponse<List<LoadOrderBean>> findLoadOrdersExport(LoadOrderQueryDTO queryDto) {
        
        DodopalResponse<List<LoadOrderBean>> response = new DodopalResponse<List<LoadOrderBean>>();
        try {
            DodopalResponse<List<LoadOrderDTO>> loadOrderDTOList =  loadOrderDelegate.findLoadOrdersExport(queryDto);

            if (ResponseCode.SUCCESS.equals(loadOrderDTOList.getCode())) { 

                List<LoadOrderBean> loadOrderBeanList = new ArrayList<LoadOrderBean>();

                if (CollectionUtils.isNotEmpty(loadOrderDTOList.getResponseEntity())) {
                    for (LoadOrderDTO orderDto : loadOrderDTOList.getResponseEntity()) {
                        LoadOrderBean orderBean = new LoadOrderBean();
                        PropertyUtils.copyProperties(orderBean, orderDto);
                        orderBean.setLoadAmt((double)orderDto.getLoadAmt()/100);
                        orderBean.setStatus(LoadOrderStatusEnum.getLoadOrderStatusByCode(orderDto.getStatus()).getName());
                        loadOrderBeanList.add(orderBean);
                    }
                }
                response.setResponseEntity(loadOrderBeanList);
            }
            response.setCode(loadOrderDTOList.getCode());
        }catch (HessianRuntimeException e) {
            log.debug("findLoadOrdersExport call HessianRuntimeException error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            log.debug("findLoadOrdersExport call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

   
}
