package ua.rd.webbanking.controller.command.impl;

import ua.rd.webbanking.controller.command.Command;
import ua.rd.webbanking.controller.exceptions.AuthorizationException;
import ua.rd.webbanking.entities.Client;
import ua.rd.webbanking.entities.CreditCard;
import ua.rd.webbanking.model.services.CheckClientData;
import ua.rd.webbanking.model.services.UnblockAccount;

import javax.servlet.http.HttpServletRequest;

public class UnblockAccConfirm extends Command {
    private UnblockAccount unblockAccount = new UnblockAccount();

    public UnblockAccConfirm() {
//        default constructor
    }

    public UnblockAccConfirm(CheckClientData checkClientData, UnblockAccount unblockAccount) {
        this.checkClientData = checkClientData;
        this.unblockAccount = unblockAccount;
    }

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
