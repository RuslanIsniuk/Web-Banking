package ua.rd.webbanking.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.rd.webbanking.dao.AccountDAO;
import ua.rd.webbanking.dao.CreditCardDAO;
import ua.rd.webbanking.dao.impl.JDBCAccountDAO;
import ua.rd.webbanking.dao.impl.JDBCCreditCardDAO;
import ua.rd.webbanking.entities.Account;
import ua.rd.webbanking.entities.CreditCard;

public class UnblockAccount {
    @Autowired
    @Qualifier("HibernateCreditCardDAO")
    private CreditCardDAO creditCardDAO;
    @Autowired
    @Qualifier("HibernateAccountDAO")
    private AccountDAO accountDAO;

    public void unblockConfirm(long cardID) {
        CreditCard creditCard = creditCardDAO.read(cardID);
        Account account = creditCard.getCardAccount();

        creditCard.setCardStatus("active");
        account.setAccountStatus("active");

        creditCardDAO.update(creditCard);
        accountDAO.update(account);
    }
}
