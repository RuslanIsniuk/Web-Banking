package ua.rd.webbanking.dao.hibernateImpl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.rd.webbanking.dao.AccountDAO;
import ua.rd.webbanking.dao.ClientDAO;
import ua.rd.webbanking.dao.CreditCardDAO;
import ua.rd.webbanking.entities.Account;
import ua.rd.webbanking.entities.Client;
import ua.rd.webbanking.entities.CreditCard;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;


public class HibernateCreditCardDAOTest {
    private CreditCardDAO creditCardDAO = new HibernateCreditCardDAO();
    private AccountDAO accountDAO = new HibernateAccountDAO();
    private ClientDAO clientDAO = new HibernateClientDAO();
    private Client client;
    private Account account;
    private CreditCard creditCard;
    private CreditCard creditCard2;

    @Before
    public void setUp() {
        client = new Client();
        client.setClientLogin("testLogin");
        client.setClientPass("testPass");
        client.setClientFullName("testFullName");
        client.setAdminFlag(false);
        clientDAO.insert(client);

        BigDecimal balance = new BigDecimal("0.00");
        account = new Account();
        account.setAccountClient(client);
        account.setAccountStatus("test");
        account.setAccountDateOpen("2014-10-16");
        account.setAccountID(9999998);
        account.setAccountBalance(balance);
        accountDAO.insert(account);

        creditCard = new CreditCard();
        creditCard.setCardAccount(account);
        creditCard.setCardID(9999999999999999l);
        creditCard.setCardStatus("active");
        creditCard.setCardValidDate("2018-12-30");
        creditCard.setCardPIN("test");

        creditCard2 = new CreditCard();
        creditCard2.setCardAccount(account);
        creditCard2.setCardID(9999999999999998l);
        creditCard2.setCardStatus("active");
        creditCard2.setCardValidDate("2012-10-10");
        creditCard2.setCardPIN("test1");
    }

    @After
    public void finish() {
        accountDAO.delete(account.getAccountID());
        clientDAO.delete(client.getClientID());
    }

    @Test
    public void readUsingAccountID() throws Exception {
        creditCardDAO.insert(creditCard);
        List<CreditCard> creditCardList = creditCardDAO.readUsingAccountID(creditCard.getCardAccount().getAccountID());
        creditCardDAO.delete(creditCard.getCardID());

        assertEquals(creditCardList.get(0).getCardID(), creditCard.getCardID());
    }

    @Test
    public void read() throws Exception {
        creditCardDAO.insert(creditCard);
        CreditCard creditCardActual = creditCardDAO.read(creditCard.getCardID());
        creditCardDAO.delete(creditCard.getCardID());

        assertEquals(creditCard.getCardID(), creditCardActual.getCardID());
    }

    @Test
    public void readAll() throws Exception {
        creditCardDAO.insert(creditCard);
        creditCardDAO.insert(creditCard2);

        List<CreditCard> creditCardList = creditCardDAO.readAll();

        creditCardDAO.delete(creditCard.getCardID());
        creditCardDAO.delete(creditCard2.getCardID());

        assertEquals(creditCardList.get(1).getCardID(), creditCard.getCardID());
        assertEquals(creditCardList.get(0).getCardID(), creditCard2.getCardID());

    }

    @Test
    public void insert() throws Exception {
        creditCardDAO.insert(creditCard);
        CreditCard creditCardActual = creditCardDAO.read(creditCard.getCardID());
        creditCardDAO.delete(creditCard.getCardID());

        assertEquals(creditCard.getCardID(), creditCardActual.getCardID());
    }

    @Test
    public void update() throws Exception {
        creditCardDAO.insert(creditCard);
        creditCard.setCardAccount(account);
        creditCard.setCardStatus("block");
        creditCard.setCardValidDate("2018-12-30");
        creditCard.setCardPIN("test");

        creditCardDAO.update(creditCard);
        CreditCard creditCardActual = creditCardDAO.read(creditCard.getCardID());
        creditCardDAO.delete(creditCard.getCardID());

        assertEquals(creditCardActual.getCardID(), creditCard.getCardID());
    }

    @Test
    public void delete() throws Exception {
        creditCardDAO.insert(creditCard);
        creditCardDAO.delete(creditCard.getCardID());
        CreditCard creditCardActual = creditCardDAO.read(creditCard.getCardID());

        assertEquals(false, (creditCardActual instanceof CreditCard));
    }

}