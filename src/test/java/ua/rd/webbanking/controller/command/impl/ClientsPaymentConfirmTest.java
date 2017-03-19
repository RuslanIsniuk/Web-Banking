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
import ua.rd.webbanking.entities.PaymentType;
import ua.rd.webbanking.model.exceptions.ServiceException;
import ua.rd.webbanking.model.services.CheckClientData;
import ua.rd.webbanking.model.services.PaymentConfirm;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClientsPaymentConfirmTest {
    private static final String DEFAULT_OPERATION_FAIL_PATH = "/cardOperationImpl/PaymentError.jsp";
    private static final String DEFAULT_OPERATION_CONFIRM_PATH = "/cardOperationImpl/PaymentConfirm.jsp";
    private ClientsPaymentConfirm clientsPaymentConfirm;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    HttpServletRequest request;
    @Mock
    Client client;
    @Mock
    CheckClientData checkClientData;
    @Mock
    Payment payment;
    @Mock
    PaymentConfirm paymentConfirm;

    @Before
    public void setUp() {
        clientsPaymentConfirm = new ClientsPaymentConfirm(checkClientData, paymentConfirm);
        when(request.isRequestedSessionIdValid()).thenReturn(true);
        when(client.getClientID()).thenReturn(1);
        when(request.getSession().getAttribute("clientData")).thenReturn(client);
        when(request.getParameter("cardID")).thenReturn("1111222233334444");
        when(request.getParameter("amount")).thenReturn("100");

    }

    @Test
    public void executeWhenCommunalPaymentConfirm() throws AuthorizationException, ServiceException {
        when(request.getParameter("actionType")).thenReturn("communalPaymentConfirm");
        when(checkClientData.checkCreditCardID(1111222233334444l, 1)).thenReturn(true);
        when(request.getParameter("communalIDPart1")).thenReturn("1234");
        when(request.getParameter("communalIDPart2")).thenReturn("1234");
        when(paymentConfirm.makePayment(eq(1111222233334444l), any(), any(), eq(PaymentType.COMMUNAL_PAYMENT))).thenReturn(payment);

        String actualPathToJSP = clientsPaymentConfirm.execute(request);
        assertEquals(DEFAULT_OPERATION_CONFIRM_PATH, actualPathToJSP);
    }

    @Test
    public void executeWhenMobilePaymentConfirm() throws AuthorizationException, ServiceException {
        when(request.getParameter("actionType")).thenReturn("mobilePaymentConfirm");
        when(checkClientData.checkCreditCardID(1111222233334444l, 1)).thenReturn(true);
        when(request.getParameter("phoneNumber")).thenReturn("123456789");
        when(paymentConfirm.makePayment(eq(1111222233334444l), any(), any(), eq(PaymentType.MOBILE_PAYMENT))).thenReturn(payment);

        String actualPathToJSP = clientsPaymentConfirm.execute(request);
        assertEquals(DEFAULT_OPERATION_CONFIRM_PATH, actualPathToJSP);
    }

    @Test
    public void executeWhenInternetPaymentConfirm() throws AuthorizationException, ServiceException {
        when(request.getParameter("actionType")).thenReturn("internetPaymentConfirm");
        when(checkClientData.checkCreditCardID(1111222233334444l, 1)).thenReturn(true);
        when(request.getParameter("internetIDPart1")).thenReturn("12345");
        when(request.getParameter("internetIDPart2")).thenReturn("12345");
        when(paymentConfirm.makePayment(eq(1111222233334444l), any(), any(), eq(PaymentType.INTERNET_PAYMENT))).thenReturn(payment);

        String actualPathToJSP = clientsPaymentConfirm.execute(request);
        assertEquals(DEFAULT_OPERATION_CONFIRM_PATH, actualPathToJSP);
    }

    @Test
    public void executeWhenTVPaymentConfirm() throws AuthorizationException, ServiceException {
        when(request.getParameter("actionType")).thenReturn("tvPaymentConfirm");
        when(checkClientData.checkCreditCardID(1111222233334444l, 1)).thenReturn(true);
        when(request.getParameter("tvIDPart1")).thenReturn("12345");
        when(request.getParameter("tvIDPart2")).thenReturn("12345");
        when(paymentConfirm.makePayment(eq(1111222233334444l), any(), any(), eq(PaymentType.TV_PAYMENT))).thenReturn(payment);

        String actualPathToJSP = clientsPaymentConfirm.execute(request);
        assertEquals(DEFAULT_OPERATION_CONFIRM_PATH, actualPathToJSP);
    }

    @Test
    public void executeWithWrongData() throws AuthorizationException, ServiceException {
        when(request.getParameter("actionType")).thenReturn("tvPaymentConfirm");
        when(checkClientData.checkCreditCardID(1111222233334444l, 1)).thenReturn(true);
        when(request.getParameter("tvIDPart1")).thenReturn("12345");
        when(request.getParameter("tvIDPart2")).thenReturn("12345");
        doThrow(new ServiceException()).when(paymentConfirm).makePayment(eq(1111222233334444l), any(), any(), eq(PaymentType.TV_PAYMENT));

        String actualPathToJSP = clientsPaymentConfirm.execute(request);
        assertEquals(DEFAULT_OPERATION_FAIL_PATH, actualPathToJSP);
    }

    @Test
    public void executeWithIncorrectData() throws AuthorizationException, ServiceException {
        String pathToJSPExpected = "/cardOperationImpl/PaymentError.jsp";
        when(request.getParameter("actionType")).thenReturn("tvPaymentConfirm");
        when(checkClientData.checkCreditCardID(1111222233334444l, 1)).thenReturn(true);
        when(request.getParameter("tvIDPart1")).thenReturn("efgfdg");
        when(request.getParameter("tvIDPart2")).thenReturn("123ds45");

        String actualPathToJSP = clientsPaymentConfirm.execute(request);
        assertEquals(pathToJSPExpected, actualPathToJSP);
    }
}