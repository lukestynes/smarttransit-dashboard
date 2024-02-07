package seng202.team5.gui;

import com.opencsv.exceptions.CsvException;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kordamp.ikonli.javafx.FontIcon;
import seng202.team5.business.CrashManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

/**
 * Controller for the dbView.fxml. Allows the user to load multiple database tables and populate them
 * with CSV data. Also lets the user easily maintain the names and swap what dataset is open.
 */
public class DBViewController {
  private static final Logger log = LogManager.getLogger(DBViewController.class);
  private Stage stage;
  private NavigationController navigationController;
  @FXML
  private ListView dbList;
  private CrashManager crashManager;

  /**
   * Initializes the database view, assigning local variables, binding mouse/button feedback to the list view, and
   * loading the list of tables from the database into the list view.
   * @param stage Window to display the dbView on
   * @param navigationController Parent navigationView controller for parent function calls
   * @param crashManager Crash manager for querying the SQL database
   */
  public void init(Stage stage, NavigationController navigationController, CrashManager crashManager) {
    this.stage = stage;
    stage.getScene().getStylesheets().add("/css/Main.css");
    stage.getScene().getStylesheets().add("/css/DBControl.css");
    this.navigationController = navigationController;
    this.crashManager = crashManager;
    dbList.setOnMouseClicked(e -> {
      if (e.getClickCount() == 2) {
        Object selectedRow = dbList.getSelectionModel().getSelectedItem();
        if (selectedRow != null) {
          handleListSelect(selectedRow);
        }
      }
    });
    dbList.setOnKeyPressed(e -> {
      if (e.getCode() == KeyCode.ENTER) {
        Object selectedRow = dbList.getSelectionModel().getSelectedItem();
        if (selectedRow != null) {
          handleListSelect(selectedRow);
        }
      }
    });
    loadTables();
  }

  /**
   * Called by either double-clicking on a dbList row, or by highlighting a row and pressing the Enter key.
   * This changes the selection of the databaseDropDown menu to match the highlighted row.
   * The databaseDropDown has a listener for value changes, so this indirectly updates the actively-selected table.
   * @param selectedRow Object (actually a VBox) representing the row in the dbList listView
   */
  private void handleListSelect(Object selectedRow) {
    VBox row = (VBox) selectedRow;
    Text dbName = (Text) row.getChildren().get(0);
    navigationController.getDatabaseDropdown().getSelectionModel().select(dbName.getText());
    navigationController.toggleSidePane();
  }

  /**
   * Method called by clicking the Add from CSV button in the UI
   */
  @FXML
  void handleAddDB() {
    selectCSVFile();
  }

