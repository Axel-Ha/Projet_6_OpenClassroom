package com.openclassrooms.mddapi.payload.request;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A request class for user registration.
 * This class contains the necessary fields for registering a new user,
 * including username, email, and password.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    /**
     * The username for the new user.
     * This field must not be blank.
     */
    @NotBlank
    private String username;

    /**
     * The email address for the new user.
     * This field must not be blank and must be a valid email format.
     */
    @NotBlank
    @Email
    private String email;

    /**
     * The password for the new user.
     * This field must not be blank.
     * The password must be at least 8 characters long and must contain
     * at least one uppercase letter, one lowercase letter, one digit,
     * and one special character.
     */
    @Size(min = 8)
    private String password;
}