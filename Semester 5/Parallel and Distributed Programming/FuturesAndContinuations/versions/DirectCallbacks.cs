using System;
using System.IO;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading;
using System.Collections.Generic;

namespace Lab4.versions
{
    class DirectCallbacks
    {
        private string request;
        private static int port;

        public void Run(List<string> servers)
        {
            foreach (var serverName in servers)
            {
                this.DownloadFile("http://" + serverName + "/index.html", serverName);
            }
        }

        private void CreateRequest(string server, string fileName)
        {
            this.request = "GET " + fileName + " HTTP/1.1\r\n" +
                "Host: " + server + "\r\n" +
                "Connection: close\r\n" +
                "\r\n";
        }

        private void DownloadFile(string url, string serverName)
        {
            try
            {
                Uri uri = new Uri(url);
                port = 80;
                string fileName = uri.AbsolutePath;

                CreateRequest(serverName, fileName);
                ManualResetEvent downloadDone = new ManualResetEvent(false);
                StringBuilder responseContent = new StringBuilder();
                Socket socket = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
                IPEndPoint endPoint = new IPEndPoint(Dns.GetHostAddresses(serverName)[0], port);

                socket.BeginConnect(endPoint, new AsyncCallback(ConnectCallback), new { Socket = socket, DownloadDone = downloadDone, ServerName = serverName, ResponseContent = responseContent });
                downloadDone.WaitOne();
            }
            catch (Exception e)
            {
                Console.WriteLine("Error downloading from " + serverName + ": " + e.Message);
            }
        }

        private void ConnectCallback(IAsyncResult ar)
        {
            dynamic state = ar.AsyncState;
            Socket socket = state.Socket;
            ManualResetEvent downloadDone = state.DownloadDone;
            string serverName = state.ServerName;
            StringBuilder responseContent = state.ResponseContent;

            try
            {
                socket.EndConnect(ar);
                Console.WriteLine("Connected to the server.");

                byte[] requestBytes = Encoding.ASCII.GetBytes(request);
                socket.BeginSend(requestBytes, 0, requestBytes.Length, SocketFlags.None,
                    new AsyncCallback(SendCallback), new { Socket = socket, DownloadDone = downloadDone, ServerName = serverName, ResponseContent = responseContent });
            }
            catch (Exception ex)
            {
                Console.WriteLine("Error during connection: " + ex.Message);
                downloadDone.Set();
            }
        }

        private void SendCallback(IAsyncResult ar)
        {
            dynamic state = ar.AsyncState;
            Socket socket = state.Socket;
            ManualResetEvent downloadDone = state.DownloadDone;
            string serverName = state.ServerName;
            StringBuilder responseContent = state.ResponseContent;
            try
            {
                socket.EndSend(ar);
                Console.WriteLine("Request sent.");

                byte[] buffer = new byte[8192];
                socket.BeginReceive(buffer, 0, buffer.Length, SocketFlags.None, new AsyncCallback(ReceiveCallback),
                    new { Socket = socket, Buffer = buffer, DownloadDone = downloadDone, ServerName = serverName, ResponseContent = responseContent });
            }
            catch (Exception ex)
            {
                Console.WriteLine("Error during send: " + ex.Message);
                downloadDone.Set();
            }
        }

        private void ReceiveCallback(IAsyncResult ar)
        {
            dynamic state = ar.AsyncState;
            Socket socket = state.Socket;
            byte[] buffer = state.Buffer;
            ManualResetEvent downloadDone = state.DownloadDone;
            string serverName = state.ServerName;
            StringBuilder responseContent = state.ResponseContent;

            try
            {
                int bytesRead = socket.EndReceive(ar);
                if (bytesRead > 0)
                {
                    string response = Encoding.ASCII.GetString(buffer, 0, bytesRead);
                    responseContent.Append(response);

                    socket.BeginReceive(buffer, 0, buffer.Length, SocketFlags.None, 
                        new AsyncCallback(ReceiveCallback), 
                        new { Socket = socket, Buffer = buffer, DownloadDone = downloadDone, ServerName = serverName, ResponseContent = responseContent });
                }
                else
                {
                    string response = responseContent.ToString();
                    int headerEndIndex = response.IndexOf("\r\n\r\n");
                    
                    if (headerEndIndex != -1)
                    {
                        string headers = response.Substring(0, headerEndIndex);
                        Dictionary<string, string> headerDictionary = ParseHttpHeaders(headers);

                        Console.WriteLine("Response received from: " + serverName);
                        Console.WriteLine($"Headers: {string.Join(", ", headerDictionary)}");
                        
                        if (headerDictionary.ContainsKey("Content-Length"))
                        {
                            Console.WriteLine($"Content-Length: {headerDictionary["Content-Length"]}");
                        }
                        else
                        {
                            Console.WriteLine("Content-Length: Unknown");
                        }

                        string responseBody = response.Substring(headerEndIndex + 4);
                        string fileName = serverName + ".html";
                        File.WriteAllText(fileName, responseBody);
                    }
                    else
                    {
                        Console.WriteLine("Headers not found in the response.");
                    }

                    socket.Close();
                    downloadDone.Set();
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine("Error during receive: " + ex.Message);
                socket.Close();
                downloadDone.Set();
            }
        }

        private Dictionary<string, string> ParseHttpHeaders(string response)
        {
            Dictionary<string, string> headers = new Dictionary<string, string>();
            string[] lines = response.Split(new string[] { "\r\n" }, StringSplitOptions.RemoveEmptyEntries);

            foreach (string line in lines)
            {
                if (line.Contains(":"))
                {
                    string[] parts = line.Split(':');
                    string name = parts[0].Trim();
                    string value = parts[1].Trim();
                    headers[name] = value;
                }
            }

            return headers;
        }
    }
}
