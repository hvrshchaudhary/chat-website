package com.sockets.chatapp.repository;

import com.sockets.chatapp.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    // Custom query method to find a user by their username
    Optional<User> findByUsername(String username);

    // Additional custom queries can be added as needed
}