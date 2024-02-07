package seng202.team5.cucumber.StepDefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.util.Sets;
import org.junit.After;
import org.junit.jupiter.api.Assertions;
import seng202.team5.business.CrashManager;
import seng202.team5.database.*;
import seng202.team5.gui.LoadingBarController;
import seng202.team5.models.Search;
import seng202.team5.models.TableColumns;

import java.io.File;
import java.util.*;

public class SearchDefs {
    private Search search;
    private CrashManager crashManager;
    private static final CSVManager csvManager = new CSVManager();
    private static final CrashDAO crashDAO = new CrashDAO();
    private final GeneralDefs generalDefs = new GeneralDefs();


    @Before
    public void addTestTable() {
        LoadingBarController.testMode();
        System.out.println("clearing DB");
        crashDAO.dropTable("testTable");
        crashDAO.createTable("testTable");
        System.out.println("Loading File");
        File testFile = new File("src/test/java/seng202/team5/unittests/query_test_data.csv");
        ArrayList<CrashFromFile> testRecords = csvManager.readFile(testFile);
        crashDAO.addBatch(
                testRecords,
                "testTable",
                "testTable",
                integer -> {},
                s -> {});
        crashManager = new CrashManager(crashDAO);
    }
    @After
    public void removeTestTable() {
        crashManager.dropTable("testTable");
    }

