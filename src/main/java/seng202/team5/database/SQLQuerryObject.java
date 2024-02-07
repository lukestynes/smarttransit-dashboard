
package seng202.team5.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import seng202.team5.exceptions.QueryNotBuiltException;
import seng202.team5.models.Conditional;
import seng202.team5.models.GenericChart;
import seng202.team5.models.MapPin;
import seng202.team5.models.Search;

import java.util.*;

/**
 * <p>A class that builds an sql string from a search object. This object works in 3 steps </p><ol><li>create the
 * object and add search parameters by adding a search object into the constructor call</li><li>call one of the
 * build query functions to choose what type of sql query your string should represent (e.g buildCountQuery will return
 * a query that returns a count of the results that a non paginated select query would get</li> <li> retrive the
 * sql string using this objects getSQL() function and run it on your database</li></ol>
 * <p>internally this object works by calling sub functions each building part of the sql string that will be returned.</p>
 * @author Dominic Gorny
 */
public class SQLQuerryObject {
    private static final Logger log = LogManager.getLogger(SQLQuerryObject.class);
    private boolean countQuery = false;
    String SQLString;

    Search searchObject;

    boolean built = false;

    /**l
     * <p>constructor for sql query object, takes in a search object and table name and creates an sql string stores within
     * this object. This query is made up of multiple segments, each of which further narrow down the records found
     * as to the specifications that are set in the search object. </p> <br>
     * @param searchQuery the search object that is turned to an sql string
     */
    public SQLQuerryObject(Search searchQuery)
    {
        this.searchObject = searchQuery;
    }

    /**
     * Builds the sql string for a count type query, this type of query will take a search object, search the database
     * according to the filters it describes, then return the number of records found.
     */
    public void buildCountQuery()
    {
        SQLString = "SELECT Count(*) FROM";


        SQLString += " " + searchObject.getTable() + " WHERE ";
        buildSearchbarSql(this.searchObject);
        buildFiltersSql(this.searchObject);
        buildConditionalsSQL(this.searchObject);
        buildLocationSql(this.searchObject);

        checkEmpty();
        built = true;
    }

    /**
     * Builds the sql string for a select type query, this type of query will take a search object, search the database
     * according to the filters it describes, then return the records found up to a pagination limit.
     */
    public void buildSelectQuery()
    {
        SQLString = "SELECT * FROM";


        SQLString += " " + searchObject.getTable() + " WHERE ";
        buildSearchbarSql(this.searchObject);
        buildFiltersSql(this.searchObject);
        buildConditionalsSQL(this.searchObject);
        buildLocationSql(this.searchObject);

        checkEmpty();
        addPagination(this.searchObject);

        built = true;
    }

    /**
     * Builds an SQL query that should return tuples of a name and count that can be graphed on the chart that is being created by the calling method.
     * This method is specifically for catagorical charts such as Bar and Pie charts as it recieves a category parameter.
     * @param category is the category that the data is to be grouped by. eg Severity.
     */
    public void buildCatChartQuery(GenericChart.Categories category)
    {
        SQLString = "SELECT COUNT(*), "+ category.dbName +" FROM";


        SQLString += " " + searchObject.getTable() + " WHERE ";
        buildSearchbarSql(this.searchObject);
        buildFiltersSql(this.searchObject);
        buildConditionalsSQL(this.searchObject);
        buildLocationSql(this.searchObject);
        checkEmpty();

        SQLString += " GROUP BY " + category.dbName;

        built = true;
    }

    /**
     * Builds an SQL query that should return tuples of a name and count that can be graphed on the chart that is being created by the calling method.
     * This method is specifically for continuous charts such as Line charts as it recieves a continuous variable parameter.
     * @param category is the category that the data is to be grouped by. eg Year.
     */
    public void buildChartQuery(GenericChart.Continuous category)
    {
        SQLString = "SELECT COUNT(*), "+ category.dbName +" FROM";


        SQLString += " " + searchObject.getTable() + " WHERE ";
        buildSearchbarSql(this.searchObject);
        buildFiltersSql(this.searchObject);
        buildConditionalsSQL(this.searchObject);
        buildLocationSql(this.searchObject);
        checkEmpty();

        SQLString += " GROUP BY " + category.dbName;

        built = true;
    }

