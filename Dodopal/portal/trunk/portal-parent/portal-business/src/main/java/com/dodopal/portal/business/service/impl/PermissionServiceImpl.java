package com.dodopal.portal.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.users.dto.PortalUserDTO;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.service.PermissionService;
import com.dodopal.portal.delegate.UserLoginDelegate;

@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private UserLoginDelegate userLoginDelegate;

    @Override
    public DodopalResponse<PortalUserDTO> login(String userName, String password) {
        return userLoginDelegate.login(userName, password);
    }

	@Override
	public DodopalResponse<PortalUserDTO> unionLogin(String loginid, String password, String usertype) {
		return userLoginDelegate.unionLogin(loginid, password, usertype);
	}

}
