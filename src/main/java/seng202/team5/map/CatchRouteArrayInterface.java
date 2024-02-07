package seng202.team5.map;


/**
 * A basic interface to allow for passing the catch route lambda function between classes
 * This interface accepts any lambda that takes in a string representation of a json object and returns void
 * @author Sergey Antonets
 */
public interface CatchRouteArrayInterface {

    /**
     * Operation
     * @param coords The JSON formatted route coordinates.
     */
    void operation(String coords);


}
