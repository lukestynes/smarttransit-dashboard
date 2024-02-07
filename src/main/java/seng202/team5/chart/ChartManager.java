package seng202.team5.chart;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.chart.*;
import javafx.scene.layout.AnchorPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import seng202.team5.business.CrashManager;
import seng202.team5.database.CrashCount;
import seng202.team5.gui.ErrorModalController;
import seng202.team5.models.GenericChart;
import seng202.team5.models.Search;

import java.util.ArrayList;

/**
 * This class handles any logic related to chart setup and drawing into the correct window.
 * @author Zoe Perry
 */
public class ChartManager {
    private static final Logger log = LogManager.getLogger(ChartManager.class);
    private AnchorPane chartContainer;
    static public GenericChart.ChartType chartType;
    static private boolean noNullValues = false;
    static private String title;
    static private boolean titleSet = false;
    private CrashManager crashManager = new CrashManager();
    private Search search;

    /**
     * Initializes a chart manager to keep track of the desired chart type and how to draw it with the given data.
     * @param chartContainer is where the charts should be displayed.
     * @param search is how the crashes are filtered before being put into the charts.
     */
    public void init(AnchorPane chartContainer, Search search) {
        this.chartContainer = chartContainer;
        this.search = search;
        chartContainer.getChildren().clear();
    }

    /**
     * Changes the selected chart type to the given type so the next drawn is the correct type.
     * @param type is the updated type of chart.
     */
    static public void changeChartType(GenericChart.ChartType type) {
        chartType = type;
        log.info("Chart Type now " + type);
    }

    /**
     * Changes the variable that keeps track of if the user is happy to see data that has not been recorded correctly/Null in the database.
     * @param wanted is a boolean about if null values are wanted.
     */
    static public void changeNullValues(boolean wanted) {
        noNullValues = wanted;
        log.info("Null values switch set at "+wanted);
    }

    /**
     * Sets the title of the current graph to be the given string.
     * @param newTitle is the string to be set as the title.
     */
    static public void setTitle(String newTitle) {
        title = newTitle;
        titleSet = true;
    }

    /**
     * Is called when a chart should be displayed and calls the correct function depending on the chart type that is stored in the object.
     * @param varOne is the category of the pie chart or x axis of the other chart types.
     * @param varTwo is the y axis of the cart types other than pie.
     */
    public void drawChart(Object varOne, Object varTwo) {
        Platform.runLater(() -> {
        if (chartType != null) {
            switch (chartType) {
                case AREA:
                    drawAreaChart(varOne, varTwo);
                    log.info("Drawing Area Chart of " + varOne + " and " + varTwo);
                    titleSet = false;
                    break;
                case BAR:
                    drawBarChart(varOne);
                    log.info("Drawing Bar Chart of " + varOne);
                    titleSet = false;
                    break;
                case LINE:
                    drawLineChart(varOne, varTwo);
                    log.info("Drawing Line Chart of " + varOne);
                    titleSet = false;
                    break;
                case PIE:
                    drawPieChart(varOne);
                    log.info("Drawing Pie Chart of " + varOne);
                    titleSet = false;
                    break;
            }
        }
        else {
            ErrorModalController.showError("Please choose a chart type first");
        }


    });
    }

    /**
     * Gets a list of crashes that should be included in the chart.
     * @param varOne is the category to group the crashes into.
     * @return a list of crashes that should be included in the chart that is calling the function.
     */
    private ArrayList<CrashCount> getCrashes(Object varOne) {
        for (GenericChart.Categories category : GenericChart.Categories.values()) {
            if (varOne == category.name) {
                return crashManager.runCatChartQuery(search, category);
            }
        }
        for (GenericChart.Continuous category : GenericChart.Continuous.values()) {
            if (varOne == category.name) {
                return crashManager.runChartQuery(search, category);
            }
        }
        return null;
    }

