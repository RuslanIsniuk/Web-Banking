package ua.rd.webbanking.dao.impl;

import ua.rd.webbanking.DBdata.ConnectionPoolH2;
import ua.rd.webbanking.dao.ClientDAO;
import ua.rd.webbanking.DBdata.ConnectionPool;
import ua.rd.webbanking.DBdata.ConnectionUtil;
import ua.rd.webbanking.entities.Client;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import java.sql.*;

public class JDBCClientDAO implements ClientDAO {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(JDBCClientDAO.class);

    private String SQLStatementRead;
    private String SQLStatementCreate;
    private String SQLStatementUpdate;
    private String SQLStatementDelete;
    private String SQLStatementReadWhenLogGiven;
    private String SQLStatementReadAll;

    private ConnectionUtil connectionUtil;

    public JDBCClientDAO() {
        Properties properties = new Properties();
        connectionUtil = ConnectionPool.getInstance();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("JDBCClientDAOConfig.properties")) {

            properties.load(inputStream);

            SQLStatementRead = properties.getProperty("StatementRead");
            SQLStatementUpdate = properties.getProperty("StatementUpdate");
            SQLStatementDelete = properties.getProperty("StatementDelete");
            SQLStatementCreate = properties.getProperty("StatementCreate");
            SQLStatementReadAll = properties.getProperty("StatementReadAll");
            SQLStatementReadWhenLogGiven = properties.getProperty("StatementReadWhenLogGiven");

        } catch (IOException ex) {
            logger.error(ex);
        }
    }

    public JDBCClientDAO(DataSource dataSource) {
        Properties properties = new Properties();
        connectionUtil = new ConnectionPoolH2(dataSource);

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("JDBCClientDAOConfig.properties")) {

            properties.load(inputStream);

            SQLStatementRead = properties.getProperty("StatementRead");
            SQLStatementUpdate = properties.getProperty("StatementUpdate");
            SQLStatementDelete = properties.getProperty("StatementDelete");
            SQLStatementCreate = properties.getProperty("StatementCreate");
            SQLStatementReadAll = properties.getProperty("StatementReadAll");
            SQLStatementReadWhenLogGiven = properties.getProperty("StatementReadWhenLogGiven");

        } catch (IOException ex) {
            logger.error(ex);
        }
    }

    Client parseResultSet(ResultSet resultSet) throws SQLException {
        Client client = new Client();

        client.setClientID(resultSet.getInt("client_id"));
        client.setClientPass(resultSet.getString("client_password"));
        client.setClientLogin(resultSet.getString("client_login"));
        client.setClientFullName(resultSet.getString("client_full_name"));
        client.setAdminFlag(resultSet.getInt("admin_flag"));

        return client;
    }

    @Override
    public Client read(int clientID) {
        Client client = null;

        try (Connection connection = connectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLStatementRead)) {

            preparedStatement.setInt(1, clientID);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                client = parseResultSet(resultSet);
            }
            resultSet.close();
            logger.debug("Read client data successfully.");
        } catch (SQLException e) {
            logger.error(e);
        }
        return client;
    }

    @Override
    public int insert(Client client) {
        int clientID = 0;

        try (Connection connection = connectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLStatementCreate, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, client.getClientLogin());
            preparedStatement.setString(2, client.getClientPass());
            preparedStatement.setString(3, client.getClientFullName());
            preparedStatement.setInt(4, client.getAdminFlagInt());

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                clientID = resultSet.getInt(1);
            }
            resultSet.close();
            logger.debug("Create new client successfully.");
        } catch (SQLException e) {
            logger.error(e);
        }
        return clientID;
    }

    @Override
    public void update(Client client) {

        try (Connection connection = connectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLStatementUpdate)) {

            preparedStatement.setString(1, client.getClientLogin());
            preparedStatement.setString(2, client.getClientPass());
            preparedStatement.setString(3, client.getClientFullName());
            preparedStatement.setInt(4, client.getAdminFlagInt());
            preparedStatement.setInt(5, client.getClientID());

            preparedStatement.executeUpdate();
            logger.debug("Update client data successfully.");
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    @Override
    public void delete(int clientID) {
        try (Connection connection = connectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLStatementDelete)) {

            preparedStatement.setInt(1, clientID);

            preparedStatement.executeUpdate();
            logger.debug("Delete client data successfully.");
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    @Override
    public Client read(String clientLogin, String clientPass) {
        Client client = null;

        try (Connection connection = connectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLStatementReadWhenLogGiven)) {

            preparedStatement.setString(1, clientLogin);
            preparedStatement.setString(2, clientPass);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                client = parseResultSet(resultSet);
            }
            resultSet.close();
            logger.debug("Read client data successfully.");
        } catch (SQLException e) {
            logger.error(e);
        }
        return client;
    }

    @Override
    public List<Client> readAll() {
        ArrayList<Client> clientArrayList = new ArrayList<>();

        try (Connection connection = connectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLStatementReadAll)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                clientArrayList.add(parseResultSet(resultSet));
            }

            resultSet.close();
            logger.debug("Read client data list successfully.");
        } catch (SQLException e) {
            logger.error(e);
        }
        return clientArrayList;
    }

}
