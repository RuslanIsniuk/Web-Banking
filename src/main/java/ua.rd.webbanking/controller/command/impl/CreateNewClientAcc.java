package electronicPaymentSystem.controller.command.impl;

import electronicPaymentSystem.controller.command.Command;
import electronicPaymentSystem.controller.exceptions.AuthorizationException;
import electronicPaymentSystem.controller.exceptions.InvalidAccountDataInputException;
import electronicPaymentSystem.controller.exceptions.InvalidCardDataInputException;
import electronicPaymentSystem.controller.exceptions.InvalidClientDataInputException;
import electronicPaymentSystem.entities.Account;
import electronicPaymentSystem.entities.Client;
import electronicPaymentSystem.entities.CreditCard;
import electronicPaymentSystem.model.exceptions.ServiceException;
import electronicPaymentSystem.model.services.CheckClientData;
import electronicPaymentSystem.model.services.CreateNewClientPerArea;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Руслан on 26.02.2017.
 */
public class CreateNewClientAcc extends Command {
    private static final Logger logger = Logger.getLogger(CreateNewClientAcc.class);

    private CheckClientData checkClientData = new CheckClientData();
    private CreateNewClientPerArea createNewClientPerArea = new CreateNewClientPerArea();

    private static final String LOGIN_OR_PASS_FIELD = "\\w{3,25}";
    private static final String FULL_NAME_FIELD = "(\\w{3,20}\\s{1,5}\\w{3,20})||(\\w{3,12}\\s{1,2}\\w{3,12}\\s{1,2}\\w{3,12})";
    private static final String ACC_ID_FIELD = "[1-9]\\d{5}";
    private static final String ACC_BALANCE_FIELD = "([1234]?\\d{1,4}(\\u002E\\d{1,2})?)||([5][0]{4}(\\u002E[0]{1,2})?)";
    private static final String CARD_ID_FIELD = "\\d{16}";
    private static final String CARD_PIN_FIELD = "\\d{4}";
    private static final String CARD_DATA_FIELD = "[2][0][1-2][0-9][-](([0]?[1-9])||([1][0-2]))[-](([0-2]?[1-9])||([3][01])||([12][0]))";
    private static final String DEFAULT_PASS_TO_JSP = "/adminOperationImpl/CreateNewClientPage.jsp";

    @Override
    public String execute(HttpServletRequest request) throws AuthorizationException {
        String pathToJSP = "";
        Client clientAdmin = getClientFromSession(request);

        if (!checkClientData.checkAdminFlag(clientAdmin.getClientID())) {
            throw new AuthorizationException();
        }

        try {
            Client client = validationClientDataInput(request);
            client.setClientID(createNewClientPerArea.createNewClient(client));
            Account account = validationAccountDataInput(request, client);
            createNewClientPerArea.createNewAccount(account);
            CreditCard creditCard = validationCardDataInput(request, account);
            createNewClientPerArea.createNewCreditCard(creditCard);
            request.setAttribute("statusMessage","New client created successfully!");
            pathToJSP = "/adminOperationImpl/OperationConfirm.jsp";

        } catch (InvalidClientDataInputException icde) {
            logger.error(icde);
            request.setAttribute("errorMessageClient", icde.getMessage());
            pathToJSP = DEFAULT_PASS_TO_JSP;
        } catch (InvalidAccountDataInputException iade) {
            logger.error(iade);
            request.setAttribute("errorMessageAccount", iade.getMessage());
            pathToJSP = DEFAULT_PASS_TO_JSP;
        } catch (InvalidCardDataInputException icde) {
            logger.error(icde);
            request.setAttribute("errorMessageCard", icde.getMessage());
            pathToJSP = DEFAULT_PASS_TO_JSP;
        }catch (ServiceException se){
            logger.error(se);
            request.setAttribute("errorMessage", se.getMessage());
            pathToJSP = DEFAULT_PASS_TO_JSP;
        }
        return pathToJSP;
    }

