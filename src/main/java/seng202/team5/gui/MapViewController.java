package seng202.team5.gui;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Popup;
import javafx.stage.Stage;

import netscape.javascript.JSException;
import netscape.javascript.JSObject;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import seng202.team5.business.CrashManager;
import seng202.team5.database.MapPinDatapoint;
import seng202.team5.map.GeoLocator;
import seng202.team5.map.JavaScriptBridge;
import seng202.team5.models.MapPin;
import seng202.team5.models.Route;
import seng202.team5.models.Search;

import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Controller for managing the map.fxml file. Interfaces with the JavaScript in the map.html file
 * through javaScriptConnector.
 */
public class MapViewController implements Initializable {
    private Stage stage;
    private Search search = new Search();
    @FXML private WebView geoView;
    @FXML private TextField addressField;
    @FXML private CheckBox pinToggle1;
    @FXML private CheckBox heatToggle1;
    @FXML private CheckBox pinToggleRoute;
    @FXML private CheckBox heatToggleRoute;
    @FXML private TextField sourceField;
    @FXML private TextField destinationField;
    @FXML private VBox searchUpdateBox;
    @FXML private Text updateLabel;
    @FXML private Button updateButton;
    @FXML private Button generateRoutesButton;
    @FXML private Button clearRoutesButton;
    private WebEngine webEngine;
    private JSObject javaScriptConnector;
    private GeoLocator geoLocator;
    private JavaScriptBridge javaScriptBridge;
    private CrashManager crashManager;
    private Popup addressMatchPopup = new Popup();
    private Popup sourceMatchPopup = new Popup();
    private Popup destMatchPopup = new Popup();
    private Timer addressTimer = null;
    private Timer sourceTimer = null;
    private Timer destTimer = null;
    private boolean checkMatches = true;
    private static final Logger log = LogManager.getLogger(MapViewController.class);
    private NavigationController navigationController;
    private JSONArray crashJSON;
    private MapPin maxCoords;
    private MapPin minCoords;
    private boolean loaded = false;