    /**
     * Builds an SQL query that should return tuples of a name and count that can be graphed on the chart that is being created by the calling method.
     * This method is specifically for the area chart as it recieves a category parameter and a continuous parameter.
     * @param xCategory is the category that the x-axis is representing.
     * @param yCategory is the category that the values must be grouped into for seperate line graphs on the plot.
     * @param value is the value that the yCategory must match for that particular search.
     */
    public void buildDoubleChartQuery(GenericChart.Continuous xCategory,GenericChart.Categories yCategory, String value)
    {
        SQLString = "SELECT COUNT(*), "+ xCategory.dbName +" FROM";


        SQLString += " " + searchObject.getTable() + " WHERE " + yCategory.dbName + " = " + "'"+value+"'";
        buildSearchbarSql(this.searchObject);
        buildFiltersSql(this.searchObject);
        buildConditionalsSQL(this.searchObject);
        buildLocationSql(this.searchObject);
        checkEmpty();

        SQLString += " GROUP BY " + xCategory.dbName;

        built = true;
    }

    /**
     * Builds an SQL query that should return tuples of a name and count that can be graphed on the chart that is being created by the calling method.
     * This method is specifically for the line chart as it recieves a category parameter and a count parameter.
     * @param xCategory is the category that the x-axis is representing.
     * @param yCategory is the category that the values must be grouped into and counted from.
     */
    public void buildCountChartQuery(GenericChart.Continuous xCategory,GenericChart.Counts yCategory)
    {
        SQLString = "SELECT SUM(" + yCategory.dbName + "), "+ xCategory.dbName +" FROM";


        SQLString += " " + searchObject.getTable() + " WHERE ";
        buildSearchbarSql(this.searchObject);
        buildFiltersSql(this.searchObject);
        buildConditionalsSQL(this.searchObject);
        buildLocationSql(this.searchObject);
        if (SQLString.substring(SQLString.length()-5).equals(" AND ")) {
            SQLString = SQLString.substring(0, SQLString.length()-5);
        }

        checkEmpty();

        SQLString += " GROUP BY " + xCategory.dbName;

        built = true;
    }

    /**
     * Basic map query that adds all currently applied filters to the SQL string.
     */
    public void buildMapQuery()
    {
        SQLString = "SELECT ID,lat,lng,crashSeverity,crashYear FROM";


        SQLString += " " + searchObject.getTable() + " WHERE ";
        buildSearchbarSql(this.searchObject);
        buildFiltersSql(this.searchObject);
        buildConditionalsSQL(this.searchObject);
        buildLocationSql(this.searchObject);
        checkEmpty();

        built = true;
    }

    /**
     * A basic route query that uses a list of map pins and a radius to create circle searches at each point with the specified radius.
     * @param pins A lost of pins which are to be checked.
     * @param radius The radius of the circle for each query.
     */
    public void buildRouteQuery(List<MapPin> pins, Integer radius)
    {
        SQLString = "SELECT ID,lat,lng FROM";

        SQLString += " " + searchObject.getTable() + " WHERE ";
        buildSearchbarSql(this.searchObject);
        buildFiltersSql(this.searchObject);
        buildConditionalsSQL(this.searchObject);
        buildLocationSql(this.searchObject);
        buildRouteCirclesSql(pins, radius);
        checkEmpty();

        built = true;
    }

