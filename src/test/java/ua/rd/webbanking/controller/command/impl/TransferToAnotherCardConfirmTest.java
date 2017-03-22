package ua.rd.webbanking.controller.command.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.rd.webbanking.controller.exceptions.AuthorizationException;
import ua.rd.webbanking.entities.Client;
import ua.rd.webbanking.entities.Payment;
import ua.rd.webbanking.model.exceptions.ServiceException;
import ua.rd.webbanking.model.services.CheckClientData;
import ua.rd.webbanking.model.services.PaymentConfirm;

import javax.servlet.http.HttpServletRequest;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransferToAnotherCardConfirmTest {
    private static final String DEFAULT_OPERATION_FAIL_PATH = "/cardOperationImpl/PaymentError.jsp";
    private static final String DEFAULT_OPERATION_CONFIRM_PATH = "/cardOperationImpl/PaymentConfirm.jsp";
    private TransferToAnotherCardConfirm transferToAnotherCardConfirm;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    HttpServletRequest request;
    @Mock
    Client client;
    @Mock
    CheckClientData checkClientData;
    @Mock
    PaymentConfirm paymentConfirm;
    @Mock
    Payment payment;

    @Before
    public void setUp() throws AuthorizationException {
        transferToAnotherCardConfirm = new TransferToAnotherCardConfirm(checkClientData, paymentConfirm);
        when(client.getClientID()).thenReturn(1);
        when(request.isRequestedSessionIdValid()).thenReturn(true);
        when(request.getSession().getAttribute("clientData")).thenReturn(client);
        when(request.getParameter("cardID")).thenReturn("1111333355556666");
        when(checkClientData.checkAdminFlag(1)).thenReturn(true);
        when(checkClientData.checkCreditCardID(1111333355556666l, 1)).thenReturn(true);

        when(request.getParameter("cardIDForTransferPart1")).thenReturn("1111");
        when(request.getParameter("cardIDForTransferPart2")).thenReturn("2222");
        when(request.getParameter("cardIDForTransferPart3")).thenReturn("3333");
        when(request.getParameter("cardIDForTransferPart4")).thenReturn("4444");

        when(request.getParameter("amount")).thenReturn("100");
        when(payment.getPaymentDestination()).thenReturn("testDestination");
    }

    @Test
    public void defaultExecute() throws AuthorizationException, ServiceException {
        when(paymentConfirm.makePayment(eq(1111333355556666l), eq(1111222233334444l), any())).thenReturn(payment);
        String actualPathToJSP = transferToAnotherCardConfirm.execute(request);
        assertEquals(DEFAULT_OPERATION_CONFIRM_PATH, actualPathToJSP);
    }

    @Test
    public void executeWithWrongData() throws ServiceException, AuthorizationException {
        doThrow(new ServiceException()).when(paymentConfirm).makePayment(eq(1111333355556666l), eq(1111222233334444l), any());
        String actualPathToJSP = transferToAnotherCardConfirm.execute(request);
        assertEquals(DEFAULT_OPERATION_FAIL_PATH, actualPathToJSP);
    }

    @Test
    public void executeWithIncorrectData() throws ServiceException, AuthorizationException {
        when(request.getParameter("amount")).thenReturn("test");
        String actualPathToJSP = transferToAnotherCardConfirm.execute(request);
        assertEquals(DEFAULT_OPERATION_FAIL_PATH, actualPathToJSP);
    }

    @Test(expected = AuthorizationException.class)
    public void executeThrowAuthorizationException() throws AuthorizationException {
        when(checkClientData.checkCreditCardID(1111333355556666l, 1)).thenReturn(false);
        transferToAnotherCardConfirm.execute(request);
    }
}