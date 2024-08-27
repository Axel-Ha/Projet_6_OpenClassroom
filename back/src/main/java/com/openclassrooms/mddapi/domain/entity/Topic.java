package com.openclassrooms.mddapi.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "topics")
public class Topic {
    /**
     * Topic's unique ID.
     * This field is the primary key for the topic entity and is auto-generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topic_id")
    private Long id;

    /**
     * Topic's name.
     * This field must be non-null, non-blank, and is required for each topic.
     */
    @NonNull
    @Column(nullable = false)
    @NotBlank
    private String name;

    /**
     * Topic's description.
     * This field must be non-null, non-blank, and provides additional details about
     * the topic.
     */
    @NonNull
    @Column(nullable = false)
    @NotBlank
    private String description;

}
