package seng202.team5.database;

import javafx.application.Platform;
import org.apache.commons.lang3.time.StopWatch;
import seng202.team5.gui.ResultSetListener;
import seng202.team5.models.Search;
import seng202.team5.table.TableManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Class that will run a select query on a seperate thread. once initialised, the query is run with the start() function
 * called on this object and the results are retrived with the get() function. it is important to note that the get()
 * function will wait for the query to be completed and will block the main thread while waiting. Hence, calling get()
 * directly after start is inefficient,
 * @author Dominic Gorny
 */
public class AsynchronousSelectQuery extends Thread{

    private ArrayList<CrashFromDatabase> result = new ArrayList<>();
    private String sqlString = "";
    private DatabaseManager instance;
    private Search search;

    private ResultSetListener callingTableManager;


    /**
     * Constructor requires the sql string this query should run and an instance of database manager to retrieve the
     * database connection from. please note start() must still be called to start the query this object runs
     * @param sqlString the sql query to be run
     * @param managerInstance an instance of the database manager to get the database connection from
     * @param callingInstance the instance that requested this update. This instance will be informed when the thread
     *                        has finished
     */
    public AsynchronousSelectQuery(String sqlString, DatabaseManager managerInstance, ResultSetListener callingInstance, Search searchObject) {
        this.sqlString = sqlString;
        this.instance = managerInstance;
        this.callingTableManager = callingInstance;
        this.search = searchObject;
    }


    /**
     * runs the underlying query on a separate thread, stores the result in this objects result variable
     */
    public void run() {
        ArrayList<CrashFromDatabase> crashFromFiles = new ArrayList<>();
        try (Connection connection = instance.connect();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(this.sqlString)) {
            while (rs.next()) {
                crashFromFiles.add(
                        new CrashFromDatabase(
                                new CrashFromFile(
                                        rs.getInt("ID"),
                                        rs.getInt("advisorySpeed"),
                                        rs.getInt("bicycle"),
                                        rs.getInt("bridge"),
                                        rs.getInt("bus"),
                                        rs.getInt("stationWagon"),
                                        rs.getInt("cliff"),
                                        rs.getString("crashDirection"),
                                        rs.getString("financialYear"),
                                        rs.getString("crashLocationA"),
                                        rs.getString("crashLocationB"),
                                        rs.getString("sideRoad"),
                                        rs.getString("crashSeverity"),
                                        rs.getString("crashSHDescription"),
                                        rs.getInt("crashYear"),
                                        rs.getInt("debris"),
                                        rs.getString("directionRole"),
                                        rs.getInt("ditch"),
                                        rs.getInt("fatalCount"),
                                        rs.getInt("fence"),
                                        rs.getString("flatHill"),
                                        rs.getInt("guardRail"),
                                        rs.getString("holiday"),
                                        rs.getInt("building"),
                                        rs.getString("intersection"),
                                        rs.getInt("kerb"),
                                        rs.getString("light"),
                                        rs.getInt("minorInjury"),
                                        rs.getInt("moped"),
                                        rs.getInt("motorcycle"),
                                        rs.getInt("numberOfLanes"),
                                        rs.getInt("thrownObject"),
                                        rs.getInt("otherObject"),
                                        rs.getInt("otherVehicle"),
                                        rs.getInt("overBank"),
                                        rs.getInt("parkedVehicle"),
                                        rs.getInt("pedestrian"),
                                        rs.getInt("phoneBox"),
                                        rs.getInt("post"),
                                        rs.getString("region"),
                                        rs.getString("roadCharacter"),
                                        rs.getString("roadLane"),
                                        rs.getString("roadSurface"),
                                        rs.getInt("roadWorks"),
                                        rs.getInt("schoolBuses"),
                                        rs.getInt("seriousInjuries"),
                                        rs.getInt("slipFlood"),
                                        rs.getInt("speedLimit"),
                                        rs.getInt("strayAnimal"),
                                        rs.getString("streetLight"),
                                        rs.getInt("suv"),
                                        rs.getInt("taxi"),
                                        rs.getInt("tempSpeedLimit"),
                                        rs.getString("tlaName"),
                                        rs.getString("trafficControl"),
                                        rs.getInt("trafficIsland"),
                                        rs.getInt("trafficSign"),
                                        rs.getInt("train"),
                                        rs.getInt("tree"),
                                        rs.getInt("truck"),
                                        rs.getInt("unknownVehicle"),
                                        rs.getString("urban"),
                                        rs.getInt("vanUtility"),
                                        rs.getInt("vehicle"),
                                        rs.getInt("waterRiver"),
                                        rs.getString("weatherA"),
                                        rs.getString("weatherB"),
                                        rs.getDouble("lat"),
                                        rs.getDouble("lng"))
                        )
                );
            }
            result =  crashFromFiles;
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
            result = new ArrayList<>();
        }
        for (CrashFromDatabase crashFromDatabase: result)
        {
            crashFromDatabase.enhancedUserReadability();
        }
        callingTableManager.RowsUpdate(result, search);

    }


}



