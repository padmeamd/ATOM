package com.atomid.AtomID.service;

import com.atomid.AtomID.entity.Message;
import com.atomid.AtomID.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message createMessage(Message message) {
        return messageRepository.save(message);
    }

    public Optional<Message> updateMessage(Long id, Message updatedMessage) {
        Optional<Message> optionalMessage = messageRepository.findById(id);
        if (optionalMessage.isPresent()) {
            Message message = optionalMessage.get();
            message.setAuthor(updatedMessage.getAuthor());
            message.setText(updatedMessage.getText());
            return Optional.of(messageRepository.save(message));
        } else {
            // Handle error if message not found
            return Optional.empty();
        }
    }

    public void deleteMessage(Long id) {
        messageRepository.deleteById(id);
    }
}
