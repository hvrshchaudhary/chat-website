package com.sockets.chatapp.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;

class MessageTest {

    private Message message;
    private final LocalDateTime now = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        message = new Message("1", "senderId", "receiverId", "Hello", now);
    }

    @Test
    void testGetId() {
        assertThat(message.getId()).isEqualTo("1");
    }

    @Test
    void testSetId() {
        message.setId("2");
        assertThat(message.getId()).isEqualTo("2");
    }

    @Test
    void testGetSender() {
        assertThat(message.getSender()).isEqualTo("senderId");
    }

    @Test
    void testSetSender() {
        message.setSender("newSenderId");
        assertThat(message.getSender()).isEqualTo("newSenderId");
    }

    @Test
    void testGetReceiver() {
        assertThat(message.getReceiver()).isEqualTo("receiverId");
    }

    @Test
    void testSetReceiver() {
        message.setReceiver("newReceiverId");
        assertThat(message.getReceiver()).isEqualTo("newReceiverId");
    }

    @Test
    void testGetContent() {
        assertThat(message.getContent()).isEqualTo("Hello");
    }

    @Test
    void testSetContent() {
        message.setContent("Goodbye");
        assertThat(message.getContent()).isEqualTo("Goodbye");
    }

    @Test
    void testGetTimestamp() {
        assertThat(message.getTimestamp()).isEqualTo(now);
    }

    @Test
    void testSetTimestamp() {
        LocalDateTime newTime = LocalDateTime.now();
        message.setTimestamp(newTime);
        assertThat(message.getTimestamp()).isEqualTo(newTime);
    }

    @Test
    void testToString() {
        String messageString = message.toString();
        assertThat(messageString).contains("senderId", "receiverId", "Hello");
        assertThat(messageString).doesNotContain("'id='"); // assuming you don't want the ID in toString
    }

    // Tests for equals and hashCode would be similar to UserTest
}
