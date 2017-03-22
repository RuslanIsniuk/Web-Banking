package ua.rd.webbanking.dao.impl;

import ua.rd.webbanking.dao.AccountDAO;
import ua.rd.webbanking.dao.ClientDAO;
import ua.rd.webbanking.dao.CreditCardDAO;
import ua.rd.webbanking.entities.Account;
import ua.rd.webbanking.entities.Client;
import ua.rd.webbanking.entities.CreditCard;
import org.junit.*;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Руслан on 26.12.2016.
 */
public class JDBCCreditCardDAOTest {
    private static int clientID;
    private static int accountID;
    private static Client client = new Client();
    private static Account account = new Account();
    private static ClientDAO clientDAO = new JDBCClientDAO();
    private static AccountDAO accountDAO = new JDBCAccountDAO();
    private CreditCardDAO creditCardDAO = new JDBCCreditCardDAO();

    @BeforeClass
    public static void createClientAndAccount() throws Exception{
        client.setClientLogin("TestLog");
        client.setClientPass("TestPass");
        client.setClientFullName("TestName");
        clientID = clientDAO.insert(client);
        client.setClientID(clientID);

        BigDecimal balance = new BigDecimal("0.00");
        account.setAccountClient(client);
        account.setAccountStatus("test");
        account.setAccountDateOpen("2014-10-16");
        account.setAccountID(9999999);
        account.setAccountBalance(balance);
        accountID = account.getAccountID();

        accountDAO.insert(account);
    }

    @AfterClass
    public static void deleteClientAndAccount()throws Exception{
        accountDAO.delete(accountID);
        clientDAO.delete(clientID);
    }

    @Test
    public void read() throws Exception {
        CreditCard creditCard = new CreditCard();
        CreditCard creditCard1;

        creditCard.setCardAccount(account);
        creditCard.setCardID(9999999999999999l);
        creditCard.setCardStatus("active");
        creditCard.setCardValidDate("2018-12-30");
        creditCard.setCardPIN("test");

        creditCardDAO.insert(creditCard);
        creditCard1 = creditCardDAO.read(creditCard.getCardID());
        creditCardDAO.delete(creditCard.getCardID());

        assertEquals(creditCard,creditCard1);
    }

    @Test
    public void readUsingAccountID(){
        CreditCard creditCard = new CreditCard();
        CreditCard creditCard1 = new CreditCard();
        List<CreditCard> creditCardArrayList;

        creditCard.setCardAccount(account);
        creditCard.setCardID(9999999999999999l);
        creditCard.setCardStatus("active");
        creditCard.setCardValidDate("2018-12-30");
        creditCard.setCardPIN("test");

        creditCard1.setCardAccount(account);
        creditCard1.setCardID(9999999999999998l);
        creditCard1.setCardStatus("active");
        creditCard1.setCardValidDate("2012-10-10");
        creditCard1.setCardPIN("test");

        creditCardDAO.insert(creditCard);
        creditCardDAO.insert(creditCard1);
        creditCardArrayList = creditCardDAO.readUsingAccountID(accountID);
        creditCardDAO.delete(creditCard.getCardID());
        creditCardDAO.delete(creditCard1.getCardID());

        assertEquals(creditCard,creditCardArrayList.get(1));
        assertEquals(creditCard1,creditCardArrayList.get(0));
    }

    @Test
    public void readAll(){
        CreditCard creditCard = new CreditCard();
        CreditCard creditCard1 = new CreditCard();
        CreditCard creditCard2 = new CreditCard();
        List<CreditCard> creditCardArrayList;

        creditCard.setCardAccount(account);
        creditCard.setCardID(9999999999999999l);
        creditCard.setCardStatus("active");
        creditCard.setCardValidDate("2018-12-30");
        creditCard.setCardPIN("test");

        creditCard1.setCardAccount(account);
        creditCard1.setCardID(9999999999999998l);
        creditCard1.setCardStatus("active");
        creditCard1.setCardValidDate("2012-10-10");
        creditCard1.setCardPIN("test1");

        creditCard2.setCardAccount(account);
        creditCard2.setCardID(9999999999999997l);
        creditCard2.setCardStatus("active");
        creditCard2.setCardValidDate("2012-9-20");
        creditCard2.setCardPIN("test2");

        creditCardDAO.insert(creditCard);
        creditCardDAO.insert(creditCard1);
        creditCardDAO.insert(creditCard2);
        creditCardArrayList = creditCardDAO.readAll();
        creditCardDAO.delete(creditCard.getCardID());
        creditCardDAO.delete(creditCard1.getCardID());
        creditCardDAO.delete(creditCard2.getCardID());

        assertEquals(creditCard,creditCardArrayList.get(2));
        assertEquals(creditCard1,creditCardArrayList.get(1));
        assertEquals(creditCard2,creditCardArrayList.get(0));
    }

    @Test
    public void create() throws Exception {
        CreditCard creditCard = new CreditCard();
        CreditCard creditCard1;

        creditCard.setCardAccount(account);
        creditCard.setCardID(9999999999999997l);
        creditCard.setCardStatus("active");
        creditCard.setCardValidDate("2018-12-30");
        creditCard.setCardPIN("test");

        creditCardDAO.insert(creditCard);
        creditCard1 = creditCardDAO.read(creditCard.getCardID());
        creditCardDAO.delete(creditCard.getCardID());

        assertEquals(creditCard,creditCard1);
        
    }

    @Test
    public void update() throws Exception {
        CreditCard creditCard = new CreditCard();
        CreditCard creditCard1;

        creditCard.setCardAccount(account);
        creditCard.setCardID(9999999999999999l);
        creditCard.setCardStatus("active");
        creditCard.setCardValidDate("2018-12-30");
        creditCard.setCardPIN("test");

        creditCardDAO.insert(creditCard);

        creditCard.setCardStatus("active");
        creditCard.setCardValidDate("2012-10-10");
        creditCard.setCardPIN("test");

        creditCardDAO.update(creditCard);
        creditCard1 = creditCardDAO.read(creditCard.getCardID());
        creditCardDAO.delete(creditCard.getCardID());

        assertEquals(creditCard,creditCard1);
    }

    @Test
    public void delete() throws Exception {
        CreditCard creditCard = new CreditCard();
        CreditCard creditCard1;
        CreditCard creditCard2 = new CreditCard();


        creditCard.setCardAccount(account);
        creditCard.setCardID(9999999999999999l);
        creditCard.setCardStatus("active");
        creditCard.setCardValidDate("2018-12-30");
        creditCard.setCardPIN("test");

        creditCardDAO.insert(creditCard);
        creditCardDAO.delete(creditCard.getCardID());
        creditCard1 = creditCardDAO.read(creditCard.getCardID());

        assertEquals(creditCard1,creditCard2);
    }

}