package ua.rd.webbanking.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import ua.rd.webbanking.entities.Account;
import ua.rd.webbanking.entities.Client;
import ua.rd.webbanking.entities.CreditCard;

import java.util.ArrayList;
import java.util.List;

public class GetBlockedAccountsData {
    @Autowired
    private GetClientsData getClientsData;

    public List<CreditCard> getBlockedCardsList() {
        List<CreditCard> creditCardList = getClientsData.getAllCreditCards();
        List<CreditCard> blockedCreditCardList = new ArrayList<>();

        for (CreditCard creditCard : creditCardList) {
            if ("blocked".equals(creditCard.getCardStatus())) {
                blockedCreditCardList.add(creditCard);
            }
        }
        return blockedCreditCardList;
    }

    public List<Account> getAccountsListByBlockedCards() {
        List<CreditCard> creditCardList = getBlockedCardsList();
        List<Account> accountList = new ArrayList<>();

        for (CreditCard creditCard : creditCardList) {
            accountList.add(creditCard.getCardAccount());
        }
        return accountList;
    }

    public List<Client> getClientsListByBlockedCards() {
        List<Account> accountList = getAccountsListByBlockedCards();
        List<Client> clientList = new ArrayList<>();

        for (Account account : accountList) {
            clientList.add(account.getAccountClient());
        }
        return clientList;
    }
}
