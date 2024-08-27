package com.openclassrooms.mddapi.domain.dto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    /**
     * The unique identifier of the user.
     */
    private Long userId;

    /**
     * The email address of the user.
     */
    @NotBlank
    @Email
    private String email;

    /**
     * The username of the user.
     */
    @NotBlank
    private String username;

}
