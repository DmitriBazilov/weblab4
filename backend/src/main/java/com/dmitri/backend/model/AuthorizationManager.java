package com.dmitri.backend.model;

import com.dmitri.backend.util.AuthStatus;

import javax.ejb.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class AuthorizationManager {

    private Map<String, User> users = new HashMap<>();

    public AuthStatus addUser(String username, User user) {
        boolean containsFlag = users.containsKey(username);
        if (containsFlag) {
            return AuthStatus.AUTH_WRONG_LOGIN;
        } else {
            users.put(username, user);
            return AuthStatus.AUTH_OK;
        }
    }

    public AuthStatus authenticate(User user) {
        String reqUsername = user.getUsername();
        String reqPassword = user.getPassword();
        if (!users.containsKey(reqUsername)) {
            return AuthStatus.AUTH_WRONG_LOGIN;
        } else if (!users.get(reqUsername).getPassword().equals(reqPassword)) {
            return AuthStatus.AUTH_WRONG_PASSWORD;
        } else {
            return AuthStatus.AUTH_OK;
        }
    }
}
