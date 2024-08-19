package com.openclassrooms.mddapi.domain.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
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


    @CreationTimestamp
    @Column(name= "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name= "updated_at")
    private LocalDateTime updatedAt;

}
