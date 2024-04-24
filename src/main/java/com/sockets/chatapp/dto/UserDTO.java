package com.sockets.chatapp.dto;

public class UserDTO {

    private String username;

    // Constructor
    public UserDTO(String username) {
        this.username = username;
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}