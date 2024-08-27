package com.openclassrooms.mddapi.domain.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "posts")
public class Post {
    /**
     * Post's unique ID.
     * This field is the primary key for the post entity and is auto-generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "post_id")
    private Long id;

    /**
     * Topic associated with the post.
     * This field represents a many-to-one relationship with the {@link Topic}
     * entity.
     */
    @ManyToOne
    @JoinColumn(name = "topic_id", referencedColumnName = "topic_id")
    private Topic topic;

    /**
     * Post's title.
     * This field must be non-null and non-blank.
     */
    @NotNull
    @NotBlank
    @Size(max = 50)
    private String title;

    /**
     * Author of the post.
     * This field represents a many-to-one relationship with the {@link UserEntity}
     * entity.
     */
    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "user_id")
    private UserEntity user;

    /**
     * Post's content.
     * This field must be non-null and non-blank.
     */
    @NotNull
    @NotBlank
    @Size(max = 10000)
    private String content;

    /**
     * Post's creation date.
     * This field is automatically set to the date when the post is created.
     */
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
