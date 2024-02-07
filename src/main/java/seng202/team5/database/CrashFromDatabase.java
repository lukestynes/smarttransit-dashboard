package seng202.team5.database;

/**
 * Object that holds an individual Crash with string types:
 *
 * <p>Used for passing around crash data especially outside the data layer, all variables in this
 * object are stored as String (except for geo location data). This enables the editing of data that
 * is not user friendly to something more understandable (e.g Null -> no data)
 *
 * @author Dominic Gorny
 */
public class CrashFromDatabase {
    /** A records ID * */
    private Integer objectID = null;
    /** The advised Speed * */
    private String advisorySpeed = null;
    /** Number of bicycles in crash* */
    private String bicycle = null;
    /** Number of bridges in crash* */
    private String bridge = null;
    /** Number of busses in crash* */
    private String bus = null;
    /** Number of station wagons in crash* */
    private String carStationWagon = null;
    /** Number of cliffs or ledges in crash* */
    private String cliffBank = null;
    /** Direction the crash occurred in * */
    private String crashDirectionDescription = null;
    /** Financial year of crash * */
    private String crashFinancialYear = null;
    /** location of crash pt1* */
    private String crashLocation1 = null;
    /** location of crash pt2* */
    private String crashLocation2 = null;

    private String crashRoadSideRoad = null;

    /** Severity of crash* */
    private String crashSeverity = null;
    /** crash SH description* */
    private String crashSHDescription = null;
    /** year of the crash* */
    private String crashYear = null;
    /** Number of crash Debris* */
    private String debris = null;
    /** Direction crash members rolled* */
    private String directionRoleDescription = null;
    /** Number of ditches involved* */
    private String ditch = null;
    /** Number of Fatalities* */
    private String fatalCount = null;
    /** Number of Fences* */
    private String fence = null;
    /** Types of hills involved, flat if none* */
    private String flatHill = null;
    /** Number of Guard Rails involved* */
    private String guardRail = null;
    /** Holiday currently active if any* */
    private String holiday = null;
    /** Number of Buildings involved* */
    private String houseOrBuilding = null;
    /** Number of intersections involved* */
    private String intersection = null;
    /** Number of Kerbs involved* */
    private String kerb = null;
    /** Amount of light at time of crash* */
    private String light = null;
    /** Number of minor injuries in crash* */
    private String minorInjuryCount = null;
    /** Number of mopeds in crash* */
    private String moped = null;
    /** Number of motorcycles in crash* */
    private String motorcycle = null;
    /** Number of lanes on road* */
    private String NumberOfLanes = null;
    /** Number of Thrown or dropped objects* */
    private String objectThrownOrDropped = null;
    /** Number of other objects* */
    private String otherObject = null;
    /** Number of other Vehicles* */
    private String otherVehicleType = null;
    /** Number of Banks* */
    private String overBank = null;
    /** Number of Parked Vehicles* */
    private String parkedVehicle = null;
    /** Number of Pedestrians* */
    private String pedestrian = null;
    /** Number og phone boxes etc* */
    private String phoneBoxEtc = null;
    /** Number of post,poles or similar* */
    private String postOrPole = null;
    /** Crash region* */
    private String region = null;
    /** Road character (e.g highway ramp)* */
    private String roadCharacter = null;
    /** Road lane (e.g 1 way or 2 way...)* */
    private String roadLane = null;
    /** Road Surface Type* */
    private String roadSurface = null;
    /** Number of Roadworks* */
    private String roadworks = null;
    /** Number of School buses* */
    private String schoolBus = null;
    /** Number of serious injuries* */
    private String seriousInjuryCount = null;
    /** Number of slips of floods* */
    private String slipOrFlood = null;
    /** Legal Speed limit* */
    private String speedLimit = null;
    /** Number of SUVs in collision* */
    private String strayAnimal = null;

    private String streetLight = null;
    private String suv = null;
    /** Number of Taxis* */
    private String taxi = null;
    /** Temporary speed limit if applicable* */
    private String temporarySpeedLimit = null;
    /** tla region name* */
    private String tlaName = null;
    /** type of traffic control e.g. intersection* */
    private String trafficControl = null;
    /** Number of Traffic Islands* */
    private String trafficIsland = null;
    /** Number of Traffic signals* */
    private String trafficSign = null;
    /** Number of Trains* */
    private String train = null;
    /** Number of trees* */
    private String tree = null;
    /** Number of Trucks* */
    private String truck = null;
    /** Number of unknown vehicles* */
    private String unknownVehicleType = null;
    /** if the crash is in an urban area* */
    private String urban = null;
    /** Number of vans or utility vehicles* */
    private String vanOrUtility = null;
    /** Number of vehicles* */
    private String vehicle = null;
    /** Number of Bodies of water involved* */
    private String waterRiver = null;
    /** Weather A* */
    private String weatherA = null;
    /** Weather B* */
    private String weatherB = null;
    /** Crash Latitude* */
    private double lat = 0.0;
    /** Crash Longitude* */
    private double lng = 0.0;

    /** A constructor to create blank object where all fields are populated with default values */
    public CrashFromDatabase() {}

