package seng202.team5.map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import seng202.team5.business.CrashManager;
import seng202.team5.database.CrashFromDatabase;
import seng202.team5.gui.ExtendedViewController;
import seng202.team5.gui.MapViewController;
import seng202.team5.gui.SearchPanelController;
import seng202.team5.models.MapPin;

import java.util.ArrayList;

/**
 * Creates a bridge from the map's javascript back to the program.
 * The functions within can be called from our javascript in the map view when we set an object of this class
 * as a member of the javascript.
 * @author Luke Stynes
 */
public class JavaScriptBridge {
    private static final Logger log = LogManager.getLogger(SearchPanelController.class);
    private OpenModalInterface openModalInterface;
    private CatchRouteArrayInterface catchRouteArrayInterface;
    private ArrayList<MapPin> points = new ArrayList<>();
    private boolean processed = false;
    private boolean stored = false;
    private MapViewController mapViewController;


    /**
     * Creates a javascript bridge object with a 'callback' lambda function for displaying an extended view modal on a crash.
     * @param openModalLambda open modal lambda to open the extended view modal
     * @param openCatchLambda the lambda for getting the pins
     */
    public JavaScriptBridge(OpenModalInterface openModalLambda, CatchRouteArrayInterface openCatchLambda) {
        openModalInterface = openModalLambda;
        catchRouteArrayInterface = openCatchLambda;
    }

    /**
     * Default constructor with a void callback.
      */
    public JavaScriptBridge(MapViewController instance) {
        openModalInterface =(int id, String tableName) -> {};
        catchRouteArrayInterface = (String route) -> {}; //put return value in {}
        this.mapViewController = instance;
    }
    /**
     * Opens the extended view modal for a specified crash.
     *
     * @param id The ID of the crash.
     * @param tableName The name of the table containing the crash data.
     */
    public void openExtendedViewModal(int id, String tableName) {
        log.info("Table: " + tableName);
        log.info("Called from js");
        CrashManager crashManager = new CrashManager();
        CrashFromDatabase selectedCrash = crashManager.getOne(id, tableName);
        if (selectedCrash != null) {
            ExtendedViewController newView = new ExtendedViewController();
            newView.showExtendedView(selectedCrash);
            log.info("Opening extended view modal for Crash #" + id + " via JSBridge");
        }
    }

    /**
     * Processes route coordinates received from JavaScript and stores them in points ArrayList.
     *
     * @param route The JSON formatted route coordinates.
     * @throws ParseException If there's an error parsing the route coordinates.
     */
    public void catchArray(String route) throws ParseException {
        processed = false;
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(route);
        var coordinates = (JSONArray) jsonObject.get("coordinates");

        coordinates.forEach(p -> {
            var point = (JSONObject) p;
            points.add(new MapPin((Double) point.get("lat"), (Double) point.get("lng"), ""));
        });
        mapViewController.createRoutePins();
    }

    /**
     * Retrieves the last set of stored coordinates.
     *
     * @return An ArrayList of MapPin objects representing the last set of coordinates.
     */
    public ArrayList<MapPin> getLastCoords() {
        return this.points;
    }

    /**
     * Clears the last set of stored coordinates.
     */
    public void clearLastCoords() {
        this.points = new ArrayList<>();
        processed = false;
        stored = false;
    }

    public boolean getProcessed() {
        return this.processed;
    }

    public boolean getStored() {
        return this.stored;
    }
}

