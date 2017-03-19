package ua.rd.webbanking.controller.command.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.rd.webbanking.controller.exceptions.AuthorizationException;
import ua.rd.webbanking.entities.Client;
import ua.rd.webbanking.model.exceptions.ServiceException;
import ua.rd.webbanking.model.services.CheckLoginAndPass;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CheckLoginInputTest {
    private static final String DEFAULT_OPERATION_FAIL_PATH = "/index.jsp";
    private static final String DEFAULT_OPERATION_CONFIRM_PATH = "pageOpened";
    private CheckLoginInput checkLoginInput;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    HttpServletRequest request;
    @Mock
    CheckLoginAndPass checkLoginAndPass;
    @Mock
    Client client;
    @Mock
    OpenSimplePage openSimplePage;

    @Before
    public void setUp() throws AuthorizationException {
        checkLoginInput = new CheckLoginInput(checkLoginAndPass, openSimplePage);
        when(client.getClientID()).thenReturn(1);
    }

    @Test
    public void defaultExecute() throws AuthorizationException, ServiceException {
        when(request.getParameter("username")).thenReturn("testLogin");
        when(request.getParameter("password")).thenReturn("testPass");
        when(checkLoginAndPass.validateLoginData("testLogin", "testPass")).thenReturn(client);
        when(openSimplePage.execute(request)).thenReturn("pageOpened");

        String actualPathToJSP = checkLoginInput.execute(request);
        assertEquals(DEFAULT_OPERATION_CONFIRM_PATH, actualPathToJSP);
    }

    @Test
    public void executeWithEmptyLogin() throws AuthorizationException {
        when(request.getParameter("username")).thenReturn("");
        when(request.getParameter("password")).thenReturn("testPass");

        String actualPathToJSP = checkLoginInput.execute(request);
        assertEquals(DEFAULT_OPERATION_FAIL_PATH, actualPathToJSP);
    }

    @Test
    public void executeWithEmptyPass() throws AuthorizationException {
        when(request.getParameter("username")).thenReturn("testLogin");
        when(request.getParameter("password")).thenReturn("");

        String actualPathToJSP = checkLoginInput.execute(request);
        assertEquals(DEFAULT_OPERATION_FAIL_PATH, actualPathToJSP);
    }

    @Test
    public void executeWithWrongData() throws AuthorizationException, ServiceException {
        when(request.getParameter("username")).thenReturn("testLogin");
        when(request.getParameter("password")).thenReturn("testPass");
        doThrow(new ServiceException()).when(checkLoginAndPass).validateLoginData("testLogin", "testPass");

        String actualPathToJSP = checkLoginInput.execute(request);
        assertEquals(DEFAULT_OPERATION_FAIL_PATH, actualPathToJSP);
    }
}