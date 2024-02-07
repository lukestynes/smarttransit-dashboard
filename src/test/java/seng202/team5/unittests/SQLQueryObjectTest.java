package seng202.team5.unittests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import seng202.team5.database.SQLQuerryObject;
import seng202.team5.exceptions.QueryNotBuiltException;
import seng202.team5.models.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SQLQueryObjectTest {

    @Test
    public void initialTest()
    {
        SQLQuerryObject testObject = new SQLQuerryObject(new Search() );
        testObject.buildSelectQuery();
        assertInstanceOf(SQLQuerryObject.class, testObject);
    }

    @Test
    public void correctEmptySearchSelectQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject);
        testObject.buildSelectQuery();
        assertEquals("SELECT * FROM table ORDER BY ID ASC, ID ASC LIMIT 30 OFFSET 0;", testObject.getSql());

    }

    @Test
    public void invalidVal1ConditionalSearchSelectQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        Conditional testConditional = new Conditional(TableColumns.Column.ID, Conditional.Operator.EQUAL,
                null,null);
        testSearchObject.addConditional(0,testConditional);

        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject);
        testObject.buildSelectQuery();
        assertEquals("SELECT * FROM table ORDER BY ID ASC, ID ASC LIMIT 30 OFFSET 0;", testObject.getSql());

    }

    @Test
    public void invalidVal2ConditionalSearchSelectQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        Conditional testConditional = new Conditional(TableColumns.Column.ID, Conditional.Operator.BETWEEN,
                "1",null);
        testSearchObject.addConditional(0,testConditional);

        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject);
        testObject.buildSelectQuery();
        assertEquals("SELECT * FROM table ORDER BY ID ASC, ID ASC LIMIT 30 OFFSET 0;", testObject.getSql());

    }

    @Test
    public void invalidOpConditionalSearchSelectQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        Conditional testConditional = new Conditional(null, Conditional.Operator.BETWEEN,
                "1","2");
        testSearchObject.addConditional(0,testConditional);

        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject);
        testObject.buildSelectQuery();
        assertEquals("SELECT * FROM table ORDER BY ID ASC, ID ASC LIMIT 30 OFFSET 0;", testObject.getSql());
    }

    @Test
    public void invalidColumnConditionalSearchSelectQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        Conditional testConditional = new Conditional(TableColumns.Column.ID, null,
                "1",null);
        testSearchObject.addConditional(0,testConditional);

        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject);
        testObject.buildSelectQuery();
        assertEquals("SELECT * FROM table ORDER BY ID ASC, ID ASC LIMIT 30 OFFSET 0;", testObject.getSql());
    }

    @Test
    public void betweenConditionalSearchSelectQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        Conditional testConditional = new Conditional(TableColumns.Column.ID, Conditional.Operator.BETWEEN,
                "1","2");
        testSearchObject.addConditional(0,testConditional);

        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject);
        testObject.buildSelectQuery();
        assertEquals("SELECT * FROM table WHERE (ID BETWEEN '1' AND '2')  ORDER BY ID ASC, ID ASC LIMIT 30" +
                " OFFSET 0;", testObject.getSql());

    }

    @Test
    public void containsConditionalSearchSelectQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        Conditional testConditional = new Conditional(TableColumns.Column.ID, Conditional.Operator.CONTAINS,
                "test","2");
        testSearchObject.addConditional(0,testConditional);

        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject);
        testObject.buildSelectQuery();
        assertEquals("SELECT * FROM table WHERE ID LIKE '%test%'  ORDER BY ID ASC, ID ASC LIMIT 30 OFFSET 0;", testObject.getSql());

    }

    @Test
    public void containsNotEqualSearchSelectQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        Conditional testConditional = new Conditional(TableColumns.Column.ID, Conditional.Operator.NOTEQUAL,
                "1",null);
        testSearchObject.addConditional(0,testConditional);

        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject);
        testObject.buildSelectQuery();
        assertEquals("SELECT * FROM table WHERE ID != '1'  ORDER BY ID ASC, ID ASC LIMIT 30" +
                " OFFSET 0;", testObject.getSql());

    }
    @Test
    public void containsNotEqualAndEqualSearchSelectQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        Conditional testConditional = new Conditional(TableColumns.Column.ID, Conditional.Operator.NOTEQUAL,
                "1",null);
        Conditional testConditional2 = new Conditional(TableColumns.Column.ID, Conditional.Operator.EQUAL,
                "1",null);


        testSearchObject.addConditional(0,testConditional);
        testSearchObject.addConditional(1,testConditional2);

        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject);
        testObject.buildSelectQuery();
        assertEquals("SELECT * FROM table WHERE ID != '1'  AND ID = '1'  ORDER BY ID ASC, ID ASC LIMIT 30" +
                " OFFSET 0;", testObject.getSql());

    }

    @Test
    public void containsNotEqualAndPrirorSearchSelectQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setStartYear(1);
        Conditional testConditional = new Conditional(TableColumns.Column.ID, Conditional.Operator.NOTEQUAL,
                "1",null);
        testSearchObject.addConditional(0,testConditional);

        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject);
        testObject.buildSelectQuery();
        assertEquals("SELECT * FROM table WHERE crashYear >= 1 AND ID != '1'  ORDER BY ID ASC," +
                " ID ASC LIMIT 30 OFFSET 0;", testObject.getSql());

    }


    @Test
    public void correctEmptySearchSelectQueryUsingToString()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject);
        testObject.buildSelectQuery();
        assertEquals("SELECT * FROM table ORDER BY ID ASC, ID ASC LIMIT 30 OFFSET 0;", testObject.toString());

    }

    @Test
    public void unbuiltSearchQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject);
        try
        {
            testObject.getSql();
            fail("Should thrown QueryNotBuildException but threw nothing");
        }
        catch (QueryNotBuiltException err)
        {
            return;
        }
        catch (Exception err)
        {
            fail("Should thrown QueryNotBuildException but threw " + err.getMessage());
        }

    }

    @ParameterizedTest
    @EnumSource(TableColumns.Collisions.class)
    void testCollisionFilters(TableColumns.Collisions collisions)
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        HashSet<String> testString = new HashSet<>();
        testString.add(collisions.dbName);
        testSearchObject.setCollisions(testString);
        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject);

        String expectedSQL = "SELECT * FROM table WHERE (" + collisions.dbName + " > 0) ORDER BY ID ASC, ID ASC LIMIT 30 OFFSET 0;";
        testObject.buildSelectQuery();

        assertEquals(expectedSQL, testObject.getSql());
    }

    @ParameterizedTest
    @EnumSource(TableColumns.Participants.class)
    void testParticipantsFilters(TableColumns.Participants participants)
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        HashSet<String> testString = new HashSet<>();
        testString.add(participants.dbName);
        testSearchObject.setParticipants(testString);
        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject);

        String expectedSQL = "SELECT * FROM table WHERE (" + participants.dbName + " > 0) ORDER BY ID ASC, ID ASC LIMIT 30 OFFSET 0;";
        testObject.buildSelectQuery();

        assertEquals(expectedSQL, testObject.getSql());
    }

    @Test
    void testFiltersHillTrue()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setHill(true);
        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject);

        String expectedSQL = "SELECT * FROM table WHERE ";
        expectedSQL += "flatHill";
        expectedSQL += " == 'Hill Road' ORDER BY ID ASC, ID ASC LIMIT 30 OFFSET 0;";
        testObject.buildSelectQuery();

        assertEquals(expectedSQL, testObject.getSql());
    }

    @Test
    void testFiltersHillFalse()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setHill(false);
        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject);

        String expectedSQL = "SELECT * FROM table WHERE ";
        expectedSQL += "flatHill";
        expectedSQL += " == 'Flat' ORDER BY ID ASC, ID ASC LIMIT 30 OFFSET 0;";
        testObject.buildSelectQuery();

        assertEquals(expectedSQL, testObject.getSql());
    }

    @Test
    void testFiltersSHTrue()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setStateHighway(true);
        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject);

        String expectedSQL = "SELECT * FROM table WHERE ";
        expectedSQL += "crashSHDescription";
        expectedSQL += " == 'Yes' ORDER BY ID ASC, ID ASC LIMIT 30 OFFSET 0;";
        testObject.buildSelectQuery();

        assertEquals(expectedSQL, testObject.getSql());
    }

    @Test
    void testFiltersSHFalse()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setStateHighway(false);
        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject);

        String expectedSQL = "SELECT * FROM table WHERE ";
        expectedSQL += "crashSHDescription";
        expectedSQL += " == 'No' ORDER BY ID ASC, ID ASC LIMIT 30 OFFSET 0;";
        testObject.buildSelectQuery();

        assertEquals(expectedSQL, testObject.getSql());
    }

    @Test
    void testFiltersStreetLightsTrue()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setStreetLights(true);
        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject);

        String expectedSQL = "SELECT * FROM table WHERE ";
        expectedSQL += "streetLight";
        expectedSQL += " == 'On' ORDER BY ID ASC, ID ASC LIMIT 30 OFFSET 0;";
        testObject.buildSelectQuery();

        assertEquals(expectedSQL, testObject.getSql());
    }

    @Test
    void testFiltersStreetLightsFalse()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setStreetLights(false);
        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject);

        String expectedSQL = "SELECT * FROM table WHERE ";
        expectedSQL += "streetLight";
        expectedSQL += " in ('Off', 'None') ORDER BY ID ASC, ID ASC LIMIT 30 OFFSET 0;";
        testObject.buildSelectQuery();

        assertEquals(expectedSQL, testObject.getSql());
    }


    @Test
    public void unbuiltSearchQueryUsingToString()
    {
        SQLQuerryObject testObject = new SQLQuerryObject(new Search() );
        try
        {
            testObject.toString();
            fail("Should thrown QueryNotBuildException but threw nothing");
        }
        catch (QueryNotBuiltException err)
        {
            return;
        }
        catch (Exception err)
        {
            fail("Should thrown QueryNotBuildException but threw " + err.getMessage());
        }

    }

    @Test
    public void correctNullKeywordSearchSelectQuery()
    {
        Search testSearch = new Search();
        testSearch.setTable("table");
        testSearch.setKeywords(null);
        SQLQuerryObject testObject = new SQLQuerryObject(testSearch );
        testObject.buildSelectQuery();
        assertEquals("SELECT * FROM table ORDER BY ID ASC, ID ASC LIMIT 30 OFFSET 0;", testObject.getSql());

    }

    @Test
    public void correctEmptyKeywordSearchSelectQuery()
    {
        Search testSearch = new Search();
        testSearch.setTable("table");
        testSearch.setKeywords("");
        SQLQuerryObject testObject = new SQLQuerryObject(testSearch );
        testObject.buildSelectQuery();
        assertEquals("SELECT * FROM table ORDER BY ID ASC, ID ASC LIMIT 30 OFFSET 0;", testObject.getSql());

    }

    @Test
    public void invalidConditionalsSearchSelectQuery()
    {
        Search testSearch = new Search();
        testSearch.setTable("table");
        Conditional testCondional = new Conditional(TableColumns.Column.ID, Conditional.Operator.EQUAL,null,null);
        SQLQuerryObject testObject = new SQLQuerryObject(testSearch );
        testObject.buildSelectQuery();
        assertEquals("SELECT * FROM table ORDER BY ID ASC, ID ASC LIMIT 30 OFFSET 0;", testObject.getSql());

    }

    @Test
    public void correctEmptySearchCatChartQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject);
        testObject.buildCatChartQuery(GenericChart.Categories.SEVERITY);
        assertEquals("SELECT COUNT(*), crashSeverity FROM table GROUP BY crashSeverity", testObject.getSql());
    }

    @Test
    public void correctEmptySearchChartQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject);        testObject.buildChartQuery(GenericChart.Continuous.YEAR);
        assertEquals("SELECT COUNT(*), crashYear FROM table GROUP BY crashYear", testObject.getSql());
    }

    @Test
    public void correctEmptySearchChartDoubleQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject);        testObject.buildDoubleChartQuery(GenericChart.Continuous.YEAR, GenericChart.Categories.SEVERITY, "Serious Crash");
        assertEquals("SELECT COUNT(*), crashYear FROM table WHERE crashSeverity = 'Serious Crash' GROUP BY crashYear", testObject.getSql());
    }

    @Test
    public void correctEmptySearchChartCountQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject);
        testObject.buildCountChartQuery(GenericChart.Continuous.YEAR, GenericChart.Counts.FATALCOUNT);
        assertEquals("SELECT SUM(fatalCount), crashYear FROM table GROUP BY crashYear", testObject.getSql());
    }

    @Test
    public void corrctEmptySearchMapQuery() {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject);        testObject.buildMapQuery();
        assertEquals("SELECT ID,lat,lng,crashSeverity,crashYear FROM table", testObject.getSql());
    }

    @Test
    public void correctRouteQuery() {
        Search search = new Search();
        search.setLat(-36.0);
        search.setLng(174.0);
        search.setRadius(2);
        search.setTable("table");
        SQLQuerryObject testObject = new SQLQuerryObject(search);
        MapPin pinOne = new MapPin(-36, 174, "Pin1");
        MapPin pinTwo = new MapPin(-36, 174.0000001, "Pin2");
        List<MapPin> pins = new ArrayList<>();
        pins.add(pinOne);
        pins.add(pinTwo);
        testObject.buildRouteQuery(pins, 1);
        assertEquals("SELECT ID,lat,lng FROM table WHERE ((6371 * ACOS(COS(RADIANS(-36.0)) * COS(RADIANS(lat)) * COS(RADIANS(174.0) - RADIANS(lng)) + SIN(RADIANS(-36.0)) * SIN(RADIANS(lat)))) <= 2)(6371 * ACOS(COS(RADIANS(-36.0)) * COS(RADIANS(lat)) * COS(RADIANS(174.0) - RADIANS(lng)) + SIN(RADIANS(-36.0)) * SIN(RADIANS(lat)))) <= 1(6371 * ACOS(COS(RADIANS(-36.0)) * COS(RADIANS(lat)) * COS(RADIANS(174.0000001) - RADIANS(lng)) + SIN(RADIANS(-36.0)) * SIN(RADIANS(lat)))) <= 1", testObject.getSql());
    }

    @Test
    public void descendingOrderSearchSelectQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setPrimarySortDirection(TableColumns.SortDirection.DESCENDING);
        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject );

        testObject.buildSelectQuery();
		assertEquals("SELECT * FROM table ORDER BY ID DESC, ID ASC LIMIT 30 OFFSET 0;", testObject.getSql());
    }

    @Test
    public void ascendingOrderSearchSelectQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setPrimarySortDirection(TableColumns.SortDirection.ASCENDING);
        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject );

        testObject.buildSelectQuery();
		assertEquals("SELECT * FROM table ORDER BY ID ASC, ID ASC LIMIT 30 OFFSET 0;", testObject.getSql());
    }

    @Test
    public void correctPaginationSearchSelectQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setPage(3);
        testSearchObject.setPageSize(80);
        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject );

        testObject.buildSelectQuery();
		assertEquals("SELECT * FROM table ORDER BY ID ASC, ID ASC LIMIT 80 OFFSET 240;",testObject.getSql());
    }

    @Test
    public void yearIntegerFieldDoubleBoundsTestSelectQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setStartYear(0);
        testSearchObject.setEndYear(1);
        TableColumns.Column column = TableColumns.Column.YEAR;
        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject );
        String expectedSQL = "SELECT * FROM table WHERE ";
        expectedSQL += "(" + column.dbLabel + " BETWEEN 0 AND 1)";
        expectedSQL += " ORDER BY ID ASC, ID ASC LIMIT 30 OFFSET 0;";
        testObject.buildSelectQuery();
		assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void speedLimitIntegerFieldDoubleBoundsTestSelectQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setStartSpeedLimit(0);
        testSearchObject.setEndSpeedLimit(1);
        TableColumns.Column column = TableColumns.Column.SPEEDLIMIT;
        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject );
        String expectedSQL = "SELECT * FROM table WHERE ";
        expectedSQL += "(" + column.dbLabel + " BETWEEN 0 AND 1)";
        expectedSQL += " ORDER BY ID ASC, ID ASC LIMIT 30 OFFSET 0;";
        testObject.buildSelectQuery();
		assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void advSpeedIntegerFieldDoubleBoundsTestSelectQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setStartAdvisedSpeed(0);
        testSearchObject.setEndAdvisedSpeed(1);
        TableColumns.Column column = TableColumns.Column.ADVSPEED;
        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject );
        String expectedSQL = "SELECT * FROM table WHERE ";
        expectedSQL += "(" + column.dbLabel + " BETWEEN 0 AND 1)";
        expectedSQL += " ORDER BY ID ASC, ID ASC LIMIT 30 OFFSET 0;";
        testObject.buildSelectQuery();
		assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void tempSpeedIntegerFieldDoubleBoundsTestSelectQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setTemporaryStartLimit(0);
        testSearchObject.setTemporaryEndLimit(1);
        TableColumns.Column column = TableColumns.Column.TEMPSPEED;
        SQLQuerryObject testObject = new SQLQuerryObject( testSearchObject );
        String expectedSQL = "SELECT * FROM table WHERE ";
        expectedSQL += "(" + column.dbLabel + " BETWEEN 0 AND 1)";
        expectedSQL += " ORDER BY ID ASC, ID ASC LIMIT 30 OFFSET 0;";
        testObject.buildSelectQuery();
		assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void deathsIntegerFieldDoubleBoundsTestSelectQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setStartDeaths(0);
        testSearchObject.setEndDeaths(1);
        TableColumns.Column column = TableColumns.Column.FATALCOUNT;
        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject );
        String expectedSQL = "SELECT * FROM table WHERE ";
        expectedSQL += "(" + column.dbLabel + " BETWEEN 0 AND 1)";
        expectedSQL += " ORDER BY ID ASC, ID ASC LIMIT 30 OFFSET 0;";
        testObject.buildSelectQuery();
		assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void minorInjuryIntegerFieldDoubleBoundsTestSelectQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setStartMinorInjuries(0);
        testSearchObject.setEndMinorInjuries(1);
        TableColumns.Column column = TableColumns.Column.MINORINJURY;
        SQLQuerryObject testObject = new SQLQuerryObject( testSearchObject );
        String expectedSQL = "SELECT * FROM table WHERE ";
        expectedSQL += "(" + column.dbLabel + " BETWEEN 0 AND 1)";
        expectedSQL += " ORDER BY ID ASC, ID ASC LIMIT 30 OFFSET 0;";
        testObject.buildSelectQuery();
		assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void majorInjuryIntegerFieldDoubleBoundsTestSelectQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setStartMajorInjuries(0);
        testSearchObject.setEndMajorInjuries(1);
        TableColumns.Column column = TableColumns.Column.SERIOUSINJURY;
        SQLQuerryObject testObject = new SQLQuerryObject( testSearchObject );
        String expectedSQL = "SELECT * FROM table WHERE ";
        expectedSQL += "(" + column.dbLabel + " BETWEEN 0 AND 1)";
        expectedSQL += " ORDER BY ID ASC, ID ASC LIMIT 30 OFFSET 0;";
        testObject.buildSelectQuery();
		assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void lanesIntegerFieldDoubleBoundsTestSelectQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setStartLanes(0);
        testSearchObject.setEndLanes(1);
        TableColumns.Column column = TableColumns.Column.NUMLANES;
        SQLQuerryObject testObject = new SQLQuerryObject( testSearchObject );
        String expectedSQL = "SELECT * FROM table WHERE ";
        expectedSQL += "(" + column.dbLabel + " BETWEEN 0 AND 1)";
        expectedSQL += " ORDER BY ID ASC, ID ASC LIMIT 30 OFFSET 0;";
        testObject.buildSelectQuery();
		assertEquals(expectedSQL,testObject.getSql());
    }


    @Test
    public void yearIntegerFieldLowerBoundsTestSelectQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setStartYear(0);
        TableColumns.Column column = TableColumns.Column.YEAR;
        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject );
        String expectedSQL = "SELECT * FROM table WHERE ";
        expectedSQL += column.dbLabel + " >= 0";
        expectedSQL += " ORDER BY ID ASC, ID ASC LIMIT 30 OFFSET 0;";
        testObject.buildSelectQuery();
		assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void speedLimitIntegerFieldLowerBoundsTestSelectQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setStartSpeedLimit(0);
        TableColumns.Column column = TableColumns.Column.SPEEDLIMIT;
        SQLQuerryObject testObject = new SQLQuerryObject( testSearchObject );
        String expectedSQL = "SELECT * FROM table WHERE ";
        expectedSQL += column.dbLabel + " >= 0";
        expectedSQL += " ORDER BY ID ASC, ID ASC LIMIT 30 OFFSET 0;";
        testObject.buildSelectQuery();
		assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void advSpeedIntegerFieldLowerBoundsTestSelectQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setStartAdvisedSpeed(0);
        TableColumns.Column column = TableColumns.Column.ADVSPEED;
        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject );
        String expectedSQL = "SELECT * FROM table WHERE ";
        expectedSQL += column.dbLabel + " >= 0";
        expectedSQL += " ORDER BY ID ASC, ID ASC LIMIT 30 OFFSET 0;";
        testObject.buildSelectQuery();
		assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void tempSpeedIntegerFieldLowerBoundsTestSelectQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setTemporaryStartLimit(0);
        TableColumns.Column column = TableColumns.Column.TEMPSPEED;
        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject );
        String expectedSQL = "SELECT * FROM table WHERE ";
        expectedSQL += column.dbLabel + " >= 0";
        expectedSQL += " ORDER BY ID ASC, ID ASC LIMIT 30 OFFSET 0;";
        testObject.buildSelectQuery();
		assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void deathsIntegerFieldLowerBoundsTestSelectQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setStartDeaths(0);
        TableColumns.Column column = TableColumns.Column.FATALCOUNT;
        SQLQuerryObject testObject = new SQLQuerryObject( testSearchObject );
        String expectedSQL = "SELECT * FROM table WHERE ";
        expectedSQL += column.dbLabel + " >= 0";
        expectedSQL += " ORDER BY ID ASC, ID ASC LIMIT 30 OFFSET 0;";
        testObject.buildSelectQuery();
		assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void minorInjuryIntegerFieldLowerBoundsTestSelectQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setStartMinorInjuries(0);
        TableColumns.Column column = TableColumns.Column.MINORINJURY;
        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject );
        String expectedSQL = "SELECT * FROM table WHERE ";
        expectedSQL += column.dbLabel + " >= 0";
        expectedSQL += " ORDER BY ID ASC, ID ASC LIMIT 30 OFFSET 0;";
        testObject.buildSelectQuery();
		assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void majorInjuryIntegerFieldLowerBoundsTestSelectQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setStartMajorInjuries(0);
        TableColumns.Column column = TableColumns.Column.SERIOUSINJURY;
        SQLQuerryObject testObject = new SQLQuerryObject( testSearchObject );
        String expectedSQL = "SELECT * FROM table WHERE ";
        expectedSQL += column.dbLabel + " >= 0";
        expectedSQL += " ORDER BY ID ASC, ID ASC LIMIT 30 OFFSET 0;";
        testObject.buildSelectQuery();
		assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void lanesIntegerFieldLowerBoundsTestSelectQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setStartLanes(0);
        TableColumns.Column column = TableColumns.Column.NUMLANES;
        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject );
        String expectedSQL = "SELECT * FROM table WHERE ";
        expectedSQL += column.dbLabel + " >= 0";
        expectedSQL += " ORDER BY ID ASC, ID ASC LIMIT 30 OFFSET 0;";
        testObject.buildSelectQuery();
		assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void yearIntegerFieldUpperBoundsTestSelectQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setEndYear(0);
        TableColumns.Column column = TableColumns.Column.YEAR;
        SQLQuerryObject testObject = new SQLQuerryObject( testSearchObject );
        String expectedSQL = "SELECT * FROM table WHERE ";
        expectedSQL += column.dbLabel + " <= 0";
        expectedSQL += " ORDER BY ID ASC, ID ASC LIMIT 30 OFFSET 0;";
        testObject.buildSelectQuery();
		assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void speedLimitIntegerFieldUpperBoundsTestSelectQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setEndSpeedLimit(0);
        TableColumns.Column column = TableColumns.Column.SPEEDLIMIT;
        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject );
        String expectedSQL = "SELECT * FROM table WHERE ";
        expectedSQL += column.dbLabel + " <= 0";
        expectedSQL += " ORDER BY ID ASC, ID ASC LIMIT 30 OFFSET 0;";
        testObject.buildSelectQuery();
		assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void advSpeedIntegerFieldUpperBoundsTestSelectQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setEndAdvisedSpeed(0);
        TableColumns.Column column = TableColumns.Column.ADVSPEED;
        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject );
        String expectedSQL = "SELECT * FROM table WHERE ";
        expectedSQL += column.dbLabel + " <= 0";
        expectedSQL += " ORDER BY ID ASC, ID ASC LIMIT 30 OFFSET 0;";
        testObject.buildSelectQuery();
		assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void tempSpeedIntegerFieldUpperBoundsTestSelectQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setTemporaryEndLimit(0);
        TableColumns.Column column = TableColumns.Column.TEMPSPEED;
        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject );
        String expectedSQL = "SELECT * FROM table WHERE ";
        expectedSQL += column.dbLabel + " <= 0";
        expectedSQL += " ORDER BY ID ASC, ID ASC LIMIT 30 OFFSET 0;";
        testObject.buildSelectQuery();
		assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void deathsIntegerFieldUpperBoundsTestSelectQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setEndDeaths(0);
        TableColumns.Column column = TableColumns.Column.FATALCOUNT;
        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject );
        String expectedSQL = "SELECT * FROM table WHERE ";
        expectedSQL += column.dbLabel + " <= 0";
        expectedSQL += " ORDER BY ID ASC, ID ASC LIMIT 30 OFFSET 0;";
        testObject.buildSelectQuery();
		assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void minorInjuryIntegerFieldUpperBoundsTestSelectQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setEndMinorInjuries(0);
        TableColumns.Column column = TableColumns.Column.MINORINJURY;
        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject );
        String expectedSQL = "SELECT * FROM table WHERE ";
        expectedSQL += column.dbLabel + " <= 0";
        expectedSQL += " ORDER BY ID ASC, ID ASC LIMIT 30 OFFSET 0;";
        testObject.buildSelectQuery();
		assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void majorInjuryIntegerFieldUpperBoundsTestSelectQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setEndMajorInjuries(0);
        TableColumns.Column column = TableColumns.Column.SERIOUSINJURY;
        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject );
        String expectedSQL = "SELECT * FROM table WHERE ";
        expectedSQL += column.dbLabel + " <= 0";
        expectedSQL += " ORDER BY ID ASC, ID ASC LIMIT 30 OFFSET 0;";
        testObject.buildSelectQuery();
		assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void lanesIntegerFieldUpperBoundsTestSelectQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setEndLanes(0);
        TableColumns.Column column = TableColumns.Column.NUMLANES;
        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject );
        String expectedSQL = "SELECT * FROM table WHERE ";
        expectedSQL += column.dbLabel + " <= 0";
        expectedSQL += " ORDER BY ID ASC, ID ASC LIMIT 30 OFFSET 0;";
        testObject.buildSelectQuery();
		assertEquals(expectedSQL,testObject.getSql());
    }

    @ParameterizedTest
    @EnumSource(TableColumns.Column.class)
    public void sortOrderTestSelectQuery(TableColumns.Column column)
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setPrimarySort(column);
        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject );
        String expectedSql = "SELECT * FROM table ORDER BY "+ column.dbLabel +" ASC, ID ASC LIMIT 30 OFFSET 0;";
        testObject.buildSelectQuery();
		assertEquals(expectedSql,testObject.getSql());
    }

    @Test
    public void severitiesSetTestValueTestSelectQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        HashSet<String> testHashSet = new HashSet<String>();
        testHashSet.add("test");
        testSearchObject.setSeverities(testHashSet);
        TableColumns.Column column = TableColumns.Column.SEVERITY;

        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject );
        String expectedSQL = "SELECT * FROM table WHERE (crashSeverity IN ('test')) ORDER BY ID ASC, ID ASC LIMIT 30 OFFSET 0;";
        testObject.buildSelectQuery();
		assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void regionsSetTestValueTestSelectQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        HashSet<String> testHashSet = new HashSet<String>();
        testHashSet.add("test");

        testSearchObject.setRegions(testHashSet);
        TableColumns.Column column = TableColumns.Column.REGION;

        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject );
        String expectedSQL = "SELECT * FROM table WHERE (region IN ('test')) ORDER BY ID ASC, ID ASC LIMIT 30 OFFSET 0;";
        testObject.buildSelectQuery();
		assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void weatherASetTestValueTestSelectQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        HashSet<String> testHashSet = new HashSet<String>();
        testHashSet.add("test");

        testSearchObject.setWeatherA(testHashSet);
        TableColumns.Column column = TableColumns.Column.WEATHER1;

        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject );
        String expectedSQL = "SELECT * FROM table WHERE (weatherA IN ('test')) ORDER BY ID ASC, ID ASC LIMIT 30 OFFSET 0;";
        testObject.buildSelectQuery();
		assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void weatherBSetTestValueTestSelectQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        HashSet<String> testHashSet = new HashSet<String>();
        testHashSet.add("test");

        testSearchObject.setWeatherB(testHashSet);
        TableColumns.Column column = TableColumns.Column.WEATHER2;

        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject );
        String expectedSQL = "SELECT * FROM table WHERE (weatherB IN ('test')) ORDER BY ID ASC, ID ASC LIMIT 30 OFFSET 0;";
        testObject.buildSelectQuery();
		assertEquals(expectedSQL,testObject.getSql());
    }


    @Test
    public void lightSetTestValueTestSelectQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        HashSet<String> testHashSet = new HashSet<String>();
        testHashSet.add("test");

        testSearchObject.setLight(testHashSet);
        TableColumns.Column column = TableColumns.Column.LIGHT;

        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject );
        String expectedSQL = "SELECT * FROM table WHERE (light IN ('test')) ORDER BY ID ASC, ID ASC LIMIT 30 OFFSET 0;";
        testObject.buildSelectQuery();
		assertEquals(expectedSQL,testObject.getSql());
    }


    @Test
    public void holidaysSetTestValueTestSelectQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        HashSet<String> testHashSet = new HashSet<String>();
        testHashSet.add("test");

        testSearchObject.setHolidays(testHashSet);
        TableColumns.Column column = TableColumns.Column.HOLIDAY;

        SQLQuerryObject testObject = new SQLQuerryObject( testSearchObject );
        String expectedSQL = "SELECT * FROM table WHERE (holiday IN ('test')) ORDER BY ID ASC, ID ASC LIMIT 30 OFFSET 0;";
        testObject.buildSelectQuery();
		assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void authoritiesSetTestValueTestSelectQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        HashSet<String> testHashSet = new HashSet<String>();
        testHashSet.add("test");

        testSearchObject.setLocalAuthorities(testHashSet);
        TableColumns.Column column = TableColumns.Column.TLA;

        SQLQuerryObject testObject = new SQLQuerryObject( testSearchObject );
        String expectedSQL = "SELECT * FROM table WHERE (tlaName IN ('test')) ORDER BY ID ASC, ID ASC LIMIT 30 OFFSET 0;";
        testObject.buildSelectQuery();
		assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void trafficControlsSetTestValueTestSelectQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        HashSet<String> testHashSet = new HashSet<String>();
        testHashSet.add("test");

        testSearchObject.setTrafficControls(testHashSet);
        TableColumns.Column column = TableColumns.Column.CONTROL;

        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject );
        String expectedSQL = "SELECT * FROM table WHERE (trafficControl IN ('test')) ORDER BY ID ASC, ID ASC LIMIT 30 OFFSET 0;";
        testObject.buildSelectQuery();
		assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void crashDirectionSetTestValueTestSelectQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        HashSet<String> testHashSet = new HashSet<String>();
        testHashSet.add("test");

        testSearchObject.setCrashDirections(testHashSet);
        TableColumns.Column column = TableColumns.Column.DIRECTION;

        SQLQuerryObject testObject = new SQLQuerryObject( testSearchObject );
        String expectedSQL = "SELECT * FROM table WHERE (crashDirection IN ('test')) ORDER BY ID ASC, ID ASC LIMIT 30 OFFSET 0;";
        testObject.buildSelectQuery();
		assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void rollDirectionSetTestValueTestSelectQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        HashSet<String> testHashSet = new HashSet<String>();
        testHashSet.add("test");

        testSearchObject.setVehicleDirections(testHashSet);
        TableColumns.Column column = TableColumns.Column.ROLEDIRECTION;

        SQLQuerryObject testObject = new SQLQuerryObject( testSearchObject );
        String expectedSQL = "SELECT * FROM table WHERE (directionRole IN ('test')) ORDER BY ID ASC, ID ASC LIMIT 30 OFFSET 0;";
        testObject.buildSelectQuery();
		assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void laneConfigsSetTestValueTestSelectQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        HashSet<String> testHashSet = new HashSet<String>();
        testHashSet.add("test");

        testSearchObject.setLaneConfigs(testHashSet);
        TableColumns.Column column = TableColumns.Column.ROADLANE;

        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject );
        String expectedSQL = "SELECT * FROM table WHERE (roadLane IN ('test')) ORDER BY ID ASC, ID ASC LIMIT 30 OFFSET 0;";
        testObject.buildSelectQuery();
		assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void roadSurfaceSetTestValueTestSelectQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        HashSet<String> testHashSet = new HashSet<String>();
        testHashSet.add("test");

        testSearchObject.setRoadSurfaces(testHashSet);
        TableColumns.Column column = TableColumns.Column.ROADSURFACE;

        SQLQuerryObject testObject = new SQLQuerryObject( testSearchObject );
        String expectedSQL = "SELECT * FROM table WHERE (roadSurface IN ('test')) ORDER BY ID ASC, ID ASC LIMIT 30 OFFSET 0;";
        testObject.buildSelectQuery();
		assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void correctEmptySearchCountQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject);
        testObject.buildCountQuery();
        assertEquals("SELECT Count(*) FROM table", testObject.getSql());

    }



    @Test
    public void yearIntegerFieldDoubleBoundsTestCountQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setStartYear(0);
        testSearchObject.setEndYear(1);
        TableColumns.Column column = TableColumns.Column.YEAR;
        SQLQuerryObject testObject = new SQLQuerryObject( testSearchObject );
        String expectedSQL = "SELECT Count(*) FROM table WHERE ";
        expectedSQL += "(" + column.dbLabel + " BETWEEN 0 AND 1)";

        testObject.buildCountQuery();
        assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void speedLimitIntegerFieldDoubleBoundsTestCountQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setStartSpeedLimit(0);
        testSearchObject.setEndSpeedLimit(1);
        TableColumns.Column column = TableColumns.Column.SPEEDLIMIT;
        SQLQuerryObject testObject = new SQLQuerryObject( testSearchObject );
        String expectedSQL = "SELECT Count(*) FROM table WHERE ";
        expectedSQL += "(" + column.dbLabel + " BETWEEN 0 AND 1)";

        testObject.buildCountQuery();
        assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void advSpeedIntegerFieldDoubleBoundsTestCountQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setStartAdvisedSpeed(0);
        testSearchObject.setEndAdvisedSpeed(1);
        TableColumns.Column column = TableColumns.Column.ADVSPEED;
        SQLQuerryObject testObject = new SQLQuerryObject( testSearchObject );
        String expectedSQL = "SELECT Count(*) FROM table WHERE ";
        expectedSQL += "(" + column.dbLabel + " BETWEEN 0 AND 1)";

        testObject.buildCountQuery();
        assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void tempSpeedIntegerFieldDoubleBoundsTestCountQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setTemporaryStartLimit(0);
        testSearchObject.setTemporaryEndLimit(1);
        TableColumns.Column column = TableColumns.Column.TEMPSPEED;
        SQLQuerryObject testObject = new SQLQuerryObject( testSearchObject );
        String expectedSQL = "SELECT Count(*) FROM table WHERE ";
        expectedSQL += "(" + column.dbLabel + " BETWEEN 0 AND 1)";

        testObject.buildCountQuery();
        assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void deathsIntegerFieldDoubleBoundsTestCountQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setStartDeaths(0);
        testSearchObject.setEndDeaths(1);
        TableColumns.Column column = TableColumns.Column.FATALCOUNT;
        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject );
        String expectedSQL = "SELECT Count(*) FROM table WHERE ";
        expectedSQL += "(" + column.dbLabel + " BETWEEN 0 AND 1)";

        testObject.buildCountQuery();
        assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void minorInjuryIntegerFieldDoubleBoundsTestCountQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setStartMinorInjuries(0);
        testSearchObject.setEndMinorInjuries(1);
        TableColumns.Column column = TableColumns.Column.MINORINJURY;
        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject );
        String expectedSQL = "SELECT Count(*) FROM table WHERE ";
        expectedSQL += "(" + column.dbLabel + " BETWEEN 0 AND 1)";

        testObject.buildCountQuery();
        assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void majorInjuryIntegerFieldDoubleBoundsTestCountQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setStartMajorInjuries(0);
        testSearchObject.setEndMajorInjuries(1);
        TableColumns.Column column = TableColumns.Column.SERIOUSINJURY;
        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject );
        String expectedSQL = "SELECT Count(*) FROM table WHERE ";
        expectedSQL += "(" + column.dbLabel + " BETWEEN 0 AND 1)";

        testObject.buildCountQuery();
        assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void lanesIntegerFieldDoubleBoundsTestCountQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setStartLanes(0);
        testSearchObject.setEndLanes(1);
        TableColumns.Column column = TableColumns.Column.NUMLANES;
        SQLQuerryObject testObject = new SQLQuerryObject( testSearchObject );
        String expectedSQL = "SELECT Count(*) FROM table WHERE ";
        expectedSQL += "(" + column.dbLabel + " BETWEEN 0 AND 1)";

        testObject.buildCountQuery();
        assertEquals(expectedSQL,testObject.getSql());
    }


    @Test
    public void yearIntegerFieldLowerBoundsTestCountQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setStartYear(0);
        TableColumns.Column column = TableColumns.Column.YEAR;
        SQLQuerryObject testObject = new SQLQuerryObject( testSearchObject );
        String expectedSQL = "SELECT Count(*) FROM table WHERE ";
        expectedSQL += column.dbLabel + " >= 0";

        testObject.buildCountQuery();
        assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void speedLimitIntegerFieldLowerBoundsTestCountQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setStartSpeedLimit(0);
        TableColumns.Column column = TableColumns.Column.SPEEDLIMIT;
        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject );
        String expectedSQL = "SELECT Count(*) FROM table WHERE ";
        expectedSQL += column.dbLabel + " >= 0";

        testObject.buildCountQuery();
        assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void advSpeedIntegerFieldLowerBoundsTestCountQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setStartAdvisedSpeed(0);
        TableColumns.Column column = TableColumns.Column.ADVSPEED;
        SQLQuerryObject testObject = new SQLQuerryObject( testSearchObject );
        String expectedSQL = "SELECT Count(*) FROM table WHERE ";
        expectedSQL += column.dbLabel + " >= 0";

        testObject.buildCountQuery();
        assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void tempSpeedIntegerFieldLowerBoundsTestCountQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setTemporaryStartLimit(0);
        TableColumns.Column column = TableColumns.Column.TEMPSPEED;
        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject );
        String expectedSQL = "SELECT Count(*) FROM table WHERE ";
        expectedSQL += column.dbLabel + " >= 0";

        testObject.buildCountQuery();
        assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void deathsIntegerFieldLowerBoundsTestCountQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setStartDeaths(0);
        TableColumns.Column column = TableColumns.Column.FATALCOUNT;
        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject );
        String expectedSQL = "SELECT Count(*) FROM table WHERE ";
        expectedSQL += column.dbLabel + " >= 0";

        testObject.buildCountQuery();
        assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void minorInjuryIntegerFieldLowerBoundsTestCountQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setStartMinorInjuries(0);
        TableColumns.Column column = TableColumns.Column.MINORINJURY;
        SQLQuerryObject testObject = new SQLQuerryObject( testSearchObject );
        String expectedSQL = "SELECT Count(*) FROM table WHERE ";
        expectedSQL += column.dbLabel + " >= 0";

        testObject.buildCountQuery();
        assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void majorInjuryIntegerFieldLowerBoundsTestCountQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setStartMajorInjuries(0);
        TableColumns.Column column = TableColumns.Column.SERIOUSINJURY;
        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject );
        String expectedSQL = "SELECT Count(*) FROM table WHERE ";
        expectedSQL += column.dbLabel + " >= 0";

        testObject.buildCountQuery();
        assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void lanesIntegerFieldLowerBoundsTestCountQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setStartLanes(0);
        TableColumns.Column column = TableColumns.Column.NUMLANES;
        SQLQuerryObject testObject = new SQLQuerryObject( testSearchObject );
        String expectedSQL = "SELECT Count(*) FROM table WHERE ";
        expectedSQL += column.dbLabel + " >= 0";

        testObject.buildCountQuery();
        assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void yearIntegerFieldUpperBoundsTestCountQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setEndYear(0);
        TableColumns.Column column = TableColumns.Column.YEAR;
        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject );
        String expectedSQL = "SELECT Count(*) FROM table WHERE ";
        expectedSQL += column.dbLabel + " <= 0";

        testObject.buildCountQuery();
        assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void speedLimitIntegerFieldUpperBoundsTestCountQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setEndSpeedLimit(0);
        TableColumns.Column column = TableColumns.Column.SPEEDLIMIT;
        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject );
        String expectedSQL = "SELECT Count(*) FROM table WHERE ";
        expectedSQL += column.dbLabel + " <= 0";

        testObject.buildCountQuery();
        assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void advSpeedIntegerFieldUpperBoundsTestCountQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setEndAdvisedSpeed(0);
        TableColumns.Column column = TableColumns.Column.ADVSPEED;
        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject );
        String expectedSQL = "SELECT Count(*) FROM table WHERE ";
        expectedSQL += column.dbLabel + " <= 0";

        testObject.buildCountQuery();
        assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void tempSpeedIntegerFieldUpperBoundsTestCountQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setTemporaryEndLimit(0);
        TableColumns.Column column = TableColumns.Column.TEMPSPEED;
        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject );
        String expectedSQL = "SELECT Count(*) FROM table WHERE ";
        expectedSQL += column.dbLabel + " <= 0";

        testObject.buildCountQuery();
        assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void deathsIntegerFieldUpperBoundsTestCountQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setEndDeaths(0);
        TableColumns.Column column = TableColumns.Column.FATALCOUNT;
        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject );
        String expectedSQL = "SELECT Count(*) FROM table WHERE ";
        expectedSQL += column.dbLabel + " <= 0";

        testObject.buildCountQuery();
        assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void minorInjuryIntegerFieldUpperBoundsTestCountQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setEndMinorInjuries(0);
        TableColumns.Column column = TableColumns.Column.MINORINJURY;
        SQLQuerryObject testObject = new SQLQuerryObject( testSearchObject );
        String expectedSQL = "SELECT Count(*) FROM table WHERE ";
        expectedSQL += column.dbLabel + " <= 0";

        testObject.buildCountQuery();
        assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void majorInjuryIntegerFieldUpperBoundsTestCountQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setEndMajorInjuries(0);
        TableColumns.Column column = TableColumns.Column.SERIOUSINJURY;
        SQLQuerryObject testObject = new SQLQuerryObject( testSearchObject );
        String expectedSQL = "SELECT Count(*) FROM table WHERE ";
        expectedSQL += column.dbLabel + " <= 0";

        testObject.buildCountQuery();
        assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void lanesIntegerFieldUpperBoundsTestCountQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        testSearchObject.setEndLanes(0);
        TableColumns.Column column = TableColumns.Column.NUMLANES;
        SQLQuerryObject testObject = new SQLQuerryObject( testSearchObject );
        String expectedSQL = "SELECT Count(*) FROM table WHERE ";
        expectedSQL += column.dbLabel + " <= 0";

        testObject.buildCountQuery();
        assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void severitiesSetTestValueTestCountQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        HashSet<String> testHashSet = new HashSet<String>();
        testHashSet.add("test");
        testSearchObject.setSeverities(testHashSet);
        TableColumns.Column column = TableColumns.Column.SEVERITY;

        SQLQuerryObject testObject = new SQLQuerryObject( testSearchObject );
        String expectedSQL = "SELECT Count(*) FROM table WHERE (crashSeverity IN ('test'))";

        testObject.buildCountQuery();
        assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void regionsSetTestValueTestCountQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        HashSet<String> testHashSet = new HashSet<String>();
        testHashSet.add("test");

        testSearchObject.setRegions(testHashSet);
        TableColumns.Column column = TableColumns.Column.REGION;

        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject );
        String expectedSQL = "SELECT Count(*) FROM table WHERE (region IN ('test'))";

        testObject.buildCountQuery();
        assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void weatherASetTestValueTestCountQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        HashSet<String> testHashSet = new HashSet<String>();
        testHashSet.add("test");

        testSearchObject.setWeatherA(testHashSet);
        TableColumns.Column column = TableColumns.Column.WEATHER1;

        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject );
        String expectedSQL = "SELECT Count(*) FROM table WHERE (weatherA IN ('test'))";

        testObject.buildCountQuery();
        assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void weatherBSetTestValueTestCountQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        HashSet<String> testHashSet = new HashSet<String>();
        testHashSet.add("test");

        testSearchObject.setWeatherB(testHashSet);
        TableColumns.Column column = TableColumns.Column.WEATHER2;

        SQLQuerryObject testObject = new SQLQuerryObject( testSearchObject );
        String expectedSQL = "SELECT Count(*) FROM table WHERE (weatherB IN ('test'))";

        testObject.buildCountQuery();
        assertEquals(expectedSQL,testObject.getSql());
    }


    @Test
    public void lightSetTestValueTestCountQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        HashSet<String> testHashSet = new HashSet<String>();
        testHashSet.add("test");

        testSearchObject.setLight(testHashSet);
        TableColumns.Column column = TableColumns.Column.LIGHT;

        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject );
        String expectedSQL = "SELECT Count(*) FROM table WHERE (light IN ('test'))";
        testObject.buildCountQuery();
        assertEquals(expectedSQL,testObject.getSql());
    }


    @Test
    public void holidaysSetTestValueTestCountQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        HashSet<String> testHashSet = new HashSet<String>();
        testHashSet.add("test");

        testSearchObject.setHolidays(testHashSet);
        TableColumns.Column column = TableColumns.Column.HOLIDAY;

        SQLQuerryObject testObject = new SQLQuerryObject( testSearchObject );
        String expectedSQL = "SELECT Count(*) FROM table WHERE (holiday IN ('test'))";
        testObject.buildCountQuery();
        assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void authoritiesSetTestValueTestCountQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        HashSet<String> testHashSet = new HashSet<String>();
        testHashSet.add("test");

        testSearchObject.setLocalAuthorities(testHashSet);
        TableColumns.Column column = TableColumns.Column.TLA;

        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject );
        String expectedSQL = "SELECT Count(*) FROM table WHERE (tlaName IN ('test'))";
        testObject.buildCountQuery();
        assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void trafficControlsSetTestValueTestCountQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        HashSet<String> testHashSet = new HashSet<String>();
        testHashSet.add("test");

        testSearchObject.setTrafficControls(testHashSet);
        TableColumns.Column column = TableColumns.Column.CONTROL;

        SQLQuerryObject testObject = new SQLQuerryObject( testSearchObject );
        String expectedSQL = "SELECT Count(*) FROM table WHERE (trafficControl IN ('test'))";
        testObject.buildCountQuery();
        assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void crashDirectionSetTestValueTestCountQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        HashSet<String> testHashSet = new HashSet<String>();
        testHashSet.add("test");

        testSearchObject.setCrashDirections(testHashSet);
        TableColumns.Column column = TableColumns.Column.DIRECTION;

        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject );
        String expectedSQL = "SELECT Count(*) FROM table WHERE (crashDirection IN ('test'))";
        testObject.buildCountQuery();
        assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void rollDirectionSetTestValueTestCountQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        HashSet<String> testHashSet = new HashSet<String>();
        testHashSet.add("test");

        testSearchObject.setVehicleDirections(testHashSet);
        TableColumns.Column column = TableColumns.Column.ROLEDIRECTION;

        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject );
        String expectedSQL = "SELECT Count(*) FROM table WHERE (directionRole IN ('test'))";
        testObject.buildCountQuery();
        assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void laneConfigsSetTestValueTestCountQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        HashSet<String> testHashSet = new HashSet<String>();
        testHashSet.add("test");

        testSearchObject.setLaneConfigs(testHashSet);
        TableColumns.Column column = TableColumns.Column.ROADLANE;

        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject );
        String expectedSQL = "SELECT Count(*) FROM table WHERE (roadLane IN ('test'))";
        testObject.buildCountQuery();
        assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void roadSurfaceSetTestValueTestCountQuery()
    {
        Search testSearchObject = new Search();
        testSearchObject.setTable("table");
        HashSet<String> testHashSet = new HashSet<String>();
        testHashSet.add("test");

        testSearchObject.setRoadSurfaces(testHashSet);
        TableColumns.Column column = TableColumns.Column.ROADSURFACE;

        SQLQuerryObject testObject = new SQLQuerryObject(testSearchObject );
        String expectedSQL = "SELECT Count(*) FROM table WHERE (roadSurface IN ('test'))";
        testObject.buildCountQuery();
        assertEquals(expectedSQL,testObject.getSql());
    }

    @Test
    public void keywordTestSearchQuery() {
        String expectedSQL = "SELECT * FROM table WHERE (((ID == 'test') OR (crashDirection like '%test%') OR (financialYear like '%test%') OR (crashLocationA like '%test%') OR (crashLocationB like '%test%') OR (sideRoad like '%test%') OR (crashSeverity like '%test%') OR (crashSHDescription like '%test%') OR (directionRole like '%test%') OR (flatHill like '%test%') OR (holiday like '%test%') OR (intersection like '%test%') OR (light like '%test%') OR (region like '%test%') OR(roadCharacter like '%test%') OR (roadLane like '%test%') OR (roadSurface like '%test%') OR (streetLight like '%test%') OR (tlaName like '%test%') OR (trafficControl like '%test%') OR (urban like '%test%') OR (weatherA like '%test%') OR (weatherB like '%test%')) )  ORDER BY ID ASC, ID ASC LIMIT 30 OFFSET 0;";

        Search testSearchObject = new Search();
        testSearchObject.setTable("table");

        testSearchObject.setKeywords("test");
        TableColumns.Column column = TableColumns.Column.ROADSURFACE;
        SQLQuerryObject testObject = new SQLQuerryObject( testSearchObject);

        testObject.buildSelectQuery();
        assertEquals(expectedSQL, testObject.getSql());
    }








}
