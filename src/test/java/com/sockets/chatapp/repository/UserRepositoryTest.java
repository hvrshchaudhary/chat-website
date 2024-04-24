package com.sockets.chatapp.repository;

import com.sockets.chatapp.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.dao.DuplicateKeyException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataMongoTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        // Ensure the database is clean before each test
        userRepository.deleteAll();
    }

    @Test
    void shouldSaveAndFindByUsername() {
        // Arrange
        String username = "testUser";
        User user = new User(null, username, "password123");
        userRepository.save(user);

        // Act
        Optional<User> foundUser = userRepository.findByUsername(username);

        // Assert
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo(username);
    }

    @Test
    void shouldNotFindUserIfNotExists() {
        // Act
        Optional<User> foundUser = userRepository.findByUsername("nonExistingUser");

        // Assert
        assertThat(foundUser).isNotPresent();
    }

    // Additional tests could include saving a user with an existing username to test for duplicates,
    // updating a user, and deleting a user.

}
