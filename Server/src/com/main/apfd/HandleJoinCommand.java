package com.main.apfd;

import java.util.List;

public class HandleJoinCommand implements HandleCommand {

    @Override
    public void handle(Action action, List<String> arguments) {
        if(!isJoin(action)) return;
        String room = arguments.get(0);
        Store.shared.addChatRoom(room);
    }

    private boolean isJoin(Action action){
        return action.equals(Action.join);
    }
}
