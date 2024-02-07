package seng202.team5.database;

import seng202.team5.gui.ErrorModalController;
import seng202.team5.gui.ResultNumberListener;
import seng202.team5.models.Search;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Class that will run a count query on a separate thread. once initialised, the query is run with the start() function
 * called on this object and the results are retrieved with the get() function. it is important to note that the get()
 * function will wait for the query to be completed and will block the main thread while waiting. Hence, calling get()
 * directly after start is inefficient,
 * @author Dominic Gorny
 */
public class AsynchronousCountQuery extends Thread{

    private int result = 0;
    private String sqlString = "";
    private DatabaseManager instance;

    private ArrayList<ResultNumberListener> callingClass;

    private Search search;


    /**
     * Constructor requires the sql string this query should run and an instance of database manager to retrieve the
     * database connection from. please note start() must still be called to start the query this object runs
     * @param sqlString the sql query to run
     * @param managerInstance an array list of classes in which a callback function should be called upon completion
     */
    public AsynchronousCountQuery(String sqlString, DatabaseManager managerInstance, ArrayList<ResultNumberListener> callingClass, Search search) {
        this.sqlString = sqlString;
        this.instance = managerInstance;
        this.callingClass = callingClass;
        this.search = search;
    }



    /**
     * runs the underlying query on a separate thread, stores the result in this objects result variable
     */
    public void run() {
        int count = 0;
        try (Connection connection = instance.connect();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(this.sqlString)) {
            count = rs.getInt("COUNT(*)");
            result =  count;
        } catch (SQLException sqlException) {
            ErrorModalController.showError("Error when counting returned objects: " + sqlException.getMessage());
            result =  0;
        }
        for (ResultNumberListener caller: callingClass) {
            caller.updateNumRow(result, search);
        }
    }
}



