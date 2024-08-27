package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.domain.dto.UserDto;
import com.openclassrooms.mddapi.domain.entity.Topic;
import com.openclassrooms.mddapi.domain.entity.UserEntity;
import com.openclassrooms.mddapi.exceptions.NotFoundException;
import com.openclassrooms.mddapi.exceptions.BadRequestException;
import com.openclassrooms.mddapi.mapper.UserMapper;
import com.openclassrooms.mddapi.payload.response.AuthResponse;
import com.openclassrooms.mddapi.payload.response.MessageResponse;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.security.jwt.JwtService;
import com.openclassrooms.mddapi.security.services.UserDetailsImpl;
import com.openclassrooms.mddapi.security.services.UserDetailsServiceImpl;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for managing users in the app.
 * <p>
 * Provides methods to retrieve and update users.
 * Interacts with {@link UserRepository} and {@link TopicRepository}.
 * </p>
 */
@Slf4j
@Data
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TopicRepository topicRepository;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtService jwtService;
    /**
     * Constructs a new {@link UserService} with the specified dependencies.
     *
     * @param userRepository     the repository to manage users
     * @param topicRepository    the repository to manage topics
     * @param userMapper         the mapper to convert between {@link UserEntity} and
     *                           {@link UserDto}
     * @param jwtService         the service to handle JWT operations
     * @param userDetailsService the service to load user details
     */

    public UserService(UserRepository userRepository, UserMapper userMapper, TopicRepository topicRepository, UserDetailsServiceImpl userDetailsService, JwtService jwtService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.topicRepository = topicRepository;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    /**
     * Retrieves a user by their unique identifier.
     *
     * @param id the id of the user to be retrieved
     * @return the {@link UserDto} corresponding to the specified id
     * @throws ResponseStatusException if no user is found with the given id
     */
    @Transactional(readOnly = true)
    public UserDto getUserById(final Long id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new NotFoundException());

        return userMapper.toDto(user);
    }

    /**
     * Updates a user's information based on the provided {@link UserDto}.
     *
     * @param id      the id of the user to update
     * @param userDto the data transfer object containing the updated user details
     * @return a {@link ResponseEntity} containing the updated {@link AuthResponse}
     * @throws NotFoundException   if no user is found with the given id
     * @throws BadRequestException if the email or username is already in use
     */
    public ResponseEntity<?> updateUser(Long id, UserDto userDto){
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new NotFoundException());


        if (userRepository.existsByEmail(userDto.getEmail()) && !userDto.getEmail().equalsIgnoreCase(user.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Email already in use"));
        }


        if (userRepository.existsByUsername(userDto.getUsername()) && !userDto.getUsername().equalsIgnoreCase(user.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Username already in use"));
        }

        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        userRepository.save(user);

        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(user.getUsername());
        String token = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(
                token,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail()
        ));
    }

    /**
     * Retrieves a list of topics that the user is subscribed to.
     *
     * @param id the unique identifier of the user
     * @return a list of {@link Topic} objects representing the user's subscriptions
     * @throws NotFoundException if no user is found with the given id
     */
    @Transactional(readOnly = true)
    public List<Topic> getSubscriptions(Long id) {
        // Find the user and get their subscriptions
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new NotFoundException());

        return user.getSubscriptions();
    }

    /**
     * Subscribes a user to a specific topic.
     *
     * @param idUser  the unique identifier of the user
     * @param idTopic the unique identifier of the topic to subscribe to
     * @throws NotFoundException   if the user or topic is not found
     * @throws BadRequestException if the user is already subscribed to the topic
     */
    public void subscribe(Long idUser, Long idTopic) {
        // Find the user and topic
        UserEntity user = userRepository.findById(idUser).orElseThrow(() -> new NotFoundException());

        Topic topic = topicRepository.findById(idTopic).orElseThrow(() -> new NotFoundException());

        // Check if the user is already subscribed
        boolean isSubscribed = user.getSubscriptions().stream()
                .anyMatch(t -> t.getId().equals(idTopic));
        if (isSubscribed) {
            throw new BadRequestException();
        }

        // Subscribe the user to the topic
        user.getSubscriptions().add(topic);
        userRepository.save(user);
    }
    /**
     * Unsubscribes a user from a specific topic.
     *
     * @param idUser  the unique identifier of the user
     * @param idTopic the unique identifier of the topic to unsubscribe from
     * @throws NotFoundException   if the user or topic is not found
     * @throws BadRequestException if the user is not subscribed to the topic
     */
    public void unSubscribe(Long idUser, Long idTopic) {
        // Find the user and topic
        UserEntity user = userRepository.findById(idUser).orElseThrow(() -> new NotFoundException());
        topicRepository.findById(idTopic).orElseThrow(() -> new NotFoundException());

        // Check if the user is subscribed to the topic
        boolean isSubscribed = user.getSubscriptions().stream()
                .anyMatch(t -> t.getId().equals(idTopic));
        if (!isSubscribed) {
            throw new BadRequestException();
        }

        // Remove the subscription with the specified idTopic from the user's subscriptions
        user.setSubscriptions(
                user.getSubscriptions().stream()
                        .filter(t -> !t.getId().equals(idTopic))
                        .collect(Collectors.toList()));
        userRepository.save(user);
    }
}
