package ua.rd.webbanking.controller.command.impl;

import ua.rd.webbanking.controller.command.Command;
import ua.rd.webbanking.controller.exceptions.AuthorizationException;
import ua.rd.webbanking.entities.CreditCard;
import ua.rd.webbanking.model.services.BlockAccount;

import javax.servlet.http.HttpServletRequest;

public class BlockAccConfirm extends Command {
    private BlockAccount blockAccount = new BlockAccount();

    @Override
    public String execute(HttpServletRequest request) throws AuthorizationException {
        String pathToJSP;

        checkCreditCardOwner(request);
        long cardID = getCreditCardIDFromRequest(request);

        blockAccount.BlockConfirm(cardID);
        String cardIDStr = CreditCard.cardNumberToString(cardID);

        request.setAttribute("infoMessage", "Card № " + cardIDStr + "blocked successfully!");
        request.setAttribute("cardID", cardID);
        pathToJSP = "/cardOperationImpl/BlockAccount.jsp";

        return pathToJSP;
    }
}
