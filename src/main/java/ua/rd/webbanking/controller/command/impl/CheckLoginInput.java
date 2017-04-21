package ua.rd.webbanking.controller.command.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ua.rd.webbanking.controller.command.Command;
import ua.rd.webbanking.controller.exceptions.AuthorizationException;
import ua.rd.webbanking.entities.Client;
import ua.rd.webbanking.model.exceptions.ServiceException;
import ua.rd.webbanking.model.services.CheckLoginAndPass;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CheckLoginInput extends Command {
    @Autowired
    private OpenSimplePage openSimplePage;
    @Autowired
    private CheckLoginAndPass checkLoginAndPass;
    private String username;
    private String password;
    private String pathToJSP;

    private static final Logger logger = Logger.getLogger(CheckLoginInput.class);
    private static final String defaultPassStr = "/index.jsp";
    private static final String errorAttributesStr = "errorMessage";

    public CheckLoginInput() {
//        default constructor
    }

    public CheckLoginInput(CheckLoginAndPass checkLoginAndPass, OpenSimplePage openSimplePage) {
        this.checkLoginAndPass = checkLoginAndPass;
        this.openSimplePage = openSimplePage;
    }

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
