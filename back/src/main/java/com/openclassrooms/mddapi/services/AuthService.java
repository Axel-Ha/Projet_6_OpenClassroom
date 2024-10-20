package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.domain.entity.UserEntity;
import com.openclassrooms.mddapi.payload.request.LoginRequest;
import com.openclassrooms.mddapi.payload.request.RegisterRequest;
import com.openclassrooms.mddapi.payload.response.AuthResponse;
import com.openclassrooms.mddapi.payload.response.MessageResponse;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.security.jwt.JwtService;
import com.openclassrooms.mddapi.security.services.UserDetailsImpl;
import com.openclassrooms.mddapi.security.services.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for authentication operations.
 * <p>
 * This class implements functionalities
 * such as user login and registration. It handles user authentication, token
 * generation,
 * and user registration, and interacts with the {@link UserRepository} to
 * manage user data.
 * </p>
 */
@Slf4j
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;

    /**
     * Constructs an {@link AuthService} with the specified {@link UserRepository},
     * {@link PasswordEncoder}, {@link JwtService}, {@link AuthenticationManager},
     * and {@link UserDetailsServiceImpl}.
     *
     * @param userRepository     the repository used to access user data
     * @param passwordEncoder    the encoder used to hash passwords
     * @param jwtService         the service used to generate and validate JWT
     *                           tokens
     * @param authenticationManager   the manager used to handle authentication
     * @param userDetailsService the service used to load user details
     */
    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtService jwtService,
                       UserDetailsServiceImpl userDetailsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Authenticates a user based on the provided login request.
     * <p>
     * This method validates the user's credentials and, if successful, generates a
     * JWT token
     * and returns it along with user details.
     * </p>
     *
     * @param loginRequest the login request containing the user's username/email
     *                     and password
     * @return a {@link ResponseEntity} containing an {@link AuthResponse} with the
     *         JWT token
     *         and user details if authentication is successful
     */
    @Transactional(readOnly = true)
    public ResponseEntity<?> login(LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwt = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(
                jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail()));
    }



    /**
     * Registers a new user based on the provided registration request.
     * <p>
     * This method checks if the email or username already exists and, if not,
     * creates a new user,
     * hashes their password, and saves the user to the repository.
     * </p>
     *
     * @param registerRequest the registration request containing the user's details
     * @return a {@link ResponseEntity} containing a {@link MessageResponse} with a
     *         success
     *         or error message
     */
    public ResponseEntity<?> register(RegisterRequest registerRequest){
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already taken!"));
        }
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: username is already taken!"));
        }

        UserEntity user = new UserEntity();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));

    }

    /**
     * Retrieves the details of the currently authenticated user.
     * <p>
     * This method extracts the username from the security context, loads the user
     * details,
     * and returns them in an {@link AuthResponse}.
     * </p>
     *
     * @return a {@link ResponseEntity} containing an {@link AuthResponse} with the
     *         user details
     */
    @Transactional(readOnly = true)
    public ResponseEntity<?> me() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(username);
        return ResponseEntity.ok(new AuthResponse(
                "",
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail()));
    }
}