    /**
     * Creates a crashFromData from a CrashFromFile object. In doing so all integer fields in the
     * crashFromFile object are turned into String fields.
     *
     * @param crashFromFile the crashFromFile object of which this object is a copy
     */
    public CrashFromDatabase(CrashFromFile crashFromFile) {
        this.objectID = crashFromFile.getObjectID();
        this.advisorySpeed = crashFromFile.getAdvisorySpeed().toString();
        this.bicycle = crashFromFile.getBicycle().toString();
        this.bridge = crashFromFile.getBridge().toString();
        this.bus = crashFromFile.getBus().toString();
        this.carStationWagon = crashFromFile.getCarStationWagon().toString();
        this.cliffBank = crashFromFile.getCliffBank().toString();
        this.crashDirectionDescription = crashFromFile.getCrashDirectionDescription();
        this.crashFinancialYear = crashFromFile.getCrashFinancialYear();
        this.crashLocation1 = crashFromFile.getCrashLocation1();
        this.crashLocation2 = crashFromFile.getCrashLocation2();
        this.crashRoadSideRoad = crashFromFile.getCrashRoadSideRoad();
        this.crashSeverity = crashFromFile.getCrashSeverity();
        this.crashSHDescription = crashFromFile.getCrashSHDescription();
        this.crashYear = crashFromFile.getCrashYear().toString();
        this.debris = crashFromFile.getDebris().toString();
        this.directionRoleDescription = crashFromFile.getDirectionRoleDescription();
        this.ditch = crashFromFile.getDitch().toString();
        this.fatalCount = crashFromFile.getFatalCount().toString();
        this.fence = crashFromFile.getFence().toString();
        this.flatHill = crashFromFile.getFlatHill();
        this.guardRail = crashFromFile.getGuardRail().toString();
        this.holiday = crashFromFile.getHoliday();
        this.houseOrBuilding = crashFromFile.getHouseOrBuilding().toString();
        this.intersection = crashFromFile.getIntersection();
        this.kerb = crashFromFile.getKerb().toString();
        this.light = crashFromFile.getLight();
        this.minorInjuryCount = crashFromFile.getMinorInjuryCount().toString();
        this.moped = crashFromFile.getMoped().toString();
        this.motorcycle = crashFromFile.getMotorcycle().toString();
        this.NumberOfLanes = crashFromFile.getNumberOfLanes().toString();
        this.objectThrownOrDropped = crashFromFile.getObjectThrownOrDropped().toString();
        this.otherObject = crashFromFile.getOtherObject().toString();
        this.otherVehicleType = crashFromFile.getOtherVehicleType().toString();
        this.overBank = crashFromFile.getOverBank().toString();
        this.parkedVehicle = crashFromFile.getParkedVehicle().toString();
        this.pedestrian = crashFromFile.getPedestrian().toString();
        this.phoneBoxEtc = crashFromFile.getPhoneBoxEtc().toString();
        this.postOrPole = crashFromFile.getPostOrPole().toString();
        this.region = crashFromFile.getRegion();
        this.roadCharacter = crashFromFile.getRoadCharacter();
        this.roadLane = crashFromFile.getRoadLane();
        this.roadSurface = crashFromFile.getRoadSurface();
        this.roadworks = crashFromFile.getRoadworks().toString();
        this.schoolBus = crashFromFile.getSchoolBus().toString();
        this.seriousInjuryCount = crashFromFile.getSeriousInjuryCount().toString();
        this.slipOrFlood = crashFromFile.getSlipOrFlood().toString();
        this.speedLimit = crashFromFile.getSpeedLimit().toString();
        this.strayAnimal = crashFromFile.getStrayAnimal().toString();
        this.streetLight = crashFromFile.getStreetLight();
        this.suv = crashFromFile.getSuv().toString();
        this.taxi = crashFromFile.getTaxi().toString();
        this.temporarySpeedLimit = crashFromFile.getTemporarySpeedLimit().toString();
        this.tlaName = crashFromFile.getTlaName();
        this.trafficControl = crashFromFile.getTrafficControl();
        this.trafficIsland = crashFromFile.getTrafficIsland().toString();
        this.trafficSign = crashFromFile.getTrafficSign().toString();
        this.train = crashFromFile.getTrain().toString();
        this.tree = crashFromFile.getTree().toString();
        this.truck = crashFromFile.getTruck().toString();
        this.unknownVehicleType = crashFromFile.getUnknownVehicleType().toString();
        this.urban = crashFromFile.getUrban();
        this.vanOrUtility = crashFromFile.getVanOrUtility().toString();
        this.vehicle = crashFromFile.getVehicle().toString();
        this.waterRiver = crashFromFile.getWaterRiver().toString();
        this.weatherA = crashFromFile.getWeatherA();
        this.weatherB = crashFromFile.getWeatherB();
        this.lat = crashFromFile.getLat();
        this.lng = crashFromFile.getLng();
    }

