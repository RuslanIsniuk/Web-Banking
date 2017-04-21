package ua.rd.webbanking.model.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.rd.webbanking.dao.AccountDAO;
import ua.rd.webbanking.dao.CreditCardDAO;
import ua.rd.webbanking.dao.impl.JDBCAccountDAO;
import ua.rd.webbanking.dao.impl.JDBCCreditCardDAO;
import ua.rd.webbanking.entities.Account;
import ua.rd.webbanking.entities.CreditCard;

public class BlockAccount {
    @Autowired
    @Qualifier("HibernateAccountDAO")
    private AccountDAO accountDAO;
    @Autowired
    @Qualifier("HibernateCreditCardDAO")
    private CreditCardDAO creditCardDAO;

    public void BlockConfirm(long cardID) {
        CreditCard creditCard = creditCardDAO.read(cardID);
        Account account = creditCard.getCardAccount();

        creditCard.setCardStatus("blocked");
        account.setAccountStatus("blocked");

        creditCardDAO.update(creditCard);
        accountDAO.update(account);
    }
}
