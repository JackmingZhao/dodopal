package com.dodopal.portal.business.auth;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import com.dodopal.api.users.dto.PortalUserDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.service.PermissionService;

public class DomainAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    private PermissionService permissionService;

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        if (StringUtils.isBlank(username)) {
            throw new BadCredentialsException("登录名或密码输入有误");
        }
        String presentedPassword = authentication.getCredentials().toString();

        if (StringUtils.isBlank(presentedPassword)) {
            throw new BadCredentialsException("登录名或密码输入有误");
        }
        
        DodopalResponse<PortalUserDTO> response = permissionService.login(username, presentedPassword);

        if (response == null) {
            throw new BadCredentialsException("登录过程发生异常！");
        }

        if (!ResponseCode.SUCCESS.equals(response.getCode())) {
            throw new BadCredentialsException(generateErrorMessage(response.getCode(), response.getMessage()));
        }

        AppUserDetails ad = new AppUserDetails(response.getResponseEntity());
        return ad;
    }

    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    private String generateErrorMessage(String code, String message) {
        if (StringUtils.isNotEmpty(code) || StringUtils.isNotEmpty(message)) {
            if (code.startsWith("999")) {
                return "系统故障,错误码：" + code;
            } else {
                if (StringUtils.isNotEmpty(message)) {
                    return message;
                } else {
                    return "错误码：" + code;
                }
            }
        } else {
            return "出错啦,无法获取到错误码或者错误信息.";
        }
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
    }

}
