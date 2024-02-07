package seng202.team5.models;

import javafx.scene.control.TableColumn;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class contains various enums relevant to the displaying and formatting of the table view's
 * columns.
 *
 * @author Isla Smyth
 * @author Dominic Gorny
 */
public class TableColumns {

  /**
   * Used to map the TableColumn.SortType object in JavaFX to the database SortDirection type
   *
   * @param type TableColumn.SortType object representing an ascending or descending sort
   * @return SortDirection object representing an ascending or descending sort
   */
  public static SortDirection getDirFromType(TableColumn.SortType type) {
    if (type == TableColumn.SortType.ASCENDING) {
      return SortDirection.ASCENDING;
    } else {
      return SortDirection.DESCENDING;
    }
  }

  /**
   * Returns a list of all columns belonging to the specified ColumnGroup.
   *
   * @param group ColumnGroup object of the target group to return objects from
   * @return A list of all columns in the specified grouping
   */
  public static List<Column> getColumnsOfGroup(ColumnGroup group) {
    List<Column> columns = new ArrayList<>();
    if (group == ColumnGroup.GENERAL) {
      List<ColumnGroup> fullGeneral =
          new ArrayList<>() {
            {
              add(ColumnGroup.GENERAL);
            }
          };
      for (Column column : TableColumns.Column.values()) {
        if (fullGeneral.contains(column.group)) {
          columns.add(column);
        }
      }
    } else {
      for (Column column : TableColumns.Column.values()) {
        if (column.group == group) {
          columns.add(column);
        }
      }
    }
    return columns;
  }

  /**
   * ColumnGroup enumeration object for grouping columns in the table view. Objects include a name
   * (for displaying as a header in the tableView) and description (for displaying as a tooltip in
   * the tableView).
   */
  public enum ColumnGroup {
    ALL("All", "These fields are required for all column views"),
    GENERAL("General", "General information about the crash."),
    ADDITIONAL("Additional", "Additional information about the crash."),
    EXTRA("Extra", "Extraneous information about the crash."), // Stuff unlikely to be useful
    INJURIES("Injuries", "Quantities of injuries in the crash."),
    PARTICIPANTS("Participants", "Quantities of differing participants involved in the crash."),
    COLLISIONS("Collisions", "The number of times various objects were hit during the crash."),
    ;
    public final String name;
    public final String description;

    ColumnGroup(String name, String description) {
      this.name = name;
      this.description = description;
    }

    /**
     * Get the ColumnGroup enumeration object from its name.
     *
     * @param name The name of the ColumnGroup.
     * @return The ColumnGroup enumeration object if found, otherwise null.
     */
    public static ColumnGroup getGroupFromName(String name) {
      for (ColumnGroup group : values()) {
        if (group.name.equals(name)) {
          return group;
        }
      }
      return null;
    }

    /**
     * Get the name of the ColumnGroup.
     *
     * @return The name of the ColumnGroup.
     */
    public String getName() {
      return name;
    }
  }

