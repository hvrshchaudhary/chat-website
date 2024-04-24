package com.sockets.chatapp.repository;

import com.sockets.chatapp.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {

    // Custom query to find messages sent from one user to another
    @Query("{ 'sender' : ?0, 'receiver' : ?1 }")
    List<Message> findBySenderIdAndReceiverId(String senderId, String receiverId);

}
