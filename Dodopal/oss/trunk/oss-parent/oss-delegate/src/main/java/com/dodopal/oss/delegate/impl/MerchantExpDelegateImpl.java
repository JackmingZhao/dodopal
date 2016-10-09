package com.dodopal.oss.delegate.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dodopal.api.users.dto.MerchantDTO;
import com.dodopal.api.users.dto.MerchantQueryDTO;
import com.dodopal.api.users.facade.MerchantFacade;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.delegate.BaseDelegate;
import com.dodopal.oss.delegate.MerchantExpDelegate;
import com.dodopal.oss.delegate.constant.DelegateConstant;

@Service("merchantExpDelegate")
public class MerchantExpDelegateImpl extends BaseDelegate implements MerchantExpDelegate  {

    @Override
    public DodopalResponse<List<MerchantDTO>> getExportMerchantList(MerchantQueryDTO merchantQueryDTO) {
        MerchantFacade facade = getFacade(MerchantFacade.class, DelegateConstant.FACADE_USERS_URL);
        DodopalResponse<List<MerchantDTO>> response = facade.findMerchantByPageList(merchantQueryDTO);
        return response;
    }

    @Override
    public DodopalResponse<Integer> getExpMerchantCount(MerchantQueryDTO merchantQueryDTO) {
        MerchantFacade facade = getFacade(MerchantFacade.class, DelegateConstant.FACADE_USERS_URL);
        DodopalResponse<Integer> response = facade.findMerchantByPageCount(merchantQueryDTO);
        return response;
    }

}