  /**
   * Column enumeration object for defining each column's properties in the tableView. Consists of:
   * label -- the column's label to display in tableView csvLabel -- the name of the CSV/crash
   * object field from which to pull the data dbLabel -- the name of the field as stored in our SQL
   * database type -- the expected type of the data group -- the columnGroup to which the column
   * belongs width -- the preferred width of the column in pixels; this value must ensure that the
   * longest cell value/header string with sorting icon is fully displayed without "..." truncation.
   * tooltipDesc -- the description of the information, for displaying as a tooltip in tableView
   *
   * <p>Descriptions from
   * https://opendata-nzta.opendata.arcgis.com/pages/cas-data-field-descriptions
   */
  public enum Column {
    ID(
        "ID",
        "objectID",
        "ID",
        Integer.class,
        ColumnGroup.ALL,
        52.0,
        "The unique ID value for the crash record."),
    ADVSPEED(
        "Advised Speed",
        "advisorySpeed",
        "advisorySpeed",
        Integer.class,
        ColumnGroup.ADDITIONAL,
        130,
        "The advised speed at the time/location of the crash."),
    BICYCLE(
        "Bicycles",
        "bicycle",
        "bicycle",
        Integer.class,
        ColumnGroup.PARTICIPANTS,
        72,
        "Derived variable indicating how many bicycles were involved in the crash."),
    BRIDGE(
        "Bridge Hits",
        "bridge",
        "bridge",
        Integer.class,
        ColumnGroup.COLLISIONS,
        98,
        "Derived variable to indicate how many times a bridge, tunnel, the abutments, and/or handrails were struck in the crash."),
    BUS(
        "Buses",
        "bus",
        "bus",
        Integer.class,
        ColumnGroup.PARTICIPANTS,
        55,
        "Derived variable to indicate how many buses were involved in the crash. Excludes school buses, which are counted in the School Bus field separately."),
    STATIONWAGON(
        "Cars/Station Wagons",
        "carStationWagon",
        "StationWagon",
        Integer.class,
        ColumnGroup.PARTICIPANTS,
        180,
        "Derived variable to indicate how many cars or station wagons were involved in the crash."),
    CLIFFBANK(
        "Cliff/Bank Hits",
        "cliffBank",
        "cliff",
        Integer.class,
        ColumnGroup.COLLISIONS,
        120,
        "Derived variable to indicate how many times a cliff or bank was struck in the crash. This includes retaining walls."),
    DIRECTION(
        "Crash Direction",
        "crashDirectionDescription",
        "crashDirection",
        Integer.class,
        ColumnGroup.EXTRA,
        133,
        "The direction of the crash from the reference point."),
    FINANCIALYEAR(
        "Financial Year",
        "crashFinancialYear",
        "financialYear",
        String.class,
        ColumnGroup.EXTRA,
        124,
        "The financial year in which the crash occurred."),
    LOCATION1(
        "Location 1",
        "crashLocation1",
        "crashLocationA",
        String.class,
        ColumnGroup.GENERAL,
        180,
        "First part of the crash location. May be a road name, route position, landmark, or similar."),
    LOCATION2(
        "Location 2",
        "crashLocation2",
        "crashLocationB",
        String.class,
        ColumnGroup.GENERAL,
        180,
        "Second part of the crash location. May be a road name, route position, landmark, or similar."),
    ROADSIDE(
        "Roadside",
        "crashRoadSideRoad",
        "sideRoad",
        null,
        null,
        100.0,
        "N/A"), // This value is never assigned in the dataset.
    SEVERITY(
        "Severity",
        "crashSeverity",
        "crashSeverity",
        String.class,
        ColumnGroup.GENERAL,
        98,
        "The severity of the crash. This is determined by the worst injury sustained in the crash at the time of entry."),
    SHDESCRIPTION(
        "State Highway",
        "crashSHDescription",
        "crashSHDescription",
        String.class,
        ColumnGroup.EXTRA,
        135,
        "Indicates whether or not the crash occurred on a State Highway."),
    YEAR(
        "Year",
        "crashYear",
        "crashYear",
        Integer.class,
        ColumnGroup.GENERAL,
        50,
        "The year in which the crash occurred."),
    DEBRIS(
        "Debris Hits",
        "debris",
        "debris",
        Integer.class,
        ColumnGroup.COLLISIONS,
        105,
        "Derived variable to indicate how many times debris, boulders, or items dropped or thrown from (a) vehicle(s) were struck in the crash."),
    ROLEDIRECTION(
        "Vehicle Roll Direction",
        "directionRoleDescription",
        "directionRole",
        String.class,
        ColumnGroup.EXTRA,
        180,
        "The direction of the principal vehicle involved in the crash."),
    DITCH(
        "Ditch Hits",
        "ditch",
        "ditch",
        Integer.class,
        ColumnGroup.COLLISIONS,
        85,
        "Derived variable to indicate how many times a ditch or waterable drainage channel was struck in the crash."),
    FATALCOUNT(
        "Deaths",
        "fatalCount",
        "fatalCount",
        Integer.class,
        ColumnGroup.INJURIES,
        60,
        "The number of fatalities associated with this crash."),
    FENCE(
        "Fence Hits",
        "fence",
        "fence",
        Integer.class,
        ColumnGroup.COLLISIONS,
        95,
        "Derived variable to indicate how many times a fence was struck in the crash. This includes letterboxes, hoardings, private roadside furniture, hedges, sight rails, etc.."),
    FLATHILL(
        "Flat/Hill",
        "flatHill",
        "flatHill",
        String.class,
        ColumnGroup.EXTRA,
        70,
        "Whether the road is flat or sloped."),
    GUARDRAIL(
        "Guard Rail Hits",
        "guardRail",
        "guardRail",
        Integer.class,
        ColumnGroup.COLLISIONS,
        134,
        "Derived variable to indicate how many times a guard or guard rail was struck in the crash. This includes 'New Jersey' barriers, 'ARMCO', sand-filled barriers, wire-catch fences, etc.."),
    HOLIDAY(
        "Holiday",
        "holiday",
        "holiday",
        String.class,
        ColumnGroup.ADDITIONAL,
        135,
        "Indicates what holiday, if any, the crash occurred on."),
    HOUSEBLDG(
        "Building Hits",
        "houseOrBuilding",
        "building",
        Integer.class,
        ColumnGroup.COLLISIONS,
        120,
        "Derived variable to indicate how many times houses, garages, sheds, or other buildings were struck in the crash."),
    INTERSECTION(
        "Intersection",
        "intersection",
        "intersection",
        null,
        null,
        100.0,
        "N/A"), // This value is never assigned in the dataset.
    KERB(
        "Kerb Hits",
        "kerb",
        "kerb",
        Integer.class,
        ColumnGroup.COLLISIONS,
        100,
        "Derived variable to indicate how many times a kerb was struck in the crash, where it directly contributed to the crash."),
    LIGHT(
        "Light",
        "light",
        "light",
        String.class,
        ColumnGroup.ADDITIONAL,
        62,
        "The light at the time and place of the crash."),
    MINORINJURY(
        "Minor Injuries",
        "minorInjuryCount",
        "minorInjury",
        Integer.class,
        ColumnGroup.INJURIES,
        125,
        "The number of minor injuries associated with the crash."),
    MOPED(
        "Mopeds",
        "moped",
        "moped",
        Integer.class,
        ColumnGroup.PARTICIPANTS,
        68,
        "Derived variable to indicate how many mopeds were involved in the crash."),
    MOTORCYCLE(
        "Motorcycles",
        "motorcycle",
        "motorcycle",
        Integer.class,
        ColumnGroup.PARTICIPANTS,
        106,
        "Derived variable to indicate how many motorcycles were involved in the crash."),
    NUMLANES(
        "Lanes",
        "NumberOfLanes",
        "NumberOfLanes",
        Integer.class,
        ColumnGroup.EXTRA,
        56,
        "The number of lanes on the crash road"),
    OBJECT(
        "Objects Thrown/Dropped",
        "objectThrownOrDropped",
        "thrownObject",
        Integer.class,
        ColumnGroup.COLLISIONS,
        210,
        "Derived variable to indicate how many times objects were thrown at or dropped on vehicles in the crash."),
    OTHEROBJECT(
        "Other Objects Struck",
        "otherObject",
        "otherObject",
        Integer.class,
        ColumnGroup.COLLISIONS,
        175,
        "Derived variable to indicate how many times an object was struck in a crash and the object struck was not pre-defined. This variable includes stockpiled materials, rubbish bins, fallen poles, fallen trees, etc.."),
    OTHERVEHICLE(
        "Other Vehicles",
        "otherVehicleType",
        "otherVehicle",
        Integer.class,
        ColumnGroup.PARTICIPANTS,
        125,
        "Derived variable to indicate how many other vehicles (not include in any other category) were involved in the crash"),
    OVERBANK(
        "Embankment Hits",
        "overBank",
        "overBank",
        Integer.class,
        ColumnGroup.COLLISIONS,
        160,
        "Derived variable to indicate how many times an embankment was struck or driven over during a crash. This variable includes other vertical drops driven over during a crash."),
    PARKEDVEHICLE(
        "Parked Vehicle Hits",
        "parkedVehicle",
        "parkedVehicle",
        Integer.class,
        ColumnGroup.COLLISIONS,
        170,
        "Derived variable to indicate how many times a parked or unattended vehicle was struck in the crash. This includes trailers."),
    PEDESTRIAN(
        "Pedestrians",
        "pedestrian",
        "pedestrian",
        Integer.class,
        ColumnGroup.PARTICIPANTS,
        110,
        "Derived variable to indicate how many pedestrians were involved in the crash. This includes pedestrians on skateboards, scooters, and wheelchairs."),
    PHONEBOX(
        "Phone Box, etc. Hits",
        "phoneBoxEtc",
        "phoneBox",
        Integer.class,
        ColumnGroup.COLLISIONS,
        180,
        "Derived variable to indicate how many times telephone kiosks, traffic signal controllers, bus shelters, or other public furniture was struck in the crash."),
    POSTPOLE(
        "Post/Pole Hits",
        "postOrPole",
        "post",
        Integer.class,
        ColumnGroup.COLLISIONS,
        125,
        "Derived variable to indicate how many times a post/pole was struck in the crash. This includes light, power, phone, utility poles, and objects practically forming part of the pole (i.e. 'Transformer Guy' wires)."),
    REGION(
        "Region",
        "region",
        "region",
        String.class,
        ColumnGroup.GENERAL,
        168,
        "Identifies the local government region. The boundaries match territorial local authority boundaries."),
    ROADCHAR(
        "Road Character",
        "roadCharacter",
        "roadCharacter",
        String.class,
        ColumnGroup.EXTRA,
        130,
        "The general nature of the road."),
    ROADLANE(
        "Road Lanes",
        "roadLane",
        "roadLane",
        String.class,
        ColumnGroup.EXTRA,
        104,
        "The lane configuration of the road."),
    ROADSURFACE(
        "Road Surface",
        "roadSurface",
        "roadSurface",
        String.class,
        ColumnGroup.EXTRA,
        115,
        "The road markings at the crash site."),
    ROADWORKS(
        "Road Works Hits",
        "roadworks",
        "roadworks",
        Integer.class,
        ColumnGroup.COLLISIONS,
        145,
        "Derived variable to indicate how many times an object associated with 'roadworks' (including signs, cones, drums, barriers, but not roadwork vehicles) was struck during the crash."),
    SCHOOLBUS(
        "School Buses",
        "schoolBus",
        "schoolBuses",
        Integer.class,
        ColumnGroup.PARTICIPANTS,
        113,
        "Derived variable to indicate how many school buses were involved in the crash."),
    SERIOUSINJURY(
        "Serious Injuries",
        "seriousInjuryCount",
        "seriousInjuries",
        Integer.class,
        ColumnGroup.INJURIES,
        145,
        "A count of the number of serious injuries associated with the crash."),
    SLIPFLOOD(
        "Slip/Flood Hits",
        "slipOrFlood",
        "slipFlood",
        Integer.class,
        ColumnGroup.COLLISIONS,
        123,
        "Derived variable to indicate how many times landslips, washouts, or floods (excluding rivers) were objects struck in the crash."),
    SPEEDLIMIT(
        "Speed Limit",
        "speedLimit",
        "speedLimit",
        Integer.class,
        ColumnGroup.GENERAL,
        103,
        "The speed limit in force at the crash site at the time of the crash. LSZ refers to a Limited Speed Zone."),
    STRAYANIMAL(
        "Stray Animal Hits",
        "strayAnimal",
        "strayAnimal",
        Integer.class,
        ColumnGroup.COLLISIONS,
        150,
        "Derived variable to indicate how many times stray animals were struck in the crash. This variable includes wild animals, such as pigs, goats, deer, straying farm animals, house pets, and birds."),
    STREETLIGHT(
        "Street Lighting",
        "streetLight",
        "streetLight",
        String.class,
        ColumnGroup.ADDITIONAL,
        136,
        "The street lighting at the time of the crash."),
    SUV(
        "SUVs",
        "suv",
        "suv",
        Integer.class,
        ColumnGroup.PARTICIPANTS,
        49,
        "Derived variable to indicate how many SUVs were involved in the crash."),
    TAXI(
        "Taxis",
        "taxi",
        "taxi",
        Integer.class,
        ColumnGroup.PARTICIPANTS,
        50,
        "Derived variable to indicate how many taxis were involved in the crash."),
    TEMPSPEED(
        "Temporary Speed Limit",
        "temporarySpeedLimit",
        "tempSpeedLimit",
        Integer.class,
        ColumnGroup.ADDITIONAL,
        205,
        "The temporary speed limit at the crash site if one exists."),
    TLA(
        "Territorial Local Authority",
        "tlaName",
        "tlaName",
        String.class,
        ColumnGroup.ADDITIONAL,
        235,
        "The name of the Territorial Local Authority the crash has been attributed."),
    CONTROL(
        "Traffic Control",
        "trafficControl",
        "trafficControl",
        String.class,
        ColumnGroup.ADDITIONAL,
        130,
        "The traffic control signals at the crash site."),
    ISLAND(
        "Traffic Island Hits",
        "trafficIsland",
        "trafficIsland",
        Integer.class,
        ColumnGroup.COLLISIONS,
        158,
        "Derived variable to indicate how many times a traffic island, medians (excluding barriers), etc. was struck in the crash."),
    SIGN(
        "Traffic Sign Hits",
        "trafficSign",
        "trafficSign",
        Integer.class,
        ColumnGroup.COLLISIONS,
        136,
        "Derived variable to indicate how many times traffic signage (including traffic signals, their poles, bollards, or roadside delineators) was struck in the crash."),
    TRAIN(
        "Train Hits",
        "train",
        "train",
        Integer.class,
        ColumnGroup.COLLISIONS,
        86,
        "Derived variable to indicate how many times a train, rolling stock, or jiggers was struck in the crash, whether stationary or moving."),
    TREE(
        "Tree Hits",
        "tree",
        "tree",
        Integer.class,
        ColumnGroup.COLLISIONS,
        82,
        "Derived variable to indicate how many times trees or other growing items were struck during the crash."),
    TRUCK(
        "Trucks",
        "truck",
        "truck",
        Integer.class,
        ColumnGroup.PARTICIPANTS,
        56,
        "Derived variable to indicate how many trucks were involved in the crash."),
    UNKNOWNVEHICLE(
        "Unknown Vehicles",
        "unknownVehicleType",
        "unknownVehicle",
        Integer.class,
        ColumnGroup.PARTICIPANTS,
        160,
        "Derived variable to indicate how many vehicles were involved in the crash where the vehicle type was unknown."),
    URBAN(
        "Urban",
        "urban",
        "urban",
        String.class,
        ColumnGroup.EXTRA,
        57,
        "Derived from the Speed Limit variable. Values are Urban (if < 80) and Open Road (if LSZ or >= 80)."),
    VANUTILITY(
        "Vans/Utes",
        "vanOrUtility",
        "vanUtility",
        Integer.class,
        ColumnGroup.PARTICIPANTS,
        87,
        "Derived variable to indicate how many vans or utes were involved in the crash."),
    VEHICLE(
        "Vehicle Hits",
        "vehicle",
        "vehicle",
        Integer.class,
        ColumnGroup.COLLISIONS,
        115,
        "Derived variable to indicate how many times a stationary attended vehicle was struck in the crash. This includes broken down vehicles, workmen's vehicles, taxis, buses, etc.."),
    WATERRIVER(
        "Water Hits",
        "waterRiver",
        "waterRiver",
        Integer.class,
        ColumnGroup.COLLISIONS,
        100,
        "Derived variable to indicate how many times a body of water (including rivers, streams, lakes, the sea, tidal flats, canals, watercourses, or swamps) was struck in the crash."),
    WEATHER1(
        "Precipitation",
        "weatherA",
        "weatherA",
        String.class,
        ColumnGroup.GENERAL,
        130,
        "Indicates weather at the crash time/place."),
    WEATHER2(
        "Frost or Wind",
        "weatherB",
        "weatherB",
        String.class,
        ColumnGroup.GENERAL,
        130,
        "Indicates weather at the crash time/place."),
    LATITUDE(
        "Latitude",
        "lat",
        "lat",
        Double.class,
        ColumnGroup.GENERAL,
        111,
        "Indicates the latitude coordinate of the crash in degrees."),
    LONGITUDE(
        "Longitude",
        "lng",
        "lng",
        Double.class,
        ColumnGroup.GENERAL,
        111,
        "Indicates the longitude coordinate of the crash in degrees."),
    ;

