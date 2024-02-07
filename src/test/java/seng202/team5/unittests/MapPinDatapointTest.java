package seng202.team5.unittests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import seng202.team5.database.MapPinDatapoint;
import seng202.team5.models.MapPin;

class MapPinDatapointTest {

    @Test
    void testConstructor()
    {
        MapPinDatapoint testObject = new MapPinDatapoint(12,0.1,0.2,"testSeverity", 1989);
        assertEquals(12, testObject.getObjectID());
        assertEquals(0.1, testObject.getLat());
        assertEquals(0.2, testObject.getLng());
        assertEquals("testSeverity", testObject.getCrashSeverity());
        assertEquals(1989, testObject.getCrashYear());

    }

    @Test
    void testObjectId()
    {
        MapPinDatapoint testObject = new MapPinDatapoint(12,0.1,0.2,"testSeverity", 1989);
        testObject.setObjectID(15);
        assertEquals(15, testObject.getObjectID());

    }

    @Test
    void testLat()
    {
        MapPinDatapoint testObject = new MapPinDatapoint(12,0.1,0.2,"testSeverity", 1989);
        testObject.setLat(1.1);
        assertEquals(1.1, testObject.getLat());


    }

    @Test
    void testLng()
    {
        MapPinDatapoint testObject = new MapPinDatapoint(12,0.1,0.2,"testSeverity", 1989);
        testObject.setLng(1.2);
        assertEquals(1.2, testObject.getLng());
    }

    @Test
    void testSeverity()
    {
        MapPinDatapoint testObject = new MapPinDatapoint(12,0.1,0.2,"testSeverity", 1989);
        testObject.setCrashSeverity("newTestSeverity");
        assertEquals("newTestSeverity", testObject.getCrashSeverity());


    }

    @Test
    void testYear()
    {
        MapPinDatapoint testObject = new MapPinDatapoint(12,0.1,0.2,"testSeverity", 1989);
        testObject.setCrashYear(2030);
        assertEquals(2030, testObject.getCrashYear());

    }
}