    /**
     * Constructs a query to retrieve pins within the provided polygons, utilizing bounding box optimization
     * based on maximum and minimum coordinates.
     *
     * @param allPolygons A list containing lists of points representing the corners of polygons, within which crashes are being searched.
     * @param maxPin The maximum coordinates for bounding box calculation.
     * @param minPin The minimum coordinates for bounding box calculation.
     */
    public void buildPinsInBoxQuery(ArrayList<ArrayList<MapPin>> allPolygons, MapPin maxPin, MapPin minPin) {
        SQLString = "SELECT ID,lat,lng,crashSeverity,crashYear FROM";
        SQLString += " " + "(SELECT * FROM " + searchObject.getTable() + " WHERE " + buildBigBox(maxPin,minPin)+")";
        SQLString += " WHERE ";
        buildBoxSql(allPolygons);
        buildSearchbarSql(this.searchObject);
        buildFiltersSql(this.searchObject);
        buildConditionalsSQL(this.searchObject);

        checkEmpty();
        built = true;
    }

    /**
     * Constructs an SQL statement corresponding bounding box condition based on maximum and minimum coordinates.
     *
     * @param maxPin The maximum coordinates for bounding box calculation.
     * @param minPin The minimum coordinates for bounding box calculation.
     * @return A SQL string condition representing the bounding box.
     */
    private String buildBigBox(MapPin maxPin, MapPin minPin) {
        return "((lat >= " + minPin.getLat() + " AND lat <= " + maxPin.getLat() + ") AND (lng >= " + minPin.getLng() +  " AND lng <= " + maxPin.getLng() + "))";
    }


    /**
     * Constructs SQL conditions for polygons based on coordinates corresponding to corners of the polygons.
     *
     * @param allPolygons A list containing lists of points representing the corners of polygons.
     */
    private void buildBoxSql(ArrayList<ArrayList<MapPin>> allPolygons) {
        for (int i = 0; i < allPolygons.size(); i++) {
            ArrayList<MapPin> innerList = allPolygons.get(i);
            double latMin = Math.min(Math.min(innerList.get(0).getLat(), innerList.get(1).getLat()), Math.min(innerList.get(2).getLat(), innerList.get(3).getLat()));
            double latMax = Math.max(Math.max(innerList.get(0).getLat(), innerList.get(1).getLat()), Math.max(innerList.get(2).getLat(), innerList.get(3).getLat()));
            double lngMin = Math.min(Math.min(innerList.get(0).getLng(), innerList.get(1).getLng()), Math.min(innerList.get(2).getLng(), innerList.get(3).getLng()));
            double lngMax = Math.max(Math.max(innerList.get(0).getLng(), innerList.get(1).getLng()), Math.max(innerList.get(2).getLng(), innerList.get(3).getLng()));
            String query = "((lat >= " + latMin + " AND lat <= " + latMax + ") AND (lng >= " + lngMin +  " AND lng <= " + lngMax + "))";
            SQLString += query;
            if (i != allPolygons.size() - 1) {
                SQLString += " OR ";
            }
        }
    }

    /**
     * Creates an SQL search query for crashes within a given radius of a given lat/lng position,
     * provided sufficient parameters exist in the search object to perform the query.
     * @param search
     */
    private void buildLocationSql(Search search) {
        if (search.getRadius() != null && search.getLat() != null && search.getLng() != null) {
            if (!SQLString.substring(SQLString.length() - 7).equals(" WHERE ")) {
                SQLString += " AND ";
            }
            String radius = String.valueOf(search.getRadius()); //in km
            String lat = String.valueOf(search.getLat());
            String lng = String.valueOf(search.getLng());
            String distanceQuery = "((6371 * ACOS(COS(RADIANS(" + lat + ")) * COS(RADIANS(lat)) * COS(RADIANS(" + lng + ") - RADIANS(lng)) + SIN(RADIANS(" + lat + ")) * SIN(RADIANS(lat)))) <= " + radius + ")";
            SQLString += distanceQuery;
        }
    }

