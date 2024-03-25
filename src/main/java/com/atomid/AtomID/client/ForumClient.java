package com.atomid.AtomID.client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ForumClient {

    private final RestTemplate restTemplate;

    @Autowired
    public ForumClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Получить список топиков
    public ResponseEntity<String> getTopics() {
        return restTemplate.exchange("http://localhost:8080/api/topics", HttpMethod.GET, null, String.class);
    }

    // Получить сообщения в указанном топике
    public ResponseEntity<String> getMessagesInTopic(Long topicId) {
        return restTemplate.exchange("http://localhost:8080/api/topics/" + topicId + "/messages", HttpMethod.GET, null, String.class);
    }

    // Создать топик (с первым сообщением в нем)
    public ResponseEntity<String> createTopic(String title, String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestBody = "{\"title\":\"" + title + "\", \"author\":\"user\", \"text\":\"" + message + "\"}";
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        return restTemplate.exchange("http://localhost:8080/api/topics", HttpMethod.POST, requestEntity, String.class);
    }

    // Создать сообщение в указанном топике
    public ResponseEntity<String> createMessageInTopic(Long topicId, String author, String text) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestBody = "{\"author\":\"" + author + "\", \"text\":\"" + text + "\"}";
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        return restTemplate.exchange("http://localhost:8080/api/topics/" + topicId + "/messages", HttpMethod.POST, requestEntity, String.class);
    }

    // Отредактировать свое сообщение
    public ResponseEntity<String> editMessage(Long messageId, String newText) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestBody = "{\"text\":\"" + newText + "\"}";
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        return restTemplate.exchange("http://localhost:8080/api/messages/" + messageId, HttpMethod.PUT, requestEntity, String.class);
    }

    // Удалить свое сообщение
    public ResponseEntity<String> deleteMessage(Long messageId) {
        return restTemplate.exchange("http://localhost:8080/api/messages/" + messageId, HttpMethod.DELETE, null, String.class);
    }
}
