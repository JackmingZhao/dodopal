package com.dodopal.product.delegate.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dodopal.api.users.dto.MerUserCardBDDTO;
import com.dodopal.api.users.dto.MerUserCardBDFindDTO;
import com.dodopal.api.users.facade.MerUserCardBDFacade;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.delegate.BaseDelegate;
import com.dodopal.product.delegate.CardDelegate;
import com.dodopal.product.delegate.constant.DelegateConstant;

@Service("cardDelegate")
public class CardDelegateImpl extends BaseDelegate implements CardDelegate {

    //卡片绑定
    public DodopalResponse<MerUserCardBDDTO> saveMerUserCardBD(MerUserCardBDDTO cardBDDTO) {
        MerUserCardBDFacade facade = getFacade(MerUserCardBDFacade.class, DelegateConstant.FACADE_USERS_URL);
        DodopalResponse<MerUserCardBDDTO> response = facade.saveMerUserCardBD(cardBDDTO);
        return response;
    }

    //卡片解绑
    public DodopalResponse<String> unBundlingCard(String userId, String operName, List<String> ids, String source) {
        MerUserCardBDFacade facade = getFacade(MerUserCardBDFacade.class, DelegateConstant.FACADE_USERS_URL);
        DodopalResponse<String> response = facade.unBundlingCard(userId, operName, ids, source);
        return response;
    }

    //用户卡片列表
    public DodopalResponse<List<MerUserCardBDDTO>> findMerUserCardBD(MerUserCardBDFindDTO cardBDFindDTO) {
        MerUserCardBDFacade facade = getFacade(MerUserCardBDFacade.class, DelegateConstant.FACADE_USERS_URL);
        DodopalResponse<List<MerUserCardBDDTO>> response = facade.findMerUserCardBD(cardBDFindDTO);
        return response;
    }



}
