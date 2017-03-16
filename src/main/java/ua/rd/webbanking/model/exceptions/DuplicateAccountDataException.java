package ua.rd.webbanking.model.exceptions;

/**
 * Created by Руслан on 03.03.2017.
 */
public class DuplicateAccountDataException extends ServiceException {
    public DuplicateAccountDataException() {
        super("ERROR! Operation failed! Such accountID is already exist");
    }
}
