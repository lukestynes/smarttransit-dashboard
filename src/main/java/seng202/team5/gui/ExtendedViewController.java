package seng202.team5.gui;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import seng202.team5.database.CrashFromDatabase;

import java.io.IOException;

/**
 * Controller for the extended view,
 * Creates a pop-up windows which is the populated with all the information from the passed in CrashFromDatabase object.
 * @author Sergey Antonets
 */
public class ExtendedViewController {

    @FXML
    private Label objectID;
    @FXML
    private Label speedLimit;
    @FXML
    private Label lct2;
    @FXML
    private Label avgSpeed;
    @FXML
    private Label tempSpdLmt;
    @FXML
    private Label crashDir;
    @FXML
    private Label flatHill;
    @FXML
    private Label roadSrfs;
    @FXML
    private Label deaths;
    @FXML
    private Label bicycles;
    @FXML
    private Label mbike;
    @FXML
    private Label suv;
    @FXML
    private Label vanUts;
    @FXML
    private Label bridge;
    @FXML
    private Label fence;
    @FXML
    private Label severity;
    @FXML
    private Label lat;
    @FXML
    private Label wthrA;
    @FXML
    private Label holiday;
    @FXML
    private Label trafficCtrl;
    @FXML
    private Label financeYear;
    @FXML
    private Label lanes;
    @FXML
    private Label urban;
    @FXML
    private Label minInjuries;
    @FXML
    private Label buses;
    @FXML
    private Label othrVehc;
    @FXML
    private Label taxi;
    @FXML
    private Label cliff;
    @FXML
    private Label rail;
    @FXML
    private Label year;
    @FXML
    private Label longitude;
    @FXML
    private Label wthrB;
    @FXML
    private Label light;
    @FXML
    private Label territorialAuth;
    @FXML
    private Label stHighway;
    @FXML
    private Label roadChar;
    @FXML
    private Label majInjuries;
    @FXML
    private Label carStnWag;
    @FXML
    private Label pedestrians;
    @FXML
    private Label truck;
    @FXML
    private Label debris;
    @FXML
    private Label kerb;
    @FXML
    private Label region;
    @FXML
    private Label lct1;
    @FXML
    private Label streetLight;
    @FXML
    private Label vhlcRollDir;
    @FXML
    private Label roadLanes;
    @FXML
    private Label moped;
    @FXML
    private Label schoolBus;
    @FXML
    private Label uknVehc;
    @FXML
    private Label ditch;
    @FXML
    private Label objThrow;
    @FXML 
    private Label objOther;
    @FXML
    private Label embank;
    @FXML
    private Label parkedVehc;
    @FXML
    private Label phoneBox;
    @FXML
    private Label pole;
    @FXML
    private Label roadWrks;
    @FXML
    private Label flood;
    @FXML
    private Label animal;
    @FXML
    private Label tree;
    @FXML
    private Label trafficIsland;
    @FXML
    private Label vehcHits;
    @FXML
    private Label water;
    @FXML
    private Label sign;
    @FXML
    private Label train;


    /**
     * Initializes the ExtendedViewController with the data from the row that was clicked on.
     * @param selected The row that was clicked on in table view.
     */
    public void init(CrashFromDatabase selected){
        populateData(selected);
    }

    /**
     * Creates a new modal window pop up containing the extended view. Calls populateData to populate the window.
     * @param selected The row that was clicked on in table view.
     */
    public void showExtendedView(CrashFromDatabase selected) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/extendedview.fxml"));
            Parent root = loader.load();

            ExtendedViewController controller = loader.getController();
            controller.init(selected);
            Stage extendedView = new Stage();
            Scene scene = new Scene(root);
            extendedView.setScene(scene);
            scene.getStylesheets().add("/css/Main.css");
            Image image = new Image("img/icon.png");
            extendedView.setTitle("Extended Data View");
            extendedView.getIcons().add(image);

            extendedView.show();
            extendedView.setMinWidth(extendedView.getWidth());
            extendedView.setMinHeight(extendedView.getHeight());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the FXML labels within the pop-out window corresponding to the fields of the selected row in the table view.
     * @param selected The row that was clicked on in table view.
     */
    public void populateData(CrashFromDatabase selected) {
        objectID.setText(String.valueOf((selected.getObjectID())));
        speedLimit.setText(selected.getSpeedLimit());
        lct1.setText(selected.getCrashLocation1());
        lct2.setText(selected.getCrashLocation2());
        avgSpeed.setText(selected.getAdvisorySpeed());
        tempSpdLmt.setText(selected.getTemporarySpeedLimit());
        crashDir.setText(selected.getCrashDirectionDescription());
        flatHill.setText(selected.getFlatHill());
        roadSrfs.setText(selected.getRoadSurface());
        deaths.setText(selected.getFatalCount());
        bicycles.setText(selected.getBicycle());
        mbike.setText(selected.getMotorcycle());
        suv.setText(selected.getSuv());
        vanUts.setText(selected.getVanOrUtility());
        bridge.setText(selected.getBridge());
        fence.setText(selected.getFence());
        severity.setText(selected.getCrashSeverity());
        lat.setText(String.valueOf(selected.getLat()));
        wthrA.setText(selected.getWeatherA());
        wthrB.setText(selected.getWeatherB());
        holiday.setText(selected.getHoliday());
        trafficCtrl.setText(selected.getTrafficControl());
        financeYear.setText(selected.getCrashFinancialYear());
        lanes.setText(selected.getNumberOfLanes());
        urban.setText(selected.getUrban());
        roadChar.setText(selected.getRoadCharacter());
        minInjuries.setText(selected.getMinorInjuryCount());
        majInjuries.setText(selected.getSeriousInjuryCount());
        buses.setText(selected.getBus());
        othrVehc.setText(selected.getOtherVehicleType());
        taxi.setText(selected.getTaxi());
        cliff.setText(selected.getCliffBank());
        rail.setText(selected.getGuardRail());
        year.setText(selected.getCrashYear());
        longitude.setText(String.valueOf(selected.getLng()));
        light.setText(selected.getLight());
        territorialAuth.setText(selected.getTlaName());
        stHighway.setText(selected.getCrashSHDescription());
        carStnWag.setText(selected.getCarStationWagon());
        pedestrians.setText(selected.getPedestrian());
        truck.setText(selected.getTruck());
        debris.setText(selected.getDebris());
        kerb.setText(selected.getKerb());
        region.setText(selected.getRegion());
        streetLight.setText(selected.getStreetLight());
        vhlcRollDir.setText(selected.getDirectionRoleDescription());
        roadLanes.setText(selected.getRoadLane());
        moped.setText(selected.getMoped());
        schoolBus.setText(selected.getSchoolBus());
        uknVehc.setText(selected.getUnknownVehicleType());
        ditch.setText(selected.getDitch());
        objThrow.setText(selected.getObjectThrownOrDropped());
        objOther.setText(selected.getOtherObject());
        embank.setText(selected.getOverBank());
        parkedVehc.setText(selected.getParkedVehicle());
        phoneBox.setText(selected.getPhoneBoxEtc());
        pole.setText(selected.getPostOrPole());
        roadWrks.setText(selected.getRoadworks());
        flood.setText(selected.getSlipOrFlood());
        animal.setText(selected.getStrayAnimal());
        tree.setText(selected.getTree());
        trafficIsland.setText(selected.getTrafficIsland());
        vehcHits.setText(selected.getVehicle());
        water.setText(selected.getWaterRiver());
        sign.setText(selected.getTrafficSign());
        train.setText(selected.getTrain());
    }
}
