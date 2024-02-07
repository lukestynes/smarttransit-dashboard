package seng202.team5.business;

import static seng202.team5.gui.LoadingBarController.showProgress;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import javafx.application.Platform;
import javafx.concurrent.Task;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import seng202.team5.database.*;
import seng202.team5.gui.DBViewController;
import seng202.team5.gui.ResultNumberListener;
import seng202.team5.gui.ResultSetListener;
import seng202.team5.models.GenericChart;
import seng202.team5.models.MapPin;
import seng202.team5.models.Search;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Class to handle all interactions related to crashes, it functions as a controller to take
 * requests from the application view and interact with the model and return results.
 *
 * @author Luke Stynes, Dominic Gorny
 */
public class CrashManager {
  private CrashDAO crashDAO;
  private CSVManager csvManager = new CSVManager();
  private static final Logger log = LogManager.getLogger(CrashManager.class);

  /**
   * Creates a CrashManager object with a private CrashDAO object that is used for all future
   * database interactions.
   */
  public CrashManager() {
    crashDAO = new CrashDAO();
  }

  /**
   * Creates a CrashManager object with a private CrashDAO object that is used for all future
   * database interactions. This overloaded constructor takes a preformed crashDAO for testing
   * purposes
   */
  public CrashManager(CrashDAO inputDAO) {
    crashDAO = inputDAO;
  }

  /**
   * Add a number of crashes stored within a CSV file of crash records. This function is run on
   * crash DAO in a thread separate to the main runtime thread
   *
   * @param csvFile the csv file of crash records.
   * @param tableName the name of the target table in the database.
   * @param tableString a string identifier for the table.
   * @param dbViewController the controller for the database view.
   */
  public void addCrashFromFile(
      File csvFile, String tableName, String tableString, DBViewController dbViewController) {
    Task<Void> newBatchTask =
        new Task<Void>() {
          @Override
          protected Void call() {
            log.info("Starting task 2");
            // loads records from csv
            updateMessage("reading CSV data");
            ArrayList<CrashFromFile> crashFromFiles = csvManager.readFile(csvFile);
            if (crashFromFiles.isEmpty()) {
              // provided CSV is empty; cannot continue
              Platform.runLater(dbViewController::createErrorDialog);
            } else {
              // adds records to database, pass in a lambda function (as consumer) to get the
              // progress
              // and save it to this task
              updateMessage("loading records");
              crashDAO.createStoreTable();
              crashDAO.dropTable("test");
              crashDAO.createTable("test");
              crashDAO.addBatch(
                  crashFromFiles,
                  "test",
                  tableString,
                  (num) -> updateProgress(num, 100),
                  this::updateMessage);
              crashDAO.renameTestTable(tableName);
              crashDAO.setStoredTable("lastOpen", tableName);
              updateMessage("finishing");
            }
            return null;
          }
        };
    try {
      showProgress(null, newBatchTask, true);
    } catch (IOException e) {
      e.printStackTrace();
    }
    newBatchTask.setOnSucceeded(
        e -> {
          Platform.runLater(dbViewController::loadTables);
        });
    // runs the above task asynchronously
    Thread newThread = new Thread(newBatchTask);
    newThread.start();
  }

  /**
   * Renames a table in the database.
   *
   * @param dbName The current name of the table to be renamed.
   * @param newName The new name to assign to the table.
   * @param dbViewController The view controller associated with the database, which will be updated
   *     after the table is renamed.
   */
  public void renameTable(String dbName, String newName, DBViewController dbViewController) {
    Task<Void> renameTask =
        new Task<Void>() {

          @Override
          protected Void call() throws Exception {
            crashDAO.renameTable(dbName, newName);
            return null;
          }
        };
    renameTask.setOnSucceeded(
        e -> {
          Platform.runLater(dbViewController::loadTables);
        });
    Thread newThread = new Thread(renameTask);
    newThread.start();
  }

