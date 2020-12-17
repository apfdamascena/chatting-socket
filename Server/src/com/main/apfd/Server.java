package com.main.apfd;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private final int port = 9807;

    public void handleInitialize(){
        try {
            initialize();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void initialize() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        while(true){
            System.out.println("about to accept client connection..");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Accepeted connection from " + clientSocket);
            ServerWorker worker = new ServerWorker(new Client(clientSocket));
            worker.start();
        }
    }

}
