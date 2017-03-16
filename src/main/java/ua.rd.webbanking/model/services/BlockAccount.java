package electronicPaymentSystem.model.services;

import electronicPaymentSystem.dao.AccountDAO;
import electronicPaymentSystem.dao.CreditCardDAO;
import electronicPaymentSystem.dao.impl.JDBCAccountDAO;
import electronicPaymentSystem.dao.impl.JDBCCreditCardDAO;
import electronicPaymentSystem.entities.Account;
import electronicPaymentSystem.entities.CreditCard;

/**
 * Created by Руслан on 21.02.2017.
 */
public class BlockAccount {
    private AccountDAO accountDAO = new JDBCAccountDAO();
    private CreditCardDAO creditCardDAO = new JDBCCreditCardDAO();

    public void BlockConfirm(long cardID) {
        CreditCard creditCard = creditCardDAO.read(cardID);
        Account account = creditCard.getCardAccount();

        creditCard.setCardStatus("blocked");
        account.setAccountStatus("blocked");

        creditCardDAO.update(creditCard);
        accountDAO.update(account);
    }
}
