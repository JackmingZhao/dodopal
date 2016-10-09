package com.dodopal.portal.business.service;

import com.dodopal.api.users.dto.PortalUserDTO;
import com.dodopal.common.model.DodopalResponse;

public interface PermissionService {

    DodopalResponse<PortalUserDTO> login(String userName, String password);

}
