package electronicPaymentSystem.controller.exceptions;

/**
 * Created by Руслан on 26.02.2017.
 */
public class InvalidAccountDataInputException extends InvalidInputException {

    public InvalidAccountDataInputException() {
        super("ERROR! Incorrect account data input!");
    }

    public InvalidAccountDataInputException(String incorrectData) {
        super("ERROR! Incorrect account " + incorrectData + " input!");
    }

}
