package com.main.apfd;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {

    private final int  port = 9807;

    @Override
    public void run(){
        try {
            connect();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void connect() throws  IOException{
        ServerSocket serverSocket = new ServerSocket(port);
        while(true){
            Socket clientSocket = serverSocket.accept();
            ServerWorker worker = new ServerWorker(clientSocket);
            Store.shared.addWorker(worker);
            worker.start();
        }
    }
}
