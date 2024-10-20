package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.domain.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for {@link Topic} entities.
 * <p>
 * This interface provides methods for performing CRUD operations on topics.
 * </p>
 */
@Repository
public interface TopicRepository extends JpaRepository<Topic, Long>  {
}
