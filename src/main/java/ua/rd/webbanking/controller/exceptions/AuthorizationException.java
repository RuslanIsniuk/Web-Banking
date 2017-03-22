package ua.rd.webbanking.controller.exceptions;


public class AuthorizationException extends Exception {

    public AuthorizationException(){
        super("Error! Session time ran out!");
    }

    public AuthorizationException(String errorMessage){
        super(errorMessage);
    }
}
