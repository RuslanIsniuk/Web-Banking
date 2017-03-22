package ua.rd.webbanking.model.services;

import ua.rd.webbanking.dao.ClientDAO;
import ua.rd.webbanking.dao.impl.JDBCClientDAO;
import ua.rd.webbanking.entities.Client;
import ua.rd.webbanking.model.exceptions.ServiceException;

public class CheckLoginAndPass {

    public Client validateLoginData(String userLogin, String userPass) throws ServiceException {
        ClientDAO clientDAO = new JDBCClientDAO();
        Client client = clientDAO.read(userLogin, userPass);

        if (!(client instanceof Client)) {
            throw new ServiceException("Error! Check login or password!");
        }

        return client;
    }
}
