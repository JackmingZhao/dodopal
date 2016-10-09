package com.dodopal.portal.delegate.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dodopal.api.product.dto.ChargeOrderDTO;
import com.dodopal.api.product.dto.ConsumptionOrderCountDTO;
import com.dodopal.api.product.dto.ConsumptionOrderDTO;
import com.dodopal.api.product.dto.query.ChargeOrderQueryDTO;
import com.dodopal.api.product.dto.query.ConsumptionOrderQueryDTO;
import com.dodopal.api.product.facade.MerJointQueryFacade;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.delegate.BaseDelegate;
import com.dodopal.portal.delegate.MerJointQueryDelegate;
import com.dodopal.portal.delegate.constant.DelegateConstant;
@Service("merJointQueryDelegate")
public class MerJointQueryDelegateImpl extends BaseDelegate implements MerJointQueryDelegate{
    

    @Override
    public DodopalResponse<DodopalDataPage<ChargeOrderDTO>> findChargeOrderByPage(ChargeOrderQueryDTO queryDTO) {
        MerJointQueryFacade facade = getFacade(MerJointQueryFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
        DodopalResponse<DodopalDataPage<ChargeOrderDTO>> response = facade.findChargeOrderByPage(queryDTO);
        return response;
    }

    @Override
    public DodopalResponse<DodopalDataPage<ConsumptionOrderDTO>> findConsumptionOrderByPage(ConsumptionOrderQueryDTO queryDTO) {
        MerJointQueryFacade facade = getFacade(MerJointQueryFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
        DodopalResponse<DodopalDataPage<ConsumptionOrderDTO>> response = facade.findConsumptionOrderByPage(queryDTO);
        return response;
    }

    @Override
    public DodopalResponse<DodopalDataPage<ConsumptionOrderCountDTO>> findConsumptionOrderCountByPage(ChargeOrderQueryDTO queryDTO) {
        MerJointQueryFacade facade = getFacade(MerJointQueryFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
        DodopalResponse<DodopalDataPage<ConsumptionOrderCountDTO>> response = facade.findConsumptionOrderCountByPage(queryDTO);
        return response;
    }

    @Override
    public DodopalResponse<ConsumptionOrderCountDTO> findConsumptionOrderCount(ChargeOrderQueryDTO queryDTO) {
        MerJointQueryFacade facade = getFacade(MerJointQueryFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
        DodopalResponse<ConsumptionOrderCountDTO> response = facade.findConsumptionOrderCount(queryDTO);
        return response;
    }

    @Override
    public DodopalResponse<List<ChargeOrderDTO>> exportChargeOrder(ChargeOrderQueryDTO queryDTO) {
        MerJointQueryFacade facade = getFacade(MerJointQueryFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
        DodopalResponse<List<ChargeOrderDTO>> response = facade.exportChargeOrder(queryDTO);
        return response;
    }

    @Override
    public DodopalResponse<List<ConsumptionOrderDTO>> exportConsumptionOrder(ConsumptionOrderQueryDTO queryDTO) {
        MerJointQueryFacade facade = getFacade(MerJointQueryFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
        DodopalResponse<List<ConsumptionOrderDTO>> response = facade.exportConsumptionOrder(queryDTO);
        return response;
    }

    @Override
    public DodopalResponse<List<ConsumptionOrderCountDTO>> exportConsumptionOrderCount(ChargeOrderQueryDTO queryDTO) {
        MerJointQueryFacade facade = getFacade(MerJointQueryFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
        DodopalResponse<List<ConsumptionOrderCountDTO>> response = facade.exportConsumptionOrderCount(queryDTO);
        return response;
    }

}
