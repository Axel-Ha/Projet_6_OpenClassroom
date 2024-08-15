package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.domain.dto.UserDto;
import com.openclassrooms.mddapi.domain.entity.UserEntity;
import com.openclassrooms.mddapi.mapper.UserMapper;
import com.openclassrooms.mddapi.repository.UserRepository;
import jakarta.ws.rs.NotFoundException;
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
public class UserService {

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
        UserEntity user = userRepository.findById(id).orElseThrow(NotFoundException::new);
        return userMapper.toDto(user);
    }

}