  /**
   * Retrieves a single crash record from the specified table in the database by its ID.
   *
   * @param id The ID of the crash record to retrieve.
   * @param tableName The name of the table from which to retrieve the record.
   * @return A single CrashFromDatabase object representing the crash record.
   */
  public CrashFromDatabase getOne(int id, String tableName) {
    return crashDAO.getOne(id, tableName);
  }

  // REMOVE
  /**
   * Retrieves all crash records from the specified table in the database.
   *
   * @param tableName The name of the table from which to retrieve crash records.
   * @return A list of all CrashFromFile objects representing the crash records.
   */
  public ArrayList<CrashFromFile> getAll(String tableName) {
    return crashDAO.getAll(tableName);
  }

  /**
   * Allows custom queries to be executed on the database, with results provided to the specified
   * ResultSetListener.
   *
   * @param searchQuery A Search object specifying the custom query to be performed.
   * @param callingInstance The object that called this method (must be an instance of a table
   *     manager). This table manager instance will be informed to update when the data has been
   *     retrieved.
   */
  public void runTableQuery(Search searchQuery, ResultSetListener callingInstance) {

    SQLQuerryObject queryFromSearch = new SQLQuerryObject(searchQuery);
    queryFromSearch.buildSelectQuery();
    String sqlString = queryFromSearch.getSql();

    // grabs the results from database, then makes them more readable before returning
    crashDAO.queryAll(searchQuery, sqlString, callingInstance);
  }

  /**
   * Executes a SQL query to retrieve crash counts for categorical charting based on the given
   * search criteria and category.
   *
   * @param searchQuery The search criteria for filtering crash data.
   * @param category The category for which crash counts will be computed.
   * @return An ArrayList of CrashCount objects containing the query results for categorical
   *     charting.
   */
  public ArrayList<CrashCount> runCatChartQuery(
      Search searchQuery, GenericChart.Categories category) {

    SQLQuerryObject queryFromSearch = new SQLQuerryObject(searchQuery);
    queryFromSearch.buildCatChartQuery(category);
    String sqlString = queryFromSearch.getSql();

    // grabs the results from database, then makes them more readable before returning
    ArrayList<CrashCount> queryResults = crashDAO.catChartQueryAll(sqlString, category);

    return queryResults;
  }

  /**
   * Executes a SQL query to retrieve crash counts for continuous charting based on the given search
   * criteria and category.
   *
   * @param searchQuery The search criteria for filtering crash data.
   * @param category The continuous category for which crash counts will be computed.
   * @return An ArrayList of CrashCount objects containing the query results for continuous
   *     charting.
   */
  public ArrayList<CrashCount> runChartQuery(Search searchQuery, GenericChart.Continuous category) {

    SQLQuerryObject queryFromSearch = new SQLQuerryObject(searchQuery);
    queryFromSearch.buildChartQuery(category);
    String sqlString = queryFromSearch.getSql();

    // grabs the results from database, then makes them more readable before returning
    return crashDAO.chartQueryAll(sqlString, category);
  }

  /**
   * Executes a SQL query to retrieve crash counts for a double chart based on the given search
   * criteria and two categories.
   *
   * @param searchQuery The search criteria for filtering crash data.
   * @param xCategory The continuous x-axis category for the double chart.
   * @param yCategory The categorical y-axis category for the double chart.
   * @param value The specific value to filter within the yCategory.
   * @return An ArrayList of CrashCount objects containing the query results for the double chart.
   */
  public ArrayList<CrashCount> runDoubleChartQuery(
      Search searchQuery,
      GenericChart.Continuous xCategory,
      GenericChart.Categories yCategory,
      String value) {

    SQLQuerryObject queryFromSearch = new SQLQuerryObject(searchQuery);
    queryFromSearch.buildDoubleChartQuery(xCategory, yCategory, value);
    String sqlString = queryFromSearch.getSql();

    // grabs the results from database, then makes them more readable before returning
    ArrayList<CrashCount> queryResults = crashDAO.chartQueryAll(sqlString, xCategory);

    return queryResults;
  }

