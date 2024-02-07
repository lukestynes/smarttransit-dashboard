package seng202.team5.database;

import static java.sql.Types.INTEGER;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import seng202.team5.gui.ResultNumberListener;
import seng202.team5.gui.ResultSetListener;
import seng202.team5.models.GenericChart.*;
import seng202.team5.models.Search;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * This class provides methods to interact with crash data stored in a database. It allows you to
 * retrieve individual crash records and reset the database to its initial state.
 *
 * @author Morgan English
 * @author Rinlada Tolley
 * @author Luke Stynes
 */
public class CrashDAO {

  private static final Logger log = LogManager.getLogger(CrashDAO.class);
  private final DatabaseManager instance;

  /**
   * Initializes a new CrashDAO object and sets the instance to that of the current database
   * instance.
   */
  public CrashDAO() {
    instance = DatabaseManager.getInstance();
  }

  /**
   * Retrieves an individual crash record from the database by its ID.
   *
   * @param ID The unique identifier of the crash record.
   * @param tableName The name of the table from which to retrieve the crash record.
   * @return A CrashFromDatabase object representing the crash record, or null if not found.
   */
  public CrashFromDatabase getOne(int ID, String tableName) {
    CrashFromDatabase crashFromDatabase = null;
    String sql = "SELECT * FROM " + tableName + " WHERE id=?";
    try (Connection connection = instance.connect();
        PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setInt(1, ID);
      try (ResultSet rs = statement.executeQuery()) {
        while (rs.next()) {
          crashFromDatabase =
              new CrashFromDatabase(
                  rs.getInt("ID"),
                  rs.getString("advisorySpeed"),
                  rs.getString("bicycle"),
                  rs.getString("bridge"),
                  rs.getString("bus"),
                  rs.getString("stationWagon"),
                  rs.getString("cliff"),
                  rs.getString("crashDirection"),
                  rs.getString("financialYear"),
                  rs.getString("crashLocationA"),
                  rs.getString("crashLocationB"),
                  rs.getString("sideRoad"),
                  rs.getString("crashSeverity"),
                  rs.getString("crashSHDescription"),
                  rs.getString("crashYear"),
                  rs.getString("debris"),
                  rs.getString("directionRole"),
                  rs.getString("ditch"),
                  rs.getString("fatalCount"),
                  rs.getString("fence"),
                  rs.getString("flatHill"),
                  rs.getString("guardRail"),
                  rs.getString("holiday"),
                  rs.getString("building"),
                  rs.getString("intersection"),
                  rs.getString("kerb"),
                  rs.getString("light"),
                  rs.getString("minorInjury"),
                  rs.getString("moped"),
                  rs.getString("motorcycle"),
                  rs.getString("numberOfLanes"),
                  rs.getString("thrownObject"),
                  rs.getString("otherObject"),
                  rs.getString("otherVehicle"),
                  rs.getString("overBank"),
                  rs.getString("parkedVehicle"),
                  rs.getString("pedestrian"),
                  rs.getString("phoneBox"),
                  rs.getString("post"),
                  rs.getString("region"),
                  rs.getString("roadCharacter"),
                  rs.getString("roadLane"),
                  rs.getString("roadSurface"),
                  rs.getString("roadWorks"),
                  rs.getString("schoolBuses"),
                  rs.getString("seriousInjuries"),
                  rs.getString("slipFlood"),
                  rs.getString("speedLimit"),
                  rs.getString("strayAnimal"),
                  rs.getString("streetLight"),
                  rs.getString("suv"),
                  rs.getString("taxi"),
                  rs.getString("tempSpeedLimit"),
                  rs.getString("tlaName"),
                  rs.getString("trafficControl"),
                  rs.getString("trafficIsland"),
                  rs.getString("trafficSign"),
                  rs.getString("train"),
                  rs.getString("tree"),
                  rs.getString("truck"),
                  rs.getString("unknownVehicle"),
                  rs.getString("urban"),
                  rs.getString("vanUtility"),
                  rs.getString("vehicle"),
                  rs.getString("waterRiver"),
                  rs.getString("weatherA"),
                  rs.getString("weatherB"),
                  rs.getDouble("lat"),
                  rs.getDouble("lng"));
        }
        connection.close();
        return crashFromDatabase;
      }
    } catch (SQLException sqlException) {
      log.error(sqlException);
      return null;
    }
  }

