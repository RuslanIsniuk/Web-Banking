package electronicPaymentSystem.dao;

import electronicPaymentSystem.entities.CreditCard;

import java.util.List;

/**
 * Created by Руслан on 21.12.2016.
 */
public interface CreditCardDAO {
    List<CreditCard> readUsingAccountID (int accountID);
    CreditCard read(long cardID);
    List<CreditCard> readAll();
    long create (CreditCard creditCard);
    void update (CreditCard creditCard);
    void delete (long cardID);
}
