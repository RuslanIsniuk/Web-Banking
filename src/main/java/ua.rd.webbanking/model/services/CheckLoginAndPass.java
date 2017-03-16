package electronicPaymentSystem.model.services;

import electronicPaymentSystem.dao.ClientDAO;
import electronicPaymentSystem.dao.impl.JDBCClientDAO;
import electronicPaymentSystem.entities.Client;
import electronicPaymentSystem.model.exceptions.ServiceException;

/**
 * Created by Руслан on 12.02.2017.
 */
public class CheckLoginAndPass {

    public Client validateLoginData(String userLogin, String userPass) throws ServiceException {
        ClientDAO clientDAO = new JDBCClientDAO();
        Client client = clientDAO.read(userLogin, userPass);

        if (client.getClientID() == 0) {
            throw new ServiceException("Error! Check login or password!");
        }

        return client;
    }
}
