package seng202.team5.table;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.CheckComboBox;

import seng202.team5.business.CrashManager;
import seng202.team5.database.CrashFromDatabase;
import seng202.team5.gui.ExtendedViewController;
import seng202.team5.gui.ResultNumberListener;
import seng202.team5.gui.ResultSetListener;
import seng202.team5.gui.TableViewController;
import seng202.team5.models.Search;
import seng202.team5.models.TableColumns;

import java.util.*;

/**
 * TableManager class is responsible for drawing the table object in Table View, and performing logical operations
 * specific to the Table View.
 */
public class TableManager implements ResultNumberListener, ResultSetListener {
    private static final Logger log = LogManager.getLogger(TableViewController.class);
    private Pagination tableContainer;
    private TableView<CrashFromDatabase> crashTableView;
    private final CrashManager crashManager = new CrashManager();
    private Search search;
    private final ObservableList<Integer> numResults = FXCollections.observableArrayList(0);
    private TableViewController tableViewController;


    /**
     * Initializes the table and its contents.
     * @param paginator A pagination JavaFX object to contain the tableView within.
     */
    public void init(Pagination paginator, Search search, TableViewController tableViewController) {
        this.search = search;
        this.tableViewController = tableViewController;
        tableContainer = paginator;
        initTableView();
        initSorting();
        initColumns();
        initPagination();
        showExtendedView();
    }

    /**
     * Initializes the tableView object such that it will expand to fill the available panel's space
     */
    private void initTableView() {
        crashTableView = new TableView<>();
        crashTableView.setMaxHeight(Double.MAX_VALUE);
        crashTableView.setMinHeight(Region.USE_COMPUTED_SIZE);
        crashTableView.setMaxWidth(Double.MAX_VALUE);
        crashTableView.setMinWidth(Region.USE_COMPUTED_SIZE);
    }

    /**
     * Replaces the existing sorting built into the tableView with a call to update the search object and repopulate the
     * table from an SQL query.
     * First we store the changed sort policy locally. Then we assign values to the search object if present.
     * Then we update our rows based on the search object.
     */
    private void initSorting() {
        crashTableView.setSortPolicy(p -> {
            while (crashTableView.getSortOrder().size() > 2) {
                crashTableView.getSortOrder().remove(2);
            }
            ObservableList<TableColumn<CrashFromDatabase, ?>> sortOrder = crashTableView.getSortOrder();
            if (!sortOrder.isEmpty()) {
                search.setPrimarySort(TableColumns.Column.getColumnFromLabel(sortOrder.get(0).getId()));
                search.setPrimarySortDirection(TableColumns.getDirFromType(sortOrder.get(0).getSortType()));
            } else {
                search.setPrimarySort(TableColumns.Column.ID);
                search.setPrimarySortDirection(TableColumns.SortDirection.ASCENDING);
            }
            if (sortOrder.size() > 1) {
                search.setSecondarySort(TableColumns.Column.getColumnFromLabel(sortOrder.get(1).getId()));
                search.setSecondarySortDirection(TableColumns.getDirFromType(sortOrder.get(1).getSortType()));
            } else {
                search.setSecondarySort(TableColumns.Column.ID);
                search.setSecondarySortDirection(TableColumns.SortDirection.ASCENDING);
            }
            tableViewController.getNavigationController().toggleViewUpdating(true);
            requestRowsUpdate(null);
            return true;
        });
    }


