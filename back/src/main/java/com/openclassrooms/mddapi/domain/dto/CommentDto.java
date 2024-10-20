package com.openclassrooms.mddapi.domain.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    /**
     * The unique identifier of the comment.
     */
    private Long id;

    /**
     * The ID of the post associated with the comment.
     */
    @NotNull
    private Long postId;

    /**
     * The ID of the user who created the comment.
     */
    @NotNull
    private Long userId;

    /**
     * The main content of the comment.
     */
    @NotBlank
    @Size(max = 10000)
    private String message;

    /**
     * The date and time when the comment was created.
     */
    private LocalDateTime createdAt;
}
