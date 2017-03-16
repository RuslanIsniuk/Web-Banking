package electronicPaymentSystem.controller.exceptions;

/**
 * Created by Руслан on 26.02.2017.
 */
public class InvalidCardDataInputException extends InvalidInputException {

    public InvalidCardDataInputException() {
        super("ERROR! Incorrect credit card data input!");
    }

    public InvalidCardDataInputException(String incorrectData) {
        super("ERROR! Incorrect credit card " + incorrectData + " input!");
    }
}
