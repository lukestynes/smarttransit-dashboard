package seng202.team5.unittests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.Test;

import seng202.team5.database.CrashFromDatabase;
import seng202.team5.database.CrashFromFile;

public class CrashFromDatabaseTest {

    private CrashFromDatabase testCrash = new CrashFromDatabase(
        1,
        "50",
        "0",
        "0",
        "0",
        "0",
        "0",
        "North",
        "2000",
        "Street St",
        "Road Rd",
        "0",
        "Fatal",
        "No",
        "0",
        "0",
        "North",
        "0",
        "0",
        "0",
        "0",
        "0",
        "null",
        "0",
        "0",
        "0",
        "Dark",
        "0",
        "0",
        "0",
        "1",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "Waikato Region",
        "Nil",
        "Off road",
        "Sealed",
        "0",
        "0",
        "0",
        "0",
        "50",
        "0",
        "0",
        "0",
        "0",
        "0",
        "Auckland",
        "Nil",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "Light rain",
        "Null",
        -36.88774896677,
        174.750689551825
    );

    @Test
    public void createCrashFromDatabaseTest() {
        assertNotNull(testCrash);
    }

    @Test public void createCrashFromDatabaseFromCrashFromFileTest() {
        CrashFromFile crashFromFile = new CrashFromFile(
            1,
            50,
            0,
            0,
            0,
            0,
            0,
            "North",
            "2000",
            "Street St",
            "Road Rd",
            "null",
            "Fatal",
            "No",
            2000,
            0,
            "North",
            0,
            0,
            0,
            "0",
            0,
            "null",
            0,
            "",
            0,
            "Dark",
            0,
            0,
            0,
            1,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            "Waikato Region",
            "Nil",
            "Off road",
            "Sealed",
            0,
            0,
            0,
            0,
            50,
            0,
            "0",
            0,
            0,
            0,
            "Auckland",
            "Nil",
            0,
            0,
            0,
            0,
            0,
            0,
            "0",
            0,
            0,
            0,
            "Light rain",
            "Null",
            -36.88774896677,
            174.750689551825
        );

        CrashFromDatabase crashFromDatabase = new CrashFromDatabase(crashFromFile);
        assertEquals(crashFromDatabase.getAdvisorySpeed(), testCrash.getAdvisorySpeed());
        assertEquals(crashFromDatabase.getWeatherB(), testCrash.getWeatherB());
    }

    @Test
    public void enhanceReadabilityTest() {
        testCrash.enhancedUserReadability();
        // Test converting a 0 year to a -
        assertEquals("-", testCrash.getCrashYear());

        // Test converting a null to a -
        assertEquals("-", testCrash.getTemporarySpeedLimit());

        // Test converting a nil to a -
        assertEquals("-", testCrash.getRoadCharacter());

        // Test that nullToNoDataFilter is working
        assertEquals("-", testCrash.getWeatherB());
    }
}
