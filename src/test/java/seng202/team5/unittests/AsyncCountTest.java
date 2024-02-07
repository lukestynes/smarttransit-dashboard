package seng202.team5.unittests;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import seng202.team5.business.CrashManager;
import seng202.team5.database.*;
import seng202.team5.gui.LoadingBarController;
import seng202.team5.gui.ResultNumberListener;
import seng202.team5.models.*;

import java.io.File;
import java.util.*;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

public class AsyncCountTest implements ResultNumberListener {
    private static CrashManager crashManager = new CrashManager();
    private static CrashDAO crashDAO = new CrashDAO();
    private static CSVManager csvManager = new CSVManager();

    private Integer testResultNumber = 0;


    @Override
    public void updateNumRow(Integer newNum, Search searchObject) {
        testResultNumber = newNum;
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
    public void testDBSizeCorrect()
    {
        String sqlString = "SELECT Count(*) FROM testTable";
        ArrayList<ResultNumberListener> callingClasses = new ArrayList<>();
        callingClasses.add(this);
        AsynchronousCountQuery query = crashDAO.queryAllCount(sqlString, callingClasses,new Search());
        System.out.println("waiting for async query");
        try {
            query.join();
        } catch (InterruptedException err)
        {
            err.printStackTrace();
        }
        assertEquals(10255,testResultNumber);
    }

    @Test
    public void testGetOneSizeCorrect()
    {
        String sqlString = "SELECT Count(*) FROM testTable WHERE id=80";
        ArrayList<ResultNumberListener> callingClasses = new ArrayList<>();
        callingClasses.add(this);
        AsynchronousCountQuery query = crashDAO.queryAllCount(sqlString, callingClasses,new Search());
        System.out.println("waiting for async query");
        try {
            query.join();
        } catch (InterruptedException err)
        {
            err.printStackTrace();
        }
        assertEquals(1,testResultNumber);
    }

    @Test
    public void testGetNoneSizeCorrect()
    {
        String sqlString = "SELECT Count(*) FROM testTable WHERE id=50";
        ArrayList<ResultNumberListener> callingClasses = new ArrayList<>();
        callingClasses.add(this);
        AsynchronousCountQuery query = crashDAO.queryAllCount(sqlString, callingClasses,new Search());
        System.out.println("waiting for async query");
        try {
            query.join();
        } catch (InterruptedException err)
        {
            err.printStackTrace();
        }
        assertEquals(0,testResultNumber);
    }

    @Test
    public void testErrorReturnCorrect()
    {
        String sqlString = "SELECT Count(*) FROM testTable WHERE";
        ArrayList<ResultNumberListener> callingClasses = new ArrayList<>();
        callingClasses.add(this);
        AsynchronousCountQuery query = crashDAO.queryAllCount(sqlString, callingClasses,new Search());
        System.out.println("waiting for async query");
        try {
            query.join();
        } catch (InterruptedException err)
        {
            err.printStackTrace();
        }
        assertEquals(0,testResultNumber);
    }


}
