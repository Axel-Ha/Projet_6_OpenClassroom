package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.domain.dto.UserDto;
import com.openclassrooms.mddapi.domain.entity.UserEntity;
import org.springframework.stereotype.Component;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;



@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Mapping(target = "created_at", source = "createdAt")
    @Mapping(target = "updated_at", source = "updatedAt")
    public abstract UserDto toDto(UserEntity user);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "subscriptions", ignore = true)
    public abstract UserEntity toEntity(UserDto userDto);
}