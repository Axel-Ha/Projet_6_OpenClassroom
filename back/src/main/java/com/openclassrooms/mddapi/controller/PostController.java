package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.domain.dto.PostDto;
import com.openclassrooms.mddapi.domain.entity.Post;
import com.openclassrooms.mddapi.mapper.PostMapper;
import com.openclassrooms.mddapi.services.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.openclassrooms.mddapi.configuration.SwaggerConfig.NAME_SECURITY_REQUIREMENT;

/**
 * REST controller for managing posts.
 * This controller provides endpoints to handle requests related to {@link Post}
 * entities,
 * including retrieving individual posts, retrieving posts subscribed by a user,
 * and creating new posts.
 *
 * <p>
 * All responses are wrapped in a {@link ResponseEntity} to provide appropriate
 * HTTP status codes and response bodies.
 * </p>
 *
 * @see Post
 * @see PostDto
 * @see PostService
 */
@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;

    public PostController(PostService postService, PostMapper postMapper) {
        this.postService = postService;
        this.postMapper = postMapper;
    }

    @Operation(
            summary = "Get all posts ",
            description = "This endpoint is used to retrieve all the posts, and returns the list of posts")
    @ApiResponses(value = {
            @ApiResponse(description = "Returns the posts", responseCode = "200"),
            @ApiResponse(description = "Unauthorized, The request is missing a valid token ", responseCode = "401"),
    })
    @GetMapping
    @SecurityRequirement(name=NAME_SECURITY_REQUIREMENT)
    public ResponseEntity<?> getPosts(){
        List<Post> posts = this.postService.findAll();
        return ResponseEntity.ok(this.postMapper.toDto(posts));
    }

    @Operation(
            summary = "Get a post by id ",
            description = "This endpoint is used to retrieve a post by its id, and returns the post if it exist")
    @ApiResponses(value = {
            @ApiResponse(description = "Returns the post", responseCode = "200"),
            @ApiResponse(description = "Unauthorized, The request is missing a valid token ", responseCode = "401"),
            @ApiResponse(description = "Not found, the post does not exist ", responseCode = "404"),
    })
    @GetMapping("/{id}")
    @SecurityRequirement(name=NAME_SECURITY_REQUIREMENT)
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
    @Operation(
            summary = "Create a post ",
            description = "This endpoint is used create a post with the information provided by the user")
    @ApiResponses(value = {
            @ApiResponse(description = "The post is created", responseCode = "200"),
            @ApiResponse(description = "Unauthorized, The request is missing a valid token.", responseCode = "401"),
            @ApiResponse(description = "Bad request: The data entered is not valid. ", responseCode = "400"),
    })
    @PostMapping()
    @SecurityRequirement(name=NAME_SECURITY_REQUIREMENT)
    public ResponseEntity<?> create(@Valid @RequestBody PostDto postDto){
        return ResponseEntity.ok(this.postService.createPost(postDto));
    }

}
