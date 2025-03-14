package org.acme.Service;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Set;

@ApplicationScoped
public class AuthService {

    public String generateToken(String username, Set<String> roles) {
        return Jwt.issuer("quarkus")
                .subject(username)
                .groups(roles)
                .expiresAt(System.currentTimeMillis() / 1000 + 3600)
                .sign();
    }
}