    /**
     * Adds columns to the tableView.
     * Columns are generated from the TableColumns.Column enumeration.
     * Columns are nested into groups from the TableColumns.ColumnGroup enumeration.
     * Columns without a group (the empty columns sideroad and intersection, for instance) are not added.
     * The Location, Coordinates, and Weather groups are further nested into General.
     * Each column has its label and tooltip set from the Column enumeration, and its propertyValueFactory
     * set to match the name of the corresponding field in the csv file.
     * All columns except for the ALL and GENERAL groupings are set as invisible by default (ALL refers to unhide-able fields, such as ID).
     * The columns are added to the table.
     */
    private void initColumns() {
        ArrayList<TableColumn<CrashFromDatabase, ?>> columnsAll = new ArrayList<TableColumn<CrashFromDatabase, ?>>();
        ArrayList<TableColumn<CrashFromDatabase, ?>> columnsGeneral = new ArrayList<TableColumn<CrashFromDatabase, ?>>();
        ArrayList<TableColumn<CrashFromDatabase, ?>> columnsAdditional = new ArrayList<TableColumn<CrashFromDatabase, ?>>();
        ArrayList<TableColumn<CrashFromDatabase, ?>> columnsExtra = new ArrayList<TableColumn<CrashFromDatabase, ?>>();
        ArrayList<TableColumn<CrashFromDatabase, ?>> columnsInjuries = new ArrayList<TableColumn<CrashFromDatabase, ?>>();
        ArrayList<TableColumn<CrashFromDatabase, ?>> columnsParticipants = new ArrayList<TableColumn<CrashFromDatabase, ?>>();
        ArrayList<TableColumn<CrashFromDatabase, ?>> columnsCollisions = new ArrayList<TableColumn<CrashFromDatabase, ?>>();

        for (TableColumns.Column column : TableColumns.Column.values()) {
            TableColumn<CrashFromDatabase, ?> col = new TableColumn<>();

            Label colLabel = new Label(column.label);
            colLabel.setTooltip(new Tooltip(column.tooltipDesc));
            col.setGraphic(colLabel);
            col.setCellValueFactory(new PropertyValueFactory<>(column.csvLabel));
            col.setId(column.label);
            col.setMinWidth(30);
            col.setMaxWidth(300.0);
            col.setPrefWidth(column.getWidth());
            if (column.group == TableColumns.ColumnGroup.ALL) {
                columnsAll.add(col);
            }else if (column.group == TableColumns.ColumnGroup.GENERAL) {
                columnsGeneral.add(col);
            }else if (column.group == TableColumns.ColumnGroup.ADDITIONAL) {
                columnsAdditional.add(col);
            }else if (column.group == TableColumns.ColumnGroup.EXTRA) {
                columnsExtra.add(col);
            }else if (column.group == TableColumns.ColumnGroup.INJURIES) {
                columnsInjuries.add(col);
            }else if (column.group == TableColumns.ColumnGroup.PARTICIPANTS) {
                columnsParticipants.add(col);
            } else if (column.group == TableColumns.ColumnGroup.COLLISIONS) {
                columnsCollisions.add(col);
            }
        }
        columnsAll.sort(Comparator.comparing(TableColumnBase::getText));
        columnsGeneral.sort(Comparator.comparing(TableColumnBase::getText));
        columnsAdditional.sort(Comparator.comparing(TableColumnBase::getText));
        columnsExtra.sort(Comparator.comparing(TableColumnBase::getText));
        columnsCollisions.sort(Comparator.comparing(TableColumnBase::getText));
        columnsParticipants.sort(Comparator.comparing(TableColumnBase::getText));

        columnsGeneral.stream().forEach(x -> x.setVisible(false));
        columnsAdditional.stream().forEach(x -> x.setVisible(false));
        columnsExtra.stream().forEach(x -> x.setVisible(false));
        columnsCollisions.stream().forEach(x -> x.setVisible(false));
        columnsInjuries.stream().forEach(x -> x.setVisible(false));
        columnsParticipants.stream().forEach(x -> x.setVisible(false));

        TableColumn<CrashFromDatabase, ?> general = new TableColumn<>("General Information");
        general.setId(TableColumns.ColumnGroup.GENERAL.getName());
        TableColumn<CrashFromDatabase, ?> additional = new TableColumn<>("Additional Information");
        additional.setId(TableColumns.ColumnGroup.ADDITIONAL.getName());
        TableColumn<CrashFromDatabase, ?> extra = new TableColumn<>("Extra Information");
        extra.setId(TableColumns.ColumnGroup.EXTRA.getName());
        TableColumn<CrashFromDatabase, ?> injuries = new TableColumn<>("Injury Counts");
        injuries.setId(TableColumns.ColumnGroup.INJURIES.getName());
        TableColumn<CrashFromDatabase, ?> participants = new TableColumn<>("Participant Counts");
        participants.setId(TableColumns.ColumnGroup.PARTICIPANTS.getName());
        TableColumn<CrashFromDatabase, ?> collisions = new TableColumn<>("Collision Counts");
        collisions.setId(TableColumns.ColumnGroup.COLLISIONS.getName());

        general.getColumns().addAll(columnsGeneral);
        additional.getColumns().addAll(columnsAdditional);
        extra.getColumns().addAll(columnsExtra);
        collisions.getColumns().addAll(columnsCollisions);
        injuries.getColumns().addAll(columnsInjuries);
        participants.getColumns().addAll(columnsParticipants);

        crashTableView.getColumns().addAll(columnsAll);
        crashTableView.getColumns().addAll(general);
        crashTableView.getColumns().addAll(additional);
        crashTableView.getColumns().addAll(extra);
        crashTableView.getColumns().addAll(injuries);
        crashTableView.getColumns().addAll(participants);
        crashTableView.getColumns().addAll(collisions);
    }