    public final String label;
    public final String csvLabel;

    public final String dbLabel;
    public Class<?> type;
    public ColumnGroup group;
    public double width;
    public final String tooltipDesc;

    Column(
        String label,
        String csvLabel,
        String dbLabel,
        Class<?> type,
        ColumnGroup group,
        double width,
        String tooltipDesc) {
      this.label = label;
      this.csvLabel = csvLabel;
      this.dbLabel = dbLabel;
      this.type = type;
      this.group = group;
      this.width = width;
      this.tooltipDesc = tooltipDesc;
    }

    /**
     * Gets the data type of this column.
     *
     * @return The data type of the column.
     */
    public Class<?> getType() {
      return type;
    }

    /**
     * Retrieves a Column based on its label.
     *
     * @param label The label of the column to retrieve.
     * @return The Column corresponding to the given label, or null if not found.
     */
    public static Column getColumnFromLabel(String label) {
      for (Column column : values()) {
        if (column.label.equals(label)) {
          return column;
        }
      }
      return null;
    }

    /**
     * Retrieves the ColumnGroup to which this column belongs based on its label.
     *
     * @param label The label of the column.
     * @return The ColumnGroup of the column, or null if not found.
     */
    public static ColumnGroup getGroupFromLabel(String label) {
      for (Column column : values()) {
        if (column.label.equals(label)) {
          return column.group;
        }
      }
      return null;
    }

