package seng202.team5.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Simple class representing a route as any number of waypoints along the way.
 */
public class Route {
    private final List<MapPin> route = new ArrayList<>();

    /**
     * Create a new route with any number of positions
     * @param points points along the route in order first to last
     */
    public Route(MapPin ...points) {
        Collections.addAll(route, points);
    }

    /**
     * Converts a list of positions into a JSON array representation.
     * Each position is represented as an object with "lat" (latitude) and "lng" (longitude) properties.
     *
     * @return A string representing the JSON array of positions.
     */
    public String toJSONArray() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        route.forEach(pos -> stringBuilder.append(
                String.format("{\"lat\": %f, \"lng\": %f}, ", pos.lat, pos.lng)));
        if (stringBuilder.length() > 2)
            stringBuilder.setLength(stringBuilder.length() -2);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
