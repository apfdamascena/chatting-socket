package com.main.apfd;

public class ChattingBuilder {

    private String message;
    private ServerWorker worker;
    private String receiver;
    private boolean isChatRoom;

    public ChattingBuilder from(ServerWorker worker) {
        this.worker = worker;
        return this;
    }

    public ChattingBuilder with(String message) {
        this.message = message;
        return this;
    }

    public ChattingBuilder forThis(String user){
        this.receiver = user;
        return this;
    }

    public ChattingBuilder inRoom(boolean isChatRoom){
        this.isChatRoom = isChatRoom;
        return this;
    }

    public Chatting build(){
        return new Chatting(message, worker, receiver, isChatRoom);
    }
}
