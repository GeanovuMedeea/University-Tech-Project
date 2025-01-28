using System;
using System.Collections;
using System.IO;
using System.Net;
using System.Net.Sockets;
using System.Runtime.InteropServices.JavaScript;
using System.Text;
using System.Threading;
using Lab4.versions;

namespace Lab4
{
    class Program
    {
        static void printMenu()
        {
            Console.WriteLine("\n1. Event callbacks");
            Console.WriteLine("2. Task wrap");
            Console.WriteLine("3. Async callbacks");
            Console.WriteLine("0. Exit");
        }
        
        static async Task Main(string[] args)
        {

            List<string> list = new List<string>();
            list.Add("example.com");
            list.Add("icio.us");
            list.Add("info.cern.ch");
            string input;
            while (true)
            {
                printMenu();
                
                Console.WriteLine("Please input the choice: \n");
                input = Console.ReadLine();
                if (input.Equals("1"))
                {
                    DirectCallbacks directCallbacks = new DirectCallbacks();
                    directCallbacks.Run(list);
                }
                else if (input.Equals("2"))
                {
                    TaskWrap taskWrap = new TaskWrap();
                    taskWrap.Run(list);
                }
                else if (input.Equals("3"))
                {
                    AsyncTaskWrap asyncTaskWrap = new AsyncTaskWrap();
                    await asyncTaskWrap.Run(list);
                }
                else if (input.Equals("0"))
                {
                    return;
                }
                else
                {
                    Console.WriteLine("Invalid input. Please try again.");
                }
            }

            //DirectCallbacks directCallbacks = new DirectCallbacks();
            //directCallbacks.Run(list);
            //TaskWrap taskWrap = new TaskWrap();
            //taskWrap.Run(list);
            //AsyncTaskWrap asyncTaskWrap = new AsyncTaskWrap();
            //await asyncTaskWrap.Run(list);
        }
    }
}