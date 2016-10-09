package com.dodopal.portal.delegate.impl;

import org.springframework.stereotype.Service;

import com.dodopal.api.product.dto.ProxyCardAddDTO;
import com.dodopal.api.product.dto.ProxyCountAllByIDDTO;
import com.dodopal.api.product.dto.ProxyOffLineOrderTbDTO;
import com.dodopal.api.product.dto.query.ProxyCardAddQueryDTO;
import com.dodopal.api.product.dto.query.ProxyOffLineOrderTbQueryDTO;
import com.dodopal.api.product.facade.ProxyCardAddQueryFacade;
import com.dodopal.api.users.dto.ProfitDetailsDTO;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.delegate.BaseDelegate;
import com.dodopal.portal.delegate.ProxyCardAddDelegate;
import com.dodopal.portal.delegate.constant.DelegateConstant;

@Service("proxyCardAddDelegate")
public class ProxyCardAddDelegateImpl extends BaseDelegate implements ProxyCardAddDelegate {

    /**
     * 查询城市一卡通充值记录 
     */
    @Override
    public DodopalResponse<DodopalDataPage<ProxyCardAddDTO>> cardTradeList(ProxyCardAddQueryDTO proxyCardAddQueryDTO) {
        ProxyCardAddQueryFacade facade = getFacade(ProxyCardAddQueryFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
        DodopalResponse<DodopalDataPage<ProxyCardAddDTO>> response = facade.cardTradeList(proxyCardAddQueryDTO);
        return response;
    }

    /**
     * 查询城市一卡通充值记录 统计
     */
    @Override
    public DodopalResponse<ProxyCountAllByIDDTO> findCardTradeListCount(ProxyCardAddQueryDTO proxyCardAddQueryDTO) {
        ProxyCardAddQueryFacade facade = getFacade(ProxyCardAddQueryFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
        DodopalResponse<ProxyCountAllByIDDTO> response = facade.findCardTradeListCount(proxyCardAddQueryDTO);
        return response;
    }

    /**
     * 查询城市一卡通消费记录
     */
    @Override
    public DodopalResponse<DodopalDataPage<ProxyOffLineOrderTbDTO>> offLineTradeList(ProxyOffLineOrderTbQueryDTO proxyOffLineOrderTbQueryDTO) {
        ProxyCardAddQueryFacade facade = getFacade(ProxyCardAddQueryFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
        DodopalResponse<DodopalDataPage<ProxyOffLineOrderTbDTO>> response = facade.offLineTradeList(proxyOffLineOrderTbQueryDTO);
        return response;
    }

    @Override
    public DodopalResponse<ProxyCountAllByIDDTO> findoffLineTradeListCount(ProxyOffLineOrderTbQueryDTO proxyOffLineOrderTbQueryDTO) {
        ProxyCardAddQueryFacade facade = getFacade(ProxyCardAddQueryFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
        DodopalResponse<ProxyCountAllByIDDTO> response = facade.findoffLineTradeListCount(proxyOffLineOrderTbQueryDTO);
        return response;
    }

}
