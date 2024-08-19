package com.openclassrooms.mddapi.domain.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;


@Data
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "comment_id")
    private Long comment_id;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "post_id")
    private Post post;

    @Column(name="message")
    private String message;

    @CreationTimestamp
    @Column(name="created_at")
    private LocalDateTime createdAt;
}
