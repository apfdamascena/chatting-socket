package com.main.apfd;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.List;

public class Communicator implements Communicate {

    private final OutputStream outputStream;
    private final Server server;
    private final String user;

    public Communicator(Server server, OutputStream outputStream, String user){
        this.outputStream = outputStream;
        this.server = server;
        this.user = user;
    }

    @Override
    public void tryTosend(String message, String user) {
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

    public void showCurrentUserStatusTo(String status){
        String statusUsersToCurrentUser = status + ": " + user + "\n";
        List<ServerWorker> workers = server.getWorkers();

        workers.forEach((worker) -> {
            if(!isCurrentUserEqualToWorkerUser(worker, user)){
                worker.tryTosend(statusUsersToCurrentUser, user);
            }
        });
    }

    private boolean isCurrentUserEqualToWorkerUser(ServerWorker worker, String currentUser){
        String workerUser = worker.getUser();
        return currentUser.equals(workerUser);
    }

    public void showUsersLogged(){
        List<ServerWorker> workers = server.getWorkers();
        workers.forEach((worker) -> {
            String workerUser = worker.getUser();
            if(!isCurrentUserEqualToWorkerUser(worker, user)){
                String onlineMessageToOthersUsersLogged = "online: " + workerUser + "\n";
                tryTosend(onlineMessageToOthersUsersLogged, workerUser);
            }
        });
    }

    public void handleMessage(String[] tokens, HashSet<String> rooms) {
        String receiver = tokens[1];
        String bodyMessage = tokens[2];

        Boolean isChatRoom = receiver.charAt(0) == '#';

        List<ServerWorker> workers = server.getWorkers();
        workers.forEach((worker) -> {
            String workerUser = worker.getUser();
            if(isReceiverEqualTo(workerUser, receiver)){
                String message = "Message from " + user + ": " + bodyMessage + "\n";
                worker.tryTosend(message, user);
            }
        });
    }

    private boolean isReceiverEqualTo(String workerUser, String receiver){
        return receiver.equalsIgnoreCase(workerUser);
    }
}
