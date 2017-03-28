package ua.rd.webbanking.model.services;

import ua.rd.webbanking.dao.AccountDAO;
import ua.rd.webbanking.dao.ClientDAO;
import ua.rd.webbanking.dao.CreditCardDAO;
import ua.rd.webbanking.dao.impl.JDBCAccountDAO;
import ua.rd.webbanking.dao.impl.JDBCClientDAO;
import ua.rd.webbanking.dao.impl.JDBCCreditCardDAO;
import ua.rd.webbanking.entities.Account;
import ua.rd.webbanking.entities.Client;
import ua.rd.webbanking.entities.CreditCard;

import java.util.ArrayList;
import java.util.List;

public class GetClientsData {
    private ClientDAO clientDAO = new JDBCClientDAO();
    private CreditCardDAO creditCardDAO = new JDBCCreditCardDAO();
    private AccountDAO accountDAO = new JDBCAccountDAO();

    public Client getClient(int clientID) {
        return clientDAO.read(clientID);
    }

    public List<CreditCard> getCreditCards(Client client) {
        List<Account> accountList = accountDAO.readUsingClientID(client.getClientID());
        List<CreditCard> creditCardList = new ArrayList<>();

        for (Account account : accountList) {
            creditCardList.addAll(creditCardDAO.readUsingAccountID(account.getAccountID()));
        }
        return creditCardList;
    }

    public List<String> getCreditCardsStr(Client client) {
        List<Account> accountList = accountDAO.readUsingClientID(client.getClientID());
        List<CreditCard> creditCardList = new ArrayList<>();
        List<String> creditCardStrList = new ArrayList<>();

        for (Account account : accountList) {
            creditCardList.addAll(creditCardDAO.readUsingAccountID(account.getAccountID()));
        }

        for (CreditCard creditCard : creditCardList) {
            creditCardStrList.add(CreditCard.cardNumberToString(creditCard.getCardID()));
        }

        return creditCardStrList;
    }

    public List<String> getCreditCardsStr(List<CreditCard> creditCardList) {
        List<String> creditCardStrList = new ArrayList<>();

        for (CreditCard creditCard : creditCardList) {
            creditCardStrList.add(CreditCard.cardNumberToString(creditCard.getCardID()));
        }
        return creditCardStrList;
    }

    public List<Account> getAccounts(Client client) {
        return accountDAO.readUsingClientID(client.getClientID());
    }

    public CreditCard getCreditCardInfo(long cardID) {
        return creditCardDAO.read(cardID);
    }

    public List<Client> getClientsList() {
        List<Client> finalClientList = new ArrayList<>();
        List<Client> clientList = clientDAO.readAll();
        for (Client clientFromList : clientList) {
            if (!(clientFromList.isAdminFlag())) {
                finalClientList.add(clientFromList);
            }
        }
        return finalClientList;
    }

    public List<CreditCard> getAllCreditCards() {
        return creditCardDAO.readAll();
    }
}
