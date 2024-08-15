package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.domain.dto.UserDto;
import com.openclassrooms.mddapi.domain.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDto userToUserDto(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        } else {
            UserDto userDto = new UserDto();
            userDto.setUser_id(userEntity.getUserId());
            userDto.setEmail(userEntity.getEmail());
            userDto.setUsername(userEntity.getUsername());
            userDto.setCreated_at(userEntity.getCreatedAt());
            userDto.setUpdated_at(userEntity.getUpdatedAt());
            return userDto;
        }
    }

    public static UserEntity toEntity(UserDto dto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(dto.getUser_id());
        userEntity.setUsername(dto.getUsername());
        userEntity.setEmail(dto.getEmail());
        return userEntity;
    }
}
