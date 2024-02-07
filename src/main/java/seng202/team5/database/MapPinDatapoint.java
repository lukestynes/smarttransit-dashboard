package seng202.team5.database;

/**
 * Stores the crash information for a single crash. Limited to only information necessary for displaying this crash
 * on the apps map view.
 * @author Dominic Gorny
 */
public class MapPinDatapoint {
    private int objectID;
    private double lat;
    private double lng;

    private int crashYear;
    private String crashSeverity;

    /**
     * creates a new MapPinDatapoint from inputed values, This object has all the information needed for displaying on
     * the map as a crash.
     * @param id the crashes id
     * @param lat latitude of the crash
     * @param lng longitude of the crash
     * @param crashSeverity severity of the crash
     * @param crashYear year the crash occurred in.
     */
        public MapPinDatapoint(int id, double lat, double lng, String crashSeverity, int crashYear)
        {
            this.objectID = id;
            this.lat = lat;
            this.lng = lng;
            this.crashYear = crashYear;
            this.crashSeverity = crashSeverity;
        }
    public int getObjectID() {
        return objectID;
    }

    /**
     * change the objects crash id
     * @param objectID new id
     */
    public void setObjectID(int objectID) {
        this.objectID = objectID;
    }

    /**
     * get the crashes latitude
     * @return latitude
     */
    public double getLat() {
        return lat;
    }

    /**
     * set the crash's latitude
     * @param lat new latitude
     */
    public void setLat(double lat) {
        this.lat = lat;
    }

    /**
     * get the crashes longitude
     * @return longitude
     */
    public double getLng() {
        return lng;
    }

    /**
     * set a new longitude for the crash
     * @param lng new longitude
     */
    public void setLng(double lng) {
        this.lng = lng;
    }

    /**
     * get the year of the stored crash
     * @return crash year
     */
    public int getCrashYear() {
        return crashYear;
    }

    /**
     * set the year of the stored crash
     * @param crashYear crash year
     */
    public void setCrashYear(int crashYear) {
        this.crashYear = crashYear;
    }

    /**
     * get the severity of the crash
     * @return crash severity
     */
    public String getCrashSeverity() {
        return crashSeverity;
    }

    /**
     * set the crashes Severity
     * @param crashSeverity new Severity
     */
    public void setCrashSeverity(String crashSeverity) {
        this.crashSeverity = crashSeverity;
    }
}

