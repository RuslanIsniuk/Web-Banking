package ua.rd.webbanking.controllerSpring.command.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import ua.rd.webbanking.controllerSpring.command.Command;
import ua.rd.webbanking.controllerSpring.model.LoginInputForm;
import ua.rd.webbanking.entities.Client;
import ua.rd.webbanking.model.exceptions.ServiceException;
import ua.rd.webbanking.model.services.CheckLoginAndPass;

public class CheckLoginInput extends Command{
    private static final Logger logger = Logger.getLogger(CheckLoginInput.class);

    @Autowired
    private CheckLoginAndPass checkLoginAndPass;
    private Client client;

    public String execute(LoginInputForm target){
        try{
            client = checkLoginAndPass.validateLoginData(target.getUserName(),target.getUserPassword());
        }catch (ServiceException se) {
           logger.error(se);
           target.setErrorMessage(se.getMessage());
           return "index";
        }

        if(client.isAdminFlag()){
            return "PersonalAdminArea";
        }else {
            return "PersonalClientArea";
        }
    }
}
