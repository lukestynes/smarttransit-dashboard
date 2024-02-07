package seng202.team5.gui;

import io.github.palexdev.mfxcore.utils.fx.SwingFXUtils;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.*;

import seng202.team5.chart.ChartManager;
import seng202.team5.models.GenericChart;
import seng202.team5.models.GenericChart.ChartType;
import seng202.team5.models.Search;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

/**
 * This class manages any calls from the UI to modify the chart view.
 * It calls a ChartManager to implement most of these changes.
 * @author Zoe Perry
 */
public class ChartViewController {
    private Stage stage;
    private Search search;
    @FXML
    private Button toggleCreateButton;
    @FXML
    private Button toggleSearchButton;
    @FXML
    private AnchorPane chartContainer;
    @FXML
    private ComboBox chartTypeComboBox;
    @FXML
    private ComboBox varOneComboBox;
    @FXML
    private ComboBox varTwoComboBox;
    @FXML
    private CheckBox noNullCheckBox;
    @FXML
    private Button titleButton;
    @FXML
    private Button saveButton;
    @FXML
    private VBox infoBox;
    private ChartManager chartManager;
    private NavigationController navigationController;

    /**
    * Initializes the controller with the window it will be working with to display the charts.
     * @param stage the window to which to display
     * @param search the search object
    */
    public void init(Stage stage, Search search, NavigationController navigationController) {
        this.stage = stage;
        stage.getScene().getStylesheets().add("/css/Main.css");
        stage.getScene().getStylesheets().add("/css/Chart.css");
        this.search = search;
        this.navigationController = navigationController;
        initComboBoxes();
        initChartManager(chartContainer, search);
        stage.setMinWidth(stage.getWidth());
        stage.setMinHeight(stage.getHeight());
        initHelp();
    }

    /**
     * Creates help mode tooltips for the Chart View.
     */
    private void initHelp() {
        navigationController.createHelpEvent(chartTypeComboBox, "Select which type of chart to display.");
        navigationController.createHelpEvent(toggleCreateButton, "Click this to create a chart.\nA chart type and corresponding variables must be selected first.");
        navigationController.createHelpEvent(varOneComboBox, "Select a variable for the chart to display data for.");
        navigationController.createHelpEvent(varTwoComboBox, "Select a variable to group data in the chart by.");
        navigationController.createHelpEvent(noNullCheckBox, "Select this box to remove all data with unknown values.");
        navigationController.createHelpEvent(titleButton, "Click this to change the title of the displayed chart.");
        navigationController.createHelpEvent(saveButton, "Click this to save the displayed chart to your local files.");
    }

    /**
    * Initializes the combo box used to choose the chart type to display.
    */
    private void initComboBoxes() {
        List<String> chartTypeOptions = Stream.of(ChartType.values()).map(g -> g.name).toList();
        chartTypeComboBox.getItems().addAll(chartTypeOptions);
    }

    /**
    * Called by a change to the selected value of the chart type combo box.
    * It will call a function to record the new desired chart type and another function to adjust the axes/category options that can be selected for that type of chart.
    */
    @FXML
    void handleChartChange() {changeChartType(); makeComboBoxVisible();}

    /**
    * Changes the desired chart type in chart manager so once a call is made to draw a chart, the right type is drawn.
     */
    private void changeChartType() {
        for (GenericChart.ChartType chart : GenericChart.ChartType.values()) {
            if (chart.name == chartTypeComboBox.getValue()) {
                ChartManager.changeChartType(chart);
            }
        }
    }

    /**
    * Toggles the visibility of combo boxes based on their relevance to the chart type.
    * Adjusts the options loaded into the visible combo boxes to the relevant categories.
     */
    private void makeComboBoxVisible() {
        if (chartTypeComboBox.getValue() == ChartType.PIE.name || chartTypeComboBox.getValue() == ChartType.BAR.name) {
            List<String> categoryOptions = Stream.of(GenericChart.Categories.values()).map(g -> g.name).toList();
            varOneComboBox.setVisible(true);
            varTwoComboBox.setVisible(false);
            varOneComboBox.getItems().clear();
            varOneComboBox.getItems().addAll(categoryOptions);
        } else if (chartTypeComboBox.getValue() == ChartType.LINE.name) {
            List<String> categoryOptions = Stream.of(GenericChart.Continuous.values()).map(g -> g.name).toList();
            List<String> countOptions = Stream.of(GenericChart.Counts.values()).map(g -> g.name).toList();
            varOneComboBox.setVisible(true);
            varTwoComboBox.setVisible(true);
            varOneComboBox.getItems().clear();
            varOneComboBox.getItems().addAll(categoryOptions);
            varTwoComboBox.getItems().clear();
            varTwoComboBox.getItems().addAll(countOptions);
        } else {
                List<String> axisTypeOptions = Stream.of(GenericChart.Continuous.values()).map(g -> g.name).toList();
                List<String> categoryOptions = Stream.of(GenericChart.Categories.values()).map(g -> g.name).toList();
                varOneComboBox.setVisible(true);
                varTwoComboBox.setVisible(true);
                varOneComboBox.getItems().clear();
                varOneComboBox.getItems().addAll(axisTypeOptions);
                varTwoComboBox.getItems().clear();
                varTwoComboBox.getItems().addAll(categoryOptions);
        }
    }

