package com.openclassrooms.mddapi.security.services;

import com.openclassrooms.mddapi.domain.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.repository.UserRepository;

/**
 * Implementation of {@link UserDetailsService} for loading user-specific data.
 * <p>
 * This service is used by Spring Security to retrieve user details based on the
 * provided username or email.
 * </p>
 *
 * @version 1.0
 * @since 2024-07-22
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Constructs a {@link UserDetailsServiceImpl} with the specified
     * {@link UserRepository}.
     *
     * @param userRepository the repository used to access user data
     */
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads user-specific data by username or email.
     * <p>
     * This method retrieves the user from the repository based on the provided
     * username or email.
     * If the user is found, it returns an instance of {@link UserDetailsImpl}.
     * If the user is not found, it throws a {@link UsernameNotFoundException}.
     * </p>
     *
     * @param email the username or email of the user to be retrieved
     * @return a {@link UserDetails} object containing user details
     * @throws UsernameNotFoundException if no user is found with the provided
     *                                   username or email
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User not found with email: " + email));
        return new UserDetailsImpl(user.getUserId(), user.getUsername(), user.getEmail(), user.getPassword());
    }

}