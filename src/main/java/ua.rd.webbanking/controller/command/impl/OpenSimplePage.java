package electronicPaymentSystem.controller.command.impl;

import electronicPaymentSystem.controller.command.Command;
import electronicPaymentSystem.controller.exceptions.AuthorizationException;
import electronicPaymentSystem.entities.Account;
import electronicPaymentSystem.entities.Client;
import electronicPaymentSystem.entities.CreditCard;
import electronicPaymentSystem.model.services.CheckClientData;
import electronicPaymentSystem.model.services.GetBlockedAccountsData;
import electronicPaymentSystem.model.services.GetClientsData;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Руслан on 21.02.2017.
 */
public class OpenSimplePage extends Command {
    private String pathToJSP;
    private GetClientsData getClientsData = new GetClientsData();
    private CheckClientData checkClientData = new CheckClientData();
    private GetBlockedAccountsData getBlockedAccountsData = new GetBlockedAccountsData();

    @Override
    public String execute(HttpServletRequest request) throws AuthorizationException {
        String actionType = request.getParameter("actionType");

        switch (actionType) {
            case "CheckLoginData":
                Client client = (Client) request.getSession().getAttribute("clientData");
                if (client.isAdminFlag()) {
                    pathToJSP = openPerAdminAreaPage(request);
                } else {
                    pathToJSP = openPerClientAreaPage(request);
                }
                break;

            case "returnToPerArea":
                pathToJSP = openPerClientAreaPage(request);
                break;

            case "returnToPerAdminAreaPage":
                pathToJSP = openPerAdminAreaPage(request);
                break;

            case "openCardOperationMenu":
                pathToJSP = openCardOperationMenu(request);
                break;

            case "openBlockAccPage":
                pathToJSP = openBlockAccPage(request);
                break;

            case "openCardTransactionPage":
                pathToJSP = openCardTransactionPage(request);
                break;

            case "openCommPaymentPage":
                pathToJSP = openCommPaymentPage(request);
                break;

            case "openMobilePaymentPage":
                pathToJSP = openMobilePaymentPage(request);
                break;

            case "openTranToAnoCardPage":
                pathToJSP = openTranToAnoCardPage(request);
                break;

            case "openAllClientsAccPage":
                pathToJSP = openAllClientsAccPage(request);
                break;

            case "openBlockedAccountsPage":
                pathToJSP = openBlockedAccountsPage(request);
                break;

            case "openFormForCreatingNewAccPage":
                pathToJSP = openFormForCreatingNewAccPage(request);
                break;

            case "openUnblockAccPage":
                pathToJSP = openUnblockAccPage(request);
                break;

            case "openDeleteAccPage":
                pathToJSP = openDeleteAccPage(request);
                break;

            case "openClientDetailsPage":
                pathToJSP = openClientDetailsPage(request);
                break;

            case "openInternetPaymentPage":
                pathToJSP = openInternetPaymentPage(request);
                break;

            case "openTVPaymentPage":
                pathToJSP = openTVPaymentPage(request);
                break;

            default:
//                add logger here...
                break;
        }

        return pathToJSP;
    }

    private String openPerClientAreaPage(HttpServletRequest request) throws AuthorizationException {
        Client client = getClientFromSession(request);

        if (!request.isRequestedSessionIdValid()) {
            throw new AuthorizationException();
        }

        request.setAttribute("cardListStr", getClientsData.getCreditCardsStr(client));
        request.setAttribute("cardList", getClientsData.getCreditCards(client));
        request.setAttribute("accountList", getClientsData.getAccounts(client));
        request.setAttribute("clientName", client.getClientFullName());
        return "/PersonalClientArea.jsp";
    }

    private String openPerAdminAreaPage(HttpServletRequest request) throws AuthorizationException {
        Client client = getClientFromSession(request);

        if (!request.isRequestedSessionIdValid()) {
            throw new AuthorizationException();
        }

        request.setAttribute("clientName", client.getClientFullName());
        return "/PersonalAdminArea.jsp";
    }

    private String openUnblockAccPage(HttpServletRequest request) throws AuthorizationException{
        Client client = getClientFromSession(request);

        if (!checkClientData.checkAdminFlag(client.getClientID())) {
            throw new AuthorizationException();
        }

        long cardID = getCreditCardIDFromRequest(request);
        CreditCard creditCard = getClientsData.getCreditCardInfo(cardID);

        request.setAttribute("cardID",creditCard.getCardID());
        request.setAttribute("cardIDStr",CreditCard.cardNumberToString(cardID));
        request.setAttribute("accountID",creditCard.getCardAccount().getAccountID());
        return "/adminOperationImpl/UnblockAccPage.jsp";
    }

    private String openDeleteAccPage(HttpServletRequest request)throws AuthorizationException{
        Client adminClient = getClientFromSession(request);
        String clientFullName = request.getParameter("clientFullName");
        int clientID = Integer.parseInt(request.getParameter("clientID"));

        if (!checkClientData.checkAdminFlag(adminClient.getClientID())) {
            throw new AuthorizationException();
        }

        request.setAttribute("clientFullName",clientFullName);
        request.setAttribute("clientID",clientID);
        return "/adminOperationImpl/DeleteAccPage.jsp";
    }

