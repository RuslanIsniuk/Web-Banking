package ua.rd.webbanking.controller.command.impl;

import ua.rd.webbanking.controller.command.Command;
import ua.rd.webbanking.controller.exceptions.AuthorizationException;
import ua.rd.webbanking.entities.Client;
import ua.rd.webbanking.model.services.CheckClientData;
import ua.rd.webbanking.model.services.DeleteAccount;

import javax.servlet.http.HttpServletRequest;

public class DeleteAccConfirm extends Command {
    private CheckClientData checkClientData = new CheckClientData();
    private DeleteAccount deleteAccount = new DeleteAccount();

    @Override
    public String execute(HttpServletRequest request) throws AuthorizationException{
        String pathToJSP = "/adminOperationImpl/OperationConfirm.jsp";
        Client clientAdmin = getClientFromSession(request);

        if (!checkClientData.checkAdminFlag(clientAdmin.getClientID())) {
            throw new AuthorizationException();
        }

        int clientID = Integer.parseInt(request.getParameter("clientID"));
        deleteAccount.deleteConfirm(clientID);

        request.setAttribute("statusMessage","Client's data removed successfully!");
        return pathToJSP;
    }
}
