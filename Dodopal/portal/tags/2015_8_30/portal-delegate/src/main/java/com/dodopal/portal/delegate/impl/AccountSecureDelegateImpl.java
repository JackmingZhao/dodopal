package com.dodopal.portal.delegate.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.dodopal.api.users.dto.MerKeyTypeDTO;
import com.dodopal.api.users.dto.MerchantUserDTO;
import com.dodopal.api.users.facade.MerKeyTypeFacade;
import com.dodopal.api.users.facade.MerchantUserFacade;
import com.dodopal.api.users.facade.SendFacade;
import com.dodopal.common.enums.MerKeyTypeMD5PwdEnum;
import com.dodopal.common.enums.MoblieCodeTypeEnum;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.delegate.AccountSecureDelegate;
import com.dodopal.portal.delegate.BaseDelegate;
import com.dodopal.portal.delegate.constant.DelegateConstant;
@Service("accountSecureDelegate")
public class AccountSecureDelegateImpl extends BaseDelegate implements AccountSecureDelegate {

    @Override
    public DodopalResponse<Map<String, String>> send(String moblieNum, MoblieCodeTypeEnum codeType) {
        SendFacade facade = getFacade(SendFacade.class, DelegateConstant.FACADE_USERS_URL);
        DodopalResponse<Map<String, String>> response = facade.send(moblieNum, codeType);
        return response;
    }

    @Override
    public DodopalResponse<Map<String, String>> sendNoCheck(String moblieNum) {
        SendFacade facade = getFacade(SendFacade.class, DelegateConstant.FACADE_USERS_URL);
        DodopalResponse<Map<String, String>> response = facade.sendNoCheck(moblieNum);
        return response;
    }

    @Override
    public DodopalResponse<String> moblieCodeCheck(String moblieNum, String dypwd, String serialNumber) {
        SendFacade facade = getFacade(SendFacade.class, DelegateConstant.FACADE_USERS_URL);
        DodopalResponse<String> response = facade.moblieCodeCheck(moblieNum, dypwd, serialNumber);
        return response;
    }

    @Override
    public DodopalResponse<Boolean> saveMerUserMoblie(MerchantUserDTO merchantUserDTO) {
        MerchantUserFacade facade = getFacade(MerchantUserFacade.class, DelegateConstant.FACADE_USERS_URL);
        DodopalResponse<Boolean> response = facade.updateMerUserMobile(merchantUserDTO.getMerUserMobile(), merchantUserDTO.getId());
        return response;
    }

    @Override
    public DodopalResponse<Boolean> validateMerUserPWD(String id, String merUserPWD) {
        MerchantUserFacade facade = getFacade(MerchantUserFacade.class, DelegateConstant.FACADE_USERS_URL);
        DodopalResponse<Boolean> response = facade.validateMerUserPWD(merUserPWD, id);
        return response;
    }

    @Override
    public DodopalResponse<Boolean> updateMerUserPWDById(String id, String merUserPWD, String newMerUserPWD) {
        MerchantUserFacade facade = getFacade(MerchantUserFacade.class, DelegateConstant.FACADE_USERS_URL);
        DodopalResponse<Boolean> response = facade.updateMerUserPWDById(merUserPWD, newMerUserPWD, id);
        return response;
    }

    @Override
    public DodopalResponse<MerKeyTypeDTO> findMerMD5PayPWDOrBackPayPWD(MerKeyTypeDTO merKeyTypeDTO, MerKeyTypeMD5PwdEnum enum1) {
        MerKeyTypeFacade facade = getFacade(MerKeyTypeFacade.class, DelegateConstant.FACADE_USERS_URL);
        DodopalResponse<MerKeyTypeDTO> response = facade.findMerMD5PayPWDOrBackPayPWD(merKeyTypeDTO, enum1);
        return response;
    }

    @Override
    public DodopalResponse<Boolean> updateMerMD5PayPwdOrBackPayPWD(MerKeyTypeDTO merKeyTypeDTO, String oldPWD, MerKeyTypeMD5PwdEnum enum1) {
        MerKeyTypeFacade facade = getFacade(MerKeyTypeFacade.class, DelegateConstant.FACADE_USERS_URL);
        DodopalResponse<Boolean> response = facade.updateMerMD5PayPwdOrBackPayPWD(merKeyTypeDTO, oldPWD, enum1);
        return response;
    }

    @Override
    public DodopalResponse<Boolean> upModifyPayInfoFlg(MerchantUserDTO merchantUserDTO) {
        MerchantUserFacade facade = getFacade(MerchantUserFacade.class, DelegateConstant.FACADE_USERS_URL);
        DodopalResponse<Boolean> response = facade.modifyPayInfoFlg(merchantUserDTO);
        return response;
    }

    @Override
    public DodopalResponse<MerchantUserDTO> findModifyPayInfoFlg(String id) {
        MerchantUserFacade facade = getFacade(MerchantUserFacade.class, DelegateConstant.FACADE_USERS_URL);
        DodopalResponse<MerchantUserDTO> response = facade.findUserInfoById(id);
        return response;
    }

}