    /**
     * Request a replacement for the rows in the table and the updates the number of records that the requested
     * search query will return. The requested query will be run on a seperate thread which will call RowsUpdate
     * when it has finished.,
     * this function calculates pagination
     * Pass numResults in as newCount to update the stored number if required.
     * @param newCount the number of results from the search query. null if value hasn't changed.
     */
    public void requestRowsUpdate(Integer newCount) {
        if (newCount != null) {
            ArrayList<ResultNumberListener> callers = new ArrayList<>();
            callers.add(this);
            crashManager.numResults(search, callers);
        }

        crashManager.runTableQuery(search, this);
    }

    /**
     * Function updates table values to inputted array list, will be called from worker thread when a
     * query is run.
     * @param newValues new values to display
     */
    public void RowsUpdate(ArrayList<CrashFromDatabase> newValues, Search searchObject)
    {
        Platform.runLater(()-> {
            if (searchObject == search) {
                crashTableView.getItems().clear();
                ObservableList<CrashFromDatabase> list = FXCollections.observableArrayList(newValues);
                crashTableView.getItems().setAll(list);
                tableViewController.getNavigationController().toggleViewUpdating(false);
            }
        });
    }

    /**
     * Updates the table's stored number of results from the search query with the new count value.
     * To mitigate race conditions, the passed searchObject must match the stored search for the update to occur.
     * The stored number of results is used in math related to table pagination.
     * @param newNum Number of results the searchObject's query yielded
     * @param searchObject Search object used in the numRows SQL query
     */
    @Override
    public void updateNumRow(Integer newNum, Search searchObject) {
        Platform.runLater (() -> {
            if (search == searchObject) {
                this.numResults.set(0,newNum);
            }
        });
    }


    /**
     * We assign to the pagination object a page factory, which simply returns the current page object from
     * the method createPage().
     */
    private void initPagination() {
        tableContainer.setPageFactory(this::createPage);
        log.info("establishing pagination");
        numResults.addListener((ListChangeListener<Integer>) c -> {
            log.info("updating pagination");
            Integer result = numResults.get(0);
            tableContainer.setPageCount(Math.max((result + search.getPageSize()-1) / search.getPageSize(), 1));
        });
    }

    /**
     * At the moment, this returns the tableView anchored to an anchorpane to fill.
     * In the future, it will paginate the table's results.
     * This will likely be achieved by updating the start/finish indexes for our SQL search query, then clearing and
     * loading in the resultant data as items in the table.
     * @param index The index of the page to navigate to. Remember 0 is the index of the first page.
     * @return An anchorpane containing the tableView, stretched to fill its parent.
     */
    private AnchorPane createPage(Integer index) {
        search.setPage(index);
        requestRowsUpdate(null);
        handleFirstLastButtons();
        AnchorPane pane = new AnchorPane();
        pane.getChildren().add(crashTableView);
        AnchorPane.setTopAnchor(crashTableView, 0.0);
        AnchorPane.setRightAnchor(crashTableView, 0.0);
        AnchorPane.setBottomAnchor(crashTableView, 0.0);
        AnchorPane.setLeftAnchor(crashTableView, 0.0);
        return pane;
    }

    /**
     * Navigate to the specified page number.
     * Calls the paginator with an index. Page indices start at 0, while page numbers start at 1.
     * A value of -1 means the last page with results.
     * @param pageNumber Page number to navigate to.
     */
    public void toPage(Integer pageNumber) {
        if (pageNumber == -1) {
            tableContainer.setCurrentPageIndex(tableContainer.getPageCount() - 1);
            search.setPage(tableContainer.getPageCount() - 1);
        } else {
            tableContainer.setCurrentPageIndex(Math.min(pageNumber - 1, tableContainer.getPageCount() - 1));
            search.setPage(Math.min(pageNumber - 1, tableContainer.getPageCount() - 1));
        }
        handleFirstLastButtons();
        requestRowsUpdate(null);
    }

