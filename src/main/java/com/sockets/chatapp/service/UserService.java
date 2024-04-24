package com.sockets.chatapp.service;

import com.sockets.chatapp.dto.OnlineUserDTO;
import com.sockets.chatapp.model.User;

import java.util.List;
import java.util.Set;


public interface UserService {

    // Method to register/create a new user with a hashed password
    User registerUser(User user);

    // Method to update an existing user's information
    User updateUser(User user);

    // Method to retrieve a user by username
    User findByUsername(String username);

    // Method to delete a user by ID
    void deleteUser(String userId);

    // Method to find all users
    List<User> findAllUsers();

    // Method to retrieve all online users
    Set<OnlineUserDTO> findAllOnlineUsers();

    // Method to mark a user as online
    void markUserOnline(String username);

    // Method to mark a user as offline
    void markUserOffline(String username);
}