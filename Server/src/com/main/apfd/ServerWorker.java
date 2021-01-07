package com.main.apfd;

import org.apache.commons.lang3.StringUtils;
import java.io.*;
import java.net.Socket;
import java.util.HashSet;
import java.util.List;

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

            Command command = new CommandProcessor().process(line);
            Action action = command.getAction();
            List<String> arguments = command.getArguments();

            if(isLogin(action)) handleLogin(arguments);
            if(isLogOut(action)) handleLogoff();
            if(isMessage(action)) {
                String[] withLotsOfSpace = StringUtils.split(line,null, 3);
                communicator.handleMessage(withLotsOfSpace, rooms);
            }
            if(isJoin(action)) handleChattingGroup(arguments);
            line = reader.readLine();
        }
    }

    private boolean isLogOut(Action command){ return command.equals(Action.logout); }
    private boolean isLogin(Action command){
         return command.equals(Action.login);
    }
    private boolean isMessage(Action command) { return command.equals(Action.message); }
    private boolean isJoin(Action command) {
        return command.equals(Action.join);
    }

    private void handleLogin(List<String> arguments) {
        if(!wasWrittenUserPassword(arguments)) return;
        String user = arguments.get(0);
        String password = arguments.get(1);
        Boolean isLogged = authenticator.authenticate(user, password);

        if(!isLogged) return;
        this.user = user;
        this.communicator = new Communicator(server, outputStream, user);
        communicator.showCurrentUserStatusTo("Online");
        communicator.showUsersLogged();
    }

    private boolean wasWrittenUserPassword(List<String> arguments){
        return arguments.size() == 2;
    }

    private void handleLogoff() throws IOException {
        server.removeWorker(this);
        communicator.showCurrentUserStatusTo("Offline");
        clientSocket.close();
    }

    private void handleChattingGroup(List<String> arguments){
        if(wasWrittenRoom(arguments)){
            String room = arguments.get(0);
            rooms.add(room);
        }
    }

    private boolean wasWrittenRoom(List<String> arguments){
        return arguments.size() == 1;
    }

    @Override
    public void tryTosend(String message, String user) {
        communicator.tryTosend(message, user);
    }

    public String getUser(){
        return user;
    }
}
