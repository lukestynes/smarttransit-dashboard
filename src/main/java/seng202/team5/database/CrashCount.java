package seng202.team5.database;

/**
 * An object that is used to pass into charts as it keeps track of a category name and the count of crashes in that category.
 * @author Zoe Perry
 */
public class CrashCount {
    public int count;
    public String category;

    public CrashCount(int count, String category) {
        this.count = count;
        this.category = category;
    }
}
