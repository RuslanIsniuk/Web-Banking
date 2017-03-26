package ua.rd.webbanking.model.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.rd.webbanking.dao.AccountDAO;
import ua.rd.webbanking.dao.ClientDAO;
import ua.rd.webbanking.dao.CreditCardDAO;
import ua.rd.webbanking.entities.Account;
import ua.rd.webbanking.entities.Client;
import ua.rd.webbanking.entities.CreditCard;
import ua.rd.webbanking.model.exceptions.ServiceException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateNewClientPerAreaTest {
    private CreateNewClientPerArea createNewClientPerArea;
    @Mock
    ClientDAO clientDAO;
    @Mock
    AccountDAO accountDAO;
    @Mock
    CreditCardDAO creditCardDAO;
    @Mock
    Client client;

    @Before
    public void setUp() {
        createNewClientPerArea = new CreateNewClientPerArea(clientDAO, accountDAO, creditCardDAO);
        List<Client> clientList = new ArrayList<>();
        List<Account> accountList = new ArrayList<>();
        List<CreditCard> creditCardList = new ArrayList<>();
        Account account = new Account();
        account.setAccountID(111111);
        CreditCard creditCard = new CreditCard();
        creditCard.setCardID(1111222233334444l);
        clientList.add(client);
        accountList.add(account);
        creditCardList.add(creditCard);
        when(clientDAO.readAll()).thenReturn(clientList);
        when(accountDAO.readAll()).thenReturn(accountList);
        when(creditCardDAO.readAll()).thenReturn(creditCardList);
    }

    @Test(expected = ServiceException.class)
    public void createNewClientWithDuplicateLogin() throws ServiceException {
        when(client.getClientLogin()).thenReturn("testLogin");
        Client clientActual = new Client();
        clientActual.setClientLogin("testLogin");
        createNewClientPerArea.createNewClient(clientActual);
    }

    @Test(expected = ServiceException.class)
    public void createNewClientWithDuplicateFullName() throws ServiceException {
        when(client.getClientLogin()).thenReturn("testLogin");
        when(client.getClientFullName()).thenReturn("testFullName");
        Client clientActual = new Client();
        clientActual.setClientLogin("test2Login");
        clientActual.setClientFullName("testFullName");
        createNewClientPerArea.createNewClient(clientActual);
    }

    @Test(expected = ServiceException.class)
    public void createNewAccountWithDuplicateAccID() throws ServiceException {
        Account accountActual = new Account();
        accountActual.setAccountID(111111);
        createNewClientPerArea.createNewAccount(accountActual);
    }

    @Test(expected = ServiceException.class)
    public void createNewCreditCardWithDuplicateCardID() throws ServiceException {
        CreditCard creditCardActual = new CreditCard();
        creditCardActual.setCardID(1111222233334444l);
        createNewClientPerArea.createNewCreditCard(creditCardActual);
    }
}