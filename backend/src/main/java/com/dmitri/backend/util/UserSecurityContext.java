package com.dmitri.backend.util;

import com.dmitri.backend.model.UserPrincipal;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

public class UserSecurityContext implements SecurityContext {

    private UserPrincipal user;
    private String scheme;

    public UserSecurityContext(UserPrincipal user, String scheme) {
        this.user = user;
        this.scheme = scheme;
    }

    @Override
    public Principal getUserPrincipal() {
        return user;
    }

    @Override
    public boolean isUserInRole(String s) {
        return true;
    }

    @Override
    public boolean isSecure() {
        return true;
    }

    @Override
    public String getAuthenticationScheme() {
        return "Bearer";
    }
}
