package ua.rd.webbanking.dao.hibernateImpl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.rd.webbanking.dao.AccountDAO;
import ua.rd.webbanking.dao.ClientDAO;
import ua.rd.webbanking.dao.CreditCardDAO;
import ua.rd.webbanking.dao.PaymentDAO;
import ua.rd.webbanking.entities.Account;
import ua.rd.webbanking.entities.Client;
import ua.rd.webbanking.entities.CreditCard;
import ua.rd.webbanking.entities.Payment;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

public class HibernatePaymentDAOTest {
    private PaymentDAO paymentDAO = new HibernatePaymentDAO();
    private CreditCardDAO creditCardDAO = new HibernateCreditCardDAO();
    private AccountDAO accountDAO = new HibernateAccountDAO();
    private ClientDAO clientDAO = new HibernateClientDAO();
    private Client client;
    private Account account;
    private CreditCard creditCard;
    private Payment payment;
    private Payment payment2;

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
        creditCardDAO.insert(creditCard);

        payment = new Payment();
        BigDecimal amount = new BigDecimal("200.00");

        payment.setPaymentCreditCard(creditCard);
        payment.setPaymentDestination("TestType");
        payment.setPaymentDate();
        payment.setPaymentAmount(amount);

        payment2 = new Payment();
        BigDecimal amount2 = new BigDecimal("100.00");

        payment2.setPaymentCreditCard(creditCard);
        payment2.setPaymentDestination("TestType2");
        payment2.setPaymentDate();
        payment2.setPaymentAmount(amount2);
    }

    @After
    public void finish() {
        creditCardDAO.delete(creditCard.getCardID());
        accountDAO.delete(account.getAccountID());
        clientDAO.delete(client.getClientID());
    }

    @Test
    public void readUsingCardID() throws Exception {
        paymentDAO.insert(payment);
        paymentDAO.insert(payment2);

        List<Payment> paymentList = paymentDAO.readUsingCardID(creditCard.getCardID());

        paymentDAO.delete(payment.getPaymentID());
        paymentDAO.delete(payment2.getPaymentID());

        assertEquals(paymentList.get(0).getPaymentID(), payment.getPaymentID());
        assertEquals(paymentList.get(1).getPaymentID(), payment2.getPaymentID());
    }

    @Test
    public void read() throws Exception {
        paymentDAO.insert(payment);
        Payment paymentActual = paymentDAO.read(payment.getPaymentID());
        paymentDAO.delete(payment.getPaymentID());

        assertEquals(payment.getPaymentID(), paymentActual.getPaymentID());
    }

    @Test
    public void update() throws Exception {
        paymentDAO.insert(payment);
        BigDecimal amount = new BigDecimal("10.00");
        payment.setPaymentDestination("TestType4");
        payment.setPaymentDate();
        payment.setPaymentAmount(amount);

        paymentDAO.update(payment);
        Payment paymentActual = paymentDAO.read(payment.getPaymentID());
        paymentDAO.delete(payment.getPaymentID());

        assertEquals(payment.getPaymentID(), paymentActual.getPaymentID());
    }

    @Test
    public void insert() throws Exception {
        paymentDAO.insert(payment);
        Payment paymentActual = paymentDAO.read(payment.getPaymentID());
        paymentDAO.delete(payment.getPaymentID());

        assertEquals(payment.getPaymentID(), paymentActual.getPaymentID());
    }

    @Test
    public void delete() throws Exception {
        paymentDAO.insert(payment);
        paymentDAO.delete(payment.getPaymentID());
        Payment paymentActual = paymentDAO.read(payment.getPaymentID());

        assertEquals(false, (paymentActual instanceof Payment));
    }

}