package ua.rd.webbanking.dao;

import ua.rd.webbanking.entities.CreditCard;

import java.util.List;

public interface CreditCardDAO {
    List<CreditCard> readUsingAccountID (int accountID);
    CreditCard read(long cardID);
    List<CreditCard> readAll();
    long insert(CreditCard creditCard);
    void update (CreditCard creditCard);
    void delete (long cardID);
}
