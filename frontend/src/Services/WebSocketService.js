import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';

const SERVER_URL = process.env.REACT_APP_BASE_URL;
const WEBSOCKET_TOPIC = '/user/queue/messages';

class WebSocketService {
  connect(jwtToken, onMessageReceived) {
    console.log('Connecting to WebSocket with token:', jwtToken);
    const socket = new SockJS(SERVER_URL + '/ws');
    const stompClient = new Client({
      webSocketFactory: () => socket,
      connectHeaders: {
        Authorization: `Bearer ${jwtToken}`,
      },
      debug: (str) => {
              console.log('STOMP: ' + str);
      },
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
    });

    stompClient.onConnect = () => {
      console.log('Connected to WebSocket');
      stompClient.subscribe(WEBSOCKET_TOPIC, message => {
        console.log('Message received from WebSocket:', message);
        onMessageReceived(JSON.parse(message.body));
      });
    };

    stompClient.onStompError = frame => {
      console.error('Broker reported error: ' + frame.headers['message']);
      console.error('Additional details: ' + frame.body);
    };

    stompClient.activate();
    this.stompClient = stompClient;
  }

  disconnect() {
    console.log('Disconnecting from WebSocket');
    if (this.stompClient) {
      this.stompClient.deactivate();
    }
  }

  sendMessage(message) {
    if (this.stompClient && this.stompClient.connected) {
      console.log('Sending message via WebSocket:', message);
      this.stompClient.publish({
        destination: '/app/chat.send',
        body: JSON.stringify(message),
      });
    } else {
      console.error('WebSocket is not connected. Cannot send message.');
    }
  }
}

export default new WebSocketService();
