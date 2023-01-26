package com.dmitri.backend.model;

import lombok.Getter;
import lombok.Setter;

import javax.security.auth.Subject;
import java.security.Principal;

@Getter
@Setter
public class UserPrincipal implements Principal {

    private String username;

    public UserPrincipal() {
    }

    public UserPrincipal(String username) {
        this.username = username;
    }

    @Override
    public String getName() {
        return username;
    }

    @Override
    public boolean implies(Subject subject) {
        return Principal.super.implies(subject);
    }
}
