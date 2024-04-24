package com.sockets.chatapp.service;

import com.sockets.chatapp.model.User;
import com.sockets.chatapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("1", "userTest", "password");
    }

    @Test
    void registerUser_ShouldEncodePasswordAndSaveUser() {
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User savedUser = userService.registerUser(user);

        assertThat(savedUser.getPassword()).isEqualTo("encodedPassword");
        verify(userRepository).save(savedUser);
        verify(passwordEncoder).encode(user.getPassword());
    }

    @Test
    void updateUser_ShouldFindUserAndUpdate() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedNewPassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        user.setPassword("newPassword");
        User updatedUser = userService.updateUser(user);

        assertThat(updatedUser.getPassword()).isEqualTo("encodedNewPassword");
        verify(userRepository).save(updatedUser);
        verify(passwordEncoder).encode("newPassword");
    }

    @Test
    void updateUser_ShouldThrowExceptionWhenUserNotFound() {
        when(userRepository.findById("nonExistingId")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.updateUser(new User("nonExistingId", "", "")))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("User not found with id: nonExistingId");
    }

    @Test
    void findByUsername_ShouldReturnUser() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        User foundUser = userService.findByUsername(user.getUsername());

        assertThat(foundUser).isEqualTo(user);
        verify(userRepository).findByUsername(user.getUsername());
    }

    @Test
    void findByUsername_ShouldThrowExceptionWhenUserNotFound() {
        when(userRepository.findByUsername("nonExistingUsername")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.findByUsername("nonExistingUsername"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("User not found with username: nonExistingUsername");
    }

    @Test
    void deleteUser_ShouldCallDeleteOnRepository() {
        doNothing().when(userRepository).deleteById(anyString());

        userService.deleteUser(user.getId());

        verify(userRepository).deleteById(user.getId());
    }
}
