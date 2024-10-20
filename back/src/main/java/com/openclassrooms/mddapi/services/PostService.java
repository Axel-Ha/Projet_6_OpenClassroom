package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.domain.dto.PostDto;
import com.openclassrooms.mddapi.domain.entity.Post;
import com.openclassrooms.mddapi.exceptions.NotFoundException;
import com.openclassrooms.mddapi.mapper.PostMapper;
import com.openclassrooms.mddapi.payload.response.MessageResponse;
import com.openclassrooms.mddapi.repository.PostRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for managing posts in the application.
 * <p>
 * Provides methods to retrieve and create posts.
 * Interacts with {@link PostRepository}
 * </p>
 */
@Service
public class PostService {

    public final PostRepository postRepository;
    public final PostMapper postMapper;

    /**
     * Constructs a new {@link PostService} with the specified repositories and
     * mapper.
     *
     * @param postRepository the repository for managing posts
     * @param postMapper     the mapper to convert between {@link Post} and
     *                       {@link PostDto}
     */
    public PostService(PostRepository postRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }
    /**
     * Retrieves a post by its unique identifier.
     *
     * @param id the unique identifier of the post
     * @throws NotFoundException if no post is found with the given id
     * @return a {@link Post} representing the requested post
     */
    public Post getById(Long id){
      return this.postRepository.findById(id).orElseThrow(() -> new NotFoundException());
    }

    /**
     * Retrieves a list of post.
     *
     * @return a {@link Post} representing the requested post
     */
    public List<Post> findAll(){
        return this.postRepository.findAll();
    }

    /**
     * Creates a new post with the provided data.
     *
     * @param postDto the data transfer object containing the post details
     * @return a {@link PostDto} representing the created post
     */
    public ResponseEntity<MessageResponse> createPost(PostDto postDto){
        postRepository.save(postMapper.toEntity(postDto));
        return ResponseEntity.ok(new MessageResponse("Post created successfully!"));
    }
}
