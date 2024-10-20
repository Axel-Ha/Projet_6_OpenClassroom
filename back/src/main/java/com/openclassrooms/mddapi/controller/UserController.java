package com.openclassrooms.mddapi.controller;


import com.openclassrooms.mddapi.domain.dto.UserDto;
import com.openclassrooms.mddapi.services.UserService;
import com.openclassrooms.mddapi.domain.entity.UserEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.openclassrooms.mddapi.configuration.SwaggerConfig.NAME_SECURITY_REQUIREMENT;

/**
 * REST controller for managing users.
 * This controller provides endpoints to handle requests related to {@link UserEntity}
 * entities,
 * including retrieving user details, updating user information, and managing
 * user subscriptions to topics.
 *
 * <p>
 * All operations return a {@link ResponseEntity} to encapsulate the HTTP status
 * code and response body.
 * </p>
 *
 * @see UserDto
 * @see UserService
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Get a user by id ",
            description = "This endpoint is used to retrieve a user by its id, and returns the user if it exist")
    @ApiResponses(value = {
            @ApiResponse(description = "Returns the user", responseCode = "200"),
            @ApiResponse(description = "Unauthorized, The request is missing a valid token ", responseCode = "401"),
            @ApiResponse(description = "Not found, the user does not exist ", responseCode = "404"),
    })
    @GetMapping("{id}")
    @SecurityRequirement(name=NAME_SECURITY_REQUIREMENT)
    public ResponseEntity<?> getUser(@PathVariable("id") Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @Operation(
            summary = "Update a user by id ",
            description = "This endpoint is used to update a user with the id provided, and returns the new user information")
    @ApiResponses(value = {
            @ApiResponse(description = "The user is updated", responseCode = "200"),
            @ApiResponse(description = "Unauthorized, The request is missing a valid token ", responseCode = "401"),
            @ApiResponse(description = "Not found, the user does not exist ", responseCode = "404"),
    })
    @PutMapping("{id}")
    @SecurityRequirement(name=NAME_SECURITY_REQUIREMENT)
    public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @Valid @RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.updateUser(id, userDto));
    }

    @Operation(
            summary = "Gets all subscribed topics ",
            description = "This endpoint is used to get all the topics the user has subscribed and returns the list of topics.")
    @ApiResponses(value = {
            @ApiResponse(description = "Returns the list of subscribed topics.", responseCode = "200"),
            @ApiResponse(description = "Unauthorized, The request is missing a valid token ", responseCode = "401"),
            @ApiResponse(description = "Not found, the user does not exist ", responseCode = "404"),
    })
    @GetMapping("{id}/subscriptions")
    @SecurityRequirement(name=NAME_SECURITY_REQUIREMENT)
    public ResponseEntity<?> getSubscriptions(@PathVariable("id") Long id){
        return ResponseEntity.ok(userService.getSubscriptions(id));
    }

    @Operation(
            summary = "Subscribe to a topic ",
            description = "This endpoint is used to subscribe a user to a topic")
    @ApiResponses(value = {
            @ApiResponse(description = "Subscribe the user to the topic", responseCode = "200"),
            @ApiResponse(description = "Unauthorized, The request is missing a valid token ", responseCode = "401"),
            @ApiResponse(description = "Not found, The user or the topic does not exist ", responseCode = "404"),
    })
    @PutMapping("{id}/subscribe/{idTopic}")
    @SecurityRequirement(name=NAME_SECURITY_REQUIREMENT)
    public ResponseEntity<?> subscribe(@PathVariable("id") Long id, @PathVariable("idTopic") Long idTopic){
        userService.subscribe(id,idTopic);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Unsubscribe to a topic ",
            description = "This endpoint is used to unsubscribe a user to a topic")
    @ApiResponses(value = {
            @ApiResponse(description = "Unsubscribe the user to the topic", responseCode = "200"),
            @ApiResponse(description = "Unauthorized, The request is missing a valid token ", responseCode = "401"),
            @ApiResponse(description = "Not found, The user or the topic does not exist ", responseCode = "404"),
    })
    @DeleteMapping("{id}/subscribe/{idTopic}")
    @SecurityRequirement(name=NAME_SECURITY_REQUIREMENT)
    public ResponseEntity<?> unSubscribe(@PathVariable("id") Long id, @PathVariable("idTopic") Long idTopic){
        userService.unSubscribe(id,idTopic);
        return ResponseEntity.ok().build();
    }
}
