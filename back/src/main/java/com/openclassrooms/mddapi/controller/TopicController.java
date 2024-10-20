package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.domain.entity.Topic;
import com.openclassrooms.mddapi.services.TopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.openclassrooms.mddapi.configuration.SwaggerConfig.NAME_SECURITY_REQUIREMENT;

/**
 * Controller for managing topics.
 * This controller provides endpoints to handle requests related to
 * {@link Topic} entities.
 *
 * @see TopicService
 */
@RestController
@RequestMapping("/api/topic")
public class TopicController {
    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @Operation(
            summary = "Get all topics ",
            description = "This endpoint is used to retrieve all the topics, and returns the list of topics")
    @ApiResponses(value = {
            @ApiResponse(description = "Returns the topics", responseCode = "200"),
            @ApiResponse(description = "Unauthorized, The request is missing a valid token ", responseCode = "401"),
    })
    @GetMapping()
    @SecurityRequirement(name=NAME_SECURITY_REQUIREMENT)
    public List<Topic> getTopics(){
        return this.topicService.findAll();
    }

    @Operation(
            summary = "Get a topic by id ",
            description = "This endpoint is used to retrieve a topic by its id, and returns the topic if it exist")
    @ApiResponses(value = {
            @ApiResponse(description = "Returns the topic", responseCode = "200"),
            @ApiResponse(description = "Unauthorized, The request is missing a valid token ", responseCode = "401"),
            @ApiResponse(description = "Not found, the topic does not exist ", responseCode = "404"),
    })
    @GetMapping("{id}")
    @SecurityRequirement(name=NAME_SECURITY_REQUIREMENT)
    public Topic getTopic(@PathVariable("id") String id){
        return this.topicService.getById(Long.valueOf(id));
    }
}
