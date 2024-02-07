package seng202.team5.gui;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXToggleButton;

import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.CheckComboBox;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.kordamp.ikonli.javafx.FontIcon;

import seng202.team5.map.GeoLocator;
import seng202.team5.models.Conditional;
import seng202.team5.models.MapPin;
import seng202.team5.models.Search;
import seng202.team5.models.TableColumns;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * SearchPanelController handles user input relating to the search Panel of the GUI.
 */
public class SearchPanelController {
    private static final Logger log = LogManager.getLogger(SearchPanelController.class);
    /**
     * The ID that the next conditional will be given upon being added.
     * Technically, if the user adds two billion conditionals to the program, it will no longer be able to generate
     * unique conditional IDs and will start to glitch out. However, I would not consider this an issue...
     */
    private Integer conditionalIndex = 0;
    private NavigationController navigationController;
    private Search search;
    private Stage stage;
    private GeoLocator geoLocator;
    @FXML
    private TextField keywordField;
    @FXML
    private MFXToggleButton matchToggleButton;
    @FXML
    private ComboBox startYearComboBox;
    @FXML
    private ComboBox endYearComboBox;
    @FXML
    private ComboBox startSpeedLimitBox;
    @FXML
    private ComboBox endSpeedLimitBox;
    @FXML
    private CheckComboBox severityComboBox;
    @FXML
    private CheckComboBox regionComboBox;
    @FXML
    private CheckComboBox participantComboBox;
    @FXML
    private CheckComboBox weatherComboBoxA;
    @FXML
    private CheckComboBox weatherComboBoxB;
    @FXML
    private CheckComboBox lightComboBox;
    @FXML
    private CheckComboBox holidayComboBox;
    @FXML
    private ComboBox advisedStartBox;
    @FXML
    private ComboBox advisedEndBox;
    @FXML
    private ComboBox temporaryStartBox;
    @FXML
    private ComboBox temporaryEndBox;
    @FXML
    private TextField startDeathsField;
    @FXML
    private TextField endDeathsField;
    @FXML
    private TextField startMinorInjuriesField;
    @FXML
    private TextField endMinorInjuriesField;
    @FXML
    private TextField startMajorInjuriesField;
    @FXML
    private TextField endMajorInjuriesField;
    @FXML
    private CheckComboBox tlaComboBox;
    @FXML
    private CheckComboBox trafficCtrlBox;
    @FXML
    private CheckComboBox collisionComboBox;
    @FXML
    private CheckComboBox crashDirectionBox;
    @FXML
    private CheckComboBox vehicleDirectionBox;
    @FXML
    private ComboBox laneStartBox;
    @FXML
    private ComboBox laneEndBox;
    @FXML
    private CheckComboBox laneConfigBox;
    @FXML
    private CheckComboBox roadCharBox;
    @FXML
    private CheckComboBox roadSurfaceBox;
    @FXML
    private CheckBox shCheckBox;
    @FXML
    private CheckBox hillCheckBox;
    @FXML
    private CheckBox lightsCheckBox;
    @FXML
    private VBox conditionalsContainer;
    @FXML
    private TitledPane locationPane;
    @FXML
    private TitledPane basicPane;
    @FXML
    private TitledPane advancedPane;
    @FXML
    private TitledPane conditionalPane;
    @FXML
    private CheckBox yearCheckBox;
    @FXML
    private CheckBox speedLimitCheckBox;
    @FXML
    private HBox yearHBox;
    @FXML
    private HBox speedLimitHBox;
    @FXML
    private CheckBox advisedSpeedCheckBox;
    @FXML
    private CheckBox tempSpeedCheckBox;
    @FXML
    private CheckBox deathsCheckBox;
    @FXML
    private CheckBox minorInjuryCheckBox;
    @FXML
    private CheckBox majorInjuryCheckBox;
    @FXML
    private CheckBox numLanesCheckBox;
    @FXML
    private HBox advisedSpeedHBox;
    @FXML
    private HBox tempSpeedHBox;
    @FXML
    private HBox deathsHBox;
    @FXML
    private HBox minorInjuryHBox;
    @FXML
    private HBox majorInjuryHBox;
    @FXML
    private HBox numLanesHBox;

    @FXML
    private FontIcon severityIcon;
    @FXML
    private FontIcon regionIcon;
    @FXML
    private FontIcon participantsIcon;
    @FXML
    private FontIcon weatherAIcon;
    @FXML
    private FontIcon weatherBIcon;
    @FXML
    private FontIcon lightIcon;
    @FXML
    private FontIcon holidayIcon;
    @FXML
    private FontIcon tlaIcon;
    @FXML
    private FontIcon traffControlIcon;
    @FXML
    private FontIcon collisionsIcon;
    @FXML
    private FontIcon crashDirectionIcon;
    @FXML
    private FontIcon vehicleDirectionIcon;
    @FXML
    private FontIcon laneConfigIcon;
    @FXML
    private FontIcon roadCharIcon;
    @FXML
    private FontIcon roadSurfaceIcon;
    @FXML
    private MFXToggleButton locationSearchToggle;
    @FXML
    private TextField locationField;
    @FXML
    private Button findLocationButton;
    @FXML
    private Text radiusLabel;
    @FXML
    private Slider radiusSlider;
    @FXML
    private MFXButton resetSearchButton;
    @FXML
    private Button severityToggle;
    @FXML
    private Button regionToggle;
    @FXML
    private Button participantsToggle;
    @FXML
    private Button weatherAToggle;
    @FXML
    private Button weatherBToggle;
    @FXML
    private Button lightToggle;
    @FXML
    private Button holidayToggle;
    @FXML
    private Button tlaToggle;
    @FXML
    private Button traffCtrlToggle;
    @FXML
    private Button collisionsToggle;
    @FXML
    private Button crashDirToggle;
    @FXML
    private Button vehicleDirToggle;
    @FXML
    private Button laneConfigToggle;
    @FXML
    private Button roadCharToggle;
    @FXML
    private Button roadSurfaceToggle;
    @FXML
    private ToggleButton participantAndToggle;
    @FXML
    private ToggleButton collisionAndToggle;
    private MapPin matchLocation = null;
    private Popup locationPopup = new Popup();
    private Timer addressTimer = new Timer();
    private boolean checkMatches = true;


    /**
     * Initializes the searchPanelController.
     * This is done by calling various methods to initialize and populate the various GUI elements of the search panel.
     * These elements are initialized, where possible, from stored values in the search object, in the event that we
     * implement saved states between application sessions.
     * @param stage The window onto which the search object is displayed
     * @param search The search object for storing and retrieving search parameters
     * @param navigationController The navigationController, for updating the main view if needed
     */
    public void init(Stage stage, Search search, NavigationController navigationController) {
        this.stage = stage;
        stage.getScene().getStylesheets().add("/css/Main.css");
        stage.getScene().getStylesheets().add("/css/Search.css");
        this.search = search;
        this.navigationController = navigationController;
        geoLocator = new GeoLocator();
        matchToggleButton.setSelected(search.isMatchAll());
        initLocationField();
        initTitledPanes();
        initCheckComboBoxes();
        initComboBoxes();
        initIndeterminateCheckBoxes();
        initConditionals();
        initKeywordField();
        initStartNumericalFields();
        initEndNumericalFields();
        initRangeCheckBoxes();
        initRangeSlider();
        initHelp();
    }

