package com.main.apfd;

public class Login {

    Authenticator authenticator = new Authenticator();
    String errorToLengthLessThanThree = "The length must be three words";

    public String handleLogin(String[] tokens) {
        if(isLengthEqualThree(tokens)){
            String user = tokens[1], password = tokens[2];
            return this.authenticator.authenticate(user, password);
        } else {
            return errorToLengthLessThanThree;
        }
    }

    private boolean isLengthEqualThree(String[] tokens){
        return tokens.length == 3;
    }

}
