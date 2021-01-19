package com.main.apfd;

import java.net.Socket;
import java.util.Arrays;
import java.util.List;

public class HandleCommandFactory {
    private Display display;
    private Socket clientSocket;

    HandleCommandFactory(Display display, Socket clientSocket) {
        this.display = display;
        this.clientSocket = clientSocket;
    }

    public List<HandleCommand> make() {
        return Arrays.asList(
                new HandleLoginCommand(display),
                new HandleLogoutCommand(clientSocket, display),
                new HandleMessageCommand(),
                new HandleJoinCommand()
        );
    }
}
