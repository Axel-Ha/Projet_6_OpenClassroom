package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for {@link Post} entities.
 * <p>
 * This interface provides methods for performing CRUD operations on posts
 * and retrieving posts associated with specific topics.
 * </p>
 *
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
