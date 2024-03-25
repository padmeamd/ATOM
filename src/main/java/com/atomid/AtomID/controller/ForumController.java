package com.atomid.AtomID.controller;

import com.atomid.AtomID.entity.Message;
import com.atomid.AtomID.entity.Topic;
import com.atomid.AtomID.repository.MessageRepository;
import com.atomid.AtomID.repository.TopicRepository;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/topics")
public class ForumController {
    private final TopicRepository topicRepository;
    private final MessageRepository messageRepository;

    public ForumController(TopicRepository topicRepository, MessageRepository messageRepository) {
        this.topicRepository = topicRepository;
        this.messageRepository = messageRepository;
    }

    @PostConstruct
    public void init() {
        // Создание и сохранение тестовых данных
        Topic topic = new Topic("Test Topic");
        topic = topicRepository.save(topic);

        Message message1 = new Message("Author 1", "Test message 1", LocalDateTime.now());
        message1.setTopic(topic);
        messageRepository.save(message1);

        Message message2 = new Message("Author 2", "Test message 2", LocalDateTime.now());
        message2.setTopic(topic);
        messageRepository.save(message2);
    }

    @GetMapping
    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    @GetMapping("/{topicId}/messages")
    public List<Message> getMessagesByTopic(@PathVariable Long topicId) {
        return messageRepository.findByTopicId(topicId);
    }

    @PostMapping
    public Topic createTopic(@RequestBody Topic topic) {
        if (topic.getMessages() == null || topic.getMessages().isEmpty()) {
            throw new IllegalArgumentException("Topic must have at least one message");
        }
        return topicRepository.save(topic);
    }

    @PostMapping("/{topicId}/messages")
    public Message createMessage(@PathVariable Long topicId, @RequestBody Message message) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new IllegalArgumentException("Topic not found with id: " + topicId));
        if (message.getTopic() != null && !message.getTopic().getId().equals(topicId)) {
            throw new IllegalArgumentException("Message topic ID does not match the provided topic ID");
        }
        message.setTopic(topic);
        return messageRepository.save(message);
    }

    @PutMapping("/messages/{messageId}")
    public Message updateMessage(@PathVariable Long messageId, @RequestBody Message updatedMessage) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new IllegalArgumentException("Message not found with id: " + messageId));
        // Проверка авторства сообщения может быть выполнена здесь
        if (!message.getAuthor().equals(updatedMessage.getAuthor())) {
            throw new IllegalArgumentException("Cannot update message authored by another user");
        }
        message.setText(updatedMessage.getText());
        return messageRepository.save(message);
    }

    @DeleteMapping("/messages/{messageId}")
    public void deleteMessage(@PathVariable Long messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new IllegalArgumentException("Message not found with id: " + messageId));
        // Проверка авторства сообщения может быть выполнена здесь
        messageRepository.delete(message);
    }
}
