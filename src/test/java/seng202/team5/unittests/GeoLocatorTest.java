package seng202.team5.unittests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.simple.JSONArray;
import org.junit.jupiter.api.Test;

import seng202.team5.map.GeoLocator;
import seng202.team5.models.MapPin;

public class GeoLocatorTest {
    private GeoLocator geoLocator = new GeoLocator();
    @Test
    public void getAddressTest() {
        MapPin address = geoLocator.getAddress("2 Janet Street");
        MapPin actualPin = new MapPin(-43.5365313, 172.5866546, "2, Janet Street, Upper Riccarton, Christchurch, Christchurch City, Canterbury, 8041, New Zealand / Aotearoa");

        assertEquals(address.getLat(), actualPin.getLat());
        assertEquals(address.getLng(), actualPin.getLng());
        assertEquals(address.getName(), actualPin.getName());
    }

    @Test
    public void getAddressNotFoundTest() {
        MapPin address = geoLocator.getAddress("ghghghgh");

        assertEquals(address.getLat(), 0d);
        assertEquals(address.getLng(), 0d);
        assertEquals(address.getName(), "Not found");
    }

    @Test
    public void getMatchesTest() {
        JSONArray matches = geoLocator.getMatches("2 Janet St");
        JSONArray expected = geoLocator.getMatches("2, Janet Street, Upper Riccarton, Christchurch, Christchurch City, Canterbury, 8041, New Zealand / Aotearoa");

        assertEquals(matches, expected);
    }
}
