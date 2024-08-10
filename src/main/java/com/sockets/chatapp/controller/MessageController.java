package com.sockets.chatapp.controller;

import com.sockets.chatapp.model.Message;
import com.sockets.chatapp.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    // Endpoint to send a message
    @PostMapping("/")
    public ResponseEntity<Message> sendMessage(@RequestBody Message message) {
        Message sentMessage = messageService.sendMessage(message);
        return ResponseEntity.ok(sentMessage);
    }

    // Endpoint to fetch the conversation between two users
    @GetMapping("/conversation")
    public ResponseEntity<List<Message>> getConversation(
            @RequestParam String senderId,
            @RequestParam String receiverId) {
        List<Message> conversation = messageService.getConversation(senderId, receiverId);
        return ResponseEntity.ok(conversation);
    }

    // Additional endpoints can be implemented for deleting, updating messages, etc.

}
