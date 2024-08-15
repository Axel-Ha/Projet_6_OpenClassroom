package com.openclassrooms.mddapi.domain.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "user_id")
    private Long userId;

    @Column(name= "email", unique = true)
    @Size(max = 50)
    private String email;

    @Column(name= "username")
    @Size(max = 20)
    private String username;

    @Column(name= "password")
    @Size(max = 120)
    private String password;

    @Column(name= "created_at")
    private Date createdAt;

    @Column(name= "updated_at")
    private Date updatedAt;

    //Ne vas pas etre mapper, defaut = USER
    @Transient
    private final String role = "USER";
}
