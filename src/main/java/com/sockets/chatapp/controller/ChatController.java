package com.sockets.chatapp.controller;

import com.sockets.chatapp.dto.MessageDTO;
import com.sockets.chatapp.model.Message;
import com.sockets.chatapp.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.handler.annotation.SendTo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

@Controller
public class ChatController {

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    private MessageService messageService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.send")
    @SendTo("/topic/public")
    public void sendMessage(@Payload MessageDTO messageDTO) {
        logger.info("Received message: {}", messageDTO);

        // Validate messageDTO fields
        if (messageDTO.getSenderUsername() == null || messageDTO.getReceiverUsername() == null) {
            logger.error("Sender or receiver is null. Aborting message sending.");
            throw new IllegalArgumentException("Sender and receiver must not be null.");
        }

        // Convert MessageDTO to Message entity
        Message message = new Message();
        message.setSender(messageDTO.getSenderUsername());
        message.setReceiver(messageDTO.getReceiverUsername());
        message.setContent(messageDTO.getContent());
        message.setTimestamp(messageDTO.getTimestamp() != null ? messageDTO.getTimestamp() : LocalDateTime.now());

        logger.info("Saving message: {}", message);

        // Save the message using MessageService
        messageService.sendMessage(message);

        // Send the message to the receiver
        messagingTemplate.convertAndSendToUser(
                messageDTO.getReceiverUsername(), // Username of the receiver
                "/queue/messages", // Subscribed destination in the client
                messageDTO); // The actual message to be sent
        System.out.println("Sending message to user: " + messageDTO.getReceiverUsername());

        logger.info("Message sent to user: {} via WebSocket", messageDTO.getReceiverUsername());
    }
}
