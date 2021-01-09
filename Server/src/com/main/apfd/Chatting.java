package com.main.apfd;

public class Chatting {

    private String message;
    private ServerWorker worker;
    private String receiver;
    private boolean isChatRoom;
    private String chatting = "";

    public Chatting(String message, ServerWorker worker, String whoWillReceive, boolean isChatRoom){
        this.message = message;
        this.worker = worker;
        this.receiver = whoWillReceive;
        this.isChatRoom = isChatRoom;
    }

    public String doConversation(){
        return isChatRoom ? chatRoom() : direct();
    }

    private String chatRoom(){
        String room = receiver;
        boolean isMemberOfRoom = Store.shared.isMemberOf(room);
        if(isMemberOfRoom){
            chatting = "Message from "
                    + Store.shared.getUser() +
                    " at " + room + ": "
                    + message + "\n";
        }
        return chatting;
    }

    private String direct(){
        String workerUser = worker.getCurrentUser();
        if(isReceiverEqualToWorkerUser(receiver, workerUser)){
            chatting = "Message from "
                        + Store.shared.getUser() + ": "
                        + message + "\n";
        }
        return chatting;
    }

    private boolean isReceiverEqualToWorkerUser(String receiver, String workerUser){
        return receiver.equalsIgnoreCase(workerUser);
    }



}
