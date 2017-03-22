package ua.rd.webbanking.model.services;

import ua.rd.webbanking.dao.AccountDAO;
import ua.rd.webbanking.dao.ClientDAO;
import ua.rd.webbanking.dao.CreditCardDAO;
import ua.rd.webbanking.dao.impl.JDBCAccountDAO;
import ua.rd.webbanking.dao.impl.JDBCClientDAO;
import ua.rd.webbanking.dao.impl.JDBCCreditCardDAO;
import ua.rd.webbanking.entities.Account;
import ua.rd.webbanking.entities.Client;
import ua.rd.webbanking.entities.CreditCard;
import ua.rd.webbanking.model.exceptions.DuplicateAccountDataException;
import ua.rd.webbanking.model.exceptions.DuplicateClientDataException;
import ua.rd.webbanking.model.exceptions.DuplicateCreditCardException;
import ua.rd.webbanking.model.exceptions.ServiceException;

import java.util.List;

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

        return clientDAO.insert(client);
    }

    public void createNewAccount(Account account) throws ServiceException{
        AccountDAO accountDAO = new JDBCAccountDAO();
        List<Account> accountList = accountDAO.readAll();

        for(Account accountFromList: accountList){
            if(account.getAccountID() == accountFromList.getAccountID()){
                throw new DuplicateAccountDataException();
            }
        }

        accountDAO.insert(account);
    }

    public void createNewCreditCard(CreditCard creditCard) throws ServiceException{
        CreditCardDAO creditCardDAO = new JDBCCreditCardDAO();
        List<CreditCard> creditCardList = creditCardDAO.readAll();

        for(CreditCard creditCardFromList:creditCardList){
            if(creditCard.getCardID() == creditCardFromList.getCardID()){
                throw new DuplicateCreditCardException();
            }
        }

        creditCardDAO.insert(creditCard);
    }
}
