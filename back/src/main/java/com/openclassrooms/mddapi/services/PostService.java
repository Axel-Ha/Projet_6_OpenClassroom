package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.domain.dto.PostDto;
import com.openclassrooms.mddapi.domain.entity.Post;
import com.openclassrooms.mddapi.mapper.PostMapper;
import com.openclassrooms.mddapi.payload.response.MessageResponse;
import com.openclassrooms.mddapi.repository.PostRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    public final PostRepository postRepository;
    public final PostMapper postMapper;

    public PostService(PostRepository postRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }


    public ResponseEntity<MessageResponse> createPost(PostDto postDto){
        postRepository.save(postMapper.toEntity(postDto));
        return ResponseEntity.ok(new MessageResponse("Post created successfully!"));
    }
}
