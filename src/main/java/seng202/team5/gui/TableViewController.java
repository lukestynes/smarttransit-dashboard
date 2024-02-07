package seng202.team5.gui;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.CheckComboBox;

import seng202.team5.App;
import seng202.team5.models.Search;
import seng202.team5.models.TableColumns;
import seng202.team5.table.TableManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * TableViewController handles user input for the tableView (though not the table or pagination itself).
 */
public class TableViewController {
    private static final Logger log = LogManager.getLogger(TableViewController.class);
    private TableManager tableManager;
    @FXML
    private Pagination paginator;
    @FXML
    private ComboBox paginationComboBox;
    @FXML
    private CheckComboBox columnGroupBox;
    @FXML
    private CheckComboBox columnComboBox;
    @FXML
    private TextField pageNavigateField;
    @FXML
    private Button firstPageButton;
    @FXML
    private Button lastPageButton;
    @FXML
    private Button pageNavigateButton;

    /**
     * A list of column groups not to display in the column group drop-down.
     * ALL is for the ID column, which must always be visible.
     * The remaining entries are included under General
     */
    private final List<TableColumns.ColumnGroup> hiddenGroups = new ArrayList<TableColumns.ColumnGroup>() {
        {
            add(TableColumns.ColumnGroup.ALL);
        }
    };

    /**
     * Individual columns not to display in the column drop-down.
     * ID must always be visible, while RoadSide and Intersection are never populated in the dataset.
     */
    private final List<TableColumns.Column> hiddenCols = new ArrayList<>() {
        {
            add(TableColumns.Column.ID);
            add(TableColumns.Column.ROADSIDE);
            add(TableColumns.Column.INTERSECTION);
        }
    };
    private Stage stage;
    public Search search;
    private NavigationController navigationController;

    /**
     * Initializes various aspects related to the table.fxml file.
     * TableManager is responsible for table logic.
     * ColumnCheckBoxes are for selecting/deselecting visible columns.
     * PageSizeComboBox is for adjusting the number of visible rows.
     * We also create a listener for the table's height property, so that the number of visible rows can be set
     * automatically if desired (adjust to window size). This is updated only after a resize operation has had time
     * to finish, to avoid any potential hit to performance.
     * @param stage The current window
     * @param search The current search object
     */
    public void init(Stage stage, Search search, NavigationController navigationController) {
        this.stage = stage;
        stage.getScene().getStylesheets().add("/css/Main.css");
        stage.getScene().getStylesheets().add("/css/TableView.css");
        this.search = search;
        this.navigationController = navigationController;
        initHelp();
        initTableManager();
        initColumnCheckComboBoxes();
        initPageSizeComboBox();
        columnGroupBox.getCheckModel().check(0);
    }


    /**
     * Creates help mode tooltips for the Table View.
     */
    private void initHelp() {
        navigationController.createHelpEvent(paginationComboBox, "Select a pre-set page size from the drop-down menu, or enter a custom one in the range 1-1000.");
        navigationController.createHelpEvent(columnGroupBox, "Toggle the visibility of groups of columns in the table view.");
        navigationController.createHelpEvent(columnComboBox, "Toggle the visibility of individual columns in the table view.");
        navigationController.createHelpEvent(pageNavigateField, "Enter the number of the page to navigate to.");
        navigationController.createHelpEvent(pageNavigateButton, "Click this to navigate to the specified page.");
        navigationController.createHelpEvent(paginator, "Left-click on column headers to sort by them.\nShift-left-click to enable secondary sorting.\nClick and drag on column headers within a group to re-order them.\nDouble-click on a row to view more information.");
    }

    /**
     * Initialize the page size combobox with numbers from 10 to 100 in increments of 10.
     */
    private void initPageSizeComboBox() {
        paginationComboBox.getItems().addAll(IntStream.range(10/10, 100/10 + 1).map(e -> e * 10).boxed().toList());
        paginationComboBox.getSelectionModel().select(2);
    }

    /**
     * Called by the "Go" button beside the page number text field, or by pressing Enter with the field highlighted.
     * If the value is a positive integer, it calls setPage in the table manager with the specified value.
     * It then clears the user's input for easier re-entry.
     */
    @FXML
    void handlePageNavigate() {
        String value = pageNavigateField.getText();
        if (value.matches("\\d+") && !value.matches("0+")) {
            Integer pageNumber = Integer.valueOf(value);
            tableManager.toPage(pageNumber);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Page number invalid");
            alert.setHeaderText("The entered page number is invalid.");
            alert.setContentText("Ensure the page number entered is numeric, and in the appropriate range.");
            alert.showAndWait();
        }
        pageNavigateField.clear();
    }

    /**
     * Called by the tableManager to enable/disable the firstPage shortcut button based on logical conditions.
     * @param enabled Boolean for if the button should be enabled.
     */
    public void setEnabledFirstPage(boolean enabled) {
        firstPageButton.setDisable(!enabled);
    }

    /**
     * Called by the tableManager to enable/disable the lastPage shortcut button based on logical conditions.
     * @param enabled Boolean for if the button should be enabled.
     */
    public void setEnabledLastPage(boolean enabled) {
        lastPageButton.setDisable(!enabled);
    }