  /**
   * Called by clicking on the add from CSV button in the UI.
   * Creates a fileChooser window filtering only for CSV files, letting the user select a target file.
   * If a file is selected, this method continues the add process by calling createTableNamer
   */
  public void selectCSVFile() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Select a crash data CSV file");
    fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV File", "*.csv"));
    try {
      fileChooser.setInitialDirectory(new File(NavigationController.class.getProtectionDomain().getCodeSource()
              .getLocation().toURI()).getParentFile());
    } catch (URISyntaxException e) {
      log.error("Error on opening FileChooser: Could not parse the initial directory as a URI.");
      e.printStackTrace();
    }
    File csvFile = fileChooser.showOpenDialog(stage);
    if (csvFile != null) {
      createTableNamer(csvFile);
    }
  }

  /**
   * Creates a text input dialog window for the user to enter their desired table name.
   * This method can be recursive: if the user enters a name, and it's already present in the list of tables,
   * an error dialog is created to warn them of this, before calling the createTableNamer again.
   * If a unique name is submitted, the csv is checked through crashManager for matching headers against the CAS specification.
   * If the headers are valid, the table is given a databaseTable name (the string the user sees and the actual table name are different)
   * consistent of "T" followed by a random positive 64-bit signed integer, and then an add operation is attempted through the
   * crash manager.
   * If the headers are invalid, createErrorDialog() is called.
   * @param csvFile File corresponding to database table to add
   */
  public void createTableNamer(File csvFile) {
    TextInputDialog tableNamer = new TextInputDialog();
    tableNamer.setTitle("Name database");
    tableNamer.setHeaderText("Enter a name for the database:");
    Optional<String> input = tableNamer.showAndWait();
    input.ifPresent(tableString -> {
      HashMap<String, String> tables = crashManager.getTableNames();
      List<String> existing = new ArrayList<>(tables.values());
      if (existing.contains(tableString)) {
        Alert existingNameError = new Alert(Alert.AlertType.ERROR);
        existingNameError.setTitle("Database already exists");
        existingNameError.setHeaderText("The name you have entered is already taken.\nPlease enter another name.");
        existingNameError.showAndWait();
        createTableNamer(csvFile);
      } else {
        boolean csvValid = false;
        try {
          csvValid = crashManager.checkHeaders(csvFile);
        } catch (CsvException | IOException e) {
          log.error("An exception occurred while checking the provided CSV file's headers for CAS-conformity.");
          e.printStackTrace();
        }
          if (csvValid) {
            //Generate random table name using the letter T followed by a random positive long
            Random random = new Random();
            String tableName = "T" + Math.abs(random.nextLong());
            while (tables.containsKey(tableName)) {
              tableName = "T" + Math.abs(random.nextLong());
          }
          crashManager.addCrashFromFile(csvFile, tableName, tableString, this);
        } else {
            createErrorDialog();
          }
      }
    });
  }

  /**
   * Creates a window for renaming a table's display name.
   * The window checks the new name is unique, and if so, renames the table with crashManager.
   * Otherwise, it creates an error prompt, before recursively calling createTableRenamer again.
   * @param dbName String of the database reference name to rename (i.e., T341890357498)
   * @param currentName String of the current display name for the table (i.e., "My cool table")
   */
  public void createTableRenamer(String dbName, String currentName) {
    TextInputDialog tableNamer = new TextInputDialog();
    tableNamer.setTitle("Rename database");
    tableNamer.setHeaderText("Enter a new name for the database: " + currentName);
    Optional<String> input = tableNamer.showAndWait();
    input.ifPresent(tableString -> {
      HashMap<String, String> tables = crashManager.getTableNames();
      List<String> existing = new ArrayList<>(tables.values());
      if (existing.contains(tableString)) {
        log.error("Provide table string name already exists in database.");
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle("Database already exists");
        error.setHeaderText("The name you have entered is already taken.\nPlease enter another name.");
        error.showAndWait();
        createTableRenamer(dbName, currentName);
      } else {
        crashManager.renameTable(dbName, tableString, this);
        log.info("Table with reference " + dbName + " renamed from " + currentName + " to " + tableString + ".");
      }
    });
  }

  /**
   * Creates a confirmation dialog for users to ensure tables aren't accidentally deleted.
   * The dialog has two buttons, Yes/No. If the user selects Yes, the table is removed.
   * Removal consists of removing the table from the database, removing it from the listView, and removing it from the dropdown.
   * @param dbName DBReference name of the table (e.g.:, T3907825487904)
   * @param tableName String name of the table (e.g.:, "My cool table")
   * @param row Row object of the listview representing the table
   */
  public void createTableDeleter(String dbName, String tableName, VBox row) {
    Alert deleter = new Alert(Alert.AlertType.WARNING);
    deleter.setTitle("Delete database");
    deleter.setHeaderText("Are you sure you want to delete the database " + tableName + "?");
    deleter.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
    Optional<ButtonType> choice = deleter.showAndWait();
    choice.ifPresent(selection -> {
      if (selection == ButtonType.YES) {
        // Handle deletion
        crashManager.dropTable(dbName);
        navigationController.getDatabaseDropdown().getItems().remove(tableName);
        dbList.getItems().remove(row);
        dbList.getSelectionModel().selectFirst();
        log.info("Table " + tableName + " with reference " + dbName + " deleted.");
      }
    });
  }

  /**
   * Creates an error dialog when the supplied CSV file could not be processed.
   * Either the CSV has invalid headers, or the CSV file has no valid rows of data for importing.
   */
  public void createErrorDialog() {
    Alert invalidCSVError = new Alert(Alert.AlertType.ERROR);
    invalidCSVError.setTitle("CSV Format is Invalid");
    invalidCSVError.setHeaderText("The content in the file provided does not conform to the CAS data specification.\nEnsure that the headers in the provided file match the specification. Every record must have a unique ID field.");
    invalidCSVError.showAndWait();
  }

  /**
   * Gets the list of tables from the database, and constructs the database listView from them.
   * Firstly, a map of tableNames is produced from the crashManager.
   * This map consists of DBName -> DisplayName key, value pairs.
   * Next, we retrieve the map's entry pairs, and sort them alphabetically using a case-insensitive comparator.
   * Then we clear the listView and dropdown of items, and if the table map isn't empty, add each entry in.
   * For each table in the database:
   * - We add the displayName to the dropdown
   * - We create a VBox row to contain the table's information and options in the list view.
   * The VBox consists of two components: firstly text displaying the table's name, and secondly an HBox lowerRow.
   * The HBox lowerRow contains a text string of the table's entry count, wrapped for formatting in a VBox (to center it vertically),
   * alongside rename and delete buttons for the table. These are spaced by a blank pane in-between, which grows to accompany the horizontal blank space.
   */
  public void loadTables() {
    //key -> value pairs : tableDBName -> tableStringName
    log.info("Loading table list view.");
    HashMap<String, String> tables = crashManager.getTableNames();
    List<Map.Entry<String, String>> sortedTables = new ArrayList<>(tables.entrySet());
    sortedTables.sort((a, b) -> {
      String x = a.getValue().toLowerCase();
      String y = b.getValue().toLowerCase();
      return x.compareTo(y);
    });
    dbList.getItems().clear();
    navigationController.getDatabaseDropdown().getItems().clear();
    if (!tables.isEmpty()) {
      for (Map.Entry<String, String> table : sortedTables) {
        String tableDBName = table.getKey();
        String tableStringName = table.getValue();
        navigationController.getDatabaseDropdown().getItems().add(tableStringName);
        Integer count = crashManager.getNumResults(tableDBName);

        VBox row = new VBox(2);
        HBox lowerRow = new HBox(5);

        Text tableNameText = new Text(tableStringName);
        tableNameText.setWrappingWidth(200);
        Text countText = new Text(count + " entries");
        VBox countBox = new VBox();
        countBox.getChildren().add(countText);
        countBox.setAlignment(Pos.CENTER_LEFT);

        FontIcon renameIcon = new FontIcon("mdi2s-square-edit-outline");
        FontIcon deleteIcon = new FontIcon("mdi2d-delete");
        renameIcon.setIconSize(20);
        deleteIcon.setIconSize(20);
        renameIcon.setId("rename-icon");
        deleteIcon.setId("delete-icon");

        Button renameButton = new Button("", renameIcon);
        Button deleteButton = new Button("", deleteIcon);
        renameButton.setPrefSize(32, 32);
        deleteButton.setPrefSize(32, 32);

        HBox buttons = new HBox(2);
        buttons.setAlignment(Pos.CENTER_RIGHT);
        buttons.getChildren().addAll(renameButton, deleteButton);

        Pane pane = new Pane();
        pane.setMinSize(0, 0);
        HBox.setHgrow(pane, Priority.ALWAYS);

        renameButton.setOnAction(event -> {
          createTableRenamer(tableDBName, tableStringName);
        });
        deleteButton.setOnAction(event -> {
          createTableDeleter(tableDBName, tableStringName, row);
        });
        lowerRow.getChildren().addAll(countBox, pane, buttons);
        row.getChildren().addAll(tableNameText, lowerRow);
        dbList.getItems().add(row);
      }
      selectStored();
      navigationController.getDatabaseDropdown().getSelectionModel().selectFirst();
    }
  }

  /**
   * Opens the last used table by default when the application is loaded to ensure that a dataset
   * is always open.
   */
  public void selectStored() {
    HashMap<String, String> tables = crashManager.getTableNames();
    String table = crashManager.getLastTable();
    if (table != null) {
      for (Map.Entry<String, String> entry : tables.entrySet()) {
        if (table.equals(entry.getKey())) {
          log.info("Found a pre-existing table reference. Loading now.");
          navigationController.getDatabaseDropdown().getSelectionModel().select(entry.getValue());
        }
      }
    } else {
      log.info("No pre-existing table reference found.");
      navigationController.getDatabaseDropdown().getSelectionModel().selectFirst();
    }
  }
}