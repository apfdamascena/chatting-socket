package com.main.apfd;

import java.util.HashMap;
import java.util.Map;

public class Authenticator implements Authenticate {

    Map<String,String> users = new HashMap<>(){{
        put("guest", "guest");
        put("alex", "alex");
    }};

    @Override
    public String authenticate(String user, String password) {
        String message = "error login\n";
        if(!users.containsKey(user)) return message;

        String passwordToCheck = users.get(user);
        if(passwordToCheck.equals(password)){
            message = "ok login\nUser logged in successfully: " + user + "\n";
        }
        return message;
    }
}
