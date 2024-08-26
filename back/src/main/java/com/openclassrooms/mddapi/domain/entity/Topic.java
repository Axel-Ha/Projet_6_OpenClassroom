package com.openclassrooms.mddapi.domain.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;


@Data
@Entity
@Table(name = "topics")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "topic_id")
    private Long id;

    @Column(name= "name", unique = true)
    @Size(max = 50)
    private String name;

    @Column(name= "description")
    @Size(max = 2000)
    private String description;

}
