package com.sockets.chatapp.repository;

import com.sockets.chatapp.model.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class MessageRepositoryTest {

    @Autowired
    private MessageRepository messageRepository;

    private Message message;

    @BeforeEach
    void setUp() {
        // Ensure the database is clean before each test
        messageRepository.deleteAll();

        // Setup a message to be used by the tests
        message = new Message(
                null,
                "senderId",
                "receiverId",
                "Hello, this is a test message",
                LocalDateTime.now()
        );
        messageRepository.save(message);
    }

    @Test
    void shouldFindMessagesBySenderIdAndReceiverId() {
        // Act
        List<Message> messages = messageRepository.findBySenderIdAndReceiverId(message.getSender(), message.getReceiver());

        // Assert
        assertThat(messages).hasSize(1);
        assertThat(messages).extracting(Message::getContent).containsExactly("Hello, this is a test message");
    }

    @Test
    void shouldReturnEmptyListIfNoMessagesExist() {
        // Act
        List<Message> messages = messageRepository.findBySenderIdAndReceiverId("nonExistingSenderId", "nonExistingReceiverId");

        // Assert
        assertThat(messages).isEmpty();
    }

    // Additional tests could include checking for messages in the other direction (receiver to sender),
    // updating a message, and deleting a message.
}
