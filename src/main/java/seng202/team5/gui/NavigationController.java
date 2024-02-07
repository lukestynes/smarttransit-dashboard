package seng202.team5.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import seng202.team5.business.CrashManager;
import seng202.team5.chart.ChartManager;
import seng202.team5.models.Search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * NavigationController handles the behaviour of the main.fxml file.
 * It manages the user's navigation between different views, and serves as the hub for initializing most other controllers.
 */
public class NavigationController implements ResultNumberListener {

    /**
     * CrashManager object is used to interface with the SQL database
     */
    private CrashManager crashManager = new CrashManager();

    private static final Logger log = LogManager.getLogger(NavigationController.class);
    /**
     * Root node of the main.fxml
     */
    @FXML
    private AnchorPane root;
    /**
     * Whichever view is currently loaded is placed and scaled to the edges of the mainPane AnchorPane.
     */
    @FXML
    private AnchorPane mainPane;

    /**
     * The sidePane is toggled by clicking the Menu button in the navigation bar.
     * It should provide options relevant to all views.
     */
    @FXML
    private BorderPane sidePane;

    /**
     * Container for loading the database view into.
     * This is presented within the sidePane.
     */
    @FXML
    private AnchorPane dbContainer;
    /**
     * The searchContainer is an AnchorPane, hidden by default, onto which the searchPanel is loaded and displayed.
     */
    @FXML
    private AnchorPane searchContainer;
    /**
     * The button for toggling the visibility of the searchContainer searchPanel
     */
    @FXML
    private Button toggleSearchButton;
    /**
     * The button for toggling the visibility of the sidePane menu
     */
    @FXML
    private  Button openMenuButton;
    /**
     * The button for navigating to the Map View
     */
    @FXML
    private Button mapButton;
    /**
     * Button for navigating to chart view
     */
    @FXML
    private Button chartButton;
    /**
     * Button for navigating to table view
     */
    @FXML
    private Button tableButton;
    /**
     * Drop-down menu for selecting the loaded database to search from.
     * Changing this should also perform an updateFromSearch()
     */
    @FXML
    private ComboBox databaseDropdown;
    /**
     * Label displayed below the database drop-down.
     * Should display either "No database loaded."
     * or "Viewing SEARCH_COUNT of DATABASE_SIZE crashes."
     */
    @FXML
    private Text databaseLabel;

    /**
     * Button in UI for toggling help tooltips
     */
    @FXML
    private Button helpButton;
    /**
     * Progress indicator overlaid on the application during a search/update operation
     */
    @FXML
    private ProgressIndicator updatingIcon;
    /**
     * Boolean for tracking if help tooltips should be displayed
     */
    private boolean helpEnabled = false;
    /**
     * Integer for storing the total number of entries in the currently-selected table
     */
    private Integer databaseSize = 0;
    /**
     * Integer for storing the number of entries yielded by the current search query
     */
    private Integer numResults = 0;

    /**
     * This object stores all relevant data about user search/filter, sorting, and pagination preferences.
     * It is initialized from this view, and passed down to child nodes, so that the search state is persistent between
     * all the application's views.
     */
    private Search search = new Search(this);

    /**
     * Window to display within
     */
    private Stage stage;

    /**
     * A hashmap for storing and retrieving views once they've been initialized from their FXML files.
     * The map is a list of Strings representing each view's fxml name (i.e., "chart"), and a list of FXMLLoader objects
     * for each FXML file. As .load() has already been called on these objects, when loading them into the view we only
     * call for the .getController() and .getParent() methods to obtain the controller object and parent node respectively.
     */
    private HashMap<String, FXMLLoader> fxmls = new HashMap<>();
    /**
     * Tooltip object for displaying help information within
     */
    private Tooltip helpTooltip = new Tooltip();