    /**
     * Logically handles enabling/disabling the first/last page buttons in the GUI
     * based on the number of pages, and the current page.
     * (i.e., if on the last page with results, disable the last page button)
     */
    public void handleFirstLastButtons() {
        int currentIndex = tableContainer.getCurrentPageIndex();
        int numPages = tableContainer.getPageCount();
        if (numPages == 1) {
            tableViewController.setEnabledFirstPage(false);
            tableViewController.setEnabledLastPage(false);
        } else {
            if (currentIndex == 0) {
                tableViewController.setEnabledFirstPage(false);
                tableViewController.setEnabledLastPage(true);
            } else if (currentIndex == numPages - 1) {
                tableViewController.setEnabledFirstPage(true);
                tableViewController.setEnabledLastPage(false);
            } else {
                tableViewController.setEnabledFirstPage(true);
                tableViewController.setEnabledLastPage(true);
            }
        }
    }


    /**
     * Function to handle the logic for a change in the column group checkboxes selected by the user.
     * This function has a bit of a hack: To prevent infinite recursion, this function can only be called while the parent
     * checkComboBox object is enabled. The function disables the checkComboBox while making any changes to it, then enables
     * it when done,
     * First, the function creates add/remove lists of indices.
     * Then, for each of these items, the function first updates the groupComboBox string of format (#/#): GroupName
     * such that the number of items selected matches the size of the group (selecting a group selects all columns in the group),
     * or is set to zero (deselecting a group deselects all columns in the group).
     * The method for editing the string in place is inefficient -- I found out that checkComboBoxes do in fact have a
     * string constructor method for their stored object -- but it works, and it'd take some time to change.
     * Then, the function updates the checked indices for the checkComboBox to reflect whichever groups have been selected.
     * Finally, the function updates the columnComboBox so that its state is equivalent to the groupColumnBox one. Any columns
     * within the added/removed group will have their indices checked/unchecked.
     * @param c The listChangeListener change event object
     * @param columnBox The individual column checkComboBox for selecting visible columns in the table.
     * @param groupBox The column group checkComboBox for selecting visible groups of columns in the table.
     */
    public void updateVisibleGroups(ListChangeListener.Change c, CheckComboBox columnBox, CheckComboBox groupBox) {
        List<Integer> added = c.getAddedSubList();
        List<Integer> removed = c.getRemoved();
        ObservableList<TableColumn<CrashFromDatabase, ?>> parents = crashTableView.getColumns();
        List<String> groupBoxItems = groupBox.getItems();
        groupBox.setDisable(true);
        for (int index : added) {
            String add = groupBoxItems.get(index);
            String groupName = removeStartFromString(add);
            String toReplace = generateGroupString(TableColumns.ColumnGroup.getGroupFromName(groupName), -1);
            groupBox.getItems().set(index, toReplace);
            groupBox.getCheckModel().checkIndices(index);
            columnBox.setDisable(true);
            List<String> colNames = TableColumns.getColumnsOfGroup(TableColumns.ColumnGroup.getGroupFromName(groupName)).stream().map(TableColumns.Column::getLabel).toList();
            List<String> colBoxItems = columnBox.getItems().stream().map(e -> removeStartFromString(e.toString())).toList();
            for (String colName : colNames) {
                if (colBoxItems.contains(colName)) {
                    Integer i = colBoxItems.indexOf(colName);
                    columnBox.getCheckModel().checkIndices(i);
                }
            }
            columnBox.setDisable(false);
            for (TableColumn parent : parents) {
                if (parent.getId().equals(groupName)) {
                    parent.setVisible(true);
                    if (!parent.getColumns().isEmpty()) {
                        ObservableList<TableColumn<CrashFromDatabase, ?>> children = parent.getColumns();
                        for (TableColumn child : children) {
                            child.setVisible(true);
                            if (!child.getColumns().isEmpty()) {
                                ObservableList<TableColumn<CrashFromDatabase, ?>> leaves = child.getColumns();
                                for (TableColumn leaf : leaves) {
                                    leaf.setVisible(true);
                                }
                            }
                        }
                    }
                }
            }
        }
        for (int index : removed) {
            String remove = groupBoxItems.get(index);
            String groupName = removeStartFromString(remove);
            String toReplace = generateGroupString(TableColumns.ColumnGroup.getGroupFromName(groupName), 0);
            groupBox.getItems().set(index, toReplace);
            groupBox.getCheckModel().clearCheck(index);
            columnBox.setDisable(true);
            List<String> colNames = TableColumns.getColumnsOfGroup(TableColumns.ColumnGroup.getGroupFromName(groupName)).stream().map(e -> e.getLabel()).toList();
            List<String> colBoxItems = columnBox.getItems().stream().map(e -> removeStartFromString(e.toString())).toList();
            for (String colName : colNames) {
                if (colBoxItems.contains(colName)) {
                    int i = colBoxItems.indexOf(colName);
                    columnBox.getCheckModel().clearCheck(i);
                }
            }
            columnBox.setDisable(false);
            for (TableColumn parent : parents) {
                if (parent.getId().equals(groupName)) {
                    parent.setVisible(false);
                    if (!parent.getColumns().isEmpty()) {
                        ObservableList<TableColumn<CrashFromDatabase, ?>> children = parent.getColumns();
                        for (TableColumn child : children) {
                            child.setVisible(false);
                            if (!child.getColumns().isEmpty()) {
                                ObservableList<TableColumn<CrashFromDatabase, ?>> leaves = child.getColumns();
                                for (TableColumn leaf : leaves) {
                                    leaf.setVisible(false);
                                }
                            }
                        }
                    }
                }
            }
        }
        groupBox.setDisable(false);
    }

