package com.main.apfd;

import java.io.IOException;

public class ServerWorker extends Thread {

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
}
