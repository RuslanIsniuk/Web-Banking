package ua.rd.webbanking.controller;

import ua.rd.webbanking.controller.command.impl.*;
import ua.rd.webbanking.controller.exceptions.AuthorizationException;

import javax.servlet.http.HttpServletRequest;

public class Dispatcher {
    private static final Dispatcher Instance = new Dispatcher();

    private CheckLoginInput checkLoginInput = new CheckLoginInput();
    private BlockAccConfirm blockAccConfirm = new BlockAccConfirm();
    private OpenSimplePage openSimplePage = new OpenSimplePage();
    private TransferToAnotherCardConfirm transferToAnotherCardConfirm = new TransferToAnotherCardConfirm();
    private CreateNewClientAcc createNewClientAcc = new CreateNewClientAcc();
    private UnblockAccConfirm unblockAccConfirm = new UnblockAccConfirm();
    private DeleteAccConfirm deleteAccConfirm = new DeleteAccConfirm();
    private ClientsPaymentConfirm clientsPaymentConfirm = new ClientsPaymentConfirm();
    private Dispatcher(){}

    public String logicIdentificator(HttpServletRequest request) {
        String pathToJSP = new String();
        String attributeStr = elementaryIdentification(request);

        try{
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
                    //log here
                    break;
            }
        }catch (AuthorizationException se){
            request.setAttribute("errorMessage", se.getMessage());
            pathToJSP = "/index.jsp";
        }

        return pathToJSP;
    }

    private String elementaryIdentification(HttpServletRequest request) {
        return request.getParameter("actionType");
    }

    public static Dispatcher getInstance(){
        return Instance;
    }
}
