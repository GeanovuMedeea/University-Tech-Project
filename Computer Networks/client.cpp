#include <iostream>
#include <string>
#include <winsock2.h>
#include <ws2tcpip.h>

#pragma comment(lib, "ws2_32.lib")

void ReceiveThread(void* lpParam) {
    SOCKET clientSocket = *(SOCKET*)lpParam;
    char buffer[101];
    int bytesRead;
    while (true) {
        bytesRead = recv(clientSocket, buffer, sizeof(buffer) - 1, 0);
        if (bytesRead == SOCKET_ERROR || bytesRead == 0) {
            if (bytesRead == 0) {
                if (strcmp(buffer, "KICKED") == 0) {
                    std::cerr << "You have been kicked from the chat." << std::endl;
                } else {
                    std::cerr << "You have quit the conversation (Socket closed)." << std::endl;
                }
            } else {
                std::cerr << "Socket error from server." << std::endl;
            }
            closesocket(clientSocket);

            return;
        }

        buffer[bytesRead] = '\0';

        if (strcmp(buffer, "KICKED") != 0) {
            std::cout << buffer << std::flush;
        }
    }
}


int main() {
    WSADATA wsaData;
    if (WSAStartup(MAKEWORD(2, 2), &wsaData) != 0) {
        std::cerr << "WSAStartup failed" << std::endl;
        return 1;
    }

    SOCKET clientSocket = socket(AF_INET, SOCK_STREAM, 0);
    if (clientSocket == INVALID_SOCKET) {
        std::cerr << "Socket creation error" << std::endl;
        return 1;
    }

    sockaddr_in serverAddress = {0};
    serverAddress.sin_family = AF_INET;
    serverAddress.sin_port = htons(1112);
    inet_pton(AF_INET, "172.30.112.134", &(serverAddress.sin_addr));

    if (connect(clientSocket, (struct sockaddr*)&serverAddress, sizeof(serverAddress)) == SOCKET_ERROR) {
        std::cerr << "Connection error." << std::endl;
        closesocket(clientSocket);
        WSACleanup();
        return 1;
    }

    std::cout << "Username: ";

    CreateThread(NULL, 0, (LPTHREAD_START_ROUTINE)ReceiveThread, &clientSocket, 0, NULL);

    int bytesSent;

    while (true) {
        std::string userInput;
        std::getline(std::cin, userInput);
        if (userInput.empty()) {
            continue;
        }
        
        userInput += '\n';

        bytesSent = send(clientSocket, userInput.c_str(), userInput.length(), 0);
        if (bytesSent == SOCKET_ERROR) {
            std::cerr << "Disconnected from the server." << std::endl;
            closesocket(clientSocket);
            break;
        }

        bytesSent = 0;
    }

    WSACleanup(); // Moved WSACleanup here

    return 0;
}
