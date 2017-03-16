package ua.rd.webbanking.dao.impl;

import ua.rd.webbanking.dao.AccountDAO;
import ua.rd.webbanking.dao.ClientDAO;
import ua.rd.webbanking.dao.CreditCardDAO;
import ua.rd.webbanking.entities.Account;
import ua.rd.webbanking.entities.Client;
import ua.rd.webbanking.entities.CreditCard;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Руслан on 27.12.2016.
 */
public class JDBCAccountDAOTest {
    static int clientID;
    static int clientID2;
    private AccountDAO accountDAO = new JDBCAccountDAO();
    private static ClientDAO clientDAO = new JDBCClientDAO();
    private static Client client = new Client();
    private static Client client2 = new Client();

    @BeforeClass
    public static void createTestClient() throws Exception{
        client.setClientLogin("TestLog");
        client.setClientPass("TestPass");
        client.setClientFullName("TestName");
        client.setAdminFlag(false);

        client2.setClientLogin("TestLog2");
        client2.setClientPass("TestPass2");
        client2.setClientFullName("TestName2");
        client2.setAdminFlag(false);

        clientID2 = clientDAO.create(client2);
        clientID = clientDAO.create(client);
        client.setClientID(clientID);
        client2.setClientID(clientID2);
    }

    @AfterClass
    public static void deleteTestClient() throws Exception{
        clientDAO.delete(clientID);
        clientDAO.delete(clientID2);
    }

    @Test
    public void read() throws Exception {
        Account account = new Account();
        Account account1;
        BigDecimal balance = new BigDecimal("0.00");

        account.setAccountClient(client);
        account.setAccountStatus("test");
        account.setAccountDateOpen("2014-10-16");
        account.setAccountID(9999998);
        account.setAccountBalance(balance);

        accountDAO.create(account);
        account1 = accountDAO.read(account.getAccountID());
        accountDAO.delete(account.getAccountID());

        assertEquals(account,account1);
    }

    @Test
    public void readUsingClientID(){
        Account account = new Account();
        Account account1 = new Account();
        List<Account> accountList;
        BigDecimal balance = new BigDecimal("0.00");

        account.setAccountClient(client);
        account.setAccountStatus("test1");
        account.setAccountDateOpen("2014-10-16");
        account.setAccountID(9999968);
        account.setAccountBalance(balance);

        account1.setAccountClient(client);
        account1.setAccountStatus("test2");
        account1.setAccountDateOpen("2014-10-16");
        account1.setAccountID(9999969);
        account1.setAccountBalance(balance);

        accountDAO.create(account);
        accountDAO.create(account1);
        accountList = accountDAO.readUsingClientID(clientID);
        accountDAO.delete(account.getAccountID());
        accountDAO.delete(account1.getAccountID());

        assertEquals(account.getAccountID(),accountList.get(0).getAccountID());
        assertEquals(account.getAccountClient(),accountList.get(0).getAccountClient());

        assertEquals(account1.getAccountID(),accountList.get(1).getAccountID());
        assertEquals(account1.getAccountClient().getClientID(),accountList.get(1).getAccountClient().getClientID());

    }

    @Test
    public void readAll(){
        Account account = new Account();
        Account account1 = new Account();
        Account account2 = new Account();
        List<Account> accountList;
        BigDecimal balance = new BigDecimal("0.00");

        account.setAccountClient(client);
        account.setAccountStatus("test1");
        account.setAccountDateOpen("2014-10-16");
        account.setAccountID(9999968);
        account.setAccountBalance(balance);

        account1.setAccountClient(client);
        account1.setAccountStatus("test2");
        account1.setAccountDateOpen("2014-10-16");
        account1.setAccountID(9999969);
        account1.setAccountBalance(balance);

        account2.setAccountClient(client2);
        account2.setAccountStatus("test3");
        account2.setAccountDateOpen("2014-10-16");
        account2.setAccountID(9999967);
        account2.setAccountBalance(balance);

        accountDAO.create(account);
        accountDAO.create(account1);
        accountDAO.create(account2);
        accountList = accountDAO.readAll();
        accountDAO.delete(account.getAccountID());
        accountDAO.delete(account1.getAccountID());
        accountDAO.delete(account2.getAccountID());

        assertEquals(account2.getAccountID(),accountList.get(0).getAccountID());
        assertEquals(account2.getAccountClient(),accountList.get(0).getAccountClient());

        assertEquals(account.getAccountID(),accountList.get(1).getAccountID());
        assertEquals(account.getAccountClient().getClientID(),accountList.get(1).getAccountClient().getClientID());

        assertEquals(account1.getAccountID(),accountList.get(2).getAccountID());
        assertEquals(account1.getAccountClient().getClientID(),accountList.get(2).getAccountClient().getClientID());

    }

    @Test
    public void create() throws Exception {
        Account account = new Account();
        Account account1;
        BigDecimal balance = new BigDecimal("0.00");

        account.setAccountClient(client);
        account.setAccountStatus("test");
        account.setAccountDateOpen("2014-10-16");
        account.setAccountID(9999999);
        account.setAccountBalance(balance);

        accountDAO.create(account);
        account1 = accountDAO.read(account.getAccountID());
        accountDAO.delete(account.getAccountID());

        assertEquals(account,account1);
    }

    @Test
    public void update() throws Exception {
        Account account = new Account();
        Account account1;
        BigDecimal balance1 = new BigDecimal("0");
        BigDecimal balance2 = new BigDecimal("1200.00");


        account.setAccountClient(client);
        account.setAccountStatus("test");
        account.setAccountDateOpen("2014-10-16");
        account.setAccountID(9999999);
        account.setAccountBalance(balance1);

        accountDAO.create(account);

        account.setAccountBalance(balance2);
        account.setAccountStatus("test2");
        account.setAccountDateOpen("2010-12-30");

        accountDAO.update(account);
        account1 = accountDAO.read(account.getAccountID());
        accountDAO.delete(account.getAccountID());

        assertEquals(account,account1);
    }

    @Test
    public void delete() throws Exception {
        Account account = new Account();
        Account account1 ;
        Account account2 = new Account();
        BigDecimal balance1 = new BigDecimal("0.00");

        account.setAccountClient(client);
        account.setAccountStatus("test");
        account.setAccountDateOpen("2014-10-16");
        account.setAccountID(9999999);
        account.setAccountBalance(balance1);

        accountDAO.create(account);
        accountDAO.delete(account.getAccountID());
        account1 = accountDAO.read(clientID);

        assertEquals(account2,account1);
    }

}