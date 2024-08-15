package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.domain.entity.Topic;
import com.openclassrooms.mddapi.services.TopicService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/topic")
public class TopicController {
    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping()
    public List<Topic> getTopics(){
        return this.topicService.findAll();
    }

    @GetMapping("{id}")
    public Topic getTopic(@PathVariable("id") String id){
        return this.topicService.getById(Long.valueOf(id));
    }
}
