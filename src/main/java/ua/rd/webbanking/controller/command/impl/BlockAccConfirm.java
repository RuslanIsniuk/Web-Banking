package ua.rd.webbanking.controller.command.impl;

import ua.rd.webbanking.controller.command.Command;
import ua.rd.webbanking.controller.exceptions.AuthorizationException;
import ua.rd.webbanking.entities.CreditCard;
import ua.rd.webbanking.model.services.BlockAccount;
import ua.rd.webbanking.model.services.CheckClientData;

import javax.servlet.http.HttpServletRequest;

public class BlockAccConfirm extends Command {
    private BlockAccount blockAccount = new BlockAccount();

    public BlockAccConfirm() {
//        default constructor
    }

    public BlockAccConfirm(CheckClientData checkClientData, BlockAccount blockAccount) {
        this.checkClientData = checkClientData;
        this.blockAccount = blockAccount;
    }

    @Override
    public String execute(HttpServletRequest request) throws AuthorizationException, NumberFormatException {
        String pathToJSP;

        checkCreditCardOwner(request);
        long cardID = getCreditCardIDFromRequest(request);

        blockAccount.BlockConfirm(cardID);
        String cardIDStr = CreditCard.cardNumberToString(cardID);

        request.setAttribute("infoMessage", "Card № " + cardIDStr + "blocked successfully!");
        request.setAttribute("cardID", cardID);
        pathToJSP = "/clientPages/BlockAccount.jsp";

        return pathToJSP;
    }
}
