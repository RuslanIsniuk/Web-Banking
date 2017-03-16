package ua.rd.webbanking.model.exceptions;

/**
 * Created by Руслан on 03.03.2017.
 */
public class DuplicateClientDataException extends ServiceException{

    public DuplicateClientDataException() {
        super("ERROR! Operation failed! Such client is already exist");
    }
}
