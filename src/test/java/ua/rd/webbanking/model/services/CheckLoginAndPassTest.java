package ua.rd.webbanking.model.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.rd.webbanking.dao.ClientDAO;
import ua.rd.webbanking.entities.Client;
import ua.rd.webbanking.model.exceptions.ServiceException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CheckLoginAndPassTest {
    private CheckLoginAndPass checkLoginAndPass;
    @Mock
    ClientDAO clientDAO;
    @Mock
    Client client;

    @Before
    public void setUp(){
        checkLoginAndPass = new CheckLoginAndPass(clientDAO);
        when(clientDAO.readByLogin("clientLogin","clientPass")).thenReturn(client);
        when(clientDAO.readByLogin("exception","exception")).thenReturn(null);
    }

    @Test
    public void validateLoginDataDefault() throws ServiceException {
        Client clientActual = checkLoginAndPass.validateLoginData("clientLogin","clientPass");
        assertEquals(client,clientActual);
    }

    @Test(expected = ServiceException.class)
    public void validateLoginDataWithServiceException() throws ServiceException {
        checkLoginAndPass.validateLoginData("exception","exception");
    }
}