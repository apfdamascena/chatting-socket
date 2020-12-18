package com.main.apfd;

import java.io.*;
import org.apache.commons.lang3.StringUtils;

public class Communicator {

    private InputStream inputStream;
    private OutputStream outputStream;
    private Login login = new Login();

    public Communicator(InputStream inputStream, OutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public void communicate() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = reader.readLine();

        while(line != null){
            String[] tokens = StringUtils.split(line);
            String command = tokens[0];

            if("quit".equalsIgnoreCase(command)) return;
            handleLogin(command,tokens);
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
}
