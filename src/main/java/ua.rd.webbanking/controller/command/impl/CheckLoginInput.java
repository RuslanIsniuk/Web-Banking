package electronicPaymentSystem.controller.command.impl;

import electronicPaymentSystem.controller.command.Command;
import electronicPaymentSystem.controller.exceptions.AuthorizationException;
import electronicPaymentSystem.entities.Client;
import electronicPaymentSystem.model.exceptions.ServiceException;
import electronicPaymentSystem.model.services.CheckLoginAndPass;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Руслан on 16.02.2017.
 */
public class CheckLoginInput extends Command {
    private OpenSimplePage openSimplePage = new OpenSimplePage();
    private CheckLoginAndPass checkLoginAndPass = new CheckLoginAndPass();
    private String username;
    private String password;
    private String pathToJSP;

    private static final Logger logger = Logger.getLogger(CheckLoginInput.class);
    private static final String defaultPassStr = "/index.jsp";
    private static final String errorAttributesStr = "errorMessage";

    @Override
    public String execute(HttpServletRequest request) throws AuthorizationException {
        if (validationOnCorrectInput(request)) {
            try {
                Client client = checkLoginAndPass.validateLoginData(username, password);
                request.setAttribute("clientID", client.getClientID());

                HttpSession session = request.getSession(true);
                session.setMaxInactiveInterval(600);
                session.setAttribute("clientData", client);

                pathToJSP = openSimplePage.execute(request);
            } catch (ServiceException se) {
                logger.info(se);
                request.setAttribute(errorAttributesStr, se.getMessage());
                pathToJSP = defaultPassStr;
            }
        }
        return pathToJSP;
    }

    private boolean validationOnCorrectInput(HttpServletRequest request) {
        boolean dataCorrect = true;
        username = request.getParameter("username");
        password = request.getParameter("password");

        if (("".equals(username)) || ("".equals(password))) {
            dataCorrect = false;
            request.setAttribute(errorAttributesStr, "Error! Wrong login or password!");
            pathToJSP = defaultPassStr;
        }

        if ((username.length() > 25) || password.length() > 25) {
            dataCorrect = false;
            request.setAttribute(errorAttributesStr, "Error! Wrong login or password!");
            pathToJSP = defaultPassStr;
        }
        return dataCorrect;
    }
}
