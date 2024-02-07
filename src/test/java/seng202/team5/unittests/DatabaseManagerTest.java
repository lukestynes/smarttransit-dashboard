package seng202.team5.unittests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import seng202.team5.database.DatabaseManager;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseManagerTest {

    @Test
    void testDatabaseManagerInstance() {
        DatabaseManager databaseManager = DatabaseManager.getInstance();
        Assertions.assertNotNull(databaseManager);
        Assertions.assertEquals(databaseManager, DatabaseManager.getInstance());
    }

    @Test
    void testDatabaseConnection() throws SQLException {
        DatabaseManager databaseManager = DatabaseManager.getInstance();
        Connection connection = databaseManager.connect();
        Assertions.assertNotNull(connection);
        Assertions.assertNotNull(connection.getMetaData());
        connection.close();
    }
}
