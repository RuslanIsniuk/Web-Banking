package electronicPaymentSystem.dao.impl;

import electronicPaymentSystem.dao.CreditCardDAO;
import electronicPaymentSystem.dao.PaymentDAO;
import electronicPaymentSystem.DBdata.ConnectionPool;
import electronicPaymentSystem.DBdata.ConnectionUtil;
import electronicPaymentSystem.entities.Payment;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Руслан on 28.12.2016.
 */
public class JDBCPaymentDAO implements PaymentDAO {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(JDBCPaymentDAO.class);

    private String SQLStatementRead;
    private String SQLStatementUpdate;
    private String SQLStatementDelete;
    private String SQLStatementCreate;
    private String SQLStatementReadAll;
    private CreditCardDAO creditCardDAO = new JDBCCreditCardDAO();

    public JDBCPaymentDAO() {
        Properties properties = new Properties();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("JDBCPaymentDAOConfig.properties")) {

            properties.load(inputStream);

            SQLStatementRead = properties.getProperty("StatementRead");
            SQLStatementUpdate = properties.getProperty("StatementUpdate");
            SQLStatementDelete = properties.getProperty("StatementDelete");
            SQLStatementCreate = properties.getProperty("StatementCreate");
            SQLStatementReadAll = properties.getProperty("StatementReadAll");

        } catch (IOException ex) {
            ex.getStackTrace();
        }
    }

    private ConnectionUtil connectionUtil = ConnectionPool.getInstance();

    Payment parseResultSet(ResultSet resultSet) throws SQLException {
        Payment payment = new Payment();

        payment.setPaymentID(resultSet.getInt("payments_id"));
        payment.setPaymentDestination(resultSet.getString("payments_type"));
        payment.setPaymentDate(resultSet.getDate("payments_date"));
        payment.setPaymentAmount(resultSet.getBigDecimal("payments_amount"));
        payment.setPaymentCreditCard(creditCardDAO.read(resultSet.getLong("card_id")));
        return payment;
    }

    @Override
    public Payment read(int paymentsID) {
        Payment payment = new Payment();

        try (Connection connection = connectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLStatementRead)) {

            preparedStatement.setInt(1, paymentsID);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                payment = parseResultSet(resultSet);
            }
            resultSet.close();
            logger.debug("Read payment data successfully.");
        } catch (SQLException e) {
            logger.error(e);
        }
        return payment;
    }

    @Override
    public List<Payment> readUsingCardID(long cardID) {
        ArrayList<Payment> paymentArrayList = new ArrayList<Payment>();

        try (Connection connection = connectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLStatementReadAll)) {

            preparedStatement.setLong(1, cardID);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                paymentArrayList.add(parseResultSet(resultSet));
            }
            resultSet.close();
            logger.debug("Read payments data list successfully.");
        } catch (SQLException e) {
            logger.error(e);
        }
        return paymentArrayList;
    }

    @Override
    public void create(Payment payment) {
        int paymentsID = 0;

        try (Connection connection = connectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLStatementCreate, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setLong(1, payment.getPaymentCrediCard().getCardID());
            preparedStatement.setString(2, payment.getPaymentDestination());
            preparedStatement.setDate(3, payment.getPaymentDate());
            preparedStatement.setBigDecimal(4, payment.getPaymentAmount());

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                paymentsID = resultSet.getInt(1);
            }
            resultSet.close();
            logger.debug("Create payment data list successfully.");
        } catch (SQLException e) {
            logger.error(e);
        }
        payment.setPaymentID(paymentsID);
    }

    public void update(Payment payment) {
        try (Connection connection = connectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLStatementUpdate)) {

            preparedStatement.setLong(1, payment.getPaymentCrediCard().getCardID());
            preparedStatement.setString(2, payment.getPaymentDestination());
            preparedStatement.setDate(3, payment.getPaymentDate());
            preparedStatement.setBigDecimal(4, payment.getPaymentAmount());
            preparedStatement.setInt(5, payment.getPaymentID());

            preparedStatement.executeUpdate();
            logger.debug("Update payment data list successfully.");
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    public void delete(int paymentsID) {
        try (Connection connection = connectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLStatementDelete)) {

            preparedStatement.setInt(1, paymentsID);

            preparedStatement.executeUpdate();
            logger.debug("Delete payments data list successfully.");
        } catch (SQLException e) {
            logger.error(e);
        }
    }
}
