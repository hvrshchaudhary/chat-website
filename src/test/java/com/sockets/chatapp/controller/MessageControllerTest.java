package com.sockets.chatapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sockets.chatapp.model.Message;
import com.sockets.chatapp.service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class MessageControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MessageService messageService;

    @InjectMocks
    private MessageController messageController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(messageController).build();
    }

    @Test
    void sendMessage_ShouldReturnSentMessage() throws Exception {
        Message message = new Message("1", "senderId", "receiverId", "Hello", LocalDateTime.now());
        given(messageService.sendMessage(any(Message.class))).willReturn(message);

        mockMvc.perform(post("/api/messages/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(message)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sender").value(message.getSender()))
                .andExpect(jsonPath("$.receiver").value(message.getReceiver()))
                .andExpect(jsonPath("$.content").value(message.getContent()));
    }

    @Test
    void getConversation_ShouldReturnConversationList() throws Exception {
        List<Message> conversation = Arrays.asList(
                new Message("1", "senderId", "receiverId", "Hello", LocalDateTime.now()),
                new Message("2", "receiverId", "senderId", "Hi", LocalDateTime.now().plusMinutes(1))
        );
        given(messageService.getConversation("senderId", "receiverId")).willReturn(conversation);

        mockMvc.perform(get("/api/messages/conversation")
                        .param("senderId", "senderId")
                        .param("receiverId", "receiverId"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(conversation.size()))
                .andExpect(jsonPath("$.[0].sender").value(conversation.get(0).getSender()))
                .andExpect(jsonPath("$.[1].content").value(conversation.get(1).getContent()));
    }

    // Note: You can add more tests for additional endpoints as needed.
}
