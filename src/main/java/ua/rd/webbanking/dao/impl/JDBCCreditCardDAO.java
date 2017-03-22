package ua.rd.webbanking.dao.impl;

import ua.rd.webbanking.dao.AccountDAO;
import ua.rd.webbanking.dao.ClientDAO;
import ua.rd.webbanking.dao.CreditCardDAO;
import ua.rd.webbanking.DBdata.ConnectionPool;
import ua.rd.webbanking.DBdata.ConnectionUtil;
import ua.rd.webbanking.entities.Account;
import ua.rd.webbanking.entities.CreditCard;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class JDBCCreditCardDAO implements CreditCardDAO {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(JDBCCreditCardDAO.class);

    private String SQLStatementRead;
    private String SQLStatementDelete;
    private String SQLStatementCreate;
    private String SQLStatementUpdate;
    private String SQLStatementReadAll;
    private String SQLStatementReadAllUsingAccID;
    private AccountDAO accountDAO = new JDBCAccountDAO();
    private ConnectionUtil connectionUtil = ConnectionPool.getInstance();

    public JDBCCreditCardDAO() {
        Properties properties = new Properties();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("JDBCCreditCardDAOConfig.properties")) {

            properties.load(inputStream);

            SQLStatementRead = properties.getProperty("StatementRead");
            SQLStatementUpdate = properties.getProperty("StatementUpdate");
            SQLStatementDelete = properties.getProperty("StatementDelete");
            SQLStatementCreate = properties.getProperty("StatementCreate");
            SQLStatementReadAllUsingAccID = properties.getProperty("StatementReadAllUsingAccID");
            SQLStatementReadAll = properties.getProperty("StatementReadAll");

        } catch (IOException ex) {
            logger.error(ex);
        }
    }

    CreditCard parseResultSet(ResultSet resultSet) throws SQLException {
        CreditCard creditCard = new CreditCard();

        creditCard.setCardID(resultSet.getLong("card_id"));
        creditCard.setCardStatus(resultSet.getString("card_status"));
        creditCard.setCardValidDate(resultSet.getDate("card_valid_data"));
        creditCard.setCardPIN(resultSet.getString("card_pin"));
        creditCard.setCardAccount(accountDAO.read(resultSet.getInt("account_id")));
        return creditCard;
    }

    @Override
    public List<CreditCard> readUsingAccountID(int accountID) {
        ArrayList<CreditCard> creditCardArrayList = new ArrayList<>();

        try (Connection connection = connectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLStatementReadAllUsingAccID)) {

            preparedStatement.setLong(1, accountID);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                creditCardArrayList.add(parseResultSet(resultSet));
            }

            resultSet.close();
            logger.debug("Read credit card data list successfully.");
        } catch (SQLException e) {
            logger.error(e);
        }

        if(creditCardArrayList.size() == 0){
            return null;
        }else{
            return creditCardArrayList;
        }
    }

    @Override
    public List<CreditCard> readAll(){
        ArrayList<CreditCard> creditCardArrayList = new ArrayList<>();

        try (Connection connection = connectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLStatementReadAll)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                creditCardArrayList.add(parseResultSet(resultSet));
            }

            resultSet.close();
            logger.debug("Read credit card data list successfully.");
        } catch (SQLException e) {
            logger.error(e);
        }
        return creditCardArrayList;
    }

    @Override
    public CreditCard read(long cardID) {
        CreditCard creditCard = null;

        try (Connection connection = connectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLStatementRead)) {

            preparedStatement.setLong(1, cardID);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                creditCard = parseResultSet(resultSet);
            }

            resultSet.close();
            logger.debug("Read credit card data successfully.");
        } catch (SQLException e) {
            logger.error(e);
        }
        return creditCard;
    }

    @Override
    public long insert(CreditCard creditCard) {
        long cardID = creditCard.getCardID();

        try (Connection connection = connectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLStatementCreate)) {

            preparedStatement.setInt(1, creditCard.getCardAccount().getAccountID());
            preparedStatement.setLong(2, creditCard.getCardID());
            preparedStatement.setString(3, creditCard.getCardPIN());
            preparedStatement.setDate(4, creditCard.getCardValidDate());
            preparedStatement.setString(5, creditCard.getCardStatus());

            preparedStatement.executeUpdate();
            logger.debug("Create credit card data successfully.");
        } catch (SQLException e) {
            logger.error(e);
        }
        return cardID;
    }

    @Override
    public void update(CreditCard creditCard) {

        try (Connection connection = connectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLStatementUpdate)) {

            preparedStatement.setInt(1,creditCard.getCardAccount().getAccountID());
            preparedStatement.setString(2, creditCard.getCardPIN());
            preparedStatement.setDate(3, creditCard.getCardValidDate());
            preparedStatement.setString(4, creditCard.getCardStatus());
            preparedStatement.setLong(5, creditCard.getCardID());

            preparedStatement.executeUpdate();
            logger.debug("Update credit card data list successfully.");
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    @Override
    public void delete(long cardID) {
        try (Connection connection = connectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLStatementDelete)) {

            preparedStatement.setLong(1, cardID);

            preparedStatement.executeUpdate();
            logger.debug("Delete credit card data successfully.");
        } catch (SQLException e) {
            logger.error(e);
        }
    }
}
