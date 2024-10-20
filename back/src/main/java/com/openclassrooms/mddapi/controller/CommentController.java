package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.domain.dto.CommentDto;
import com.openclassrooms.mddapi.services.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.openclassrooms.mddapi.configuration.SwaggerConfig.NAME_SECURITY_REQUIREMENT;

/**
 * REST controller for managing comments.
 * This controller provides endpoints for retrieving and creating comments
 * related to specific posts. It uses {@link CommentService} to interact with
 * the business logic layer.
 *
 * <p>
 * All operations return a {@link ResponseEntity} to indicate the HTTP status
 * and the response body.
 * </p>
 *
 * @see CommentDto
 * @see CommentService
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(
            summary = "Get all comments from a post ",
            description = "This endpoint is used to retrieve all the comments from a post, and returns the list of comments")
    @ApiResponses(value = {
            @ApiResponse(description = "Returns the comments", responseCode = "200"),
            @ApiResponse(description = "Unauthorized, The request is missing a valid token ", responseCode = "401"),
            @ApiResponse(description = "The post does not exist", responseCode = "404")
    })
    @GetMapping("{id}")
    @SecurityRequirement(name=NAME_SECURITY_REQUIREMENT)
    public ResponseEntity<?> getComments(@PathVariable("id") Long id){
        return ResponseEntity.ok(commentService.getComments(id));
    }

    @Operation(
            summary = "Create a comment to a post ",
            description = "This endpoint is used to get create a new comment under a post.")
    @ApiResponses(value = {
            @ApiResponse(description = "Comment Created", responseCode = "200"),
            @ApiResponse(description = "Unauthorized, The request is missing a valid token ", responseCode = "401"),
    })
    @PostMapping()
    @SecurityRequirement(name=NAME_SECURITY_REQUIREMENT)
    public ResponseEntity<?> createComment(@Valid @RequestBody CommentDto comment){
        return ResponseEntity.ok(commentService.createComment(comment));
    }
}
