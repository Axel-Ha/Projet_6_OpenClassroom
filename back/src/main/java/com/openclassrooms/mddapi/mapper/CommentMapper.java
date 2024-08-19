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

    // Mapping Comment entity to CommentDto
    @Mapping(target = "author_id", source = "user.userId")
    @Mapping(target = "post_id", source = "post.post_id")
    @Mapping(target = "message", source = "message")
    @Mapping(target = "createdAt", source = "createdAt")
    public abstract CommentDto toDto(Comment comment);

    // Mapping CommentDto to Comment entity
    @Mapping(target = "user", source = "author_id", qualifiedByName = "toUserEntity")
    @Mapping(target = "post", source = "post_id", qualifiedByName = "toPostEntity")
    @Mapping(target = "createdAt", ignore = true)
    public abstract Comment toEntity(CommentDto commentDto);

    // Mapping list of Comment entities to list of Comment DTOs
    public abstract List<CommentDto> toDtos(List<Comment> commentList);

    @Named("toUserEntity")
    UserEntity toUserEntity(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    @Named("toPostEntity")
    Post toPostEntity(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + id));
    }
}

