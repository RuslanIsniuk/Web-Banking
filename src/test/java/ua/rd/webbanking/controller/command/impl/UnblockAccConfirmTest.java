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
import ua.rd.webbanking.model.services.UnblockAccount;

import javax.servlet.http.HttpServletRequest;

import java.util.Locale;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UnblockAccConfirmTest {
    private static final String DEFAULT_OPERATION_CONFIRM_PATH = "/adminOperationImpl/OperationConfirm.jsp";
    private UnblockAccConfirm unblockAccConfirm;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    HttpServletRequest request;
    @Mock
    UnblockAccount unblockAccount;
    @Mock
    CheckClientData checkClientData;
    @Mock
    Client client;

    @Before
    public void setUp() {
        unblockAccConfirm = new UnblockAccConfirm(checkClientData, unblockAccount);
        when(client.getClientID()).thenReturn(1);
        when(request.getSession().getAttribute("clientData")).thenReturn(client);
        when(checkClientData.checkAdminFlag(1)).thenReturn(true);
        when(request.getParameter("cardID")).thenReturn("1111222233334444");
    }

    @Test
    public void defaultExecute() throws AuthorizationException, ServiceException {
        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);
        String actualPathToJSP = unblockAccConfirm.execute(request);
        assertEquals(DEFAULT_OPERATION_CONFIRM_PATH, actualPathToJSP);
        verify(unblockAccount).unblockConfirm(argument.capture());
    }

    @Test(expected = AuthorizationException.class)
    public void executeWithWrongData() throws AuthorizationException {
        when(checkClientData.checkAdminFlag(1)).thenReturn(false);
        unblockAccConfirm.execute(request);
    }
}