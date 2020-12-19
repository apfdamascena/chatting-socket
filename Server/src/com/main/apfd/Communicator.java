package com.main.apfd;

import java.io.*;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

public class Communicator implements Workers {

    private InputStream inputStream;
    private OutputStream outputStream;
    private Login login = new Login();
    private ArrayList<ServerWorker> workers;
    private Client client;

    public Communicator(Client client, InputStream inputStream, OutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.client = client;
    }

    @Override
    public void send(ArrayList<ServerWorker> workers) {
        this.workers = workers;
    }

    public void communicate() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = reader.readLine();

        while(line != null){
            String[] tokens = StringUtils.split(line);
            String command = tokens[0];

            if("quit".equalsIgnoreCase(command)) return;
            client.user = tokens[1];
            handleLogin(command,tokens);
            showUsers();

            line = reader.readLine();
        }
    }

    private void handleLogin(String command, String[] tokens) throws IOException {
        String message;
        if("login".equalsIgnoreCase(command)){
            message = login.handleLogin(tokens);
        } else {
            message = "You typed: " + command + "\n";
        }
        outputStream.write(message.getBytes());
    }

    private void showUsers() throws IOException {
        for(ServerWorker worker : workers) {
            String online = "Online: " + worker.getUser() + "\n";
            sendMessage(online);
        }
    }

    public void sendMessage(String message) throws IOException {
        if(login.getUser() != null){
            outputStream.write(message.getBytes());
        }
    }

}
