const WebSocket = require('ws');

function createWebSocketServer(server){
    const wss = new WebSocket.Server({ server });

    wss.on('connection', (ws) => {
        console.log('Client connected');
        //console.log(wss);

        ws.on('message', (message) => {
            console.log(`Received message: ${message}`);
        });

        ws.on('close', () => {
            console.log('Client disconnected');
        });
    });

    return wss;
};

module.exports = createWebSocketServer;