    /**
     * Function to handle the logic for a change in the individual column checkboxes selected by the user.
     * This function has a bit of a hack: To prevent infinite recursion, this function can only be called while the parent
     * checkComboBox object is enabled. The function disables the checkComboBox while making any changes to it, then enables
     * it when done,
     * First, the function creates add/remove lists of indices.
     * Then, for each of these items, the function first updates the groupComboBox string of format (#/#): GroupName
     * such that the number of items selected matches the size of the group (+/- 1 for the group affected by the selection).
     * The method for editing the string in place is inefficient -- I found out that checkComboBoxes do in fact have a
     * string constructor method for their stored object -- but it works, and it'd take some time to change.
     * Then, the function updates the checked indices for the checkComboBox to reflect whichever columns have been selected.
     * Finally, the functionchecks if any group has been filled/broken by the changes. i.e. (9/10) -> (10/10) General should result
     * in General being checked, while the reverse should result in it being unchecked.
     * @param c The listChangeListener change event object
     * @param columnBox The individual column checkComboBox for selecting visible columns in the table.
     * @param groupBox The column group checkComboBox for selecting visible groups of columns in the table.
     */
    public void updateVisibleColumns(ListChangeListener.Change c, CheckComboBox columnBox, CheckComboBox groupBox) {
        List<Integer> added = c.getAddedSubList();
        List<Integer> removed = c.getRemoved();
        ObservableList<TableColumn<CrashFromDatabase, ?>> parents = crashTableView.getColumns();
        List<String> colBoxItems = columnBox.getItems();
        List<TableColumns.Column> checkedCols = new ArrayList<>();
        for (Object item : columnBox.getCheckModel().getCheckedItems()) {
            String name = removeStartFromString(item.toString());
            checkedCols.add(TableColumns.Column.getColumnFromLabel(name));
        }
        columnBox.setDisable(true);
        for (int index : added) {
            String add = colBoxItems.get(index);
            String colName = removeStartFromString(add);
            columnBox.getCheckModel().checkIndices(index);
            groupBox.setDisable(true);
            ObservableList checked = groupBox.getCheckModel().getCheckedIndices();

            TableColumns.ColumnGroup addGroup = TableColumns.Column.getGroupFromLabel(colName);
            List<String> groupStrings = groupBox.getItems().stream().map(e -> removeStartFromString(e.toString())).toList();
            int groupIndex = groupStrings.indexOf(addGroup.getName());
            List<TableColumns.Column> shared = new ArrayList<>(checkedCols);
            shared.retainAll(TableColumns.getColumnsOfGroup(addGroup));
            groupBox.getItems().set(groupIndex, generateGroupString(addGroup, shared.size()));
            if (shared.size() == TableColumns.getColumnsOfGroup(addGroup).size()) {
                groupBox.getCheckModel().checkIndices(groupIndex);
            }
            for (Object check : checked) {
                groupBox.getCheckModel().checkIndices((Integer) check);
            }
            groupBox.setDisable(false);
            List<String> colStrings = columnBox.getCheckModel().getCheckedItems().stream().map(e -> removeStartFromString(e.toString())).toList();
            for (TableColumn parent : parents) {
                if (Objects.equals(parent.getId(), addGroup.getName())) {
                    parent.setVisible(true);
                }if (!parent.getColumns().isEmpty()) {
                    ObservableList<TableColumn> children = parent.getColumns();
                    for (TableColumn child : children) {
                        if (colStrings.contains(child.getId())) {
                            child.setVisible(true);
                        } else {
                            child.setVisible(false);
                        }
                    }
                }
            }
        }
        for (int index : removed) {
            String remove = colBoxItems.get(index);
            String colName = removeStartFromString(remove);
            columnBox.getCheckModel().clearCheck(index);
            groupBox.setDisable(true);
            TableColumns.ColumnGroup removeGroup = TableColumns.Column.getGroupFromLabel(colName);
            List<String> groupStrings = groupBox.getItems().stream().map(e -> removeStartFromString(e.toString())).toList();
            int groupIndex = groupStrings.indexOf(removeGroup.getName());
            if (groupIndex == -1) {
                groupIndex = 0;
            }
            List<TableColumns.Column> shared = new ArrayList<>(checkedCols);
            shared.retainAll(TableColumns.getColumnsOfGroup(removeGroup));
            groupBox.getItems().set(groupIndex, generateGroupString(removeGroup, shared.size()));
            if ((shared.size() != TableColumns.getColumnsOfGroup(removeGroup).size())) {
                groupBox.getCheckModel().clearCheck(groupIndex);
            }
            groupBox.setDisable(false);
            for (TableColumn parent : parents) {
                if (Objects.equals(parent.getId(), colName)) {
                    parent.setVisible(false);
                }else if (!parent.getColumns().isEmpty()) {
                    ObservableList<TableColumn> children = parent.getColumns();
                    for (TableColumn child : children) {
                        if (Objects.equals(child.getId(), colName)) {
                            child.setVisible(false);
                        } else if (!child.getColumns().isEmpty()) {
                            ObservableList<TableColumn> leaves = child.getColumns();
                            for (TableColumn leaf : leaves) {
                                if (Objects.equals(leaf.getId(), colName)) {
                                    leaf.setVisible(false);
                                }
                            }
                        }
                    }
                }
            }
        }
        columnBox.setDisable(false);
    }