    private String openClientDetailsPage(HttpServletRequest request) throws AuthorizationException{
        Client clientAdmin = getClientFromSession(request);

        if (!checkClientData.checkAdminFlag(clientAdmin.getClientID())) {
            throw new AuthorizationException();
        }

        int clientID = Integer.parseInt(request.getParameter("clientID"));
        Client client = getClientsData.getClient(clientID);
        request.setAttribute("accountList",getClientsData.getAccounts(client));
        request.setAttribute("cardList",getClientsData.getCreditCards(client));
        request.setAttribute("cardListStr",getClientsData.getCreditCardsStr(client));
        request.setAttribute("client",client);
        return "/adminOperationImpl/ClientDetailsPage.jsp";
    }

    private String openCardOperationMenu(HttpServletRequest request) throws AuthorizationException {
        checkCreditCardOwner(request);
        long cardID = getCreditCardIDFromRequest(request);
        String cardIDStr = CreditCard.cardNumberToString(cardID);

        request.setAttribute("cardID", cardID);
        request.setAttribute("cardIDStr", cardIDStr);
        return "/CardOperationPage.jsp";
    }

    private String openBlockAccPage(HttpServletRequest request) throws AuthorizationException {
        checkCreditCardOwner(request);
        long cardID = getCreditCardIDFromRequest(request);
        String cardIDStr = CreditCard.cardNumberToString(cardID);

        Account account = getClientsData.getCreditCardInfo(cardID).getCardAccount();
        request.setAttribute("accountID", account.getAccountID());
        request.setAttribute("cardID", cardID);
        request.setAttribute("cardIDStr", cardIDStr);
        return "/cardOperationImpl/BlockAccount.jsp";
    }

    private String openCardTransactionPage(HttpServletRequest request) throws AuthorizationException {
        checkCreditCardOwner(request);
        long cardID = getCreditCardIDFromRequest(request);
        String cardIDStr = CreditCard.cardNumberToString(cardID);

        request.setAttribute("cardID", cardID);
        request.setAttribute("cardIDStr", cardIDStr);
        return "/cardOperationImpl/CardTransaction.jsp";
    }

    private String openCommPaymentPage(HttpServletRequest request) throws AuthorizationException {
        checkCreditCardOwner(request);
        long cardID = getCreditCardIDFromRequest(request);
        String cardIDStr = CreditCard.cardNumberToString(cardID);

        request.setAttribute("cardID", cardID);
        request.setAttribute("cardIDStr", cardIDStr);
        return "/cardOperationImpl/CommunalPayment.jsp";
    }

    private String openMobilePaymentPage(HttpServletRequest request) throws AuthorizationException {
        checkCreditCardOwner(request);
        long cardID = getCreditCardIDFromRequest(request);
        String cardIDStr = CreditCard.cardNumberToString(cardID);

        request.setAttribute("cardID", cardID);
        request.setAttribute("cardIDStr", cardIDStr);
        return "/cardOperationImpl/MobilePayment.jsp";
    }

    private String openInternetPaymentPage(HttpServletRequest request) throws AuthorizationException{
        checkCreditCardOwner(request);
        long cardID = getCreditCardIDFromRequest(request);
        String cardIDStr = CreditCard.cardNumberToString(cardID);

        request.setAttribute("cardID", cardID);
        request.setAttribute("cardIDStr", cardIDStr);
        return "/cardOperationImpl/InternetPayment.jsp";
    }

    private String openTVPaymentPage(HttpServletRequest request) throws AuthorizationException{
        checkCreditCardOwner(request);
        long cardID = getCreditCardIDFromRequest(request);
        String cardIDStr = CreditCard.cardNumberToString(cardID);

        request.setAttribute("cardID", cardID);
        request.setAttribute("cardIDStr", cardIDStr);
        return "/cardOperationImpl/TVPayment.jsp";
    }

    private String openTranToAnoCardPage(HttpServletRequest request) throws AuthorizationException {
        checkCreditCardOwner(request);
        long cardID = getCreditCardIDFromRequest(request);
        String cardIDStr = CreditCard.cardNumberToString(cardID);

        request.setAttribute("cardID", cardID);
        request.setAttribute("cardIDStr", cardIDStr);
        return "/cardOperationImpl/TransferMToAnotherCard.jsp";
    }

    private String openAllClientsAccPage(HttpServletRequest request) throws AuthorizationException {
        Client client = getClientFromSession(request);

        if (!checkClientData.checkAdminFlag(client.getClientID())) {
            throw new AuthorizationException();
        }

        request.setAttribute("clientList", getClientsData.getClientsList());
        return "/adminOperationImpl/ClientsListPage.jsp";
    }

    private String openBlockedAccountsPage(HttpServletRequest request) throws AuthorizationException {
        Client client = getClientFromSession(request);

        if (!checkClientData.checkAdminFlag(client.getClientID())) {
            throw new AuthorizationException();
        }

        List<CreditCard> creditCards = getBlockedAccountsData.getBlockedCardsList();
        List<String> creditCardsStr = getClientsData.getCreditCardsStr(creditCards);
        List<Account> accounts = getBlockedAccountsData.getAccountsListByBlockedCards();
        List<Client> clients = getBlockedAccountsData.getClientsListByBlockedCards();

        request.setAttribute("cardListStr", creditCardsStr);
        request.setAttribute("cardList", creditCards);
        request.setAttribute("accountList", accounts);
        request.setAttribute("clientList", clients);
        request.setAttribute("sizeList", creditCards.size());
        return "/adminOperationImpl/BlockedAccListPage.jsp";
    }

    private String openFormForCreatingNewAccPage(HttpServletRequest request) throws AuthorizationException {
        Client client = getClientFromSession(request);

        if (!checkClientData.checkAdminFlag(client.getClientID())) {
            throw new AuthorizationException();
        }
        return "/adminOperationImpl/CreateNewClientPage.jsp";
    }
}
