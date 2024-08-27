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
public class PostDto {

    /**
     * The unique identifier of the post.
     */
    private Long id;

    /**
     * The ID of the topic associated with the post.
     */
    @NotNull
    private Long topicId;

    /**
     * The title of the post.
     */
    @NotBlank
    @Size(max = 50)
    private String title;

    /**
     * The ID of the author who created the post.
     */
    @NotNull
    private Long authorId;

    /**
     * The main content of the post.
     */
    @NotBlank
    @Size(max = 10000)
    private String content;

    /**
     * The date and time when the post was created.
     */
    private LocalDateTime createdAt;
}