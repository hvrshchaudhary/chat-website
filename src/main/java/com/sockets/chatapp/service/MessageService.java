package com.sockets.chatapp.service;

import com.sockets.chatapp.model.Message;
import java.util.List;

public interface MessageService {

    // Method to send/save a new message
    Message sendMessage(Message message);

    // Method to retrieve the conversation between two users
    List<Message> getConversation(String senderId, String receiverId);

    // Any additional methods as per your chat application's needs could be added here.
}