    /**
     * Retrieves the CSV label of this column based on its label.
     *
     * @param label The label of the column.
     * @return The CSV label of the column, or null if not found.
     */
    public static String getCSVLabelFromLabel(String label) {
      for (Column column : values()) {
        if (column.label.equals(label)) {
          return column.getCSVLabel();
        }
      }
      return null;
    }

    public static String getDBLabelFromLabel(String label) {
      for (Column column : values()) {
        if (column.label.equals(label)) {
          return column.getDbLabel();
        }
      }
      return null;
    }

    /**
     * Gets the ColumnGroup to which this column belongs.
     *
     * @return The ColumnGroup of the column.
     */
    public ColumnGroup getGroup() {
      return group;
    }

    /**
     * Gets the label of this column.
     *
     * @return The label of the column.
     */
    public String getLabel() {
      return label;
    }

    /**
     * Gets the CSV label of this column.
     *
     * @return The CSV label of the column.
     */
    public String getCSVLabel() {
      return csvLabel;
    }

    /**
     * Gets the database label of this column.
     *
     * @return The database label of the column.
     */
    public String getDbLabel() {
      return dbLabel;
    }

    /**
     * Gets the preferred width of this column in pixels.
     *
     * @return The preferred width of the column in pixels.
     */
    public double getWidth() {
      return width;
    }
  }

