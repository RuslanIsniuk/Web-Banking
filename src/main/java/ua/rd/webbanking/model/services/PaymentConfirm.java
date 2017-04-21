package ua.rd.webbanking.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.rd.webbanking.dao.AccountDAO;
import ua.rd.webbanking.dao.CreditCardDAO;
import ua.rd.webbanking.dao.PaymentDAO;
import ua.rd.webbanking.dao.impl.JDBCAccountDAO;
import ua.rd.webbanking.dao.impl.JDBCCreditCardDAO;
import ua.rd.webbanking.dao.impl.JDBCPaymentDAO;
import ua.rd.webbanking.entities.Account;
import ua.rd.webbanking.entities.CreditCard;
import ua.rd.webbanking.entities.Payment;
import ua.rd.webbanking.entities.PaymentType;
import ua.rd.webbanking.model.exceptions.*;

import java.math.BigDecimal;

public class PaymentConfirm {
    @Autowired
    @Qualifier("HibernateCreditCardDAO")
    private CreditCardDAO creditCardDAO ;
    @Autowired
    @Qualifier("HibernateAccountDAO")
    private AccountDAO accountDAO;
    @Autowired
    @Qualifier("HibernatePaymentDAO")
    private PaymentDAO paymentDAO;
    private Payment payment;

    public Payment makePayment(long cardID, BigDecimal amount, String destination, PaymentType paymentType) throws ServiceException {
        CreditCard creditCard = creditCardDAO.read(cardID);
        Account account = creditCard.getCardAccount();

        checkCreditCardsBalanceAndStatus(amount, creditCard);

        BigDecimal newBalance = account.getAccountBalance().subtract(amount);
        account.setAccountBalance(newBalance);
        accountDAO.update(account);

        payment = new Payment.PaymentBuilder()
                .paymentCreditCard(creditCard)
                .paymentAmount(amount)
                .build();


        switch (paymentType) {
            case COMMUNAL_PAYMENT:
                payment = new Payment.PaymentBuilder()
                        .paymentCreditCard(creditCard)
                        .paymentDestination("For communal services paid by " + account.getAccountClient().getClientFullName() + " on № " + destination + ".")
                        .paymentAmount(amount)
                        .build();
                paymentDAO.insert(payment);
                break;

            case MOBILE_PAYMENT:
                payment = new Payment.PaymentBuilder()
                        .paymentCreditCard(creditCard)
                        .paymentDestination("For mobile services paid by " + account.getAccountClient().getClientFullName() + " on № " + destination + ".")
                        .paymentAmount(amount)
                        .build();
                paymentDAO.insert(payment);
                break;

            case INTERNET_PAYMENT:
                payment = new Payment.PaymentBuilder()
                        .paymentCreditCard(creditCard)
                        .paymentDestination("For internet services paid by " + account.getAccountClient().getClientFullName() + " on № " + destination + ".")
                        .paymentAmount(amount)
                        .build();
                paymentDAO.insert(payment);
                break;

            case TV_PAYMENT:
                payment = new Payment.PaymentBuilder()
                        .paymentCreditCard(creditCard)
                        .paymentDestination("For TV services paid by " + account.getAccountClient().getClientFullName() + " on № " + destination + ".")
                        .paymentAmount(amount)
                        .build();
                paymentDAO.insert(payment);
                break;
        }

        return payment;
    }

    public Payment makePayment(long cardIDFrom, long cardIDTo, BigDecimal amount) throws ServiceException {
        CreditCard creditCardFrom = creditCardDAO.read(cardIDFrom);
        CreditCard creditCardTo = creditCardDAO.read(cardIDTo);

        if (creditCardTo.getCardID() == 0) {
            throw new CardNotFoundException(CreditCard.cardNumberToString(cardIDTo));
        }

        checkCreditCardsBalanceAndStatus(amount, creditCardFrom, creditCardTo);

        Account accountFrom = creditCardFrom.getCardAccount();
        Account accountTo = creditCardTo.getCardAccount();
        BigDecimal newBalanceFrom = accountFrom.getAccountBalance().subtract(amount);
        BigDecimal newBalanceTo = accountTo.getAccountBalance().add(amount);

        accountFrom.setAccountBalance(newBalanceFrom);
        accountTo.setAccountBalance(newBalanceTo);
        accountDAO.update(accountFrom);
        accountDAO.update(accountTo);

        payment = new Payment.PaymentBuilder()
                .paymentCreditCard(creditCardFrom)
                .paymentDestination("For mobile services paid by " + accountFrom.getAccountClient().getClientFullName() + " on № " + CreditCard.cardNumberToString(cardIDTo) + ".")
                .paymentAmount(amount)
                .build();
        paymentDAO.insert(payment);
        return payment;
    }

    private void checkCreditCardsBalanceAndStatus(BigDecimal amount, CreditCard cardFrom, CreditCard cardTo) throws ServiceException {
        if (cardFrom.getCardAccount().getAccountBalance().compareTo(amount) < 0) {
            throw new NotEnoughMoneyException();
        }

        if (("blocked".equals(cardFrom.getCardAccount().getAccountStatus())) || ("blocked".equals(cardTo.getCardAccount().getAccountStatus()))) {
            throw new AccountBlockedException();
        }

        if (("blocked".equals(cardFrom.getCardStatus())) || ("blocked".equals(cardTo.getCardStatus()))) {
            throw new CardBlockedException();
        }
    }

    private void checkCreditCardsBalanceAndStatus(BigDecimal amount, CreditCard card) throws ServiceException {

        if (card.getCardAccount().getAccountBalance().compareTo(amount) < 0) {
            throw new NotEnoughMoneyException();
        }

        if ("blocked".equals(card.getCardAccount().getAccountStatus())) {
            throw new AccountBlockedException();
        }

        if ("blocked".equals(card.getCardStatus())) {
            throw new CardBlockedException();
        }
    }

}
