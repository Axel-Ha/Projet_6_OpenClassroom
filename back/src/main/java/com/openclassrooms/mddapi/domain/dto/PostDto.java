package com.openclassrooms.mddapi.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    /**
     * The unique identifier of the post.
     */
    private Long post_id;

    /**
     * The ID of the topic associated with the post.
     */
    @NotNull
    private Long topic_id;

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
    private Long author_id;

    /**
     * The main content of the post.
     */
    @NotBlank
    @Size(max = 10000)
    private String content;

    /**
     * The date and time when the post was created.
     */
    private Date createdAt;
}