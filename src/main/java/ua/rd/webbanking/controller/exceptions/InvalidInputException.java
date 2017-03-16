package ua.rd.webbanking.controller.exceptions;

/**
 * Created by Руслан on 26.02.2017.
 */
public class InvalidInputException extends Exception {
    public InvalidInputException() {
        super();
    }

    public InvalidInputException(String incorrectData) {
        super(incorrectData);
    }
}