  /**
   * Enumeration of the severity values in the crash database name -- the displayed name in the
   * table csvName -- the value as it appears in the csv
   */
  public enum Severity {
    FATAL("Fatal Crash", "Fatal Crash"),
    MINOR("Minor Crash", "Minor Crash"),
    NONINJURY("Non-Injury Crash", "Non-Injury Crash"),
    SERIOUS("Serious Crash", "Serious Crash"),
    ;
    public final String name;
    public final String csvName;

    Severity(String name, String csvName) {
      this.name = name;
      this.csvName = csvName;
    }

    /**
     * Retrieves the CSV name corresponding to the given display name.
     *
     * @param name The display name.
     * @return The CSV name or null if not found.
     */
    public static String getCSVNameFromName(String name) {
      for (Severity severity : Severity.values()) {
        if (Objects.equals(severity.name, name)) {
          return severity.csvName;
        }
      }
      return null;
    }

    /**
     * Retrieves the display name corresponding to the given CSV name.
     *
     * @param csvName The CSV name.
     * @return The display name or null if not found.
     */
    public static String getNameFromCSVName(String csvName) {
      for (Severity severity : Severity.values()) {
        if (Objects.equals(severity.csvName, csvName)) {
          return severity.name;
        }
      }
      return null;
    }
  }

  /**
   * Enumeration of the region values in the crash database name -- the displayed name in the table
   * csvName -- the value as it appears in the csv
   */
  public enum Region {
    AUCKLAND("Auckland", "Auckland Region"),
    BAYOFPLENTY("Bay of Plenty", "Bay of Plenty Region"),
    CANTERBURY("Canterbury", "Canterbury Region"),
    GISBORNE("Gisborne", "Gisborne Region"),
    HAWKESBAY("Hawke's Bay", "Hawke''s Bay Region"),
    MANAWATU("Manawatū-Whanganui", "Manawatū-Whanganui Region"),
    MARLBOROUGH("Marlborough Region", "Marlborough Region"),
    NELSON("Nelson Region", "Nelson Region"),
    NORTHLAND("Northland Region", "Northland Region"),
    OTAGO("Otago Region", "Otago Region"),
    SOUTHLAND("Southland Region", "Southland Region"),
    TARANAKI("Taranaki Region", "Taranaki Region"),
    TASMAN("Tasman Region", "Tasman Region"),
    WAIKATO("Waikato Region", "Waikato Region"),
    WELLINGTON("Wellington Region", "Wellington Region"),
    WESTCOAST("West Coast Region", "West Coast Region"),
    ;
    public final String name;
    public final String csvName;

    Region(String name, String csvName) {
      this.name = name;
      this.csvName = csvName;
    }

    /**
     * Retrieves the CSV name corresponding to the given display name.
     *
     * @param name The display name.
     * @return The CSV name or null if not found.
     */
    public static String getCSVNameFromName(String name) {
      for (Region values : Region.values()) {
        if (Objects.equals(values.name, name)) {
          return values.csvName;
        }
      }
      return null;
    }

    /**
     * Retrieves the display name corresponding to the given CSV name.
     *
     * @param csvName The CSV name.
     * @return The display name or null if not found.
     */
    public static String getNameFromCSVName(String csvName) {
      for (Region values : Region.values()) {
        if (Objects.equals(values.csvName, csvName)) {
          return values.name;
        }
      }
      return null;
    }

    /**
     * Gets the display name of the region.
     *
     * @return The display name of the region.
     */
    public String getName() {
      return name;
    }
  }

  /**
   * Enumeration of the participants values in the crash database name -- the displayed name in the
   * table csvName -- the value as it appears in the csv dbName -- the name as it appears in our SQL
   * database
   */
  public enum Participants {
    CARSTATIONWAGON("Cars/Station Wagons", "carStationWagon", "StationWagon"),
    SUV("SUVs", "suv", "suv"),
    VANUTE("Vans/Utes", "vanOrUtility", "vanUtility"),
    BICYCLE("Bicycles", "bicycle", "bicycle"),
    MOTORCYCLE("Motorcycles", "motorcycle", "motorcycle"),
    MOPED("Mopeds", "moped", "moped"),
    PEDESTRIAN("Pedestrians", "pedestrian", "pedestrian"),
    BUS("Buses", "bus", "bus"),
    SCHOOLBUS("School Buses", "schoolBus", "schoolBuses"),
    TAXI("Taxis", "taxi", "taxi"),
    TRUCK("Trucks", "truck", "truck"),
    OTHER("Other Vehicles", "otherVehicleType", "otherVehicle"),
    UNKNOWN("Unknown Vehicles", "unknownVehicleType", "unknownVehicle"),
    ;
    public final String name;
    public final String csvName;
    public final String dbName;

    Participants(String name, String csvName, String dbName) {
      this.name = name;
      this.csvName = csvName;
      this.dbName = dbName;
    }

    public static String getNameFromDBName(String dbName) {
      for (Participants values : Participants.values()) {
        if (Objects.equals(values.dbName, dbName)) {
          return values.name;
        }
      }
      return null;
    }

    public String getName() {
      return name;
    }

