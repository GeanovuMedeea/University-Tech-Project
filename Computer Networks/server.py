import select
import socket

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.setblocking(False)
s.bind(('0.0.0.0', 1112))
s.listen(5)

ins = [s]
outs = []
msg = []

dict_usernames = {}
disconnect_command = "QUIT"
kick_command = "KICK"
first_client = None

while ins:
    r, w, e = select.select(ins, outs, ins)
    clients_to_remove = []
    clients_that_want_a_taken_username = []

    for ss in r:
        if ss == s:
            cs, addr = ss.accept()
            print("Client from:", addr)
            ins.append(cs)
            #if first_client is None:
            #    first_client = cs
        else:
            try:
                data = ss.recv(100)
                data_decoded = data.decode()
                data_decoded = data_decoded[:len(data_decoded) - 1]
               
                if data_decoded == disconnect_command:
                    print("Client " + dict_usernames[ss] + " requested disconnect.")
                    clients_to_remove.append(ss)
                    #ins.remove(ss)
                    outs.remove(ss)

                    #ss.close()
                    for os in outs:
                        os.send(("* " + dict_usernames[ss] + " has disconnected! *\n").encode())
                    if ss in clients_that_want_a_taken_username:
                        clients_that_want_a_taken_username.remove(ss)
                    if ss in list(dict_usernames.keys()):
                        del dict_usernames[ss]
                        if first_client == ss and len(dict_usernames):
                            first_client = list(dict_usernames.keys())[0]
                            for os in outs:
                                os.send(("* The new admin is " + dict_usernames[first_client] + "* \n").encode())
                        elif len(dict_usernames) == 0:
                            first_client = None     
                    ss.close()
                elif data_decoded.startswith(kick_command) and ss == first_client:
                    username_to_kick = data_decoded[len(kick_command) + 1:]
                    print("Client " + dict_usernames[ss] + " requested to kick " + username_to_kick)

                    for client_socket, client_username in dict_usernames.items():
                        if client_socket == first_client and client_username == username_to_kick:
                            client_socket.send(("* You can't kick yourself! MWAHAHAHA! *\n").encode())
                            continue
                        elif client_username == username_to_kick:
                            clients_to_remove.append(client_socket)
                            for os in outs:
                                if os != client_socket:
                                    os.send(("* " + username_to_kick + " has been kicked by " + dict_usernames[ss] + " *\n").encode())
                            client_socket.send("KICKED".encode())
                            del dict_usernames[client_socket]
                            client_socket.close()
                            break
                elif ss not in dict_usernames:
                    if data_decoded in list(dict_usernames.values()):
                        ss.send("* This username is taken! Take another one! * \n".encode())
                        if ss not in clients_that_want_a_taken_username:
                            clients_that_want_a_taken_username.append(ss)
                    else:
                        if ss in clients_that_want_a_taken_username:
                            clients_that_want_a_taken_username.remove(ss)

                        dict_usernames[ss] = data_decoded
                        outs.append(ss)
                        txt = "* " + data_decoded + " has connected to the chat! *\n"
                        if first_client is None:
                            first_client = list(dict_usernames.keys())[0]
                        msg.append(("server", txt))
                else:
                    txt = dict_usernames[ss] + ": " + data_decoded + "\n"
                    #print(txt + "\n")
                    msg.append((ss, txt))
                if ss not in outs:
                    outs.append(ss)
            except (ConnectionResetError, ConnectionAbortedError, OSError):
                print("Client disconnected with an error.")
                clients_to_remove.append(ss)
                for os in outs:
                    try:
                        os.send(("* " + dict_usernames[ss] + " has disconnected! *\n").encode())
                    except BrokenPipeError:
                        pass
                if ss in clients_that_want_a_taken_username:
                    clients_that_want_a_taken_username.remove(ss)
                if ss in list(dict_usernames.keys()):
                    del dict_usernames[ss]
                    if first_client == ss and len(dict_usernames):
                        first_client = list(dict_usernames.keys())[0]
                        for os in outs:
                            if os != ss:
                                os.send(("* The new admin is " + dict_usernames[first_client] + "* \n").encode())
                    elif len(dict_usernames) == 0:
                        first_client = None
                ss.close()
    for ss in e:
        print("Error from client. Removed the faulty client.")
        clients_to_remove.append(ss)

    ins = [c for c in ins if c not in clients_to_remove]
    outs = [c for c in outs if c not in clients_to_remove and c not in clients_that_want_a_taken_username]

    for c in clients_to_remove:
        c.close()

    new_outs = []
    #for i in msg:
    #    print(i[0]," ",i[1])

    for ss in outs:
        try:
            for i in msg:
                if i[0] != ss:
                    ss.send(i[1].encode())
            new_outs.append(ss)
        except BrokenPipeError:
            print("BrokenPipeError - client disconnected while sending")
    outs = new_outs
    msg = []