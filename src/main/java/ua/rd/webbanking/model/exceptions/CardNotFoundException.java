package ua.rd.webbanking.model.exceptions;

/**
 * Created by Руслан on 25.02.2017.
 */
public class CardNotFoundException extends ServiceException {

    public CardNotFoundException() {
        super("ERROR! Operation failed! Card not found!");
    }

    public CardNotFoundException(String cardIDStr) {
        super("ERROR! Card № " + cardIDStr + " not found!");
    }
}
