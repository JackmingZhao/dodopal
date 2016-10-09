package com.dodopal.oss.delegate.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dodopal.api.card.dto.PosSignInOutDTO;
import com.dodopal.api.card.facade.SignInSignOutCardFacade;
import com.dodopal.api.product.dto.YktPsamDTO;
import com.dodopal.api.product.dto.query.YktPsamQueryDTO;
import com.dodopal.api.product.facade.YktPsamFacade;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.delegate.BaseDelegate;
import com.dodopal.oss.delegate.PsamDelegate;
import com.dodopal.oss.delegate.constant.DelegateConstant;

@Service("psamDelegate")
public class PsamDelegateImpl extends BaseDelegate implements PsamDelegate {

    @Override
    public DodopalResponse<DodopalDataPage<YktPsamDTO>> findYktPsamByPage(YktPsamQueryDTO yktPsamQueryDTO) {
        YktPsamFacade facade = getFacade(YktPsamFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
        DodopalResponse<DodopalDataPage<YktPsamDTO>> response = facade.findYktPsamByPage(yktPsamQueryDTO);
        return response;
    }

    
    public DodopalResponse<String> addYktPsam(YktPsamDTO yktPsamDTO) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        YktPsamFacade facade = getFacade(YktPsamFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
        response = facade.addYktPsam(yktPsamDTO);
        return response;
    }

    public DodopalResponse<String> updateYktPsam(YktPsamDTO yktPsamDTO) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        YktPsamFacade facade = getFacade(YktPsamFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
        response = facade.updateYktPsam(yktPsamDTO);
        return response;
    }

    @Override
    public DodopalResponse<YktPsamDTO> findPsamById(String id) {
        DodopalResponse<YktPsamDTO> response = new DodopalResponse<YktPsamDTO>();
        YktPsamFacade facade = getFacade(YktPsamFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
        response = facade.findPsamById(id);
        return response;
    }


    @Override
    public DodopalResponse<String> batchDeleteYktPsamByIds(List<String> ids) {
        YktPsamFacade facade = getFacade(YktPsamFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
        return facade.batchDeleteYktPsamByIds(ids);
    }


    @Override
    public DodopalResponse<String> batchActivate(List<String> ids) {
        YktPsamFacade facade = getFacade(YktPsamFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
        return facade.batchActivateMerByIds(ids);
    }


    @Override
    public DodopalResponse<String> batchNeedDownLoad(List<String> ids) {
        YktPsamFacade facade = getFacade(YktPsamFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
        return facade.batchNeedDownLoadPsamByIds(ids);
    }


    @Override
    public DodopalResponse<PosSignInOutDTO> posSignInOutForV61(PosSignInOutDTO posSignInOutDTO) {
        SignInSignOutCardFacade facade= getFacade(SignInSignOutCardFacade.class, DelegateConstant.FACADE_CARD_URL);
        return facade.posSignInOutForV61(posSignInOutDTO);
    }

}
