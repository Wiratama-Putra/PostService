package org.acme.Config;

import io.smallrye.jwt.build.Jwt;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.Response;
import java.util.Set;
import java.util.Objects;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    AuthService authService;

    @POST
    @Path("/login")
    public Response login(LoginRequest request) {
        if (request == null || request.username == null || request.username.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Username harus diisi\"}")
                    .build();
        }

        Set<String> userRoles = Objects.requireNonNullElse(request.roles, Set.of());

        String token = authService.generateToken(request.username, userRoles);
        return Response.ok(new AuthResponse(token)).build();
    }

    // DTO untuk menerima request
    public static class LoginRequest {
        public String username;
        public Set<String> roles;
    }

    // DTO untuk response
    public static class AuthResponse {
        public String token;

        public AuthResponse(String token) {
            this.token = token;
        }
    }
}


