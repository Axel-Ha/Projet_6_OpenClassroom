package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.domain.dto.UserDto;
import com.openclassrooms.mddapi.domain.entity.UserEntity;
import org.springframework.stereotype.Component;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;



@Mapper(componentModel = "spring")
public abstract class UserMapper {
    /**
     * Converts a {@link UserEntity} entity to a {@link UserDto}.
     * <p>
     * This method maps the {@link UserEntity} entity to a {@link UserDto} while
     * converting the user's
     * subscriptions to a list of subscription IDs. If the subscriptions are null,
     * an empty list
     * is used instead.
     * </p>
     *
     * @param user the {@link UserEntity} entity to convert
     * @return the corresponding {@link UserDto}
     */
    public abstract UserDto toDto(UserEntity user);

    /**
     * Converts a {@link UserDto} to a {@link UserEntity} entity.
     * <p>
     * This method maps the {@link UserDto} to a {@link UserEntity} entity. Certain fields
     * are ignored during
     * the mapping:
     * <ul>
     * <li>{@code createdAt}: The creation timestamp is not set during the
     * conversion.</li>
     * <li>{@code updatedAt}: The last update timestamp is not set during the
     * conversion.</li>
     * <li>{@code password}: The password is not included in the conversion to avoid
     * security issues.</li>
     * <li>{@code subscriptions}: The subscriptions list is not set, as it is not
     * included in the DTO.</li>
     * </ul>
     * </p>
     *
     * @param userDto the {@link UserDto} to convert
     * @return the corresponding {@link UserEntity} entity
     */
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "subscriptions", ignore = true)
    public abstract UserEntity toEntity(UserDto userDto);
}