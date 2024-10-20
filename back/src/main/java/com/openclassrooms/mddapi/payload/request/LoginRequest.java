package com.openclassrooms.mddapi.payload.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

/**
 * A request class for user login.
 * This class contains the necessary fields for authenticating a user,
 * including a username or email and a password.
 */
@Data
@Getter
@Setter
public class LoginRequest {

    /**
     * The email of the user attempting to log in.
     * This field must not be blank.
     */
    @NotBlank
    private String email;

    /**
     * The password of the user attempting to log in.
     * This field must not be blank.
     */
    @NotBlank
    private String password;
}