    /**
     * Creates a CrashFromDatabase Object populated with data. All data recorded in this one object
     * should correspond to information about a single crash
     *
     * @param objectID The crash ID
     * @param advisorySpeed the advised speed at time of crash
     * @param bicycle number of bicycles in crash
     * @param bridge how many times a bridge, tunnel, the abutments, and/or handrails were struck in
     *     the crash
     * @param bus how many buses were involved in the crash. Excludes school buses.
     * @param carStationWagon how many cars or station wagons were involved in the crash.
     * @param cliffBank how many times a cliff or bank was struck in the crash.
     * @param crashDirectionDescription The direction of the crash from the reference point.
     * @param crashFinancialYear The financial year in which the crash occurred.
     * @param crashLocation1 First part of the crash location.
     * @param crashLocation2 Second part of the crash location.
     * @param sideRoadName Second part of the crash location.
     * @param crashSeverity The severity of the crash.
     * @param crashSHDescription Indicates whether the crash occurred on a State Highway.
     * @param crashYear The year in which the crash occurred.
     * @param debris how many times debris, boulders, or items dropped or thrown vehicles were
     *     struck in crash
     * @param directionRoll The direction of the principal vehicle involved in the crash.
     * @param ditch The direction of the principal vehicle involved in the crash.
     * @param fatalCount number of fatalities
     * @param fence how many times a fence was struck in the crash.
     * @param flatHill Whether the road is flat or sloped
     * @param guardRail how many times a guard or guard rail was struck in the crash
     * @param holiday what holiday, if any, the crash occurred on.
     * @param houseOrBuilding indicate how many times houses, garages, sheds, or other buildings
     *     were struck in the crash.
     * @param intersection (No data) <sub>this is a dead value never assigned in the dataset but
     *     kept for integrity when loading data from a csv source</sub>
     * @param kerb many times a kerb was struck in the crash
     * @param light The light at the time and place of the crash.
     * @param minorInjuryCount number of minor injuries associated with the crash.
     * @param moped how many mopeds were involved in the crash.
     * @param motorcycle how many motorcycles were involved in the crash.
     * @param numberOfLanes The number of lanes on the crash road
     * @param objectThrownOrDropped how many times objects were thrown at or dropped on vehicles in
     *     the crash.
     * @param otherObject how many times an object was struck in a crash and the object struck was
     *     not pre-defined.
     * @param otherVehicle how many other vehicles (not include in any other category) were involved
     *     in the crash
     * @param overBank how many times an embankment was struck or driven over during a crash.
     * @param parkedVehicle how many times a parked or unattended vehicle was struck in the crash.
     * @param pedestrian how many pedestrians were involved in the crash.
     * @param phoneBoxEtc how many times telephone kiosks, traffic signal controllers, bus shelters,
     *     or other public furniture was struck in the crash
     * @param postOrPole how many times a post/pole was struck in the crash.
     * @param region Identifies the local government region in which the crash occurred
     * @param roadCharacter The general nature of the road.
     * @param roadLane The lane configuration of the road.
     * @param roadSurface The road markings at the crash site.
     * @param roadworks how many times an object associated with 'roadworks' was struck in the
     *     crash. excludes vehicles
     * @param schoolBus how many school buses were involved in the crash.
     * @param seriousInjuryCount the number of serious injuries associated with the crash.
     * @param slipOrFlood how many times landslips, washouts, or floods (excluding rivers) were
     *     objects struck in the crash.
     * @param speedLimit The speed limit in force at the crash site at the time of the crash.
     * @param strayAnimals how many times stray animals were struck in the crash.
     * @param streetLight The status of streetlights at the time of crash
     * @param suv how many SUVs were involved in the crash.
     * @param taxi indicate how many taxis were involved in the crash.
     * @param temporarySpeedLimit temporary speed limit at the crash site if one exists.
     * @param tlaName name of the Territorial Local Authority the crash has been attributed.
     * @param trafficControl The traffic control signals at the crash site.
     * @param trafficIsland how many times a traffic island, medians (excluding barriers) were hit
     *     in the crash
     * @param trafficSign indicate how many times traffic signage was hit in the crash
     * @param train how many times a train, rolling stock, or jiggers was struck in the crash
     * @param tree how many times trees or other growing items were struck during the crash.
     * @param truck how many trucks were involved in the crash.
     * @param unknownVehicleType how many vehicles were involved in the crash where the vehicle type
     *     was unknown
     * @param urban Derived from the Speed Limit variable. Values are Urban (if &lt; 80) and Open
     *     Road (if LSZ or &gt; 79).
     * @param vanOrUtility how many vans or utes were involved in the crash.
     * @param vehicle how many times a stationary attended vehicle was struck in the crash.
     * @param waterRiver how many times a body of water was hit in the crash
     * @param weatherA Indicates weather at the crash time/place.
     * @param weatherB Indicates weather at the crash time/place.
     * @param lat Indicates the latitude coordinate of the crash in degrees.
     * @param lng Indicates the longitude coordinate of the crash in degrees.
     */
    public CrashFromDatabase(
            Integer objectID,
            String advisorySpeed,
            String bicycle,
            String bridge,
            String bus,
            String carStationWagon,
            String cliffBank,
            String crashDirectionDescription,
            String crashFinancialYear,
            String crashLocation1,
            String crashLocation2,
            String sideRoadName,
            String crashSeverity,
            String crashSHDescription,
            String crashYear,
            String debris,
            String directionRoll,
            String ditch,
            String fatalCount,
            String fence,
            String flatHill,
            String guardRail,
            String holiday,
            String houseOrBuilding,
            String intersection,
            String kerb,
            String light,
            String minorInjuryCount,
            String moped,
            String motorcycle,
            String numberOfLanes,
            String objectThrownOrDropped,
            String otherObject,
            String otherVehicle,
            String overBank,
            String parkedVehicle,
            String pedestrian,
            String phoneBoxEtc,
            String postOrPole,
            String region,
            String roadCharacter,
            String roadLane,
            String roadSurface,
            String roadworks,
            String schoolBus,
            String seriousInjuryCount,
            String slipOrFlood,
            String speedLimit,
            String strayAnimals,
            String streetLight,
            String suv,
            String taxi,
            String temporarySpeedLimit,
            String tlaName,
            String trafficControl,
            String trafficIsland,
            String trafficSign,
            String train,
            String tree,
            String truck,
            String unknownVehicleType,
            String urban,
            String vanOrUtility,
            String vehicle,
            String waterRiver,
            String weatherA,
            String weatherB,
            double lat,
            double lng) {
        this.objectID = objectID;
        this.advisorySpeed = advisorySpeed;
        this.bicycle = bicycle;
        this.bridge = bridge;
        this.bus = bus;
        this.carStationWagon = carStationWagon;
        this.cliffBank = cliffBank;
        this.crashDirectionDescription = crashDirectionDescription;
        this.crashFinancialYear = crashFinancialYear;
        this.crashLocation1 = crashLocation1;
        this.crashLocation2 = crashLocation2;
        this.crashRoadSideRoad = sideRoadName;
        this.crashSeverity = crashSeverity;
        this.crashSHDescription = crashSHDescription;
        this.crashYear = crashYear;
        this.debris = debris;
        this.directionRoleDescription = directionRoll;
        this.ditch = ditch;
        this.fatalCount = fatalCount;
        this.fence = fence;
        this.flatHill = flatHill;
        this.guardRail = guardRail;
        this.holiday = holiday;
        this.houseOrBuilding = houseOrBuilding;
        this.intersection = intersection;
        this.kerb = kerb;
        this.light = light;
        this.minorInjuryCount = minorInjuryCount;
        this.moped = moped;
        this.motorcycle = motorcycle;
        this.NumberOfLanes = numberOfLanes;
        this.objectThrownOrDropped = objectThrownOrDropped;
        this.otherObject = otherObject;
        this.otherVehicleType = otherVehicle;
        this.overBank = overBank;
        this.parkedVehicle = parkedVehicle;
        this.pedestrian = pedestrian;
        this.phoneBoxEtc = phoneBoxEtc;
        this.postOrPole = postOrPole;
        this.region = region;
        this.roadCharacter = roadCharacter;
        this.roadLane = roadLane;
        this.roadSurface = roadSurface;
        this.roadworks = roadworks;
        this.schoolBus = schoolBus;
        this.seriousInjuryCount = seriousInjuryCount;
        this.slipOrFlood = slipOrFlood;
        this.speedLimit = speedLimit;
        this.strayAnimal = strayAnimals;
        this.streetLight = streetLight;
        this.suv = suv;
        this.taxi = taxi;
        this.temporarySpeedLimit = temporarySpeedLimit;
        this.tlaName = tlaName;
        this.trafficControl = trafficControl;
        this.trafficIsland = trafficIsland;
        this.trafficSign = trafficSign;
        this.train = train;
        this.tree = tree;
        this.truck = truck;
        this.unknownVehicleType = unknownVehicleType;
        this.urban = urban;
        this.vanOrUtility = vanOrUtility;
        this.vehicle = vehicle;
        this.waterRiver = waterRiver;
        this.weatherA = weatherA;
        this.weatherB = weatherB;
        this.lat = lat;
        this.lng = lng;
    }

    /**
     * If a string is passed through containing null it converts it to "-" to be more user-friendly
     * for readability.
     *
     * @param input input string
     * @return input string with null string filtered out
     */
    private static String nullToNoDataFilter(String input) {
        if ((input == null)
                || (input.equals("Null"))
                || (input.equals("null"))
                || (input.equals("Nil"))) {
            return "-";
        }
        return input;
    }

