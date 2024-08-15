package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.domain.entity.UserEntity;
import com.openclassrooms.mddapi.domain.dto.RegisterUserDto;
import org.springframework.stereotype.Component;

@Component
public class RegisterMapper {

    public UserEntity registerDtoToUserEntity(RegisterUserDto registerUserDto) {
        if (registerUserDto == null) {
            return null;
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(registerUserDto.getEmail());
        userEntity.setUsername(registerUserDto.getUsername());
        userEntity.setPassword(registerUserDto.getPassword());

        return userEntity;
    }
}
