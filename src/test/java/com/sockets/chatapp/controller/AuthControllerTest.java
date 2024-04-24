package com.sockets.chatapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sockets.chatapp.dto.AuthRequest;
import com.sockets.chatapp.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthenticationManager authenticationManager;

    // Add more mocks as needed, for example, JwtTokenUtil if used in AuthController

    @Test
    void whenRegisterUser_thenReturns200() throws Exception {
        // Given
        AuthRequest request = new AuthRequest("testUser", "password");

        // When & Then
        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        // Further assertions and verifications...
    }

    @Test
    void whenLoginUser_thenReturns200() throws Exception {
        // Given
        AuthRequest request = new AuthRequest("testUser", "password");

        // Assume necessary mocking of userService, authenticationManager, etc.

        // When & Then
        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        // Further assertions and verifications...
    }
}