    /**
     * gets the name field for a participants enum and returns the enum
     *
     * @param name the name in the name field of the enum
     * @throws IllegalArgumentException when passes a value not in the enum
     * @return A participants type enum
     */
    public static Participants getByName(String name) {
      for (Participants participants : Participants.values()) {
        if (Objects.equals(participants.name, name)) {
          return participants;
        }
      }
      throw new IllegalArgumentException("no such enum with name " + name + " in Collisions");
    }
  }

  /**
   * Enumeration of the weatherA values in the crash database name -- the displayed name in the
   * table csvName -- the value as it appears in the csv
   */
  public enum WeatherA {
    FINE("Fine", "Fine"),
    MISTFOG("Mist/Fog", "Mist or Fog"),

    LIGHTRAIN("Light rain", "Light rain"),
    HEAVYRAIN("Heavy rain", "Heavy rain"),
    HAILSLEET("Hail/Sleet", "Hail or Sleet"),
    SNOW("Snow", "Snow");
    public final String name;
    public final String csvName;

    WeatherA(String name, String csvName) {
      this.name = name;
      this.csvName = csvName;
    }

    public static String getCSVNameFromName(String name) {
      for (WeatherA values : WeatherA.values()) {
        if (Objects.equals(values.name, name)) {
          return values.csvName;
        }
      }
      return null;
    }

    public static String getNameFromCSVName(String csvName) {
      for (WeatherA values : WeatherA.values()) {
        if (Objects.equals(values.csvName, csvName)) {
          return values.name;
        }
      }
      return null;
    }
  }

  /**
   * Enumeration of the weatherB values in the crash database name -- the displayed name in the
   * table csvName -- the value as it appears in the csv
   */
  public enum WeatherB {
    FROST("Frost", "Frost"),
    STRONGWIND("Strong wind", "Strong wind"),
    NOFROSTWIND("No frost/wind", "None"),
    ;
    public final String name;
    public final String csvName;

    WeatherB(String name, String csvName) {
      this.name = name;
      this.csvName = csvName;
    }

    public static String getCSVNameFromName(String name) {
      for (WeatherB values : WeatherB.values()) {
        if (Objects.equals(values.name, name)) {
          return values.csvName;
        }
      }
      return null;
    }

    public static String getNameFromCSVName(String csvName) {
      for (WeatherB values : WeatherB.values()) {
        if (Objects.equals(values.csvName, csvName)) {
          return values.name;
        }
      }
      return null;
    }
  }

  /**
   * Enumeration of the light values in the crash database name -- the displayed name in the table
   * csvName -- the value as it appears in the csv
   */
  public enum Light {
    SUN("Bright sun", "Bright sun"),
    DARK("Dark", "Dark"),
    OVERCAST("Overcast", "Overcast"),
    TWILIGHT("Twilight", "Twilight"),
    ;
    public final String name;
    public final String csvName;

    Light(String name, String csvName) {
      this.name = name;
      this.csvName = csvName;
    }
  }

  /**
   * Enumeration of the holiday values in the crash database name -- the displayed name in the table
   * csvName -- the value as it appears in the csv
   */
  public enum Holiday {
    CHRISTMAS("Christmas/New Year", "Christmas New Year"),
    EASTER("Easter", "Easter"),
    LABOUR("Labour Weekend", "Labour Weekend"),
    QUEEN("Queen's Birthday", "Queens Birthday"),
    ;
    public final String name;
    public final String csvName;

    Holiday(String name, String csvName) {
      this.name = name;
      this.csvName = csvName;
    }

    public static String getCSVNameFromName(String name) {
      for (Holiday values : Holiday.values()) {
        if (Objects.equals(values.name, name)) {
          return values.csvName;
        }
      }
      return null;
    }

    public static String getNameFromCSVName(String csvName) {
      for (Holiday values : Holiday.values()) {
        if (Objects.equals(values.csvName, csvName)) {
          return values.name;
        }
      }
      return null;
    }
  }

