package com.dodopal.payment.delegate.impl;

import java.util.Map;

import com.dodopal.api.users.dto.MerchantUserDTO;
import com.dodopal.api.users.facade.MerchantUserFacade;
import org.springframework.stereotype.Service;

import com.dodopal.api.users.facade.MerchantFacade;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.payment.delegate.BaseDelegate;
import com.dodopal.payment.delegate.MerchantDelegate;
import com.dodopal.payment.delegate.constant.DelegateConstant;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年8月17日 下午7:10:07
 */
@Service("merchantDelegate")
public class MerchantDelegateImpl extends BaseDelegate implements MerchantDelegate {

    @Override
    public DodopalResponse<Map<String, Object>> validateMerchantForPayment(MerUserTypeEnum userType, String code) {
        MerchantFacade facade = getFacade(MerchantFacade.class, DelegateConstant.FACADE_USERS_URL);
        return facade.validateMerchantForPayment(userType, code);
    }

    /**
     * @descripton 根据userId 查询userCode
     * @param userID
     * @return
     */
    @Override
    public DodopalResponse<MerchantUserDTO> findMerUser(String userID) {
        MerchantUserFacade merUserFacade = getFacade(MerchantUserFacade.class, DelegateConstant.FACADE_USERS_URL);
        return merUserFacade.findUserInfoById(userID);
    }
    /**
     * @descripton 根据userCode验证用户和商户的合法性
     * @param userCode
     * @return
     */
    @Override
    public DodopalResponse<Map<String,Object>> validateMerchantUserForPayment(String userCode) {
        MerchantFacade merUserFacade = getFacade(MerchantFacade.class, DelegateConstant.FACADE_USERS_URL);
        return merUserFacade.validateMerchantUserForPayment(userCode);
    }
}
