package com.openclassrooms.mddapi.domain.entity;

import com.openclassrooms.mddapi.security.validation.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity {
    /**
     * User's unique ID.
     * This field is the primary key for the user entity and is auto-generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "user_id")
    private Long id;

    /**
     * User's unique email address.
     * This field must be non-null, non-blank, and a valid email format.
     * It must also be unique across all users.
     */
    @Column(name= "email", unique = true)
    @Email
    @NonNull
    @NotBlank
    private String email;

    /**
     * User's unique username.
     * This field must be non-null, non-blank, and unique across all users.
     */
    @Column(name= "username")
    @NonNull
    @NotBlank
    private String username;

    /**
     * User's password.
     * This field must be non-null and non-blank.
     */
    @Column(name= "password")
    @Size(min = 8)
    @NonNull
    @NotBlank
    @ValidPassword
    private String password;

    /**
     * User's topics subscriptions.
     * Represents a many-to-many relationship between users and topics.
     */
    @ManyToMany
    @JoinTable(name = "Subscriptions",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "topic_id"))
    private List<Topic> subscriptions;

    /**
     * User's creation date.
     * Automatically set to the timestamp when the user is created.
     */
    @CreationTimestamp
    @Column(name= "created_at")
    private LocalDateTime createdAt;
    /**
     * User's account last update date.
     * Automatically updated to the timestamp when the user entity is updated.
     */
    @UpdateTimestamp
    @Column(name= "updated_at")
    private LocalDateTime updatedAt;

}
