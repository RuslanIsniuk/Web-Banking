package ua.rd.webbanking.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.rd.webbanking.dao.AccountDAO;
import ua.rd.webbanking.dao.ClientDAO;
import ua.rd.webbanking.dao.CreditCardDAO;
import ua.rd.webbanking.dao.impl.JDBCAccountDAO;
import ua.rd.webbanking.dao.impl.JDBCClientDAO;
import ua.rd.webbanking.dao.impl.JDBCCreditCardDAO;
import ua.rd.webbanking.entities.Account;
import ua.rd.webbanking.entities.CreditCard;

import java.util.ArrayList;
import java.util.List;

public class DeleteAccount {
    @Autowired
    @Qualifier("HibernateClientDAO")
    private ClientDAO clientDAO;
    @Autowired
    @Qualifier("HibernateAccountDAO")
    private AccountDAO accountDAO;
    @Autowired
    @Qualifier("HibernateCreditCardDAO")
    private CreditCardDAO creditCardDAO;

    public void deleteConfirm(int clientID) {
        List<Account> accountList = accountDAO.readUsingClientID(clientID);
        List<CreditCard> creditCardList = new ArrayList<>();

        for (Account account : accountList) {
            List<CreditCard> creditCardsFromCurrentAccount = creditCardDAO.readUsingAccountID(account.getAccountID());
            creditCardList.addAll(creditCardsFromCurrentAccount);
        }

        for (CreditCard creditCard : creditCardList) {
            creditCardDAO.delete(creditCard.getCardID());
        }

        for (Account account : accountList) {
            accountDAO.delete(account.getAccountID());
        }

        clientDAO.delete(clientID);
    }
}
