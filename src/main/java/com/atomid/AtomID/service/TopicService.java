package com.atomid.AtomID.service;

import com.atomid.AtomID.entity.Topic;
import com.atomid.AtomID.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TopicService {
    private final TopicRepository topicRepository;

    @Autowired
    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    public Optional<Topic> getTopicById(Long id) {
        return topicRepository.findById(id);
    }

    public Topic createTopic(Topic topic) {
        if (topic.getTitle() == null || topic.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Заголовок топика не может быть пустым");
        }

        return topicRepository.save(topic);
    }

    public Optional<Topic> updateTopic(Long id, Topic updatedTopic) {
        Optional<Topic> optionalTopic = topicRepository.findById(id);
        if (optionalTopic.isPresent()) {
            Topic topic = optionalTopic.get();
            topic.setTitle(updatedTopic.getTitle());
            return Optional.of(topicRepository.save(topic));
        } else {
            // Handle error if topic not found
            return Optional.empty();
        }
    }

    public void deleteTopicById(Long id) {
        topicRepository.deleteById(id);
    }
}

