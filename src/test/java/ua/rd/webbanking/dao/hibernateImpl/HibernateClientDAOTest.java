package ua.rd.webbanking.dao.hibernateImpl;

import org.junit.Before;
import org.junit.Test;
import ua.rd.webbanking.dao.ClientDAO;
import ua.rd.webbanking.entities.Client;

import java.util.List;


import static org.junit.Assert.*;

public class HibernateClientDAOTest {
    private ClientDAO clientDAO = new HibernateClientDAO();
    private Client client;
    private Client client2;

    @Before
    public void setUp() {
        client = new Client();
        client.setClientLogin("testLogin");
        client.setClientPass("testPass");
        client.setClientFullName("testFullName");
        client.setAdminFlag(false);

        client2 = new Client();
        client2.setClientLogin("testLogin3");
        client2.setClientPass("testPass3");
        client2.setClientFullName("testFullName3");
        client2.setAdminFlag(false);

    }

    @Test
    public void readByClientID() throws Exception {
        int clientID = clientDAO.insert(client);
        Client clientActual = clientDAO.read(clientID);
        clientDAO.delete(clientID);

        assertEquals(clientActual.getClientFullName(),client.getClientFullName());
    }

    @Test
    public void readByLogin() throws Exception {
        int clientID = clientDAO.insert(client);
        Client clientActual = clientDAO.readByLogin("testLogin","testPass");
        clientDAO.delete(clientID);

        assertEquals(clientActual.getClientFullName(),client.getClientFullName());
    }

    @Test
    public void insert() throws Exception {
        int clientID = clientDAO.insert(client);
        Client clientExpected = clientDAO.read(clientID);
        clientDAO.delete(clientID);

        assertEquals(client.getClientFullName(),clientExpected.getClientFullName());
    }

    @Test
    public void readAll() throws Exception {
        int clientID1 = clientDAO.insert(client);
        int clientID2 = clientDAO.insert(client2);

        List<Client> clientList = clientDAO.readAll();

        clientDAO.delete(clientID1);
        clientDAO.delete(clientID2);

        assertEquals(clientList.get(0).getClientFullName(),client.getClientFullName());
        assertEquals(clientList.get(1).getClientFullName(),client2.getClientFullName());
    }

    @Test
    public void update() throws Exception {
        int clientID = clientDAO.insert(client);
        client.setClientLogin("testLogin2");
        client.setClientPass("testPass2");
        client.setClientFullName("testFullName2");
        clientDAO.update(client);
        Client clientActual = clientDAO.read(clientID);
        clientDAO.delete(clientID);

        assertEquals(client.getClientFullName(),clientActual.getClientFullName());
    }

    @Test
    public void delete() throws Exception {
        int clientID = clientDAO.insert(client);
        clientDAO.delete(clientID);
        Client clientActual = clientDAO.read(clientID);

        assertEquals(false,(clientActual instanceof Client));
    }

}