package com.main.apfd;

public class Login {

    private Authenticator authenticator = new Authenticator();
    private String errorToLengthLessThanThree = "The length must be three words";
    private String user;


    public String handleLogin(String[] tokens) {
        if(isLengthEqualThree(tokens)){
            this.user = tokens[1];
            String password = tokens[2];
            return this.authenticator.authenticate(user, password);
        } else {
            return errorToLengthLessThanThree;
        }
    }

    private boolean isLengthEqualThree(String[] tokens){
        return tokens.length == 3;
    }

    public String getUser(){
        return user;
    }
}
