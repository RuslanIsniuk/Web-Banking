package electronicPaymentSystem.controller.exceptions;

/**
 * Created by Руслан on 26.02.2017.
 */
public class InvalidClientDataInputException extends InvalidInputException {

    public InvalidClientDataInputException() {
        super("ERROR! Incorrect client data input!");
    }

    public InvalidClientDataInputException(String incorrectData) {
        super("ERROR! Incorrect client " + incorrectData + " input!");
    }
}
