package electronicPaymentSystem.controller.command.impl;

import electronicPaymentSystem.controller.command.Command;
import electronicPaymentSystem.controller.exceptions.AuthorizationException;
import electronicPaymentSystem.entities.CreditCard;
import electronicPaymentSystem.entities.Payment;
import electronicPaymentSystem.model.exceptions.ServiceException;
import electronicPaymentSystem.model.services.PaymentConfirm;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Руслан on 22.02.2017.
 */
public class TransferToAnotherCardConfirm extends Command {
    private static final Logger logger = Logger.getLogger(TransferToAnotherCardConfirm.class);

    private PaymentConfirm paymentConfirm = new PaymentConfirm();
    private static final String NUM_FIELD = "([1234]?\\d{1,4}(\\u002E\\d{1,2})?)||([5][0]{4}(\\u002E[0]{1,2})?)";
    private static final String CARD_FIELD = "\\d{16}";

    @Override
    public String execute(HttpServletRequest request) throws AuthorizationException {
        String pathToJSP;

        checkCreditCardOwner(request);
        long cardID = getCreditCardIDFromRequest(request);
        String cardIDStr = CreditCard.cardNumberToString(cardID);

        String cardIDToPart1 = request.getParameter("cardIDForTransferPart1");
        String cardIDToPart2 = request.getParameter("cardIDForTransferPart2");
        String cardIDToPart3 = request.getParameter("cardIDForTransferPart3");
        String cardIDToPart4 = request.getParameter("cardIDForTransferPart4");
        String cardIDToStr = cardIDToPart1 + cardIDToPart2 + cardIDToPart3 + cardIDToPart4;
        String amountStr = request.getParameter("amount");

        if ((elementaryCheck(cardIDToStr, CARD_FIELD)) && (elementaryCheck(amountStr, NUM_FIELD))) {
            try {
                BigDecimal amount = new BigDecimal(amountStr);
                long cardIDTo = Long.parseLong(cardIDToStr);
                cardIDToStr = cardIDToPart1 + " " + cardIDToPart2 + " " + cardIDToPart3 + " " + cardIDToPart4;
                Payment payment = paymentConfirm.makePayment(cardID, cardIDTo, amount);
                request.setAttribute("cardID", cardID);
                request.setAttribute("cardIDStr", cardIDStr);
                request.setAttribute("cardIDToStr", cardIDToStr);
                request.setAttribute("infoMessage", payment.getPaymentDestination());
                request.setAttribute("statusMessage", "Operation successfully completed! PaymentConfirm № " + payment.getPaymentID() + ".");
                pathToJSP = "/cardOperationImpl/PaymentConfirm.jsp";
            } catch (ServiceException se) {
                logger.info(se);
                request.setAttribute("errorMessage", se.getMessage());
                request.setAttribute("cardID", cardID);
                pathToJSP = "/cardOperationImpl/PaymentError.jsp";
            }
        } else {
            request.setAttribute("cardID", cardID);
            request.setAttribute("errorMessage", "Error! Incorrect data input!");
            pathToJSP = "/cardOperationImpl/PaymentError.jsp";
        }
        return pathToJSP;
    }


    private boolean elementaryCheck(String checkedObject, String compileStr) {
        Pattern pattern = Pattern.compile(compileStr);
        Matcher matcher = pattern.matcher(checkedObject);
        return matcher.matches();
    }
}
