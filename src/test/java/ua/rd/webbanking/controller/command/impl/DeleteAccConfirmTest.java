package ua.rd.webbanking.controller.command.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.rd.webbanking.controller.exceptions.AuthorizationException;
import ua.rd.webbanking.entities.Client;
import ua.rd.webbanking.model.exceptions.ServiceException;
import ua.rd.webbanking.model.services.CheckClientData;
import ua.rd.webbanking.model.services.DeleteAccount;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DeleteAccConfirmTest {
    private static final String DEFAULT_OPERATION_CONFIRM_PATH = "/adminOperationImpl/OperationConfirm.jsp";
    private DeleteAccConfirm deleteAccConfirm;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    HttpServletRequest request;
    @Mock
    Client client;
    @Mock
    CheckClientData checkClientData;
    @Mock
    DeleteAccount deleteAccount;

    @Before
    public void setUp() {
        deleteAccConfirm = new DeleteAccConfirm(checkClientData, deleteAccount);
        when(client.getClientID()).thenReturn(1);
        when(request.getSession().getAttribute("clientData")).thenReturn(client);
        when(checkClientData.checkAdminFlag(1)).thenReturn(true);
        when(request.getParameter("clientID")).thenReturn("1");
    }

    @Test
    public void defaultExecute() throws AuthorizationException, ServiceException {
        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        String actualPathToJSP = deleteAccConfirm.execute(request);
        assertEquals(DEFAULT_OPERATION_CONFIRM_PATH, actualPathToJSP);
        verify(deleteAccount).deleteConfirm(argument.capture());
    }

    @Test(expected = AuthorizationException.class)
    public void executeWithWrongData() throws AuthorizationException {
        when(checkClientData.checkAdminFlag(1)).thenReturn(false);
        deleteAccConfirm.execute(request);
    }
}