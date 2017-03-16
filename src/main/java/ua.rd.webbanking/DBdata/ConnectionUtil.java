package electronicPaymentSystem.DBdata;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Руслан on 26.12.2016.
 */
public interface ConnectionUtil {
    Connection getConnection() throws SQLException;

}
