package seng202.team5.models;

/**
 * Class for storing map locations as pins
 * Is also accessed when browsing to a location via location search, though the pin created is temporary and not stored.
 */
public class MapPin {
    //We will want to add more stored variables here later
    //i.e., any relevant information to display as a tooltip or speech-bubble pop-up should be stored with the pin.
    //If we want to colour pins by parameters (i.e., severity) then these need to be stored as well.
    //We should also store the crash ID, so extended viewing is possible from a crash pin
    public double lat;
    public double lng;
    public String name;

    /**
     * Initialize the Map Pin object
     * @param lat The latitude of the pin.
     * @param lng The longitude of the pin.
     * @param name The name of the pin.
     */
    public MapPin(double lat, double lng, String name) {
        this.lat = lat;
        this.lng = lng;
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public double getLat() {return lat;}
    public double getLng() {return lng;}
}