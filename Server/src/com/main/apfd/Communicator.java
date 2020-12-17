package com.main.apfd;

import java.io.*;

public class Communicator {

    private InputStream inputStream;
    private OutputStream outputStream;

    public Communicator(InputStream inputStream, OutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public void communicate() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;

        line = reader.readLine();

        while(line != null){
            if("quit".equalsIgnoreCase(line)) return;
            String message = "You typed: " + line + "\n";
            outputStream.write(message.getBytes());
            line = reader.readLine();
        }
    }
}
