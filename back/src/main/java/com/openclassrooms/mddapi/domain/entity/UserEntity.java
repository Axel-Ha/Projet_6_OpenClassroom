package com.openclassrooms.mddapi.domain.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

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
    @Size(min = 8)
    private String password;

    @ManyToMany
    @JoinTable(name = "Subscriptions",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "topic_id"))
    private List<Topic> subscriptions;


    @Column(name= "created_at")
    private Date createdAt;

    @Column(name= "updated_at")
    private Date updatedAt;

}