    /**
     * Creates part of an SQL search query, generating circles around the supplied list of pins, in the indicated radius.
     * @param pins List of lat/lng pins in the route
     * @param radius Km of the radius
     */
    private void buildRouteCirclesSql(List<MapPin> pins, Integer radius) {
        for (MapPin pin : pins) {
            String rad = String.valueOf(radius); //in km
            String lat = String.valueOf(pin.getLat());
            String lng = String.valueOf(pin.getLng());
            String distanceQuery = "(6371 * ACOS(COS(RADIANS(" + lat + ")) * COS(RADIANS(lat)) * COS(RADIANS(" + lng + ") - RADIANS(lng)) + SIN(RADIANS(" + lat + ")) * SIN(RADIANS(lat)))) <= " + rad;
            SQLString += distanceQuery;
        }
    }



    /**
     * takes the search object out of which this object is built and builds the keyword search sub query for this sql
     * query
     * @param search the search object that is used to create this sql object.
     */

    private void buildSearchbarSql(Search search)
    {
        String keywords = search.getKeywords();
        boolean matchAll = search.isMatchAll();
        if (keywords != null && !keywords.isEmpty()) {
            keywords = keywords.replace("'","''");
            List<String> words = Arrays.asList(keywords.split("\\s+"));
            if (matchAll) {
                SQLString += "(";
                for (String word : words) {
                    searchBarString(word);
                    SQLString += "AND ";
                }
                SQLString = SQLString.substring(0, SQLString.length() - 4);
                SQLString += ") ";
            } else {
                SQLString += "(";
                for (String word : words) {
                    searchBarString(word);
                    SQLString += "OR ";
                }
                SQLString = SQLString.substring(0, SQLString.length() - 3);
                SQLString += ") ";
            }
        }
    }

    /**
     * Assistant function called by buildSearchbarSql to create a set of search parameters for a single word
     * @param key Word to use for query
     */
    private void searchBarString(String key) {
        SQLString += "((ID == '" + key + "')";
        SQLString += " OR ";
        SQLString += "(crashDirection like '%" + key + "%')";
        SQLString += " OR ";
        SQLString += "(financialYear like '%" + key + "%')";
        SQLString += " OR ";
        SQLString += "(crashLocationA like '%" + key + "%')";
        SQLString += " OR ";
        SQLString += "(crashLocationB like '%" + key + "%')";
        SQLString += " OR ";
        SQLString += "(sideRoad like '%" + key + "%')";
        SQLString += " OR ";
        SQLString += "(crashSeverity like '%" + key + "%')";
        SQLString += " OR ";
        SQLString += "(crashSHDescription like '%" + key + "%')";
        SQLString += " OR ";
        SQLString += "(directionRole like '%" + key + "%')";
        SQLString += " OR ";
        SQLString += "(flatHill like '%" + key + "%')";
        SQLString += " OR ";
        SQLString += "(holiday like '%" + key + "%')";
        SQLString += " OR ";
        SQLString += "(intersection like '%" + key + "%')";
        SQLString += " OR ";
        SQLString += "(light like '%" + key + "%')";
        SQLString += " OR ";
        SQLString += "(region like '%" + key + "%')";
        SQLString += " OR";
        SQLString += "(roadCharacter like '%" + key + "%')";
        SQLString += " OR ";
        SQLString += "(roadLane like '%" + key + "%')";
        SQLString += " OR ";
        SQLString += "(roadSurface like '%" + key + "%')";
        SQLString += " OR ";
        SQLString += "(streetLight like '%" + key + "%')";
        SQLString += " OR ";
        SQLString += "(tlaName like '%" + key + "%')";
        SQLString += " OR ";
        SQLString += "(trafficControl like '%" + key + "%')";
        SQLString += " OR ";
        SQLString += "(urban like '%" + key + "%')";
        SQLString += " OR ";
        SQLString += "(weatherA like '%" + key + "%')";
        SQLString += " OR ";
        SQLString += "(weatherB like '%" + key + "%')) ";
    }

