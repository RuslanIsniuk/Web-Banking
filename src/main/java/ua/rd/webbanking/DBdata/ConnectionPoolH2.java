package ua.rd.webbanking.DBdata;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPoolH2 implements ConnectionUtil {
    private DataSource dataSource;

    public ConnectionPoolH2(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
