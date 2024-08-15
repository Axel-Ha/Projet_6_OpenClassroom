package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.domain.dto.UserDto;
import com.openclassrooms.mddapi.domain.entity.UserEntity;
import com.openclassrooms.mddapi.mapper.UserMapper;
import com.openclassrooms.mddapi.repository.UserRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collections;

@Slf4j
@Data
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional(readOnly = true)
    public UserDto getUserById(@PathVariable final Long id){
        // Find the user by their ID in the repository
        // Map the UserEntity to UserDto using userMapper if the user is found
        return userRepository.findById(id).map(userMapper::userToUserDto).orElseThrow();
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        try{
            // Find the user by email in the repository
            UserEntity userEntity = userRepository.findByEmail(email)
                    // Throw an exception if the user is not found
                    .orElseThrow(() -> new UsernameNotFoundException("User Not found"));
            // Create a GrantedAuthority object for the user's role
            GrantedAuthority authority = new SimpleGrantedAuthority(userEntity.getRole());
            // Return a User object containing the user email, password, and authorities
            return new User(userEntity.getEmail(), userEntity.getPassword(), Collections.singletonList(authority));
//        }
//        catch (Exception e){
//            throw new UserErrorException("Error loading user by username", e);
//        }

    }

}