    /**
     * Data is run against this before being displayed to the user to enhance readability of
     * database artifacts such as null values and missing values. e.g.: a year saved as "0" is
     * returned as "-"
     */
    public void enhancedUserReadability() {
        if (crashYear.equals("0")) {
            crashYear = "-";
        }
        if (advisorySpeed.equals("0")) {
            advisorySpeed = "-";
        }
        if (temporarySpeedLimit.equals("0")) {
            temporarySpeedLimit = "-";
        }
        weatherA = nullToNoDataFilter(weatherA);
        weatherB = nullToNoDataFilter(weatherB);
        trafficControl = nullToNoDataFilter(trafficControl);
        crashDirectionDescription = nullToNoDataFilter(crashDirectionDescription);
        roadCharacter = nullToNoDataFilter(roadCharacter);
    }

    /**
     * Gets this objects crashRoadSideRoad attribute
     *
     * @return this objects crashRoadSideRoad attribute
     */
    public String getCrashRoadSideRoad() {
        return crashRoadSideRoad;
    }

    /**
     * Sets this objects crashRoadSideRoad attribute and then returns this object. By returning
     * itself set statements can be chained together.
     *
     * @param crashRoadSideRoad the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setCrashRoadSideRoad(String crashRoadSideRoad) {
        this.crashRoadSideRoad = crashRoadSideRoad;
        return this;
    }

    /**
     * Gets this objects objectID attribute
     *
     * @return this objects objectID attribute
     */
    public Integer getObjectID() {
        return objectID;
    }

    /**
     * Sets this objects objectID attribute and then returns this object. By returning itself set
     * statements can be chained together.
     *
     * @param objectID the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setObjectID(Integer objectID) {
        this.objectID = objectID;
        return this;
    }

    /**
     * Gets this objects advisorySpeed attribute
     *
     * @return this objects advisorySpeed attribute
     */
    public String getAdvisorySpeed() {
        return advisorySpeed;
    }

    /**
     * Sets this objects advisorySpeed attribute and then returns this object. By returning itself
     * set statements can be chained together.
     *
     * @param advisorySpeed the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setAdvisorySpeed(String advisorySpeed) {
        this.advisorySpeed = advisorySpeed;
        return this;
    }

    /**
     * Gets this objects bicycle attribute
     *
     * @return this objects bicycle attribute
     */
    public String getBicycle() {
        return bicycle;
    }

    /**
     * Sets this objects bicycle attribute and then returns this object. By returning itself set
     * statements can be chained together.
     *
     * @param bicycle the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setBicycle(String bicycle) {
        this.bicycle = bicycle;
        return this;
    }

    /**
     * Gets this objects bridge attribute
     *
     * @return this objects bridge attribute
     */
    public String getBridge() {
        return bridge;
    }

    /**
     * Sets this objects bridge attribute and then returns this object. By returning itself set
     * statements can be chained together.
     *
     * @param bridge the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setBridge(String bridge) {
        this.bridge = bridge;
        return this;
    }

    /**
     * Gets this objects bus attribute
     *
     * @return this objects bus attribute
     */
    public String getBus() {
        return bus;
    }

    /**
     * Sets this objects bus attribute and then returns this object. By returning itself set
     * statements can be chained together.
     *
     * @param bus the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setBus(String bus) {
        this.bus = bus;
        return this;
    }

    /**
     * Gets this objects carStationWagon attribute
     *
     * @return this objects carStationWagon attribute
     */
    public String getCarStationWagon() {
        return carStationWagon;
    }

    /**
     * Sets this objects carStationWagon attribute and then returns this object. By returning itself
     * set statements can be chained together.
     *
     * @param carStationWagon the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setCarStationWagon(String carStationWagon) {
        this.carStationWagon = carStationWagon;
        return this;
    }

    /**
     * Gets this objects cliffBank attribute
     *
     * @return this objects cliffBank attribute
     */
    public String getCliffBank() {
        return cliffBank;
    }

    /**
     * Sets this objects cliffBank attribute and then returns this object. By returning itself set
     * statements can be chained together.
     *
     * @param cliffBank the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setCliffBank(String cliffBank) {
        this.cliffBank = cliffBank;
        return this;
    }

    /**
     * Gets this objects crashDirectionDescription attribute
     *
     * @return this objects crashDirectionDescription attribute
     */
    public String getCrashDirectionDescription() {
        return crashDirectionDescription;
    }

    /**
     * Sets this objects crashDirectionDescription attribute and then returns this object. By
     * returning itself set statements can be chained together.
     *
     * @param crashDirectionDescription the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setCrashDirectionDescription(String crashDirectionDescription) {
        this.crashDirectionDescription = crashDirectionDescription;
        return this;
    }

    /**
     * Gets this objects crashFinancialYear attribute
     *
     * @return this objects crashFinancialYear attribute
     */
    public String getCrashFinancialYear() {
        return crashFinancialYear;
    }

    /**
     * Sets this objects crashFinancialYear attribute and then returns this object. By returning
     * itself set statements can be chained together.
     *
     * @param crashFinancialYear the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setCrashFinancialYear(String crashFinancialYear) {
        this.crashFinancialYear = crashFinancialYear;
        return this;
    }

    /**
     * Gets this objects crashLocation1 attribute
     *
     * @return this objects crashLocation1 attribute
     */
    public String getCrashLocation1() {
        return crashLocation1;
    }

    /**
     * Sets this objects crashLocation1 attribute and then returns this object. By returning itself
     * set statements can be chained together.
     *
     * @param crashLocation1 the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setCrashLocation1(String crashLocation1) {
        this.crashLocation1 = crashLocation1;
        return this;
    }

    /**
     * Gets this objects crashLocation2 attribute
     *
     * @return this objects crashLocation2 attribute
     */
    public String getCrashLocation2() {
        return crashLocation2;
    }

    /**
     * Sets this objects crashLocation2 attribute and then returns this object. By returning itself
     * set statements can be chained together.
     *
     * @param crashLocation2 the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setCrashLocation2(String crashLocation2) {
        this.crashLocation2 = crashLocation2;
        return this;
    }

    /**
     * Gets this objects crashSeverity attribute
     *
     * @return this objects crashSeverity attribute
     */
    public String getCrashSeverity() {
        return crashSeverity;
    }

    /**
     * Sets this objects crashSeverity attribute and then returns this object. By returning itself
     * set statements can be chained together.
     *
     * @param crashSeverity the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setCrashSeverity(String crashSeverity) {
        this.crashSeverity = crashSeverity;
        return this;
    }

    /**
     * Gets this objects crashSHDescription attribute
     *
     * @return this objects crashSHDescription attribute
     */
    public String getCrashSHDescription() {
        return crashSHDescription;
    }

