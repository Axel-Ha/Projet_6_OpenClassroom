package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.domain.entity.Topic;
import com.openclassrooms.mddapi.exceptions.NotFoundException;
import com.openclassrooms.mddapi.repository.TopicRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for managing topics in the app.
 *<p>
 * Provides methods to retrieve topics.
 * Interacts with {@link TopicRepository}
 *</p>
 */
@Service
public class TopicService {

    public final TopicRepository topicRepository;
    /**
     * Constructs a new {@code TopicService} with the specified
     * {@code TopicRepository}.
     *
     * @param topicRepository the repository used to manage topics
     */
    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    /**
     * Retrieves all topics.
     *
     * @return a list of {@link Topic} objects
     */
    public List<Topic> findAll() {
        return this.topicRepository.findAll();
    }
    /**
     * Retrieves a topic by its ID.
     *
     * @param id the ID of the topic to retrieve
     * @return the {@link Topic} with the specified ID
     * @throws NotFoundException if the topic with the specified ID is not found
     */
    public Topic getById(Long id) {
        return topicRepository.findById(id).orElseThrow(() -> new NotFoundException());
    }

}
