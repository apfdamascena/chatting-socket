package com.main.apfd;

import java.util.List;

public class Command {

    private Action action;
    private List<String> arguments;

    public Command action(Action action) {
        this.action = action;
        return this;
    }

    public Command arguments(List<String> arguments) {
        this.arguments = arguments;
        return this;
    }

    public Action getAction() {
        return action;
    }
    public List<String> getArguments(){
        return arguments;
    }
}
