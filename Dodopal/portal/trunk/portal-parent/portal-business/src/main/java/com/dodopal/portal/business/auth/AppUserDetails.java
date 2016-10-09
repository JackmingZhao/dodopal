package com.dodopal.portal.business.auth;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.dodopal.api.users.dto.PortalUserDTO;


public class AppUserDetails extends org.springframework.security.core.userdetails.User implements Serializable {
    private static final long serialVersionUID = -6079812302427316288L;
    private PortalUserDTO user;

    public AppUserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public AppUserDetails(PortalUserDTO user) {
        super(user.getMerUserName(), user.getMerUserPWD(), true, true, true, true, new ArrayList<GrantedAuthority>() {
            private static final long serialVersionUID = 1L;
            {
                add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            }
        });
        this.user = user;
    }

    public PortalUserDTO getUser() {
        return user;
    }

}
