package electronicPaymentSystem.model.exceptions;

/**
 * Created by Руслан on 03.03.2017.
 */
public class DuplicateCreditCardException extends ServiceException {

    public DuplicateCreditCardException() {
        super("ERROR! Operation failed! Such card ID is already exist");
    }
}
