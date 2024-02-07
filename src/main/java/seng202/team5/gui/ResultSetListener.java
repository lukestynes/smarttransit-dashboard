package seng202.team5.gui;

import seng202.team5.database.CrashFromDatabase;
import seng202.team5.models.Search;

import java.util.ArrayList;

/**
 * an interface to enforce callback function. This function should update the implementing class's result table when an
 * asynchronous query returns. Note that if this update is not done in a platform.runlater() function updates may fail.
 * This is as this function will be called by a worker thread and, implementing classes (likely gui classes)
 * may have problems being updated from worker (non java fx) threads.
 */
public interface ResultSetListener {

    /**
     * This callback function will be called by an asynchronous count query and should update the calling classes
     * record of how many tuples were returned when the inserted search query is searched for. To solve order of
     * search issues, this update should only occur if the inputted search object matches the users most recent search.
     * @param newValues
     * @param searchObject
     */
    public void RowsUpdate(ArrayList<CrashFromDatabase> newValues, Search searchObject);

}
