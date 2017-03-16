package electronicPaymentSystem.model.services;

import electronicPaymentSystem.dao.ClientDAO;
import electronicPaymentSystem.dao.CreditCardDAO;
import electronicPaymentSystem.dao.impl.JDBCClientDAO;
import electronicPaymentSystem.dao.impl.JDBCCreditCardDAO;
import electronicPaymentSystem.entities.Account;
import electronicPaymentSystem.entities.Client;

/**
 * Created by Руслан on 18.02.2017.
 */
public class CheckClientData {
    private CreditCardDAO creditCardDAO = new JDBCCreditCardDAO();
    private ClientDAO clientDAO = new JDBCClientDAO();

    public boolean checkCreditCardID(long cardID, int clientID){
        Account accountAuthentic = creditCardDAO.read(cardID).getCardAccount();
        return accountAuthentic.getAccountClient().getClientID() == clientID;
    }

    public  boolean checkAdminFlag(int clientID){
        Client client = clientDAO.read(clientID);
        if(client.isAdminFlag()){
            return true;
        }else{
            return false;
        }
    }
}
