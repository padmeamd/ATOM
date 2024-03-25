package com.atomid.AtomID.client;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootApplication
public class ForumClientApplication implements CommandLineRunner {

    private final ForumClient forumClient;

    public ForumClientApplication(ForumClient forumClient) {
        this.forumClient = forumClient;
    }

    public static void main(String[] args) {
        SpringApplication.run(ForumClientApplication.class, args);
    }

    @Override
    public void run(String... args) {
        // Пример использования клиента для получения списка топиков
        ResponseEntity<String> response = forumClient.getTopics();
        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("Список топиков: " + response.getBody());
        } else {
            System.out.println("Ошибка при получении списка топиков: " + response.getStatusCodeValue());
        }

    }
}