    /**
     * takes a fieldname, lower and upper bounds and then creates a conditional for a sql string which requires that
     * the value of the field is within the given bounds. each of these bounds may be null;
     * @param fieldName the name of the field for which this comparison is made (must be the name used in the sql table)
     * @param lower the lower limit that should be applied to the field
     * @param upper the upper limit that should be applied to the field
     * @return returns the sql sub string
     */
    private static String integerLimitsSql(String fieldName, Integer lower, Integer upper)
    {
        if(lower == null && upper == null)
        {
            return "";
        }
        else if(lower == null)
        {
            return (fieldName + " <= " + upper);
        }
        else if(upper == null)
        {
            return (fieldName + " >= " + lower);
        }
        else
        {
            return ('(' + fieldName + " BETWEEN " + lower + " AND " + upper + ")");
        }

    }

    /**
     * takes a fieldname, and a hex list of possible values and makes a fragment of an sql query specified that all
     * records included in the search should have one of the values in the list for the specified field.
     * if the hash set is empty an empty string is returned
     * @param fieldName the name of the cell as described in the database
     * @param options a hash list of possible options that oncluded fields should have
     * @return an sql sub string
     */
    private static String inHashSet(String fieldName, HashSet<String> options)
    {
        if (options.size() == 0)
        {
            return "";
        }
        else
        {
            StringBuilder SQLString = new StringBuilder();
            SQLString.append ("(");
            SQLString.append(fieldName);
            SQLString.append(" IN (");

            for (String opt: options)
            {
                SQLString.append("'");
                SQLString.append(opt);
                SQLString.append("'");
                SQLString.append(", ");
            }
            SQLString.delete((SQLString.length()-2),SQLString.length());
            SQLString.append("))");
            return SQLString.toString();
        }

    }

    /**
     * Adds <b>"AND"</b> to the end of a string that is passed in only if that string is longer than 0 characters
     * @param inputStatement the input string
     * @return the resulting string being either empty or the original plus <i>" AND "</i> appended to the back
     */
    private static String addAndForNonEmpty(String inputStatement)
    {
        if (inputStatement.length() == 0)
        {
            return "";
        }
        else
        {
            return (inputStatement + " AND ");
        }
    }

