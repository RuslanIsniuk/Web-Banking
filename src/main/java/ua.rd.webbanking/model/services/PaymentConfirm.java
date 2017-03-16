package electronicPaymentSystem.model.services;

import electronicPaymentSystem.dao.AccountDAO;
import electronicPaymentSystem.dao.CreditCardDAO;
import electronicPaymentSystem.dao.PaymentDAO;
import electronicPaymentSystem.dao.impl.JDBCAccountDAO;
import electronicPaymentSystem.dao.impl.JDBCCreditCardDAO;
import electronicPaymentSystem.dao.impl.JDBCPaymentDAO;
import electronicPaymentSystem.entities.Account;
import electronicPaymentSystem.entities.CreditCard;
import electronicPaymentSystem.entities.Payment;
import electronicPaymentSystem.entities.PaymentType;
import electronicPaymentSystem.model.exceptions.*;

import java.math.BigDecimal;

/**
 * Created by Руслан on 28.02.2017.
 */
public class PaymentConfirm {
    private CreditCardDAO creditCardDAO = new JDBCCreditCardDAO();
    private AccountDAO accountDAO = new JDBCAccountDAO();
    private PaymentDAO paymentDAO = new JDBCPaymentDAO();
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
                paymentDAO.create(payment);
                break;

            case MOBILE_PAYMENT:
                payment = new Payment.PaymentBuilder()
                        .paymentCreditCard(creditCard)
                        .paymentDestination("For mobile services paid by " + account.getAccountClient().getClientFullName() + " on № " + destination + ".")
                        .paymentAmount(amount)
                        .build();
                paymentDAO.create(payment);
                break;

            case INTERNET_PAYMENT:
                payment = new Payment.PaymentBuilder()
                        .paymentCreditCard(creditCard)
                        .paymentDestination("For internet services paid by " + account.getAccountClient().getClientFullName() + " on № " + destination + ".")
                        .paymentAmount(amount)
                        .build();
                paymentDAO.create(payment);
                break;

            case TV_PAYMENT:
                payment = new Payment.PaymentBuilder()
                        .paymentCreditCard(creditCard)
                        .paymentDestination("For TV services paid by " + account.getAccountClient().getClientFullName() + " on № " + destination + ".")
                        .paymentAmount(amount)
                        .build();
                paymentDAO.create(payment);
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
        paymentDAO.create(payment);
        return payment;
    }

    private void checkCreditCardsBalanceAndStatus(BigDecimal amount, CreditCard cardFrom, CreditCard cardTo) throws ServiceException {
        if (cardFrom.getCardAccount().getAccountBalance().compareTo(amount) < 0) {
            throw new NotEnoughMoneyException();
        }

        if (("block".equals(cardFrom.getCardAccount().getAccountStatus())) || ("block".equals(cardTo.getCardAccount().getAccountStatus()))) {
            throw new AccountBlockedException();
        }

        if (("block".equals(cardFrom.getCardStatus())) || ("block".equals(cardTo.getCardStatus()))) {
            throw new CardBlockedException();
        }
    }

    private void checkCreditCardsBalanceAndStatus(BigDecimal amount, CreditCard card) throws ServiceException {

        if (card.getCardAccount().getAccountBalance().compareTo(amount) < 0) {
            throw new NotEnoughMoneyException();
        }

        if ("block".equals(card.getCardAccount().getAccountStatus())) {
            throw new AccountBlockedException();
        }

        if ("block".equals(card.getCardStatus())) {
            throw new CardBlockedException();
        }
    }

}
