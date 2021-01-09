package com.main.apfd;

import java.util.ArrayList;
import java.util.HashSet;

public class Store {
    public static Store shared = new Store();

    private ArrayList<ServerWorker> workers = new ArrayList<>();
    private String user = null;
    private HashSet<String> rooms = new HashSet<>();

    private Store() {
    }

    public void addWorker(ServerWorker worker) {
        workers.add(worker);
    }
    public boolean isMemberOf(String room){ return rooms.contains(room); }
    public ArrayList<ServerWorker> getWorkers(){ return workers; }
    public HashSet<String> getRooms() {
        return rooms;
    }
    public void removeWorker(ServerWorker worker){
        workers.remove(worker);
    }
    public void setUser(String currentUser){ user = currentUser; }
    public void addChatRoom(String room){
        rooms.add(room);
    }
    public String getUser(){
        return user;
    }

}
