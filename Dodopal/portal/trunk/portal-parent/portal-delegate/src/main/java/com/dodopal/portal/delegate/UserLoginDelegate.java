package com.dodopal.portal.delegate;

import com.dodopal.api.users.dto.PortalUserDTO;
import com.dodopal.common.model.DodopalResponse;

/**
 * 类说明 ：
 * @author lifeng
 */

public interface UserLoginDelegate {
    public DodopalResponse<PortalUserDTO> login(String userName, String password);

    /**
     * 联合登录接口
     * @param loginid
     * @param password
     * @param usertype
     * @return
     */
    public DodopalResponse<PortalUserDTO> unionLogin(String loginid, String password, String usertype);
}
