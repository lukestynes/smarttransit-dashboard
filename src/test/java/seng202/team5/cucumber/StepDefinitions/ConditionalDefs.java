package seng202.team5.cucumber.StepDefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.After;
import org.junit.jupiter.api.Assertions;
import seng202.team5.business.CrashManager;
import seng202.team5.database.*;
import seng202.team5.gui.LoadingBarController;
import seng202.team5.models.Conditional;
import seng202.team5.models.Search;
import seng202.team5.models.TableColumns;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.fail;

public class ConditionalDefs {
    private Search search;
    private Integer conditionalIndex = 0;
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

    @Given("I have a fresh conditional search")
    public void freshSearch() {
        search = new Search();
        search.setTable("testTable");
        conditionalIndex = 0;
    }

    @When("I create a conditional with field {string}, operator {string}, and value {string}")
    public void newConditional(String field, String operator, String value) {
        Conditional conditional = new Conditional(TableColumns.Column.getColumnFromLabel(field), Conditional.Operator.getOperatorFromDisplayString(operator), value, null);
        search.addConditional(conditionalIndex, conditional);
        conditionalIndex++;
    }

    @When("I create a conditional with field {string}, operator {string}, and values {string} and {string}")
    public void newConditional2(String field, String operator, String value1, String value2) {
        Conditional conditional = new Conditional(TableColumns.Column.getColumnFromLabel(field), Conditional.Operator.getOperatorFromDisplayString(operator), value1, value2);
        search.addConditional(conditionalIndex, conditional);
        conditionalIndex++;
    }

    @Then("if there is a result, its field {string} should be the value {string}")
    public void checkEQUAL(String field, String value) {
        checkConditional(Conditional.Operator.EQUAL, field, value, null);
    }

    @Then("if there is a result, its field {string} should not be the value {string}")
    public void checkNOTEQUAL(String field, String value) {
        checkConditional(Conditional.Operator.NOTEQUAL, field, value, null);
    }

    @Then("if there is a result, its field {string} should be greater than {string}")
    public void checkGREATERTHAN(String field, String value) {
        checkConditional(Conditional.Operator.GREATERTHAN, field, value, null);
    }

    @Then("if there is a result, its field {string} should be at least {string}")
    public void checkGREATERTHANOREQUAL(String field, String value) {
        checkConditional(Conditional.Operator.GREATERTHANOREQUAL, field, value, null);
    }

    @Then("if there is a result, its field {string} should be less than {string}")
    public void checkLESSTHAN(String field, String value) {
        checkConditional(Conditional.Operator.LESSTHAN, field, value, null);
    }

    @Then("if there is a result, its field {string} should be at most {string}")
    public void checkLESSTHANOREQUAL(String field, String value) {
        checkConditional(Conditional.Operator.LESSTHANOREQUAL, field, value, null);
    }

    @Then("if there is a result, its field {string} should contain {string}")
    public void checkCONTAINS(String field, String value) {
        checkConditional(Conditional.Operator.CONTAINS, field, value, null);
    }

    @Then("if there is a result, its field {string} should not contain {string}")
    public void checkNOTLIKE(String field, String value) {
        checkConditional(Conditional.Operator.NOTLIKE, field, value, null);
    }

    @Then("if there is a result, its field {string} should be between {string} and {string}")
    public void checkBETWEEN(String field, String value, String value2) {
        checkConditional(Conditional.Operator.BETWEEN, field, value, value2);
    }

    @Then("if there is a result, its field {string} should match the pattern {string}")
    public void checkLIKE(String field, String value) {
        checkConditional(Conditional.Operator.LIKE, field, value, null);
    }

    public void checkConditional(Conditional.Operator operator, String field, String value, String value2) {
        SQLQuerryObject queryFromSearch = new SQLQuerryObject(search);
        queryFromSearch.buildQuery();
        String sql = queryFromSearch.getSql();
        List<CrashFromDatabase> results = crashManager.runQuery(sql);
        for (CrashFromDatabase result : results) {
            String resultValue = generalDefs.getResultValue(result, field);
            if (resultValue != null) {
                checkOperator(operator, resultValue, value, value2);
            }
        }
    }



    public void checkOperator(Conditional.Operator operator, String result, String value, String value2) {
        if (operator == Conditional.Operator.EQUAL) {
            Assertions.assertEquals(result, value);
        } else if (operator == Conditional.Operator.NOTEQUAL) {
            Assertions.assertNotEquals(result, value);
        } else if (operator == Conditional.Operator.GREATERTHAN) {
            Assertions.assertTrue(Integer.parseInt(result) > Integer.parseInt(value));
        } else if (operator == Conditional.Operator.GREATERTHANOREQUAL) {
            Assertions.assertTrue(Integer.parseInt(result) >= Integer.parseInt(value));
        } else if (operator == Conditional.Operator.LESSTHAN) {
            Assertions.assertTrue(Integer.parseInt(result) < Integer.parseInt(value));
        } else if (operator == Conditional.Operator.LESSTHANOREQUAL) {
            Assertions.assertTrue(Integer.parseInt(result) <= Integer.parseInt(value));
        } else if (operator == Conditional.Operator.CONTAINS) {
            Assertions.assertTrue(result.contains(value));
        } else if (operator == Conditional.Operator.NOTLIKE) {
            Assertions.assertFalse(result.contains(value));
        } else if (operator == Conditional.Operator.BETWEEN) {
            boolean upperBound = Integer.parseInt(result) >= Integer.parseInt(value);
            boolean lowerBound = Integer.parseInt(result) <= Integer.parseInt(value2);
            Assertions.assertTrue(upperBound && lowerBound);
        } else if (operator == Conditional.Operator.LIKE) {
            String pattern = value.replace("*", ".*");
            Assertions.assertTrue(Pattern.matches(pattern, result));
        }
    }
}