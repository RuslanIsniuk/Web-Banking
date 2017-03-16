package ua.rd.webbanking.controller.command;

import ua.rd.webbanking.controller.exceptions.AuthorizationException;
import ua.rd.webbanking.entities.Client;
import ua.rd.webbanking.model.services.CheckClientData;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public abstract class Command {
    private static final Logger logger = Logger.getLogger(Command.class);
    private CheckClientData checkClientData = new CheckClientData();

    public abstract String execute(HttpServletRequest request) throws AuthorizationException;

    public void checkCreditCardOwner(HttpServletRequest request) throws AuthorizationException {
        if (!request.isRequestedSessionIdValid()) {
            throw new AuthorizationException();
        }

        Client client = getClientFromSession(request);
        long cardID = getCreditCardIDFromRequest(request);

        if (!checkClientData.checkCreditCardID(cardID, client.getClientID())) {
            throw new AuthorizationException();
        }
    }

    public Client getClientFromSession(HttpServletRequest request) throws AuthorizationException {
        try {
            return (Client) request.getSession().getAttribute("clientData");
        } catch (NullPointerException npe) {
            logger.error(npe);
            throw new AuthorizationException("ERROR! Session not valid!");
        }
    }

    public long getCreditCardIDFromRequest(HttpServletRequest request) {
        return Long.parseLong(request.getParameter("cardID"));
    }

}