    private boolean validationOnCorrectInput(String checkedObject, String compileStr) {
        Pattern pattern = Pattern.compile(compileStr);
        Matcher matcher = pattern.matcher(checkedObject);
        return matcher.matches();
    }

    private Client validationClientDataInput(HttpServletRequest request) throws InvalidClientDataInputException {
        Client client = new Client();
        String clientLogin = request.getParameter("clientLogin");
        String clientPass = request.getParameter("clientPass");
        String clientFullName = request.getParameter("clientFullName");
        String clientAdminFlag = request.getParameter("adminFlag");

        if (!validationOnCorrectInput(clientLogin, LOGIN_OR_PASS_FIELD)) {
            throw new InvalidClientDataInputException("login");
        }

        if (!validationOnCorrectInput(clientPass, LOGIN_OR_PASS_FIELD)) {
            throw new InvalidClientDataInputException("password");
        }

        if (!validationOnCorrectInput(clientFullName, FULL_NAME_FIELD)) {
            throw new InvalidClientDataInputException("full name");
        }

        client.setClientLogin(clientLogin);
        client.setClientPass(clientPass);
        client.setClientFullName(clientFullName);

        if ("yes".equals(clientAdminFlag)) {
            client.setAdminFlag(true);
        } else {
            client.setAdminFlag(false);
        }

        return client;
    }

    private Account validationAccountDataInput(HttpServletRequest request, Client client) throws InvalidAccountDataInputException {
        Account account = new Account();
        String accountID = request.getParameter("accountID");
        String accountBalance = request.getParameter("accountBalance");
        String accountStatus = request.getParameter("accStatusFlag");

        if (!validationOnCorrectInput(accountID, ACC_ID_FIELD)) {
            throw new InvalidAccountDataInputException("ID");
        }

        if (!validationOnCorrectInput(accountBalance, ACC_BALANCE_FIELD)) {
            throw new InvalidAccountDataInputException("balance");
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.now();
        String accDate = dtf.format(localDate);

        account.setAccountID(Integer.parseInt(accountID));
        account.setAccountBalance(new BigDecimal(accountBalance));
        account.setAccountDateOpen(accDate);
        account.setAccountStatus(accountStatus);
        account.setAccountClient(client);

        return account;
    }

    private CreditCard validationCardDataInput(HttpServletRequest request, Account account) throws InvalidCardDataInputException {
        CreditCard creditCard = new CreditCard();
        String cardIDP1 = request.getParameter("cardIDPart1");
        String cardIDP2 = request.getParameter("cardIDPart2");
        String cardIDP3 = request.getParameter("cardIDPart3");
        String cardIDP4 = request.getParameter("cardIDPart4");
        String cardIDStr = cardIDP1 + cardIDP2 + cardIDP3 + cardIDP4;
        String cardPINStr = request.getParameter("cardPIN");
        String cardStatus = request.getParameter("cardStatusFlag");
        String dayStr = request.getParameter("dayDate");
        String monthStr = request.getParameter("monthDate");
        String yearStr = request.getParameter("yearDate");
        String cardDataStr = yearStr + "-" + monthStr + "-" + dayStr;

        if (!validationOnCorrectInput(cardIDStr, CARD_ID_FIELD)) {
            throw new InvalidCardDataInputException("ID");
        }

        if (!validationOnCorrectInput(cardPINStr, CARD_PIN_FIELD)) {
            throw new InvalidCardDataInputException("PIN");
        }

        if (!validationOnCorrectInput(cardDataStr, CARD_DATA_FIELD)) {
            throw new InvalidCardDataInputException("data");
        }

        creditCard.setCardID(Long.parseLong(cardIDStr));
        creditCard.setCardStatus(cardStatus);
        creditCard.setCardPIN(cardPINStr);
        creditCard.setCardValidDate(cardDataStr);
        creditCard.setCardAccount(account);

        return creditCard;
    }
}
