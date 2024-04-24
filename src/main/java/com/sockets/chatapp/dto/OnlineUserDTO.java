package com.sockets.chatapp.dto;

public class OnlineUserDTO {

    private String username;

    // Constructor
    public OnlineUserDTO(String username) {
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
