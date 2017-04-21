package ua.rd.webbanking.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.rd.webbanking.controller.exceptions.AuthorizationException;
import ua.rd.webbanking.dao.ClientDAO;
import ua.rd.webbanking.dao.CreditCardDAO;
import ua.rd.webbanking.dao.impl.JDBCClientDAO;
import ua.rd.webbanking.dao.impl.JDBCCreditCardDAO;
import ua.rd.webbanking.entities.Account;
import ua.rd.webbanking.entities.Client;

public class CheckClientData {
    @Autowired
    @Qualifier("HibernateCreditCardDAO")
    private CreditCardDAO creditCardDAO;
    @Autowired
    @Qualifier("HibernateClientDAO")
    private ClientDAO clientDAO;

    public CheckClientData() {
//        default constructor
    }

    public CheckClientData(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    public boolean checkCreditCardID(long cardID, int clientID) {
        Account accountAuthentic = creditCardDAO.read(cardID).getCardAccount();
        return accountAuthentic.getAccountClient().getClientID() == clientID;
    }

    public boolean checkAdminFlag(int clientID) throws AuthorizationException {
        Client client = clientDAO.read(clientID);

        if (!(client instanceof Client)) {
            throw new AuthorizationException("Error! Check login or password!");
        }

        if (client.isAdminFlag()) {
            return true;
        } else {
            return false;
        }
    }
}
