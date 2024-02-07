package seng202.team5.cucumber.StepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.eo.Do;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import seng202.team5.business.CrashManager;
import seng202.team5.database.*;
import seng202.team5.gui.LoadingBarController;
import seng202.team5.gui.ResultNumberListener;
import seng202.team5.models.Search;
import seng202.team5.models.TableColumns;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AsyncCountDefs implements ResultNumberListener {
    private static CrashManager crashManager = new CrashManager();
    private static CrashDAO crashDAO = new CrashDAO();
    private static CSVManager csvManager = new CSVManager();
    private Search search;


    private Integer testResultNumber = 0;


    @Override
    public void updateNumRow(Integer newNum, Search searchObject) {
        testResultNumber = newNum;
    }
    public static void initDatabse()
    {
        LoadingBarController.testMode();

        System.out.println("clearing DB");
        crashDAO.dropTable("testTable");
        crashDAO.createTable("testTable");
        System.out.println("Loading File");

        File testFile = new File("src/test/java/seng202/team5/unittests/query_test_data.csv");
        ArrayList<CrashFromFile> testRecords = csvManager.readFile(testFile);
        crashDAO.addBatch(testRecords, "testTable", "testTable", new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {

            }
        }, new Consumer<String>() {
            @Override
            public void accept(String s) {

            }
        });
    }
    @AfterAll
    public static void clearTestTable()
    {
        crashDAO.dropTable("testTable");
    }

    @Given("I have loaded the query_test_data.csv file")
    public void loadQueryTestDataCSV(){initDatabse();}

    @When("I query for the number of results returned by an empty search")
    public void queryForNumberOfResultsEmptySearch() {
        String sqlString = "SELECT Count(*) FROM testTable";
        ArrayList<ResultNumberListener> callingClasses = new ArrayList<>();
        callingClasses.add(this);
        AsynchronousCountQuery query = crashDAO.queryAllCount(sqlString, callingClasses,new Search());
        try {
            query.join();
        } catch (InterruptedException err)
        {
            err.printStackTrace();
        }
    }
//    @Then("I should find that my query returns ${int} results")
//    public void verifyQueryReturnsSpecificExpectedLines(int expectedLines) {
//        assertEquals(expectedLines,testResultNumber);
//    }

    @When("I query for the number of results returned by a search where buses involved")
    public void queryForNumberOfResultsWhereBusesInvolved() {

        Search testSearchObject = new Search();
        HashSet<String> testParticipant = new HashSet<>();
        testParticipant.add("bus");
        testSearchObject.setParticipants(testParticipant);
        testSearchObject.setTable("testTable");
        SQLQuerryObject testQueryObject = new SQLQuerryObject(testSearchObject);
        testQueryObject.buildCountQuery();
        System.out.println(testQueryObject.getSql());
        String sqlString = testQueryObject.getSql();

        ArrayList<ResultNumberListener> callingClasses = new ArrayList<>();
        callingClasses.add(this);
        AsynchronousCountQuery query = crashDAO.queryAllCount(sqlString, callingClasses,new Search());
        try {
            query.join();
        } catch (InterruptedException err)
        {
            err.printStackTrace();
        }
    }

    @Then("I should find that my query returns ${int} results")
    public void verifyQueryResultsCount(int expectedResult) {
        assertEquals(expectedResult,testResultNumber);
    }
}


