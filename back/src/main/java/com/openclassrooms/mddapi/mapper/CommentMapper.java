package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.domain.dto.CommentDto;
import com.openclassrooms.mddapi.domain.entity.Comment;
import com.openclassrooms.mddapi.domain.entity.Post;
import com.openclassrooms.mddapi.domain.entity.Topic;
import com.openclassrooms.mddapi.domain.entity.UserEntity;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CommentMapper {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    /**
     * Converts a Comment entity to a CommentDto.
     * <p>
     * This method maps the {@link Comment} entity to a {@link CommentDto}, converting the
     * associated {@link UserEntity} and {@link Post} entities to their respective IDs.
     * </p>
     *
     * @param comment the Comment entity to convert
     * @return the corresponding CommentDto
     */
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "post.id", target = "postId")
    @Mapping(target = "message", source = "message")
    public abstract CommentDto toDto(Comment comment);

    /**
     * Converts a CommentDto to a Comment entity.
     * <p>
     * This method maps a {@link CommentDto} to a {@link Comment} entity, resolving the user and post
     * references using their IDs. It also ignores the `createdAt` field during the conversion.
     * </p>
     *
     * @param commentDto the CommentDto to convert
     * @return the corresponding Comment entity
     */
    @Mapping(target = "user", source = "userId", qualifiedByName = "toUserEntity")
    @Mapping(target = "post", source = "postId", qualifiedByName = "toPostEntity")
    public abstract Comment toEntity(CommentDto commentDto);

    /**
     * Converts a list of Comment entities to a list of CommentDto objects.
     * <p>
     * This method iterates through a list of {@link Comment} entities and converts each one to a
     * {@link CommentDto}.
     * </p>
     *
     * @param commentList the list of Comment entities to convert
     * @return the corresponding list of CommentDto objects
     */
    public abstract List<CommentDto> toDtos(List<Comment> commentList);

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
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    /**
     * Converts a post ID to a Post entity.
     * <p>
     * This method finds a {@link Post} entity by its ID using the {@link PostRepository}.
     * If the post is not found, a {@link EntityNotFoundException} is thrown.
     * </p>
     *
     * @param id the ID of the post
     * @return the corresponding Post entity
     * @throws EntityNotFoundException if the post is not found
     */
    @Named("toPostEntity")
    Post toPostEntity(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + id));
    }
}

