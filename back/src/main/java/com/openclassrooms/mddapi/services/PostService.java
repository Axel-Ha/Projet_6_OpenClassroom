package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.domain.dto.PostDto;
import com.openclassrooms.mddapi.domain.entity.Post;
import com.openclassrooms.mddapi.mapper.PostMapper;
import com.openclassrooms.mddapi.payload.response.MessageResponse;
import com.openclassrooms.mddapi.repository.PostRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    public final PostRepository postRepository;
    public final PostMapper postMapper;

    public PostService(PostRepository postRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    public Post getById(Long id){return this.postRepository.findById(id).orElse(null);}
    public List<Post> findAll(){
        return this.postRepository.findAll();
    }
    public ResponseEntity<MessageResponse> createPost(PostDto postDto){
        System.out.println("ceci est un test "+postDto.getId());
        postRepository.save(postMapper.toEntity(postDto));
        return ResponseEntity.ok(new MessageResponse("Post created successfully!"));
    }
}
