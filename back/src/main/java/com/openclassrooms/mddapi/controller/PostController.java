package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.domain.dto.PostDto;
import com.openclassrooms.mddapi.domain.entity.Post;
import com.openclassrooms.mddapi.mapper.PostMapper;
import com.openclassrooms.mddapi.services.PostService;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;

    public PostController(PostService postService, PostMapper postMapper) {
        this.postService = postService;
        this.postMapper = postMapper;
    }

    @GetMapping
    public ResponseEntity<?> getPosts(){
        List<Post> posts = this.postService.findAll();
        return ResponseEntity.ok(this.postMapper.toDto(posts));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findPostById(@PathVariable("id") String id){
        try {
            Post post = this.postService.getById(Long.valueOf(id));
            if (post == null){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(this.postMapper.toDto(post));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping()
    public ResponseEntity<?> create(@Valid @RequestBody PostDto postDto){
        return ResponseEntity.ok(this.postService.createPost(postDto));
    }

}
