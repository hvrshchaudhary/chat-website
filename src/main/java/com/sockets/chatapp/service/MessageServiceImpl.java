package com.sockets.chatapp.service;

import com.sockets.chatapp.model.Message;
import com.sockets.chatapp.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Message sendMessage(Message message) {
        // Log the message content to verify fields
        System.out.println("Saving Message: " + message);
        if (message.getSender() == null || message.getReceiver() == null) {
            throw new IllegalArgumentException("Sender and receiver must not be null");
        }
        message.setTimestamp(LocalDateTime.now());
        return messageRepository.save(message);
    }


    @Override
    public List<Message> getConversation(String senderId, String receiverId) {
        // Retrieve all messages where the sender and receiver match the provided IDs
        List<Message> sentMessages = messageRepository.findBySenderIdAndReceiverId(senderId, receiverId);
        List<Message> receivedMessages = messageRepository.findBySenderIdAndReceiverId(receiverId, senderId);

        // Combine and sort the messages by timestamp
        List<Message> conversation = sentMessages;
        conversation.addAll(receivedMessages);
        conversation.sort((m1, m2) -> m1.getTimestamp().compareTo(m2.getTimestamp()));

        return conversation;
    }
}