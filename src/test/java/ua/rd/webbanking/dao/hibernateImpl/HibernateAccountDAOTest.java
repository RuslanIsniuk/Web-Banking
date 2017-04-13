package ua.rd.webbanking.dao.hibernateImpl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.rd.webbanking.dao.AccountDAO;
import ua.rd.webbanking.dao.ClientDAO;
import ua.rd.webbanking.entities.Account;
import ua.rd.webbanking.entities.Client;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class HibernateAccountDAOTest {
    private AccountDAO accountDAO = new HibernateAccountDAO();
    private ClientDAO clientDAO = new HibernateClientDAO();
    private Client client;
    private Account account;
    private Account account2;

    @Before
    public void setUp(){
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

        BigDecimal balance2 = new BigDecimal("10.00");
        account2 = new Account();
        account2.setAccountClient(client);
        account2.setAccountStatus("test1");
        account2.setAccountDateOpen("2014-10-16");
        account2.setAccountID(9999968);
        account2.setAccountBalance(balance2);

    }

    @After
    public void finish(){
        clientDAO.delete(client.getClientID());
    }

    @Test
    public void read() throws Exception {
        accountDAO.insert(account);
        Account accountActual = accountDAO.read(account.getAccountID());
        accountDAO.delete(account.getAccountID());

        assertEquals(account.getAccountID(),accountActual.getAccountID());
    }

    @Test
    public void readUsingClientID() throws Exception {
        accountDAO.insert(account);
        List<Account> accountList = accountDAO.readUsingClientID(account.getAccountClient().getClientID());
        accountDAO.delete(account.getAccountID());

        assertEquals(account.getAccountID(),accountList.get(0).getAccountID());
    }

    @Test
    public void readAll() throws Exception {
        accountDAO.insert(account);
        accountDAO.insert(account2);

        List<Account> accountList = accountDAO.readAll();

        accountDAO.delete(account.getAccountID());
        accountDAO.delete(account2.getAccountID());

        assertEquals(accountList.get(1).getAccountID(),account.getAccountID());
        assertEquals(accountList.get(0).getAccountID(),account2.getAccountID());
    }

    @Test
    public void insert() throws Exception {
        accountDAO.insert(account);
        Account accountActual = accountDAO.read(account.getAccountID());
        accountDAO.delete(account.getAccountID());

        assertEquals(account.getAccountID(),accountActual.getAccountID());
    }

    @Test
    public void update() throws Exception {
        accountDAO.insert(account);
        BigDecimal balance = new BigDecimal("10.00");
        account.setAccountClient(client);
        account.setAccountStatus("test1");
        account.setAccountDateOpen("2014-10-16");
        account.setAccountBalance(balance);

        accountDAO.update(account);
        Account accountActual = accountDAO.read(account.getAccountID());
        accountDAO.delete(account.getAccountID());

        assertEquals(account.getAccountID(),accountActual.getAccountID());
    }

    @Test
    public void delete() throws Exception {
        accountDAO.insert(account);
        accountDAO.delete(account.getAccountID());
        Account accountActual = accountDAO.read(account.getAccountID());

        assertEquals(false,(accountActual instanceof Account));
    }

}