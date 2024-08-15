package com.openclassrooms.mddapi.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicDto {
    private Long topicId;

    @NonNull
    @Size(max = 50)
    private String name;

    @NonNull
    @Size(max = 2000)
    private String description;
}
