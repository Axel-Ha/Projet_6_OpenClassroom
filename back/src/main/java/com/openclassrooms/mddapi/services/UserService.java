package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.domain.dto.UserDto;
import com.openclassrooms.mddapi.domain.entity.Topic;
import com.openclassrooms.mddapi.domain.entity.UserEntity;
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

@Slf4j
@Data
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TopicRepository topicRepository;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, UserMapper userMapper, TopicRepository topicRepository, UserDetailsServiceImpl userDetailsService, JwtService jwtService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.topicRepository = topicRepository;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    @Transactional(readOnly = true)
    public UserDto getUserById(final Long id) {
        // Find the user by their ID in the repository
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with ID: " + id));
        return userMapper.toDto(user);
    }

    public ResponseEntity<?> updateUser(Long id, UserDto userDto){
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with ID: " + id));

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


    @Transactional(readOnly = true)
    public List<Topic> getSubscriptions(Long id) {
        // Find the user and get their subscriptions
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with ID: " + id));
        return user.getSubscriptions();
    }

    public void subscribe(Long idUser, Long idTopic) {
        // Find the user and topic
        UserEntity user = userRepository.findById(idUser)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with ID: " + idUser));
        Topic topic = topicRepository.findById(idTopic)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Topic not found with ID: " + idTopic));

        // Check if the user is already subscribed
        boolean isSubscribed = user.getSubscriptions().stream()
                .anyMatch(t -> t.getId().equals(idTopic));
        if (isSubscribed) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is already subscribed to topic with ID: " + idTopic);
        }

        // Subscribe the user to the topic
        user.getSubscriptions().add(topic);
        userRepository.save(user);
    }

    public void unSubscribe(Long idUser, Long idTopic) {
        // Find the user and topic
        UserEntity user = userRepository.findById(idUser)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with ID: " + idUser));
        topicRepository.findById(idTopic)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Topic not found with ID: " + idTopic));

        // Check if the user is subscribed to the topic
        boolean isSubscribed = user.getSubscriptions().stream()
                .anyMatch(t -> t.getId().equals(idTopic));
        if (!isSubscribed) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not subscribed to topic with ID: " + idTopic);
        }

        // Remove the subscription with the specified idTopic from the user's subscriptions
        user.setSubscriptions(
                user.getSubscriptions().stream()
                        .filter(t -> !t.getId().equals(idTopic))
                        .collect(Collectors.toList()));
        userRepository.save(user);
    }
}
