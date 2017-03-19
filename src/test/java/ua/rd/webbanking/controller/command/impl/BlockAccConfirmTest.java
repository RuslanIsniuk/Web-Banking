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
import ua.rd.webbanking.model.services.BlockAccount;
import ua.rd.webbanking.model.services.CheckClientData;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BlockAccConfirmTest {
    private static final String DEFAULT_OPERATION_CONFIRM_PATH = "/cardOperationImpl/BlockAccount.jsp";
    private BlockAccConfirm blockAccConfirm;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    HttpServletRequest request;
    @Mock
    CheckClientData checkClientData;
    @Mock
    BlockAccount blockAccount;
    @Mock
    Client client;

    @Before
    public void setUp() {
        blockAccConfirm = new BlockAccConfirm(checkClientData, blockAccount);
        when(client.getClientID()).thenReturn(1);
        when(request.isRequestedSessionIdValid()).thenReturn(true);
        when(request.getSession().getAttribute("clientData")).thenReturn(client);
        when(request.getParameter("cardID")).thenReturn("1111222233334444");


    }

    @Test
    public void defaultExecute() throws AuthorizationException {
        when(checkClientData.checkCreditCardID(1111222233334444l, 1)).thenReturn(true);
        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);
        String actualPathToJSP = blockAccConfirm.execute(request);
        assertEquals(DEFAULT_OPERATION_CONFIRM_PATH, actualPathToJSP);
        verify(blockAccount).BlockConfirm(argument.capture());
    }

    @Test(expected = AuthorizationException.class)
    public void executeWithAuthorizationException() throws AuthorizationException {
        when(checkClientData.checkCreditCardID(1111222233334444l, 1)).thenReturn(false);
        blockAccConfirm.execute(request);
    }
}