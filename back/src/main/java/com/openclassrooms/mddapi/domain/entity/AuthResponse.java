package com.openclassrooms.mddapi.domain.entity;

import lombok.Data;

@Data
public class AuthResponse {
    String token;

    public AuthResponse(String token) {
        this.token = token;
    }

}