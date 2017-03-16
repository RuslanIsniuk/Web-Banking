package ua.rd.webbanking.DBdata;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionUtil {
    Connection getConnection() throws SQLException;

}
