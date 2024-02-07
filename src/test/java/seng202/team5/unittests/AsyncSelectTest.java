package seng202.team5.unittests;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
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
import seng202.team5.models.*;

import java.io.File;
import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AsyncSelectTest implements ResultSetListener {
    private static CrashManager crashManager = new CrashManager();
    private static CrashDAO crashDAO = new CrashDAO();
    private static CSVManager csvManager = new CSVManager();

    private ArrayList<CrashFromDatabase> testResultList = new ArrayList<>();


    @Override
    public void RowsUpdate(ArrayList<CrashFromDatabase> newValues, Search searchObject) {
        testResultList = newValues;
    }

    @BeforeAll
    public static void addTestTable()
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

    @Test
    public void testDBWorks()
    {
        assertEquals(80,crashDAO.getOne(80,"testTable").getObjectID());
    }


    @Test
    public void testAsyncWorks()
    {
        String sqlString = "SELECT * FROM testTable WHERE id=80";
        AsynchronousSelectQuery query = crashDAO.queryAll(new Search(),sqlString, this);
        System.out.println("waiting for async query");
        try {
            query.join();
        } catch (InterruptedException err)
        {
            err.printStackTrace();
        }
        assertEquals(80,testResultList.get(0).getObjectID());
    }

    @Test
    public void testAsyncInvalid()
    {
        String sqlString = "SELECT * FROM testTable WHERE nothing works";
        AsynchronousSelectQuery query = crashDAO.queryAll(new Search(),sqlString, this);
        System.out.println("waiting for async query");
        try {
            query.join();
        } catch (InterruptedException err)
        {
            err.printStackTrace();
        }
        assertEquals(new ArrayList<CrashFromDatabase>(),testResultList);
    }

    @Test
    public void testCompleteRecord()
    {
        String sqlString = "SELECT * FROM testTable WHERE id=240";
        AsynchronousSelectQuery query = crashDAO.queryAll(new Search(),sqlString, this);
        System.out.println("waiting for async query");
        try {
            query.join();
        } catch (InterruptedException err)
        {
            err.printStackTrace();
        }
        assertEquals(240,testResultList.get(0).getObjectID());
        assertEquals("2001",testResultList.get(0).getCrashYear());
        assertEquals("West",testResultList.get(0).getDirectionRoleDescription());



    }


}
