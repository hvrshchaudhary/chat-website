package com.sockets.chatapp.dto;

import java.time.LocalDateTime;

public class MessageDTO {

    private String senderUsername;
    private String receiverUsername;
    private String content;
    private LocalDateTime timestamp;

    // No-argument constructor
    public MessageDTO() {
    }

    // Constructor
    public MessageDTO(String senderUsername, String receiverUsername, String content, LocalDateTime timestamp) {
        this.senderUsername = senderUsername;
        this.receiverUsername = receiverUsername;
        this.content = content;
        this.timestamp = timestamp;
    }

    // Getters and setters
    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }

    public void setReceiverUsername(String receiverUsername) {
        this.receiverUsername = receiverUsername;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "MessageDTO{" +
                "senderUsername='" + senderUsername + '\'' +
                ", receiverUsername='" + receiverUsername + '\'' +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
