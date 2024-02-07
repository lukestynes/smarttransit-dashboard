package seng202.team5.unittests;

import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.*;

import seng202.team5.business.CrashManager;
import seng202.team5.database.*;
import seng202.team5.gui.DBViewController;
import seng202.team5.gui.LoadingBarController;

import seng202.team5.models.GenericChart;
import seng202.team5.models.MapPin;
import seng202.team5.models.Search;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CrashManagerTest {
  private static CrashManager testCrashManager;
  private static DBViewController testDbViewController;
  private static File testCSVFile;
  private static CSVManager csvManager = new CSVManager();

  private static CrashDAO crashDAO = new CrashDAO();

  @BeforeAll
  public static void addTestTable() {
    LoadingBarController.testMode();

    System.out.println("clearing DB");
    crashDAO.dropTable("testTable");
    crashDAO.createTable("testTable");
    System.out.println("Loading File");

    File testFile = new File("src/test/java/seng202/team5/unittests/query_test_data.csv");

    ArrayList<CrashFromFile> testRecords = csvManager.readFile(testFile);
    crashDAO.addBatch(
        testRecords,
        "testTable",
        "testTable",
        new Consumer<Integer>() {
          @Override
          public void accept(Integer integer) {}
        },
        new Consumer<String>() {
          @Override
          public void accept(String s) {}
        });
    testCrashManager = new CrashManager(crashDAO);
  }

  @AfterAll
  public static void clearTestTable() {
    crashDAO.dropTable("testTable");
  }

  @Test
  @Order(1)
  public void testDBWorks() {
    assertEquals(80, crashDAO.getOne(80, "testTable").getObjectID());
  }

  @Test
  @Order(2)
  public void testGetOne() {
    CrashFromDatabase testCrash = testCrashManager.getOne(80, "testTable");
    assertEquals(80, testCrash.getObjectID());
  }

  @Test
  @Order(3)
  public void testGetAll() {
    ArrayList<CrashFromFile> testList = testCrashManager.getAll("testTable");
    assertEquals(10255, testList.size());
  }

  @Test
  @Order(4)
  public void runCatChartQuery() {
    Search testSearch = new Search();
    testSearch.setTable("testTable");
    testSearch.setKeywords(null);

    ArrayList<CrashCount> testCrashCount =
        testCrashManager.runCatChartQuery(testSearch, GenericChart.Categories.valueOf("SEVERITY"));
    assertEquals("Fatal Crash", testCrashCount.get(0).category);
    assertEquals(95, testCrashCount.get(0).count);
  }

  @Test
  @Order(5)
  public void testRunChartQuery() {
    Search testSearch = new Search();
    testSearch.setTable("testTable");
    testSearch.setKeywords(null);

    ArrayList<CrashCount> testCrashCount =
        testCrashManager.runChartQuery(testSearch, GenericChart.Continuous.valueOf("YEAR"));
    assertEquals("2000", testCrashCount.get(0).category);
    assertEquals(399, testCrashCount.get(0).count);
  }

  @Test
  @Order(6)
  public void runDoubleChartQuery() {
    Search testSearch = new Search();
    testSearch.setTable("testTable");
    testSearch.setKeywords(null);

    ArrayList<CrashCount> testCrashCount =
        testCrashManager.runDoubleChartQuery(
            testSearch,
            GenericChart.Continuous.valueOf("YEAR"),
            GenericChart.Categories.valueOf("SEVERITY"),
            "Fatal Crash");
    assertEquals("2000", testCrashCount.get(0).category);
    assertEquals(5, testCrashCount.get(0).count);
  }

  @Test
  @Order(7)
  public void runMapQueryTest() {
    Search testSearch = new Search();
    testSearch.setTable("testTable");
    testSearch.setKeywords(null);
    testSearch.setLat(-39.0630101313493);
    testSearch.setLng(177.115400323368);
    testSearch.setRadius(1);

    ArrayList<MapPinDatapoint> testPins = testCrashManager.runMapQuery(testSearch);
    assertEquals(560, testPins.get(0).getObjectID());
    assertEquals(2001, testPins.get(0).getCrashYear());
  }

  @Test
  @Order(8)
  public void runPinsInBoxQueryTest() {
    Search testSearch = new Search();
    testSearch.setTable("testTable");
    testSearch.setKeywords(null);

    ArrayList<MapPin> testInner = new ArrayList<>();
    ArrayList<ArrayList<MapPin>> testAllPolygons = new ArrayList<>();

    MapPin c1 = new MapPin(-43.852541840414, -176.646673733175, "");
    MapPin c2 = new MapPin(-44.052541840414, -176.646673733175, "");
    MapPin c3 = new MapPin(-44.052541840414, -176.446673733175, "");
    MapPin c4 = new MapPin(-43.852541840414, -176.446673733175, "");
    MapPin max =
        new MapPin(
            Math.max(-44.152541840414, -43.752541840414),
            Math.max(-176.346673733175, -176.746673733175),
            "");
    MapPin min =
        new MapPin(
            Math.min(-44.152541840414, -43.752541840414),
            Math.min(-176.346673733175, -176.746673733175),
            "");

    testInner.add(c1);
    testInner.add(c2);
    testInner.add(c3);
    testInner.add(c4);

    testAllPolygons.add(testInner);
    ArrayList<MapPinDatapoint> testCrashesOnRoute =
        testCrashManager.runPinsInBoxQuery(testSearch, testAllPolygons, max, min);
    assertEquals(3, testCrashesOnRoute.size());
    assertEquals(126951, testCrashesOnRoute.get(0).getObjectID());
  }

  @Test
  @Order(9)
  public void getNumResultsTest() {
    Integer testSize = testCrashManager.getNumResults("testTable");
    assertEquals(10255, testSize);
  }

  @Test
  @Order(10)
  public void checkHeadersTest() throws IOException, CsvException {
    File testFile = new File("src/test/java/seng202/team5/unittests/query_test_data.csv");
    assertTrue(testCrashManager.checkHeaders(testFile));
  }

  @Test
  @Order(11)
  public void dropTableTest() {
    testCrashManager.dropTable("testTable");
    assertFalse(testCrashManager.getTableNames().containsValue("testTable"));
    testCrashManager = new CrashManager(crashDAO);
  }
}
