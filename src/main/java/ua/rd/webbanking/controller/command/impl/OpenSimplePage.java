package ua.rd.webbanking.controller.command.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ua.rd.webbanking.controller.command.Command;
import ua.rd.webbanking.controller.exceptions.AuthorizationException;
import ua.rd.webbanking.entities.Account;
import ua.rd.webbanking.entities.Client;
import ua.rd.webbanking.entities.CreditCard;
import ua.rd.webbanking.model.services.GetBlockedAccountsData;
import ua.rd.webbanking.model.services.GetClientsData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class OpenSimplePage extends Command {
    private String pathToJSP;
    @Autowired
    private GetClientsData getClientsData;
    @Autowired
    private GetBlockedAccountsData getBlockedAccountsData;

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

            case "logOut":
                pathToJSP = logOut(request);
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
        return "PersonalClientArea";
    }

    private String openPerAdminAreaPage(HttpServletRequest request) throws AuthorizationException {
        Client client = getClientFromSession(request);

        if (!request.isRequestedSessionIdValid()) {
            throw new AuthorizationException();
        }

        request.setAttribute("clientName", client.getClientFullName());
        return "PersonalAdminArea";
    }

    private String openUnblockAccPage(HttpServletRequest request) throws AuthorizationException {
        Client client = getClientFromSession(request);

        if (!checkClientData.checkAdminFlag(client.getClientID())) {
            throw new AuthorizationException();
        }

        long cardID = getCreditCardIDFromRequest(request);
        CreditCard creditCard = getClientsData.getCreditCardInfo(cardID);
        String clientFullName = request.getParameter("clientFullName");

        request.setAttribute("cardID", creditCard.getCardID());
        request.setAttribute("cardIDStr", CreditCard.cardNumberToString(cardID));
        request.setAttribute("accountID", creditCard.getCardAccount().getAccountID());
        request.setAttribute("clientFullName", clientFullName);
        return "/adminPages/UnblockAccPage.jsp";
    }

    private String openDeleteAccPage(HttpServletRequest request) throws AuthorizationException {
        Client adminClient = getClientFromSession(request);
        String clientFullName = request.getParameter("clientFullName");
        int clientID = Integer.parseInt(request.getParameter("clientID"));

        if (!checkClientData.checkAdminFlag(adminClient.getClientID())) {
            throw new AuthorizationException();
        }

        request.setAttribute("clientFullName", clientFullName);
        request.setAttribute("clientID", clientID);
        return "/adminPages/DeleteAccPage.jsp";
    }

    private String openClientDetailsPage(HttpServletRequest request) throws AuthorizationException {
        Client clientAdmin = getClientFromSession(request);

        if (!checkClientData.checkAdminFlag(clientAdmin.getClientID())) {
            throw new AuthorizationException();
        }

        int clientID = Integer.parseInt(request.getParameter("clientID"));
        Client client = getClientsData.getClient(clientID);
        request.setAttribute("accountList", getClientsData.getAccounts(client));
        request.setAttribute("cardList", getClientsData.getCreditCards(client));
        request.setAttribute("cardListStr", getClientsData.getCreditCardsStr(client));
        request.setAttribute("client", client);
        return "/adminPages/ClientDetailsPage.jsp";
    }

    private String openCardOperationMenu(HttpServletRequest request) throws AuthorizationException, NumberFormatException {
        checkCreditCardOwner(request);
        long cardID = getCreditCardIDFromRequest(request);
        String cardIDStr = CreditCard.cardNumberToString(cardID);

        request.setAttribute("cardID", cardID);
        request.setAttribute("cardIDStr", cardIDStr);
        return "/CardOperationPage.jsp";
    }

    private String openBlockAccPage(HttpServletRequest request) throws AuthorizationException, NumberFormatException {
        checkCreditCardOwner(request);
        long cardID = getCreditCardIDFromRequest(request);
        String cardIDStr = CreditCard.cardNumberToString(cardID);

        Account account = getClientsData.getCreditCardInfo(cardID).getCardAccount();
        request.setAttribute("accountID", account.getAccountID());
        request.setAttribute("cardID", cardID);
        request.setAttribute("cardIDStr", cardIDStr);
        return "/clientPages/BlockAccount.jsp";
    }

    private String openCardTransactionPage(HttpServletRequest request) throws AuthorizationException, NumberFormatException {
        checkCreditCardOwner(request);
        long cardID = getCreditCardIDFromRequest(request);
        String cardIDStr = CreditCard.cardNumberToString(cardID);

        request.setAttribute("cardID", cardID);
        request.setAttribute("cardIDStr", cardIDStr);
        return "/clientPages/CardTransaction.jsp";
    }

    private String openCommPaymentPage(HttpServletRequest request) throws AuthorizationException, NumberFormatException {
        checkCreditCardOwner(request);
        long cardID = getCreditCardIDFromRequest(request);
        String cardIDStr = CreditCard.cardNumberToString(cardID);

        request.setAttribute("cardID", cardID);
        request.setAttribute("cardIDStr", cardIDStr);
        return "/clientPages/CommunalPayment.jsp";
    }

    private String openMobilePaymentPage(HttpServletRequest request) throws AuthorizationException, NumberFormatException {
        checkCreditCardOwner(request);
        long cardID = getCreditCardIDFromRequest(request);
        String cardIDStr = CreditCard.cardNumberToString(cardID);

        request.setAttribute("cardID", cardID);
        request.setAttribute("cardIDStr", cardIDStr);
        return "/clientPages/MobilePayment.jsp";
    }

    private String openInternetPaymentPage(HttpServletRequest request) throws AuthorizationException, NumberFormatException {
        checkCreditCardOwner(request);
        long cardID = getCreditCardIDFromRequest(request);
        String cardIDStr = CreditCard.cardNumberToString(cardID);

        request.setAttribute("cardID", cardID);
        request.setAttribute("cardIDStr", cardIDStr);
        return "/clientPages/InternetPayment.jsp";
    }

    private String openTVPaymentPage(HttpServletRequest request) throws AuthorizationException, NumberFormatException {
        checkCreditCardOwner(request);
        long cardID = getCreditCardIDFromRequest(request);
        String cardIDStr = CreditCard.cardNumberToString(cardID);

        request.setAttribute("cardID", cardID);
        request.setAttribute("cardIDStr", cardIDStr);
        return "/clientPages/TVPayment.jsp";
    }

    private String openTranToAnoCardPage(HttpServletRequest request) throws AuthorizationException, NumberFormatException {
        checkCreditCardOwner(request);
        long cardID = getCreditCardIDFromRequest(request);
        String cardIDStr = CreditCard.cardNumberToString(cardID);

        request.setAttribute("cardID", cardID);
        request.setAttribute("cardIDStr", cardIDStr);
        return "/clientPages/TransferMToAnotherCard.jsp";
    }

    private String openAllClientsAccPage(HttpServletRequest request) throws AuthorizationException {
        Client client = getClientFromSession(request);

        if (!checkClientData.checkAdminFlag(client.getClientID())) {
            throw new AuthorizationException();
        }

        request.setAttribute("clientList", getClientsData.getClientsList());
        return "/adminPages/ClientsListPage.jsp";
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
        return "/adminPages/BlockedAccListPage.jsp";
    }

    private String openFormForCreatingNewAccPage(HttpServletRequest request) throws AuthorizationException {
        Client client = getClientFromSession(request);

        if (!checkClientData.checkAdminFlag(client.getClientID())) {
            throw new AuthorizationException();
        }
        return "/adminPages/CreateNewClientPage.jsp";
    }

    private String logOut(HttpServletRequest request) throws AuthorizationException {
        if (request.getSession() instanceof HttpSession) {
            request.getSession(false).invalidate();
            HttpSession session = request.getSession(false);
            if (session != null) {
                throw new AuthorizationException("Error! Session not closed!");
            }
        }

        return "/index0.jsp";
    }
}
