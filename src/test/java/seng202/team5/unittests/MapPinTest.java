package seng202.team5.unittests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import seng202.team5.models.MapPin;

class MapPinTest {

    @Test
    void testGetters() {
        MapPin pin = new MapPin(1, -1, "Test Pin");

        assertEquals(1, pin.getLat()); // Check if latitude is set correctly
        assertEquals(-1, pin.getLng()); // Check if longitude is set correctly
        assertEquals("Test Pin", pin.getName()); // Check if name is set correctly
    }
}