package seng202.team5.models;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Conditionals have 4 fields: Column, Operator, Value, and an optional Value2 (which is only used
 * for the Between operator). With Between as the Operator, Value is the lower-bound, while Value2
 * is the upper-bound. Conditionals are used for creating special search queries beyond the existing
 * preset options. An example conditional would be "ID at least 360", or for Between, "ID between
 * 360 and 500". Conditionals are stored in and retrieved from the active Search object.
 */
public class Conditional {
  public TableColumns.Column column;
  public Operator operator;
  public String value;
  public String value2;

  public Conditional(TableColumns.Column column, Operator operator, String value, String value2) {
    this.column = column;
    this.operator = operator;
    this.value = value;
    this.value2 = value2;
  }

  /**
   * Sets the column for the conditional.
   *
   * @param column The table column to set.
   */
  public void setColumn(TableColumns.Column column) {
    this.column = column;
  }

  /**
   * Sets the operator for the conditional.
   *
   * @param operator The operator to set.
   */
  public void setOperator(Operator operator) {
    this.operator = operator;
  }

  /**
   * Gets the operator of the conditional.
   *
   * @return The operator of the conditional.
   */
  public Operator getOperator() {
    return operator;
  }

  /**
   * Gets the column associated with the conditional.
   *
   * @return The column associated with the conditional.
   */
  public TableColumns.Column getColumn() {
    return column;
  }

  /**
   * Sets the primary value of the conditional.
   *
   * @param newValue The new primary value to set.
   */
  public void setValue(String newValue) {
    this.value = newValue;
  }

  /**
   * Gets the primary value of the conditional.
   *
   * @return The primary value of the conditional.
   */
  public String getValue() {
    return value;
  }

  /**
   * Sets the secondary value (Value2) of the Between operator (optional).
   *
   * @param newValue2 The new secondary value (Value2) to set.
   */
  public void setValue2(String newValue2) {
    this.value2 = newValue2;
  }

  /**
   * Gets the secondary value (Value2) of the Between operator (optional).
   *
   * @return The secondary value (Value2) of the Between operator, or null if not applicable.
   */
  public String getValue2() {
    return value2;
  }

  /**
   * enum of the possible operations on the data. displayString -- The string to display in the UI
   * drop-down operator box sqlString -- The string used in the SQL query corresponding to the
   * operator valuesTaken -- The number of values the argument takes; can be 1 or 2 canBeString --
   * Whether the operator makes logical sense with string arguments
   */
  public enum Operator {
    // {DisplayString, SQLString, valuesTaken, canBeString?}
    EQUAL("is", "=", 1, true),
    NOTEQUAL("is not", "!=", 1, true),
    GREATERTHAN("greater than", ">", 1, false),
    GREATERTHANOREQUAL("at least", ">=", 1, false),
    LESSTHAN("less than", "<", 1, false),
    LESSTHANOREQUAL("at most", "<=", 1, false),
    CONTAINS("contains", "CONTAINS", 1, true),
    NOTLIKE("does not contain", "NOT LIKE", 1, true),
    BETWEEN("between", "BETWEEN", 2, false),
    LIKE("matches pattern", "GLOB", 1, true),
    ;
    public final String displayString;
    public final String sqlString;
    public final Integer valuesTaken;
    public final boolean canBeString;

    Operator(String displayString, String sqlString, Integer valuesTaken, boolean canBeString) {
      this.displayString = displayString;
      this.sqlString = sqlString;
      this.valuesTaken = valuesTaken;
      this.canBeString = canBeString;
    }

    /**
     * Gets the display string for the operator.
     *
     * @return The display string for the operator.
     */
    public String getDisplayString() {
      return displayString;
    }

    /**
     * Gets the Operator enum from its display string.
     *
     * @param displayString The display string to match.
     * @return The Operator enum corresponding to the display string, or null if not found.
     */
    public static Operator getOperatorFromDisplayString(String displayString) {
      for (Operator operator : values()) {
        if (Objects.equals(operator.displayString, displayString)) {
          return operator;
        }
      }
      return null;
    }

    /**
     * Checks if the operator can be applied to string arguments.
     *
     * @return true if the operator can be applied to string arguments, false otherwise.
     */
    public boolean getCanBeString() {
      return canBeString;
    }
  }
}
