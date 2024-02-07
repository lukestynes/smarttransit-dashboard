package seng202.team5.unittests;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.shadow.com.univocity.parsers.csv.Csv;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import seng202.team5.App;
import seng202.team5.business.CrashManager;
import seng202.team5.database.*;
import seng202.team5.exceptions.QueryNotBuiltException;
import seng202.team5.gui.DBViewController;
import seng202.team5.gui.LoadingBarController;
import seng202.team5.gui.ResultSetListener;
import seng202.team5.gui.TableViewController;
import seng202.team5.models.*;

import java.io.File;
import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AsyncChartTest {

    private static CrashDAO crashDAO = new CrashDAO();
    private static CSVManager csvManager = new CSVManager();
    private static final Logger log = LogManager.getLogger(AsyncChartTest.class);


    @BeforeAll
    public static void addTestTable() {
        LoadingBarController.testMode();

        log.info("clearing DB");
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
    public static void clearTestTable() {
        crashDAO.dropTable("testTable");
    }

    @Test
    public void testDBWorks() {
        assertEquals(80, crashDAO.getOne(80, "testTable").getObjectID());
    }

    @Test
    public void testChartQueryCat1() {
        String sqlString = "SELECT crashSeverity, Count(*) from testTable group by crashSeverity order by crashSeverity;";
        assertEquals(95, crashDAO.catChartQueryAll(sqlString,GenericChart.Categories.SEVERITY).get(0).count);
    }
    @Test
    public void testChartQueryCat2() {
        String sqlString = "SELECT crashSeverity, Count(*) from testTable group by crashSeverity order by crashSeverity;";
        assertEquals(2392, crashDAO.catChartQueryAll(sqlString,GenericChart.Categories.SEVERITY).get(1).count);
    }
    @Test
    public void testChartQueryCat3() {
        String sqlString = "SELECT crashSeverity, Count(*) from testTable group by crashSeverity order by crashSeverity;";
        assertEquals(7164, crashDAO.catChartQueryAll(sqlString,GenericChart.Categories.SEVERITY).get(2).count);
    }
    @Test
    public void testChartQueryCat4() {
        String sqlString = "SELECT crashSeverity, Count(*) from testTable group by crashSeverity order by crashSeverity;";
        assertEquals(604, crashDAO.catChartQueryAll(sqlString,GenericChart.Categories.SEVERITY).get(3).count);
    }
}