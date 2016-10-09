package com.dodopal.portal.delegate.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dodopal.api.product.dto.CcOrderFormDTO;
import com.dodopal.api.product.dto.CzOrderByPosCountDTO;
import com.dodopal.api.product.dto.CzOrderByPosDTO;
import com.dodopal.api.product.dto.query.QueryCcOrderFormDTO;
import com.dodopal.api.product.dto.query.QueryCzOrderByPosCountDTO;
import com.dodopal.api.product.facade.CcOrderFacade;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.delegate.BaseDelegate;
import com.dodopal.portal.delegate.CcOrderDelegate;
import com.dodopal.portal.delegate.constant.DelegateConstant;
@Service("ccOrderDelegate")
public class CcOrderDelegateImpl extends BaseDelegate implements CcOrderDelegate {

    @Override
    public DodopalResponse<DodopalDataPage<CcOrderFormDTO>> findCcOrderListByPage(QueryCcOrderFormDTO queryCcOrderFormDTO) {
       CcOrderFacade facade = getFacade(CcOrderFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
       return facade.findCcOrderFormByPage(queryCcOrderFormDTO);
    }


    @Override
    public DodopalResponse<DodopalDataPage<CzOrderByPosDTO>> findCzOrderByPosByPage(QueryCzOrderByPosCountDTO queryCzOrderByPosCountDto) {
        CcOrderFacade facade = getFacade(CcOrderFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
        return facade.findCzOrderByPosCountByPage(queryCzOrderByPosCountDto);
    }

    @Override
    public DodopalResponse<CzOrderByPosCountDTO> findCzOrderByPosCount(QueryCzOrderByPosCountDTO queryCzOrderByPosCountDto) {
        CcOrderFacade facade = getFacade(CcOrderFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
        return facade.findCzOrderByPosCountAll(queryCzOrderByPosCountDto);
    }


    @Override
    public DodopalResponse<List<CzOrderByPosDTO>> findCzOrderByPosExcel(QueryCzOrderByPosCountDTO queryCzOrderByPosCountDto) {
        CcOrderFacade facade = getFacade(CcOrderFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
        return facade.findCzOrderByPosCountExcel(queryCzOrderByPosCountDto);
    }


    @Override
    public DodopalResponse<List<CcOrderFormDTO>> findCcOrderListExcel(QueryCcOrderFormDTO queryCcOrderFormDTO) {
        CcOrderFacade facade = getFacade(CcOrderFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
        return facade.findCcOrderFormExcel(queryCcOrderFormDTO);
    }

}
