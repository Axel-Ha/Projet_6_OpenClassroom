package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for {@link UserEntity} entities.
 * Provides methods for performing CRUD operations and additional query methods
 * related to user entities.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    /**
     * Finds a user by their email.
     *
     * @param email the email of the user
     * @return an {@link Optional} containing the found user, or {@link Optional#empty()} if no user found
     */
    Optional<UserEntity> findByEmailOrUsername(String username, String email);

    /**
     * Checks if a user exists with the given email.
     *
     * @param email the email to check
     * @return {@code true} if a user with the given email exists, {@code false} otherwise
     */
    Boolean existsByEmail(String email);

    /**
     * Checks if a user exists with the given username.
     *
     * @param username the username to check
     * @return {@code true} if a user with the given username exists, {@code false} otherwise
     */
    Boolean existsByUsername(String username);
}
