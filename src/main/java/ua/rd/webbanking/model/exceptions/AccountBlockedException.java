package ua.rd.webbanking.model.exceptions;

/**
 * Created by Руслан on 25.02.2017.
 */
public class AccountBlockedException extends ServiceException {

    public AccountBlockedException() {
        super("ERROR! Operation failed! Account is blocked!");
    }

    public AccountBlockedException(String error) {
        super(error);
    }
}
