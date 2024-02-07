package seng202.team5.gui;

/**
 * Interface that establishes a callback function which an asynchronous count query can return to.
 * @author Dominic Gorny
 */

import seng202.team5.models.Search;

/**
 * an interface to enforce callback function. This function should update the implementing class's count for the number of
 * results returned by a query. Note that if this update is not done in a platform.runlater() function updates may fail.
 * This is as this function will be called by a worker thread and, implementing classes (likely gui classes)
 * may have problems being updated from worker (non java fx) threads.
 */
public interface ResultNumberListener {
    /**
     * this function is the callback function and should process the returned query at the receiver end
     * @param newNum the count that the async query found
     * @param searchObject the search object used to make the search (a callback should only update the view if this
     *                     object reflects the most recent user search.)
     */
    public void updateNumRow(Integer newNum, Search searchObject);
}
