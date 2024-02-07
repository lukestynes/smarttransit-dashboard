package seng202.team5.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.*;

/**
 * A singleton class responsible for interaction with the underlying SQLite database
 *
 * <p>(Functionality from this class was derived from the methods defined by Morgan English in the
 * advanced_fx_lab)
 *
 * @author Luke Stynes
 * @author Dominic Gorny
 * @author Morgan English
 */
public class DatabaseManager {
    private static DatabaseManager instance = null;
    private static final Logger log = LogManager.getLogger(DatabaseManager.class);
    private final String url;

    /**
     * Private constructor to make this class a singleton. If a database file can't be found in the
     * specified location a new one is created.
     *
     * @param urlIn the url location of the database
     */
    private DatabaseManager(String urlIn) {
        if (urlIn == null || urlIn.isEmpty()) {
            this.url = getDatabasePath();
        } else {
            this.url = urlIn;
        }

        if (!checkDatabaseExists(url)) {
            createDatabaseFile(url);
            resetDatabase();
        }
    }

    /**
     * Returns the current instance of the database, or generates a new instance if one doesn't
     * exist. Only allows one instance to exist at a time, as it's a singleton class.
     *
     * @return instance of DatabaseManager
     */
    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager("jdbc:sqlite:App Crash Data.std");
        }
        return instance;
    }

    /**
     * Gets the databases path relative to the jar file.
     *
     * @return jdbc encoded url location of database
     */
    private String getDatabasePath() {
        String path =
                DatabaseManager.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        path = URLDecoder.decode(path, StandardCharsets.UTF_8);
        File jarDir = new File(path);

        return "jdbc:sqlite:" + jarDir.getParentFile() + "/App Crash Data.std";
    }

    /**
     * Connect to the SQLite database
     *
     * @return the database connection
     */
    public Connection connect() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(this.url);
        } catch (SQLException e) {
            log.error(e);
        }
        return connection;
    }

    /**
     * Runs a sql file that initialises a database table containing all the required headers for the
     * CAS data set.
     */
    public void resetDatabase() {
        try {
            InputStream setupScript = getClass().getResourceAsStream("/sql/setup_database.sql");
            executeSQLScript(setupScript);
        } catch (NullPointerException e) {
            log.error("Error loading database setup script", e);
        }
    }

    /**
     * Check that a database exists in the expected location
     *
     * @param url expected location of database
     * @return true if database exists, else false
     */
    private boolean checkDatabaseExists(String url) {
        File file = new File(url.substring(12));
        return file.exists();
    }

    /**
     * Generates a sqlite database file at the specified url
     *
     * @param url the location to create the database
     */
    private void createDatabaseFile(String url) {
        try (Connection connection = DriverManager.getConnection(url)) {
            if (connection != null) {
                DatabaseMetaData databaseMetaData = connection.getMetaData();
                log.info(
                        String.format(
                                "A new database has been created. The driver name is %s",
                                databaseMetaData.getDriverName()));
            }
        } catch (SQLException e) {
            log.error(String.format("Error creating new database file url:%s", url));
            log.error(e);
        }
    }

    /**
     * Reads in and executes all statements within a sql file Note that each statement must be
     * separated by '--SPLIT'.
     *
     * @param sqlFile input stream of file containing sql statements for execution (seperated by
     *     --SPLIT)
     */
    private void executeSQLScript(InputStream sqlFile) {
        String line;
        StringBuilder stringBuilder = new StringBuilder();

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(sqlFile))) {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            String[] individualSQLStatements = stringBuilder.toString().split("--SPLIT");

            try (Connection conn = this.connect();
                    Statement statement = conn.createStatement()) {
                for (String singleStatement : individualSQLStatements) {
                    statement.executeUpdate(singleStatement);
                }
            }
        } catch (FileNotFoundException e) {
            log.error("Error could not find specified database initialisation file", e);
        } catch (IOException e) {
            log.error("Error working with database initialisation file", e);
        } catch (SQLException e) {
            log.error("Error executing sql statements in database initialisation file", e);
        }
    }
}