    /**
     * takes the search object out of which this SQLQueryObject is built and builds the filter search sub query for this sql
     * query
     * @param search the search object that is used to create this sql object.
     */
    private void buildFiltersSql(Search search)
    {
        StringBuilder filtersSqlBuilder = new StringBuilder();
        filtersSqlBuilder.append(this.SQLString);

        if (!SQLString.substring(SQLString.length() - 7).equals(" WHERE ")) {
            filtersSqlBuilder.append(" AND ");
            }

        // runs through all fields that check for either string participation or integer limits within boundaries and
        // builds sql fragments for these using the helper functions defined above (namely:integerLimitsSql,
        // inHashSet, addAndForNoneEmpty)
        filtersSqlBuilder.append(addAndForNonEmpty(integerLimitsSql("crashYear",search.getStartYear(),search.getEndYear())));
        filtersSqlBuilder.append(addAndForNonEmpty(integerLimitsSql("advisorySpeed",search.getStartAdvisedSpeed(),search.getEndAdvisedSpeed())));
        filtersSqlBuilder.append(addAndForNonEmpty(inHashSet("crashSeverity",search.getSeverities())));
        filtersSqlBuilder.append(addAndForNonEmpty(inHashSet("region",search.getRegions())));
        filtersSqlBuilder.append(addAndForNonEmpty(integerLimitsSql("speedLimit",search.getStartSpeedLimit(),search.getEndSpeedLimit())));
        filtersSqlBuilder.append(addAndForNonEmpty(inHashSet("weatherA",search.getWeatherA())));
        filtersSqlBuilder.append(addAndForNonEmpty(inHashSet("weatherB",search.getWeatherB())));
        filtersSqlBuilder.append(addAndForNonEmpty(inHashSet("light",search.getLight())));
        filtersSqlBuilder.append(addAndForNonEmpty(inHashSet("holiday",search.getHolidays())));
        filtersSqlBuilder.append(addAndForNonEmpty(integerLimitsSql("tempSpeedLimit",search.getStartTemporarySpeed(),search.getEndTemporarySpeed())));
        filtersSqlBuilder.append(addAndForNonEmpty(integerLimitsSql("fatalCount",search.getStartDeaths(),search.getEndDeaths())));
        filtersSqlBuilder.append(addAndForNonEmpty(integerLimitsSql("minorInjury",search.getStartMinorInjuries(),search.getEndMinorInjuries())));
        filtersSqlBuilder.append(addAndForNonEmpty(integerLimitsSql("seriousInjuries",search.getStartMajorInjuries(),search.getEndMajorInjuries())));
        filtersSqlBuilder.append(addAndForNonEmpty(inHashSet("tlaName",search.getLocalAuthorities())));
        filtersSqlBuilder.append(addAndForNonEmpty(inHashSet("trafficControl",search.getTrafficControls())));
        filtersSqlBuilder.append(addAndForNonEmpty(inHashSet("crashDirection",search.getCrashDirections())));
        filtersSqlBuilder.append(addAndForNonEmpty(inHashSet("directionRole",search.getVehicleDirections())));
        filtersSqlBuilder.append(addAndForNonEmpty(integerLimitsSql("NumberOfLanes",search.getStartLanes(),search.getEndLanes())));
        filtersSqlBuilder.append(addAndForNonEmpty(inHashSet("roadLane",search.getLaneConfigs())));
        filtersSqlBuilder.append(addAndForNonEmpty(inHashSet("roadCharacter",search.getRoadCharacters())));
        filtersSqlBuilder.append(addAndForNonEmpty(inHashSet("roadSurface",search.getRoadSurfaces())));

        // adds conditions for all boolean (is or is not participating) fields to end of sql string
        if (search.getCollisionAnd()) {
            if (!search.getCollisions().isEmpty()) {
                filtersSqlBuilder.append("(");
                for(String collisionType: search.getCollisions())
                {
                    filtersSqlBuilder.append(collisionType);
                    filtersSqlBuilder.append(" > 0 AND ");
                }
                filtersSqlBuilder.delete(filtersSqlBuilder.length() - 5, filtersSqlBuilder.length());
                filtersSqlBuilder.append(") AND ");
            }
        } else {
            if (!search.getCollisions().isEmpty()) {
                filtersSqlBuilder.append("(");
                for(String collisionType: search.getCollisions())
                {
                    filtersSqlBuilder.append(collisionType);
                    filtersSqlBuilder.append(" > 0 OR ");
                }
                filtersSqlBuilder.delete(filtersSqlBuilder.length() - 4, filtersSqlBuilder.length());
                filtersSqlBuilder.append(") AND ");
            }
        }

        if (search.getParticipantAnd()) {
            if (!search.getParticipants().isEmpty()) {
                filtersSqlBuilder.append("(");
                for(String participantType: search.getParticipants())
                {
                    filtersSqlBuilder.append(participantType);
                    filtersSqlBuilder.append(" > 0 AND ");
                }
                filtersSqlBuilder.delete(filtersSqlBuilder.length() - 5, filtersSqlBuilder.length());
                filtersSqlBuilder.append(") AND ");
            }
        } else {
            if (!search.getParticipants().isEmpty()) {
                filtersSqlBuilder.append("(");
                for(String participantType: search.getParticipants())
                {
                    filtersSqlBuilder.append(participantType);
                    filtersSqlBuilder.append(" > 0 OR ");
                }
                filtersSqlBuilder.delete(filtersSqlBuilder.length() - 4, filtersSqlBuilder.length());
                filtersSqlBuilder.append(") AND ");
            }
        }

        if (search.getHill() == Boolean.TRUE)
        {
            filtersSqlBuilder.append("flatHill == 'Hill Road' AND ");
        }
        else if(search.getHill() == Boolean.FALSE)
        {
            filtersSqlBuilder.append("flatHill == 'Flat' AND ");
        }

        if (search.getStateHighway() == Boolean.TRUE)
        {
            filtersSqlBuilder.append("crashSHDescription == 'Yes' AND ");
        }
        else if(search.getStateHighway() == Boolean.FALSE)
        {
            filtersSqlBuilder.append("crashSHDescription == 'No' AND ");
        }

        if (search.getStreetLights() == Boolean.TRUE)
        {
            filtersSqlBuilder.append("streetLight == 'On' AND ");
        }
        else if(search.getStreetLights() == Boolean.FALSE)
        {
            filtersSqlBuilder.append("streetLight in ('Off', 'None') AND ");
        }


        // removes dangling " AND " at end of sql statement
        if (filtersSqlBuilder.substring(filtersSqlBuilder.length() - 5).equals(" AND ")) {
            int currentLen = filtersSqlBuilder.length();
            filtersSqlBuilder.delete(currentLen-5,currentLen);
        // removes dangling " OR " at end of sql statement
        } else if (filtersSqlBuilder.substring(filtersSqlBuilder.length() - 4).equals(" OR ")) {
            int currentLen = filtersSqlBuilder.length();
            filtersSqlBuilder.delete(currentLen-4,currentLen);
        }

        this.SQLString = filtersSqlBuilder.toString();

    }

