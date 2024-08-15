package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.domain.dto.LoginUserDto;
import com.openclassrooms.mddapi.domain.dto.RegisterUserDto;
import com.openclassrooms.mddapi.domain.entity.AuthResponse;
import com.openclassrooms.mddapi.domain.entity.UserEntity;
import com.openclassrooms.mddapi.mapper.RegisterMapper;
import com.openclassrooms.mddapi.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static java.rmi.server.LogStream.log;

@Slf4j
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RegisterMapper registerMapper;
    private final UserService userService;
    private final JwtService jwtService;
//    private final UserMapper userMapper;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       RegisterMapper registerMapper,
                       UserService userService,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.registerMapper = registerMapper;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    //Register
    public AuthResponse register(RegisterUserDto registerUserDto){
//        try {
            // Create a new User
            UserEntity userEntity = registerMapper.registerDtoToUserEntity(registerUserDto);
            userEntity.setEmail(registerUserDto.getEmail());
            userEntity.setUsername(registerUserDto.getUsername());

            // Encode password
            userEntity.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));

            /* Get the current DayTime */
            userEntity.setCreatedAt(new Date());
            userEntity.setUpdatedAt(new Date());

            //Save the user in DB
            userRepository.save(userEntity);

            //Load the user details using the email provided in the registration DTO
            UserDetails userDetails = userService.loadUserByUsername(registerUserDto.getEmail());

            //Generate a JWT token for the registered user using their user details
            return new AuthResponse(jwtService.generateToken(userDetails));
//        }
//        catch (Exception e){
//            throw new UserErrorException("User registration failed", e);
//        }
    }

    //login a user
    @Transactional(readOnly = true)
    public AuthResponse login(LoginUserDto loginUserDto){
//        try{
            // Authenticate the user using the provided email and password
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginUserDto.getEmail(),
                            loginUserDto.getPassword()
                    )
            );
            //Load the user details using the email provided in the registration DTO
            UserDetails userDetails = userService.loadUserByUsername(loginUserDto.getEmail());

            // Generate and return a JWT token for the authenticated user
            return new AuthResponse(jwtService.generateToken(userDetails));
//        }
//        catch (Exception e){
//            throw new UserErrorException("User failed login", e);
//        }
    }
}
