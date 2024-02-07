package seng202.team5.unittests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import seng202.team5.models.MapPin;
import seng202.team5.models.Route;

class RouteTest {

    @Test
    void testToJSONArray() {
        MapPin pin1 = new MapPin(1, -1, "TestPoint 1");
        MapPin pin2 = new MapPin(2, -2, "TestPoint 2");
        MapPin pin3 = new MapPin(3, -3, "TestPoint 3");

        Route route = new Route(pin1, pin2, pin3);

        String expectedJson = "[{\"lat\": 1.000000, \"lng\": -1.000000}, " +
                "{\"lat\": 2.000000, \"lng\": -2.000000}, " +
                "{\"lat\": 3.000000, \"lng\": -3.000000}]";

        assertEquals(expectedJson, route.toJSONArray());
    }
}