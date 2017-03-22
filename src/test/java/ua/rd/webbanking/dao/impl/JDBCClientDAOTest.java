package ua.rd.webbanking.dao.impl;



import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.RunScript;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.rd.webbanking.dao.ClientDAO;
import ua.rd.webbanking.entities.Client;

import javax.sql.DataSource;
import java.io.File;
import java.sql.SQLException;

import static org.h2.engine.Constants.UTF8;
import static org.junit.Assert.*;

public class JDBCClientDAOTest {
    private static final String JDBC_DRIVER = org.h2.Driver.class.getName();
    private static final String JDBC_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private ClientDAO clientDAO ;
    private Client clientExpected;
    private IDataSet dataSet;

    @BeforeClass
    public static void createSchema() throws SQLException {
        RunScript.execute(JDBC_URL, USER, PASSWORD, "src/test/resources/db/clients.sql", UTF8, false);
    }

    @Before
    public void importDataSet() throws Exception {
        dataSet = readDataSet();
        cleanlyInsert(dataSet);

        clientExpected = new Client();
        clientExpected.setClientID(1);
        clientExpected.setClientLogin("testLog");
        clientExpected.setClientPass("testPass");
        clientExpected.setClientFullName("testFullName");
    }

    private IDataSet readDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(new File("src/test/resources/db/client-ds.xml"));
    }

    private void cleanlyInsert(IDataSet dataSet) throws Exception {
        IDatabaseTester databaseTester = new JdbcDatabaseTester(JDBC_DRIVER, JDBC_URL, USER, PASSWORD,"WEBBANKING");
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();
    }

    private DataSource dataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL(JDBC_URL);
        dataSource.setUser(USER);
        dataSource.setPassword(PASSWORD);
        return dataSource;
    }

    @Test
    public void read(){
        clientDAO = new JDBCClientDAO(dataSource());
        Client clientActual = clientDAO.read(1);
//        assertEquals(clientExpected,clientActual);
    }
}