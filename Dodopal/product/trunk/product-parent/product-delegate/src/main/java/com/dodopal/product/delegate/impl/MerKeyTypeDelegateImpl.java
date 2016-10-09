package com.dodopal.product.delegate.impl;

import org.springframework.stereotype.Service;

import com.dodopal.api.users.dto.MerKeyTypeDTO;
import com.dodopal.api.users.facade.MerKeyTypeFacade;
import com.dodopal.common.enums.MerKeyTypeMD5PwdEnum;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.delegate.BaseDelegate;
import com.dodopal.product.delegate.MerKeyTypeDelegate;
import com.dodopal.product.delegate.constant.DelegateConstant;
@Service("merKeyTypeDelegate")
public class MerKeyTypeDelegateImpl extends BaseDelegate implements MerKeyTypeDelegate {

    @Override
    public DodopalResponse<MerKeyTypeDTO> findMerMD5PayPWDOrBackPayPWD(MerKeyTypeDTO merKeyTypeDTO, MerKeyTypeMD5PwdEnum keyTypeMD5PwdEnum) {
        MerKeyTypeFacade merFacade = getFacade(MerKeyTypeFacade.class, DelegateConstant.FACADE_USERS_URL);
        return merFacade.findMerMD5PayPWDOrBackPayPWD(merKeyTypeDTO,keyTypeMD5PwdEnum);
    }

}
