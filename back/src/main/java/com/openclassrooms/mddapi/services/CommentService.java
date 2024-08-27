package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.domain.dto.CommentDto;
import com.openclassrooms.mddapi.domain.entity.Comment;
import com.openclassrooms.mddapi.domain.entity.Post;
import com.openclassrooms.mddapi.exceptions.NotFoundException;
import com.openclassrooms.mddapi.mapper.CommentMapper;
import com.openclassrooms.mddapi.payload.response.MessageResponse;
import com.openclassrooms.mddapi.repository.CommentRepository;
import com.openclassrooms.mddapi.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Service for managing comments in the application.
 * <p>
 * This service provides methods to retrieve and create comments associated with posts.
 * It interacts with the {@link CommentRepository} and {@link PostRepository} for data access operations.
 * Uses {@link CommentMapper} for converting between entity and DTO representations of comments.
 * </p>
 */
@Slf4j
@Service
public class CommentService {
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentService(CommentMapper commentMapper, CommentRepository commentRepository, PostRepository postRepository) {
        this.commentMapper = commentMapper;
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    /**
     * Retrieves all comments associated with a specific post.
     *
     * @param postId the unique identifier of the post
     * @return a list of {@link CommentDto} objects representing the comments of the specified post
     * @throws ResponseStatusException if the post with the specified ID is not found
     */
    public List<CommentDto> getComments(Long postId){
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException());
        List<Comment> postComments = commentRepository.findByPost(post);
        return commentMapper.toDtos(postComments);
    }

    /**
     * Creates a new comment.
     *
     * @param commentDto the data transfer object containing the details of the comment to be created
     * @return the created {@link CommentDto}
     */
    public ResponseEntity<MessageResponse> createComment(CommentDto commentDto){
        commentRepository.save(commentMapper.toEntity(commentDto));
        return ResponseEntity.ok(new MessageResponse("Comment posted successfully!"));
    }
}
