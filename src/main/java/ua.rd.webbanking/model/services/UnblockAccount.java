package electronicPaymentSystem.model.services;

import electronicPaymentSystem.dao.AccountDAO;
import electronicPaymentSystem.dao.CreditCardDAO;
import electronicPaymentSystem.dao.impl.JDBCAccountDAO;
import electronicPaymentSystem.dao.impl.JDBCCreditCardDAO;
import electronicPaymentSystem.entities.Account;
import electronicPaymentSystem.entities.CreditCard;

/**
 * Created by Руслан on 04.03.2017.
 */
public class UnblockAccount {
    private CreditCardDAO creditCardDAO = new JDBCCreditCardDAO();
    private AccountDAO accountDAO = new JDBCAccountDAO();

    public void unblockConfirm(long cardID) {
        CreditCard creditCard = creditCardDAO.read(cardID);
        Account account = creditCard.getCardAccount();

        creditCard.setCardStatus("active");
        account.setAccountStatus("active");

        creditCardDAO.update(creditCard);
        accountDAO.update(account);
    }
}