    /**
     * Sets this objects crashSHDescription attribute and then returns this object. By returning
     * itself set statements can be chained together.
     *
     * @param crashSHDescription the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setCrashSHDescription(String crashSHDescription) {
        this.crashSHDescription = crashSHDescription;
        return this;
    }

    /**
     * Gets this objects crashYear attribute
     *
     * @return this objects crashYear attribute
     */
    public String getCrashYear() {
        return crashYear;
    }

    /**
     * Sets this objects crashYear attribute and then returns this object. By returning itself set
     * statements can be chained together.
     *
     * @param crashYear the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setCrashYear(String crashYear) {
        this.crashYear = crashYear;
        return this;
    }

    /**
     * Gets this objects debris attribute
     *
     * @return this objects debris attribute
     */
    public String getDebris() {
        return debris;
    }

    /**
     * Sets this objects debris attribute and then returns this object. By returning itself set
     * statements can be chained together.
     *
     * @param debris the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setDebris(String debris) {
        this.debris = debris;
        return this;
    }

    /**
     * Gets this objects directionRoleDescription attribute
     *
     * @return this objects directionRoleDescription attribute
     */
    public String getDirectionRoleDescription() {
        return directionRoleDescription;
    }

    /**
     * Sets this objects directionRoleDescription attribute and then returns this object. By
     * returning itself set statements can be chained together.
     *
     * @param directionRoleDescription the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setDirectionRoleDescription(String directionRoleDescription) {
        this.directionRoleDescription = directionRoleDescription;
        return this;
    }

    /**
     * Gets this objects ditch attribute
     *
     * @return this objects ditch attribute
     */
    public String getDitch() {
        return ditch;
    }

    /**
     * Sets this objects ditch attribute and then returns this object. By returning itself set
     * statements can be chained together.
     *
     * @param ditch the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setDitch(String ditch) {
        this.ditch = ditch;
        return this;
    }

    /**
     * Gets this objects fatalCount attribute
     *
     * @return this objects fatalCount attribute
     */
    public String getFatalCount() {
        return fatalCount;
    }

    /**
     * Sets this objects fatalCount attribute and then returns this object. By returning itself set
     * statements can be chained together.
     *
     * @param fatalCount the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setFatalCount(String fatalCount) {
        this.fatalCount = fatalCount;
        return this;
    }

    /**
     * Gets this objects fence attribute
     *
     * @return this objects fence attribute
     */
    public String getFence() {
        return fence;
    }

    /**
     * Sets this objects fence attribute and then returns this object. By returning itself set
     * statements can be chained together.
     *
     * @param fence the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setFence(String fence) {
        this.fence = fence;
        return this;
    }

    /**
     * Gets this objects flatHill attribute
     *
     * @return this objects flatHill attribute
     */
    public String getFlatHill() {
        return flatHill;
    }

    /**
     * Sets this objects flatHill attribute and then returns this object. By returning itself set
     * statements can be chained together.
     *
     * @param flatHill the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setFlatHill(String flatHill) {
        this.flatHill = flatHill;
        return this;
    }

    /**
     * Gets this objects guardRail attribute
     *
     * @return this objects guardRail attribute
     */
    public String getGuardRail() {
        return guardRail;
    }

    /**
     * Sets this objects guardRail attribute and then returns this object. By returning itself set
     * statements can be chained together.
     *
     * @param guardRail the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setGuardRail(String guardRail) {
        this.guardRail = guardRail;
        return this;
    }

    /**
     * Gets this objects holiday attribute
     *
     * @return this objects holiday attribute
     */
    public String getHoliday() {
        return holiday;
    }

    /**
     * Sets this objects holiday attribute and then returns this object. By returning itself set
     * statements can be chained together.
     *
     * @param holiday the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setHoliday(String holiday) {
        this.holiday = holiday;
        return this;
    }

    /**
     * Gets this objects houseOrBuilding attribute
     *
     * @return this objects houseOrBuilding attribute
     */
    public String getHouseOrBuilding() {
        return houseOrBuilding;
    }

    /**
     * Sets this objects houseOrBuilding attribute and then returns this object. By returning itself
     * set statements can be chained together.
     *
     * @param houseOrBuilding the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setHouseOrBuilding(String houseOrBuilding) {
        this.houseOrBuilding = houseOrBuilding;
        return this;
    }

    /**
     * Gets this objects intersection attribute
     *
     * @return this objects intersection attribute
     */
    public String getIntersection() {
        return intersection;
    }

    /**
     * Sets this objects intersection attribute and then returns this object. By returning itself
     * set statements can be chained together.
     *
     * @param intersection the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setIntersection(String intersection) {
        this.intersection = intersection;
        return this;
    }

    /**
     * Gets this objects kerb attribute
     *
     * @return this objects kerb attribute
     */
    public String getKerb() {
        return kerb;
    }

    /**
     * Sets this objects kerb attribute and then returns this object. By returning itself set
     * statements can be chained together.
     *
     * @param kerb the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setKerb(String kerb) {
        this.kerb = kerb;
        return this;
    }

    /**
     * Gets this objects light attribute
     *
     * @return this objects light attribute
     */
    public String getLight() {
        return light;
    }

    /**
     * Sets this objects light attribute and then returns this object. By returning itself set
     * statements can be chained together.
     *
     * @param light the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setLight(String light) {
        this.light = light;
        return this;
    }

    /**
     * Gets this objects minorInjuryCount attribute
     *
     * @return this objects minorInjuryCount attribute
     */
    public String getMinorInjuryCount() {
        return minorInjuryCount;
    }

    /**
     * Sets this objects minorInjuryCount attribute and then returns this object. By returning
     * itself set statements can be chained together.
     *
     * @param minorInjuryCount the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setMinorInjuryCount(String minorInjuryCount) {
        this.minorInjuryCount = minorInjuryCount;
        return this;
    }

    /**
     * Gets this objects moped attribute
     *
     * @return this objects moped attribute
     */
    public String getMoped() {
        return moped;
    }

    /**
     * Sets this objects moped attribute and then returns this object. By returning itself set
     * statements can be chained together.
     *
     * @param moped the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setMoped(String moped) {
        this.moped = moped;
        return this;
    }

    /**
     * Gets this objects motorcycle attribute
     *
     * @return this objects motorcycle attribute
     */
    public String getMotorcycle() {
        return motorcycle;
    }

    /**
     * Sets this objects motorcycle attribute and then returns this object. By returning itself set
     * statements can be chained together.
     *
     * @param motorcycle the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setMotorcycle(String motorcycle) {
        this.motorcycle = motorcycle;
        return this;
    }

    /**
     * Gets this objects NumberOfLanes attribute
     *
     * @return this objects NumberOfLanes attribute
     */
    public String getNumberOfLanes() {
        return NumberOfLanes;
    }

