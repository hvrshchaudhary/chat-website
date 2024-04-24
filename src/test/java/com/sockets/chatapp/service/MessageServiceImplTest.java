package com.sockets.chatapp.service;

import com.sockets.chatapp.model.Message;
import com.sockets.chatapp.repository.MessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageServiceImplTest {

    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private MessageServiceImpl messageService;

    private Message message;

    @BeforeEach
    void setUp() {
        message = new Message("1", "senderId", "receiverId", "Hello", LocalDateTime.now());
    }

    @Test
    void sendMessage_ShouldSetTimestampAndSaveMessage() {
        when(messageRepository.save(any(Message.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Message sentMessage = messageService.sendMessage(message);

        assertThat(sentMessage.getTimestamp()).isNotNull();
        verify(messageRepository).save(sentMessage);
    }

    @Test
    void getConversation_ShouldReturnSortedMessages() {
        LocalDateTime earlier = LocalDateTime.now().minusDays(1);
        LocalDateTime later = LocalDateTime.now();
        Message earlierMessage = new Message("1", "senderId", "receiverId", "Hello earlier", earlier);
        Message laterMessage = new Message("2", "receiverId", "senderId", "Hello later", later);

        when(messageRepository.findBySenderIdAndReceiverId("senderId", "receiverId"))
                .thenReturn(Arrays.asList(earlierMessage));
        when(messageRepository.findBySenderIdAndReceiverId("receiverId", "senderId"))
                .thenReturn(Arrays.asList(laterMessage));

        List<Message> conversation = messageService.getConversation("senderId", "receiverId");

        assertThat(conversation).containsExactly(earlierMessage, laterMessage);
        assertThat(conversation.get(0).getTimestamp()).isBefore(conversation.get(1).getTimestamp());
    }
}
