package com.atomid.AtomID.controller;

import com.atomid.AtomID.entity.Message;
import com.atomid.AtomID.entity.Topic;
import com.atomid.AtomID.repository.MessageRepository;
import com.atomid.AtomID.repository.TopicRepository;
import com.atomid.AtomID.service.MessageService;
import com.atomid.AtomID.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/topics")
@RequiredArgsConstructor
public class ForumController {
    private final TopicRepository topicRepository;
    private final MessageRepository messageRepository;
    private final MessageService messageService;
    private final TopicService topicService;

    @PostConstruct
    public void init() {
        Topic topic = Topic.builder()
                .title("Test Topic")
                .build();
        topic = topicRepository.save(topic);

        Message message1 = Message.builder()
                .author("Author 1")
                .text("Message 1")
                .createdAt(LocalDateTime.now())
                .topic(topic)
                .build();
        messageRepository.save(message1);

        Message message2 = Message.builder()
                .author("Author 2")
                .text("Message 2")
                .createdAt(LocalDateTime.now())
                .topic(topic)
                .build();
        messageRepository.save(message2);
    }

    @GetMapping
    public List<Topic> getAllTopics() {
        return topicService.getAllTopics();
    }
    @GetMapping("/{topicId}/messages")
    public List<Message> getMessagesByTopic(@PathVariable Long topicId) {
        return messageService.getMessagesByTopic(topicId);
    }
    @PostMapping
    public Topic createTopic(@RequestBody Topic topic) {
        return topicService.createTopic(topic);
    }
    @PostMapping("/{topicId}/messages")
    public Message createMessage(@PathVariable Long topicId, @RequestBody Message message) {
        return messageService.createMessage(topicId,message);
    }
    @PutMapping("/messages/{messageId}")
    public Message updateMessage(@PathVariable Long messageId, @RequestBody Message updatedMessage) {
        return messageService.updateMessage(messageId,updatedMessage);

    }
    @PutMapping("/messages/{messageId}")
    public Optional<Topic> updateTopic(@PathVariable Long topicId, @RequestBody Topic updatedTopic) {
        return topicService.updateTopic(topicId, updatedTopic);
    }
    @DeleteMapping("/messages/{messageId}")
    public void deleteMessage(@PathVariable Long messageId) {
        messageService.deleteMessage(messageId);
    }
    @DeleteMapping("/{topicId}")
    public void deleteTopic(@PathVariable Long topicId) {
        topicService.deleteTopicById(topicId);
    }
}