    /**
     * Sets this objects NumberOfLanes attribute and then returns this object. By returning itself
     * set statements can be chained together.
     *
     * @param NumberOfLanes the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setNumberOfLanes(String NumberOfLanes) {
        this.NumberOfLanes = NumberOfLanes;
        return this;
    }

    /**
     * Gets this objects objectThrownOrDropped attribute
     *
     * @return this objects objectThrownOrDropped attribute
     */
    public String getObjectThrownOrDropped() {
        return objectThrownOrDropped;
    }

    /**
     * Sets this objects objectThrownOrDropped attribute and then returns this object. By returning
     * itself set statements can be chained together.
     *
     * @param objectThrownOrDropped the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setObjectThrownOrDropped(String objectThrownOrDropped) {
        this.objectThrownOrDropped = objectThrownOrDropped;
        return this;
    }

    /**
     * Gets this objects otherObject attribute
     *
     * @return this objects otherObject attribute
     */
    public String getOtherObject() {
        return otherObject;
    }

    /**
     * Sets this objects otherObject attribute and then returns this object. By returning itself set
     * statements can be chained together.
     *
     * @param otherObject the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setOtherObject(String otherObject) {
        this.otherObject = otherObject;
        return this;
    }

    /**
     * Gets this objects otherVehicleType attribute
     *
     * @return this objects otherVehicleType attribute
     */
    public String getOtherVehicleType() {
        return otherVehicleType;
    }

    /**
     * Sets this objects otherVehicleType attribute and then returns this object. By returning
     * itself set statements can be chained together.
     *
     * @param otherVehicleType the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setOtherVehicleType(String otherVehicleType) {
        this.otherVehicleType = otherVehicleType;
        return this;
    }

    /**
     * Gets this objects overBank attribute
     *
     * @return this objects overBank attribute
     */
    public String getOverBank() {
        return overBank;
    }

    /**
     * Sets this objects overBank attribute and then returns this object. By returning itself set
     * statements can be chained together.
     *
     * @param overBank the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setOverBank(String overBank) {
        this.overBank = overBank;
        return this;
    }

    /**
     * Gets this objects parkedVehicle attribute
     *
     * @return this objects parkedVehicle attribute
     */
    public String getParkedVehicle() {
        return parkedVehicle;
    }

    /**
     * Sets this objects parkedVehicle attribute and then returns this object. By returning itself
     * set statements can be chained together.
     *
     * @param parkedVehicle the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setParkedVehicle(String parkedVehicle) {
        this.parkedVehicle = parkedVehicle;
        return this;
    }

    /**
     * Gets this objects pedestrian attribute
     *
     * @return this objects pedestrian attribute
     */
    public String getPedestrian() {
        return pedestrian;
    }

    /**
     * Sets this objects pedestrian attribute and then returns this object. By returning itself set
     * statements can be chained together.
     *
     * @param pedestrian the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setPedestrian(String pedestrian) {
        this.pedestrian = pedestrian;
        return this;
    }

    /**
     * Gets this objects phoneBoxEtc attribute
     *
     * @return this objects phoneBoxEtc attribute
     */
    public String getPhoneBoxEtc() {
        return phoneBoxEtc;
    }

    /**
     * Sets this objects phoneBoxEtc attribute and then returns this object. By returning itself set
     * statements can be chained together.
     *
     * @param phoneBoxEtc the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setPhoneBoxEtc(String phoneBoxEtc) {
        this.phoneBoxEtc = phoneBoxEtc;
        return this;
    }

    /**
     * Gets this objects postOrPole attribute
     *
     * @return this objects postOrPole attribute
     */
    public String getPostOrPole() {
        return postOrPole;
    }

    /**
     * Sets this objects postOrPole attribute and then returns this object. By returning itself set
     * statements can be chained together.
     *
     * @param postOrPole the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setPostOrPole(String postOrPole) {
        this.postOrPole = postOrPole;
        return this;
    }

    /**
     * Gets this objects region attribute
     *
     * @return this objects region attribute
     */
    public String getRegion() {
        return region;
    }

    /**
     * Sets this objects region attribute and then returns this object. By returning itself set
     * statements can be chained together.
     *
     * @param region the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setRegion(String region) {
        this.region = region;
        return this;
    }

    /**
     * Gets this objects roadCharacter attribute
     *
     * @return this objects roadCharacter attribute
     */
    public String getRoadCharacter() {
        return roadCharacter;
    }

    /**
     * Sets this objects roadCharacter attribute and then returns this object. By returning itself
     * set statements can be chained together.
     *
     * @param roadCharacter the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setRoadCharacter(String roadCharacter) {
        this.roadCharacter = roadCharacter;
        return this;
    }

    /**
     * Gets this objects roadLane attribute
     *
     * @return this objects roadLane attribute
     */
    public String getRoadLane() {
        return roadLane;
    }

    /**
     * Sets this objects roadLane attribute and then returns this object. By returning itself set
     * statements can be chained together.
     *
     * @param roadLane the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setRoadLane(String roadLane) {
        this.roadLane = roadLane;
        return this;
    }

    /**
     * Gets this objects roadSurface attribute
     *
     * @return this objects roadSurface attribute
     */
    public String getRoadSurface() {
        return roadSurface;
    }

    /**
     * Sets this objects roadSurface attribute and then returns this object. By returning itself set
     * statements can be chained together.
     *
     * @param roadSurface the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setRoadSurface(String roadSurface) {
        this.roadSurface = roadSurface;
        return this;
    }

    /**
     * Gets this objects roadworks attribute
     *
     * @return this objects roadworks attribute
     */
    public String getRoadworks() {
        return roadworks;
    }

    /**
     * Sets this objects roadworks attribute and then returns this object. By returning itself set
     * statements can be chained together.
     *
     * @param roadworks the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setRoadworks(String roadworks) {
        this.roadworks = roadworks;
        return this;
    }

    /**
     * Gets this objects schoolBus attribute
     *
     * @return this objects schoolBus attribute
     */
    public String getSchoolBus() {
        return schoolBus;
    }

    /**
     * Sets this objects schoolBus attribute and then returns this object. By returning itself set
     * statements can be chained together.
     *
     * @param schoolBus the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setSchoolBus(String schoolBus) {
        this.schoolBus = schoolBus;
        return this;
    }

    /**
     * Gets this objects seriousInjuryCount attribute
     *
     * @return this objects seriousInjuryCount attribute
     */
    public String getSeriousInjuryCount() {
        return seriousInjuryCount;
    }

