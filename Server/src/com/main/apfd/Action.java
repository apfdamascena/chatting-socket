package com.main.apfd;

enum Action {
    login, message, logout, join, none;

    public static Action actionFrom (String action){
        if(action.equalsIgnoreCase(Constants.LOGIN_COMMAND)){
            return Action.login;
        }

        if(action.equalsIgnoreCase(Constants.MESSAGE_COMMAND)){
            return Action.message;
        }

        if(action.equalsIgnoreCase(Constants.LOGOUT_COMMAND)){
            return Action.logout;
        }

        if(action.equalsIgnoreCase(Constants.JOIN_COMMAND)){
            return Action.join;
        }
        return Action.none;
    }
}