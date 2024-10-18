const WebSocket = require('ws');

const PORT = 8080;

const ws = new WebSocket(`ws://localhost:${PORT}`);

ws.on('open', () => {
    console.log('Connected to WebSocket server');
});

ws.on('message', (message) => {
    console.log('Received message:', message);
});

ws.on('close', () => {
    console.log('Disconnected from WebSocket server');
});

ws.on('error', (error) => {
    console.error('WebSocket error:', error);
});
