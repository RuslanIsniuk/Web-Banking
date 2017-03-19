package ua.rd.webbanking.controller.command.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.rd.webbanking.controller.exceptions.AuthorizationException;
import ua.rd.webbanking.entities.Account;
import ua.rd.webbanking.entities.Client;
import ua.rd.webbanking.entities.CreditCard;
import ua.rd.webbanking.model.exceptions.ServiceException;
import ua.rd.webbanking.model.services.CheckClientData;
import ua.rd.webbanking.model.services.CreateNewClientPerArea;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateNewClientAccTest {
    private static final String DEFAULT_OPERATION_FAIL_PATH = "/adminOperationImpl/CreateNewClientPage.jsp";
    private static final String DEFAULT_OPERATION_CONFIRM_PATH = "/adminOperationImpl/OperationConfirm.jsp";
    private CreateNewClientAcc createNewClientAcc;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    HttpServletRequest request;
    @Mock
    CheckClientData checkClientData;
    @Mock
    Client client;
    @Mock
    CreateNewClientPerArea createNewClientPerArea;

    @Before
    public void setUp() throws ServiceException {
        createNewClientAcc = new CreateNewClientAcc(checkClientData, createNewClientPerArea);
        when(client.getClientID()).thenReturn(1);
        when(request.getSession().getAttribute("clientData")).thenReturn(client);

        when(request.getParameter("clientLogin")).thenReturn("testLogin");
        when(request.getParameter("clientPass")).thenReturn("testPass");
        when(request.getParameter("clientFullName")).thenReturn("test FullName");
        when(request.getParameter("adminFlag")).thenReturn("no");

        when(request.getParameter("accountID")).thenReturn("123456");
        when(request.getParameter("accountBalance")).thenReturn("100");
        when(request.getParameter("accStatusFlag")).thenReturn("active");

        when(request.getParameter("cardIDPart1")).thenReturn("1111");
        when(request.getParameter("cardIDPart2")).thenReturn("2222");
        when(request.getParameter("cardIDPart3")).thenReturn("3333");
        when(request.getParameter("cardIDPart4")).thenReturn("4444");
        when(request.getParameter("cardPIN")).thenReturn("1234");
        when(request.getParameter("cardStatusFlag")).thenReturn("active");
        when(request.getParameter("dayDate")).thenReturn("20");
        when(request.getParameter("monthDate")).thenReturn("10");
        when(request.getParameter("yearDate")).thenReturn("2015");

        when(createNewClientPerArea.createNewClient(any())).thenReturn(1);

    }

    @Test
    public void defaultExecute() throws AuthorizationException, ServiceException {
        when(checkClientData.checkAdminFlag(1)).thenReturn(true);
        ArgumentCaptor<Account> argumentAcc = ArgumentCaptor.forClass(Account.class);
        ArgumentCaptor<CreditCard> argumentCard = ArgumentCaptor.forClass(CreditCard.class);

        String actualPathToJSP = createNewClientAcc.execute(request);
        assertEquals(DEFAULT_OPERATION_CONFIRM_PATH, actualPathToJSP);
        verify(createNewClientPerArea).createNewAccount(argumentAcc.capture());
        verify(createNewClientPerArea).createNewCreditCard(argumentCard.capture());

    }

    @Test
    public void executeWithDefaultWrongData() throws ServiceException, AuthorizationException {
        when(checkClientData.checkAdminFlag(1)).thenReturn(true);
        doThrow(new ServiceException()).when(createNewClientPerArea).createNewClient(any());
        String actualPathToJSP = createNewClientAcc.execute(request);
        assertEquals(DEFAULT_OPERATION_FAIL_PATH, actualPathToJSP);

    }

    @Test(expected = AuthorizationException.class)
    public void executeWithAuthorizationException() throws AuthorizationException {
        when(checkClientData.checkAdminFlag(1)).thenReturn(false);
        createNewClientAcc.execute(request);
    }

    @Test
    public void executeWithWrongLogin() throws AuthorizationException {
        when(checkClientData.checkAdminFlag(1)).thenReturn(true);
        when(request.getParameter("clientLogin")).thenReturn("1");
        String actualPathToJSP = createNewClientAcc.execute(request);
        assertEquals(DEFAULT_OPERATION_FAIL_PATH, actualPathToJSP);
    }

    @Test
    public void executeWithWrongPass() throws AuthorizationException {
        when(checkClientData.checkAdminFlag(1)).thenReturn(true);
        when(request.getParameter("clientPass")).thenReturn("1");
        String actualPathToJSP = createNewClientAcc.execute(request);
        assertEquals(DEFAULT_OPERATION_FAIL_PATH, actualPathToJSP);
    }

    @Test
    public void executeWithWrongFullName() throws AuthorizationException {
        when(checkClientData.checkAdminFlag(1)).thenReturn(true);
        when(request.getParameter("clientFullName")).thenReturn("testFullName");
        String actualPathToJSP = createNewClientAcc.execute(request);
        assertEquals(DEFAULT_OPERATION_FAIL_PATH, actualPathToJSP);
    }

    @Test
    public void executeWithWrongAccountID() throws AuthorizationException {
        when(checkClientData.checkAdminFlag(1)).thenReturn(true);
        when(request.getParameter("accountID")).thenReturn("0");
        String actualPathToJSP = createNewClientAcc.execute(request);
        assertEquals(DEFAULT_OPERATION_FAIL_PATH, actualPathToJSP);
    }

    @Test
    public void executeWithWrongAccountBalance() throws AuthorizationException {
        when(checkClientData.checkAdminFlag(1)).thenReturn(true);
        when(request.getParameter("accountBalance")).thenReturn("test");
        String actualPathToJSP = createNewClientAcc.execute(request);
        assertEquals(DEFAULT_OPERATION_FAIL_PATH, actualPathToJSP);
    }

    @Test
    public void executeWithWrongCardID() throws AuthorizationException {
        when(checkClientData.checkAdminFlag(1)).thenReturn(true);
        when(request.getParameter("cardIDPart4")).thenReturn("test");
        String actualPathToJSP = createNewClientAcc.execute(request);
        assertEquals(DEFAULT_OPERATION_FAIL_PATH, actualPathToJSP);
    }

    @Test
    public void executeWithWrongCardPIN() throws AuthorizationException {
        when(checkClientData.checkAdminFlag(1)).thenReturn(true);
        when(request.getParameter("cardPIN")).thenReturn("test");
        String actualPathToJSP = createNewClientAcc.execute(request);
        assertEquals(DEFAULT_OPERATION_FAIL_PATH, actualPathToJSP);
    }

    @Test
    public void executeWithWrongCardDate() throws AuthorizationException {
        when(checkClientData.checkAdminFlag(1)).thenReturn(true);
        when(request.getParameter("monthDate")).thenReturn("test");
        String actualPathToJSP = createNewClientAcc.execute(request);
        assertEquals(DEFAULT_OPERATION_FAIL_PATH, actualPathToJSP);
    }
}