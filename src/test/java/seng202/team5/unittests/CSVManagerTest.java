package seng202.team5.unittests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import seng202.team5.database.CSVManager;
import seng202.team5.database.CrashFromFile;

import java.io.File;
import java.util.ArrayList;

public class CSVManagerTest {
    private CSVManager csvManager = new CSVManager();

    // Test reading a CSV
    @Test
    public void readFileTest() {
        File file = new File("src/test/resources/crash_data_1_crash.csv");
        ArrayList<CrashFromFile> crashes = csvManager.readFile(file);
        assertEquals(1, crashes.size());
        assertEquals(crashes.get(0).getObjectID(), 80);
    }
}
