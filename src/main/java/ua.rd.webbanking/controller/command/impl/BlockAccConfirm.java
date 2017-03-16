package electronicPaymentSystem.controller.command.impl;

import electronicPaymentSystem.controller.command.Command;
import electronicPaymentSystem.controller.exceptions.AuthorizationException;
import electronicPaymentSystem.entities.CreditCard;
import electronicPaymentSystem.model.services.BlockAccount;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Руслан on 21.02.2017.
 */
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
