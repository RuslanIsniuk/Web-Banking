package ua.rd.webbanking.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import ua.rd.webbanking.controller.command.impl.*;
import ua.rd.webbanking.controller.exceptions.AuthorizationException;
import ua.rd.webbanking.entities.CreditCard;

import javax.servlet.http.HttpServletRequest;

public class Dispatcher {
    private static final Logger logger = Logger.getLogger(Dispatcher.class);

    @Autowired
    private CheckLoginInput checkLoginInput;
    @Autowired
    private BlockAccConfirm blockAccConfirm;
    @Autowired
    private OpenSimplePage openSimplePage;
    @Autowired
    private TransferToAnotherCardConfirm transferToAnotherCardConfirm;
    @Autowired
    private CreateNewClientAcc createNewClientAcc;
    @Autowired
    private UnblockAccConfirm unblockAccConfirm;
    @Autowired
    private DeleteAccConfirm deleteAccConfirm;
    @Autowired
    private ClientsPaymentConfirm clientsPaymentConfirm;

    public String logicIdentificator(HttpServletRequest request) throws AuthorizationException, NumberFormatException {
        String pathToJSP = "";
        String attributeStr = elementaryIdentification(request);

        if (attributeStr == null) {
            long cardID = Long.parseLong(request.getParameter("cardID"));
            String cardIDStr = CreditCard.cardNumberToString(cardID);
            request.setAttribute("cardID", cardID);
            request.setAttribute("cardIDStr", cardIDStr);
            request.setAttribute("errorMessage", "Error! Nothing chosen!");
            pathToJSP = request.getParameter("pageLocation");
        } else {
//            try{
            switch (attributeStr) {
                case "CheckLoginData":
                    pathToJSP = checkLoginInput.execute(request);
                    break;

                case "returnToPerArea":
                case "returnToPerAdminAreaPage":
                case "openCardOperationMenu":
                case "openBlockAccPage":
                case "openCardTransactionPage":
                case "openCommPaymentPage":
                case "openMobilePaymentPage":
                case "openInternetPaymentPage":
                case "openTVPaymentPage":
                case "openTranToAnoCardPage":
                case "openAllClientsAccPage":
                case "openBlockedAccountsPage":
                case "openFormForCreatingNewAccPage":
                case "openUnblockAccPage":
                case "openDeleteAccPage":
                case "openClientDetailsPage":
                case "logOut":
                    pathToJSP = openSimplePage.execute(request);
                    break;

                case "confirmBlockAccount":
                    pathToJSP = blockAccConfirm.execute(request);
                    break;

                case "communalPaymentConfirm":
                case "mobilePaymentConfirm":
                case "internetPaymentConfirm":
                case "tvPaymentConfirm":
                    pathToJSP = clientsPaymentConfirm.execute(request);
                    break;

                case "transfToAnotherCardConfirm":
                    pathToJSP = transferToAnotherCardConfirm.execute(request);
                    break;

                case "createNewClientConfirm":
                    pathToJSP = createNewClientAcc.execute(request);
                    break;

                case "unblockClientAccount":
                    pathToJSP = unblockAccConfirm.execute(request);
                    break;

                case "deleteClientAccount":
                    pathToJSP = deleteAccConfirm.execute(request);
                    break;

                default:
                    logger.error("Error in dispatcher class!");
                    break;
            }
//            }catch (AuthorizationException se){
//                logger.error(se);
//                request.setAttribute("errorMessage", se.getMessage());
//                pathToJSP = "/index.jsp";
//            }catch (NumberFormatException ne){
//                logger.error(ne);
//                request.setAttribute("errorMessage", "403 Forbidden access!");
//                pathToJSP = "/403_error.jsp";
//            }
        }
        return pathToJSP;
    }

    private String elementaryIdentification(HttpServletRequest request) {
        return request.getParameter("actionType");
    }

}
