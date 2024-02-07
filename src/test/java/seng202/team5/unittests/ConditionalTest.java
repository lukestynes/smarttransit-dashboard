package seng202.team5.unittests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng202.team5.models.Conditional;
import seng202.team5.models.TableColumns;

import static org.junit.jupiter.api.Assertions.*;

public class ConditionalTest {

    private Conditional conditional;

    @BeforeEach
    void testSetUp() {
        conditional = new Conditional(TableColumns.Column.ID, Conditional.Operator.EQUAL, null,null);
        assertInstanceOf(Conditional.class, conditional);
    }
    @Test
    void testSetAndGetColumn() {
        TableColumns.Column column  = TableColumns.Column.YEAR;
        conditional.setColumn(column);
        assertEquals(column, conditional.getColumn());
    }

    @Test
    void testSetAndGetOperator() {
        Conditional.Operator operator  = Conditional.Operator.NOTEQUAL;
        conditional.setOperator(operator);
        assertEquals(operator, conditional.getOperator());
    }

    @Test
    void testSetAndGetValue() {
        String value  = "0";
        conditional.setValue(value);
        assertEquals(value, conditional.getValue());
    }

    @Test
    void testSetAndGetValue2() {
        String value2  = "200";
        conditional.setValue2(value2);
        assertEquals(value2, conditional.getValue2());
    }

    @Test
    void testGetDisplayString() {
        assertEquals("is", conditional.operator.getDisplayString());
    }

    @Test
    void testGetCanBeString() {
        assertTrue(conditional.operator.getCanBeString());
    }

    @Test
    void testGetOperatorFromDisplayString() {
        assertEquals(Conditional.Operator.EQUAL, Conditional.Operator.getOperatorFromDisplayString("is"));
        assertNull(Conditional.Operator.getOperatorFromDisplayString("Nonexistent"));
    }

}
