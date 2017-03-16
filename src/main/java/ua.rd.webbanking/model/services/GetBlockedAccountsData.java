package electronicPaymentSystem.model.services;

import electronicPaymentSystem.entities.Account;
import electronicPaymentSystem.entities.Client;
import electronicPaymentSystem.entities.CreditCard;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Руслан on 26.02.2017.
 */
public class GetBlockedAccountsData {
    private GetClientsData getClientsData = new GetClientsData();

    public List<CreditCard> getBlockedCardsList() {
        List<CreditCard> creditCardList = getClientsData.getAllCreditCards();
        List<CreditCard> blockedCreditCardList = new ArrayList<>();

        for (CreditCard creditCard : creditCardList) {
            if ("block".equals(creditCard.getCardStatus())) {
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