    /**
     * Builds conditionals into the SQL string by looping through the stored conditionals in the search object and
     * adding the appropriate string.
     * If a conditional is not fully specified (i.e., at least one value is null), it is ignored.
     * @param search the search object used to create this SqlQueryObject
     */
    private void buildConditionalsSQL(Search search)
    {
        StringBuilder conditionalsString = new StringBuilder(SQLString);
        for(Map.Entry<Integer, Conditional> map : search.getConditionals().entrySet())
        {
            Conditional conditional = map.getValue();
            //Ignore incomplete conditionals here
            if (conditional.column == null || conditional.operator == null || conditional.value == null || conditional.value.isEmpty())
            {
                log.warn(String.format("Conditional %s is incomplete and was dropped" , conditional));
                continue;
            }
            else if ((conditional.value2 == null || conditional.value2.isEmpty()) && (conditional.operator == Conditional.Operator.BETWEEN))
            {
                log.warn(String.format("Conditional for %s column is incomplete and was dropped"
                        , conditional.getColumn().getDbLabel()));
                continue;
            }

            // ensures and is only added to the front if there is some other clause in the where
            // statement before this one
            if (!conditionalsString.substring(SQLString.length() - 7).equals(" WHERE ")) {
                conditionalsString.append(" AND ");
            }


            switch(conditional.operator)
            {
                case BETWEEN:
                    conditionalsString.append("(");
                    conditionalsString.append(conditional.getColumn().getDbLabel());
                    conditionalsString.append(" BETWEEN ");
                    conditionalsString.append("'");
                    conditionalsString.append(conditional.getValue().replace("'","''"));
                    conditionalsString.append("' ");
                    conditionalsString.append("AND");
                    conditionalsString.append(" '");
                    conditionalsString.append(conditional.getValue2().replace("'","''"));
                    conditionalsString.append("'");
                    conditionalsString.append(") ");
                    break;
                case CONTAINS:
                    conditionalsString.append(conditional.getColumn().getDbLabel());
                    conditionalsString.append(" LIKE ");
                    conditionalsString.append("'%");
                    conditionalsString.append(conditional.getValue().replace("'","''"));
                    conditionalsString.append("%' ");
                    break;
                case NOTLIKE:
                    conditionalsString.append(conditional.getColumn().getDbLabel());
                    conditionalsString.append(" NOT LIKE ");
                    conditionalsString.append("'%");
                    conditionalsString.append(conditional.getValue().replace("'","''"));
                    conditionalsString.append("%' ");
                    break;
                case LIKE:
                    conditionalsString.append(conditional.getColumn().getDbLabel());
                    conditionalsString.append(" GLOB '");
                    conditionalsString.append(conditional.getValue());
                    conditionalsString.append("' ");
                    break;
                default:
                    conditionalsString.append(conditional.getColumn().getDbLabel());
                    conditionalsString.append(" ");
                    conditionalsString.append(conditional.getOperator().sqlString);
                    conditionalsString.append(" '");
                    conditionalsString.append(conditional.getValue().replace("'","''"));
                    conditionalsString.append("' ");
                    break;
            }

        }
        this.SQLString = conditionalsString.toString();
    }

