package electronicPaymentSystem.controller.command.impl;

import electronicPaymentSystem.controller.command.Command;
import electronicPaymentSystem.controller.exceptions.AuthorizationException;
import electronicPaymentSystem.entities.Client;
import electronicPaymentSystem.model.services.CheckClientData;
import electronicPaymentSystem.model.services.DeleteAccount;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Руслан on 04.03.2017.
 */
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
