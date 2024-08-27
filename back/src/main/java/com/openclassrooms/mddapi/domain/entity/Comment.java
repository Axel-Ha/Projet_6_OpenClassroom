package com.openclassrooms.mddapi.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments")
public class Comment {

    /**
     * Unique identifier for the comment.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    /**
     * Author of the comment.
     */
    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "user_id")
    private UserEntity user;

    /**
     * Post to which the comment belongs.
     */
    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "post_id")
    private Post post;

    /**
     * The message content of the comment.
     */
    @NotBlank
    @NonNull
    @Size(max = 2000)
    private String message;

    /**
     * Timestamp when the comment was created.
     */
    @CreationTimestamp
    @Column(name="created_at")
    private LocalDateTime createdAt;
}