    /**
     * Calls createHelpEvent in the navigationController parent to bind various help tooltips
     * to their corresponding GUI components.
     */
    private void initHelp() {
        navigationController.createHelpEvent(resetSearchButton, "Resets all search parameters to their defaults, and perform a new search.");
        navigationController.createHelpEvent(locationPane, "This pane contains options for searching near a given location.");
        navigationController.createHelpEvent(locationSearchToggle, "Toggle whether or not the search should factor location into its results.");
        navigationController.createHelpEvent(locationField, "Enter a location into the field, then click Find to retrieve a best match.");
        navigationController.createHelpEvent(findLocationButton, "Enter a location into the field, then click Find to retrieve a best match.");
        navigationController.createHelpEvent(radiusLabel, "Drag the slider to adjust the radius within which to display crashes.\nThe slider allows for values in the range 1-250km.");
        navigationController.createHelpEvent(radiusSlider, "Drag the slider to adjust the radius within which to display crashes.\nThe slider allows for values in the range 1-250km.");
        navigationController.createHelpEvent(basicPane, "This pane contains basic filtering options for crashes.");
        navigationController.createHelpEvent(keywordField, "Enter keywords to match against in crashes here.");
        navigationController.createHelpEvent(matchToggleButton, "Toggle between matching any and all keywords specified.\nKeywords are separated by whitespaces when using the 'Match any terms' setting.");
        navigationController.createHelpEvent(yearCheckBox, "Enable/disable filtering records by their year.");
        navigationController.createHelpEvent(yearHBox, "Specify the range of years to filter records by.");
        navigationController.createHelpEvent(speedLimitCheckBox, "Enable/disable filtering records by their speed limit.");
        navigationController.createHelpEvent(speedLimitHBox, "Specify the range of speed limits to filter records by.");
        navigationController.createHelpEvent(severityComboBox, "Select crash severity types to filter records by.\nThis filter is inactive when no records are selected.");
        navigationController.createHelpEvent(regionComboBox, "Select which regions to display records from.\nThis filter is inactive when no records are selected.");
        navigationController.createHelpEvent(participantComboBox, "Select which participants should be in selected crashes.\nThis filter is inactive when no records are selected.");
        navigationController.createHelpEvent(weatherComboBoxA, "Select precipitation values to filter records by.\nThis filter is inactive when no records are selected.");
        navigationController.createHelpEvent(weatherComboBoxB, "Select frost/wind values to records by.\nThis filter is inactive when no records are selected.");
        navigationController.createHelpEvent(lightComboBox, "Select lighting values to filter records by.\nThis filter is inactive when no records are selected.");
        navigationController.createHelpEvent(holidayComboBox, "Select public holidays to filter records by.\nThis filter is inactive when no records are selected.");
        navigationController.createHelpEvent(advancedPane, "This pane contains advanced filtering options for crashes.");
        navigationController.createHelpEvent(advisedSpeedCheckBox, "Enable/disable filtering records by their advised speed.");
        navigationController.createHelpEvent(advisedSpeedHBox, "Specify the range of advised speeds to filter records by.");
        navigationController.createHelpEvent(tempSpeedCheckBox, "Enable/disable filtering records by their temporary speed limit.");
        navigationController.createHelpEvent(tempSpeedHBox, "Specify the range of temporary speed limits to filter records by.");
        navigationController.createHelpEvent(numLanesCheckBox, "Enable/disable filtering records by their lane count.");
        navigationController.createHelpEvent(numLanesHBox, "Specify the range of lanes to filter records by.");
        navigationController.createHelpEvent(deathsCheckBox, "Enable/disable filtering records by their death count.");
        navigationController.createHelpEvent(deathsHBox, "Specify the range of deaths to filter records by.\nLeave the start/end fields blank to remove the lower/upper search bounds respectively.\nInvalid ranges and inputs will be ignored.");
        navigationController.createHelpEvent(minorInjuryCheckBox, "Enable/disable filtering records by their minor injury count.");
        navigationController.createHelpEvent(minorInjuryHBox, "Specify the range of minor injuries to filter records by.\nLeave the start/end fields blank to remove the lower/upper search bounds respectively.\nInvalid ranges and inputs will be ignored.");
        navigationController.createHelpEvent(majorInjuryCheckBox, "Enable/disable filtering records by their major injury count.");
        navigationController.createHelpEvent(majorInjuryHBox, "Specify the range of major injuries to filter records by.\nLeave the start/end fields blank to remove the lower/upper search bounds respectively.\nInvalid ranges and inputs will be ignored.");
        navigationController.createHelpEvent(tlaComboBox, "Select which local authority regions to display records from.\nThis filter is inactive when no records are selected.");
        navigationController.createHelpEvent(trafficCtrlBox, "Select which traffic controls to display records from.\nThis filter is inactive when no records are selected.");
        navigationController.createHelpEvent(collisionComboBox, "Select which collisions to include in displaying records.\nThis filter is inactive when no records are selected.");
        navigationController.createHelpEvent(crashDirectionBox, "Select which crash direction values to include.\nThis filter is inactive when no records are selected.");
        navigationController.createHelpEvent(vehicleDirectionBox, "Select which vehicle direction values to include.\nThis filter is inactive when no records are selected.");
        navigationController.createHelpEvent(laneConfigBox, "Select which lane configurations to display records from.\nThis filter is inactive when no records are selected.");
        navigationController.createHelpEvent(roadCharBox, "Select which road characters to display records from.\nThis filter is inactive when no records are selected.");
        navigationController.createHelpEvent(roadSurfaceBox, "Select which road surfaces to display records from.\nThis filter is inactive when no records are selected.");
        navigationController.createHelpEvent(shCheckBox, "Select whether or not to search for records on a state highway.\nChecked will find state highway records, while unchecked will find non-state highway records.\nDefault state (-) is indeterminate, and will find all records.");
        navigationController.createHelpEvent(hillCheckBox, "Select whether or not to search for records on a hill.\nChecked will find hill records, while unchecked will find non-hill records.\nDefault state (-) is indeterminate, and will find all records.");
        navigationController.createHelpEvent(lightsCheckBox, "Select whether or not to search for records where the street lights were lit.\nChecked will find lit records, while unchecked will find unlit records.\nDefault state (-) is indeterminate, and will find all records.");
        navigationController.createHelpEvent(participantAndToggle, "Click this to toggle between searching for Any/All participants present in a crash.");
        navigationController.createHelpEvent(collisionAndToggle, "Click this to toggle between searching for Any/All participants present in a crash.");
        List<Button> toggles = new ArrayList<>();
        toggles.add(severityToggle);
        toggles.add(regionToggle);
        toggles.add(participantsToggle);
        toggles.add(weatherAToggle);
        toggles.add(weatherBToggle);
        toggles.add(lightToggle);
        toggles.add(holidayToggle);
        toggles.add(tlaToggle);
        toggles.add(traffCtrlToggle);
        toggles.add(collisionsToggle);
        toggles.add(crashDirToggle);
        toggles.add(vehicleDirToggle);
        toggles.add(laneConfigToggle);
        toggles.add(roadCharToggle);
        toggles.add(roadSurfaceToggle);

        for (Button toggle : toggles) {
            navigationController.createHelpEvent(toggle, "Toggles the selection of all/none for the given filter.");
        }
        navigationController.createHelpEvent(conditionalPane, "This pane contains expert filtering options for crashes, where users can generate their own conditional statements.\nConditionals consist of three sections: A field to query, an operation to perform, and the value (or values) to pass.");
    }

