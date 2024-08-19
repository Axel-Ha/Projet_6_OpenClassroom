package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.domain.dto.CommentDto;
import com.openclassrooms.mddapi.services.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getComments(@PathVariable("id") Long id){
        return ResponseEntity.ok(commentService.getComments(id));
    }

    @PostMapping()
    public ResponseEntity<?> createComment(@Valid @RequestBody CommentDto comment){
        return ResponseEntity.ok(commentService.createComment(comment));
    }
}
