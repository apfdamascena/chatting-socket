package com.main.apfd;

import java.io.IOException;
import java.util.ArrayList;

public class ServerWorker extends Thread implements Workers{

    private final Client client;

    public ServerWorker(Client client){
        this.client = client;
    }

    @Override
    public void run() {
        try {
            client.handleClientSocket();
        } catch (IOException | InterruptedException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void send(ArrayList<ServerWorker> workers) {
        client.send(workers);
    }

    public String getUser(){
        return client.user;
    }
}
