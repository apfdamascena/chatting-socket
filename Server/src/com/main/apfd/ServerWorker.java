package com.main.apfd;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ServerWorker extends Thread{


    private final Socket clientSocket;
    private final Server server;
    private Login login = new Login();
    InputStream inputStream;
    OutputStream outputStream;

    public ServerWorker(Server server, Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            handlesocketClient();
            clientSocket.close();
        } catch (IOException | InterruptedException error){
            error.printStackTrace();
        }
    }

    private void handlesocketClient() throws IOException, InterruptedException {
        this.inputStream = clientSocket.getInputStream();
        this.outputStream = clientSocket.getOutputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = reader.readLine();

        while(line != null){
            String[] tokens = StringUtils.split(line);
            String command = tokens[0];
            if(command.equalsIgnoreCase("quit")) return;
            if(command.equalsIgnoreCase("login")) handleLogin(tokens);
            line = reader.readLine();
        }
    }

    private void handleLogin(String[] tokens) {
        if(!wasWrittenCommandUserAndPassword(tokens)) return;
        String user = tokens[1];
        String password = tokens[2];
        Boolean isLogged = login.authenticator(user, password);

        if(isLogged){
            showAllOnlineUsers();
        }
    }

    private boolean wasWrittenCommandUserAndPassword(String[] tokens){
        return tokens.length == 3;
    }

    private void showAllOnlineUsers(){
        String user = login.getUser();
        String onlineUsersToCurrentUser = "online: " + user + "\n";
        List<ServerWorker> workers = server.getWorkers();

        workers.forEach((worker) -> {
            if(!isCurrentUserEqualToWorkerUser(worker)){
                worker.tryTosend(onlineUsersToCurrentUser, user);
            }
        });

        workers.forEach((worker) -> {
            if(!isCurrentUserEqualToWorkerUser(worker)){
                String workerUser = worker.getLogin();
                String onlineMessageToOthersUsersLogged = "online: " + workerUser + "\n";
                tryTosend(onlineMessageToOthersUsersLogged, workerUser);
            }
        });
    }

    private void tryTosend(String message, String user) {
        try {
            send(message, user);
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    private void send(String message, String user) throws IOException {
        if(user != null){
            outputStream.write(message.getBytes());
        }
    }

    private boolean isCurrentUserEqualToWorkerUser(ServerWorker worker){
        String currentUser = login.getUser();
        String workerUser = worker.getLogin();
        return currentUser.equals(workerUser);
    }

    private String getLogin(){
        return login.getUser();
    }

}
