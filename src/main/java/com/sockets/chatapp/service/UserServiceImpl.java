package com.sockets.chatapp.service;

import com.sockets.chatapp.dto.OnlineUserDTO;
import com.sockets.chatapp.model.User;
import com.sockets.chatapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private Set<String> onlineUsers = ConcurrentHashMap.newKeySet();


    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(User user) {
        // Hash the password before saving the user
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        // Fetch the existing user (handle the case where the user doesn't exist appropriately)
        Optional<User> existingUser = userRepository.findById(user.getId());
        if (existingUser.isPresent()) {
            // Update fields as necessary, and handle password hashing if the password is being updated
            User updatedUser = existingUser.get();
            updatedUser.setUsername(user.getUsername());
            // Assume if the password field is not empty, it needs to be updated and hashed
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                updatedUser.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            // More fields can be updated here

            return userRepository.save(updatedUser);
        } else {
            // Throw an exception or handle the absence of the user as needed
            throw new RuntimeException("User not found with id: " + user.getId());
        }
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }

    @Override
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Set<OnlineUserDTO> findAllOnlineUsers() {
        // Convert the usernames to OnlineUserDTO objects
        return onlineUsers.stream()
                .map(OnlineUserDTO::new)
                .collect(Collectors.toSet());
    }
    @Override
    public void markUserOnline(String username) {
        onlineUsers.add(username);
    }

    @Override
    public void markUserOffline(String username) {
        onlineUsers.remove(username);
    }
}
