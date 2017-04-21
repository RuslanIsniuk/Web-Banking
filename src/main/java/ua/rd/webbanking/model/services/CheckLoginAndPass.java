package ua.rd.webbanking.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.rd.webbanking.dao.ClientDAO;
import ua.rd.webbanking.dao.impl.JDBCClientDAO;
import ua.rd.webbanking.entities.Client;
import ua.rd.webbanking.model.exceptions.ServiceException;

public class CheckLoginAndPass {
    @Autowired
    @Qualifier("JDBCClientDAO")
    private ClientDAO clientDAO;

    public CheckLoginAndPass() {
//        default constructor
    }

    public CheckLoginAndPass(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    public Client validateLoginData(String userLogin, String userPass) throws ServiceException {
        Client client = clientDAO.readByLogin(userLogin, userPass);

        if (!(client instanceof Client)) {
            throw new ServiceException("Error! Check login or password!");
        }

        return client;
    }
}
