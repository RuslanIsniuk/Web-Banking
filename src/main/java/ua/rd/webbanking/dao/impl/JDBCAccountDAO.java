package ua.rd.webbanking.dao.impl;

import ua.rd.webbanking.dao.AccountDAO;
import ua.rd.webbanking.DBdata.ConnectionPool;
import ua.rd.webbanking.DBdata.ConnectionUtil;
import ua.rd.webbanking.dao.ClientDAO;
import ua.rd.webbanking.entities.Account;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Руслан on 22.12.2016.
 */
public class JDBCAccountDAO implements AccountDAO {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(JDBCAccountDAO.class);

    private String SQLStatementRead;
    private String SQLStatementUpdate;
    private String SQLStatementDelete;
    private String SQLStatementCreate;
    private String SQLStatementReadAll;
    private String SQLStatementReadUsingClientID;
    private ClientDAO clientDAO = new JDBCClientDAO();
    private ConnectionUtil connectionUtil = ConnectionPool.getInstance();

    public JDBCAccountDAO() {
        Properties properties = new Properties();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("JDBCAccountDAOConfig.properties")) {

            properties.load(inputStream);

            SQLStatementRead = properties.getProperty("StatementRead");
            SQLStatementUpdate = properties.getProperty("StatementUpdate");
            SQLStatementDelete = properties.getProperty("StatementDelete");
            SQLStatementCreate = properties.getProperty("StatementCreate");
            SQLStatementReadAll = properties.getProperty("StatementReadAll");
            SQLStatementReadUsingClientID = properties.getProperty("StatementReadUsingClientID");

        } catch (IOException ex) {
            logger.error(ex);
        }
    }

    Account parseResultSet(ResultSet resultSet) throws SQLException {
        Account account = new Account();
        
        account.setAccountID(resultSet.getInt("account_id"));
        account.setAccountBalance(resultSet.getBigDecimal("balance"));
        account.setAccountStatus(resultSet.getString("status"));
        account.setAccountDateOpen(resultSet.getDate("data_open"));
        account.setAccountClient(clientDAO.read(resultSet.getInt("client_id")));

        return account;
    }

    @Override
    public Account read(int accountID) {
        Account account = new Account();

        try (Connection connection = connectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLStatementRead)) {

            preparedStatement.setInt(1, accountID);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
               account= parseResultSet(resultSet);
            }
            resultSet.close();
            logger.debug("Read account data successfully.");
        } catch (SQLException e) {
            logger.error(e);
        }
        return account;
    }

    @Override
    public List<Account> readUsingClientID(int clientID) {
        ArrayList<Account> accountArrayList = new ArrayList<>();

        try (Connection connection = connectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLStatementReadUsingClientID)) {

            preparedStatement.setInt(1, clientID);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                accountArrayList.add(parseResultSet(resultSet));
            }
            resultSet.close();
            logger.debug("Read account data list successfully.");
        } catch (SQLException e) {
            logger.error(e);
        }
        return accountArrayList;
    }

    @Override
    public List<Account> readAll(){
        ArrayList<Account> accountArrayList = new ArrayList<>();

        try (Connection connection = connectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLStatementReadAll)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                accountArrayList.add(parseResultSet(resultSet));
            }

            resultSet.close();
            logger.debug("Read account data list successfully.");
        } catch (SQLException e) {
            logger.error(e);
        }
        return accountArrayList;
    }

    @Override
    public void create(Account account) {
        try (Connection connection = connectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLStatementCreate)) {

            preparedStatement.setInt(1, account.getAccountClient().getClientID());
            preparedStatement.setInt(2, account.getAccountID());
            preparedStatement.setBigDecimal(3, account.getAccountBalance());
            preparedStatement.setString(4, account.getAccountStatus());
            preparedStatement.setDate(5, account.getAccountDateOpen());

            preparedStatement.executeUpdate();
            logger.debug("Create account data successfully.");
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    @Override
    public void update(Account account) {
        try (Connection connection = connectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLStatementUpdate)) {

            preparedStatement.setInt(1,  account.getAccountClient().getClientID());
            preparedStatement.setBigDecimal(2, account.getAccountBalance());
            preparedStatement.setString(3, account.getAccountStatus());
            preparedStatement.setDate(4, account.getAccountDateOpen());
            preparedStatement.setInt(5, account.getAccountID());
            preparedStatement.executeUpdate();
            logger.debug("Update account data successfully.");
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    @Override
    public void delete(int accountID) {

        try (Connection connection = connectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLStatementDelete)) {

            preparedStatement.setInt(1, accountID);

            preparedStatement.executeUpdate();
            logger.debug("Delete account data successfully.");
        } catch (SQLException e) {
            logger.error(e);
        }
    }
}
