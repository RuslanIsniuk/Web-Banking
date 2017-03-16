package electronicPaymentSystem.model.exceptions;

import javax.servlet.ServletException;

/**
 * Created by Руслан on 22.02.2017.
 */
public class ServiceException extends Exception {

    public ServiceException() {
        super("Error in some service method!");
    }

    public ServiceException(String error) {
        super(error);
    }
}
