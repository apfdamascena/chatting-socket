package com.main.apfd;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;

public class Client {

    private final Socket clientSocket;
    private Communicator communicator;

    public Client(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.communicator = new Communicator(clientSocket.getInputStream(), clientSocket.getOutputStream());
    }

    public void handleClientSocket() throws IOException, InterruptedException {
        this.communicator.communicate();
        clientSocket.close();
    }
}
