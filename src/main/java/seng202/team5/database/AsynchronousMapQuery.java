package seng202.team5.database;

import seng202.team5.gui.ErrorModalController;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Class that will run a select query on a separate thread. once initialised, the query is run with the start() function
 * called on this object and the results are retrieved with the get() function. it is important to note that the get()
 * function will wait for the query to be completed and will block the main thread while waiting. Hence, calling get()
 * directly after start is inefficient,
 * unlike the query all function this query expects only a points id, lat and longitude to be returned.
 * @author Dominic Gorny
 */
public class AsynchronousMapQuery extends Thread{

    private ArrayList<MapPinDatapoint> result = new ArrayList<>();
    private String sqlString = "";
    private DatabaseManager instance;


    /**
     * Constructor requires the sql string this query should run and an instance of database manager to retrieve the
     * database connection from. please note start() must still be called to start the query this object runs
     * @param sqlString the sql query to run
     * @param managerInstance the instance of database manager which the callback should be addressed to
     */
    public AsynchronousMapQuery(String sqlString, DatabaseManager managerInstance) {
        this.sqlString = sqlString;
        this.instance = managerInstance;
    }


    /**
     * runs the underlying query on a separate thread, stores the result in this objects result variable
     */
    public void run() {
        ArrayList<MapPinDatapoint> crashDataPins = new ArrayList<>();
        try (Connection connection = instance.connect();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(this.sqlString)) {
            while (rs.next()) {
                crashDataPins.add(
                                new MapPinDatapoint(
                                        rs.getInt("ID"),
                                        rs.getDouble("lat"),
                                        rs.getDouble("lng"),
                                        rs.getString("crashSeverity"),
                                        rs.getInt("crashYear")
                                )
                );
            }
            result =  crashDataPins;
        } catch (SQLException sqlException) {
            ErrorModalController.showError("Error when collating map point information: " + sqlException.getMessage());
            result = new ArrayList<>();
        }
    }

    /**
     * gets the result of this query back to the main thread. please not this function is blocking and will wait
     * until a result is available if the thread has not yet finished.
     * @return the result of the select query
     */
    public ArrayList<MapPinDatapoint> getResult()
    {
        try {
            this.join();
        }
        catch (Exception err)
        {
            ErrorModalController.showError("Error when retrieving map point information: " + err.getMessage());
        }
        return result;
    }

}



