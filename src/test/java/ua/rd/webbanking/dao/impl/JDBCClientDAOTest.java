package ua.rd.webbanking.dao.impl;



import org.junit.Test;
import ua.rd.webbanking.dao.ClientDAO;
import ua.rd.webbanking.entities.Client;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Руслан on 22.12.2016.
 */
public class JDBCClientDAOTest {

    @Test
    public void read() throws Exception {               // перевірка на стандартне считування даних з БД
        Client clientTest = new Client();
        Client clientTest2;
        int clientTestID;

        clientTest.setClientLogin("TestLogR");
        clientTest.setClientPass("TestPassR");
        clientTest.setClientFullName("TestNameR");
        clientTest.setAdminFlag(false);

        JDBCClientDAO jdbcClientDAO = new JDBCClientDAO();
        clientTestID = jdbcClientDAO.create(clientTest);
        clientTest.setClientID(clientTestID);
        clientTest2 = jdbcClientDAO.read(clientTestID);
        jdbcClientDAO.delete(clientTest2.getClientID());

        assertEquals(clientTest,clientTest2);
    }

    @Test
    public void readAll(){
        Client clientTest = new Client();
        Client clientTest2 = new Client();
        List<Client> clientList;

        clientTest.setClientLogin("TestLogR");
        clientTest.setClientPass("TestPassR");
        clientTest.setClientFullName("TestNameR");
        clientTest.setAdminFlag(false);

        clientTest2.setClientLogin("TestLogR2");
        clientTest2.setClientPass("TestPassR2");
        clientTest2.setClientFullName("TestNameR2");
        clientTest2.setAdminFlag(false);

        ClientDAO clientDAO = new JDBCClientDAO();
        clientTest.setClientID(clientDAO.create(clientTest));
        clientTest2.setClientID(clientDAO.create(clientTest2));
        clientList = clientDAO.readAll();
        clientDAO.delete(clientList.get(0).getClientID());
        clientDAO.delete(clientList.get(1).getClientID());

        assertEquals(2,clientList.size());
        assertEquals(clientTest.getClientFullName(),clientList.get(0).getClientFullName());
        assertEquals(clientTest2.getClientFullName(),clientList.get(1).getClientFullName());
    }

    @Test
    public void readWhenNullDataGiven() throws Exception{                 // перевірка на спробу считати дані по нульовому ID з БД
        Client clientTest = new Client();
        Client clientTest2;
        int clientTestID ;
        JDBCClientDAO jdbcClientDAO = new JDBCClientDAO();

        clientTest.setClientLogin("TestLogR");
        clientTest.setClientPass("TestPassR");
        clientTest.setClientFullName("TestNameR");
        clientTest.setAdminFlag(false);

        clientTestID = jdbcClientDAO.create(clientTest);
        clientTest2 = jdbcClientDAO.read(0);
        jdbcClientDAO.delete(clientTestID);

        assertEquals(null,clientTest2.getClientLogin());
        assertEquals(null,clientTest2.getClientFullName());

    }


    @Test
    public void create() throws Exception {         // перевірка на стандартне додавання нового запису в БД
        Client clientTest = new Client();
        Client clientTest2;
        JDBCClientDAO jdbcClientDAO = new JDBCClientDAO();
        int clientTestID = 0;

        clientTest.setClientLogin("TestLogI");
        clientTest.setClientPass("TestPassI");
        clientTest.setClientFullName("TestNameI");
        clientTest.setAdminFlag(false);

        clientTestID = jdbcClientDAO.create(clientTest);
        clientTest.setClientID(clientTestID);
        clientTest2 = jdbcClientDAO.read(clientTestID);
        jdbcClientDAO.delete(clientTestID);

        assertEquals(clientTest,clientTest2);
    }

    @Test
    public void update() throws Exception {
                                            //перевірка на стандартну заміну даних
        Client client = new Client();
        Client client1;
        ClientDAO clientDAO = new JDBCClientDAO();
        int clientID;

        client.setClientLogin("TestLogD");
        client.setClientPass("TestPassD");
        client.setClientFullName("TestNameD");
        client.setAdminFlag(false);

        clientID = clientDAO.create(client);
        client.setClientID(clientID);
        client.setClientLogin("NewTestLogD");
        client.setClientPass("NewTestPassD");
        client.setClientFullName("NewTestNameD");

        clientDAO.update(client);
        client1 = clientDAO.read(clientID);
        clientDAO.delete(clientID);

        assertEquals(client,client1);
    }

    @Test
    public void delete() throws Exception {
        Client clientTest = new Client();
        Client clientTest2;
        int clientTestID = 0;
        JDBCClientDAO jdbcClientDAO = new JDBCClientDAO();

        clientTest.setClientLogin("TestLogD");
        clientTest.setClientPass("TestPassD");
        clientTest.setClientFullName("TestNameD");

        clientTestID = jdbcClientDAO.create(clientTest);
        jdbcClientDAO.delete(clientTestID);
        clientTest2 = jdbcClientDAO.read(clientTestID);

        assertEquals(null,clientTest2.getClientLogin());
        assertEquals(null,clientTest2.getClientPass());
        assertEquals(null,clientTest2.getClientFullName());
    }
}