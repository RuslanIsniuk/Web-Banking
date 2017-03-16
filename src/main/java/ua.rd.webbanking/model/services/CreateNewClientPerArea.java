package electronicPaymentSystem.model.services;

import electronicPaymentSystem.dao.AccountDAO;
import electronicPaymentSystem.dao.ClientDAO;
import electronicPaymentSystem.dao.CreditCardDAO;
import electronicPaymentSystem.dao.impl.JDBCAccountDAO;
import electronicPaymentSystem.dao.impl.JDBCClientDAO;
import electronicPaymentSystem.dao.impl.JDBCCreditCardDAO;
import electronicPaymentSystem.entities.Account;
import electronicPaymentSystem.entities.Client;
import electronicPaymentSystem.entities.CreditCard;
import electronicPaymentSystem.model.exceptions.DuplicateAccountDataException;
import electronicPaymentSystem.model.exceptions.DuplicateClientDataException;
import electronicPaymentSystem.model.exceptions.DuplicateCreditCardException;
import electronicPaymentSystem.model.exceptions.ServiceException;

import java.util.List;

/**
 * Created by Руслан on 03.03.2017.
 */
public class CreateNewClientPerArea {

    public int createNewClient(Client client)throws ServiceException{
        ClientDAO clientDAO = new JDBCClientDAO();
        List<Client> clientList = clientDAO.readAll();

        for(Client clientFromList:clientList){
            if(client.getClientLogin().equals(clientFromList.getClientLogin())){
                throw new DuplicateClientDataException();
            }

            if(client.getClientFullName().equals(clientFromList.getClientFullName())){
                throw new DuplicateClientDataException();
            }
        }

        return clientDAO.create(client);
    }

    public void createNewAccount(Account account) throws ServiceException{
        AccountDAO accountDAO = new JDBCAccountDAO();
        List<Account> accountList = accountDAO.readAll();

        for(Account accountFromList: accountList){
            if(account.getAccountID() == accountFromList.getAccountID()){
                throw new DuplicateAccountDataException();
            }
        }

        accountDAO.create(account);
    }

    public void createNewCreditCard(CreditCard creditCard) throws ServiceException{
        CreditCardDAO creditCardDAO = new JDBCCreditCardDAO();
        List<CreditCard> creditCardList = creditCardDAO.readAll();

        for(CreditCard creditCardFromList:creditCardList){
            if(creditCard.getCardID() == creditCardFromList.getCardID()){
                throw new DuplicateCreditCardException();
            }
        }

        creditCardDAO.create(creditCard);
    }
}
