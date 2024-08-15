package com.openclassrooms.mddapi.domain.dto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long user_id;

    @NonNull
    @Size(max = 50)
    @Email
    private String email;

    @NonNull
    @Size(max = 20)
    private String username;

    @NonNull
    private boolean admin;

    @JsonIgnore
    @Size(max = 120)
    private String password;

    private Date created_at;

    private Date updated_at;

}