    /**
     * Initializes the main window.
     * The window has minimum dimensions of 800x600 pixels.
     * The default view is set to Home View, displaying a splash page of the application.
     * @param stage Top level container for this window
     */
    public void init(Stage stage) throws IOException {
        this.stage = stage;
        stage.getScene().getStylesheets().add("/css/Main.css");
        stage.getScene().getStylesheets().add("/css/LoadingIndicator.css");
        helpTooltip.setStyle("-fx-font-size: 14");
        toggleViewUpdating(false);
        stage.sizeToScene();
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        toggleSidePane(); //side pane visible in fxml for editing sake, but default not visible
        setHomeView();
        addDBListener();
        loadDBView(stage);
        initHelp();
        try {
            log.info("Loading Table View");
            FXMLLoader tableViewLoader = new FXMLLoader(getClass().getResource("/fxml/table.fxml"));
            fxmls.put("table", tableViewLoader);
            Parent tableViewParent = tableViewLoader.load();
            TableViewController tableViewController = tableViewLoader.getController();
            tableViewController.init(stage, search, this);
            mainPane.setTopAnchor(tableViewParent, 0.0);
            mainPane.setRightAnchor(tableViewParent, 0.0);
            mainPane.setBottomAnchor(tableViewParent, 0.0);
            mainPane.setLeftAnchor(tableViewParent, 0.0);
            updateFromSearch();
        } catch (IOException e) {
            e.printStackTrace();
        }
        helpEnabled = true;
    }

    /**
     * Add a listener to the database dropdown menu for updating database selection when changed.
     */
    public void addDBListener() {
        databaseDropdown.getSelectionModel().selectedItemProperty().addListener((obs, old, value) -> {
            if (value != null) {
                HashMap<String, String> tables = crashManager.getTableNames();
                for (Map.Entry<String, String> entry : tables.entrySet()) {
                    if (value.equals(entry.getValue())) {
                        search.setTable(entry.getKey());
                        crashManager.setLastTable(entry.getKey());
                        databaseSize = crashManager.getNumResults(entry.getKey());
                    }
                }
                updateFromSearch();
            } if (value == null) {
                search.setTable(null);
                updateFromSearch();
            }
        });
    }