    /**
     * Run after query creation (but before pagination) to check if it is empty. If empty it will remove the <i>WHERE</i>
     * clause of the sql query. this is as a dangeling <i>WHERE</i> statement will cause an sqlLite exception when run
     * against the database
     */
    private void checkEmpty()
    {
        if (SQLString.substring(SQLString.length() - 7).equals(" WHERE ")) {
            SQLString = SQLString.substring(0,SQLString.length() - 7);
        }
    }

    /**
     * Adds pagination and sort clauses to the end of the query
     * Resources used for this function:
     * https://www.sqlitetutorial.net/sqlite-limit/
     * @param search the search object which is currently being used
     */
    private void addPagination(Search search)
    {
        StringBuilder paginationBuilder = new StringBuilder();
        paginationBuilder.append(this.SQLString);
        paginationBuilder.append(" ORDER BY ");
        paginationBuilder.append(search.getPrimarySort().getDbLabel());
        paginationBuilder.append(" ");
        paginationBuilder.append(search.getPrimarySortDirection().getSqlKeyword());
        paginationBuilder.append(", ");
        paginationBuilder.append(search.getSecondarySort().getDbLabel());
        paginationBuilder.append(" ");
        paginationBuilder.append(search.getSecondarySortDirection().getSqlKeyword());
        paginationBuilder.append(" LIMIT ");
        paginationBuilder.append(search.getPageSize());
        paginationBuilder.append(" OFFSET ");
        paginationBuilder.append(search.getPage() * search.getPageSize());
        paginationBuilder.append(";");
        this.SQLString = paginationBuilder.toString();

    }

    /**
     * returns the sql string that is made within this object. Throws an exception if none of the QueryBuild functions
     * was called prior to the calling of this function.
     * @return a sql representation of the
     * @throws QueryNotBuiltException thrown if none of the query builder functions have been called
     */
    public String getSql() throws QueryNotBuiltException
    {
        if(!built) {
            throw new QueryNotBuiltException();
        }
        log.info("New SQL String: " + SQLString);
        return SQLString;
    }

    /**
     * Clone of getSQL() function, enables users to get sql string more intuitively.
     * returns the sql string that is made within this object. Throws an exception if none of the QueryBuild functions
     * was called prior to the calling of this function.
     * @return a sql representation of the
     * @throws QueryNotBuiltException thrown if none of the query builder functions have been called
     */
    @Override
    public String toString() throws QueryNotBuiltException
    {
        if(!built) {
            throw new QueryNotBuiltException();
        }
        log.info("New SQL String: " + SQLString);
        return SQLString;
    }


    /**
     * Build a generic query from search, used for testing results are accurately represented.
     */
    public void buildQuery() {
        SQLString = "SELECT * FROM";
        SQLString += " " + searchObject.getTable() + " WHERE ";
        buildSearchbarSql(this.searchObject);
        buildFiltersSql(this.searchObject);
        buildConditionalsSQL(this.searchObject);
        buildLocationSql(this.searchObject);
        checkEmpty();
        built = true;
    }
}
