package com.main.apfd;

import java.io.IOException;
import java.io.OutputStream;

public class Communicator implements Communicate {

    private final OutputStream outputStream;

    public Communicator( OutputStream outputStream){
        this.outputStream = outputStream;
    }

    @Override
    public void tryToSend(String message) {
        try {
            send(message);
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    private void send(String message) throws IOException {
        String user = Store.shared.getUser();
        if( user != null){
            outputStream.write(message.getBytes());
        }
    }
}
