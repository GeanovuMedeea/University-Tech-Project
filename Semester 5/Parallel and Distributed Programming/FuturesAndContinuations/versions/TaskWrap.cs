using System;
using System.IO;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;
using System.Collections.Generic;

namespace Lab4.versions
{
    class TaskWrap
    {
        private string request;
        private static int port;

        public void Run(List<string> servers)
        {
            foreach (var serverName in servers)
            {
                DownloadFile("http://" + serverName + "/index.html", serverName);
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

                Socket socket = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);

                Connect(socket, serverName, port).Wait();
                Console.WriteLine("Connected to the server: " + serverName);

                Send(socket, request).Wait();
                Console.WriteLine("Request sent to: " + serverName);

                ReceiveResponse(socket, serverName);
            }
            catch (Exception e)
            {
                Console.WriteLine("Error downloading from " + serverName + ": " + e.Message);
            }
        }

        private Task Connect(Socket socket, string server, int port)
        {
            var tcs = new TaskCompletionSource<bool>();

            socket.BeginConnect(server, port, ar =>
            {
                try
                {
                    socket.EndConnect(ar);
                    tcs.SetResult(true);
                }
                catch (Exception ex)
                {
                    tcs.SetException(ex);
                }
            }, null);

            return tcs.Task;
        }

        private Task Send(Socket socket, string request)
        {
            var tcs = new TaskCompletionSource<bool>();

            byte[] requestBytes = Encoding.ASCII.GetBytes(request);

            socket.BeginSend(requestBytes, 0, requestBytes.Length, SocketFlags.None, ar =>
            {
                try
                {
                    socket.EndSend(ar);
                    tcs.SetResult(true);
                }
                catch (Exception ex)
                {
                    tcs.SetException(ex);
                }
            }, null);

            return tcs.Task;
        }

        private Task ReceiveResponse(Socket socket, string serverName)
        {
            var tcs = new TaskCompletionSource<bool>();

            byte[] buffer = new byte[8192];
            MemoryStream responseStream = new MemoryStream();

            while (true)
            {
                int bytesRead = Receive(socket, buffer).Result;
                if (bytesRead == 0) break;

                responseStream.Write(buffer, 0, bytesRead);
            }

            string response = Encoding.ASCII.GetString(responseStream.ToArray());
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
            
            return tcs.Task;
        }

        private Task<int> Receive(Socket socket, byte[] buffer)
        {
            var tcs = new TaskCompletionSource<int>();

            socket.BeginReceive(buffer, 0, buffer.Length, SocketFlags.None, ar =>
            {
                try
                {
                    int bytesRead = socket.EndReceive(ar);
                    tcs.SetResult(bytesRead);
                }
                catch (Exception ex)
                {
                    tcs.SetException(ex);
                }
            }, null);
            return tcs.Task;
        }

        private Dictionary<string, string> ParseHttpHeaders(string response)
        {
            Dictionary<string, string> headers = new Dictionary<string, string>();
            string[] lines = response.Split(new string[] { "\r\n" }, StringSplitOptions.RemoveEmptyEntries);

            foreach (string line in lines)
            {
                if (line.Contains(":"))
                {
                    int separatorIndex = line.IndexOf(':' );
                    string name = line.Substring(0, separatorIndex).Trim();
                    string value = line.Substring(separatorIndex + 1).Trim();
                    headers[name] = value;
                }
            }

            return headers;
        }
    }
}
