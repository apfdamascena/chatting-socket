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
    private List<HandleCommand> possibleCommands;


    public ServerWorker(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.inputStream = clientSocket.getInputStream();
        OutputStream outputStream = clientSocket.getOutputStream();
        this.communicator = new Communicator(outputStream);
        this.display = new Display(communicator);
        this.possibleCommands = new HandleCommandFactory(display, clientSocket).make();
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

            possibleCommands.forEach(handleCommand -> manager.realize(handleCommand));

            if(isLogin(action)) {
                user = arguments.get(0);
            }

            line = reader.readLine();
        }
    }

    private boolean isLogin(Action command){ return command.equals(Action.login);}

    @Override
    public void tryToSend(String message) { communicator.tryToSend(message); }

    public String getCurrentUser(){
        return user;
    }
}
