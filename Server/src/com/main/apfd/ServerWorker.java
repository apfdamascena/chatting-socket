package com.main.apfd;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ServerWorker extends Thread implements Communicate {

    private final InputStream inputStream;
    private final Socket clientSocket;
    private final Communicator communicator;
    private Display display;
    private String user;


    public ServerWorker(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.inputStream = clientSocket.getInputStream();
        OutputStream outputStream = clientSocket.getOutputStream();
        this.communicator = new Communicator(outputStream);
        this.display = new Display(communicator);
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

            ActionManager manager = new ActionManager(command);

            if(isLogin(action)) {
                user = arguments.get(0);
                manager.realize(new HandleLoginCommand(display));
            }
            if(isLogOut(action)) manager.realize(new HandleLogoutCommand(clientSocket, display));
            if(isMessage(action)) manager.realize(new HandleMessageCommand());
            if(isJoin(action)) manager.realize(new HandleJoinCommand());
            line = reader.readLine();
        }
    }

    private boolean isLogOut(Action command){ return command.equals(Action.logout); }
    private boolean isLogin(Action command){ return command.equals(Action.login);}
    private boolean isMessage(Action command) { return command.equals(Action.message); }
    private boolean isJoin(Action command){ return command.equals(Action.join); }

    @Override
    public void tryToSend(String message) { communicator.tryToSend(message); }

    public String getCurrentUser(){
        return user;
    }
}
