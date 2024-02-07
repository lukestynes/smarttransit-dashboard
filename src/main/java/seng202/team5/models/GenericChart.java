package seng202.team5.models;



/**
 * As charts in JavaFX are defined as specific types, this is a class
 * to store generic data about the current chart object.
 * It may also be populated with enums outlining valid values for given
 * fields.
 * @author Zoe Perry
 */
public class GenericChart {
    //Consider storing all generic properties here, so switching between
    //chart types, they can be preserved?

    
    /**
     * Enum of the chart types that can be displayed.
     */
    public enum ChartType {
        AREA ("Area Plot"),
        BAR ("Bar Graph"),
        LINE ("Line Plot"),
        PIE ("Pie Chart"),
//        STACKAREA ("Stacked Area Plot"),
//        BUBBLE ("Bubble Plot"),
//        SCATTER ("Scatter Plot"),
//        STACKBAR ("Stacked Bar Graph"),
        ;
        public String name;
        ChartType (String name) {
            this.name = name;
        }
    }

    /**
     * Enum of the categories that the pie chart can group by.
     */
    public enum Categories {
        SPEEDLIMIT("Speed Limit", "speedLimit"),
        YEAR("Year", "crashYear"),
        SEVERITY("Severity", "crashSeverity"),
        REGION("Region", "region"),
        //PARTICIPANTS("Participants", "collated???"),
        PRECIPITATION("Precipitation", "weatherA"),
        FROSTORWIND("Frost or Wind", "weatherB"),
        LIGHT("Light", "light"),
        HOLIDAY("Holiday", "holiday"),
        TRAFFIC_CONTROL("Traffic Control", "trafficControl"),
        //COLLISIONS("Collisions", "collated???"),
        DIRECTION("Direction", "crashDirection"),
        //LANE_CONFIGURATION("Lane Configuration", "collated???"),
        //ROAD_CHARACTER("Road Character", "roadChracter"),
        ROAD_SURFACE("Road Surface", "roadSurface"),
        ;
        public String name;
        public String dbName;

        Categories (String name, String dbName) {
            this.name = name;
            this.dbName = dbName;
        }
    }

    /**
     * Enum of categories that are displayed as numbers and can be considered continuous and used as a number axis.
     */
    public enum Continuous {
        SPEEDLIMIT("Speed Limit", "speedLimit"),
        ADVSPEED("Advised Speed", "advisorySpeed"),
        YEAR("Year", "crashYear")
        ;
        public String name;
        public String dbName;

        Continuous (String name, String dbName) {
            this.name = name;
            this.dbName = dbName;
        }
    }

    /**
     * Enum of categories that are displayed as numbers and can be a variable to group crashes by.
     */
    public enum Counts {
        CRASHCOUNT("Crashes", "distinct ID"),
        FATALCOUNT("Deaths", "fatalCount"),
        MINORINJURY("Minor Injuries", "minorInjury"),
        BICYCLE("Bicycles", "bicycle"),
        BUS("Buses", "bus"),
        STATIONWAGON("Cars/Station Wagons", "StationWagon"),
        MOPED("Mopeds", "moped"),
        MOTORCYCLES("Motorcycles", "motorcycle"),
        PEDESTRIAN("Pedestrians", "pedestrian"),
        SCHOOLBUS("School Buses", "schoolBuses"),
        SERIOUSINJURY("Serious Injuries", "seriousInjuries"),
        SUV("SUVs", "suv"),
        TAXI("Taxis", "taxi"),
        TRUCK("Trucks", "truck"),
        VEHICLE("Stationary Vehicles", "vehicle");

        public String name;
        public String dbName;

        Counts (String name, String dbName) {
            this.name = name;
            this.dbName = dbName;
        }
    }

}