    /**
     * Sets this objects seriousInjuryCount attribute and then returns this object. By returning
     * itself set statements can be chained together.
     *
     * @param seriousInjuryCount the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setSeriousInjuryCount(String seriousInjuryCount) {
        this.seriousInjuryCount = seriousInjuryCount;
        return this;
    }

    /**
     * Gets this objects slipOrFlood attribute
     *
     * @return this objects slipOrFlood attribute
     */
    public String getSlipOrFlood() {
        return slipOrFlood;
    }

    /**
     * Sets this objects slipOrFlood attribute and then returns this object. By returning itself set
     * statements can be chained together.
     *
     * @param slipOrFlood the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setSlipOrFlood(String slipOrFlood) {
        this.slipOrFlood = slipOrFlood;
        return this;
    }

    /**
     * Gets this objects speedLimit attribute
     *
     * @return this objects speedLimit attribute
     */
    public String getSpeedLimit() {
        return speedLimit;
    }

    /**
     * Sets this objects speedLimit attribute and then returns this object. By returning itself set
     * statements can be chained together.
     *
     * @param speedLimit the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setSpeedLimit(String speedLimit) {
        this.speedLimit = speedLimit;
        return this;
    }

    /**
     * Gets this objects suv attribute
     *
     * @return this objects suv attribute
     */
    public String getSuv() {
        return suv;
    }

    /**
     * Sets this objects suv attribute and then returns this object. By returning itself set
     * statements can be chained together.
     *
     * @param suv the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setSuv(String suv) {
        this.suv = suv;
        return this;
    }

    /**
     * Gets this objects taxi attribute
     *
     * @return this objects taxi attribute
     */
    public String getTaxi() {
        return taxi;
    }

    /**
     * Sets this objects taxi attribute and then returns this object. By returning itself set
     * statements can be chained together.
     *
     * @param taxi the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setTaxi(String taxi) {
        this.taxi = taxi;
        return this;
    }

    /**
     * Gets this objects temporarySpeedLimit attribute
     *
     * @return this objects temporarySpeedLimit attribute
     */
    public String getTemporarySpeedLimit() {
        return temporarySpeedLimit;
    }

    /**
     * Sets this objects temporarySpeedLimit attribute and then returns this object. By returning
     * itself set statements can be chained together.
     *
     * @param temporarySpeedLimit the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setTemporarySpeedLimit(String temporarySpeedLimit) {
        this.temporarySpeedLimit = temporarySpeedLimit;
        return this;
    }

    /**
     * Gets this objects tlaName attribute
     *
     * @return this objects tlaName attribute
     */
    public String getTlaName() {
        return tlaName;
    }

    /**
     * Sets this objects tlaName attribute and then returns this object. By returning itself set
     * statements can be chained together.
     *
     * @param tlaName the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setTlaName(String tlaName) {
        this.tlaName = tlaName;
        return this;
    }

    /**
     * Gets this objects trafficControl attribute
     *
     * @return this objects trafficControl attribute
     */
    public String getTrafficControl() {
        return trafficControl;
    }

    /**
     * Sets this objects trafficControl attribute and then returns this object. By returning itself
     * set statements can be chained together.
     *
     * @param trafficControl the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setTrafficControl(String trafficControl) {
        this.trafficControl = trafficControl;
        return this;
    }

    /**
     * Gets this objects trafficIsland attribute
     *
     * @return this objects trafficIsland attribute
     */
    public String getTrafficIsland() {
        return trafficIsland;
    }

    /**
     * Sets this objects trafficIsland attribute and then returns this object. By returning itself
     * set statements can be chained together.
     *
     * @param trafficIsland the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setTrafficIsland(String trafficIsland) {
        this.trafficIsland = trafficIsland;
        return this;
    }

    /**
     * Gets this objects trafficSign attribute
     *
     * @return this objects trafficSign attribute
     */
    public String getTrafficSign() {
        return trafficSign;
    }

    /**
     * Sets this objects trafficSign attribute and then returns this object. By returning itself set
     * statements can be chained together.
     *
     * @param trafficSign the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setTrafficSign(String trafficSign) {
        this.trafficSign = trafficSign;
        return this;
    }

    /**
     * Gets this objects train attribute
     *
     * @return this objects train attribute
     */
    public String getTrain() {
        return train;
    }

    /**
     * Sets this objects train attribute and then returns this object. By returning itself set
     * statements can be chained together.
     *
     * @param train the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setTrain(String train) {
        this.train = train;
        return this;
    }

    /**
     * Gets this objects tree attribute
     *
     * @return this objects tree attribute
     */
    public String getTree() {
        return tree;
    }

    /**
     * Sets this objects tree attribute and then returns this object. By returning itself set
     * statements can be chained together.
     *
     * @param tree the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setTree(String tree) {
        this.tree = tree;
        return this;
    }

    /**
     * Gets this objects truck attribute
     *
     * @return this objects truck attribute
     */
    public String getTruck() {
        return truck;
    }

    /**
     * Sets this objects truck attribute and then returns this object. By returning itself set
     * statements can be chained together.
     *
     * @param truck the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setTruck(String truck) {
        this.truck = truck;
        return this;
    }

    /**
     * Gets this objects unknownVehicleType attribute
     *
     * @return this objects unknownVehicleType attribute
     */
    public String getUnknownVehicleType() {
        return unknownVehicleType;
    }

    /**
     * Sets this objects unknownVehicleType attribute and then returns this object. By returning
     * itself set statements can be chained together.
     *
     * @param unknownVehicleType the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setUnknownVehicleType(String unknownVehicleType) {
        this.unknownVehicleType = unknownVehicleType;
        return this;
    }

    /**
     * Gets this objects urban attribute
     *
     * @return this objects urban attribute
     */
    public String getUrban() {
        return urban;
    }

    /**
     * Sets this objects urban attribute and then returns this object. By returning itself set
     * statements can be chained together.
     *
     * @param urban the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setUrban(String urban) {
        this.urban = urban;
        return this;
    }

    /**
     * Gets this objects vanOrUtility attribute
     *
     * @return this objects vanOrUtility attribute
     */
    public String getVanOrUtility() {
        return vanOrUtility;
    }

    /**
     * Sets this objects vanOrUtility attribute and then returns this object. By returning itself
     * set statements can be chained together.
     *
     * @param vanOrUtility the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setVanOrUtility(String vanOrUtility) {
        this.vanOrUtility = vanOrUtility;
        return this;
    }

    /**
     * Gets this objects vehicle attribute
     *
     * @return this objects vehicle attribute
     */
    public String getVehicle() {
        return vehicle;
    }

