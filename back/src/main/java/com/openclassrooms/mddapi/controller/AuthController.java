package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.domain.dto.LoginUserDto;
import com.openclassrooms.mddapi.domain.dto.RegisterUserDto;
import com.openclassrooms.mddapi.domain.entity.AuthResponse;
import com.openclassrooms.mddapi.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterUserDto registerUserDto ){
        return ResponseEntity.ok(authService.register(registerUserDto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginUserDto loginUserDto){
        return ResponseEntity.ok(authService.login(loginUserDto));
    }

}
