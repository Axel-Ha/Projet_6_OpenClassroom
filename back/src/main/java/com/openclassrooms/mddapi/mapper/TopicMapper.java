package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.domain.dto.TopicDto;
import com.openclassrooms.mddapi.domain.entity.Topic;
import org.springframework.stereotype.Component;

@Component
public class TopicMapper {
    public TopicDto topicToTopicDto(Topic topic){
        if(topic == null){ return null;}
        TopicDto topicDto = new TopicDto();
        topicDto.setName(topic.getName());
        topicDto.setDescription(topic.getDescription());
        topicDto.setName(topic.getName());
        return topicDto;
    }

    public Topic topicDtoToTopic(TopicDto topicDto){
        if(topicDto == null){ return null;}
        Topic topic = new Topic();
        topic.setName(topicDto.getName());
        topic.setDescription(topicDto.getDescription());
        topic.setName(topicDto.getName());
        return topic;
    }
}
