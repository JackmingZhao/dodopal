package com.dodopal.portal.delegate.impl;

import org.springframework.stereotype.Service;

import com.dodopal.api.users.dto.PortalUserDTO;
import com.dodopal.api.users.facade.UserLoginFacade;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.delegate.BaseDelegate;
import com.dodopal.portal.delegate.UserLoginDelegate;
import com.dodopal.portal.delegate.constant.DelegateConstant;

/**
 * 类说明 ：
 * @author lifeng
 */
@Service("userLoginDelegate")
public class UserLoginDelegateImpl extends BaseDelegate implements UserLoginDelegate {

    @Override
    public DodopalResponse<PortalUserDTO> login(String userName, String password) {
        UserLoginFacade userLoginFacade = getFacade(UserLoginFacade.class, DelegateConstant.FACADE_USERS_URL);
        return userLoginFacade.login(userName, password);
    }

}
