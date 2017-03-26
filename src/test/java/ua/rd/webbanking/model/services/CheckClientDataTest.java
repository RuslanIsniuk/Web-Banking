package ua.rd.webbanking.model.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.rd.webbanking.controller.exceptions.AuthorizationException;
import ua.rd.webbanking.dao.ClientDAO;
import ua.rd.webbanking.entities.Client;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CheckClientDataTest {
    private CheckClientData checkClientData;
    @Mock
    ClientDAO clientDAO;
    @Mock
    Client client;

    @Before
    public void setUp(){
        checkClientData = new CheckClientData(clientDAO);
        when(clientDAO.read(1)).thenReturn(client);
        when(clientDAO.read(0)).thenReturn(null);
    }

    @Test
    public void checkAdminFlagIsTrue() throws AuthorizationException {
        when(client.isAdminFlag()).thenReturn(true);
        boolean actualIsAdminFlag = checkClientData.checkAdminFlag(1);
        assertEquals(actualIsAdminFlag,true);
    }

    @Test
    public void checkAdminFlagIsFalse() throws AuthorizationException{
        when(client.isAdminFlag()).thenReturn(false);
        boolean actualIsAdminFlag = checkClientData.checkAdminFlag(1);
        assertEquals(actualIsAdminFlag,false);
    }

    @Test(expected = AuthorizationException.class)
    public void checkAdminFlagWithAuthorizationException() throws AuthorizationException {
        checkClientData.checkAdminFlag(0);
    }
}