package com.main.apfd;

import java.util.List;

public class ActionManager {

    private Action action;
    private List<String> arguments;

    public ActionManager(Command command) {
        this.action = command.getAction();
        this.arguments = command.getArguments();
    }

    public void realize(HandleCommand manager){
        manager.handle(action, arguments);
    }
}