    @Given("I have a fresh basic search")
    public void freshSearch() {
        search = new Search();
        search.setTable("testTable");
    }
    @When("I enter {string} as a keyword phrase")
    public void enterKeywords(String keywords) {
        search.setKeywords(keywords);
    }
    @When("I enable the range {string} with values {int} and {int}")
    public void enableRange(String field, int lowerBound, int upperBound) {
        if (Objects.equals(field, "Year")) {
            search.setStartYear(lowerBound);
            search.setEndYear(upperBound);
        } else if (Objects.equals(field, "Speed Limit")) {
            search.setStartLimit(lowerBound);
            search.setEndLimit(upperBound);
        } else if (Objects.equals(field, "Advised Speed")) {
            search.setAdvisedStartLimit(lowerBound);
            search.setAdvisedEndLimit(upperBound);
        } else if (Objects.equals(field, "Temporary Speed Limit")) {
            search.setTemporaryStartLimit(lowerBound);
            search.setTemporaryEndLimit(upperBound);
        } else if (Objects.equals(field, "Lanes")) {
            search.setStartLanes(lowerBound);
            search.setEndLanes(upperBound);
        }
    }
    @When("I select {string} from the {string} box")
    public void selectComboBox(String selectionString, String field) {
        HashSet<String> selections = getHashSetFromSelectionString(selectionString);
        if (Objects.equals(field, "Severity")) {
            search.setSeverities(selections);
        } else if (Objects.equals(field, "Region")) {
            search.setRegions(selections);
        } else if (Objects.equals(field, "Precipitation")) {
            search.setWeatherA(selections);
        } else if (Objects.equals(field, "Frost or Wind")) {
            search.setWeatherB(selections);
        } else if (Objects.equals(field, "Light")) {
            search.setLight(selections);
        } else if (Objects.equals(field, "Holiday")) {
            search.setHolidays(selections);
        } else if (Objects.equals(field, "Territorial Local Authority")) {
            search.setLocalAuthorities(selections);
        } else if (Objects.equals(field, "Traffic Control")) {
            search.setTrafficControls(selections);
        } else if (Objects.equals(field, "Crash Direction")) {
            search.setCrashDirections(selections);
        } else if (Objects.equals(field, "Vehicle Roll Direction")) {
            search.setVehicleDirections(selections);
        } else if (Objects.equals(field, "Road Lanes")) {
            search.setLaneConfigs(selections);
        } else if (Objects.equals(field, "Road Character")) {
            search.setRoadCharacters(selections);
        } else if (Objects.equals(field, "Road Surface")) {
            search.setRoadSurfaces(selections);
        }
    }
    @When("I select {string} from the {string} box with Any enabled")
    public void selectAnyComboBox(String selectionString, String field) {
        HashSet<String> selections = getHashSetFromSelectionString(selectionString);
        if (Objects.equals(field, "Participants")) {
            search.setParticipantAnd(false);
            search.setParticipants(selections);
        } else if (Objects.equals(field, "Collisions")) {
            search.setCollisionAnd(false);
            search.setCollisions(selections);
        }
    }
    @When("I select {string} from the {string} box with All enabled")
    public void selectAllComboBox(String selectionString, String field) {
        HashSet<String> selections = getHashSetFromSelectionString(selectionString);
        if (Objects.equals(field, "Participants")) {
            search.setParticipantAnd(true);
            search.setParticipants(selections);
        } else if (Objects.equals(field, "Collisions")) {
            search.setCollisionAnd(true);
            search.setCollisions(selections);
        }
    }
    @And("the search is matching any terms")
    public void setMatchAny() {
        search.setMatchAll(false);
    }
    @And("the search is matching all terms")
    public void setMatchAll() {
        search.setMatchAll(true);
    }
    @Then("if there is a result, its string fields must contain any of the words in {string}")
    public void checkAny(String keywords) {
        List<String> words = Arrays.asList(keywords.split(" "));
        SQLQuerryObject queryFromSearch = new SQLQuerryObject(search);
        queryFromSearch.buildQuery();
        String sql = queryFromSearch.getSql();
        List<CrashFromDatabase> results = crashManager.runQuery(sql);
        boolean testStatus = true;
        for (CrashFromDatabase result : results) {
            String resultString = getResultString(result);
            boolean found = false;
            for (String word : words) {
                if (resultString.toLowerCase().contains(word.toLowerCase())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                testStatus = false;
                break;
            }
        }
        Assertions.assertTrue(testStatus);
    }
    @Then("if there is a result, its string fields must contain all of the words in {string}")
    public void checkAll(String keywords) {
        List<String> words = Arrays.asList(keywords.split(" "));
        SQLQuerryObject queryFromSearch = new SQLQuerryObject(search);
        queryFromSearch.buildQuery();
        String sql = queryFromSearch.getSql();
        List<CrashFromDatabase> results = crashManager.runQuery(sql);
        boolean testStatus = true;
        for (CrashFromDatabase result : results) {
            String resultString = getResultString(result);
            for (String word : words) {
                if (!resultString.toLowerCase().contains(word.toLowerCase())) {
                    testStatus = false;
                    break;
                }
            }
        }
        Assertions.assertTrue(testStatus);
    }
    @Then("if there is a result, its field {string} is between {int} and {int}")
    public void checkDiscreteRange(String field, int lowerBound, int upperBound) {
        SQLQuerryObject queryFromSearch = new SQLQuerryObject(search);
        queryFromSearch.buildQuery();
        String sql = queryFromSearch.getSql();
        List<CrashFromDatabase> results = crashManager.runQuery(sql);
        boolean testStatus = true;
        for (CrashFromDatabase result : results) {
            Integer resultValue = Integer.parseInt(generalDefs.getResultValue(result, field));
            if (!(resultValue >= lowerBound && resultValue <= upperBound)) {
                testStatus = false;
                break;
            }
        }
        Assertions.assertTrue(testStatus);
    }
    @Then("if there is a result, its field {string} must match one of the {string} values")
    public void checkCombo(String field, String selectionString) {
        SQLQuerryObject queryFromSearch = new SQLQuerryObject(search);
        queryFromSearch.buildQuery();
        String sql = queryFromSearch.getSql();
        List<CrashFromDatabase> results = crashManager.runQuery(sql);
        HashSet<String> selections = getHashSetFromSelectionString(selectionString);
        boolean testStatus = true;
        for (CrashFromDatabase result : results) {
            String value = generalDefs.getResultValue(result, field);
            if (!selections.contains(value)) {
                testStatus = false;
                break;
            }
        }
        Assertions.assertTrue(testStatus);
    }
    @Then("if there is a result, its field {string} must have at least one {string} present")
    public void checkAnyCombo(String field, String selectionString) {
        SQLQuerryObject queryFromSearch = new SQLQuerryObject(search);
        queryFromSearch.buildQuery();
        String sql = queryFromSearch.getSql();
        List<CrashFromDatabase> results = crashManager.runQuery(sql);
        HashSet<String> selections = getHashSetFromSelectionString(selectionString);
        boolean testStatus = true;
        for (CrashFromDatabase result : results) {
            boolean resultStatus = checkMembership(false, field, result, selections);
            if (!resultStatus) {
                testStatus = false;
                break;
            }
        }
        Assertions.assertTrue(testStatus);
    }
    @Then("if there is a result, its field {string} must have all {string} present")
    public void checkAllCombo(String field, String selectionString) {
        SQLQuerryObject queryFromSearch = new SQLQuerryObject(search);
        queryFromSearch.buildQuery();
        String sql = queryFromSearch.getSql();
        List<CrashFromDatabase> results = crashManager.runQuery(sql);
        HashSet<String> selections = getHashSetFromSelectionString(selectionString);
        boolean testStatus = true;
        for (CrashFromDatabase result : results) {
            boolean resultStatus = checkMembership(true, field, result, selections);
            if (!resultStatus) {
                testStatus = false;
                break;
            }
        }
        Assertions.assertTrue(testStatus);
    }

    public HashSet<String> getHashSetFromSelectionString(String selectionString) {
        HashSet<String> selections = new HashSet<>();
        List<String> selectionList = Arrays.asList(selectionString.split(","));
        for (String selection : selectionList) {
            selections.add(TableColumns.Column.getDBLabelFromLabel(selection));
        }
        return selections;
    }

    public boolean checkMembership(boolean isAll, String field, CrashFromDatabase result, HashSet<String> selections) {
        int matchCount = 0;
        if (Objects.equals(field, "Participants")) {
            if (Integer.parseInt(result.getCarStationWagon()) > 0 && selections.contains(TableColumns.Column.STATIONWAGON.getDbLabel())) {
                matchCount++;
            } if (Integer.parseInt(result.getSuv()) > 0 && selections.contains(TableColumns.Column.SUV.getDbLabel())) {
                matchCount++;
            } if (Integer.parseInt(result.getVanOrUtility()) > 0 && selections.contains(TableColumns.Column.VANUTILITY.getDbLabel())) {
                matchCount++;
            } if (Integer.parseInt(result.getBicycle()) > 0 && selections.contains(TableColumns.Column.BICYCLE.getDbLabel())) {
                matchCount++;
            } if (Integer.parseInt(result.getMotorcycle()) > 0 && selections.contains(TableColumns.Column.MOTORCYCLE.getDbLabel())) {
                matchCount++;
            } if (Integer.parseInt(result.getMoped()) > 0 && selections.contains(TableColumns.Column.MOPED.getDbLabel())) {
                matchCount++;
            } if (Integer.parseInt(result.getPedestrian()) > 0 && selections.contains(TableColumns.Column.PEDESTRIAN.getDbLabel())) {
                matchCount++;
            } if (Integer.parseInt(result.getBus()) > 0 && selections.contains(TableColumns.Column.BUS.getDbLabel())) {
                matchCount++;
            } if (Integer.parseInt(result.getSchoolBus()) > 0 && selections.contains(TableColumns.Column.SCHOOLBUS.getDbLabel())) {
                matchCount++;
            } if (Integer.parseInt(result.getTaxi()) > 0 && selections.contains(TableColumns.Column.TAXI.getDbLabel())) {
                matchCount++;
            } if (Integer.parseInt(result.getTruck()) > 0 && selections.contains(TableColumns.Column.TRUCK.getDbLabel())) {
                matchCount++;
            } if (Integer.parseInt(result.getOtherVehicleType()) > 0 && selections.contains(TableColumns.Column.OTHERVEHICLE.getDbLabel())) {
                matchCount++;
            } if (Integer.parseInt(result.getUnknownVehicleType()) > 0 && selections.contains(TableColumns.Column.UNKNOWNVEHICLE.getDbLabel())) {
                matchCount++;
            }
        } else if (Objects.equals(field, "Collisions")) {
            if (Integer.parseInt(result.getBridge()) > 0 && selections.contains(TableColumns.Column.BRIDGE.getDbLabel())) {
                matchCount++;
            } if (Integer.parseInt(result.getCliffBank()) > 0 && selections.contains(TableColumns.Column.CLIFFBANK.getDbLabel())) {
                matchCount++;
            } if (Integer.parseInt(result.getDebris()) > 0 && selections.contains(TableColumns.Column.DEBRIS.getDbLabel())) {
                matchCount++;
            } if (Integer.parseInt(result.getDitch()) > 0 && selections.contains(TableColumns.Column.DITCH.getDbLabel())) {
                matchCount++;
            } if (Integer.parseInt(result.getFence()) > 0 && selections.contains(TableColumns.Column.FENCE.getDbLabel())) {
                matchCount++;
            } if (Integer.parseInt(result.getGuardRail()) > 0 && selections.contains(TableColumns.Column.GUARDRAIL.getDbLabel())) {
                matchCount++;
            } if (Integer.parseInt(result.getHouseOrBuilding()) > 0 && selections.contains(TableColumns.Column.HOUSEBLDG.getDbLabel())) {
                matchCount++;
            } if (Integer.parseInt(result.getKerb()) > 0 && selections.contains(TableColumns.Column.KERB.getDbLabel())) {
                matchCount++;
            } if (Integer.parseInt(result.getObjectThrownOrDropped()) > 0 && selections.contains(TableColumns.Column.OBJECT.getDbLabel())) {
                matchCount++;
            } if (Integer.parseInt(result.getOtherObject()) > 0 && selections.contains(TableColumns.Column.OTHEROBJECT.getDbLabel())) {
                matchCount++;
            } if (Integer.parseInt(result.getOverBank()) > 0 && selections.contains(TableColumns.Column.OVERBANK.getDbLabel())) {
                matchCount++;
            } if (Integer.parseInt(result.getParkedVehicle()) > 0 && selections.contains(TableColumns.Column.PARKEDVEHICLE.getDbLabel())) {
                matchCount++;
            } if (Integer.parseInt(result.getPhoneBoxEtc()) > 0 && selections.contains(TableColumns.Column.PHONEBOX.getDbLabel())) {
                matchCount++;
            } if (Integer.parseInt(result.getPostOrPole()) > 0 && selections.contains(TableColumns.Column.POSTPOLE.getDbLabel())) {
                matchCount++;
            } if (Integer.parseInt(result.getRoadworks()) > 0 && selections.contains(TableColumns.Column.ROADWORKS.getDbLabel())) {
                matchCount++;
            } if (Integer.parseInt(result.getSlipOrFlood()) > 0 && selections.contains(TableColumns.Column.SLIPFLOOD.getDbLabel())) {
                matchCount++;
            } if (Integer.parseInt(result.getStrayAnimal()) > 0 && selections.contains(TableColumns.Column.STRAYANIMAL.getDbLabel())) {
                matchCount++;
            } if (Integer.parseInt(result.getTrafficIsland()) > 0 && selections.contains(TableColumns.Column.ISLAND.getDbLabel())) {
                matchCount++;
            } if (Integer.parseInt(result.getTrafficSign()) > 0 && selections.contains(TableColumns.Column.SIGN.getDbLabel())) {
                matchCount++;
            } if (Integer.parseInt(result.getTrain()) > 0 && selections.contains(TableColumns.Column.TRAIN.getDbLabel())) {
                matchCount++;
            } if (Integer.parseInt(result.getTree()) > 0 && selections.contains(TableColumns.Column.TREE.getDbLabel())) {
                matchCount++;
            } if (Integer.parseInt(result.getVehicle()) > 0 && selections.contains(TableColumns.Column.VEHICLE.getDbLabel())) {
                matchCount++;
            } if (Integer.parseInt(result.getWaterRiver()) > 0 && selections.contains(TableColumns.Column.WATERRIVER.getDbLabel())) {
                matchCount++;
            }
        }
        if (isAll) {
            return matchCount == selections.size();
        } else {
            return matchCount > 0;
        }
    }

    public String getResultString(CrashFromDatabase result) {
        List<String> resultStrings = new ArrayList<>();
        resultStrings.add(result.getObjectID().toString());
        resultStrings.add(result.getCrashDirectionDescription());
        resultStrings.add(result.getCrashFinancialYear());
        resultStrings.add(result.getCrashLocation1());
        resultStrings.add(result.getCrashLocation2());
        resultStrings.add(result.getCrashRoadSideRoad());
        resultStrings.add(result.getCrashSeverity());
        resultStrings.add(result.getCrashSHDescription());
        resultStrings.add(result.getDirectionRoleDescription());
        resultStrings.add(result.getFlatHill());
        resultStrings.add(result.getHoliday());
        resultStrings.add(result.getIntersection());
        resultStrings.add(result.getLight());
        resultStrings.add(result.getRegion());
        resultStrings.add(result.getRoadCharacter());
        resultStrings.add(result.getRoadLane());
        resultStrings.add(result.getRoadSurface());
        resultStrings.add(result.getStreetLight());
        resultStrings.add(result.getTlaName());
        resultStrings.add(result.getTrafficControl());
        resultStrings.add(result.getUrban());
        resultStrings.add(result.getWeatherA());
        resultStrings.add(result.getWeatherB());
        return String.join(" ", resultStrings);
    }

}
