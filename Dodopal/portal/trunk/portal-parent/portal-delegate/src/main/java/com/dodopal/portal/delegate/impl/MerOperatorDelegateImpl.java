package com.dodopal.portal.delegate.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dodopal.api.users.dto.MerchantUserDTO;
import com.dodopal.api.users.dto.query.MerchantUserQueryDTO;
import com.dodopal.api.users.facade.MerchantFacade;
import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.delegate.BaseDelegate;
import com.dodopal.portal.delegate.MerOperatorDelegate;
import com.dodopal.portal.delegate.constant.DelegateConstant;

/**
 * 类说明 ：用户管理（管理操作员）
 * @author lifeng
 */
@Service("merOperatorDelegate")
public class MerOperatorDelegateImpl extends BaseDelegate implements MerOperatorDelegate {

    @Override
    public DodopalResponse<DodopalDataPage<MerchantUserDTO>> findMerOperatorByPage(MerchantUserQueryDTO userQueryDTO) {
        MerchantFacade facade = getFacade(MerchantFacade.class, DelegateConstant.FACADE_USERS_URL);
        DodopalResponse<DodopalDataPage<MerchantUserDTO>> response = facade.findMerOperatorByPage(userQueryDTO);
        return response;
    }

    @Override
    public DodopalResponse<MerchantUserDTO> findMerOperatorByUserCode(String merCode, String userCode) {
        MerchantFacade facade = getFacade(MerchantFacade.class, DelegateConstant.FACADE_USERS_URL);
        DodopalResponse<MerchantUserDTO> response = facade.findMerOperatorByUserCode(merCode, userCode);
        return response;
    }

    @Override
    public DodopalResponse<Integer> addMerOperator(MerchantUserDTO merchantUserDTO) {
        MerchantFacade facade = getFacade(MerchantFacade.class, DelegateConstant.FACADE_USERS_URL);
        DodopalResponse<Integer> response = facade.addMerOperator(merchantUserDTO);
        return response;
    }

    @Override
    public DodopalResponse<Integer> updateMerOperator(MerchantUserDTO merchantUserDTO) {
        MerchantFacade facade = getFacade(MerchantFacade.class, DelegateConstant.FACADE_USERS_URL);
        DodopalResponse<Integer> response = facade.updateMerOperator(merchantUserDTO);
        return response;
    }

    @Override
    public DodopalResponse<Integer> batchActivateMerOperator(String merCode, String updateUser, ActivateEnum activate, List<String> userCodes) {
        MerchantFacade facade = getFacade(MerchantFacade.class, DelegateConstant.FACADE_USERS_URL);
        DodopalResponse<Integer> response = facade.batchActivateMerOperator(merCode, updateUser, activate, userCodes);
        return response;
    }

    @Override
    public DodopalResponse<Integer> configMerOperatorRole(MerchantUserDTO merchantUserDTO) {
        MerchantFacade facade = getFacade(MerchantFacade.class, DelegateConstant.FACADE_USERS_URL);
        DodopalResponse<Integer> response = facade.configMerOperatorRole(merchantUserDTO);
        return response;
    }

    @Override
    public DodopalResponse<Boolean> resetOperatorPwd(String merCode, String id, String password, String updateUser) {
        MerchantFacade facade = getFacade(MerchantFacade.class, DelegateConstant.FACADE_USERS_URL);
        DodopalResponse<Boolean> response = facade.resetOperatorPwd(merCode, id, password, updateUser);
        return response;
    }

}
