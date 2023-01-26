package com.dmitri.backend.model;

import com.dmitri.backend.repository.UserRepository;
import com.dmitri.backend.util.AuthStatus;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class AuthorizationManager {

    @EJB
    private UserRepository userRepository;

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
        User userByUsername = userRepository.getUserByUsername(reqUsername);
        if (userByUsername == null) {
            return AuthStatus.AUTH_WRONG_LOGIN;
        } else if (!userByUsername.getPassword().equals(reqPassword)) {
            return AuthStatus.AUTH_WRONG_PASSWORD;
        } else {
            return AuthStatus.AUTH_OK;
        }
    }
}
