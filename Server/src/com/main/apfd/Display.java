package com.main.apfd;

import java.util.List;

public class Display {

    private final Communicator communicator;

    public Display(Communicator communicator){
        this.communicator = communicator;
    }

    public void showCurrentUserStatusTo(String status){
        String statusUsersToCurrentUser = status + ": " + Store.shared.getUser() + "\n";
        List<ServerWorker> workers = Store.shared.getWorkers();

        workers.forEach((worker) -> {
            String workerUser = worker.getCurrentUser();
            if(!isCurrentUserEqualTo(workerUser)){
                worker.tryToSend(statusUsersToCurrentUser);
            }
        });
    }

    private boolean isCurrentUserEqualTo(String workerUser){
        String currentUser = Store.shared.getUser();
        return currentUser.equals(workerUser);
    }

    public void showUsersLogged(){
        List<ServerWorker> workers = Store.shared.getWorkers();
        workers.forEach((worker) -> {
            String workerUser = worker.getCurrentUser();
            if(!isCurrentUserEqualTo(workerUser)){
                String onlineMessageToOthersUsersLogged = "Online: " + workerUser + "\n";
                communicator.tryToSend(onlineMessageToOthersUsersLogged);
            }
        });
    }
}
