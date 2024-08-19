package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.domain.entity.Topic;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import jakarta.ws.rs.NotFoundException;
import javax.persistence.EntityNotFoundException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.openclassrooms.mddapi.domain.dto.PostDto;
import com.openclassrooms.mddapi.domain.entity.Post;
import com.openclassrooms.mddapi.domain.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Mapper(componentModel = "spring")
public abstract class PostMapper {
    @Autowired
    TopicRepository topicRepository;

    @Autowired
    UserRepository userRepository;

    @Mapping(target = "topic_id", source = "topic.topic_id")
    @Mapping(target = "author_id", source = "user.userId")
    @Mapping(target = "createdAt", source = "createdAt")
    public abstract PostDto toDto(Post post);

    @Mapping(target = "topic", source = "topic_id", qualifiedByName = "toTopicEntity")
    @Mapping(target = "user", source = "author_id", qualifiedByName = "toUserEntity")
    public abstract Post toEntity(PostDto postDto);

    public abstract List<PostDto> toDto(List<Post> posts);

    @Named("toTopicEntity")
    Topic toTopicEntity(Long id) {
        return topicRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Topic not found with id: " + id));
    }

    @Named("toUserEntity")
    UserEntity toUserEntity(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }
}