  /**
   * Executes a SQL query to retrieve crash counts for a line chart based on the given search
   * criteria and two categories.
   * @param searchQuery The search criteria for filtering crash data.
   * @param xCategory The continuous x-axis category for the chart.
   * @param yCategory The type that is being counted such as number of deaths or crashes.
   * @return An arraylist of crash counts with values for the x-axis with the corresponding y-axis value.
   */
  public ArrayList<CrashCount> runCountChartQuery(
          Search searchQuery,
          GenericChart.Continuous xCategory,
          GenericChart.Counts yCategory) {

    SQLQuerryObject queryFromSearch = new SQLQuerryObject(searchQuery);
    queryFromSearch.buildCountChartQuery(xCategory, yCategory);
    String sqlString = queryFromSearch.getSql();

    // grabs the results from database, then makes them more readable before returning
    ArrayList<CrashCount> queryResults = crashDAO.chartCountQueryAll(sqlString, xCategory, yCategory);

    return queryResults;
  }

  /**
   * Executes a SQL query to retrieve map pin datapoints based on the given search criteria.
   *
   * @param searchQuery The search criteria for filtering map pin data.
   * @return An ArrayList of MapPinDatapoint objects containing the query results.
   */
  public ArrayList<MapPinDatapoint> runMapQuery(Search searchQuery) {

    StopWatch watch = new StopWatch();
    watch.start();
    SQLQuerryObject queryFromSearch = new SQLQuerryObject(searchQuery);
    queryFromSearch.buildMapQuery();
    String sqlString = queryFromSearch.getSql();
    watch.stop();
    log.info("Preparing sql took " + watch.getTime(TimeUnit.MILLISECONDS));
    watch.reset();
    watch.start();
    // grabs the results from database, then makes them more readable before returning
    ArrayList<MapPinDatapoint> queryResults = crashDAO.queryMap(sqlString);
    watch.stop();
    log.info("Data retrieval took " + watch.getTime(TimeUnit.MILLISECONDS));
    watch.reset();
    return queryResults;
  }

  /**
   * Executes a SQL query to retrieve map pins within a specified geographical area.
   *
   * @param searchQuery The search criteria specifying the geographical area and other parameters.
   * @param allPolygons A list of polygons used to define the geographical area.
   * @param source The source map pin to filter by, or null if not filtering by source.
   * @param dest The destination map pin to filter by, or null if not filtering by destination.
   * @return An ArrayList of MapPinDatapoint objects containing the query results.
   */
  public ArrayList<MapPinDatapoint> runPinsInBoxQuery(
      Search searchQuery, ArrayList<ArrayList<MapPin>> allPolygons, MapPin source, MapPin dest) {
    StopWatch watch = new StopWatch();
    watch.start();
    SQLQuerryObject queryFromSearch = new SQLQuerryObject(searchQuery);
    queryFromSearch.buildPinsInBoxQuery(allPolygons, source, dest);
    String sqlString = queryFromSearch.getSql();
    watch.stop();
    log.info("Preparing sql took " + watch.getTime(TimeUnit.MILLISECONDS));
    watch.reset();
    watch.start();
    ArrayList<MapPinDatapoint> queryResults = crashDAO.queryMap(sqlString);
    watch.stop();
    log.info("Data retrieval took " + watch.getTime(TimeUnit.MILLISECONDS));
    watch.reset();
    return queryResults;
  }

  public void dropTable(String tableName) {
    crashDAO.dropTable(tableName);
  }

  /**
   * Runs a query on the database, the filters in which are specified by the search object. Returns
   * only the number of database hits as an Integer
   *
   * @param searchQuery the search object for the query to be run
   */
  public void numResults(Search searchQuery, ArrayList<ResultNumberListener> callingClass) {
    // return the number of results from the search query, without consideration for
    // the pagination values
    SQLQuerryObject queryFromSearch = new SQLQuerryObject(searchQuery);
    queryFromSearch.buildCountQuery();
    String sqlString = queryFromSearch.getSql();
    crashDAO.queryAllCount(sqlString, callingClass, searchQuery);
  }

