package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.domain.dto.CommentDto;
import com.openclassrooms.mddapi.domain.entity.Comment;
import com.openclassrooms.mddapi.domain.entity.Post;
import com.openclassrooms.mddapi.mapper.CommentMapper;
import com.openclassrooms.mddapi.payload.response.MessageResponse;
import com.openclassrooms.mddapi.repository.CommentRepository;
import com.openclassrooms.mddapi.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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

    public List<CommentDto> getComments(Long id){
        Post post = postRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found with ID: " + id));
        List<Comment> postComments = commentRepository.findByPost(post);
        return commentMapper.toDtos(postComments);
    }

    public ResponseEntity<MessageResponse> createComment(CommentDto comment){
        commentRepository.save(commentMapper.toEntity(comment));
        return ResponseEntity.ok(new MessageResponse("Comment posted successfully!"));
    }
}
