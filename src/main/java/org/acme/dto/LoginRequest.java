package org.acme.dto;

import java.util.Set;

public class LoginRequest {
    public String username;
    public Set<String> roles;

    public LoginRequest() {
    }

    public LoginRequest(String username, Set<String> roles) {
        this.username = username;
        this.roles = roles;
    }
}
