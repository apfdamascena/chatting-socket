package com.main.apfd;

public class Authenticator implements Authenticate {

    private final String user="guest";
    private final String password="guest";

    @Override
    public String authenticate(String user, String password) {
        String message;
        if(this.user.equals(user) && this.password.equals(password)){
            message = "ok login\n";
        } else {
            message = "error login\n";
        }
        return message;
    }
}
