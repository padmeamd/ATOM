package com.atomid.AtomID.service;

import com.atomid.AtomID.entity.Topic;
import com.atomid.AtomID.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TopicService {
    private final TopicRepository topicRepository;

    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    public Optional<Topic> getTopicById(Long id) {
        return topicRepository.findById(id);
    }

    public Topic createTopic(Topic topic) {
        if (topic.getMessages() == null || topic.getMessages().isEmpty()) {
            throw new IllegalArgumentException("Topic must have at least one message!");
        }
        if (topic.getTitle() == null || topic.getTitle().isEmpty()) {
            throw new IllegalArgumentException("The topic title cannot be empty!");
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
            throw new IllegalArgumentException("Topic with id: " + id + " is not found");
        }
    }
    public void deleteTopicById(Long id) {
        topicRepository.deleteById(id);
    }
}

