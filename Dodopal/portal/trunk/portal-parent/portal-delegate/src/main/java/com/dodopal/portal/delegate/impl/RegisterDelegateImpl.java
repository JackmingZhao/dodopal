package com.dodopal.portal.delegate.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.dodopal.api.users.dto.MerchantDTO;
import com.dodopal.api.users.dto.MerchantUserDTO;
import com.dodopal.api.users.facade.RegisterFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.delegate.BaseDelegate;
import com.dodopal.portal.delegate.RegisterDelegate;
import com.dodopal.portal.delegate.constant.DelegateConstant;

/**
 * 类说明 ：
 * @author lifeng
 */
@Service("registerDelegate")
public class RegisterDelegateImpl extends BaseDelegate implements RegisterDelegate {

    /**
     * 检查手机号是否已注册
     * @param mobile
     * @return true:已注册
     */
    @Override
    public DodopalResponse<Boolean> checkMobileExist(String mobile) {
        RegisterFacade registerFacade = getFacade(RegisterFacade.class, DelegateConstant.FACADE_USERS_URL);
        return registerFacade.checkMobileExist(mobile);
    }

    /**
     * 检查用户名是否已注册
     * @param userName
     * @return true:已注册
     */
    @Override
    public DodopalResponse<Boolean> checkUserNameExist(String userName) {
        RegisterFacade registerFacade = getFacade(RegisterFacade.class, DelegateConstant.FACADE_USERS_URL);
        return registerFacade.checkUserNameExist(userName);
    }

    /**
     * 检查商户名称是否已注册
     * @param merName
     * @return
     */
    @Override
    public DodopalResponse<Boolean> checkMerchantNameExist(String merName) {
        RegisterFacade registerFacade = getFacade(RegisterFacade.class, DelegateConstant.FACADE_USERS_URL);
        return registerFacade.checkMerchantNameExist(merName);
    }

    /**
     * 个人用户注册
     * @param merUserBean
     * @return
     */
    @Override
    public DodopalResponse<MerchantUserDTO> registerUser(MerchantUserDTO merchantUserDTO, String dypwd, String serialNumber) {
        RegisterFacade registerFacade = getFacade(RegisterFacade.class, DelegateConstant.FACADE_USERS_URL);
        return registerFacade.registerUser(merchantUserDTO, dypwd, serialNumber);
    }

    /**
     * 商户注册
     * @param merUserBean merUserBean.merUserName：用户名 merUserBean.merUserPWD：密码
     * merUserBean.merUserMobile：用户手机号
     * @return 商户号
     */
    @Override
    public DodopalResponse<String> registerMerchant(MerchantDTO merchantDTO, String dypwd, String serialNumber) {
        RegisterFacade registerFacade = getFacade(RegisterFacade.class, DelegateConstant.FACADE_USERS_URL);
        DodopalResponse<String> resultResponse = registerFacade.registerMerchant(merchantDTO, dypwd, serialNumber);

        DodopalResponse<String> response = new DodopalResponse<String>();
        response.setCode(ResponseCode.SUCCESS);
        if (ResponseCode.SUCCESS.equals(resultResponse.getCode())) {
            String merCode = resultResponse.getResponseEntity();
            if (StringUtils.isNotBlank(merCode)) {
                response.setResponseEntity(merCode);
            }
        } else {
            response.setCode(resultResponse.getCode());
        }
        return response;
    }

   
}