    private void initLocationField() {
        locationPopup.setAutoHide(true);
        locationField.textProperty().addListener((obs, old, value) -> {
            if (locationSearchToggle.isSelected() && checkMatches) {
                if (addressTimer != null) {
                    addressTimer.cancel();
                }
                addressTimer = new Timer();
                addressTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Thread thread = new Thread(() -> {
                            populateMatches(locationField, locationPopup);
                        });
                        thread.start();
                    }
                }, 400);
            }
        });
    }

    private void populateMatches(TextField textField, Popup popupWindow) {
        JSONArray matchArray = geoLocator.getMatches(textField.getText());
        List<String> displayNames = new ArrayList<>();
        for (Object match : matchArray) {
            String display_name = ((JSONObject) match).get("display_name").toString();
            displayNames.add(display_name);
        }
        Platform.runLater(() -> {
            locationPopup.hide();
            popupWindow.getContent().clear();
            ListView<String> matchList = new ListView<>();
            matchList.getItems().addAll(displayNames);
            matchList.setMinWidth(500);
            matchList.setPrefWidth(500);
            matchList.setMaxWidth(500);
            matchList.setMinHeight(0);
            matchList.setPrefHeight(Region.USE_COMPUTED_SIZE);
            matchList.setMaxHeight(200);
            // Ensure width of popup stays the same as the text field's

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
            double xCoord = textField.localToScreen(0, 0).getX();
            double yCoord = textField.localToScreen(0, 0).getY() + textField.getHeight();
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
                                    handleFindLocation();
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
                            if (event.getButton() == MouseButton.PRIMARY
                                    && event.getClickCount() == 2) {
                                String matchedAddress =
                                        matchList.getSelectionModel().getSelectedItem();
                                if (matchedAddress != null) {
                                    checkMatches = false;
                                    textField.setText(matchedAddress);
                                    handleFindLocation();
                                    checkMatches = true;
                                    popupWindow.hide();
                                }
                            }
                        });
            }
        });
    }

    /**
     * Initializes the location search radius range slider, by creating a listener to its value property.
     * The stored search radius and radius label text are updated on change.
     */
    private void initRangeSlider() {
        radiusSlider.valueProperty().addListener((obs, old, value) -> {
            search.setRadius((int) radiusSlider.getValue());
            search.handleSearchChange();
            radiusLabel.setText("Display results within " + String.valueOf((int) radiusSlider.getValue()) + "km");
        });
    }

    /**
     * Called by toggling location search on/off in the GUI. Calls toggleLocationSearch().
     */
    @FXML
    void handleLocationToggle() { toggleLocationSearch(); }

    /**
     * Toggles the state of the location component of the search query.
     * When enabled, the GUI components are made interactive and opacity increased.
     * The search is updated to store all user-input values regarding location.
     * When disabled, the GUI components are made non-interactive and opacity reduced.
     * The search is updated to discard all user-input values regarding location.
     */
    private void toggleLocationSearch() {
        if (locationSearchToggle.isSelected()) {
            locationField.setDisable(false);
            findLocationButton.setDisable(false);
            radiusLabel.setOpacity(1.0);
            radiusSlider.setDisable(false);
            radiusLabel.setText("Display results within " + (int) radiusSlider.getValue() + "km");
            search.setRadius((int) radiusSlider.getValue());

            if (matchLocation != null) {
                search.setLat(matchLocation.getLat());
                search.setLng(matchLocation.getLng());
            } else {
                search.setLat(null);
                search.setLng(null);
            }
            search.handleSearchChange();

        } else {
            locationField.setDisable(true);
            findLocationButton.setDisable(true);
            radiusLabel.setOpacity(0.5);
            radiusSlider.setDisable(true);

            search.setRadius(null);
            search.setLng(null);
            search.setLat(null);
            search.handleSearchChange();
        }
    }

    /**
     * Called by Enter on the location text field, or clicking "Find".
     * Calls findLocation(), and attempts to find the location specified from the string input.
     */
    @FXML
    void handleFindLocation() {
        String locationString = locationField.getText();
        MapPin location = geoLocator.getAddress(locationString);
        if (!Objects.equals(location.getName(), "Not found")) {
            search.setLng(location.getLng());
            search.setLat(location.getLat());
            matchLocation = location;
            locationField.setText(location.getName());
            search.handleSearchChange();
        } else {
            locationField.clear();
            search.setLng(null);
            search.setLat(null);
            matchLocation = null;
            locationField.setPromptText("Could not find location.");
        }
    }
    @FXML
    void clearCrashSeverity() {
        toggleCheckComboBox(severityComboBox, severityIcon);
    }
    @FXML
    void clearRegion() {
        toggleCheckComboBox(regionComboBox, regionIcon);
    }
    @FXML
    void clearParticipants() {
        toggleCheckComboBox(participantComboBox, participantsIcon);
    }
    @FXML
    void clearDownfall() {
        toggleCheckComboBox(weatherComboBoxA, weatherAIcon);
    }
    @FXML
    void clearFrostsWinds() {
        toggleCheckComboBox(weatherComboBoxB, weatherBIcon);
    }
    @FXML
    void clearLight() {
        toggleCheckComboBox(lightComboBox, lightIcon);
    }
    @FXML
    void clearHoliday() {
        toggleCheckComboBox(holidayComboBox, holidayIcon);
    }
    @FXML
    void clearLocalAuthority() {
        toggleCheckComboBox(tlaComboBox, tlaIcon);
    }
    @FXML
    void clearTrafficControl() {
        toggleCheckComboBox(trafficCtrlBox, traffControlIcon);
    }
    @FXML
    void clearCollisions() {
        toggleCheckComboBox(collisionComboBox, collisionsIcon);
    }
    @FXML
    void clearCrashDirection() {
        toggleCheckComboBox(crashDirectionBox, crashDirectionIcon);
    }
    @FXML
    void clearVehicleDirection() {
        toggleCheckComboBox(vehicleDirectionBox, vehicleDirectionIcon);
    }
    @FXML
    void clearLaneConfig() {
        toggleCheckComboBox(laneConfigBox, laneConfigIcon);
    }
    @FXML
    void clearRoadCharacter() {
        toggleCheckComboBox(roadCharBox, roadCharIcon);
    }
    @FXML
    void clearRoadSurface() {
        toggleCheckComboBox(roadSurfaceBox, roadSurfaceIcon);
    }


    /**
     * A generalized function to either clear all, or select all items within a CheckComboBox from the GUI.
     * Called by the +/- button beside each CheckComboBox in the search GUI.
     * The function will clear all selected items from the box if at least one has been selected,
     * Otherwise it will select all items from the box.
     * It will also update the icon of the add/remove all button to logically reflect the next possible action.
     * @param toggleBox The box to toggle selections from
     * @param buttonIcon The icon from the button to update
     */
    private void toggleCheckComboBox(CheckComboBox toggleBox, FontIcon buttonIcon) {
        if (!toggleBox.getCheckModel().getCheckedItems().isEmpty()) {
            toggleBox.getCheckModel().clearChecks();
            buttonIcon.setIconLiteral("mdi2p-plus");

        } else {
            toggleBox.getCheckModel().checkAll();
            buttonIcon.setIconLiteral("mdi2m-minus");
        }
    }

    /**
     * Initializes the checkboxes for enabling/disabling range filters in the search GUI.
     * First, it checks the existing search object for any saved parameters to load back in,
     * Then it creates listeners for the discrete ranges (those represented by ComboBoxes) to either update the search
     * object with the combobox values upon enabling the range, or to set the search values back to null on disabling it.
     * Then it creates listeners for the textual ranges (those represented by TextFields) to either intentionally trip
     * their listeners so the strings in them can be reprocessed and added to the search if syntactically valid if
     * enabled, or sets the search values to null if disabled.
     */
    private void initRangeCheckBoxes() {
        if (search.getStartYear() != null) {
            yearCheckBox.setSelected(true);
            yearHBox.setDisable(false);
            startYearComboBox.getSelectionModel().select(String.valueOf(search.getStartYear()));
            endYearComboBox.getSelectionModel().select(String.valueOf(search.getEndYear()));
        }

        if (search.getStartSpeedLimit() != null) {
            speedLimitCheckBox.setSelected(true);
            speedLimitHBox.setDisable(false);
            startSpeedLimitBox.getSelectionModel().select(String.valueOf(search.getStartSpeedLimit()));
            endSpeedLimitBox.getSelectionModel().select(String.valueOf(search.getEndSpeedLimit()));
        }

        if (search.getStartAdvisedSpeed() != null) {
            advisedSpeedCheckBox.setSelected(true);
            advisedSpeedHBox.setDisable(false);
            advisedStartBox.getSelectionModel().select(String.valueOf(search.getStartAdvisedSpeed()));
            advisedEndBox.getSelectionModel().select(String.valueOf(search.getEndAdvisedSpeed()));
        }

        if (search.getStartTemporarySpeed() != null) {
            tempSpeedCheckBox.setSelected(true);
            tempSpeedHBox.setDisable(false);
            temporaryStartBox.getSelectionModel().select(String.valueOf(search.getStartTemporarySpeed()));
            temporaryEndBox.getSelectionModel().select(String.valueOf(search.getEndTemporarySpeed()));
        }

        if (search.getStartLanes() != null) {
            numLanesCheckBox.setSelected(true);
            numLanesHBox.setDisable(false);
            laneStartBox.getSelectionModel().select(String.valueOf(search.getStartLanes()));
            laneEndBox.getSelectionModel().select(String.valueOf(search.getEndLanes()));
        }

        if (search.getStartDeaths() != null) {
            deathsCheckBox.setSelected(true);
            deathsHBox.setDisable(false);
            startDeathsField.setText(String.valueOf(search.getStartDeaths()));
            endDeathsField.setText(String.valueOf(search.getEndDeaths()));
        }

        if (search.getStartMinorInjuries() != null) {
            minorInjuryCheckBox.setSelected(true);
            minorInjuryHBox.setDisable(false);
            startMinorInjuriesField.setText(String.valueOf(search.getStartMinorInjuries()));
            endMinorInjuriesField.setText(String.valueOf(search.getEndMinorInjuries()));
        }

        if (search.getStartMajorInjuries() != null) {
            majorInjuryCheckBox.setSelected(true);
            majorInjuryHBox.setDisable(false);
            startMajorInjuriesField.setText(String.valueOf(search.getStartMajorInjuries()));
            endMajorInjuriesField.setText(String.valueOf(search.getEndMajorInjuries()));
        }

        yearCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                yearHBox.setDisable(false);
                search.setStartYear(Integer.valueOf(startYearComboBox.getSelectionModel().getSelectedItem().toString()));
                search.setEndYear(Integer.valueOf(endYearComboBox.getSelectionModel().getSelectedItem().toString()));
                search.handleSearchChange();
            } else {
                yearHBox.setDisable(true);
                search.setStartYear(null);
                search.setEndYear(null);
                search.handleSearchChange();
            }
        });

        speedLimitCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                speedLimitHBox.setDisable(false);
                search.setStartSpeedLimit(Integer.valueOf(startSpeedLimitBox.getSelectionModel().getSelectedItem().toString()));
                search.setEndSpeedLimit(Integer.valueOf(endSpeedLimitBox.getSelectionModel().getSelectedItem().toString()));
                search.handleSearchChange();
            } else {
                speedLimitHBox.setDisable(true);
                search.setStartSpeedLimit(null);
                search.setEndSpeedLimit(null);
                search.handleSearchChange();
            }
        });

        advisedSpeedCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                advisedSpeedHBox.setDisable(false);
                search.setStartAdvisedSpeed(Integer.valueOf(advisedStartBox.getSelectionModel().getSelectedItem().toString()));
                search.setEndAdvisedSpeed(Integer.valueOf(advisedEndBox.getSelectionModel().getSelectedItem().toString()));
                search.handleSearchChange();
            } else {
                advisedSpeedHBox.setDisable(true);
                search.setStartAdvisedSpeed(null);
                search.setEndAdvisedSpeed(null);
                search.handleSearchChange();
            }
        });

        tempSpeedCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                tempSpeedHBox.setDisable(false);
                search.setTemporaryStartLimit(Integer.valueOf(temporaryStartBox.getSelectionModel().getSelectedItem().toString()));
                search.setTemporaryEndLimit(Integer.valueOf(temporaryEndBox.getSelectionModel().getSelectedItem().toString()));
                search.handleSearchChange();
            } else {
                tempSpeedHBox.setDisable(true);
                search.setTemporaryStartLimit(null);
                search.setTemporaryEndLimit(null);
                search.handleSearchChange();
            }
        });

        numLanesCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                numLanesHBox.setDisable(false);
                search.setStartLanes(Integer.valueOf(temporaryStartBox.getSelectionModel().getSelectedItem().toString()));
                search.setEndLanes(Integer.valueOf(temporaryEndBox.getSelectionModel().getSelectedItem().toString()));
                search.handleSearchChange();
            } else {
                numLanesHBox.setDisable(true);
                search.setStartLanes(null);
                search.setEndLanes(null);
                search.handleSearchChange();
            }
        });

        deathsCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                deathsHBox.setDisable(false);
                String startString = startDeathsField.getText();
                startDeathsField.setText("");
                startDeathsField.setText(startString);
                String endString = endDeathsField.getText();
                endDeathsField.setText("");
                endDeathsField.setText(endString);
                search.handleSearchChange();
            } else {
                deathsHBox.setDisable(true);
                search.setStartDeaths(null);
                search.setEndDeaths(null);
                search.handleSearchChange();
            }
        });

        minorInjuryCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                minorInjuryHBox.setDisable(false);
                String startString = startMinorInjuriesField.getText();
                startMinorInjuriesField.setText("");
                startMinorInjuriesField.setText(startString);
                String endString = endMinorInjuriesField.getText();
                endMinorInjuriesField.setText("");
                endMinorInjuriesField.setText(endString);
                search.handleSearchChange();
            } else {
                minorInjuryHBox.setDisable(true);
                search.setStartMinorInjuries(null);
                search.setEndMinorInjuries(null);
                search.handleSearchChange();
            }
        });

        majorInjuryCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                majorInjuryHBox.setDisable(false);
                String startString = startMajorInjuriesField.getText();
                startMajorInjuriesField.setText("");
                startMajorInjuriesField.setText(startString);
                String endString = endMajorInjuriesField.getText();
                endMajorInjuriesField.setText("");
                endMajorInjuriesField.setText(endString);
                search.handleSearchChange();
            } else {
                majorInjuryHBox.setDisable(true);
                search.setStartMajorInjuries(null);
                search.setEndMajorInjuries(null);
                search.handleSearchChange();
            }
        });
    }

    /**
     * Initializes the titledPanes such that their open/shut state can be preserved between sessions.
     * Creates listeners to their expanded property to do this.
     */
    private void initTitledPanes() {
        locationPane.setExpanded(search.getLocationPaneOpen());
        basicPane.setExpanded(search.getBasicPaneOpen());
        advancedPane.setExpanded(search.getAdvancedPaneOpen());
        conditionalPane.setExpanded(search.getConditionalPaneOpen());
        locationPane.expandedProperty().addListener((observable, oldValue, newValue) -> {
            search.setLocationPaneOpen(newValue);
        });
        basicPane.expandedProperty().addListener((observable, oldValue, newValue) -> {
            search.setBasicPaneOpen(newValue);
        });
        advancedPane.expandedProperty().addListener((observable, oldValue, newValue) -> {
            search.setAdvancedPaneOpen(newValue);
        });
        conditionalPane.expandedProperty().addListener((observable, oldValue, newValue) -> {
            search.setConditionalPaneOpen(newValue);
        });
    }

    /**
     * Each CheckComboBox (multi-selection combobox) is initialized here.
     * Firstly, the logical set of string values is added to the box.
     * Then, the option to display the checked count is enabled (i.e. Severity -> Severity (0/4))
     * Next, any stored values for the box are rechecked to preserve state.
     * Finally, each box has a listener instated, by calling the generic function for creating CheckComboBox listeners.
     */
    private void initCheckComboBoxes() {
        severityComboBox.getItems().addAll(Stream.of(TableColumns.Severity.values()).map(e -> e.name).toList());
        severityComboBox.setShowCheckedCount(true);
        for (String csvSeverity : search.getSeverities()) {
            String severity = TableColumns.Severity.getNameFromCSVName(csvSeverity);
            int index = severityComboBox.getCheckModel().getItemIndex(severity);
            severityComboBox.getCheckModel().check(index);
        }
        initCheckComboBoxListener(severityComboBox, "severity", severityIcon);

        regionComboBox.getItems().addAll(Stream.of(TableColumns.Region.values()).map(e -> e.name).toList());
        regionComboBox.setShowCheckedCount(true);
        for (String csvRegion : search.getRegions()) {
            String region = TableColumns.Region.getNameFromCSVName(csvRegion);
            int index = regionComboBox.getCheckModel().getItemIndex(region);
            regionComboBox.getCheckModel().check(index);
        }
        initCheckComboBoxListener(regionComboBox, "region", regionIcon);

        participantComboBox.getItems().addAll(Stream.of(TableColumns.Participants.values()).map(e -> e.name).toList());
        participantComboBox.setShowCheckedCount(true);
        for (String dbParticipant : search.getParticipants()) {
            String participant = TableColumns.Participants.getNameFromDBName(dbParticipant);
            int index = participantComboBox.getCheckModel().getItemIndex(participant);
            participantComboBox.getCheckModel().check(index);
        }
        initCheckComboBoxListener(participantComboBox, "participants", participantsIcon);

        weatherComboBoxA.getItems().addAll(Stream.of(TableColumns.WeatherA.values()).map(e -> e.name).toList());
        weatherComboBoxA.setShowCheckedCount(true);
        for (String csvWeatherA : search.getWeatherA()) {
            String weather = TableColumns.WeatherA.getNameFromCSVName(csvWeatherA);
            int index = weatherComboBoxA.getCheckModel().getItemIndex(weather);
            weatherComboBoxA.getCheckModel().check(index);
        }
        initCheckComboBoxListener(weatherComboBoxA, "weatherA", weatherAIcon);

        weatherComboBoxB.getItems().addAll(Stream.of(TableColumns.WeatherB.values()).map(e -> e.name).toList());
        weatherComboBoxB.setShowCheckedCount(true);
        for (String csvWeatherB : search.getWeatherB()) {
            String weather = TableColumns.WeatherB.getNameFromCSVName(csvWeatherB);
            int index = weatherComboBoxB.getCheckModel().getItemIndex(weather);
            weatherComboBoxB.getCheckModel().check(index);
        }
        initCheckComboBoxListener(weatherComboBoxB, "weatherB", weatherBIcon);

        lightComboBox.getItems().addAll(Stream.of(TableColumns.Light.values()).map(e -> e.name).toList());
        lightComboBox.setShowCheckedCount(true);
        for (String light : search.getLight()) {
            int index = lightComboBox.getCheckModel().getItemIndex(light);
            lightComboBox.getCheckModel().check(index);
        }
        initCheckComboBoxListener(lightComboBox, "light", lightIcon);

        holidayComboBox.getItems().addAll(Stream.of(TableColumns.Holiday.values()).map(e -> e.name).toList());
        holidayComboBox.setShowCheckedCount(true);
        for (String csvHoliday : search.getHolidays()) {
            String holiday = TableColumns.Holiday.getNameFromCSVName(csvHoliday);
            int index = holidayComboBox.getCheckModel().getItemIndex(holiday);
            holidayComboBox.getCheckModel().check(index);
        }
        initCheckComboBoxListener(holidayComboBox, "holiday", holidayIcon);

        tlaComboBox.getItems().addAll(Stream.of(TableColumns.LocalAuthority.values()).map(e -> e.name).toList());
        tlaComboBox.setShowCheckedCount(true);
        for (String csvTLA : search.getLocalAuthorities()) {
            String tla = TableColumns.LocalAuthority.getNameFromCSVName(csvTLA);
            int index = tlaComboBox.getCheckModel().getItemIndex(tla);
            tlaComboBox.getCheckModel().check(index);
        }
        initCheckComboBoxListener(tlaComboBox, "tla", tlaIcon);

        trafficCtrlBox.getItems().addAll(Stream.of(TableColumns.TrafficControl.values()).map(e -> e.name).toList());
        trafficCtrlBox.setShowCheckedCount(true);
        for (String csvControl : search.getTrafficControls()) {
            String control = TableColumns.TrafficControl.getNameFromCSVName(csvControl);
            int index = trafficCtrlBox.getCheckModel().getItemIndex(control);
            trafficCtrlBox.getCheckModel().check(index);
        }
        initCheckComboBoxListener(trafficCtrlBox, "trafficcontrol", traffControlIcon);

        collisionComboBox.getItems().addAll(Stream.of(TableColumns.Collisions.values()).map(e -> e.name).toList());
        collisionComboBox.setShowCheckedCount(true);
        for (String dbCollision : search.getCollisions()) {
            String collision = TableColumns.Collisions.getNameFromDBName(dbCollision);
            int index = collisionComboBox.getCheckModel().getItemIndex(collision);
            collisionComboBox.getCheckModel().check(index);
        }
        initCheckComboBoxListener(collisionComboBox, "collisions", collisionsIcon);

        crashDirectionBox.getItems().addAll(Stream.of(TableColumns.Direction.values()).map(e -> e.name).toList());
        crashDirectionBox.setShowCheckedCount(true);
        for (String direction : search.getCrashDirections()) {
            int index = crashDirectionBox.getCheckModel().getItemIndex(direction);
            crashDirectionBox.getCheckModel().check(index);
        }
        initCheckComboBoxListener(crashDirectionBox, "crashdirection", crashDirectionIcon);

        vehicleDirectionBox.getItems().addAll(Stream.of(TableColumns.Direction.values()).map(e -> e.name).toList());
        vehicleDirectionBox.setShowCheckedCount(true);
        for (String direction : search.getVehicleDirections()) {
            int index = vehicleDirectionBox.getCheckModel().getItemIndex(direction);
            vehicleDirectionBox.getCheckModel().check(index);
        }
        initCheckComboBoxListener(vehicleDirectionBox, "vehicledirection", vehicleDirectionIcon);

        laneConfigBox.getItems().addAll(Stream.of(TableColumns.LaneConfiguration.values()).map(e -> e.name).toList());
        laneConfigBox.setShowCheckedCount(true);
        for (String csvConfig : search.getLaneConfigs()) {
            String config = TableColumns.LaneConfiguration.getNameFromCSVName(csvConfig);
            int index = laneConfigBox.getCheckModel().getItemIndex(config);
            laneConfigBox.getCheckModel().check(index);
        }
        initCheckComboBoxListener(laneConfigBox, "laneconfig", laneConfigIcon);

        roadCharBox.getItems().addAll(Stream.of(TableColumns.RoadCharacter.values()).map(e -> e.name).toList());
        roadCharBox.setShowCheckedCount(true);
        for (String csvCharacter : search.getRoadCharacters()) {
            String character = TableColumns.RoadCharacter.getNameFromCSVName(csvCharacter);
            int index = roadCharBox.getCheckModel().getItemIndex(character);
            roadCharBox.getCheckModel().check(index);
        }
        initCheckComboBoxListener(roadCharBox, "roadcharacter", roadCharIcon);

        roadSurfaceBox.getItems().addAll(Stream.of(TableColumns.RoadSurface.values()).map(e -> e.name).toList());
        roadSurfaceBox.setShowCheckedCount(true);
        for (String surface : search.getRoadSurfaces()) {
            int index = roadSurfaceBox.getCheckModel().getItemIndex(surface);
            roadSurfaceBox.getCheckModel().check(index);
        }
        initCheckComboBoxListener(roadSurfaceBox, "roadsurface", roadSurfaceIcon);

        participantAndToggle.selectedProperty().addListener((obs, old, value) -> {
            if (value) {
                participantAndToggle.setText("All");
                search.setParticipantAnd(true);
                search.handleSearchChange();
            } else {
                participantAndToggle.setText("Any");
                search.setParticipantAnd(false);
                search.handleSearchChange();
            }
        });
        collisionAndToggle.selectedProperty().addListener((obs, old, value) -> {
            if (value) {
                collisionAndToggle.setText("All");
                search.setCollisionAnd(true);
                search.handleSearchChange();
            } else {
                collisionAndToggle.setText("Any");
                search.setCollisionAnd(false);
                search.handleSearchChange();
            }
        });
    }

    /**
     * Generic function to create listeners for each CheckComboBox dropdown.
     * Called by initCheckComboBoxes().
     * When items are added/removed from the check model, a corresponding function to handle their addition/removal is
     * called in search.
     * As the add/remove shortcut button functionality is determined by the number of items selected in the box,
     * its icon is updated here as required.
     * If no items are selected, the CheckComboBox also has its opacity set to 50%, to visually indicate the filter is inactive.
     * @param checkComboBox The checkComboBox to create the listener for
     * @param valueType A string representation of the value type, passed to the search function
     * @param buttonIcon The icon of the add/remove button to update
     */
    private void initCheckComboBoxListener(CheckComboBox checkComboBox, String valueType, FontIcon buttonIcon) {
        checkComboBox.getCheckModel().getCheckedItems().addListener((ListChangeListener<String>) c -> {
            while (c.next()) {
                List<? extends String> added = c.getAddedSubList();
                List<? extends String> removed = c.getRemoved();
                for (String item : added) {
                    search.addFromCheckComboBox(valueType, item);
                }
                for (String item : removed) {
                    search.removeFromCheckComboBox(valueType, item);
                }
                if (!checkComboBox.getCheckModel().getCheckedItems().isEmpty()) {
                    buttonIcon.setIconLiteral("mdi2m-minus");
                    checkComboBox.setOpacity(1.0);
                    if (checkComboBox.getId() == participantComboBox.getId()) {
                        participantAndToggle.setOpacity(1.0);
                    } else if (checkComboBox.getId() == collisionComboBox.getId()) {
                        collisionAndToggle.setOpacity(1.0);
                    }
                } else {
                    buttonIcon.setIconLiteral("mdi2p-plus");
                    checkComboBox.setOpacity(0.67);
                    if (checkComboBox.getId() == participantComboBox.getId()) {
                        participantAndToggle.setOpacity(0.67);
                    } else if (checkComboBox.getId() == collisionComboBox.getId()) {
                        collisionAndToggle.setOpacity(0.67);
                    }
                }
                search.handleSearchChange();
            }
        });
    }

    /**
     * Initializes all the comboBox objects in the UI.
     * Each comboBox has its values generated from a logical range and increment through streams,
     * then the corresponding search value stored is selected, or set to default first/last values.
     */
    private void initComboBoxes() {
        startYearComboBox.getItems().addAll(IntStream.range(2000, 2023+1).boxed().toList());
        if (search.getStartYear() == null) {
            startYearComboBox.getSelectionModel().selectFirst();
        } else {
            int index = search.getStartYear() - 2000;
            startYearComboBox.getSelectionModel().select(index);
        }
        endYearComboBox.getItems().addAll(IntStream.range(2000, 2023+1).boxed().toList());
        if (search.getEndYear() == null) {
            endYearComboBox.getSelectionModel().selectLast();
        } else {
            int index = search.getEndYear() - 2000;
            endYearComboBox.getSelectionModel().select(index);
        }

        startSpeedLimitBox.getItems().addAll(IntStream.range(0, 110/5 + 1).map(e -> e * 5).boxed().toList());
        if (search.getStartSpeedLimit() == null) {
            startSpeedLimitBox.getSelectionModel().selectFirst();
        } else {
            int index = search.getStartSpeedLimit() / 5;
            startSpeedLimitBox.getSelectionModel().select(index);
        }
        endSpeedLimitBox.getItems().addAll(IntStream.range(0, 110/5 + 1).map(e -> e * 5).boxed().toList());
        if (search.getEndSpeedLimit() == null) {
            endSpeedLimitBox.getSelectionModel().selectLast();
        } else {
            int index = search.getEndSpeedLimit() / 5;
            endSpeedLimitBox.getSelectionModel().select(index);
        }

        advisedStartBox.getItems().addAll(IntStream.range(0, 110/5 + 1).map(e -> e * 5).boxed().toList());
        if (search.getStartAdvisedSpeed() == null) {
            advisedStartBox.getSelectionModel().selectFirst();
        } else {
            int index = search.getStartAdvisedSpeed() / 5;
            advisedStartBox.getSelectionModel().select(index);
        }
        advisedEndBox.getItems().addAll(IntStream.range(0, 110/5 + 1).map(e -> e * 5).boxed().toList());
        if (search.getEndAdvisedSpeed() == null) {
            advisedEndBox.getSelectionModel().selectLast();
        } else {
            int index = search.getEndAdvisedSpeed() / 5;
            advisedEndBox.getSelectionModel().select(index);
        }

        temporaryStartBox.getItems().addAll(IntStream.range(0, 110/5 + 1).map(e -> e * 5).boxed().toList());
        if (search.getStartTemporarySpeed() == null) {
            temporaryStartBox.getSelectionModel().selectFirst();
        } else {
            int index = search.getStartAdvisedSpeed() / 5;
            temporaryStartBox.getSelectionModel().select(index);
        }
        temporaryEndBox.getItems().addAll(IntStream.range(0, 110/5 + 1).map(e -> e * 5).boxed().toList());
        if (search.getEndTemporarySpeed() == null) {
            temporaryEndBox.getSelectionModel().selectLast();
        } else {
            int index = search.getEndTemporarySpeed() / 5;
            temporaryEndBox.getSelectionModel().select(index);
        }

        laneStartBox.getItems().addAll(IntStream.range(0, 9+1).boxed().toList());
        if (search.getStartLanes() == null) {
            laneStartBox.getSelectionModel().selectFirst();
        } else {
            int index = search.getStartLanes();
            laneStartBox.getSelectionModel().select(index);
        }
        laneEndBox.getItems().addAll(IntStream.range(0, 9+1).boxed().toList());
        if (search.getEndLanes() == null) {
            laneEndBox.getSelectionModel().selectLast();
        } else {
            int index = search.getEndLanes();
            laneEndBox.getSelectionModel().select(index);
        }
    }

    /**
     * Initializes the checkboxes in the UI.
     * Checkboxes are indeterminate -- they support true, false, and null values.
     * The checkboxes are set to null/indeterminate by default, so that they are not considered in the search.
     * Listeners are then created by a generic call for each checkbox.
     */
    private void initIndeterminateCheckBoxes() {
        shCheckBox.setAllowIndeterminate(true);
        if (search.getStateHighway() == null) {
            shCheckBox.setIndeterminate(true);
        } else {
            shCheckBox.setSelected(search.getStateHighway());
        }
        initCheckBoxListener(shCheckBox, "sh");

        hillCheckBox.setAllowIndeterminate(true);
        if (search.getHill() == null) {
            hillCheckBox.setIndeterminate(true);
        } else {
            hillCheckBox.setSelected(search.getHill());
        }
        initCheckBoxListener(hillCheckBox, "hill");

        lightsCheckBox.setAllowIndeterminate(true);
        if (search.getStreetLights() == null) {
            lightsCheckBox.setIndeterminate(true);
        } else {
            lightsCheckBox.setSelected(search.getStreetLights());
        }
        initCheckBoxListener(lightsCheckBox, "lights");

    }

    /**
     * Creates changeListeners for checkboxes by generic criteria.
     * Indeterminate checkboxes require two change listeners:
     * The first listens for a boolean value, true or false, and updates search appropriately.
     * The second listens for the indeterminate value, and if present updates search with null.
     * @param checkBox Checkbox object to listen to
     * @param valueType String representation of the value to update in search
     */
    private void initCheckBoxListener(CheckBox checkBox, String valueType) {
        checkBox.selectedProperty().addListener((observe, oldVal, newVal) -> {
            search.setCheckBox(valueType, newVal);
            search.handleSearchChange();
        });
        checkBox.indeterminateProperty().addListener((observe, oldVal, newVal) -> {
            if (newVal) {
                search.setCheckBox(valueType, null);
                search.handleSearchChange();
            }
        });
    }

    /**
     * Called by the UI when the "Match All" toggle is clicked.
     * It passes the boolean state of the toggleButton into the search object, and updates the label of the toggle
     * so the user knows which option is currently active.
     */
    @FXML
    void handleMatchToggleButton() {
        boolean state = matchToggleButton.isSelected();
        if (state) {
            matchToggleButton.setText("Match all terms");
        } else {
            matchToggleButton.setText("Match any terms");
        }
        search.setMatchAll(state);
        search.handleSearchChange();
    }

    /**
     * Checks the search object for existing conditionals.
     * If none are present, populates the conditional pane with an add button.
     * Else, creates pre-filled conditional boxes from the search object,
     * and sets the conditional index to one above the highest already present.
     */
    private void initConditionals() {
        if (search.getConditionals().size() != 0) {
            for (Map.Entry<Integer, Conditional> map : search.getConditionals().entrySet()) {
                Integer id = map.getKey();
                if (id > conditionalIndex) {
                    conditionalIndex = id + 1;
                }
                HBox conditionalBox = createConditionalBox(id);
                conditionalsContainer.getChildren().add(conditionalBox);
            }
        }
        HBox addBox = createAddButton();
        conditionalsContainer.getChildren().add(addBox);
    }

    /**
     * Creates an addConditional button, places it in an HBox, and returns it.
     * The button consists of no text and a fontIcon representing a "+" sign.
     * The button is bound to the action handleAddConditional().
     * @return HBox object containing the addConditional button
     */
    private HBox createAddButton() {
        Button add = new Button();
        FontIcon icon = new FontIcon();
        icon.setIconSize(20);
        icon.setIconLiteral("mdi2p-plus");
        add.setGraphic(icon);
        add.setOnAction(e -> handleAddConditional());
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);
        container.getChildren().add(add);
        return container;
    }

    /**
     * Manually constructs a conditional box and returns it.
     * The conditional box is consistent of field and operator dropdowns, along with two value fields.
     * Only one of the value fields is typically visible, though switching to "between" in the operator will show two.
     * Each field has a listener to update the corresponding conditional by ID in the search object.
     * Each conditional box also contains a remove button on their right-hand side.
     * The construction of conditional listeners is performed in reverse order, as their logic depends on their
     * predecessor. For instance, the valid items for the operator combobox are determined by whether the selected
     * field is a string or integer type.
     * @param id: ID of the conditional to construct a box from. Call null to construct a fresh conditional,
     *          or fill it to derive existing values from a stored conditional.
     * @return HBox containing the conditional UI objects.
     */
    private HBox createConditionalBox(Integer id) {
        if (id == null) {
            id = conditionalIndex;
        }

        HBox valueBox = new HBox();
        TextField valueField = new TextField();
        valueField.setMinWidth(80);
        valueField.setPrefWidth(250.0);
        valueField.setPromptText("Value");
        valueField.setId(String.valueOf(id));
        TextField value2Field = new TextField();
        value2Field.setMinWidth(0.0);
        value2Field.setPrefWidth(0.0);
        value2Field.setVisible(false);
        value2Field.setPromptText("Value 2");
        value2Field.setId(String.valueOf(id));
        valueBox.getChildren().addAll(valueField, value2Field);
        HBox.setHgrow(valueField, Priority.ALWAYS);
        HBox.setHgrow(value2Field, Priority.ALWAYS);
        valueField.textProperty().addListener((observable, oldValue, newValue) -> {
            String conditionId = valueField.getId();
            Conditional newConditional = search.getConditional(Integer.valueOf(conditionId));
            newConditional.setValue(newValue);
            search.handleSearchChange();
        });
        value2Field.textProperty().addListener((observable, oldValue, newValue) -> {
            String conditionId = value2Field.getId();
            Conditional newConditional = search.getConditional(Integer.valueOf(conditionId));
            newConditional.setValue2(newValue);
            search.handleSearchChange();
        });

        ComboBox operatorBox = new ComboBox();
        operatorBox.setPrefWidth(250.0);
        operatorBox.setPromptText("Operator");
        operatorBox.setId(String.valueOf(id));
        operatorBox.getItems().addAll(Stream.of(Conditional.Operator.values()).map(e -> e.displayString).toList());
        operatorBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            String conditionID = operatorBox.getId();
            Conditional.Operator newOperator = Conditional.Operator.getOperatorFromDisplayString((String) newValue);
            Conditional condition = search.getConditional(Integer.valueOf(conditionID));
            condition.setOperator(newOperator);
            search.addConditional(Integer.valueOf(conditionID), condition);
            search.handleSearchChange();
            if (newOperator == Conditional.Operator.BETWEEN) {
                value2Field.setMinWidth(80);
                value2Field.setPrefWidth(80);
                value2Field.setVisible(true);
                valueField.setMinWidth(80);
                valueField.setPrefWidth(80);
            } else {
                value2Field.setMinWidth(0.0);
                value2Field.setPrefWidth(0.0);
                value2Field.setVisible(false);
                valueField.setPrefWidth(250.0);
            }
        });

        ComboBox fieldBox = new ComboBox();
        fieldBox.setPrefWidth(250.0);
        fieldBox.setPromptText("Field");
        fieldBox.setId(String.valueOf(id));
        fieldBox.getItems().addAll(Stream.of(TableColumns.Column.values()).map(e -> e.label).toList());
        fieldBox.getItems().remove(TableColumns.Column.LATITUDE.getLabel());
        fieldBox.getItems().remove(TableColumns.Column.LONGITUDE.getLabel());
        fieldBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            String conditionID = fieldBox.getId();
            TableColumns.Column field = TableColumns.Column.getColumnFromLabel((String) newValue);
            Conditional condition = search.getConditional(Integer.valueOf(conditionID));
            condition.setColumn(field);
            if (Objects.requireNonNull(field).getType() == String.class) {
                Conditional.Operator selectedOperator = null;
                if (operatorBox.getSelectionModel().getSelectedItem() != null) {
                    selectedOperator = Conditional.Operator.getOperatorFromDisplayString(operatorBox.getSelectionModel().getSelectedItem().toString());
                }
                operatorBox.getSelectionModel().clearSelection();
                operatorBox.getItems().clear();
                operatorBox.getItems().addAll(Stream.of(Conditional.Operator.values()).filter(Conditional.Operator::getCanBeString).map(e -> e.displayString).toList());
                if (selectedOperator != null) {
                    if (Stream.of(Conditional.Operator.values()).filter(Conditional.Operator::getCanBeString).map(e -> e.displayString).toList().contains(selectedOperator.getDisplayString())) {
                        operatorBox.getSelectionModel().select(selectedOperator.getDisplayString());
                    } else {
                        condition.setOperator(null);
                    }
                }
            } else {
                Object tempSelection = operatorBox.getSelectionModel().getSelectedItem();
                operatorBox.getItems().clear();
                operatorBox.getItems().addAll(Stream.of(Conditional.Operator.values()).map(e -> e.displayString).toList());
                operatorBox.getSelectionModel().select(tempSelection);
            }
            search.addConditional(Integer.valueOf(conditionID), condition);
            search.handleSearchChange();
        });

        if (search.getConditional(id) != null) {
            Conditional existingCondition = search.getConditional(id);
            TableColumns.Column existingColumn = existingCondition.getColumn();
            Conditional.Operator existingOperator = existingCondition.getOperator();
            String existingValue = existingCondition.getValue();
            String existingValue2 = existingCondition.getValue2();
            if (existingColumn != null) {
                fieldBox.getSelectionModel().select(existingColumn.getLabel());
            } if (existingOperator != null) {
                operatorBox.getSelectionModel().select(existingOperator.getDisplayString());
                if (existingOperator == Conditional.Operator.BETWEEN) {
                    value2Field.setMinWidth(80);
                    value2Field.setPrefWidth(80);
                    value2Field.setVisible(true);
                    valueField.setMinWidth(80);
                    valueField.setPrefWidth(80);
                }
            } if (existingValue != null) {
                valueField.setText(existingValue);
            } if (existingValue2 != null) {
                value2Field.setText(existingValue2);
            }
        }

        VBox fields = new VBox();
        fields.getChildren().addAll(fieldBox, operatorBox, valueBox);

        Button remove = new Button();
        remove.setPrefHeight(76.0);
        FontIcon icon = new FontIcon();
        icon.setIconSize(20);
        icon.setIconLiteral("mdi2m-minus");
        remove.setGraphic(icon);
        remove.setOnAction(this::handleRemoveConditional);

        HBox conditionalBox = new HBox();
        conditionalBox.getChildren().addAll(fields, remove);
        conditionalBox.setId(String.valueOf(id));
        conditionalBox.setPadding(new Insets(0,0,3,0));

        return conditionalBox;
    }

    /**
     * Clicking the addConditional button at the bottom of the conditional pane calls this handler function,
     * which in turn calls addConditional.
     */
    @FXML
    void handleAddConditional() {addConditional();}

    /**
     * Create a fresh conditional and add it to the conditional panel.
     * This is done by first removing the add button from the container.
     * Then, we create a new conditionalBox and addButton, and add them both back into the container.
     * The search object also has a conditional object of corresponding ID added.
     * Each time a conditional is created, the ID increments by 1 to ensure conditionals are handled discretely.
     * This would cause problems if the user created two billion+ conditionals, but we're not really worried about that.
     */
    private void addConditional() {
        int lastIndex = conditionalsContainer.getChildren().size() - 1;
        conditionalsContainer.getChildren().remove(lastIndex);
        HBox conditionalBox = createConditionalBox(null);
        conditionalsContainer.getChildren().add(conditionalBox);
        HBox addBox = createAddButton();
        conditionalsContainer.getChildren().add(addBox);
        Conditional conditional = new Conditional(null, null, null, null);
        search.addConditional(conditionalIndex, conditional);
        conditionalIndex += 1;
        search.handleSearchChange();
    }

    /**
     * Handles a call to the "-" button of a conditional.
     * Firstly, the ID of the conditional to be removed is obtained from tracing the source of the event.
     * Then, we pass the ID to the removeConditional function.
     * @param e
     */
    void handleRemoveConditional(Event e) {
        Button temp = (Button) e.getSource();
        String id = temp.getParent().getId();
        removeConditional(Integer.valueOf(id));
    }

    /**
     * Remove a conditional by its specified ID.
     * The conditional is removed both from the container, and from the search object's conditional array.
     * @param id ID value of the conditional to remove.
     */
    private void removeConditional(Integer id) {
        conditionalsContainer.getChildren().removeIf(e -> Objects.equals(e.getId(), String.valueOf(id)));
        search.removeConditional(id);
        search.handleSearchChange();
    }

    /**
     * Obtains the integer value corresponding to the comboBox field, parses it as an integer,
     * and passes it to the setStartRange function for logical checks.
     * Then modifies the search object to reflect it.
     * @param e Unused event object
     */
    @FXML
    void handleStartYearChange(Event e) {
        Integer year = (Integer) startYearComboBox.getValue();
        setStartRange(year, endYearComboBox, 2000, 1);
        search.setStartYear(year);
        search.handleSearchChange();
    }
    /**
     * Obtains the integer value corresponding to the comboBox field, parses it as an integer,
     * and passes it to the setStartRange function for logical checks.
     * Then modifies the search object to reflect it.
     * @param e Unused event object
     */
    @FXML
    void handleStartSpeedLimitChange(Event e) {
        Integer speed = (Integer) startSpeedLimitBox.getValue();
        setStartRange(speed, endSpeedLimitBox, 0, 5);
        search.setStartLimit(speed);
        search.handleSearchChange();
    }
    /**
     * Obtains the integer value corresponding to the comboBox field, parses it as an integer,
     * and passes it to the setStartRange function for logical checks.
     * Then modifies the search object to reflect it.
     * @param e Unused event object
     */
    @FXML
    void handleAdvisedStartChange(Event e) {
        Integer speed = (Integer) advisedStartBox.getValue();
        setStartRange(speed, advisedEndBox, 0, 5);
        search.setAdvisedStartLimit(speed);
        search.handleSearchChange();
    }
    /**
     * Obtains the integer value corresponding to the comboBox field, parses it as an integer,
     * and passes it to the setStartRange function for logical checks.
     * Then modifies the search object to reflect it.
     * @param e Unused event object
     */
    @FXML
    void handleTemporaryStartChange(Event e) {
        Integer speed = (Integer) temporaryStartBox.getValue();
        setStartRange(speed, temporaryEndBox, 0, 5);
        search.setTemporaryStartLimit(speed);
        search.handleSearchChange();
    }
    /**
     * Obtains the integer value corresponding to the comboBox field, parses it as an integer,
     * and passes it to the setStartRange function for logical checks.
     * Then modifies the search object to reflect it.
     * @param e Unused event object
     */
    @FXML
    void handleLaneStartChange(Event e) {
        Integer lanes = (Integer) laneStartBox.getValue();
        setStartRange(lanes, laneEndBox, 0, 1);
        search.setStartLanes(lanes);
        search.handleSearchChange();
    }

    /**
     * This function handles logical changes to comboBoxes representing the start of a range.
     * It updates the box representing the end of the range, so that illogical ranges cannot be represented.
     * (i.e. start year 2020 means the lowest end year should also be 2020).
     * It also updates a previously truncated box, restoring any missing values.
     * @param start Value selected in the startBox (i.e. 50 for speed limit)
     * @param endBox ComboBox representing the endValue input (i.e. endSpeedLimitBox)
     * @param lowerBound Lowest value possible in the range (i.e. 5 for speed limit)
     * @param increment Increment in the range (i.e. 5 for speed limit)
     */
    private void setStartRange(Integer start, ComboBox endBox, Integer lowerBound, Integer increment) {
        Integer endStart = (Integer) endBox.getItems().get(0);
        Integer end = (Integer) endYearComboBox.getSelectionModel().getSelectedItem();
        if (endStart > start) {
            endBox.getItems().addAll(0, IntStream.range(start / increment, endStart / increment).map(e -> e * increment).boxed().collect(Collectors.toList()));
        } else {
            endBox.getItems().removeAll(IntStream.range(lowerBound / increment, start / increment).map(e -> e * increment).boxed().collect(Collectors.toList()));
            if (end < start) {
                endBox.getSelectionModel().selectFirst();
            }
        }
    }

    /**
     * Obtains the integer value corresponding to the comboBox field, parses it as an integer,
     * and modifies the search object to reflect it.
     * @param e Unused event object
     */
    @FXML
    void handleEndYearChange(Event e) {setEndYear((Integer) endYearComboBox.getValue());}
    private void setEndYear(Integer year) {
        search.setEndYear(year);
        search.handleSearchChange();
    }
    /**
     * Obtains the integer value corresponding to the comboBox field, parses it as an integer,
     * and modifies the search object to reflect it.
     * @param e Unused event object
     */
    @FXML
    void handleEndSpeedLimitChange(Event e) {setEndSpeedLimit((Integer) endSpeedLimitBox.getValue());}
    private void setEndSpeedLimit(Integer speed) {
        search.setEndLimit(speed);
        search.handleSearchChange();
    }
    /**
     * Obtains the integer value corresponding to the comboBox field, parses it as an integer,
     * and modifies the search object to reflect it.
     * @param e Unused event object
     */
    @FXML
    void handleAdvisedEndChange(Event e) {setAdvisedEnd((Integer) advisedEndBox.getValue());}
    private void setAdvisedEnd(Integer speed) {
        search.setAdvisedEndLimit(speed);
        search.handleSearchChange();
    }
    /**
     * Obtains the integer value corresponding to the comboBox field, parses it as an integer,
     * and modifies the search object to reflect it.
     * @param e Unused event object
     */
    @FXML
    void handleTemporaryEndChange(Event e) {setTemporaryEnd((Integer) temporaryEndBox.getValue());}
    private void setTemporaryEnd(Integer speed) {
        search.setTemporaryEndLimit(speed);
        search.handleSearchChange();
    }
    /**
     * Obtains the integer value corresponding to the comboBox field, parses it as an integer,
     * and modifies the search object to reflect it.
     * @param e Unused event object
     */
    @FXML
    void handleLaneEndChange(Event e) {setLaneEnd((Integer) laneEndBox.getValue());}
    private void setLaneEnd(Integer lanes) {
        search.setEndLanes(lanes);
        search.handleSearchChange();
    }

    /**
     * Initializes the keyword field in the search panel, by adding a listener.
     * If the keyword isn't an empty string, it is stored in the search object,
     * while if it is an empty string, the search object's stored keyword is set to null.
     */
    private void initKeywordField() {
        if (search.getKeywords() != null) {
            keywordField.setText(search.getKeywords());
        }
        keywordField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                search.setKeywords(null);
                search.handleSearchChange();
            } else {

                search.setKeywords(newValue);
                search.handleSearchChange();
            }
        });
    }

    /**
     * Initializes the start range text fields for use in the search panel.
     * For each starting field, a listener is created monitoring the field's text property.
     * If the starting value is an empty string, the field is set to null in the search object (passed to generic function).
     * If the starting value is a positive integer or zero, we check the end value.
     *      If the start value is less than or equal to the end value, the start field is set in the search object.
     *      Otherwise, the start field is set to null in the search object.
     * If the end value isn't a valid integer, the start field is set in the search object.
     * This ensures that the range outlined in the text fields is always logically consistent.
     */
    private void initStartNumericalFields() {
        HashMap<TextField, TextField> startNumericalFields = new HashMap<>();
        startNumericalFields.put(startDeathsField, endDeathsField);
        startNumericalFields.put(startMinorInjuriesField, endMinorInjuriesField);
        startNumericalFields.put(startMajorInjuriesField, endMajorInjuriesField);

        if (search.getStartDeaths() != null) {
            startDeathsField.setText(search.getStartDeaths().toString());
        } if (search.getEndDeaths() != null) {
            endDeathsField.setText(search.getEndDeaths().toString());
        } if (search.getStartMinorInjuries() != null) {
            startMinorInjuriesField.setText(search.getStartMinorInjuries().toString());
        } if (search.getEndMinorInjuries() != null) {
            endMinorInjuriesField.setText(search.getEndMinorInjuries().toString());
        } if (search.getStartMajorInjuries() != null) {
            startMajorInjuriesField.setText(search.getStartMajorInjuries().toString());
        } if (search.getEndMajorInjuries() != null) {
            endMajorInjuriesField.setText(search.getEndMajorInjuries().toString());
        }

        startNumericalFields.forEach(
                (startField, endField) ->
                        startField.textProperty().addListener((observable, oldValue, newValue) -> {
                            if (newValue.isEmpty()) {
                                startField.getStyleClass().remove("error");
                                handleSetField(startField, null);
                            }else if (newValue.matches("\\d+")){
                                if (endField.getText().matches("\\d+")) {
                                    int upperBound = Integer.parseInt(endField.getText());
                                    if (Integer.parseInt(newValue) <= upperBound) {
                                        startField.getStyleClass().remove("error");
                                        handleSetField(startField, Integer.valueOf(newValue));
                                        handleSetField(endField, Integer.valueOf(endField.getText()));
                                    } else {
                                        log.warn("Invalid entry in numerical text field. Must be at most upper bound.");
                                        startField.getStyleClass().add("error");
                                        handleSetField(startField, null);
                                    }
                                } else {
                                    startField.getStyleClass().remove("error");
                                    handleSetField(startField, Integer.valueOf(newValue));
                                }
                            } else {
                                log.warn("Invalid entry in numerical text field. Must be positive or zero integer.");
                                startField.getStyleClass().add("error");
                                handleSetField(startField, null);
                            }
                        })
        );
    }

    /**
     * Initializes the end range text fields for use in the search panel.
     * For each starting field, a listener is created monitoring the field's text property.
     * If the ending value is an empty string, the field is set to null in the search object (passed to generic function).
     * If the ending value is a positive integer or zero, we check the start value.
     *      If the end value is greater than or equal to the start value, both fields are set in the search object.
     *      Otherwise, the end field is set to null in the search object.
     * If the start value isn't a valid integer, the end field is set in the search object.
     * This ensures that the range outlined in the text fields is always logically consistent.
     */
    private void initEndNumericalFields() {
        HashMap<TextField, TextField> endNumericalFields = new HashMap<>();
        endNumericalFields.put(endDeathsField, startDeathsField);
        endNumericalFields.put(endMinorInjuriesField, startMinorInjuriesField);
        endNumericalFields.put(endMajorInjuriesField, startMajorInjuriesField);

        endNumericalFields.forEach(
                (endField, startField) ->
                        endField.textProperty().addListener((observable, oldValue, newValue) -> {
                            if (newValue.isEmpty()) {
                                endField.getStyleClass().remove("error");
                                handleSetField(endField, null);
                            }else if (newValue.matches("\\d+")){
                                if (startField.getText().matches("\\d+")) {
                                    int lowerBound = Integer.parseInt(startField.getText());
                                    if (Integer.parseInt(newValue) >= lowerBound) {
                                        endField.getStyleClass().remove("error");
                                        handleSetField(endField, Integer.valueOf(newValue));
                                        handleSetField(startField, Integer.valueOf(startField.getText()));
                                    } else {
                                        log.warn("Invalid entry in numerical text field. Must be at least lower bound.");
                                        endField.getStyleClass().add("error");
                                        handleSetField(endField, null);
                                    }
                                } else {
                                    endField.getStyleClass().remove("error");
                                    handleSetField(endField, Integer.valueOf(newValue));
                                }
                            } else {
                                log.warn("Invalid entry in numerical text field. Must be positive or zero integer.");
                                endField.getStyleClass().add("error");
                                handleSetField(endField, null);
                            }
                        })
        );
    }

    /**
     * Called as a generic function for setting numeric text fields from the listener in init_NumericFields.
     * Sets the specified textField to the specified value in the search object.
     * @param field TextField object that was updated
     * @param value Value to set in search object
     */
    private void handleSetField(TextField field, Integer value) {
        if (field == startDeathsField) {
            search.setStartDeaths(value);
        } else if (field == endDeathsField) {
            search.setEndDeaths(value);
        } else if (field == startMinorInjuriesField) {
            search.setStartMinorInjuries(value);
        } else if (field == endMinorInjuriesField) {
            search.setEndMinorInjuries(value);
        } else if (field == startMajorInjuriesField) {
            search.setStartMajorInjuries(value);
        } else if (field == endMajorInjuriesField) {
            search.setEndMajorInjuries(value);
        }
        search.handleSearchChange();
    }

    /**
     * Called in the UI by clicking on Reset Search button.
     * Resets all fields in the search panel to their default selections (usually no selection).
     * Calls reset in the search object to initialize most of its values too.
     */
    @FXML
    private void handleResetSearch() {
        radiusSlider.setValue(1.0);
        locationField.clear();
        locationSearchToggle.setSelected(false);
        toggleLocationSearch();

        yearCheckBox.setSelected(false);
        speedLimitCheckBox.setSelected(false);
        advisedSpeedCheckBox.setSelected(false);
        tempSpeedCheckBox.setSelected(false);
        deathsCheckBox.setSelected(false);
        minorInjuryCheckBox.setSelected(false);
        majorInjuryCheckBox.setSelected(false);
        numLanesCheckBox.setSelected(false);

        keywordField.clear();
        startDeathsField.clear();
        endDeathsField.clear();
        startMinorInjuriesField.clear();
        endMinorInjuriesField.clear();
        startMajorInjuriesField.clear();
        endMajorInjuriesField.clear();

        matchToggleButton.setSelected(false);
        shCheckBox.setSelected(false);
        shCheckBox.setIndeterminate(true);
        hillCheckBox.setSelected(false);
        hillCheckBox.setIndeterminate(true);
        lightsCheckBox.setSelected(false);
        lightsCheckBox.setIndeterminate(true);

        startYearComboBox.getSelectionModel().selectFirst();
        endYearComboBox.getSelectionModel().selectLast();
        startSpeedLimitBox.getSelectionModel().selectFirst();
        endSpeedLimitBox.getSelectionModel().selectLast();
        advisedStartBox.getSelectionModel().selectFirst();
        advisedEndBox.getSelectionModel().selectLast();
        temporaryStartBox.getSelectionModel().selectFirst();
        temporaryEndBox.getSelectionModel().selectLast();
        laneStartBox.getSelectionModel().selectFirst();
        laneEndBox.getSelectionModel().selectLast();

        severityComboBox.getCheckModel().clearChecks();
        regionComboBox.getCheckModel().clearChecks();
        participantComboBox.getCheckModel().clearChecks();
        weatherComboBoxA.getCheckModel().clearChecks();
        weatherComboBoxB.getCheckModel().clearChecks();
        lightComboBox.getCheckModel().clearChecks();
        holidayComboBox.getCheckModel().clearChecks();
        tlaComboBox.getCheckModel().clearChecks();
        trafficCtrlBox.getCheckModel().clearChecks();
        collisionComboBox.getCheckModel().clearChecks();
        crashDirectionBox.getCheckModel().clearChecks();
        vehicleDirectionBox.getCheckModel().clearChecks();
        laneConfigBox.getCheckModel().clearChecks();
        roadCharBox.getCheckModel().clearChecks();
        roadSurfaceBox.getCheckModel().clearChecks();

        conditionalsContainer.getChildren().clear();
        HBox addBox = createAddButton();
        conditionalsContainer.getChildren().add(addBox);
        conditionalIndex = 0;
        participantAndToggle.setSelected(false);
        collisionAndToggle.setSelected(false);

        search.reset();
        handleSearch();
    }

    /**
     * Called by clicking the Search button. Uses the controller interface to update the given view.
     */
    @FXML
    void handleSearch() {
        if (locationSearchToggle.isSelected()) {
            handleFindLocation();
        }
        navigationController.updateFromSearch();
    }
}
