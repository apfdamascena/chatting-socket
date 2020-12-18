package com.main.apfd;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;


public class Client implements Workers {

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

    @Override
    public void send(ArrayList<ServerWorker> workers) {
        communicator.send(workers);
    }

    public Communicator getCommunicator() {
        return communicator;
    }
}
