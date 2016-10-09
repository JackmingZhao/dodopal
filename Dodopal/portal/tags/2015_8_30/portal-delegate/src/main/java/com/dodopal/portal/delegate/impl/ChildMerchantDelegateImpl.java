package com.dodopal.portal.delegate.impl;

import org.springframework.stereotype.Service;

import com.dodopal.api.users.dto.MerchantDTO;
import com.dodopal.api.users.dto.MerchantQueryDTO;
import com.dodopal.api.users.facade.ChildMerchantFacade;
import com.dodopal.api.users.facade.RegisterFacade;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.delegate.BaseDelegate;
import com.dodopal.portal.delegate.ChildMerchantDelegate;
import com.dodopal.portal.delegate.constant.DelegateConstant;
@Service("childMerchantDelegate")
public class ChildMerchantDelegateImpl extends BaseDelegate implements ChildMerchantDelegate {
    /**
     * 查询子商户信息列表-后台分页
     */
    @Override
    public DodopalResponse<DodopalDataPage<MerchantDTO>> findChildMerchantListByPage(MerchantQueryDTO merQueryDTO) {
        ChildMerchantFacade facade = getFacade(ChildMerchantFacade.class, DelegateConstant.FACADE_USERS_URL);
        return facade.findChildMerchantByPage(merQueryDTO);
    }

    /**
     * 保存子商戶信息
     */
    @Override
    public DodopalResponse<String> saveChildMerchant(MerchantDTO merchantDTO) {
        ChildMerchantFacade facade = getFacade(ChildMerchantFacade.class, DelegateConstant.FACADE_USERS_URL);
        DodopalResponse<String> response = facade.saveChildMerchant(merchantDTO);
        return response;
    }

    /**
     * 根据商户编号查询子商户单条信息
     */
    @Override
    public DodopalResponse<MerchantDTO> findChildMerchants(String merCode, String merParentCode) {
        ChildMerchantFacade facade = getFacade(ChildMerchantFacade.class, DelegateConstant.FACADE_USERS_URL);
        DodopalResponse<MerchantDTO> response = facade.findChildMerchantByMerCode(merCode,merParentCode);
        return response;
    }

    /**
     * 編輯後保存子商戶信息
     */
    @Override
    public DodopalResponse<String> updateChildMerchant(MerchantDTO merchantDTO) {
        ChildMerchantFacade facade = getFacade(ChildMerchantFacade.class, DelegateConstant.FACADE_USERS_URL);
        DodopalResponse<String> response = facade.upChildMerchant(merchantDTO);
        return response;
    }

    /**
     * 停启用子商户信息
     */
    @Override
    public DodopalResponse<Integer> startAndDisableChildMerchant(String[] merCode, String activate,String merParentCode, String updateUser) {
        ChildMerchantFacade facade = getFacade(ChildMerchantFacade.class, DelegateConstant.FACADE_USERS_URL);
        DodopalResponse<Integer> response = facade.upChildMerchantActivate(merCode, activate,merParentCode, updateUser);
        return response;
    }
    /**
     * 单个删除和批量删除子商户信息
     */
    @Override
    public DodopalResponse<Integer> deleteChildMerchant(String[] merCode, String merState, String merParentCode, String updateUser) {
        //TODO 删除
        ChildMerchantFacade facade = getFacade(ChildMerchantFacade.class, DelegateConstant.FACADE_USERS_URL);
        DodopalResponse<Integer> response = facade.upChildMerchantActivate(merCode, merState,merParentCode, updateUser);
        return response;
    }

    @Override
    public DodopalResponse<Boolean> checkMobileExist(String mobile, String merCode) {
        RegisterFacade registerFacade = getFacade(RegisterFacade.class, DelegateConstant.FACADE_USERS_URL);
        return registerFacade.checkMobileExist(mobile, merCode);
    }

    @Override
    public DodopalResponse<Boolean> checkUserNameExist(String userName, String merCode) {
        RegisterFacade registerFacade = getFacade(RegisterFacade.class, DelegateConstant.FACADE_USERS_URL);
        return registerFacade.checkUserNameExist(userName, merCode);
    }

    @Override
    public DodopalResponse<Boolean> checkMerchantNameExist(String merName, String merCode) {
        RegisterFacade registerFacade = getFacade(RegisterFacade.class, DelegateConstant.FACADE_USERS_URL);
        return registerFacade.checkMerchantNameExist(merName, merCode);
    }
  

}
