package com.openclassrooms.mddapi.domain.dto;

import com.openclassrooms.mddapi.domain.entity.Topic;
import com.openclassrooms.mddapi.domain.entity.UserEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    /**
     * The unique identifier of the comment.
     */
    private Long comment_id;

    /**
     * The ID of the post associated with the comment.
     */
    @NotNull
    private Long post_id;

    /**
     * The ID of the author who created the comment.
     */
    @NotNull
    private Long author_id;

    /**
     * The main content of the comment.
     */
    @NotBlank
    @Size(max = 10000)
    private String message;

    /**
     * The date and time when the comment was created.
     */
    private Date createdAt;
}
