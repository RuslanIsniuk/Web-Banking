package ua.rd.webbanking.controller.command;

import ua.rd.webbanking.controller.exceptions.AuthorizationException;
import ua.rd.webbanking.entities.Client;
import ua.rd.webbanking.model.services.CheckClientData;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public abstract class Command {
    private static final Logger logger = Logger.getLogger(Command.class);
    protected CheckClientData checkClientData = new CheckClientData();

    public abstract String execute(HttpServletRequest request) throws AuthorizationException;

    protected void checkCreditCardOwner(HttpServletRequest request) throws AuthorizationException,NumberFormatException {
        if (!request.isRequestedSessionIdValid()) {
            throw new AuthorizationException();
        }

        Client client = getClientFromSession(request);
        long cardID = getCreditCardIDFromRequest(request);

        if (!checkClientData.checkCreditCardID(cardID, client.getClientID())) {
            throw new AuthorizationException();
        }
    }

    protected Client getClientFromSession(HttpServletRequest request) throws AuthorizationException {
        try {
            return (Client) request.getSession().getAttribute("clientData");
        } catch (NullPointerException npe) {
            logger.error(npe);
            throw new AuthorizationException("ERROR! Session not valid!");
        }
    }

    protected long getCreditCardIDFromRequest(HttpServletRequest request) throws NumberFormatException{
        return Long.parseLong(request.getParameter("cardID"));
    }

}