  /**
   * Retrieves all crash records from the database.
   *
   * @param tableName The name of the table from which to retrieve crash records.
   * @return ArrayList of all crashes from the specified table.
   */
  public ArrayList<CrashFromFile> getAll(String tableName) {
    ArrayList<CrashFromFile> crashFromFiles = new ArrayList<>();
    String sql = "SELECT * FROM " + tableName;
    try (Connection connection = instance.connect();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql)) {
      while (rs.next()) {
        crashFromFiles.add(
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
                rs.getDouble("lng")));
      }
      connection.close();
      return crashFromFiles;
    } catch (SQLException sqlException) {
      log.error(sqlException);
      return new ArrayList<>();
    }
  }

  /**
   * Queries the database for all the crashes by a user parameter. The results are returned by
   * calling an appropriate function in the table manager instance. This request is run on a
   * separate thread to increase runtime efficiency.
   *
   * @param searchObject The Search object containing parameters for the query.
   * @param sql The SQL query command to be run against the database.
   * @param callingInstance The TableManager instance that will be updated when data is retrieved.
   * @return returns the thread the query is run on so that tests can wait for it to finish
   */
  public AsynchronousSelectQuery queryAll(
      Search searchObject, String sql, ResultSetListener callingInstance) {
    AsynchronousSelectQuery remoteQuery =
        new AsynchronousSelectQuery(sql, instance, callingInstance, searchObject);
    remoteQuery.start();
    return remoteQuery;
  }

  /**
   * Queries the database for all crashes by a user parameter and returns the results This request
   * is run on a separate thread to increase runtime efficiency.
   *
   * @param sql The SQL query command to be run against the database.
   * @return List of all filtered crashes, containing only their latitude, longitude, and ID fields.
   */
  public ArrayList<MapPinDatapoint> queryMap(String sql) {
    AsynchronousMapQuery remoteQuery = new AsynchronousMapQuery(sql, instance);
    remoteQuery.start();
    return remoteQuery.getResult();
  }

  /**
   * Queries the database for crash counts by discrete categories and returns the results. This
   * request is run on a separate thread to increase runtime efficiency.
   *
   * @param sql The SQL query command to be run against the database.
   * @param category The category for which crash counts are being queried.
   * @return ArrayList of CrashCount objects representing the crash counts.
   */
  public ArrayList<CrashCount> catChartQueryAll(String sql, Categories category) {
    AsynchronousChartQuery remoteQuery = new AsynchronousChartQuery(sql, instance, category);
    remoteQuery.start();
    return remoteQuery.getResult();
  }

  /**
   * Queries the database for crash counts by a continuous category and returns the results.
   *
   * @param sql The SQL query command to be run against the database.
   * @param category The continuous category for which crash counts are being queried.
   * @return ArrayList of CrashCount objects representing the crash counts.
   */
  public ArrayList<CrashCount> chartQueryAll(String sql, Continuous category) {
    ArrayList<CrashCount> crashFromFiles = new ArrayList<>();
    try (Connection connection = instance.connect();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql)) {
      while (rs.next()) {
        crashFromFiles.add(new CrashCount(rs.getInt("COUNT(*)"), rs.getString(category.dbName)));
      }
      connection.close();
      return crashFromFiles;
    } catch (SQLException sqlException) {
      log.error(sqlException);
      return new ArrayList<>();
    }
  }

  /**
   * Queries the database for crash counts by a category and returns the results.
   * @param sql The SQL query command to be run against the database.
   * @param xCategory The continuous category for which crash counts are being queried.
   * @param yCategory The continuous category for which crash counts are being queried.
   * @return
   */
  public ArrayList<CrashCount> chartCountQueryAll(String sql, Continuous xCategory, Counts yCategory) {
    ArrayList<CrashCount> crashFromFiles = new ArrayList<>();
    try (Connection connection = instance.connect();
         Statement statement = connection.createStatement();
         ResultSet rs = statement.executeQuery(sql)) {
      while (rs.next()) {
        crashFromFiles.add(new CrashCount(rs.getInt("SUM(" + yCategory.dbName + ")"), rs.getString(xCategory.dbName)));
      }
      connection.close();
      return crashFromFiles;
    } catch (SQLException sqlException) {
      log.error(sqlException);
      return new ArrayList<>();
    }
  }

  /**
   * Takes an SQL statement and returns the number of results that this statement has found in the
   * database this request is run on a separate thread to increase runtime efficiency.
   *
   * @param sql input sql statement from which the number of results are to be queried, not this
   *     must include the Count(*) tag within the statement.
   * @return the async query which is called (for debugging and testing purposes)
   */
  public AsynchronousCountQuery queryAllCount(
      String sql, ArrayList<ResultNumberListener> callingClass, Search searchObject) {
    AsynchronousCountQuery remoteQuery =
        new AsynchronousCountQuery(sql, instance, callingClass, searchObject);
    remoteQuery.start();
    return remoteQuery;
  }

  /**
   * Queries the database for the count of records returned by a SQL statement.
   *
   * @param sql The SQL query command to be run against the database.
   * @return The number of records returned by the SQL statement.
   */
  public Integer queryTableCount(String sql) {
    Integer count = 0;
    try (Connection connection = instance.connect();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql)) {
      count = rs.getInt("COUNT(*)");
    } catch (SQLException sqlException) {
      throw new RuntimeException(sqlException);
    }
    return count;
  }

  /**
   * Queries the database for table names using a SQL statement.
   *
   * @param sql The SQL query command to retrieve table names.
   * @return A List of table names retrieved from the database.
   */
  public List<String> queryTableNames(String sql) {
    List<String> tableNames = new ArrayList<>();
    try (Connection connection = instance.connect();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql)) {
      while (rs.next()) {
        String tableName = rs.getString(1);
        tableNames.add(tableName);
      }
    } catch (SQLException sqlException) {
      throw new RuntimeException(sqlException);
    }
    return tableNames;
  }

  /**
   * Deletes an individual data point in the database.
   *
   * @param ID The ID of the crash to be deleted from the crash database.
   * @param tableName The name of the table from which to delete the crash record.
   */
  public void delete(int ID, String tableName) {
    String sql = "DELETE FROM " + tableName + " WHERE id=?";
    try (Connection connection = instance.connect();
        PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setInt(1, ID);
      statement.executeUpdate();
    } catch (SQLException sqlException) {
      throw new RuntimeException(sqlException);
    }
  }

  /**
   * Adds an individual crash point to the database
   *
   * @param toAdd crash to be added to the database
   * @param tableName The name of the table from which to delete the crash record.
   * @param tableString A string representing the name of the table as part of the crash record.
   * @return The id if successful and -1 if unsuccessful
   */
  public int add(CrashFromFile toAdd, String tableName, String tableString) {
    String sql =
        "INSERT OR IGNORE INTO "
            + tableName
            + " (ID, advisorySpeed, bicycle, bridge, bus, stationWagon, cliff, crashDirection, financialYear, crashLocationA, crashLocationB, sideRoad, crashSeverity, crashSHDescription, crashYear, debris, directionRole, ditch, fatalCount, fence, flatHill, guardRail, holiday, building, intersection, kerb, light, minorInjury, moped, motorcycle, numberOfLanes, thrownObject, otherObject, otherVehicle, overBank, parkedVehicle, pedestrian, phoneBox, post, region, roadCharacter,  roadLane,  roadSurface,  roadWorks,  schoolBuses,  seriousInjuries,  slipFlood,  speedLimit, strayAnimal, streetLight,  suv,  taxi, tempSpeedLimit,  tlaName,  trafficControl,  trafficIsland,  trafficSign,  train,  tree,  truck,  unknownVehicle, urban, vanUtility, vehicle, waterRiver, weatherA, weatherB, lat, lng, tableName) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    try (Connection connection = instance.connect();
        PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setInt(1, toAdd.getObjectID());
      statement.setInt(2, toAdd.getAdvisorySpeed());
      statement.setInt(3, toAdd.getBicycle());
      statement.setInt(4, toAdd.getBridge());
      statement.setInt(5, toAdd.getBus());
      statement.setInt(6, toAdd.getCarStationWagon());
      statement.setInt(7, toAdd.getCliffBank());
      statement.setString(8, toAdd.getCrashDirectionDescription());
      statement.setString(9, toAdd.getCrashFinancialYear());
      statement.setString(10, toAdd.getCrashLocation1());
      statement.setString(11, toAdd.getCrashLocation2());
      statement.setString(12, toAdd.getCrashRoadSideRoad());
      statement.setString(13, toAdd.getCrashSeverity());
      statement.setString(14, toAdd.getCrashSHDescription());
      statement.setInt(15, toAdd.getCrashYear());
      statement.setInt(16, toAdd.getDebris());
      statement.setString(17, toAdd.getDirectionRoleDescription());
      statement.setInt(18, toAdd.getDitch());
      statement.setInt(19, toAdd.getFatalCount());
      statement.setInt(20, toAdd.getFence());
      statement.setString(21, toAdd.getFlatHill());
      statement.setInt(22, toAdd.getGuardRail());
      statement.setString(23, toAdd.getHoliday());
      statement.setInt(24, toAdd.getHouseOrBuilding());
      statement.setString(25, toAdd.getIntersection());
      statement.setInt(26, toAdd.getKerb());
      statement.setString(27, toAdd.getLight());
      statement.setInt(28, toAdd.getMinorInjuryCount());
      statement.setInt(29, toAdd.getMoped());
      statement.setInt(30, toAdd.getMotorcycle());
      statement.setInt(31, toAdd.getNumberOfLanes());
      statement.setInt(32, toAdd.getObjectThrownOrDropped());
      statement.setInt(33, toAdd.getOtherObject());
      statement.setInt(34, toAdd.getOtherVehicleType());
      statement.setInt(35, toAdd.getOverBank());
      statement.setInt(36, toAdd.getParkedVehicle());
      statement.setInt(37, toAdd.getPedestrian());
      statement.setInt(38, toAdd.getPhoneBoxEtc());
      statement.setInt(39, toAdd.getPostOrPole());
      statement.setString(40, toAdd.getRegion());
      statement.setString(41, toAdd.getRoadCharacter());
      statement.setString(42, toAdd.getRoadLane());
      statement.setString(43, toAdd.getRoadSurface());
      statement.setInt(44, toAdd.getRoadworks());
      statement.setInt(45, toAdd.getSchoolBus());
      statement.setInt(46, toAdd.getSeriousInjuryCount());
      statement.setInt(47, toAdd.getSlipOrFlood());
      statement.setInt(48, toAdd.getSpeedLimit());
      statement.setInt(49, toAdd.getStrayAnimal());
      statement.setString(50, toAdd.getStreetLight());
      statement.setInt(51, toAdd.getSuv());
      statement.setInt(52, toAdd.getTaxi());
      statement.setInt(53, toAdd.getTemporarySpeedLimit());
      statement.setString(54, toAdd.getTlaName());
      statement.setString(55, toAdd.getTrafficControl());
      statement.setInt(56, toAdd.getTrafficIsland());
      statement.setInt(57, toAdd.getTrafficSign());
      statement.setInt(58, toAdd.getTrain());
      statement.setInt(59, toAdd.getTree());
      statement.setInt(60, toAdd.getTruck());
      statement.setInt(61, toAdd.getUnknownVehicleType());
      statement.setString(62, toAdd.getUrban());
      statement.setInt(63, toAdd.getVanOrUtility());
      statement.setInt(64, toAdd.getVehicle());
      statement.setInt(65, toAdd.getWaterRiver());
      statement.setString(66, toAdd.getWeatherA());
      statement.setString(67, toAdd.getWeatherB());
      statement.setDouble(68, toAdd.getLat());
      statement.setDouble(69, toAdd.getLng());
      statement.setString(70, tableString);

      // Any null values from the csv can not be stored as null values in java ints and are
      // hence represented as -1. These if statements return any -1 value integers back to
      // null values as the data is loaded into the database to ensure accurate representation.
      // Author: Dominic Gorny

      if (toAdd.getObjectID() == -1) {
        statement.setNull(1, INTEGER);
      }
      if (toAdd.getAdvisorySpeed() == -1) {
        statement.setNull(2, INTEGER);
      }
      if (toAdd.getBicycle() == -1) {
        statement.setNull(3, INTEGER);
      }
      if (toAdd.getBridge() == -1) {
        statement.setNull(4, INTEGER);
      }

      if (toAdd.getBus() == -1) {
        statement.setNull(5, INTEGER);
      }
      if (toAdd.getCarStationWagon() == -1) {
        statement.setNull(6, INTEGER);
      }
      if (toAdd.getCliffBank() == -1) {
        statement.setNull(7, INTEGER);
      }

      if (toAdd.getBridge() == -1) {
        statement.setNull(15, INTEGER);
      }
      if (toAdd.getBridge() == -1) {
        statement.setNull(16, INTEGER);
      }
      if (toAdd.getBridge() == -1) {
        statement.setNull(18, INTEGER);
      }
      if (toAdd.getCrashYear() == -1) {
        statement.setNull(15, INTEGER);
      }
      if (toAdd.getDebris() == -1) {
        statement.setNull(16, INTEGER);
      }
      if (toAdd.getDitch() == -1) {
        statement.setNull(18, INTEGER);
      }
      if (toAdd.getFatalCount() == -1) {
        statement.setNull(19, INTEGER);
      }
      if (toAdd.getFence() == -1) {
        statement.setNull(20, INTEGER);
      }
      if (toAdd.getGuardRail() == -1) {
        statement.setNull(22, INTEGER);
      }
      if (toAdd.getHouseOrBuilding() == -1) {
        statement.setNull(24, INTEGER);
      }
      if (toAdd.getKerb() == -1) {
        statement.setNull(26, INTEGER);
      }
      if (toAdd.getMinorInjuryCount() == -1) {
        statement.setNull(28, INTEGER);
      }
      if (toAdd.getMoped() == -1) {
        statement.setNull(29, INTEGER);
      }
      if (toAdd.getMotorcycle() == -1) {
        statement.setNull(30, INTEGER);
      }
      if (toAdd.getNumberOfLanes() == -1) {
        statement.setNull(31, INTEGER);
      }
      if (toAdd.getObjectThrownOrDropped() == -1) {
        statement.setNull(32, INTEGER);
      }
      if (toAdd.getOtherObject() == -1) {
        statement.setNull(33, INTEGER);
      }
      if (toAdd.getOtherVehicleType() == -1) {
        statement.setNull(34, INTEGER);
      }
      if (toAdd.getOverBank() == -1) {
        statement.setNull(35, INTEGER);
      }
      if (toAdd.getParkedVehicle() == -1) {
        statement.setNull(36, INTEGER);
      }
      if (toAdd.getPedestrian() == -1) {
        statement.setNull(37, INTEGER);
      }
      if (toAdd.getPhoneBoxEtc() == -1) {
        statement.setNull(38, INTEGER);
      }
      if (toAdd.getPostOrPole() == -1) {
        statement.setNull(39, INTEGER);
      }
      if (toAdd.getRoadworks() == -1) {
        statement.setNull(44, INTEGER);
      }
      if (toAdd.getSchoolBus() == -1) {
        statement.setNull(45, INTEGER);
      }
      if (toAdd.getSeriousInjuryCount() == -1) {
        statement.setNull(46, INTEGER);
      }
      if (toAdd.getSlipOrFlood() == -1) {
        statement.setNull(47, INTEGER);
      }
      if (toAdd.getSpeedLimit() == -1) {
        statement.setNull(48, INTEGER);
      }
      if (toAdd.getStrayAnimal() == -1) {
        statement.setNull(49, INTEGER);
      }
      if (toAdd.getSuv() == -1) {
        statement.setNull(51, INTEGER);
      }
      if (toAdd.getTaxi() == -1) {
        statement.setNull(52, INTEGER);
      }
      if (toAdd.getTemporarySpeedLimit() == -1) {
        statement.setNull(53, INTEGER);
      }
      if (toAdd.getTrafficIsland() == -1) {
        statement.setNull(56, INTEGER);
      }
      if (toAdd.getTrafficSign() == -1) {
        statement.setNull(57, INTEGER);
      }
      if (toAdd.getTrain() == -1) {
        statement.setNull(58, INTEGER);
      }
      if (toAdd.getTree() == -1) {
        statement.setNull(59, INTEGER);
      }
      if (toAdd.getTruck() == -1) {
        statement.setNull(60, INTEGER);
      }
      if (toAdd.getUnknownVehicleType() == -1) {
        statement.setNull(61, INTEGER);
      }
      if (toAdd.getVanOrUtility() == -1) {
        statement.setNull(63, INTEGER);
      }
      if (toAdd.getVehicle() == -1) {
        statement.setNull(64, INTEGER);
      }
      if (toAdd.getWaterRiver() == -1) {
        statement.setNull(65, INTEGER);
      }
      statement.executeUpdate();
      ResultSet rs = statement.getGeneratedKeys();
      int insertID = -1;
      if (rs.next()) {
        insertID = rs.getInt(1);
      }
      connection.close();
      return insertID;
    } catch (SQLException sqlException) {
      log.error(sqlException);
      return -1;
    }
  }

  /**
   * Drops the specified table if it exists
   *
   * @param tableName The name of the table to be dropped.
   */
  public void dropTable(String tableName) {
    String sql = "DROP TABLE IF EXISTS " + tableName;
    try (Connection connection = instance.connect();
        PreparedStatement dropSQL = connection.prepareStatement(sql)) {
      dropSQL.executeUpdate();
    } catch (SQLException sqlException) {
      throw new RuntimeException(sqlException);
    }
  }

  /**
   * Creates a table if it does not exist already
   *
   * @param tableName The name of the table to be created.
   */
  public void createTable(String tableName) {
    String sql =
        "CREATE TABLE IF NOT EXISTS "
            + tableName
            + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, advisorySpeed INTEGER, bicycle INTEGER, bridge INTEGER, bus INTEGER, StationWagon INTEGER, cliff INTEGER, crashDirection TEXT, financialYear TEXT, crashLocationA TEXT, crashLocationB TEXT, sideRoad TEXT, crashSeverity TEXT, crashSHDescription TEXT, crashYear INTEGER, debris INTEGER, directionRole TEXT, ditch INTEGER, fatalCount INTEGER, fence INTEGER, flatHill TEXT, guardRail INTEGER, holiday TEXT, building INTEGER, intersection TEXT, kerb INTEGER, light TEXT, minorInjury INTEGER, moped INTEGER, motorcycle INTEGER, NumberOfLanes INTEGER, thrownObject INTEGER, otherObject INTEGER, otherVehicle INTEGER, overBank INTEGER, parkedVehicle INTEGER, pedestrian INTEGER, phoneBox INTEGER, post INTEGER, region TEXT, roadCharacter TEXT, roadLane TEXT, roadSurface TEXT, roadworks INTEGER, schoolBuses INTEGER, seriousInjuries INTEGER, slipFlood INTEGER, speedLimit INTEGER, strayAnimal INTEGER, streetLight TEXT, suv INTEGER, taxi INTEGER, tempSpeedLimit TEXT, tlaName TEXT, trafficControl TEXT, trafficIsland INTEGER, trafficSign INTEGER, train INTEGER, tree INTEGER, truck INTEGER, unknownVehicle INTEGER, urban TEXT, vanUtility INTEGER, vehicle INTEGER, waterRiver INTEGER, weatherA TEXT, weatherB TEXT, lat REAL, lng REAL, tableName STRING)";
    try (Connection connection = instance.connect();
        Statement createSQL = connection.createStatement()) {
      createSQL.executeUpdate(sql);
    } catch (SQLException sqlException) {
      throw new RuntimeException(sqlException);
    }
  }

  /**
   * Create a table (if not already present) for storing information pertinent to the database. Used
   * to store the last selected table.
   */
  public void createStoreTable() {
    String sql = "CREATE TABLE IF NOT EXISTS store (SETTING STRING PRIMARY KEY, VALUE STRING)";
    try (Connection connection = instance.connect();
        Statement createSQL = connection.createStatement()) {
      createSQL.executeUpdate(sql);
    } catch (SQLException sqlException) {
      throw new RuntimeException(sqlException);
    }
  }

  /**
   * Set the stored parameter in the store table for a given SETTING to the specified VALUE
   *
   * @param setting String representing the SETTING field to create/modify
   * @param value Value representing the VALUE field to store alongside the setting
   */
  public void setStoredTable(String setting, String value) {
    createStoreTable();
    String sql = "REPLACE INTO store (SETTING, VALUE) VALUES (?, ?)";
    try {
      Connection connection = instance.connect();
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setString(1, setting);
      statement.setString(2, value);
      statement.executeUpdate();
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Get the stored VALUE in the store table for a given SETTING if it exists. Returns the string of
   * the VALUE, or null if no setting/value exists.
   *
   * @param setting Setting to retrieve the value from
   * @return String of the corresponding value, or NULL
   */
  public String getStoredTable(String setting) {
    createStoreTable();
    String sql = "SELECT VALUE FROM store WHERE SETTING = ?";
    try {
      Connection connection = instance.connect();
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setString(1, setting);
      ResultSet results = statement.executeQuery();
      String result = null;
      if (results.next()) {
        result = results.getString("VALUE");
      }
      connection.close();
      return result;
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  public List<CrashFromDatabase> runQuery(String query) {
    try {
      Connection connection = instance.connect();
      PreparedStatement statement = connection.prepareStatement(query);
      ResultSet rs = statement.executeQuery();
      List<CrashFromDatabase> crashFromFiles = new ArrayList<>();
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
      return crashFromFiles;
    } catch (SQLException e) {
        e.printStackTrace();
        return null;
    }
  }

  /**
   * Renames the specified table with a new name
   *
   * @param dbName The current name of the table
   * @param newName The new name of the table
   */
  public void renameTable(String dbName, String newName) {
    String sql = "UPDATE " + dbName + " SET tableName = ?";
    try (PreparedStatement preparedStatement = instance.connect().prepareStatement(sql)) {
      preparedStatement.setString(1, newName);
      preparedStatement.executeUpdate();
    } catch (SQLException sqlException) {
      throw new RuntimeException(sqlException);
    }
  }

  /**
   * Renames the "test" table used temporarily for safety in the add operation to the desired DB
   * name.
   *
   * @param newDBName Name to assign the "test" table to (of format T###############).
   */
  public void renameTestTable(String newDBName) {
    String sql = "ALTER TABLE test RENAME TO " + newDBName;
    try (Connection connection = instance.connect();
        Statement createSQL = connection.createStatement()) {
      createSQL.executeUpdate(sql);
    } catch (SQLException sqlException) {
      throw new RuntimeException(sqlException);
    }
  }

  /**
   * Adds a batch of crash points to the database. This method is designed to be run on a separate
   * thread as a task.
   *
   * @param toAdd A list of crash points to be added to the database.
   * @param tableName The name of the table to which the crash records will be added.
   * @param tableString A string representing the name of the table as part of the crash records.
   * @param progressCallback A consumer for updating the progress value of the task.
   * @param messageCallback A consumer for updating task messages.
   */
  public void addBatch(
      List<CrashFromFile> toAdd,
      String tableName,
      String tableString,
      Consumer<Integer> progressCallback,
      Consumer<String> messageCallback) {
    // builds the foundation to a percentage counter that reports progress for every complete
    // percent
    int last_full_percent = -1;
    int current_records = 0;
    int total_records = toAdd.size();

    String sql =
        "INSERT OR IGNORE INTO "
            + tableName
            + " (ID, advisorySpeed, bicycle, bridge, bus, stationWagon, cliff, crashDirection, financialYear, crashLocationA, crashLocationB, sideRoad, crashSeverity, crashSHDescription, crashYear, debris, directionRole, ditch, fatalCount, fence, flatHill, guardRail, holiday, building, intersection, kerb, light, minorInjury, moped, motorcycle, numberOfLanes, thrownObject, otherObject, otherVehicle, overBank, parkedVehicle, pedestrian, phoneBox, post, region, roadCharacter,  roadLane,  roadSurface,  roadWorks,  schoolBuses,  seriousInjuries,  slipFlood,  speedLimit, strayAnimal, streetLight,  suv,  taxi, tempSpeedLimit,  tlaName,  trafficControl,  trafficIsland,  trafficSign,  train,  tree,  truck,  unknownVehicle, urban, vanUtility, vehicle, waterRiver, weatherA, weatherB, lat, lng, tableName) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    try (Connection connection = instance.connect();
        PreparedStatement statement = connection.prepareStatement(sql)) {
      connection.setAutoCommit(false);
      for (CrashFromFile crashFromFile : toAdd) {
        statement.setInt(1, crashFromFile.getObjectID());
        statement.setInt(2, crashFromFile.getAdvisorySpeed());
        statement.setInt(3, crashFromFile.getBicycle());
        statement.setInt(4, crashFromFile.getBridge());
        statement.setInt(5, crashFromFile.getBus());
        statement.setInt(6, crashFromFile.getCarStationWagon());
        statement.setInt(7, crashFromFile.getCliffBank());
        statement.setString(8, crashFromFile.getCrashDirectionDescription());
        statement.setString(9, crashFromFile.getCrashFinancialYear());
        statement.setString(10, crashFromFile.getCrashLocation1());
        statement.setString(11, crashFromFile.getCrashLocation2());
        statement.setString(12, crashFromFile.getCrashRoadSideRoad());
        statement.setString(13, crashFromFile.getCrashSeverity());
        statement.setString(14, crashFromFile.getCrashSHDescription());
        statement.setInt(15, crashFromFile.getCrashYear());
        statement.setInt(16, crashFromFile.getDebris());
        statement.setString(17, crashFromFile.getDirectionRoleDescription());
        statement.setInt(18, crashFromFile.getDitch());
        statement.setInt(19, crashFromFile.getFatalCount());
        statement.setInt(20, crashFromFile.getFence());
        statement.setString(21, crashFromFile.getFlatHill());
        statement.setInt(22, crashFromFile.getGuardRail());
        statement.setString(23, crashFromFile.getHoliday());
        statement.setInt(24, crashFromFile.getHouseOrBuilding());
        statement.setString(25, crashFromFile.getIntersection());
        statement.setInt(26, crashFromFile.getKerb());
        statement.setString(27, crashFromFile.getLight());
        statement.setInt(28, crashFromFile.getMinorInjuryCount());
        statement.setInt(29, crashFromFile.getMoped());
        statement.setInt(30, crashFromFile.getMotorcycle());
        statement.setInt(31, crashFromFile.getNumberOfLanes());
        statement.setInt(32, crashFromFile.getObjectThrownOrDropped());
        statement.setInt(33, crashFromFile.getOtherObject());
        statement.setInt(34, crashFromFile.getOtherVehicleType());
        statement.setInt(35, crashFromFile.getOverBank());
        statement.setInt(36, crashFromFile.getParkedVehicle());
        statement.setInt(37, crashFromFile.getPedestrian());
        statement.setInt(38, crashFromFile.getPhoneBoxEtc());
        statement.setInt(39, crashFromFile.getPostOrPole());
        statement.setString(40, crashFromFile.getRegion());
        statement.setString(41, crashFromFile.getRoadCharacter());
        statement.setString(42, crashFromFile.getRoadLane());
        statement.setString(43, crashFromFile.getRoadSurface());
        statement.setInt(44, crashFromFile.getRoadworks());
        statement.setInt(45, crashFromFile.getSchoolBus());
        statement.setInt(46, crashFromFile.getSeriousInjuryCount());
        statement.setInt(47, crashFromFile.getSlipOrFlood());
        statement.setInt(48, crashFromFile.getSpeedLimit());
        statement.setInt(49, crashFromFile.getStrayAnimal());
        statement.setString(50, crashFromFile.getStreetLight());
        statement.setInt(51, crashFromFile.getSuv());
        statement.setInt(52, crashFromFile.getTaxi());
        statement.setInt(53, crashFromFile.getTemporarySpeedLimit());
        statement.setString(54, crashFromFile.getTlaName());
        statement.setString(55, crashFromFile.getTrafficControl());
        statement.setInt(56, crashFromFile.getTrafficIsland());
        statement.setInt(57, crashFromFile.getTrafficSign());
        statement.setInt(58, crashFromFile.getTrain());
        statement.setInt(59, crashFromFile.getTree());
        statement.setInt(60, crashFromFile.getTruck());
        statement.setInt(61, crashFromFile.getUnknownVehicleType());
        statement.setString(62, crashFromFile.getUrban());
        statement.setInt(63, crashFromFile.getVanOrUtility());
        statement.setInt(64, crashFromFile.getVehicle());
        statement.setInt(65, crashFromFile.getWaterRiver());
        statement.setString(66, crashFromFile.getWeatherA());
        statement.setString(67, crashFromFile.getWeatherB());
        statement.setDouble(68, crashFromFile.getLat());
        statement.setDouble(69, crashFromFile.getLng());
        statement.setString(70, tableString);

        // Any null values from the csv can not be stored as null values in java ints and are
        // hence represented as -1. These if statements return any -1 value integers back to
        // null values as the data is loaded into the database to ensure accurate representation.
        // Author: Dominic Gorny
        if (crashFromFile.getObjectID() == -1) {
          statement.setNull(1, INTEGER);
        }
        if (crashFromFile.getAdvisorySpeed() == -1) {
          statement.setNull(2, INTEGER);
        }
        if (crashFromFile.getBicycle() == -1) {
          statement.setNull(3, INTEGER);
        }
        if (crashFromFile.getBridge() == -1) {
          statement.setNull(4, INTEGER);
        }

        if (crashFromFile.getBus() == -1) {
          statement.setNull(5, INTEGER);
        }
        if (crashFromFile.getCarStationWagon() == -1) {
          statement.setNull(6, INTEGER);
        }
        if (crashFromFile.getCliffBank() == -1) {
          statement.setNull(7, INTEGER);
        }
        if (crashFromFile.getCrashYear() == -1) {
          statement.setNull(15, INTEGER);
        }
        if (crashFromFile.getDebris() == -1) {
          statement.setNull(16, INTEGER);
        }
        if (crashFromFile.getDitch() == -1) {
          statement.setNull(18, INTEGER);
        }
        if (crashFromFile.getFatalCount() == -1) {
          statement.setNull(19, INTEGER);
        }
        if (crashFromFile.getFence() == -1) {
          statement.setNull(20, INTEGER);
        }
        if (crashFromFile.getGuardRail() == -1) {
          statement.setNull(22, INTEGER);
        }
        if (crashFromFile.getHouseOrBuilding() == -1) {
          statement.setNull(24, INTEGER);
        }
        if (crashFromFile.getKerb() == -1) {
          statement.setNull(26, INTEGER);
        }
        if (crashFromFile.getMinorInjuryCount() == -1) {
          statement.setNull(28, INTEGER);
        }
        if (crashFromFile.getMoped() == -1) {
          statement.setNull(29, INTEGER);
        }
        if (crashFromFile.getMotorcycle() == -1) {
          statement.setNull(30, INTEGER);
        }
        if (crashFromFile.getNumberOfLanes() == -1) {
          statement.setNull(31, INTEGER);
        }
        if (crashFromFile.getObjectThrownOrDropped() == -1) {
          statement.setNull(32, INTEGER);
        }
        if (crashFromFile.getOtherObject() == -1) {
          statement.setNull(33, INTEGER);
        }
        if (crashFromFile.getOtherVehicleType() == -1) {
          statement.setNull(34, INTEGER);
        }
        if (crashFromFile.getOverBank() == -1) {
          statement.setNull(35, INTEGER);
        }
        if (crashFromFile.getParkedVehicle() == -1) {
          statement.setNull(36, INTEGER);
        }
        if (crashFromFile.getPedestrian() == -1) {
          statement.setNull(37, INTEGER);
        }
        if (crashFromFile.getPhoneBoxEtc() == -1) {
          statement.setNull(38, INTEGER);
        }
        if (crashFromFile.getPostOrPole() == -1) {
          statement.setNull(39, INTEGER);
        }
        if (crashFromFile.getRoadworks() == -1) {
          statement.setNull(44, INTEGER);
        }
        if (crashFromFile.getSchoolBus() == -1) {
          statement.setNull(45, INTEGER);
        }
        if (crashFromFile.getSeriousInjuryCount() == -1) {
          statement.setNull(46, INTEGER);
        }
        if (crashFromFile.getSlipOrFlood() == -1) {
          statement.setNull(47, INTEGER);
        }
        if (crashFromFile.getSpeedLimit() == -1) {
          statement.setNull(48, INTEGER);
        }
        if (crashFromFile.getStrayAnimal() == -1) {
          statement.setNull(49, INTEGER);
        }
        if (crashFromFile.getSuv() == -1) {
          statement.setNull(51, INTEGER);
        }
        if (crashFromFile.getTaxi() == -1) {
          statement.setNull(52, INTEGER);
        }
        if (crashFromFile.getTemporarySpeedLimit() == -1) {
          statement.setNull(53, INTEGER);
        }
        if (crashFromFile.getTrafficIsland() == -1) {
          statement.setNull(56, INTEGER);
        }
        if (crashFromFile.getTrafficSign() == -1) {
          statement.setNull(57, INTEGER);
        }
        if (crashFromFile.getTrain() == -1) {
          statement.setNull(58, INTEGER);
        }
        if (crashFromFile.getTree() == -1) {
          statement.setNull(59, INTEGER);
        }
        if (crashFromFile.getTruck() == -1) {
          statement.setNull(60, INTEGER);
        }
        if (crashFromFile.getUnknownVehicleType() == -1) {
          statement.setNull(61, INTEGER);
        }
        if (crashFromFile.getVanOrUtility() == -1) {
          statement.setNull(63, INTEGER);
        }
        if (crashFromFile.getVehicle() == -1) {
          statement.setNull(64, INTEGER);
        }
        if (crashFromFile.getWaterRiver() == -1) {
          statement.setNull(65, INTEGER);
        }

        statement.addBatch();

        current_records += 1;
        // avoids division by 0 errors which occur as a result of bath numbers being too small
        if (total_records > 100) {
          if ((current_records / (total_records / 100)) > last_full_percent) {
            last_full_percent = (current_records / (total_records / 100));
            log.info(String.format("Loading at: %d percent", last_full_percent));
            progressCallback.accept((int) ((double) last_full_percent * 0.9));
          }
        }
      }
      messageCallback.accept("saving changes");
      statement.executeBatch();
      progressCallback.accept(96);
      connection.commit();
      messageCallback.accept("optimising...");
      progressCallback.accept(98);
      generateIndex(tableName);
      progressCallback.accept(100);
    } catch (SQLException sqlException) {
      throw new RuntimeException(sqlException);
    }
  }

  /**
   * Generates an index for the specified database table to improve query performance. First drops
   * an existing index (if it exists) and then creates a new index based on specific columns in the
   * table.
   *
   * @param tableName Name of the table to be indexed.
   */
  public void generateIndex(String tableName) {
    String sqlIndexClear = "DROP INDEX IF EXISTS idx_crashes;";
    String sql =
        "CREATE INDEX idx_crashes ON "
            + tableName
            + "(ID, crashSeverity, financialYear, region, speedLimit, lat, lng, crashLocationA, crashLocationB);";

    try (Connection conn = instance.connect()) {
      conn.setAutoCommit(false);
      log.info("Indexing database");
      PreparedStatement initStatement = conn.prepareStatement(sqlIndexClear);
      initStatement.executeUpdate();
      PreparedStatement statement = conn.prepareStatement(sql);
      statement.executeUpdate();
      conn.commit();
    } catch (SQLException sqlException) {
      throw new RuntimeException(sqlException);
    }
  }
}