  /**
   * Enumeration of the local authority values in the crash database name -- the displayed name in
   * the table csvName -- the value as it appears in the csv
   */
  public enum LocalAuthority {
    ASHBURTON("Ashburton", "Ashburton District"),
    AUCKLAND("Auckland", "Auckland"),
    BULLER("Buller", "Buller District"),
    CARTERTON("Carterton", "Carterton District"),
    HAWKESBAY("Central Hawke's Bay", "Central Hawke''s Bay District"),
    OTAGO("Central Otago", "Central Otago District"),
    CHATHAM("Chatham Islands", "Chatham Islands Territory"),
    CHRISTCHURCH("Christchurch", "Christchurch City"),
    CLUTHA("Clutha", "Clutha District"),
    DUNEDIN("Dunedin", "Dunedin City"),
    FARNORTH("Far North", "Far North District"),
    GISBORNE("Gisborne", "Gisborne District"),
    GORE("Gore", "Gore District"),
    GREY("Grey", "Grey District"),
    HAMILTON("Hamilton", "Hamilton District"),
    HASTINGS("Hastings", "Hastings District"),
    HAURAKI("Hauraki", "Hauraki District"),
    HOROWHENUA("Horowhenua", "Horowhenua District"),
    HURUNUI("Hurunui", "Hurunui District"),
    INVERCARGILL("Invercargill", "Invercargill City"),
    KAIKOURA("Kaikoura", "Kairkoura District"),
    KAIPARA("Kaipara", "Kaipara District"),
    KAPITICOAST("Kapiti Coast", "Kapiti Coast District"),
    KAWERAU("Kawerau", "Kawerau District"),
    LOWERHUTT("Lower Hutt", "Lower Hutt City"),
    MACKENZIE("Mackenzie", "Mackenzie District"),
    MANAWATU("Manawatu", "Manawatu District"),
    MARLBOROUGH("Marlborough", "Marlborough District"),
    MASTERTON("Masterton", "Masterton District"),
    MATAMATA("Matamata-Piako", "Matamata-Piako District"),
    NAPIER("Napier", "Napier City"),
    NELSON("Nelson", "Nelson City"),
    NEWPLYMOUTH("New Plymouth", "New Plymouth District"),
    OPOTIKI("Opotiki", "Opotiki District"),
    OTOROHANGA("Otorohanga", "Otorohanga District"),
    PALMERSTONNORTH("Palmerston North", "Palmerston North City"),
    PORIRUA("Porirua", "Porirua City"),
    QUEENSTOWN("Queenstown-Lakes", "Queenstown-Lakes District"),
    RANGITIKEI("Rangitikei", "Rangitikei District"),
    ROTORUA("Rotorua", "Rotorua District"),
    RUAPEHU("Ruapehu", "Ruapehu District"),
    SELWYN("Selwyn", "Selwyn District"),
    SOUTHTARANAKI("South Taranaki", "South Taranaki District"),
    SOUTHWAIKATO("South Waikato", "South Waikato District"),
    SOUTHLAND("Southland", "Southland District"),
    STRATFORD("Stratford", "Stratford District"),
    TARARUA("Tararua", "Tararua District"),
    TASMAN("Tasman", "Tasman District"),
    TAUPO("Taupo", "Taupo District"),
    TAURANGA("Tauranga", "Tauranga City"),
    THAMES("Thames-Coromandel", "Thames-Coromandel District"),
    TIMARU("Timaru", "Timaru District"),
    UPPERHUTT("Upper Hutt", "Upper Hutt City"),
    WAIKATO("Waikato", "Waikato District"),
    WAIMAKARIRI("Waimakariri", "Waimakariri District"),
    WAIMATE("Waimate", "Waimate District"),
    WAIPA("Waipa", "Waipa District"),
    WAIROA("Wairoa", "Wairoa District"),
    WAITAKI("Waitaki", "Waitaki District"),
    WAITOMO("Waitomo", "Waitomo District"),
    WELLINGTON("Wellington", "Wellington City"),
    BAYOFPLENTY("Western Bay of Plenty", "Western Bay of Plenty District"),
    WESTLAND("Westland", "Westland District"),
    WHAKATANE("Whakatane", "Whakatane District"),
    WHANGANUI("Whanganui", "Whanganui District"),
    WHANGAREI("Whangarei", "Whangarei District"),
    ;
    public final String name;
    public final String csvName;

    LocalAuthority(String name, String csvName) {
      this.name = name;
      this.csvName = csvName;
    }

    public static String getCSVNameFromName(String name) {
      for (LocalAuthority values : LocalAuthority.values()) {
        if (Objects.equals(values.name, name)) {
          return values.csvName;
        }
      }
      return null;
    }

    public static String getNameFromCSVName(String csvName) {
      for (LocalAuthority values : LocalAuthority.values()) {
        if (Objects.equals(values.csvName, csvName)) {
          return values.name;
        }
      }
      return null;
    }
  }

  /**
   * Enumeration of the traffic control values in the crash database name -- the displayed name in
   * the table csvName -- the value as it appears in the csv
   */
  public enum TrafficControl {
    NONE("None", "Nil"),
    GIVEWAY("Give way", "Give way"),
    STOP("Stop sign", "Stop"),
    TRAFFICSIGNALS("Traffic signals", "Traffic Signals"),
    POINTSMAN("Pointsman", "Pointsman"),
    PEDESTRIAN("Pedestrian crossing", "Isolated Pedestrian signal (non-intersection)"),
    SCHOOLPATROL("School patrol", "School Patrol/warden"),
    UNKNOWN("Unknown", "Unknown"),
    ;
    public final String name;
    public final String csvName;

    TrafficControl(String name, String csvName) {
      this.name = name;
      this.csvName = csvName;
    }

    public static String getCSVNameFromName(String name) {
      for (TrafficControl values : TrafficControl.values()) {
        if (Objects.equals(values.name, name)) {
          return values.csvName;
        }
      }
      return null;
    }

    public static String getNameFromCSVName(String csvName) {
      for (TrafficControl values : TrafficControl.values()) {
        if (Objects.equals(values.csvName, csvName)) {
          return values.name;
        }
      }
      return null;
    }
  }

  /**
   * Enumeration of the collisions values in the crash database name -- the displayed name in the
   * table csvName -- the value as it appears in the csv dbName -- the value as it appears in the
   * database
   */
  public enum Collisions {
    BRIDGE("Bridges", "bridge", "bridge"),
    CLIFFBANK("Cliffs/Banks", "cliffBank", "cliff"),
    DEBRIS("Debris", "debris", "debris"),
    DITCH("Ditches", "ditch", "ditch"),
    FENCE("Fences", "fence", "fence"),
    GUARDRAIL("Guardrails", "guardRail", "guardRail"),
    BUILDING("Buildings", "houseOrBuilding", "building"),
    KERB("Kerbs", "kerb", "kerb"),
    OBJTHROWN("Thrown/Dropped Objects", "objectThrownOrDropped", "thrownObject"),
    OBJOTHER("Other Objects", "otherObject", "otherObject"),
    EMBANKMENT("Embankments", "overBank", "overBank"),
    PARKEDVEHICLE("Parked Vehicles", "parkedVehicle", "parkedVehicle"),
    PHONEBOX("Phone Boxes & Similar", "phoneBoxEtc", "phoneBox"),
    POSTPOLE("Posts/Poles", "postOrPole", "post"),
    ROADWORKS("Roadworks", "roadworks", "roadworks"),
    SLIPFLOOD("Slips/Floods", "slipOrFlood", "slipFlood"),
    STRAYANIMAL("Stray Animals", "strayAnimal", "strayAnimal"),
    TRAFFICISLAND("Traffic Islands", "trafficIsland", "trafficIsland"),
    TRAFFICSIGN("Traffic Signage", "trafficSign", "trafficSign"),
    TRAIN("Trains", "train", "train"),
    TREE("Trees", "tree", "tree"),
    VEHICLE("Vehicles", "vehicle", "vehicle"),
    WATER("Water/Rivers", "waterRiver", "waterRiver"),
    ;
    public final String name;
    public final String csvName;
    public final String dbName;

