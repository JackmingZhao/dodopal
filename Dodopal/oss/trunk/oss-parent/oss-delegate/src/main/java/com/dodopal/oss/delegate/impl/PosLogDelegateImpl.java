package com.dodopal.oss.delegate.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dodopal.api.users.dto.PosLogDTO;
import com.dodopal.api.users.dto.PosLogQueryDTO;
import com.dodopal.api.users.facade.PosLogFacade;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.delegate.BaseDelegate;
import com.dodopal.oss.delegate.PosLogDelegate;
import com.dodopal.oss.delegate.constant.DelegateConstant;

@Service("posLogDelegate")
public class PosLogDelegateImpl extends BaseDelegate implements PosLogDelegate {

    @Override
    public DodopalResponse<DodopalDataPage<PosLogDTO>> findPogLogsByPage(PosLogQueryDTO dto) {
        PosLogFacade facade =getFacade(PosLogFacade.class,DelegateConstant.FACADE_USERS_URL);
        return facade.findPosLogList(dto);
    }

    @Override
    public DodopalResponse<List<PosLogDTO>> findPogLogsByList(PosLogQueryDTO findDto) {
        PosLogFacade facade =getFacade(PosLogFacade.class,DelegateConstant.FACADE_USERS_URL);
        return facade.findPogLogsByList(findDto);
    }
}
