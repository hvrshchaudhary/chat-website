import React, { useState, useEffect, useContext } from 'react';
import { AuthContext } from '../../context/AuthContext';
import './ChatWindow.css';
import WebSocketService from '../../Services/WebSocketService';

const ChatWindow = ({ selectedUser }) => {
    const [messages, setMessages] = useState([]);
    const { username, authToken } = useContext(AuthContext);
    const [newMessage, setNewMessage] = useState('');
    const endpoint = process.env.REACT_APP_API_BASE_URL;

    useEffect(() => {
        if (selectedUser && authToken) {
            console.log('Fetching conversation for:', username, 'with:', selectedUser);

            fetch(`${endpoint}/messages/conversation?senderId=${username}&receiverId=${selectedUser}`, {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${authToken}`,
                    'Content-Type': 'application/json'
                },
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`HTTP error! status: ${response.status}`);
                    }
                    return response.json();
                })
                .then(data => {
                    console.log('Fetched messages:', data);
                    setMessages(data);
                })
                .catch(error => {
                    console.error('Error fetching messages:', error);
                });

            // Connect to WebSocket
            WebSocketService.connect(authToken, (message) => {
                console.log('New message received via WebSocket:', message);

                // Check if the message is meant for the currently selected user
                if ((message.senderUsername === selectedUser && message.receiverUsername === username) ||
                    (message.senderUsername === username && message.receiverUsername === selectedUser)) {
                    setMessages((prevMessages) => [...prevMessages, message]);
                }
            });

            return () => {
                console.log('Disconnecting from WebSocket');
                WebSocketService.disconnect();
            };
        }
    }, [selectedUser, authToken, username, endpoint]);

    const handleSendMessage = () => {
        if (!newMessage.trim() || !selectedUser || !authToken) return;

        const messageData = {
            senderUsername: username, // Updated field name
            receiverUsername: selectedUser, // Updated field name
            content: newMessage,
            timestamp: new Date().toISOString()
        };

        console.log('Sending message:', messageData);
        WebSocketService.sendMessage(messageData);

        // Add the new message to the messages state immediately
        setMessages((prevMessages) => [...prevMessages, messageData]);
        setNewMessage(''); // Clear input after sending
    };

    return (
        <div className="chat-window">
            {selectedUser ? (
                <>
                    <div className="chat-header">
                        {selectedUser ? selectedUser : 'Select a user'}
                    </div>
                    <div className="messages-list">
                        {messages.map((msg, index) => {
                            return (
                                <div
                                    key={index}
                                    className={`message ${msg.sender === username || msg.senderUsername === username ? 'sent' : 'received'}`}
                                >
                                    <strong>{msg.sender === username || msg.senderUsername === username ? 'You' : msg.sender}: </strong>{msg.content}
                                </div>
                            );
                        })}
                    </div>
                    <div className="message-input">
                        <input
                            type="text"
                            value={newMessage}
                            onChange={(e) => setNewMessage(e.target.value)}
                            placeholder="Type a message..."
                        />
                        <button onClick={handleSendMessage}>Send</button>
                    </div>
                </>
            ) : (
                <div>Please select a user to start chatting.</div>
            )}
        </div>
    );
};

export default ChatWindow;
