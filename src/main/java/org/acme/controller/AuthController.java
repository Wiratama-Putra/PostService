package org.acme.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.Response;
import org.acme.service.AuthService;
import org.acme.dto.LoginRequest;
import org.acme.dto.AuthResponse;
import org.acme.dto.ErrorResponse;

import java.util.Set;
import java.util.Objects;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthController {

    private final AuthService authService;

    @Inject
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @POST
    @Path("/login")
    public Response login(LoginRequest request) {
        if (request == null || request.username == null || request.username.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse("Username harus diisi"))
                    .build();
        }

        Set<String> userRoles = Objects.requireNonNullElse(request.roles, Set.of());
        String token = authService.generateToken(request.username, userRoles);

        return Response.ok(new AuthResponse(token)).build();
    }
}
