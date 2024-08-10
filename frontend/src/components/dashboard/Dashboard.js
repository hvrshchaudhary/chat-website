import React, { useState, useContext, useEffect, useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '../../context/AuthContext';
import UserList from './UserList';
import ChatWindow from './ChatWindow';
import WebSocketService from '../../Services/WebSocketService';
import './Dashboard.css';

const Dashboard = () => {
  const { logout } = useContext(AuthContext);
  const navigate = useNavigate();
  const [selectedUser, setSelectedUser] = useState(null);
  const { authToken } = useContext(AuthContext);
  const [messages, setMessages] = useState([]);
  const connectionRef = useRef(false);

  useEffect(() => {
    if (authToken && !connectionRef.current) {
      WebSocketService.connect(authToken, handleMessageReceived);
      connectionRef.current = true;
    }

    return () => {
      if (connectionRef.current) {
        WebSocketService.disconnect();
        connectionRef.current = false;
      }
    };
  }, [authToken]);

  const handleMessageReceived = (message) => {
    setMessages(prevMessages => [...prevMessages, message]);
  };

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <div className="dashboard-container">
      <div className="dashboard-header">
        <button onClick={handleLogout} className="logout-button">Logout</button>
      </div>
      <div className="main-section">
        <div className="user-list-section">
          <UserList onUserSelect={setSelectedUser} />
        </div>
        <div className="chat-section">
          <ChatWindow selectedUser={selectedUser} messages={messages} />
        </div>
      </div>
    </div>
  );
};

export default Dashboard;
