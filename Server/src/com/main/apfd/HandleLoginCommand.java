package com.main.apfd;

import java.util.List;

public class HandleLoginCommand implements HandleCommand {

    private Authenticator authenticator = new Authenticator();
    private Display display;

    public HandleLoginCommand(Display display){
        this.display = display;
    }

    @Override
    public void handle(Action action, List<String> arguments) {
        if (!isLogin(action)) return;

        String user = arguments.get(0);
        String password = arguments.get(1);

        Boolean isLogged = authenticator.authenticate(user, password);

        if(!isLogged) return;
        Store.shared.setUser(user);
        showOnScreen();
    }

    private boolean isLogin(Action action){ return action.equals(Action.login); }

    private void showOnScreen(){
        display.showCurrentUserStatusTo("Online");
        display.showUsersLogged();
    }
}
