package com.main.apfd;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class ServerMain {

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}