    /**
     * Called by the UI when the Toggle Create button is clicked.
     * Checks that a chart type and axes are selected before creating.
     */
    @FXML
    void handleToggleCreateButton() {
        if (chartTypeComboBox.getValue() == ChartType.PIE.name || chartTypeComboBox.getValue() == ChartType.BAR.name) {
            if (varOneComboBox.getValue() != null) {
                createGraph();
                infoBox.setVisible(false);
                titleButton.setVisible(true);
                saveButton.setVisible(true);
                if (chartTypeComboBox.getValue() == ChartType.PIE.name || chartTypeComboBox.getValue() == ChartType.BAR.name) {
                    noNullCheckBox.setVisible(true);
                } else {
                    noNullCheckBox.setVisible(false);
                }
            }
            else {
                ErrorModalController.showError("Please select a variable to graph");
            }
        } else if (chartTypeComboBox.getValue() == null) {
            ErrorModalController.showError("Please select a type of graph");
        } else {
            if (varOneComboBox.getValue() != null && varTwoComboBox.getValue() != null) {
                createGraph();
                infoBox.setVisible(false);
                titleButton.setVisible(true);
                saveButton.setVisible(true);
                noNullCheckBox.setVisible(true);
            }
            else {
                ErrorModalController.showError("Please select two variables to graph");
            }
        }
    }


    /**
     * Called when the Null Values check box is adjusted and creates a new graph to suit the new specifications.
     */
    @FXML
    void toggleNoNullCheckBox() {
        ChartManager.changeNullValues(noNullCheckBox.isSelected());
        createGraph();
    }


    /**
     * Called when the title button in clicked in chart view.
     * Calls userinputcontroller to have the user input a new title which will then call newTitleChart() to set the new title.
     */
    @FXML
    void toggleTitleButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/userinput.fxml"));
            Parent root = loader.load();

            Stage inputView = new Stage();
            Scene scene = new Scene(root);
            inputView.setScene(scene);

            UserInputController controller = loader.getController();
            controller.init("Please Enter New Title", this, inputView, UserInputController.InputType.TITLE);

            inputView.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the current chart to have a newly customised title.
     * @param title
     */
    void newTitleChart(String title) {
        ChartManager.setTitle(title);
        createGraph();
    }

    /**
     * Called when the save button is clicked in the chart view.
     * Calls saveImage() to save the chart as an image to the local host.
     */
    @FXML
    void toggleSaveButton() {
        navigationController.closeSearch();
        navigationController.closeSidePane();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose where to save graph");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", "*.png"));
        try {
            fileChooser.setInitialDirectory(new File(NavigationController.class.getProtectionDomain().getCodeSource()
                    .getLocation().toURI()).getParentFile());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        File saveFile = fileChooser.showSaveDialog(stage);
        try {
            saveImage(saveFile.getAbsolutePath());
        }
        catch (NullPointerException err)
        {
            ErrorModalController.showError("Image not saved, please choose a valid location");
        }
    }

    /**
     * Saves an image of the chart view to the specified destination on the local host.
     * @param absolutePath is the path to where the image should be saved.
     */
    void saveImage(String absolutePath) {
        WritableImage image = stage.getScene().snapshot(new WritableImage((int)chartContainer.getWidth(), (int)stage.getHeight()));
//        BufferedImage img = SwingFXUtils.fromFXImage(image, null);
//        BufferedImage scaled = img.getSubimage(0,((int)stage.getHeight()-(int)chartContainer.getHeight()), (int)image.getWidth(), (int)chartContainer.getHeight());
        File file = new File(absolutePath);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
//            ImageIO.write(scaled, "png", file);
        } catch (IOException e) {
            ErrorModalController.showError("Error while saving file: " + e.getMessage());
        }
    }


    /**
     * Calls chart manager to draw a chart with the information it has stored.
     */
    private void createGraph() {
        chartManager.drawChart(varOneComboBox.getValue(), varTwoComboBox.getValue());
    }

    /**
     * Initializes a new chart manager to take care of creating and displaying the charts.
     * @param chartContainer is the window to display charts into.
     * @param search is the object that filters the crashes into the group of them that we want to view in the chart.
     */
    private void initChartManager(AnchorPane chartContainer, Search search) {
        chartManager = new ChartManager();
        chartManager.init(chartContainer, search);
    }

    /**
     * Updates the graph whenever the search object has an update. (New filters applied)
     */
    public void updateFromSearch() {
            createGraph();

    }
}
