package com.main.apfd;

import org.apache.commons.lang3.StringUtils;
import java.io.*;
import java.net.Socket;
import java.util.HashSet;

public class ServerWorker extends Thread implements Communicate {

    private final Socket clientSocket;
    private final Server server;
    private final Authenticator authenticator = new Authenticator();
    private final InputStream inputStream;
    private final OutputStream outputStream;

    private String user;
    private Communicator communicator;
    private HashSet<String> rooms = new HashSet<>();

    public ServerWorker(Server server, Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.server = server;
        this.inputStream = clientSocket.getInputStream();
        this.outputStream = clientSocket.getOutputStream();
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
            if(isLogin(command)) handleLogin(tokens);
            if(isQuitOrLogoff(command)) handleLogoff();
            if(isMessage(command)) {
                String[] withLotsOfSpace = StringUtils.split(line,null, 3);
                communicator.handleMessage(withLotsOfSpace, rooms);
            }
            if(isJoin(command)) handleChattingGroup(tokens);
            line = reader.readLine();
        }
    }

    private boolean isQuitOrLogoff(String command){
        return command.equalsIgnoreCase("quit") || command.equalsIgnoreCase("logoff");
    }

    private boolean isLogin(String command){
        return command.equalsIgnoreCase("login");
    }

    private boolean isMessage(String command) {
        return command.equalsIgnoreCase("message");
    }

    private boolean isJoin(String command) {
        return command.equalsIgnoreCase("join");
    }

    private void handleLogin(String[] tokens) {
        if(!wasWrittenUserPasswordAndCommand(tokens)) return;
        String user = tokens[1];
        String password = tokens[2];
        Boolean isLogged = authenticator.authenticate(user, password);

        if(!isLogged) return;
        this.user = user;
        this.communicator = new Communicator(server, outputStream, user);
        communicator.showCurrentUserStatusTo("Online");
        communicator.showUsersLogged();
    }

    private boolean wasWrittenUserPasswordAndCommand(String[] tokens){
        return tokens.length == 3;
    }

    private void handleLogoff() throws IOException {
        server.removeWorker(this);
        communicator.showCurrentUserStatusTo("Offline");
        clientSocket.close();
    }

    private void handleChattingGroup(String[] tokens){
        if(wasWrittenRoomAndCommand(tokens)){
            String room = tokens[1];
            rooms.add(room);
        }
    }

    private boolean wasWrittenRoomAndCommand(String[] tokens){
        return tokens.length == 2;
    }

    @Override
    public void tryTosend(String message, String user) {
        communicator.tryTosend(message, user);
    }

    public String getUser(){
        return user;
    }
}
