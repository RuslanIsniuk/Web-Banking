package ua.rd.webbanking.dao.impl;

import ua.rd.webbanking.dao.AccountDAO;
import ua.rd.webbanking.dao.ClientDAO;
import ua.rd.webbanking.dao.CreditCardDAO;
import ua.rd.webbanking.dao.PaymentDAO;
import ua.rd.webbanking.entities.Account;
import ua.rd.webbanking.entities.Client;
import ua.rd.webbanking.entities.CreditCard;
import ua.rd.webbanking.entities.Payment;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

public class JDBCPaymentDAOTest {
    static int clientID;
    static long cardID;
    static int accountID;
    private static ClientDAO clientDAO = new JDBCClientDAO();
    private static AccountDAO accountDAO = new JDBCAccountDAO();
    private static CreditCardDAO creditCardDAO = new JDBCCreditCardDAO();
    private static PaymentDAO paymentDAO = new JDBCPaymentDAO();
    private static Client client = new Client();
    private static Account account = new Account();
    private static CreditCard creditCard = new CreditCard();

    @BeforeClass
    public static void createBeforeTest() throws Exception{
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
        account.setAccountID(accountID);
        accountDAO.insert(account);

        creditCard.setCardAccount(account);
        creditCard.setCardID(9999999999999999l);
        creditCard.setCardStatus("active");
        creditCard.setCardValidDate("2018-12-30");
        creditCard.setCardPIN("test");
        cardID = creditCardDAO.insert(creditCard);
        creditCard.setCardID(cardID);
    }

    @AfterClass
    public static void deleteAfterTest() throws Exception{
        creditCardDAO.delete(cardID);
        accountDAO.delete(accountID);
        clientDAO.delete(clientID);
    }

    @Test
    public void read() throws Exception {
        Payment payment = new Payment();
        Payment payment1;
        BigDecimal amount = new BigDecimal("200.00");

        payment.setPaymentCreditCard(creditCard);
        payment.setPaymentDestination("TestType");
        payment.setPaymentDate();
        payment.setPaymentAmount(amount);

        paymentDAO.insert(payment);
        payment1 = paymentDAO.read(payment.getPaymentID());
        paymentDAO.delete(payment.getPaymentID());

        assertEquals(payment, payment1);
    }

    @Test
    public void readAll(){
        Payment payment = new Payment();
        Payment payment1 = new Payment();
        List<Payment> paymentList;
        BigDecimal amount = new BigDecimal("200.00");
        BigDecimal amount2 = new BigDecimal("400.00");

        payment.setPaymentCreditCard(creditCard);
        payment.setPaymentDestination("TestType");
        payment.setPaymentDate();
        payment.setPaymentAmount(amount);

        payment1.setPaymentCreditCard(creditCard);
        payment1.setPaymentDestination("TestType2");
        payment1.setPaymentDate();
        payment1.setPaymentAmount(amount2);

        paymentDAO.insert(payment);
        paymentDAO.insert(payment1);
        paymentList = paymentDAO.readUsingCardID(cardID);
        paymentDAO.delete(payment1.getPaymentID());
        paymentDAO.delete(payment.getPaymentID());

        assertEquals(payment.getPaymentDestination(), paymentList.get(0).getPaymentDestination());
        assertEquals(payment1.getPaymentDestination(), paymentList.get(1).getPaymentDestination());
    }

    @Test
    public void create() throws Exception {
        Payment payment = new Payment();
        BigDecimal amount = new BigDecimal("200.00");

        payment.setPaymentCreditCard(creditCard);
        payment.setPaymentDestination("TestType");
        payment.setPaymentDate();
        payment.setPaymentAmount(amount);

        paymentDAO.insert(payment);
        paymentDAO.delete(payment.getPaymentID());
    }

    @Test
    public void update() throws Exception {
        Payment payment = new Payment();
        Payment payment1;
        BigDecimal amount = new BigDecimal("200.00");
        BigDecimal amount2 = new BigDecimal("400.00");

        payment.setPaymentCreditCard(creditCard);
        payment.setPaymentDestination("TestType");
        payment.setPaymentDate();
        payment.setPaymentAmount(amount);

        paymentDAO.insert(payment);

        payment.setPaymentDestination("NEWTestType");
        payment.setPaymentAmount(amount2);
        payment.setPaymentID(payment.getPaymentID());
        paymentDAO.update(payment);
        payment1 = paymentDAO.read(payment.getPaymentID());
        paymentDAO.delete(payment.getPaymentID());

        assertEquals(payment, payment1);
    }

    @Test
    public void delete() throws Exception {
        Payment payment = new Payment();
        Payment payment1;
        Payment payment2 = new Payment();
        BigDecimal amount = new BigDecimal("200.00");

        payment.setPaymentCreditCard(creditCard);
        payment.setPaymentDestination("TestType");
        payment.setPaymentDate();
        payment.setPaymentAmount(amount);

        paymentDAO.insert(payment);
        paymentDAO.delete(payment.getPaymentID());
        payment1 = paymentDAO.read(payment.getPaymentID());

        assertEquals(payment2, payment1);
    }

}