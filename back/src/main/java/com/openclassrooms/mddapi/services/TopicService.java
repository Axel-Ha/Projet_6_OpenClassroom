package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.domain.entity.Topic;
import com.openclassrooms.mddapi.repository.TopicRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {

    public final TopicRepository topicRepository;

    public TopicService(TopicRepository topicRepository) {this.topicRepository = topicRepository;}

    public List<Topic> findAll() {
        return this.topicRepository.findAll();
    }

    public Topic getById(Long id) {
        return this.topicRepository.findById(id).orElse(null);
    }

}
