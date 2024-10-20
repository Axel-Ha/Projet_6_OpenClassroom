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
     * @param username the username of the user
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

    /**
     * Checks if another user exists with the given email, excluding the user with the specified ID.
     *
     * @param email the email to check
     * @param id the ID of the user to exclude from the check
     * @return {@code true} if another user with the given email exists, {@code false} otherwise
     */
    Boolean existsByEmailAndIdNot(String email, Long id);

    /**
     * Checks if another user exists with the given username, excluding the user with the specified ID.
     *
     * @param username the username to check
     * @param id the ID of the user to exclude from the check
     * @return {@code true} if another user with the given username exists, {@code false} otherwise
     */
    Boolean existsByUsernameAndIdNot(String username, Long id);
}
