package com.main.apfd;

import org.apache.commons.lang3.StringUtils;
import java.io.*;
import java.net.Socket;

public class ServerWorker extends Thread implements Communicate {

    private final Socket clientSocket;
    private final Server server;
    private String user;
    private Authenticator authenticator = new Authenticator();
    InputStream inputStream;
    OutputStream outputStream;
    private final Communicator communicator;

    public ServerWorker(Server server, Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.server = server;
        this.inputStream = clientSocket.getInputStream();
        this.outputStream = clientSocket.getOutputStream();
        this.communicator = new Communicator(server, outputStream);
    }

    @Override
    public void run() {
        try {
            handleSocketClient();
        } catch (IOException | InterruptedException error){
            error.printStackTrace();
        }
    }

    private void handleSocketClient() throws IOException, InterruptedException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = reader.readLine();

        while(line != null){
            String[] tokens = StringUtils.split(line);
            String command = tokens[0];
            if(isEqualToLogin(command)) handleLogin(tokens);
            if(isEqualToQuitOrLogoff(command)) handleLogoff();
            if(isEqualToMessage(command)) communicator.handleMessage(tokens, user);
            line = reader.readLine();
        }
    }

    private boolean isEqualToQuitOrLogoff(String command){
        return command.equalsIgnoreCase("quit") || command.equalsIgnoreCase("logoff");
    }

    private boolean isEqualToLogin(String command){
        return command.equalsIgnoreCase("login");
    }

    private boolean isEqualToMessage(String command) {
        return command.equalsIgnoreCase("message");
    }

    private void handleLogin(String[] tokens) {
        if(!wasWrittenCommandUserAndPassword(tokens)) return;
        String user = tokens[1];
        String password = tokens[2];
        Boolean isLogged = authenticator.authenticate(user, password);

        if(isLogged){
            this.user = user;
            communicator.showCurrentUserStatusTo("Online", user);
            communicator.showUsersLoggedTo(user);
        }
    }

    private boolean wasWrittenCommandUserAndPassword(String[] tokens){
        return tokens.length == 3;
    }

    private void handleLogoff() throws IOException {
        server.removeWorker(this);
        communicator.showCurrentUserStatusTo("Offline", user);
        clientSocket.close();
    }

    @Override
    public void tryTosend(String message, String user) {
        communicator.tryTosend(message,user);
    }

    public String getUser(){
        return user;
    }
}