  /**
   * Gets the number of rows in a given table. This is a single threaded version of the numResults
   * query made so as it is only run once (when a new table is loaded) and easier to use for this
   * use case.
   *
   * @param tableName Name of table to retrieve from
   * @return Integer count of rows
   */
  public Integer getNumResults(String tableName) {
    String sqlString = "SELECT COUNT(*) FROM " + tableName;
    Integer count = crashDAO.queryTableCount(sqlString);
    return count;
  }

  /**
   * Query the database to get a hashmap containing all table names, and all table string
   * identifiers. i.e. {Table1 : My Cool Table}
   *
   * @return HashMap of table names mapping to the user-visible string identifiers for all tables in
   *     the database
   */
  public HashMap<String, String> getTableNames() {
    HashMap<String, String> tableMap = new HashMap<>();
    String nameQuery = "SELECT name FROM sqlite_master WHERE type='table'";
    List<String> tableNames = crashDAO.queryTableNames(nameQuery);
    tableNames.remove("sqlite_sequence");
    tableNames.remove("crashes");
    tableNames.remove("test");
    tableNames.remove("testTable");
    tableNames.remove("store");
    if (!tableNames.isEmpty()) {
      for (String name : tableNames) {
        String query = "SELECT tableName FROM " + name + " LIMIT 1";
        List<String> tableString = crashDAO.queryTableNames(query);
        tableMap.put(name, tableString.get(0));
      }
    }
    return tableMap;
  }

  /**
   * Function to check the validity of the provided CSV file's header fields. Returns true if the
   * headers are correct in name and order, otherwise false.
   *
   * @param csvFile File to check validity of headers in
   * @return Boolean corresponding to whether or not the supplied file is a valid CAS CSV database.
   * @throws IOException If there is an I/O error while reading the CSV file.
   * @throws CsvException If there is an error parsing the CSV data.
   */
  public boolean checkHeaders(File csvFile) throws IOException, CsvException {
    CSVReader reader = new CSVReader(new FileReader(csvFile.getPath()));
    List<String[]> csvRows = reader.readAll();
    String[] expected = {
      "OBJECTID",
      "advisorySpeed",
      "bicycle",
      "bridge",
      "bus",
      "carStationWagon",
      "cliffBank",
      "crashDirectionDescription",
      "crashFinancialYear",
      "crashLocation1",
      "crashLocation2",
      "crashRoadSideRoad",
      "crashSeverity",
      "crashSHDescription",
      "crashYear",
      "debris",
      "directionRoleDescription",
      "ditch",
      "fatalCount",
      "fence",
      "flatHill",
      "guardRail",
      "holiday",
      "houseOrBuilding",
      "intersection",
      "kerb",
      "light",
      "minorInjuryCount",
      "moped",
      "motorcycle",
      "NumberOfLanes",
      "objectThrownOrDropped",
      "otherObject",
      "otherVehicleType",
      "overBank",
      "parkedVehicle",
      "pedestrian",
      "phoneBoxEtc",
      "postOrPole",
      "region",
      "roadCharacter",
      "roadLane",
      "roadSurface",
      "roadworks",
      "schoolBus",
      "seriousInjuryCount",
      "slipOrFlood",
      "speedLimit",
      "strayAnimal",
      "streetLight",
      "suv",
      "taxi",
      "temporarySpeedLimit",
      "tlaName",
      "trafficControl",
      "trafficIsland",
      "trafficSign",
      "train",
      "tree",
      "truck",
      "unknownVehicleType",
      "urban",
      "vanOrUtility",
      "vehicle",
      "waterRiver",
      "weatherA",
      "weatherB",
      "lat",
      "lng"
    };
    String[] headers = csvRows.get(0);
    return Arrays.equals(headers, expected);
  }

  public String getLastTable() {
    return crashDAO.getStoredTable("lastOpen");
  }

  public void setLastTable(String table) {
    crashDAO.setStoredTable("lastOpen", table);
  }

  public List<CrashFromDatabase> runQuery(String query) {
    List<CrashFromDatabase> results = crashDAO.runQuery(query);
    return results;
  }
}
