package com.dodopal.oss.delegate.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dodopal.api.card.dto.CrdSysOrderTDTO;
import com.dodopal.api.card.dto.query.CrdSysOrderQueryDTO;
import com.dodopal.api.card.facade.QueryCrdSysOrderFacade;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.delegate.BaseDelegate;
import com.dodopal.oss.delegate.CrdSysOrderDelegate;
import com.dodopal.oss.delegate.constant.DelegateConstant;
@Service("CrdSysOrderDelegate")
public class CrdSysOrderDelegateImpl extends BaseDelegate implements CrdSysOrderDelegate {
    @Override
    public DodopalResponse<DodopalDataPage<CrdSysOrderTDTO>> findCrdSysOrderByPage(CrdSysOrderQueryDTO crdSysOrderQueryDTO) {
        QueryCrdSysOrderFacade facade =getFacade(QueryCrdSysOrderFacade.class,DelegateConstant.FACADE_CARD_URL);
        return facade.findCrdSysOrderByPage(crdSysOrderQueryDTO);
    }

    @Override
    public DodopalResponse<CrdSysOrderTDTO> findCrdSysOrderByCode(String crdOrderNum) {
        QueryCrdSysOrderFacade facade =getFacade(QueryCrdSysOrderFacade.class,DelegateConstant.FACADE_CARD_URL);
        return facade.findCrdSysOrderByCode(crdOrderNum);
    }

    @Override
    public DodopalResponse<List<CrdSysOrderTDTO>> findCrdSysOrderAll(CrdSysOrderQueryDTO crdSysOrderQueryDTO) {
        QueryCrdSysOrderFacade facade =getFacade(QueryCrdSysOrderFacade.class,DelegateConstant.FACADE_CARD_URL);
        return facade.findCrdSysOrderAll(crdSysOrderQueryDTO);
    }

}