    Collisions(String name, String csvName, String dbName) {
      this.name = name;
      this.csvName = csvName;
      this.dbName = dbName;
    }

    public String getName() {
      return name;
    }
    /**
     * Gets the display name from the database name for the Collisions enumeration.
     *
     * @param dbName The database name to find the corresponding display name for.
     * @return The display name corresponding to the given database name, or null if not found.
     */
    public static String getNameFromDBName(String dbName) {
      for (Collisions values : Collisions.values()) {
        if (Objects.equals(values.dbName, dbName)) {
          return values.name;
        }
      }
      return null;
    }

    /**
     * gets the name field for a collision enum and returns the enum
     *
     * @param name the name in the name field of the enum
     * @throws IllegalArgumentException when passes a value not in the enum
     * @return A collision type enum
     */
    public static Collisions getByName(String name) {
      for (Collisions coll : Collisions.values()) {
        if (Objects.equals(coll.name, name)) {
          return coll;
        }
      }
      throw new IllegalArgumentException("No such enum with name " + name + " in Collisions");
    }
  }

  /**
   * Enumeration of the direction values in the crash database name -- the displayed name in the
   * table csvName -- the value as it appears in the csv
   */
  public enum Direction {
    EAST("East", "East"),
    NORTH("North", "North"),
    South("South", "South"),
    WEST("West", "West"),
    ;
    public final String name;
    public final String csvName;

    Direction(String name, String csvName) {
      this.name = name;
      this.csvName = csvName;
    }
  }

  /**
   * Enumeration of the lane configuration values in the crash database name -- the displayed name
   * in the table csvName -- the value as it appears in the csv
   */
  public enum LaneConfiguration {
    ONEWAY("One-way", "1-way"),
    TWOWAY("Two-way", "2-way"),
    OFFROAD("Off-road", "Off road"),
    ;
    public final String name;
    public final String csvName;

    LaneConfiguration(String name, String csvName) {
      this.name = name;
      this.csvName = csvName;
    }

    /**
     * Gets the CSV name from the display name of the lane configuration.
     *
     * @param name The display name.
     * @return The corresponding CSV name.
     */
    public static String getCSVNameFromName(String name) {
      for (LaneConfiguration values : LaneConfiguration.values()) {
        if (Objects.equals(values.name, name)) {
          return values.csvName;
        }
      }
      return null;
    }

    /**
     * Gets the display name from the CSV name of the lane configuration.
     *
     * @param csvName The CSV name.
     * @return The corresponding display name.
     */
    public static String getNameFromCSVName(String csvName) {
      for (LaneConfiguration values : LaneConfiguration.values()) {
        if (Objects.equals(values.csvName, csvName)) {
          return values.name;
        }
      }
      return null;
    }
  }

  /**
   * Enumeration of the road character values in the crash database name -- the displayed name in
   * the table csvName -- the value as it appears in the csv
   */
  public enum RoadCharacter {
    BRIDGE("Bridge", "Bridge"),
    MOTORWAY("Motorway ramp", "Motorway ramp"),
    OVERPASS("Overpass", "Overpass"),
    RAILXING("Railway crossing", "Rail xing"),
    SPEEDHUMP("Speed hump", "Speed hump"),
    TRAMLINES("Tram lines", "Tram lines"),
    TUNNEL("Tunnel", "Tunnel"),
    UNDERPASS("Underpass", "Underpass"),
    ;
    public final String name;
    public final String csvName;

    RoadCharacter(String name, String csvName) {
      this.name = name;
      this.csvName = csvName;
    }

    /**
     * Gets the CSV name from the display name of the road character.
     *
     * @param name The display name.
     * @return The corresponding CSV name.
     */
    public static String getCSVNameFromName(String name) {
      for (RoadCharacter values : RoadCharacter.values()) {
        if (Objects.equals(values.name, name)) {
          return values.csvName;
        }
      }
      return null;
    }

    /**
     * Gets the display name from the CSV name of the road character.
     *
     * @param csvName The CSV name.
     * @return The corresponding display name.
     */
    public static String getNameFromCSVName(String csvName) {
      for (RoadCharacter values : RoadCharacter.values()) {
        if (Objects.equals(values.csvName, csvName)) {
          return values.name;
        }
      }
      return null;
    }
  }

  /**
   * Enumeration of the road surface values in the crash database name -- the displayed name in the
   * table csvName -- the value as it appears in the csv
   */
  public enum RoadSurface {
    ENDOFSEAL("End of seal", "End of seal"),
    SEALED("Sealed", "Sealed"),
    UNSEALED("Unsealed", "Unsealed"),
    ;
    public final String name;
    public final String csvName;

    RoadSurface(String name, String csvName) {
      this.name = name;
      this.csvName = csvName;
    }
  }

  /** Used to keep track of the search priority in an SQL search object */
  public enum SortDirection {
    ASCENDING("ASC"),
    DESCENDING("DESC");
    public final String sqlKeyword;

    SortDirection(String sqlKey) {
      this.sqlKeyword = sqlKey;
    }

    /**
     * Gets the SQL keyword associated with the sort direction.
     *
     * @return The SQL keyword ("ASC" for ascending or "DESC" for descending).
     */
    public String getSqlKeyword() {
      return sqlKeyword;
    }
  }
}
