package com.sockets.chatapp.controller;

import com.sockets.chatapp.dto.MessageDTO;
import com.sockets.chatapp.model.Message;
import com.sockets.chatapp.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class ChatController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.send")
    public void sendMessage(@Payload MessageDTO messageDTO) {
        // Convert MessageDTO to Message entity
        Message message = new Message();
        message.setSender(messageDTO.getSenderUsername());
        message.setReceiver(messageDTO.getReceiverUsername());
        message.setContent(messageDTO.getContent());

        // Save the message using MessageService
        messageService.sendMessage(message);

        // Send the message to the receiver
        messagingTemplate.convertAndSendToUser(
                messageDTO.getReceiverUsername(), // Username of the receiver
                "/queue/messages", // Subscribed destination in the client
                messageDTO); // The actual message to be sent
    }
}
