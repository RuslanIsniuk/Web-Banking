package ua.rd.webbanking.model.services;

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
    private ClientDAO clientDAO = new JDBCClientDAO();
    private AccountDAO accountDAO = new JDBCAccountDAO();
    private CreditCardDAO creditCardDAO = new JDBCCreditCardDAO();

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
