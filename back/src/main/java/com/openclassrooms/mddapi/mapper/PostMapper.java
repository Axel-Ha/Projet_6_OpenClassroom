package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.domain.entity.Topic;
import com.openclassrooms.mddapi.exceptions.NotFoundException;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;

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

    /**
     * Converts a Post entity to a PostDto.
     * <p>
     * This method maps the {@link Post} entity to a {@link PostDto}, converting the
     * topic and user references to their respective IDs.
     * </p>
     *
     * @param post the Post entity to convert
     * @return the corresponding PostDto
     */
    @Mapping(target = "topicId", source = "topic.id")
    @Mapping(target = "authorId", source = "user.id")
    @Mapping(target = "createdAt", source = "createdAt")
    public abstract PostDto toDto(Post post);

    /**
     * Converts a PostDto to a Post entity.
     * <p>
     * This method maps a {@link PostDto} to a {@link Post} entity, converting the IDs
     * of the topic and user to their respective entities using the qualified mapping methods.
     * </p>
     *
     * @param postDto the PostDto to convert
     * @return the corresponding Post entity
     * @throws NotFoundException if the associated user or topic is not found in the repositories
     */
    @Mapping(target = "topic", source = "topicId", qualifiedByName = "toTopicEntity")
    @Mapping(target = "user", source = "authorId", qualifiedByName = "toUserEntity")
    public abstract Post toEntity(PostDto postDto);

    /**
     * Converts a list of Post entities to a list of PostDto objects.
     * <p>
     * This method iterates through a list of {@link Post} entities and converts each one to a
     * {@link PostDto}.
     * </p>
     *
     * @param posts the list of Post entities to convert
     * @return the corresponding list of PostDto objects
     */
    public abstract List<PostDto> toDto(List<Post> posts);

    /**
     * Converts a topic ID to a Topic entity.
     * <p>
     * This method finds a {@link Topic} entity by its ID using the {@link TopicRepository}.
     * If the topic is not found, a {@link EntityNotFoundException} is thrown.
     * </p>
     *
     * @param id the ID of the topic
     * @return the corresponding Topic entity
     * @throws EntityNotFoundException if the topic is not found
     */
    @Named("toTopicEntity")
    Topic toTopicEntity(Long id) {
        return topicRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Topic not found with id: " + id));
    }

    /**
     * Converts a user ID to a User entity.
     * <p>
     * This method finds a {@link UserEntity} entity by its ID using the {@link UserRepository}.
     * If the user is not found, a {@link EntityNotFoundException} is thrown.
     * </p>
     *
     * @param id the ID of the user
     * @return the corresponding User entity
     * @throws EntityNotFoundException if the user is not found
     */
    @Named("toUserEntity")
    UserEntity toUserEntity(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }
}