    /**
     * Custom initialise method for the map controller that sets up the Javascript Bridge,
     * GeoLocator and initialises the map.
     *
     * @param location The location used to resolve relative paths for the root object, or {@code
     *     null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if the root
     *     object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        javaScriptBridge = new JavaScriptBridge(this);
        geoLocator = new GeoLocator();
        crashManager = new CrashManager();
        initMap();
        initAddressFields();
    }

    /**
     * Creates listeners for the address fields, which await a timer of 400ms. Upon the timer
     * passing without further updates to interrupt it, the text in the field is passed to another
     * function, which populates a drop-down menu of matched addresses.
     */
    private void initAddressFields() {
        addressMatchPopup.setAutoHide(true);
        sourceMatchPopup.setAutoHide(true);
        destMatchPopup.setAutoHide(true);
        addressField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (checkMatches) {
                if (addressTimer != null) {
                    addressTimer.cancel();
                }
                addressTimer = new Timer();
                addressTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Thread thread = new Thread(() -> {
                            populateMatches(addressField, addressMatchPopup);
                        });
                        thread.start();
                    }
                }, 400);
            }
        });
        sourceField
                .textProperty()
                .addListener(
                        (observable, oldValue, newValue) -> {
                            if (checkMatches) {
                                if (sourceTimer != null) {
                                    sourceTimer.cancel();
                                }
                                sourceTimer = new Timer();
                                sourceTimer.schedule(
                                        new TimerTask() {
                                            @Override
                                            public void run() {
                                                populateMatches(sourceField, sourceMatchPopup);
                                            }
                                        },
                                        400);
                            }
                        });
        destinationField
                .textProperty()
                .addListener(
                        (observable, oldValue, newValue) -> {
                            if (checkMatches) {
                                if (destTimer != null) {
                                    destTimer.cancel();
                                }
                                destTimer = new Timer();
                                destTimer.schedule(
                                        new TimerTask() {
                                            @Override
                                            public void run() {
                                                populateMatches(
                                                        destinationField, destMatchPopup);
                                            }
                                        },
                                        400);
                            }
                        });
    }

    /**
     * Configures and displays a popup list of matching addresses for the given string. Called by
     * listener objects in the initAddressFields() function. The popup window should resize to the
     * text field's width, and move with the window as it is dragged.
     */
    private void populateMatches(TextField textField, Popup popupWindow) {
        JSONArray matchArray = geoLocator.getMatches(textField.getText());
        List<String> displayNames = new ArrayList<>();
        for (Object match : matchArray) {
            String display_name = ((JSONObject) match).get("display_name").toString();
            displayNames.add(display_name);
        }
        Platform.runLater(
                () -> {
                    // Hide all popup windows; only one can be made visible at a time
                    addressMatchPopup.hide();
                    destMatchPopup.hide();
                    sourceMatchPopup.hide();
                    popupWindow.getContent().clear();

                    ListView<String> matchList = new ListView<>();
                    matchList.getItems().addAll(displayNames);
                    Double height = 200.0; // Should size the popup to approx. the number of
                                                   // matches
                    matchList.setMinWidth(textField.getWidth());
                    matchList.setPrefWidth(textField.getWidth());
                    matchList.setMaxWidth(textField.getWidth());
                    matchList.setMinHeight(height);
                    matchList.setPrefHeight(height);
                    matchList.setMaxHeight(height);

                    // Ensure width of popup stays the same as the text field's
                    textField
                            .widthProperty()
                            .addListener(
                                    (obs, old, value) -> {
                                        matchList.setMinWidth((Double) value);
                                        matchList.setPrefWidth((Double) value);
                                        matchList.setMaxWidth((Double) value);
                                    });

                    // Create listeners for window position; on change, update the coordinates of
                    // the popup (so it follows during window dragging
                    stage.xProperty()
                            .addListener(
                                    (obs, old, value) -> {
                                        double newX = textField.localToScreen(0, 0).getX();
                                        double newY =
                                                textField.localToScreen(0, 0).getY()
                                                        + textField.getHeight();
                                        popupWindow.setX(newX);
                                        popupWindow.setY(newY);
                                    });

                    stage.yProperty()
                            .addListener(
                                    (obs, old, value) -> {
                                        double newX = textField.localToScreen(0, 0).getX();
                                        double newY =
                                                textField.localToScreen(0, 0).getY()
                                                        + textField.getHeight();
                                        popupWindow.setX(newX);
                                        popupWindow.setY(newY);
                                    });

                    // Finally, set up the popup content and behaviour
                    double xCoord = textField.localToScreen(0, 0).getX();
                    double yCoord = textField.localToScreen(0, 0).getY() + textField.getHeight();

                    // If there are no matches, add text item to give user feedback
                    if (matchArray.isEmpty()) {
                        matchList.getItems().add("No matches found");
                    }
                    popupWindow.getContent().add(matchList);
                    popupWindow.show(textField.getScene().getWindow(), xCoord, yCoord);

                    // The popup should hide when the corresponding text field is inactive
                    textField
                            .focusedProperty()
                            .addListener(
                                    (obs, old, value) -> {
                                        if (value) {
                                            double newX = textField.localToScreen(0, 0).getX();
                                            double newY =
                                                    textField.localToScreen(0, 0).getY()
                                                            + textField.getHeight();
                                            popupWindow.show(
                                                    textField.getScene().getWindow(), newX, newY);
                                        } else {
                                            popupWindow.hide();
                                        }
                                    });

                    if (!matchArray
                            .isEmpty()) { // If there are matches, user should be able to interact
                                          // with them
                        // Highlighting and pressing Enter on an option should replace the address
                        // text, and navigate to the location.
                        // To avoid infinite loop calling, we flag the field temporarily
                        // (checkMatches boolean)
                        matchList.setOnKeyPressed(
                                event -> {
                                    if (event.getCode() == KeyCode.ENTER) {
                                        String matchedAddress =
                                                matchList.getSelectionModel().getSelectedItem();
                                        if (matchedAddress != null) {
                                            checkMatches = false;
                                            textField.setText(matchedAddress);
                                            handleLocationGo();
                                            checkMatches = true;
                                            popupWindow.hide();
                                        }
                                    }
                                    // Pressing esc should hide the window
                                    if (event.getCode() == KeyCode.ESCAPE) {
                                        popupWindow.hide();
                                    }
                                });
                        // Double-clicking the item should have the same action
                        matchList.setOnMouseClicked(
                                event -> {
                                    if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                                        String matchedAddress = matchList.getSelectionModel().getSelectedItem();
                                        if (matchedAddress != null) {
                                            checkMatches = false;
                                            textField.setText(matchedAddress);
                                            handleLocationGo();
                                            checkMatches = true;
                                            popupWindow.hide();
                                        }
                                    }
                                });
                    }
                });
    }

    public void init(Stage stage, Search search, NavigationController navigationController) {
        this.stage = stage;
        stage.getScene().getStylesheets().add("/css/Main.css");
        stage.getScene().getStylesheets().add("/css/Map.css");
        this.search = search;
        this.navigationController = navigationController;
        initHelp();
    }

    /**
     * Creates help tooltips that are displayed when the help is turned on.
     */
    private void initHelp() {
        navigationController.createHelpEvent(
                addressField, "Enter a location here to quickly navigate to it on the map.");
        navigationController.createHelpEvent(
                pinToggle1, "Hide / Show the locations of crashes on the map.");
        navigationController.createHelpEvent(
                heatToggle1, "Hide / Show a heatmap of crashes on the map.");
        navigationController.createHelpEvent(
                sourceField,
                "The start location of your route, start entering a location and it will autocomplete for you.");
        navigationController.createHelpEvent(
                destinationField,
                "The end location of your route, start entering a location and it will autocomplete for you.");
        navigationController.createHelpEvent(
                generateRoutesButton,
                "Click this to generate a route between two points and show the crashes along the route.");
        navigationController.createHelpEvent(
                clearRoutesButton,
                "Click this to reset the route and clear the map of the current one.");
        navigationController.createHelpEvent(
                pinToggleRoute, "Hide / Show the crashes along the generated route.");
        navigationController.createHelpEvent(
                heatToggleRoute, "Hide / Show the heatmap of crashes along the generated route.");
    }

    /**
     * Initialises the WebView loading in the appropriate html and initialising important
     * communicator objects between Java and Javascript. Once initialised, calls updateFromSearch()
     * to populate the map if data is already present.
     */
    private void initMap() {
        log.info("Map initialised");
        webEngine = geoView.getEngine();
        webEngine.setJavaScriptEnabled(true);
        webEngine.load(MapViewController.class.getResource("/map.html").toExternalForm());

        webEngine
                .getLoadWorker()
                .stateProperty()
                .addListener(
                        (ov, oldState, newState) -> {
                            // If javascript loads successfully
                            if (newState == Worker.State.SUCCEEDED) {
                                // Set our bridge object
                                JSObject window = (JSObject) webEngine.executeScript("window");
                                window.setMember("javaScriptBridge", javaScriptBridge);
                                // Get a reference to the js object that has a reference to the js
                                // methods we need to use in java
                                javaScriptConnector =
                                        (JSObject) webEngine.executeScript("jsConnector");
                                // Call the javascript function to initialise the map
                                javaScriptConnector.call("initMap", search.getTable());
                                updateFromSearch();
                            }
                        });
        loaded = true;
    }

    /**
     * Gets all matching non-paginated results from crashManager via the search object, adds the
     * pins to a JSON array and passes it as a string to the map.html function updateFromSearch,
     * where the old layer of pins is dropped, and this new layer is created. In the future, we will
     * want to consider passing more information and adding tooltips, etc.
     */
    public void updateFromSearch() {
        updateSavedTable();
        if (search.getTable() != null) {
            Task<Void> mapQueryUpdate =
                    new Task<>() {
                        @Override
                        protected Void call() {
                            ArrayList<MapPinDatapoint> crashes = crashManager.runMapQuery(search);
                            crashJSON = new JSONArray();
                            for (MapPinDatapoint crash : crashes) {
                                JSONObject crashObject = new JSONObject();

                                // Wrap map coordinates here to save time (not much)
                                double lat = crash.getLat();
                                double lng = crash.getLng();
                                while (lng >= 270) {
                                    lng -= 360;
                                }
                                while (lng < -90) {
                                    lng += 360;
                                }

                                crashObject.put("lat", lat);
                                crashObject.put("lng", lng);
                                crashObject.put("year", crash.getCrashYear());
                                crashObject.put("severity", crash.getCrashSeverity());
                                crashObject.put("id", crash.getObjectID());
                                crashJSON.add(crashObject);
                            }
                            return null;
                        }
                    };
            mapQueryUpdate.setOnSucceeded(
                    e -> {
                        javaScriptConnector.call("updateFromSearch", crashJSON.toJSONString());
                        updateToggles();
                    });
            Thread mapQueryUpdateThread = new Thread(mapQueryUpdate);
            mapQueryUpdateThread.setDaemon(true);
            mapQueryUpdateThread.start();
        }
    }

    /**
     * Saves a copy of the currently opened table to the embedded map Javascript. This is used by
     * the Javascript Bridge later to allow opening of the extended view modal, ensuring that the
     * right database table is queried.
     */
    public void updateSavedTable() {
        javaScriptConnector.call("updateSavedTable", search.getTable());
    }

    /**
     * Called after a search/view update; ensures that the currently-selected visibility toggles
     * match what's displayed.
     */
    public void updateToggles() {
        try {
            log.info("updating pins");
            if (pinToggle1.isSelected()) {
                log.info("Showing pins");
                javaScriptConnector.call("showPins");
            } else {
                log.info("hiding pins");
                javaScriptConnector.call("hidePins");
            }
            if (heatToggle1.isSelected()) {
                javaScriptConnector.call("showHeat");
            } else {
                javaScriptConnector.call("hideHeat");
            }
        } catch (JSException err) {
            log.error("Javascript exception when updating toggles: " + err.getMessage());
        }
    }

    public void updateRouteToggles() {
        try {
            if (pinToggleRoute.isSelected()) {
                log.info("Showing route crashes");
                javaScriptConnector.call("showPinsRoute");
            } else {
                log.info("Hiding route crashes");
                javaScriptConnector.call("hidePinsRoute");
            }

            if (heatToggleRoute.isSelected()) {
                log.info("Showing route heatmap");
                javaScriptConnector.call("showHeatRoute");
            } else {
                log.info("Hiding route heatmap");
                javaScriptConnector.call("hideHeatRoute");
            }
        } catch (JSException err) {
            log.error("Javascript exception when updating toggles: " + err.getMessage());
        }
    }

    /**
     * Called by clicking the view heatmap checkbox in tab 1. Toggles visibility of the heatmap by
     * calling the JS object. Updates other tab's checkbox to match.
     */
    @FXML
    void toggleHeat1() {
        if (heatToggle1.isSelected()) {
            javaScriptConnector.call("showHeat");
            heatToggle1.setSelected(true);
        } else {
            javaScriptConnector.call("hideHeat");
            heatToggle1.setSelected(false);
        }
    }

    @FXML
    void toggleHeatRoute() {
        if (heatToggleRoute.isSelected()) {
            javaScriptConnector.call("showHeatRoute");
            heatToggleRoute.setSelected(true);
        } else {
            javaScriptConnector.call("hideHeatRoute");
            heatToggleRoute.setSelected(false);
        }
    }

    /**
     * Called by clicking the view pins checkbox in tab 1. Toggles visibility of the heatmap by
     * calling the JS object. Updates other tab's checkbox to match.
     */
    @FXML
    void togglePins1() {
        log.info("Toggling pins");
        if (pinToggle1.isSelected()) {
            javaScriptConnector.call("showPins");
            pinToggle1.setSelected(true);
        } else {
            javaScriptConnector.call("hidePins");
            pinToggle1.setSelected(false);
        }
    }

    /**
     * Called by clicking the crash checkbox in the routing tab. Passes the value through to the JS
     * to hide and show the crashes correctly.
     */
    @FXML
    void togglePinsRoute() {
        if (pinToggleRoute.isSelected()) {
            javaScriptConnector.call("showPinsRoute");
            pinToggleRoute.setSelected(true);
        } else {
            javaScriptConnector.call("hidePinsRoute");
            pinToggleRoute.setSelected(false);
        }
    }

    /**
     * Called by pressing 'Enter' in the location field, or by clicking the Go button. Attempts to
     * match the passed string to a location, and browses to it.
     */
    @FXML
    void handleLocationGo() {
        MapPin location = geoLocator.getAddress(addressField.getText());
        if (Objects.equals(location.getName(), "Not found")) {
            // TODO: Error pop-up for not found address
        } else {
            addressField.setText(location.getName());
            goToLocation(location);
        }
    }

    /**
     * Called by pressing Enter on one of the route location fields, or by clicking "Generate
     * Routes". Clears current routs then should handle the textual inputs and vehicle option, and
     * determine if routes can be created. Then call generateRoutes().
     */
    @FXML
    void handleRoutes() {
        MapPin source = geoLocator.getAddress(sourceField.getText());
        MapPin dest = geoLocator.getAddress(destinationField.getText());
        if (Objects.equals(source.getName(), "Not found")
                || Objects.equals(dest.getName(), "Not found")) {
            // TODO: Error pop-up for not found address
        } else {
            sourceField.setText(source.getName());
            destinationField.setText(dest.getName());
            generateRoutes(source, dest);
        }
    }

    /**
     * Called by pressing the "Clear Routes" button, removes the currently displaying routes (if
     * any).
     */
    @FXML
    void clearRoutes() {
        javaScriptConnector.call("removeRoute");
    }

    /** Called by the UI button to manually update the view to match the current search */
    @FXML
    void handleSearchUpdate() {
        searchUpdateBox.setVisible(false);
        searchUpdateBox.setPrefSize(0, 0);
        updateButton.setVisible(false);
        updateButton.setPrefSize(0, 0);
        updateLabel.setVisible(false);
        updateFromSearch();
    }

    /**
     * Called when the search object is updated; allows for map view to update manually using a
     * visible button
     */
    public void enableSearchButton() {
        searchUpdateBox.setVisible(true);
        searchUpdateBox.setPrefSize(180, 70);
        updateButton.setVisible(true);
        updateButton.setPrefSize(180, 32);
        updateLabel.setVisible(true);
    }

    /**
     * Generate and display routes on the map. Places a pin for source and destination address.
     *
     * @param source Location from which to begin the route.
     * @param dest Location at which the route ends at.
     */
    public void generateRoutes(MapPin source, MapPin dest) {
        Route newRoute = new Route(source, dest);
        javaScriptConnector.call("displayRoute", newRoute.toJSONArray());
    }

    /**
     * Creates a pin for all the crashes that are along a generated route, passing this through to
     * the Javascript as a JSON list.
     */
    public void createRoutePins() {
        ArrayList<MapPin> coords = javaScriptBridge.getLastCoords();
        log.info(coords);

        if (!coords.isEmpty()) {
            ArrayList<MapPinDatapoint> crashesOnRoute = generateRoutePolygon(coords);
            JSONArray routeCrashesJSON = new JSONArray();

            for (MapPinDatapoint crash : crashesOnRoute) {
                JSONObject crashObject = new JSONObject();
                double lat = crash.getLat();
                double lng = crash.getLng();

                while (lng >= 270) lng -= 360;
                while (lng < -90) lng += 360;

                crashObject.put("lat", lat);
                crashObject.put("lng", lng);
                crashObject.put("year", crash.getCrashYear());
                crashObject.put("severity", crash.getCrashSeverity());
                crashObject.put("id", crash.getObjectID());
                routeCrashesJSON.add(crashObject);
            }
            javaScriptConnector.call("showRoutePins", routeCrashesJSON.toJSONString());
        }
        updateRouteToggles();
    }

    /**
     * Set two pins which hold the maximum and minimum latitude/longitudes of the whole route.
     * The pins are later used to create a bounding box for the route and then query the small polygons withing the bounding box.
     *
     * @param coords A list of coordinates along the selected route.
     */
    public void findMaxCoords(ArrayList<MapPin> coords) {
        double maxLat = coords.get(0).getLat();
        double minLat = coords.get(0).getLat();
        double maxLng = coords.get(0).getLng();
        double minLng = coords.get(0).getLng();

        for (MapPin coord : coords) {
            if (coord.getLat() > maxLat) {
                maxLat = coord.getLat();
            }
            if (coord.getLat() < minLat) {
                minLat = coord.getLat();
            }
            if (coord.getLng() > maxLng) {
                maxLng = coord.getLng();
            }
            if (coord.getLng() < minLng) {
                minLng = coord.getLng();
            }
        }

        maxCoords = new MapPin(maxLat, maxLng, "");
        minCoords = new MapPin(minLat, minLng, "");
    }

    /**
     * Takes an array list of coordinates along a route, generates polygons between every second
     * point and the queries the database to see which crashes are inside the polygons.
     * Takes chunks of 200 polygons at a time in order to speed up the querying.
     *
     * @param coords A list of coordinates along a route
     * @return A list of all the crashes along the route.
     */
    public ArrayList<MapPinDatapoint> generateRoutePolygon(ArrayList<MapPin> coords) {
        double length;
        int loopCounter = 0;

        ArrayList<MapPinDatapoint> crashesOnRoute = new ArrayList<>();
        ArrayList<ArrayList<MapPin>> allPolygons = new ArrayList<>();

        StopWatch watch = new StopWatch();
        watch.start();

        // Calculate the max and min latitude and longitude points within the route
         findMaxCoords(coords);

        for (int i = 1; i < coords.size(); i += 2) {

            ArrayList<MapPin> innerArr = new ArrayList<>();

            double x1 = coords.get(i).getLat();
            double x2 = coords.get(i - 1).getLat();
            double y1 = coords.get(i).getLng();
            double y2 = coords.get(i - 1).getLng();

            // Calculate the distance from the first point to the second:
            length = Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));

            // Calculate a vector pointing from the first point to the second
            double a1 = 1.5 * (x2 - x1) / 2 * length;
            double a2 = 1.5 * (y2 - y1) / 2 * length;

            // Calculate a vector that is orthogonal to a by turning it 90 degrees counter-clockwise
            double b1 = -a1;

            // Calculate the 4 vertices of a bounding box around the line.
            MapPin c1 = new MapPin(x1 - a1 + b1, y1 - a2 + a1, "");
            innerArr.add(c1);
            MapPin c2 = new MapPin(x1 - a1 - b1, y1 - a2 - a1, "");
            innerArr.add(c2);
            MapPin c3 = new MapPin(x2 + a1 - b1, y2 + a2 - a1, "");
            innerArr.add(c3);
            MapPin c4 = new MapPin(x2 + a1 + b1, y2 + a2 + a1, "");
            innerArr.add(c4);

            allPolygons.add(innerArr);

            // Find all crashes inside the next 200 polygons
            if (!coords.isEmpty() && loopCounter == 200) {
                crashesOnRoute.addAll(
                        crashManager.runPinsInBoxQuery(search, allPolygons, maxCoords, minCoords));
                allPolygons.clear();
                loopCounter = 0;
            }
            loopCounter++;
        }

        // Query the remaining polygons
        if (!coords.isEmpty()) {
            crashesOnRoute.addAll(crashManager.runPinsInBoxQuery(search, allPolygons, maxCoords, minCoords));
        }

        watch.stop();
        log.info(
                "Total time for "
                        + crashesOnRoute.size()
                        + " route crashes coords: "
                        + watch.getTime(TimeUnit.MILLISECONDS));
        watch.reset();

        // Empty the coordinates of this route
        javaScriptBridge.clearLastCoords();
        return crashesOnRoute;
    }

    /**
     * Calls the HTML to pan to the supplied location coordinates.
     *
     * @param location MapPin object of the location to pan to.
     */
    public void goToLocation(MapPin location) {
        javaScriptConnector.call("goToLocation", location.lat, location.lng);
    }

    /**
     * Shows the correct information that should be displayed when the user opens the map tab of the
     * maps. This hides the route specific crashes and heatmap and shows the crash specific.
     */
    @FXML
    public void openMap() {
        if (loaded) {
            log.info("Switching to map, hiding route crashes, showing map crashes...");
            javaScriptConnector.call("hidePinsRoute");
            javaScriptConnector.call("hideHeatRoute");

            if (pinToggle1.isSelected()) {
                javaScriptConnector.call("showPins");
                pinToggle1.setSelected(true);
            } else {
                javaScriptConnector.call("hidePins");
                pinToggle1.setSelected(false);
            }

            if (heatToggle1.isSelected()) {
                javaScriptConnector.call("showHeat");
                heatToggle1.setSelected(true);
            } else {
                javaScriptConnector.call("hideHeat");
                heatToggle1.setSelected(false);
            }
            javaScriptConnector.call("removeRoute");
        }
    }

    /**
     * Shows the correct information that should be displayed when the user opens the routing tab of
     * the maps.
     */
    @FXML
    public void openRoute() {
        if (loaded) {
            log.info("Switching to routing, hiding map crashes, showing route crashes...");
            javaScriptConnector.call("hidePins");
            javaScriptConnector.call("hideHeat");

            if (pinToggleRoute.isSelected()) {
                javaScriptConnector.call("showPinsRoute");
                pinToggleRoute.setSelected(true);
            } else {
                javaScriptConnector.call("hidePinsRoute");
                pinToggleRoute.setSelected(false);
            }

            if (heatToggleRoute.isSelected()) {
                javaScriptConnector.call("showHeatRoute");
                heatToggleRoute.setSelected(true);
            } else {
                javaScriptConnector.call("hideHeatRoute");
                heatToggleRoute.setSelected(false);
            }
        }
    }
}
