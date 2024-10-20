package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.payload.request.LoginRequest;
import com.openclassrooms.mddapi.payload.request.RegisterRequest;
import com.openclassrooms.mddapi.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.openclassrooms.mddapi.configuration.SwaggerConfig.NAME_SECURITY_REQUIREMENT;

/**
 * Controller for authentication-related endpoints.
 * Provides endpoints for user login and registration.
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth Controller")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register")
    @Operation(
            summary = "Register a user",
            description = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(description = "User created", responseCode = "200"),
            @ApiResponse(description = "Bad request, validation errors", responseCode = "400"),
            @ApiResponse(description = "Internal server error", responseCode = "500")
    })
    public ResponseEntity<?> register(@RequestBody RegisterRequest RegisterRequest ){
        return authService.register(RegisterRequest);
    }

    @PostMapping("/login")
    @Operation(
            summary = "Log in a user",
            description = "Log in a user with their credentials")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User logged in"),
            @ApiResponse(responseCode = "400", description = "Bad request, validation errors"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    public ResponseEntity<?> login(@RequestBody LoginRequest LoginRequest){
        return authService.login(LoginRequest);
    }


    @SecurityRequirement(name=NAME_SECURITY_REQUIREMENT)
    @GetMapping("/me")
    @Operation(
            summary = "Get authenticated user",
            description = "Retrieve the authenticated user details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user details"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated")
    })
    public ResponseEntity<?> me(){
        return authService.me();
    }

}
