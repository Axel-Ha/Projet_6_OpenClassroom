package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.domain.dto.PostDto;
import com.openclassrooms.mddapi.services.PostService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/post")
public class PostController {

    private static final Logger log = LoggerFactory.getLogger(PostController.class);
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping()
    public ResponseEntity<?> create(@Valid @RequestBody PostDto postDto){
        log.info(String.valueOf(postDto));
        return ResponseEntity.ok(postService.createPost(postDto));
    }

}
