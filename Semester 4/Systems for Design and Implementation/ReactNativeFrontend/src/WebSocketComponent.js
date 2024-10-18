import React, { Component, useContext, useEffect, useState } from "react";
import { Alert } from 'react-native';
import { CharacterContext } from './CharacterContext.js';

const WebSocketComponent = () => {
  const [ws, setWs] = useState(null);
  const { characters, setCharacters} = useContext(CharacterContext);

  useEffect(() => {
    const socket = new WebSocket('wss://backendmpp-vsrjvd47ea-od.a.run.app');

    socket.onopen = () => {
      console.log('WebSocket connection established');
    };

    socket.onmessage = (event) => {
      const data = JSON.parse(event.data);
      setCharacters(data);
      //console.log('Received message from server:', data);
    };

    socket.onerror = (error) => {
      console.error('WebSocket error:', error);
      alert('WebSocket Error. An error occurred with the WebSocket connection.');
      socket.close(); //chagned here
    };

    socket.onclose = () => {
      console.log('WebSocket connection closed');
    };

    setWs(socket);

    return () => {
      if (socket.readyState === WebSocket.OPEN || socket.readyState === WebSocket.CONNECTING) {
        socket.close();
      }
    };
  }, []);

  return null;
};

export default WebSocketComponent;
