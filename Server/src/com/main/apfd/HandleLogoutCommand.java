package com.main.apfd;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class HandleLogoutCommand implements HandleCommand {

    private final Socket clientSocket;
    private final Display display;

    public HandleLogoutCommand(Socket clientSocket, Display display){
        this.clientSocket = clientSocket;
        this.display = display;
    }

    @Override
    public void handle(Action action, List<String> arguments) {
        if(!isLogout(action)) return;

        display.showCurrentUserStatusTo("Offline");

        closeClientSocket();
    }

    private boolean isLogout(Action action){
        return action.equals(Action.logout);
    }

    private void closeClientSocket(){
        try{
            clientSocket.close();
        } catch (IOException error){
            error.printStackTrace();
        }
    }

    public void removeThis(ServerWorker worker){
        Store.shared.removeWorker(worker);
    }
}
