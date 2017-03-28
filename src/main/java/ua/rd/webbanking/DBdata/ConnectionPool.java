package ua.rd.webbanking.DBdata;

import com.mchange.v2.c3p0.*;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;


/**
 * Created by Руслан on 25.12.2016.
 */
public class ConnectionPool implements ConnectionUtil {

    private static ConnectionPool Instance = new ConnectionPool();
    private ComboPooledDataSource cpds;
    private String URL;
    private String USERNAME;
    private String PASSWORD;
    private String DriverClass;

    private ConnectionPool() {
        cpds = new ComboPooledDataSource();
        loadDBConfigurations();

        try {
            cpds.setDriverClass(DriverClass); //loads the jdbc driver
            cpds.setJdbcUrl(URL);
            cpds.setUser(USERNAME);
            cpds.setPassword(PASSWORD);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    public void loadDBConfigurations() {
        Properties properties = new Properties();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("configDB.properties")) {

            properties.load(inputStream);

            URL = properties.getProperty("databaseURL");
            USERNAME = properties.getProperty("databaseLogin");
            PASSWORD = properties.getProperty("databasePass");
            DriverClass = properties.getProperty("driverClass");

        } catch (IOException ex) {
            ex.getStackTrace();
        }
    }

    public static ConnectionPool getInstance() {
        return Instance;
    }

    @Override
    public Connection getConnection() throws SQLException {
        Connection connection = this.cpds.getConnection();
        connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        return connection;
    }
}