    /**
     * Gets a list of crashes that should be included in the chart.
     * @param varOne is the category for the x-axis.
     * @param varTwo is the variable being counted such as number of deaths or crashes.
     * @return a list of crashes that should be included in the chart that is calling the function.
     */
    private ArrayList<CrashCount> getCrashCounts(Object varOne, Object varTwo) {
        for (GenericChart.Continuous xCategory : GenericChart.Continuous.values()) {
            if (varOne == xCategory.name) {
                for (GenericChart.Counts yCategory : GenericChart.Counts.values()) {
                    if (varTwo == yCategory.name) {
                        if (yCategory == GenericChart.Counts.CRASHCOUNT) {
                            return crashManager.runChartQuery(search, xCategory);
                        } else {
                            return crashManager.runCountChartQuery(search, xCategory, yCategory);
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Gets a list of crashes that should be included in the chart.
     * @param varOne is the category to group the crashes into.
     * @param varTwo is the continuous variable to group the crashes into.
     * @return a list of crashes that should be included in the chart that is calling the function.
     */
    private ArrayList<CrashCount> getCrashDoubles(Object varOne, Object varTwo, String value) {
        for (GenericChart.Continuous xCategory : GenericChart.Continuous.values()) {
            if (varOne == xCategory.name) {
                for (GenericChart.Categories yCategory : GenericChart.Categories.values()) {
                    if (varTwo == yCategory.name) {
                        return crashManager.runDoubleChartQuery(search, xCategory, yCategory, value);
                    }
                }
            }
        }
        return null;
    }

    /**
     * Creates and displays a pie chart grouped by the varOne category.
     * @param varOne is the category to group the pie chart by. eg Year, Speed Limit...
     */
    public void drawPieChart(Object varOne) {

        ArrayList<CrashCount> crashes = getCrashes(varOne);

        if (crashes == null || crashes.isEmpty()) {
            ErrorModalController.showError("No crashes to display in chart.");
        } else {

            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

            PieChart.Data empty = null;
            boolean emptyValue = false;

            try {
                for (CrashCount crash : crashes) {

                    if (varOne == GenericChart.Categories.REGION.name && crash.category.length() > 6) {
                        crash.category = crash.category.substring(0, (crash.category.length() - 6));
                    }

                    if (crash.category == null || crash.category.isEmpty() || crash.category.equals("Nil") || crash.category.equals("Null") || crash.category.equals("Unknown")) {
                        if (!noNullValues) {
                            emptyValue = true;
                            empty = new PieChart.Data("No Data Recorded", crash.count);
                        }
                    } else {
                        pieChartData.add(new PieChart.Data(crash.category, crash.count));
                        log.info(crash.category);
                    }
                }
                if (emptyValue) {
                    pieChartData.add(empty);
                }
                final PieChart chart = new PieChart(pieChartData);

                if (titleSet) {
                    chart.setTitle(title);
                } else {
                    chart.setTitle("Crashes by " + varOne);
                    if (varOne == GenericChart.Categories.SPEEDLIMIT.name) {
                        chart.setTitle("Crashes by " + varOne + " in km/h");
                    }
                }
                chart.setLegendSide(Side.RIGHT);

                chartContainer.getChildren().clear();
                chartContainer.getChildren().add(chart);
                chartContainer.setTopAnchor(chart, 0.0);
                chartContainer.setRightAnchor(chart, 0.0);
                chartContainer.setBottomAnchor(chart, 0.0);
                chartContainer.setLeftAnchor(chart, 0.0);

            } catch (Exception e) {
                log.info(e.getMessage());
                log.info("No crashes to display");
            }
        }
    }

    /**
     * Creates and displays a line graph.
     * @param varOne is the x-axis category.
     */
    public void drawLineChart(Object varOne, Object varTwo) {

        ArrayList<CrashCount> crashes = getCrashCounts(varOne, varTwo);

        if (crashes == null || crashes.isEmpty()) {
            ErrorModalController.showError("No crashes to display in chart.");
        } else {
            //defining the axes
            final NumberAxis xAxis;
            if (varOne == GenericChart.Continuous.YEAR.name){
                xAxis = new NumberAxis(1999, 2024, 1);
            } else {
                xAxis = new NumberAxis();
            }
            final NumberAxis yAxis = new NumberAxis();
            xAxis.setLabel(varOne.toString());
            yAxis.setLabel(varTwo.toString());
            //creating the chart

            final LineChart<Number, Number> lineChart =
                    new LineChart<Number, Number>(xAxis, yAxis);

            if (titleSet) {
                lineChart.setTitle(title);
            } else {
                lineChart.setTitle("Crashes by " + varOne);
                if (varOne == GenericChart.Categories.SPEEDLIMIT.name) {
                    lineChart.setTitle("Crashes by " + varOne + " in km/h");
                }
            }
            lineChart.setLegendVisible(false);
            //defining a series
            XYChart.Series series = new XYChart.Series();
            series.setName("Crashes");

            try {
                log.info("Adding Crashes");
                for (CrashCount crash : crashes) {
                    if (!(crash.category == null || crash.category.isEmpty() || crash.category.equals("Nil") || crash.category.equals("Null") || crash.category.equals("Unknown"))) {
                        series.getData().add(new XYChart.Data(Integer.parseInt(crash.category), crash.count));
                        log.info(Integer.parseInt(crash.category) + " graphed at " + crash.count);
                    } else {
                        log.info(crash.count + "crashes had no data recorded");
                    }
                }

                lineChart.getData().add(series);

                chartContainer.getChildren().clear();
                chartContainer.getChildren().add(lineChart);
                chartContainer.setTopAnchor(lineChart, 0.0);
                chartContainer.setRightAnchor(lineChart, 0.0);
                chartContainer.setBottomAnchor(lineChart, 0.0);
                chartContainer.setLeftAnchor(lineChart, 0.0);
                log.info("Chart complete");
            } catch (Exception e) {
                log.info(e.getMessage());
                log.info("No crashes to display");
            }
        }
    }

    /**
     * Creates and displays a bar graph.
     * @param varOne is the x-axis category.
     */
    public void drawBarChart(Object varOne) {

        ArrayList<CrashCount> crashes = getCrashes(varOne);

        if (crashes == null || crashes.isEmpty()) {
            ErrorModalController.showError("No crashes to display in chart.");
        } else {

            final CategoryAxis xAxis = new CategoryAxis();
            final NumberAxis yAxis = new NumberAxis();
            final BarChart<String, Number> barChart =
                    new BarChart<String, Number>(xAxis, yAxis);

            if (titleSet) {
                barChart.setTitle(title);
            } else {
                barChart.setTitle("Crashes by " + varOne);
                if (varOne == GenericChart.Categories.SPEEDLIMIT.name) {
                    barChart.setTitle("Crashes by " + varOne + " in km/h");
                }
            }
            xAxis.setLabel(varOne.toString());
            yAxis.setLabel("Crash Count");
            barChart.setLegendVisible(false);

            XYChart.Series series = new XYChart.Series();
            series.setName("Crashes");

            try {
                log.info("Adding Crashes");
                for (CrashCount crash : crashes) {
                    if (varOne == GenericChart.Categories.REGION.name && crash.category.length() > 6) {
                        crash.category = crash.category.substring(0, (crash.category.length() - 6));
                    }
                    if (crash.category == null || crash.category.isEmpty() || crash.category.equals("Nil") || crash.category.equals("Null") || crash.category.equals("Unknown")) {
                        if (!noNullValues) {
                            series.getData().add(new XYChart.Data("No Data Recorded", crash.count));
                        }
                    } else {
                        series.getData().add(new XYChart.Data(crash.category, crash.count));
                        log.info(crash.category);
                    }
                }

                barChart.getData().add(series);

                chartContainer.getChildren().clear();
                chartContainer.getChildren().add(barChart);
                chartContainer.setTopAnchor(barChart, 0.0);
                chartContainer.setRightAnchor(barChart, 0.0);
                chartContainer.setBottomAnchor(barChart, 0.0);
                chartContainer.setLeftAnchor(barChart, 0.0);
                log.info("Chart complete");
            } catch (Exception e) {
                log.info(e.getMessage());
                log.info("No crashes to display");
            }
        }
    }

    /**
     * Creates and displays an area graph.
     * @param varOne is the x-axis category.
     * @param varTwo is the y-axis category.
     */
    public void drawAreaChart(Object varOne, Object varTwo) {

        ArrayList<CrashCount> crashes = getCrashes(varTwo);

        if (crashes == null || crashes.isEmpty()) {
            ErrorModalController.showError("No crashes to display in chart.");
        } else {

            final NumberAxis xAxis;
            if (varOne == GenericChart.Continuous.YEAR.name) {
                xAxis = new NumberAxis(1999, 2024, 1);
            } else {
                xAxis = new NumberAxis();
            }
            final NumberAxis yAxis = new NumberAxis();
            xAxis.setLabel(varOne.toString());
            yAxis.setLabel("Crash Count");
            final AreaChart<Number, Number> ac =
                    new AreaChart<Number, Number>(xAxis, yAxis);

            if (titleSet) {
                ac.setTitle(title);
            } else {
                ac.setTitle("Crashes by " + varOne + " and " + varTwo);
                if (varTwo == GenericChart.Categories.SPEEDLIMIT.name) {
                    ac.setTitle("Crashes by " + varOne + " and " + varTwo + " in km/h");
                }
            }

            XYChart.Series empty = null;
            boolean emptyValue = false;

            for (CrashCount crash : crashes) {
                ArrayList<CrashCount> newCrashes = getCrashDoubles(varOne, varTwo, crash.category);
                log.info("Crashes for " + crash.category + " is " + newCrashes.size());
                log.info(crash.category);
                if (!(crash.category == null || crash.category.isEmpty() || crash.category.equals("Nil") || crash.category.equals("Null") || crash.category.equals("Unknown")) || !noNullValues) {
                    XYChart.Series series = new XYChart.Series();
                    if (!(crash.category == null || crash.category.isEmpty() || crash.category.equals("Nil") || crash.category.equals("Null") || crash.category.equals("Unknown"))) {
                        series.setName(crash.category);
                    } else {
                        series.setName("No Data Recorded");
                    }

                    try {
                        log.info("Adding Crashes");
                        for (CrashCount newCrash : newCrashes) {
                            if (!(newCrash.category == null || newCrash.category.isEmpty() || newCrash.category.equals("Nil") || newCrash.category.equals("Null") || newCrash.category.equals("Unknown"))) {
                                series.getData().add(new XYChart.Data(Integer.parseInt(newCrash.category), newCrash.count));
                                log.info(Integer.parseInt(newCrash.category) + " graphed at " + newCrash.count);
                            } else {
                                log.info(newCrash.count + " crashes had no data recorded");
                            }
                        }
                        if (!(crash.category == null || crash.category.isEmpty() || crash.category.equals("Nil") || crash.category.equals("Null") || crash.category.equals("Unknown"))) {
                            ac.getData().add(series);
                        } else {
                            emptyValue = true;
                            empty = series;
                        }
                    } catch (Exception e) {
                        log.info(e.getMessage());
                        log.info("No crashes to display");
                    }
                } else {
                    log.info(crash.count + " crashes had no data recorded");
                }
            }

            if (emptyValue) {
                ac.getData().add(empty);
            }

            chartContainer.getChildren().clear();
            chartContainer.getChildren().add(ac);
            chartContainer.setTopAnchor(ac, 0.0);
            chartContainer.setRightAnchor(ac, 0.0);
            chartContainer.setBottomAnchor(ac, 0.0);
            chartContainer.setLeftAnchor(ac, 0.0);
            log.info("Chart complete");
        }
    }

}
