package com.main.apfd;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class Communicator implements Communicate {

    private final OutputStream outputStream;
    private final Server server;

    public Communicator(Server server, OutputStream outputStream){
        this.outputStream = outputStream;
        this.server = server;
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

    public void showCurrentUserStatusTo(String status, String user){
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

    public void showUsersLoggedTo(String user){
        List<ServerWorker> workers = server.getWorkers();
        workers.forEach((worker) -> {
            if(!isCurrentUserEqualToWorkerUser(worker, user)){
                String workerUser = worker.getUser();
                String onlineMessageToOthersUsersLogged = "online: " + workerUser + "\n";
                tryTosend(onlineMessageToOthersUsersLogged, workerUser);
            }
        });
    }

    public void handleMessage(String[] tokens, String sendUser) {
        String receiver = tokens[1];
        String bodyMessage = tokens[2];

        List<ServerWorker> workers = server.getWorkers();
        workers.forEach((worker) -> {
            String workerUser = worker.getUser();
            if(isReceiverEqualTo(workerUser, receiver)){
                String message = "Message from " + sendUser + ": " + bodyMessage + "\n";
                worker.tryTosend(message, sendUser);
            }
        });
    }

    private boolean isReceiverEqualTo(String workerUser, String receiver){
        return receiver.equalsIgnoreCase(workerUser);
    }
}
