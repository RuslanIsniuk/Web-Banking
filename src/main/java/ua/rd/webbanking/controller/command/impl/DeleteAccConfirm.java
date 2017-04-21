package ua.rd.webbanking.controller.command.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ua.rd.webbanking.controller.command.Command;
import ua.rd.webbanking.controller.exceptions.AuthorizationException;
import ua.rd.webbanking.entities.Client;
import ua.rd.webbanking.model.services.CheckClientData;
import ua.rd.webbanking.model.services.DeleteAccount;

import javax.servlet.http.HttpServletRequest;

public class DeleteAccConfirm extends Command {
    @Autowired
    private DeleteAccount deleteAccount;

    public DeleteAccConfirm() {
//        default constructor
    }

    public DeleteAccConfirm(CheckClientData checkClientData, DeleteAccount deleteAccount) {
        this.checkClientData = checkClientData;
        this.deleteAccount = deleteAccount;
    }

    @Override
    public String execute(HttpServletRequest request) throws AuthorizationException {
        String pathToJSP = "/adminPages/OperationConfirm.jsp";
        Client clientAdmin = getClientFromSession(request);

        if (!checkClientData.checkAdminFlag(clientAdmin.getClientID())) {
            throw new AuthorizationException();
        }

        int clientID = Integer.parseInt(request.getParameter("clientID"));
        deleteAccount.deleteConfirm(clientID);

        request.setAttribute("statusMessage", "Client's data removed successfully!");
        return pathToJSP;
    }
}
