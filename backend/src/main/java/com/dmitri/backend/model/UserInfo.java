package com.dmitri.backend.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfo {

    private String username;
    private String password;

    public UserInfo() {
    }

    public UserInfo(String username, String password) {
        this.password = password;
        this.username = username;
    }
}
