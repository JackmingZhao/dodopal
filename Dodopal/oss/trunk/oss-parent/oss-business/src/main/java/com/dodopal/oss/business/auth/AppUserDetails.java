package com.dodopal.oss.business.auth;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.dodopal.oss.business.model.User;

public class AppUserDetails extends org.springframework.security.core.userdetails.User implements Serializable {
    private static final long serialVersionUID = -6079812302427316288L;
    private User user;

    public AppUserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public AppUserDetails(User user) {
        super(user.getLoginName(), user.getPassword(), true, true, true, true, new ArrayList<GrantedAuthority>() {
            private static final long serialVersionUID = 1L;
            {
                add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            }
        });
        this.user = user;
    }

    public User getUser() {
        return user;
    }

}