    /**
     * Called by clicking the firstPage shortcut button. Navigates to the first page of the table.
     */
    @FXML
    void handleFirstPage() {
        tableManager.toPage(1);
    }

    /**
     * Called by clicking the lastPage shortcut button. Navigates to the last page of the table.
     */
    @FXML
    void handleLastPage() {
        tableManager.toPage(-1);
    }

    /**
     * Called by the pageSizeComboBox's onAction()
     * Handles a request from the UI to change page size.
     * If the value is a positive integer, automatic paging is disabled and size is set to the value (max 1000).
     * If the value is "Automatic", automatic paging is enabled and called again.
     * If the value is neither of these, it is ignored with a log error.
     */
    @FXML
    void handlePageSize() {
        String value = paginationComboBox.getValue().toString();
        if (value.matches("\\d+") && !value.matches("0+")) {
            Integer pageSize = Math.min(Integer.parseInt(value), 1000);
            setPageSize(pageSize);
            paginationComboBox.setValue(pageSize);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Page Size Invalid");
            alert.setHeaderText("The entered page size is invalid.");
            alert.setContentText("Page size must be a number between 1 and 1000.");
            paginationComboBox.setValue(search.getPageSize());
            alert.showAndWait();
        }
    }

    /**
     * Change the page size to the new specified value. Calls and modifies the search object, before updating the
     * rows in the table through tableManager.
     * @param newPageSize Row count to display per page
     */
    private void setPageSize(Integer newPageSize) {
        Integer oldSize = search.getPageSize();
        search.setPageSize(newPageSize);
        tableManager.handlePageSizeChange(oldSize, newPageSize);
        tableManager.requestRowsUpdate(null);
    }

    /**
     * Creates a tableManager object for handling table logic, and calls its initialization function.
     */
    private void initTableManager() {
        tableManager = new TableManager();
        tableManager.init(paginator, search, this);
    }

    /**
     * Initializes the columnComboBox, which determines which groups of columns are visible in the table, to have the
     * specified values as assigned above in columnOptions.
     * It then ensures the initial checkbox state has 'General' checked, as this is visible by default in column
     * initialization.
     * Next, it creates a listener for the ComboBox, so that whenever the selected items change, the appropriate groups
     * of columns are added/removed from the tableView. Because the onChange while loop is uh, Funky, it has a secondary
     * check to make sure the columns being added aren't already in the table, as this would do bad things.
     */
    private void initColumnCheckComboBoxes() {
        List<String> columnGroupOptions = Stream.of(TableColumns.ColumnGroup.values()).filter(e -> !hiddenGroups.contains(e)).map(g -> tableManager.generateGroupString(g, 0)).toList();
        columnGroupBox.getItems().addAll(columnGroupOptions);
        columnGroupBox.setShowCheckedCount(true);


        columnGroupBox.getCheckModel().getCheckedIndices().addListener((ListChangeListener<Integer>) c -> {
            while (c.next()) {
                if (!columnGroupBox.isDisable()) {
                    tableManager.updateVisibleGroups(c, columnComboBox, columnGroupBox);
                }
            }
        });

        List<String> columnOptions = new ArrayList<>();
        columnOptions.addAll(TableColumns.getColumnsOfGroup(TableColumns.ColumnGroup.GENERAL).stream().map(o -> "(General) " + o.getLabel()).sorted().toList());
        columnOptions.addAll(TableColumns.getColumnsOfGroup(TableColumns.ColumnGroup.ADDITIONAL).stream().map(o -> "(Additional) " + o.getLabel()).sorted().toList());
        columnOptions.addAll(TableColumns.getColumnsOfGroup(TableColumns.ColumnGroup.EXTRA).stream().map(o -> "(Extra) " + o.getLabel()).sorted().toList());
        columnOptions.addAll(TableColumns.getColumnsOfGroup(TableColumns.ColumnGroup.INJURIES).stream().map(o -> "(Injuries) " + o.getLabel()).sorted().toList());
        columnOptions.addAll(TableColumns.getColumnsOfGroup(TableColumns.ColumnGroup.PARTICIPANTS).stream().map(o -> "(Participants) " + o.getLabel()).sorted().toList());
        columnOptions.addAll(TableColumns.getColumnsOfGroup(TableColumns.ColumnGroup.COLLISIONS).stream().map(o -> "(Collisions) " + o.getLabel()).sorted().toList());
        columnComboBox.getItems().addAll(columnOptions);
        columnComboBox.setShowCheckedCount(true);
        columnComboBox.getCheckModel().getCheckedIndices().addListener((ListChangeListener<String>) c -> {
            while (c.next()) {
                if (!columnComboBox.isDisable()) {
                    tableManager.updateVisibleColumns(c, columnComboBox, columnGroupBox);
                }
            }
        });
    }


    /**
     * Called by the searchPanel when the Search button is clicked.
     * It calls the tableManager to update its data based on the search.
     */
    public void updateFromSearch(Integer numResults) {
        tableManager.requestRowsUpdate(numResults);
    }

    public TableManager getTableManager()
    {
        return tableManager;
    }

    public NavigationController getNavigationController() {
        return navigationController;
    }
}