package com.atomid.AtomID.service;

import com.atomid.AtomID.entity.Message;
import com.atomid.AtomID.entity.Topic;
import com.atomid.AtomID.repository.MessageRepository;
import com.atomid.AtomID.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final TopicRepository topicRepository;


    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public List<Message> getMessagesByTopic(Long topicId) {
        return messageRepository.findByTopicId(topicId);
    }

    public Message createMessage(Long topicId, Message message) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new IllegalArgumentException("Topic not found with id: " + topicId));

        if (message.getAuthor() == null || message.getText() == null) {
            throw new IllegalArgumentException("Author and text are required fields for a message");
        }

        message.setCreatedAt(LocalDateTime.now());
        message.setTopic(topic);
        return messageRepository.save(message);
    }

        public Message updateMessage(Long id, Message updatedMessage) {

            Message message = messageRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Message not found with id: " + id));
            if (!message.getAuthor().equals(updatedMessage.getAuthor())) {
                throw new IllegalArgumentException("Cannot update message authored by another user");
            }
            message.setText(updatedMessage.getText());
            Optional<Message> optionalMessage = messageRepository.findById(id);
            if (optionalMessage.isPresent()) {
                Message newMessage = optionalMessage.get();
                newMessage.setAuthor(updatedMessage.getAuthor());
                newMessage.setText(updatedMessage.getText());
                return messageRepository.save(newMessage);
            }
            return null;
        }

    public void deleteMessage(Long id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Message not found with id: " + id));
        messageRepository.deleteById(id);
    }
}