    /**
     * Generates a string of the format '(a/b): GroupName', where a is the number of columns in the group that are visible,
     * b is the size of total columns in the group, and GroupName is the name of the group.
     * @param group ColumnGroup object representing the group in the string
     * @param selected The number of columns in the group that are visible
     * @return The formatted string "(a/b): GroupName"
     */
    public String generateGroupString(TableColumns.ColumnGroup group, Integer selected) {
        Integer groupSize = TableColumns.getColumnsOfGroup(group).size();
        String groupName = group.getName();
        if (selected == -1) {
            return String.format("(%d/%d): %s", groupSize, groupSize, groupName);
        } else {
            return String.format("(%d/%d): %s", selected, groupSize, groupName);
        }
    }

    /**
     * Removes the preamble from a column drop-down item string.
     * i.e. "(8/10): General" -> "General"
     * and  "(General) Latitude" -> "Latitude"
     * @param colGroupString The original string to pass in
     * @return The string with its bracketed preamble removed
     */
    public String removeStartFromString(String colGroupString) {
        ArrayList<String> stringFragments = new ArrayList<>();
        Collections.addAll(stringFragments, colGroupString.split(" "));
        stringFragments.remove(0);
        return String.join(" ", stringFragments);
    }

  /**
   * When double left mouse click on row of table, call ExtendedViewController which creates a pop-up where all the details
   * about the current row are shown.
   */
  private void showExtendedView() {
    crashTableView.setOnMousePressed(
        event -> {
          if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
            CrashFromDatabase selectedCrash = crashTableView.getSelectionModel().getSelectedItem();
            if (selectedCrash != null) {
              ExtendedViewController newView = new ExtendedViewController();
              newView.showExtendedView(selectedCrash);
            }
          }
        });
        }
    /**
     * When the page size is changed in TableViewController, the current page index and page count may change.
     * The number of pages is recalculated when this is called.
     * @param newSize number of rows displayed per page in the table now
     */
    public void handlePageSizeChange(Integer oldSize, Integer newSize) {
        Integer result = numResults.get(0);
        Integer newPageCount = Math.max((result + newSize-1) / newSize, 1);
        Integer currentPageIndex = tableContainer.getCurrentPageIndex();
        Integer firstItemIndex = currentPageIndex * oldSize;
        tableContainer.setPageCount(newPageCount);
        if (currentPageIndex > newPageCount-1) {
            tableContainer.setCurrentPageIndex(newPageCount-1);
            search.setPage(newPageCount-1);
        } else {
            Integer newPageIndex = ((firstItemIndex + newSize-1) / newSize) - 1;
            tableContainer.setCurrentPageIndex(newPageIndex);
            search.setPage(newPageIndex);
        }
    }
}