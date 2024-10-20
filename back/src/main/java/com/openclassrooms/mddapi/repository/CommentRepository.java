package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.domain.entity.Comment;
import com.openclassrooms.mddapi.domain.entity.Post;
import com.openclassrooms.mddapi.domain.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing {@link Comment} entities.
 *
 * Provides methods for retrieving comments from the database.
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * Retrieves a list of comments associated with a specific post.
     *
     * @param post the post for which to retrieve comments
     * @return a list of comments associated with the given post
     */
    List<Comment> findByPost(Post post);
}
