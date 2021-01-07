package com.main.apfd;

import java.util.List;


class Constants {
    public static final String LOGIN_COMMAND = "login";
}
enum Action {
    login, message, logout, join, none;

    public static Action actionFrom(String action){
        if(action.equalsIgnoreCase(Constants.LOGIN_COMMAND)){
            return Action.login;
        }

        if(action.equalsIgnoreCase("message")){
            return Action.message;
        }

        if(action.equalsIgnoreCase("logout")){
            return Action.logout;
        }

        if(action.equalsIgnoreCase("join")){
            return Action.join;
        }
        return Action.none;
    }
}

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
