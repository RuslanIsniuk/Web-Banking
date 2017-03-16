package electronicPaymentSystem.controller.command.impl;

import electronicPaymentSystem.controller.command.Command;
import electronicPaymentSystem.controller.exceptions.AuthorizationException;
import electronicPaymentSystem.entities.CreditCard;
import electronicPaymentSystem.entities.Payment;
import electronicPaymentSystem.entities.PaymentType;
import electronicPaymentSystem.model.exceptions.ServiceException;
import electronicPaymentSystem.model.services.PaymentConfirm;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Руслан on 05.03.2017.
 */
public class ClientsPaymentConfirm extends Command {
    private static final Logger logger = Logger.getLogger(ClientsPaymentConfirm.class);

    private static final String NUM_FIELD = "([1234]?\\d{1,4}(\\u002E\\d{1,2})?)||([5][0]{4}(\\u002E[0]{1,2})?)";
    private static final String COMMUNAL_NUM_FIELD = "\\d{8}";
    private static final String MOBILE_NUM_FIELD = "\\d{9}";
    private static final String INTERNET_NUM_FIELD = "\\d{10}";
    private static final String TV_NUM_FIELD = "\\d{10}";


    private PaymentConfirm paymentConfirm = new PaymentConfirm();
    private PaymentType paymentType;

    @Override
    public String execute(HttpServletRequest request) throws AuthorizationException {
        String pathToJSP;
        checkCreditCardOwner(request);
        long cardID = getCreditCardIDFromRequest(request);
        String cardIDStr = CreditCard.cardNumberToString(cardID);

        String amountStr = request.getParameter("amount");
        String destinationID = getDestinationIDFromTheRequest(request);

        if (validationOnCorrectInput(destinationID, amountStr, request)) {
            try {
                BigDecimal amount = new BigDecimal(request.getParameter("amount"));
                Payment payment = paymentConfirm.makePayment(cardID, amount, destinationID, paymentType);
                request.setAttribute("cardID", cardID);
                request.setAttribute("cardIDStr", cardIDStr);
                request.setAttribute("destinationID", destinationID);
                request.setAttribute("infoMessage", payment.getPaymentDestination());
                request.setAttribute("statusMessage", "Operation successfully completed! PaymentConfirm № " + payment.getPaymentID() + ".");
                pathToJSP = "/cardOperationImpl/PaymentConfirm.jsp";
            } catch (ServiceException se) {
                logger.info(se);
                request.setAttribute("cardID", cardID);
                request.setAttribute("errorMessage", se.getMessage());
                pathToJSP = "/cardOperationImpl/PaymentError.jsp";
            }
        } else {
            request.setAttribute("cardID", cardID);
            request.setAttribute("errorMessage", "Error! Incorrect data input!");
            pathToJSP = "/cardOperationImpl/PaymentError.jsp";
        }
        return pathToJSP;
    }

    private String getDestinationIDFromTheRequest(HttpServletRequest request) {
        String destinationID = "";

        switch (request.getParameter("actionType")) {
            case "communalPaymentConfirm":
                String communalIDP1 = request.getParameter("communalIDPart1");
                String communalIDP2 = request.getParameter("communalIDPart2");
                destinationID = communalIDP1 + communalIDP2;
                paymentType = PaymentType.COMMUNAL_PAYMENT;
                break;

            case "mobilePaymentConfirm":
                destinationID = request.getParameter("phoneNumber");
                paymentType = PaymentType.MOBILE_PAYMENT;
                break;

            case "internetPaymentConfirm":
                String internetIDP1 = request.getParameter("internetIDPart1");
                String internetIDP2 = request.getParameter("internetIDPart2");
                destinationID = internetIDP1 + internetIDP2;
                paymentType = PaymentType.INTERNET_PAYMENT;
                break;

            case "tvPaymentConfirm":
                String tvIDP1 = request.getParameter("tvIDPart1");
                String tvIDP2 = request.getParameter("tvIDPart2");
                destinationID = tvIDP1 + tvIDP2;
                paymentType = PaymentType.TV_PAYMENT;
                break;

            default:
                // log here
                break;
        }
        return destinationID;
    }

    private boolean validationOnCorrectInput(String checkedObject, String amountStr, HttpServletRequest request) {
        String compileStr;
        boolean amountCorrect;
        boolean paymentIDCorrect;

        switch (request.getParameter("actionType")) {
            case "communalPaymentConfirm":
                compileStr = COMMUNAL_NUM_FIELD;
                break;

            case "mobilePaymentConfirm":
                compileStr = MOBILE_NUM_FIELD;
                break;

            case "internetPaymentConfirm":
                compileStr = INTERNET_NUM_FIELD;
                break;

            case "tvPaymentConfirm":
                compileStr = TV_NUM_FIELD;
                break;

            default:
                compileStr = "";
                // log here
                break;
        }

        Pattern pattern = Pattern.compile(NUM_FIELD);
        Matcher matcher = pattern.matcher(amountStr);
        amountCorrect = matcher.matches();

        pattern = Pattern.compile(compileStr);
        matcher = pattern.matcher(checkedObject);
        paymentIDCorrect = matcher.matches();

        return amountCorrect && paymentIDCorrect;
    }
}
