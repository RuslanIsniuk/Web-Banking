package ua.rd.webbanking.model.exceptions;


/**
 * Created by Руслан on 25.02.2017.
 */
public class NotEnoughMoneyException extends ServiceException {

    public NotEnoughMoneyException() {
        super("ERROR! Not enough money!");
    }

    public NotEnoughMoneyException(String error) {
        super(error);
    }
}
