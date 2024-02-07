package seng202.team5.map;

/**
 * A basic interface to allow for passing the open modal lambda function between classes
 * This interface accepts any lambda that takes in a crashes ID and returns void
 * @author Luke Stynes
 */
public interface OpenModalInterface {
    /**
     * Operation
     * @param id the id of the crash being passed in
     * @param tableName the name of the relevant crashTable
     */
    void operation(int id, String tableName);
}
