package seng202.team5.map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import seng202.team5.models.MapPin;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Class to handle requesting location from Nominatim Geolocation API
 *
 * @author Morgan English
 */
public class GeoLocator {
    private static final Logger log = LogManager.getLogger(GeoLocator.class);

    /**
     * Runs a query with the address given and finds the most applicable lat, lng co-ordinates
     * @param address the address to find the Lat and Lng of
     * @return a MapPin of the addresses location
     */
    public MapPin getAddress(String address) {
        // As we already filter to NZ, for searches of strings to work, they cannot specify the
        // country at the end
        address = address.replace(", New Zealand / Aotearoa", ""); // the properly formatted string
        address = address.replace("New Zealand", ""); // trying to think of other cases to trim
        address = address.replace("Aotearoa", "");

        String logMessage =
                String.format("Requesting geolocation from Nominatim for address: %s", address);
        log.info(logMessage);
        address = address.replace(' ', '+');
        try {
            // Creating the http request
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request =
                    HttpRequest.newBuilder(
                                    URI.create(
                                            "https://nominatim.openstreetmap.org/search?q="
                                                    + address
                                                    + "&countrycodes=nz&format=json"))
                            .build();
            // Getting the response
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());
            // Parsing the json response to get the latitude and longitude co-ordinates
            JSONParser parser = new JSONParser();
            JSONArray results = (JSONArray) parser.parse(response.body());
            if (!results.isEmpty()) {
                JSONObject bestResult = (JSONObject) results.get(0);
                double lat = Double.parseDouble((String) bestResult.get("lat"));
                double lng = Double.parseDouble((String) bestResult.get("lon"));
                String name = (String) bestResult.get("display_name");
                return new MapPin(lat, lng, name);
            } else {
                return new MapPin(0d, 0d, "Not found");
            }
        } catch (IOException | ParseException e) {
            log.error(e);
        } catch (InterruptedException ie) {
            log.error(ie);
            Thread.currentThread().interrupt();
        }
        return new MapPin(0d, 0d, "Not found");
    }

    /**
     * Takes a string of a partial address and finds the most likely matches for what is currently
     * entered.
     *
     * @param address a partially formed address string
     * @return a JSONArray of possible matches for the partially formed add
     */
    public JSONArray getMatches(String address) {
        // As we already filter to NZ, for searches of strings to work, they cannot specify the
        // country at the end
        address = address.replace(", New Zealand / Aotearoa", ""); // The properly formatted string
        address = address.replace("New Zealand", ""); // Trying to think of other cases to trim
        address = address.replace("Aotearoa", "");

        String logMessage =
                String.format(
                        "Requesting geolocation from Nominatim for address: %s, New Zealand",
                        address);
        log.info(logMessage);
        address = address.replace(' ', '+');
        JSONArray results = new JSONArray();
        try {
            // Creating the http request
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request =
                    HttpRequest.newBuilder(
                                    URI.create(
                                            "https://nominatim.openstreetmap.org/search?q="
                                                    + address
                                                    + "&countrycodes=nz&format=json"))
                            .build();
            // Getting the response
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());
            // Parsing the json response to get the latitude and longitude co-ordinates
            JSONParser parser = new JSONParser();
            results = (JSONArray) parser.parse(response.body());
            return results;
        } catch (IOException | ParseException e) {
            log.error(e);
        } catch (InterruptedException ie) {
            log.error(ie);
            Thread.currentThread().interrupt();
        }
        return results;
    }
}
