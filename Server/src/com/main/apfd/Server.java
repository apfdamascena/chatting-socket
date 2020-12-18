package com.main.apfd;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread {

    private final int port = 9807;
    private ArrayList<ServerWorker> workers = new ArrayList<>();

    @Override
    public void run() {
        handleInitialize();
    }

    private void handleInitialize(){
        try {
            initialize();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void initialize() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        while(true){
            Socket clientSocket = serverSocket.accept();
            System.out.println("Accepeted connection from " + clientSocket);
            ServerWorker worker = new ServerWorker(new Client(clientSocket));
            workers.add(worker);
            worker.start();
            worker.send(workers);
        }
    }

    public List<ServerWorker> getWorkers(){
        return workers;
    }
}