    /**
     * Sets this objects vehicle attribute and then returns this object. By returning itself set
     * statements can be chained together.
     *
     * @param vehicle the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setVehicle(String vehicle) {
        this.vehicle = vehicle;
        return this;
    }

    /**
     * Gets this objects waterRiver attribute
     *
     * @return this objects waterRiver attribute
     */
    public String getWaterRiver() {
        return waterRiver;
    }

    /**
     * Sets this objects waterRiver attribute and then returns this object. By returning itself set
     * statements can be chained together.
     *
     * @param waterRiver the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setWaterRiver(String waterRiver) {
        this.waterRiver = waterRiver;
        return this;
    }

    /**
     * Gets this objects weatherA attribute
     *
     * @return this objects weatherA attribute
     */
    public String getWeatherA() {
        return weatherA;
    }

    /**
     * Sets this objects weatherA attribute and then returns this object. By returning itself set
     * statements can be chained together.
     *
     * @param weatherA the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setWeatherA(String weatherA) {
        this.weatherA = weatherA;
        return this;
    }

    /**
     * Gets this objects weatherB attribute
     *
     * @return this objects weatherB attribute
     */
    public String getWeatherB() {
        return weatherB;
    }

    /**
     * Sets this objects weatherB attribute and then returns this object. By returning itself set
     * statements can be chained together.
     *
     * @param weatherB the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setWeatherB(String weatherB) {
        this.weatherB = weatherB;
        return this;
    }

    /**
     * Gets this objects lat attribute
     *
     * @return this objects lat attribute
     */
    public double getLat() {
        return lat;
    }

    /**
     * Sets this objects lat attribute and then returns this object. By returning itself set
     * statements can be chained together.
     *
     * @param lat the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setLat(double lat) {
        this.lat = lat;
        return this;
    }

    /**
     * Gets this objects lng attribute
     *
     * @return this objects lng attribute
     */
    public double getLng() {
        return lng;
    }

    /**
     * Sets this objects lng attribute and then returns this object. By returning itself set
     * statements can be chained together.
     *
     * @param lng the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setLng(double lng) {
        this.lng = lng;
        return this;
    }

    /**
     * Gets this objects strayAnimal attribute
     *
     * @return this objects strayAnimal attribute
     */
    public String getStrayAnimal() {
        return strayAnimal;
    }

    /**
     * Sets this objects strayAnimal attribute and then returns this object. By returning itself set
     * statements can be chained together.
     *
     * @param strayAnimal the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setStrayAnimal(String strayAnimal) {
        this.strayAnimal = strayAnimal;
        return this;
    }

    /**
     * Gets this objects streetLight attribute
     *
     * @return this objects streetLight attribute
     */
    public String getStreetLight() {
        return streetLight;
    }

    /**
     * Sets this objects streetLight attribute and then returns this object. By returning itself set
     * statements can be chained together.
     *
     * @param streetLight the new Value to be set
     * @return the calling object
     */
    public CrashFromDatabase setStreetLight(String streetLight) {
        this.streetLight = streetLight;
        return this;
    }

    /**
     * returns a string representation of this object, particularly useful if printed for debugging
     *
     * @return A string representation of this object
     */
    @Override
    public String toString() {
        return "CrashDataPoint{"
                + "ID="
                + objectID
                + ", advisorySpeed="
                + advisorySpeed
                + ", bicycle="
                + bicycle
                + ", bridge="
                + bridge
                + ", bus="
                + bus
                + ", StationWagon="
                + carStationWagon
                + ", cliff="
                + cliffBank
                + ", crashDirection='"
                + crashDirectionDescription
                + '\''
                + ", financialYear='"
                + crashFinancialYear
                + '\''
                + ", crashLocationA='"
                + crashLocation1
                + '\''
                + ", crashLocationB='"
                + crashLocation2
                + ", crash side street"
                + crashRoadSideRoad
                + '\''
                + ", crashSeverity='"
                + crashSeverity
                + '\''
                + ", crashSHDescription='"
                + crashSHDescription
                + '\''
                + ", crashYear="
                + crashYear
                + ", debris="
                + debris
                + ", directionRoll='"
                + directionRoleDescription
                + '\''
                + ", ditch="
                + ditch
                + ", fatalCount="
                + fatalCount
                + ", fence="
                + fence
                + ", flatHill='"
                + flatHill
                + '\''
                + ", guardRail="
                + guardRail
                + ", holiday='"
                + holiday
                + '\''
                + ", building="
                + houseOrBuilding
                + ", intersection="
                + intersection
                + ", kerb="
                + kerb
                + ", light='"
                + light
                + '\''
                + ", minorInjury="
                + minorInjuryCount
                + ", moped="
                + moped
                + ", motorcycle="
                + motorcycle
                + ", numberOfLanes="
                + NumberOfLanes
                + ", thrownObject="
                + objectThrownOrDropped
                + ", otherObject="
                + otherObject
                + ", otherVehicles="
                + otherVehicleType
                + ", overBank="
                + overBank
                + ", parkedVehicle="
                + parkedVehicle
                + ", pedestrian="
                + pedestrian
                + ", phoneBox="
                + phoneBoxEtc
                + ", post="
                + postOrPole
                + ", region='"
                + region
                + '\''
                + ", roadCharacter='"
                + roadCharacter
                + '\''
                + ", roadLane='"
                + roadLane
                + '\''
                + ", roadSurface='"
                + roadSurface
                + '\''
                + ", roadWorks="
                + roadworks
                + ", schoolBusses="
                + schoolBus
                + ", seriousInjuries="
                + seriousInjuryCount
                + ", slipFlood="
                + slipOrFlood
                + ", speedLimit="
                + speedLimit
                + ", suv="
                + suv
                + ", stray Animals="
                + strayAnimal
                + ", street light status="
                + streetLight
                + ", taxi="
                + taxi
                + ", tempSpeedLimit="
                + temporarySpeedLimit
                + ", tlaName='"
                + tlaName
                + '\''
                + ", trafficControl='"
                + trafficControl
                + '\''
                + ", trafficIsland="
                + trafficIsland
                + ", trafficSignal="
                + trafficSign
                + ", train="
                + train
                + ", tree="
                + tree
                + ", truck="
                + truck
                + ", unknownVehicle="
                + unknownVehicleType
                + ", urban='"
                + urban
                + '\''
                + ", vanUtility="
                + vanOrUtility
                + ", vehicle="
                + vehicle
                + ", flowingWater="
                + waterRiver
                + ", weatherA='"
                + weatherA
                + '\''
                + ", weatherB='"
                + weatherB
                + '\''
                + ", latitude="
                + lat
                + ", longitude="
                + lng
                + '}';
    }
}
