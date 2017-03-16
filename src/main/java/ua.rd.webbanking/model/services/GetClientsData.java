package electronicPaymentSystem.model.services;

import electronicPaymentSystem.dao.AccountDAO;
import electronicPaymentSystem.dao.ClientDAO;
import electronicPaymentSystem.dao.CreditCardDAO;
import electronicPaymentSystem.dao.impl.JDBCAccountDAO;
import electronicPaymentSystem.dao.impl.JDBCClientDAO;
import electronicPaymentSystem.dao.impl.JDBCCreditCardDAO;
import electronicPaymentSystem.entities.Account;
import electronicPaymentSystem.entities.Client;
import electronicPaymentSystem.entities.CreditCard;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Руслан on 12.02.2017.
 */
public class GetClientsData {
    private ClientDAO clientDAO = new JDBCClientDAO();
    private CreditCardDAO creditCardDAO = new JDBCCreditCardDAO();
    private AccountDAO accountDAO = new JDBCAccountDAO();

    public Client getClient(int clientID){
        return clientDAO.read(clientID);
    }

    public List<CreditCard> getCreditCards(Client client) {
        List<Account> accountList = accountDAO.readUsingClientID(client.getClientID());
        List<CreditCard> creditCardList = new ArrayList<>();

        for(Account account : accountList){
            creditCardList.addAll(creditCardDAO.readUsingAccountID(account.getAccountID()));
        }
        return creditCardList;
    }

    public List<String> getCreditCardsStr(Client client){
        List<Account> accountList = accountDAO.readUsingClientID(client.getClientID());
        List<CreditCard> creditCardList = new ArrayList<>();
        List<String> creditCardStrList = new ArrayList<>();

        for(Account account : accountList){
            creditCardList.addAll(creditCardDAO.readUsingAccountID(account.getAccountID()));
        }

        for(CreditCard creditCard : creditCardList){
            creditCardStrList.add(CreditCard.cardNumberToString(creditCard.getCardID()));
        }

        return creditCardStrList;
    }

    public List<String> getCreditCardsStr(List<CreditCard> creditCardList){
        List<String> creditCardStrList = new ArrayList<>();

        for(CreditCard creditCard : creditCardList){
            creditCardStrList.add(CreditCard.cardNumberToString(creditCard.getCardID()));
        }
        return creditCardStrList;
    }

    public List<Account> getAccounts(Client client) {
        return accountDAO.readUsingClientID(client.getClientID());
    }

    public CreditCard getCreditCardInfo(long cardID){
        return creditCardDAO.read(cardID);
    }

    public List<Client> getClientsList(){
        return clientDAO.readAll();
    }

    public List<CreditCard> getAllCreditCards(){
        return creditCardDAO.readAll();
    }
}
