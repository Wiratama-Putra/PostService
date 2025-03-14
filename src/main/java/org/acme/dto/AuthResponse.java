package org.acme.dto;

public class AuthResponse {
    public String token;

    public AuthResponse() {
    }

    public AuthResponse(String token) {
        this.token = token;
    }
}
