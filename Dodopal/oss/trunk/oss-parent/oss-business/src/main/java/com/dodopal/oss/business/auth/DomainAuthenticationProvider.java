package com.dodopal.oss.business.auth;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import com.dodopal.oss.business.model.User;
import com.dodopal.oss.business.service.PermissionService;

@SuppressWarnings("deprecation")
public class DomainAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private PermissionService permissionService;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        if (authentication.getCredentials() == null) {
            logger.debug("Authentication failed: no credentials provided");

            throw new BadCredentialsException("用户名或密码错误");
        }

        String presentedPassword = authentication.getCredentials().toString();

        if(StringUtils.isBlank(presentedPassword)) {
        	throw new BadCredentialsException("用户名或密码错误");
        }
        
        if (!presentedPassword.equals(userDetails.getPassword())) {
            logger.debug("Authentication failed: password does not match stored value");
            throw new BadCredentialsException("用户名或密码错误");
        }
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        if(StringUtils.isBlank(username)) {
        	throw new BadCredentialsException("用户名或密码错误");
        }
        
        User appUser = permissionService.findUser(username.toLowerCase());
        
        if(appUser == null){
            throw new BadCredentialsException("用户名或密码错误");
        }
        
        if(appUser.getActivate()==1) {
        	throw new BadCredentialsException("用户已停用");
        }
        
       /* if(appUser.getPasswordExpiredDate() != null && appUser.getPasswordExpiredDate().before(new Date())) {
        	throw new BadCredentialsException("用户已过期，不能登录系统！");
        }*/
        	
        AppUserDetails ad = new AppUserDetails(appUser);
        return ad;
    }

	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}

}
