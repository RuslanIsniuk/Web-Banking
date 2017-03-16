package electronicPaymentSystem.model.services;

import electronicPaymentSystem.dao.AccountDAO;
import electronicPaymentSystem.dao.ClientDAO;
import electronicPaymentSystem.dao.CreditCardDAO;
import electronicPaymentSystem.dao.impl.JDBCAccountDAO;
import electronicPaymentSystem.dao.impl.JDBCClientDAO;
import electronicPaymentSystem.dao.impl.JDBCCreditCardDAO;
import electronicPaymentSystem.entities.Account;
import electronicPaymentSystem.entities.CreditCard;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Руслан on 04.03.2017.
 */
public class DeleteAccount {
    private ClientDAO clientDAO = new JDBCClientDAO();
    private AccountDAO accountDAO = new JDBCAccountDAO();
    private CreditCardDAO creditCardDAO = new JDBCCreditCardDAO();

    public void deleteConfirm(int clientID){
        List<Account> accountList = accountDAO.readUsingClientID(clientID);
        List<CreditCard> creditCardList = new ArrayList<>();

        for(Account account:accountList){
            List<CreditCard> creditCardsFromCurrentAccount = creditCardDAO.readUsingAccountID(account.getAccountID());
            creditCardList.addAll(creditCardsFromCurrentAccount);
        }

        for(CreditCard creditCard:creditCardList){
            creditCardDAO.delete(creditCard.getCardID());
        }

        for(Account account: accountList){
            accountDAO.delete(account.getAccountID());
        }

        clientDAO.delete(clientID);
    }
}
