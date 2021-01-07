package com.main.apfd;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread {

    private final int  port = 9807;
    private ArrayList<ServerWorker> workers = new ArrayList<>();

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
            ServerWorker worker = new ServerWorker(this, clientSocket);
            workers.add(worker);
            worker.start();
        }
    }

    public List<ServerWorker> getWorkers(){
        return workers;
    }

    public void removeWorker(ServerWorker worker) {
        workers.remove(worker);
    }
}
