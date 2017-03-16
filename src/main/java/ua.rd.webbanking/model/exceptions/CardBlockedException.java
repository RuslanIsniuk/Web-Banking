package electronicPaymentSystem.model.exceptions;

/**
 * Created by Руслан on 25.02.2017.
 */
public class CardBlockedException extends ServiceException {

    public CardBlockedException() {
        super("ERROR! Operation failed! Card is blocked!");
    }

    public CardBlockedException(String error) {
        super(error);
    }
}
