package ua.rd.webbanking.controller.exceptions;

/**
 * Created by Руслан on 17.02.2017.
 */
public class AuthorizationException extends Exception {

    public AuthorizationException(){
        super("Error! Session time ran out!");
    }

    public AuthorizationException(String errorMessage){
        super(errorMessage);
    }
}
