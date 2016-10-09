package com.dodopal.portal.delegate.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dodopal.api.users.dto.PortalUserDTO;
import com.dodopal.api.users.facade.UserLoginFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.TrackIdHolder;
import com.dodopal.portal.delegate.BaseDelegate;
import com.dodopal.portal.delegate.UserLoginDelegate;
import com.dodopal.portal.delegate.constant.DelegateConstant;

/**
 * 类说明 ：
 * @author lifeng
 */
@Service("userLoginDelegate")
public class UserLoginDelegateImpl extends BaseDelegate implements UserLoginDelegate {
    private Logger logger = LoggerFactory.getLogger(UserLoginDelegateImpl.class);
    
    @Override
    public DodopalResponse<PortalUserDTO> login(String userName, String password) {
        try {
            UserLoginFacade userLoginFacade = getFacade(UserLoginFacade.class, DelegateConstant.FACADE_USERS_URL);
			String trackId = TrackIdHolder.setDefaultRandomTrackId();
			if (logger.isInfoEnabled()) {
				logger.info("login create trackId : " + trackId + ", userName:[" + userName + "]");
			}
			userLoginFacade.setTrackId(trackId);
            return userLoginFacade.login(userName, password);
        } catch(Exception e) {
            logger.error("门户登录调用用户接口出错", e);
            DodopalResponse<PortalUserDTO> response = new DodopalResponse<PortalUserDTO>();
            response.setCode(ResponseCode.PORTAL_USER_LOGIN_ERROR);
            return response;
        }
    }

	@Override
	public DodopalResponse<PortalUserDTO> unionLogin(String loginid, String password, String usertype) {
		try {
			UserLoginFacade userLoginFacade = getFacade(UserLoginFacade.class, DelegateConstant.FACADE_USERS_URL);
			String trackId = TrackIdHolder.setDefaultRandomTrackId();
			if (logger.isInfoEnabled()) {
				logger.info("login create trackId : " + trackId + ", loginid:[" + loginid + "], usertype[" + usertype + "]");
			}
			userLoginFacade.setTrackId(trackId);
			return userLoginFacade.unionLogin(loginid, password, usertype);
		} catch (Exception e) {
			logger.error("门户登录调用用户接口出错", e);
			DodopalResponse<PortalUserDTO> response = new DodopalResponse<PortalUserDTO>();
			response.setCode(ResponseCode.PORTAL_USER_LOGIN_ERROR);
			return response;
		}
	}

}
