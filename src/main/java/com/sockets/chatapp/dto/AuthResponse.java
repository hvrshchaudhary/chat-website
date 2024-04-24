package com.sockets.chatapp.dto;

public class AuthResponse {

    private String token;
    private String type = "Bearer"; // Optional, if you want to explicitly state the token type.

    // Constructors
    public AuthResponse(String token) {
        this.token = token;
    }

    public AuthResponse(String token, String type) {
        this.token = token;
        this.type = type;
    }

    // Getters and Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
