package com.main.apfd;

import java.util.HashMap;
import java.util.Map;

public class Authenticator implements Authenticate {

    private String user;

    Map<String, String> accounts = new HashMap<>(){{
        put("guest", "guest");
        put("alex", "alex");
    }};

    @Override
    public Boolean authenticate(String user, String password){
        if(!accounts.get(user).equals(password)){ return false;}
        this.user = user;
        return true;
    }

    public String getUser() {
        return user;
    }
}
