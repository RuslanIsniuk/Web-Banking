package electronicPaymentSystem.controller.command.impl;

import electronicPaymentSystem.controller.command.Command;
import electronicPaymentSystem.controller.exceptions.AuthorizationException;
import electronicPaymentSystem.entities.Client;
import electronicPaymentSystem.entities.CreditCard;
import electronicPaymentSystem.model.services.CheckClientData;
import electronicPaymentSystem.model.services.UnblockAccount;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Руслан on 04.03.2017.
 */
public class UnblockAccConfirm extends Command {
    private CheckClientData checkClientData = new CheckClientData();
    private UnblockAccount unblockAccount = new UnblockAccount();

    @Override
    public String execute(HttpServletRequest request) throws AuthorizationException {
        String pathToJSP;
        Client clientAdmin = getClientFromSession(request);

        if (!checkClientData.checkAdminFlag(clientAdmin.getClientID())) {
            throw new AuthorizationException();
        }

        long cardID = getCreditCardIDFromRequest(request);

        unblockAccount.unblockConfirm(cardID);

        request.setAttribute("statusMessage", "Card № " + CreditCard.cardNumberToString(cardID) + " unblocked successfully!");
        pathToJSP = "/adminOperationImpl/OperationConfirm.jsp";

        return pathToJSP;
    }
}