    /**
     * Loads the Database View and adds it to the menu side panel.
     * Called during application launch.
     * @param stage Window to load the database view into.
     */
    public void loadDBView (Stage stage) {
        if (fxmls.containsKey("dbView")) {
            mainPane.getChildren().clear();
            mainPane.getChildren().add(fxmls.get("dbView").getRoot());
        } else {
            try {
                log.info("Loading DB View");
                FXMLLoader dbViewLoader = new FXMLLoader(getClass().getResource("/fxml/dbview.fxml"));
                fxmls.put("dbView", dbViewLoader);
                Parent dbViewParent = dbViewLoader.load();
                DBViewController dbViewController = dbViewLoader.getController();
                dbViewController.init(stage, this, crashManager);
                dbContainer.setTopAnchor(dbViewParent, 0.0);
                dbContainer.setRightAnchor(dbViewParent, 0.0);
                dbContainer.setBottomAnchor(dbViewParent, 0.0);
                dbContainer.setLeftAnchor(dbViewParent, 0.0);
                dbContainer.getChildren().clear();
                dbContainer.getChildren().add(fxmls.get("dbView").getRoot());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This is called by initializing or navigating to any view that can utilize searching.
     * First, if the search has yet to be initialized, the initSearchPanel() function is called.
     * Then, the toggle Search button is made visible in the menu bar, so that users can show/hide the search while
     * inside the searchable view.
     */
    private void loadShowSearch() {
        //1. Load SearchPanel if not already loaded
        if (!fxmls.containsKey("search")) {
            initSearchPanel();
        }
        //2. Make the ToggleSearch button visible
        toggleSearchButton.setVisible(true);
        toggleSearchButton.setMinWidth(Region.USE_COMPUTED_SIZE);
        toggleSearchButton.setMaxWidth(Region.USE_COMPUTED_SIZE);
        databaseLabel.setVisible(true);
    }

    /**
     * This is called by initializing or navigating to a view that cannot utilize searching.
     * It hides the toggleSearch button and search panel from user view.
     */
    private void hideSearch() {
        searchContainer.setVisible(true);
        toggleSearchPanel();
        toggleSearchButton.setVisible(false);
        toggleSearchButton.setMinWidth(0);
        toggleSearchButton.setMaxWidth(0);
        databaseLabel.setVisible(false);
    }

    /**
     * This function initializes the search panel from its FXML file, and stores it in the fxmls HashMap.
     * It also initializes the searchPanelController, and anchors the panel to an anchorpane in the main view.
     */
    private void initSearchPanel() {
        try {
            FXMLLoader searchPanelLoader = new FXMLLoader(getClass().getResource("/fxml/search.fxml"));
            fxmls.put("search", searchPanelLoader);
            Parent searchPanelParent = searchPanelLoader.load();
            SearchPanelController searchPanelController = searchPanelLoader.getController();
            searchPanelController.init(stage, search, this);
            searchContainer.setTopAnchor(searchPanelParent, 0.0);
            searchContainer.setRightAnchor(searchPanelParent, 0.0);
            searchContainer.setBottomAnchor(searchPanelParent, 0.0);
            searchContainer.setLeftAnchor(searchPanelParent, 0.0);
            searchContainer.getChildren().clear();
            searchContainer.getChildren().add(searchPanelParent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Called by the GUI on clicking "View Table". Calls loadTableView
     */
    @FXML
    void setTableView() { loadTableView(stage, search); }

    /**
     * This function navigates to and loads the tableView.
     * If the table view has already been initialized, its root element is retrieved from the stored FXMLLoader,
     * and it is placed on the mainPane.
     * If the table view has not been initialized, it is loaded and stored in the fxmls HashMap, before its
     * controller is initalized, and its parent node displayed in the mainPane.
     * @param stage The window on which the table is displayed
     * @param search The search object for the application
     */
    public void loadTableView(Stage stage, Search search) {
        loadShowSearch();
        //Change the main view to the Table View
        if (fxmls.containsKey("table")) {
            mainPane.getChildren().clear();
            mainPane.getChildren().add(fxmls.get("table").getRoot());
        } else {
            //Create tableView and add to pane map
            try {
                log.info("Loading Table View");
                FXMLLoader tableViewLoader = new FXMLLoader(getClass().getResource("/fxml/table.fxml"));
                fxmls.put("table", tableViewLoader);
                Parent tableViewParent = tableViewLoader.load();
                TableViewController tableViewController = tableViewLoader.getController();
                tableViewController.init(stage, search, this);
                mainPane.setTopAnchor(tableViewParent, 0.0);
                mainPane.setRightAnchor(tableViewParent, 0.0);
                mainPane.setBottomAnchor(tableViewParent, 0.0);
                mainPane.setLeftAnchor(tableViewParent, 0.0);
                mainPane.getChildren().clear();
                mainPane.getChildren().add(tableViewParent);
                updateFromSearch();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Called by the GUI on clicking "View Chart". Calls loadChartView().
     */
    @FXML
    void setChartView() {loadChartView(stage, search);}
    /**
     * This function navigates to and loads the chartView.
     * If the chart view has already been initialized, its root element is retrieved from the stored FXMLLoader,
     * and it is placed on the mainPane.
     * If the chart view has not been initialized, it is loaded and stored in the fxmls HashMap, before its
     * controller is initalized, and its parent node displayed in the mainPane.
     * @param stage The window on which the chart is displayed
     * @param search The search object for the application
     */
    public void loadChartView(Stage stage, Search search) {
        loadShowSearch();
        if (fxmls.containsKey("chart")) {
            mainPane.getChildren().clear();
            mainPane.getChildren().add(fxmls.get("chart").getRoot());
        } else {
            try {
                log.info("Loading GenericChart View");
                FXMLLoader chartViewLoader = new FXMLLoader(getClass().getResource("/fxml/chart.fxml"));
                fxmls.put("chart", chartViewLoader);
                Parent chartViewParent = chartViewLoader.load();
                ChartViewController chartViewController = chartViewLoader.getController();
                chartViewController.init(stage, search, this);
                mainPane.setTopAnchor(chartViewParent, 0.0);
                mainPane.setRightAnchor(chartViewParent, 0.0);
                mainPane.setBottomAnchor(chartViewParent, 0.0);
                mainPane.setLeftAnchor(chartViewParent, 0.0);
                mainPane.getChildren().clear();
                mainPane.getChildren().add(chartViewParent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Called by the GUI on clicking "View Map". Calls loadMapView();
     */
    @FXML
    void setMapView() {loadMapView(stage, search);}
    /**
     * This function navigates to and loads the mapView.
     * If the map view has already been initialized, its root element is retrieved from the stored FXMLLoader,
     * and it is placed on the mainPane.
     * If the map view has not been initialized, it is loaded and stored in the fxmls HashMap, before its
     * controller is initialized, and its parent node displayed in the mainPane.
     * @param stage The window on which the map is displayed
     * @param search The search object for the application
     */
    public void loadMapView(Stage stage, Search search) {
        loadShowSearch();
        if (fxmls.containsKey("map")) {
            mainPane.getChildren().clear();
            mainPane.getChildren().add(fxmls.get("map").getRoot());
        } else {
            try {
                log.info("Loading Map View");
                FXMLLoader mapViewLoader = new FXMLLoader(getClass().getResource("/fxml/map.fxml"));
                fxmls.put("map", mapViewLoader);
                Parent mapViewParent = mapViewLoader.load();
                MapViewController mapViewController = mapViewLoader.getController();
                mapViewController.init(stage, search, this);
                mainPane.setTopAnchor(mapViewParent, 0.0);
                mainPane.setRightAnchor(mapViewParent, 0.0);
                mainPane.setBottomAnchor(mapViewParent, 0.0);
                mainPane.setLeftAnchor(mapViewParent, 0.0);
                mainPane.getChildren().clear();
                mainPane.getChildren().add(mapViewParent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Called by the init() function of this class. Calls loadHomeView().
     * Separation is for potential modularity, should a "Return to Home" button be implemented.
     */
    @FXML
    void setHomeView() { loadHomeView(); }
    /**
     * This function navigates to and loads the homeView.
     * At the moment, the homeView is the default page of the application. Both due to its size, and the fact that
     * it is only accessed once at application launch, it is not currently stored for retrieval later.
     */
    private void loadHomeView() {
        hideSearch();
//        toggleSearchPanel();
//        loadShowSearch();

        try {
            log.info("Loading Home View");
            FXMLLoader homeViewLoader = new FXMLLoader(getClass().getResource("/fxml/home.fxml"));
            Parent homeViewParent = homeViewLoader.load();
            HomeViewController homeViewController = homeViewLoader.getController();
            homeViewController.init(stage);
            mainPane.setTopAnchor(homeViewParent, 0.0);
            mainPane.setRightAnchor(homeViewParent, 0.0);
            mainPane.setBottomAnchor(homeViewParent, 0.0);
            mainPane.setLeftAnchor(homeViewParent, 0.0);
            mainPane.getChildren().clear();
            mainPane.getChildren().add(homeViewParent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Called by the GUI on clicking the menu button. Calls toggleSidePane() to toggle menu visibility.
     */
    @FXML
    void handleToggleSidePane() { toggleSidePane(); }

    /**
     * This function toggles the visibility of the side pane Menu panel.
     */
    public void toggleSidePane() {
        boolean current = sidePane.isVisible();
        if (current) {
            sidePane.setMaxWidth(0);
            sidePane.setMinWidth(0);
        }else {
            sidePane.setMinWidth(Region.USE_COMPUTED_SIZE);
            sidePane.setMaxWidth(225);
        }
        sidePane.setVisible(!current);
    }

    /**
     * Called by the GUI on clicking "Toggle Search". Calls toggleSearchPanel() to toggle the search panel's visibility.
     */
    @FXML
    void handleToggleSearch() {
        toggleSearchPanel();

        if (searchContainer.isVisible()) {
            toggleSearchButton.setText("Hide Filters");
        } else {
            toggleSearchButton.setText("Show Filters");
        }

    }

    /**
     * This function toggles the visibility of the searchContainer search panel.
     */
    public void toggleSearchPanel() {
        boolean current = searchContainer.isVisible();
        if (current) {
            searchContainer.setMaxWidth(0);
            searchContainer.setMinWidth(0);
        } else {
            searchContainer.setMinWidth(250);
            searchContainer.setMaxWidth(250);
        }
        searchContainer.setVisible(!current);
    }

    /**
     * Toggles the search panel but only if it is already open.
     */
    public void closeSearch() {
        boolean current = searchContainer.isVisible();
        if (current) {
            toggleSearchPanel();
        }
    }

    /**
     * Toggles the side pane but only if it is open.
     */
    public void closeSidePane() {
        boolean current = sidePane.isVisible();
        if (current) {
            toggleSidePane();
        }
    }

    /**
     * Called by clicking the Help button in the GUI. Calls toggleHelpMode() to toggle default visibility of help tooltips.
     */
    @FXML
    void handleToggleHelpMode() { toggleHelpMode();}

    /**
     * Toggles the helpEnabled boolean.
     * When helpEnabled, help tooltips are displayed instantly on mouse-over of various UI components.
     * When disabled, only specific UI tooltips are displayed on mouse-over, after a delay.
     */
    public void toggleHelpMode() {
        if (!helpEnabled) {
            helpEnabled = true;
            root.setCursor(Cursor.CLOSED_HAND);
        } else {
            helpEnabled = false;
            root.setCursor(Cursor.DEFAULT);
            helpTooltip.setText(null);
            helpTooltip.hide();
        }
    }

    /**
     * Initializes help functionality for the navigation controller. When the boolean helpEnabled is true,
     * a tooltip is displayed for whichever component of the UI currently has mouse-over.
     */
    public void initHelp() {
        //Help Button tooltip is always enabled
        helpButton.setOnMouseEntered(e -> {
            helpTooltip.setText("This button toggles the visibility of help tooltips.");
            helpTooltip.show(helpButton, e.getScreenX(), e.getScreenY() + 20);
        });
        helpButton.setOnMouseExited(e -> {
            helpTooltip.hide();
        });
        //Create other events for when help boolean is enabled
        createHelpEvent(mapButton, "Click this to navigate to the Map/Routing view.");
        createHelpEvent(chartButton, "Click this to navigate to the Chart data view.");
        createHelpEvent(tableButton, "Click this to navigate to the Table data view.");
        createHelpEvent(databaseDropdown, "You can select the database you'd like to view crash records from using this drop-down menu.");
        createHelpEvent(toggleSearchButton, "Click this to toggle the visibility of the Search panel.");
        createHelpEvent(openMenuButton, "Click this to toggle the visibility of the side menu panel.");
    }

    /**
     * Create generic help tooltips for JavaFX objects. This method is public, and can be called by other controllers when establishing their Help tooltips.
     * @param object JavaFX node object to attach the help tooltip to.
     * @param helpText String representation of the help information to provide.
     */
    public void createHelpEvent(Node object, String helpText) {
        if (object != null) {
            object.setOnMouseEntered(e -> {
                if (helpEnabled) {
                    helpTooltip.setText(helpText);
                    helpTooltip.show(object, e.getScreenX(), e.getScreenY() + 20);
                }
            });
            object.setOnMouseExited(e -> {
                helpTooltip.hide();
            });
        }
    }

    /**
     * This function updates all currently loaded views so that their data matches the current search/filter object.
     * It does so by calling the updateFromSearch() method inside each of these views.
     * The database label is updated on a task to avoid the count query interrupting the UI.
     * After the search update completes, the Updating Search overlay is removed, if it was present.
     */
    public void updateFromSearch() {
        if (search.getTable() == null) {
            //No database is loaded; do not attempt SQL search
            databaseDropdown.getSelectionModel().clearSelection();
            databaseLabel.setText("No database loaded");
        } else {
            ArrayList<ResultNumberListener> callers = new ArrayList<>();
            callers.add(this);
            if (fxmls.containsKey("table")) {
                ((TableViewController) fxmls.get("table").getController()).updateFromSearch(numResults);
                callers.add(((TableViewController) fxmls.get("table").getController()).getTableManager());
            } if (fxmls.containsKey("chart") && ChartManager.chartType != null) {
                ((ChartViewController) fxmls.get("chart").getController()).updateFromSearch();
            } if (fxmls.containsKey("map")) {
                ((MapViewController) fxmls.get("map").getController()).enableSearchButton();
            }
            crashManager.numResults(search,callers);
        }
    }

    /**
     * Toggles the visibility of the "Updating From Search" overlay on the current view.
     * @param visible Boolean representing visibility of the overlay
     */
    public void toggleViewUpdating(boolean visible) {
        if (visible) {
            updatingIcon.setVisible(true);
            updatingIcon.setDisable(false);
        } else {
            updatingIcon.setVisible(false);
            updatingIcon.setDisable(true);
        }
    }

    /**
     * Updates the database label to correctly represent the number of results returned by the
     * current search query. In protecting against race conditions, the search is only updated if the
     * passed object matches the stored object.
     * @param newNum Count of results the search object's query returned
     * @param searchObject Search object used in determining the number of results.
     */
    @Override
    public void updateNumRow(Integer newNum, Search searchObject) {
        if (searchObject == search) {
            toggleViewUpdating(false);
            numResults = newNum;
            if (search.getTable() != null) {
                databaseLabel.setText("Viewing " + numResults + " of " + databaseSize + " crashes.");
            }
        }
    }

    /**
     * Retrieves the databaseDropdown comboBox for use by child views. Typically used by the DBView.
     * @return ComboBox of the databaseDropdown list
     */
    public ComboBox getDatabaseDropdown() {
        return databaseDropdown;
    }
}
