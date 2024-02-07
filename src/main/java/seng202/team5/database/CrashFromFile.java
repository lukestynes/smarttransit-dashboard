package seng202.team5.database;

/**
 * <p style="font-size:110%;">Object that holds an individual Crash with string types:</p>
 *
 * <p> used for passing around crash data especially from a csv file to the database, all variables in this object
 * are stored as their original types to allow for computations to be performed on them</p>
 *
 * @author Dominic Gorny
 */
public class CrashFromFile {
    /** A records ID * */
    private Integer objectID = null;
    /** The advised Speed * */
    private Integer advisorySpeed = null;
    /** Number of bicycles in crash* */
    private Integer bicycle = null;
    /** Number of bridges in crash* */
    private Integer bridge = null;
    /** Number of busses in crash* */
    private Integer bus = null;
    /** Number of station wagons in crash* */
    private Integer carStationWagon = null;
    /** Number of cliffs or ledges in crash* */
    private Integer cliffBank = null;
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
    private Integer crashYear = null;
    /** Number of crash Debris* */
    private Integer debris = null;
    /** Direction crash members rolled* */
    private String directionRoleDescription = null;
    /** Number of ditches involved* */
    private Integer ditch = null;
    /** Number of Fatalities* */
    private Integer fatalCount = null;
    /** Number of Fences* */
    private Integer fence = null;
    /** Types of hills involved, flat if none* */
    private String flatHill = null;
    /** Number of Guard Rails involved* */
    private Integer guardRail = null;
    /** Holiday currently active if any* */
    private String holiday = null;
    /** Number of Buildings involved* */
    private Integer houseOrBuilding = null;
    /** Number of intersections involved* */
    private String intersection = null;
    /** Number of Kerbs involved* */
    private Integer kerb = null;
    /** Amount of light at time of crash* */
    private String light = null;
    /** Number of minor injuries in crash* */
    private Integer minorInjuryCount = null;
    /** Number of mopeds in crash* */
    private Integer moped = null;
    /** Number of motorcycles in crash* */
    private Integer motorcycle = null;
    /** Number of lanes on road* */
    private Integer NumberOfLanes = null;
    /** Number of Thrown or dropped objects* */
    private Integer objectThrownOrDropped = null;
    /** Number of other objects* */
    private Integer otherObject = null;
    /** Number of other Vehicles* */
    private Integer otherVehicleType = null;
    /** Number of Banks* */
    private Integer overBank = null;
    /** Number of Parked Vehicles* */
    private Integer parkedVehicle = null;
    /** Number of Pedestrians* */
    private Integer pedestrian = null;
    /** Number og phone boxes etc* */
    private Integer phoneBoxEtc = null;
    /** Number of post,poles or similar* */
    private Integer postOrPole = null;
    /** Crash region* */
    private String region = null;
    /** Road character (e.g highway ramp)* */
    private String roadCharacter = null;
    /** Road lane (e.g 1 way or 2 way...)* */
    private String roadLane = null;
    /** Road Surface Type* */
    private String roadSurface = null;
    /** Number of Roadworks* */
    private Integer roadworks = null;
    /** Number of School buses* */
    private Integer schoolBus = null;
    /** Number of serious injuries* */
    private Integer seriousInjuryCount = null;
    /** Number of slips of floods* */
    private Integer slipOrFlood = null;
    /** Legal Speed limit* */
    private Integer speedLimit = null;
    /** Number of SUVs in collision* */
    private Integer strayAnimal = null;

    private String streetLight = null;
    private Integer suv = null;
    /** Number of Taxis* */
    private Integer taxi = null;
    /** Temporary speed limit if applicable* */
    private Integer temporarySpeedLimit = null;
    /** tla region name* */
    private String tlaName = null;
    /** type of traffic control e.g. intersection* */
    private String trafficControl = null;
    /** Number of Traffic Islands* */
    private Integer trafficIsland = null;
    /** Number of Traffic signals* */
    private Integer trafficSign = null;
    /** Number of Trains* */
    private Integer train = null;
    /** Number of trees* */
    private Integer tree = null;
    /** Number of Trucks* */
    private Integer truck = null;
    /** Number of unknown vehicles* */
    private Integer unknownVehicleType = null;
    /** if the crash is in an urban area* */
    private String urban = null;
    /** Number of vans or utility vehicles* */
    private Integer vanOrUtility = null;
    /** Number of vehicles* */
    private Integer vehicle = null;
    /** Number of Bodies of water involved* */
    private Integer waterRiver = null;
    /** Weather A* */
    private String weatherA = null;
    /** Weather B* */
    private String weatherB = null;
    /** Crash Latitude* */
    private double lat = 0.0;
    /** Crash Longitude* */
    private double lng = 0.0;

    /**
     * A constructor to create blank object where all fields are populated with default values
     */
    public CrashFromFile() {}

    /**
     * creates a CrashFromFile Object populated with data. All data recorded in this one object should
     * correspond to information about a single crash
     * @param objectID The crash ID
     * @param advisorySpeed the advised speed at time of crash
     * @param bicycle number of bicycles in crash
     * @param bridge how many times a bridge, tunnel, the abutments, and/or handrails were struc in the crash
     * @param bus how many buses were involved in the crash.  Excludes school buses.
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
     * @param debris how many times debris, boulders, or items dropped or thrown vehicles were struck in crash
     * @param directionRoll The direction of the principal vehicle involved in the crash.
     * @param ditch The direction of the principal vehicle involved in the crash.
     * @param fatalCount number of fatalities
     * @param fence how many times a fence was struck in the crash.
     * @param flatHill Whether the road is flat or sloped
     * @param guardRail how many times a guard or guard rail was struck in the crash
     * @param holiday what holiday, if any, the crash occurred on.
     * @param houseOrBuilding indicate how many times houses, garages, sheds, or other buildings were struck in the crash.
     * @param intersection (No data) <sub>this is a dead value never assigned in the dataset but kept for integrity
     *                     when loading data from a csv source</sub>
     * @param kerb many times a kerb was struck in the crash
     * @param light The light at the time and place of the crash.
     * @param minorInjuryCount number of minor injuries associated with the crash.
     * @param moped how many mopeds were involved in the crash.
     * @param motorcycle how many motorcycles were involved in the crash.
     * @param numberOfLanes The number of lanes on the crash road
     * @param objectThrownOrDropped how many times objects were thrown at or dropped on vehicles in the crash.
     * @param otherObject how many times an object was struck in a crash and the object struck was not pre-defined.
     * @param otherVehicle how many other vehicles (not include in any other category) were involved in the crash
     * @param overBank how many times an embankment was struck or driven over during a crash.
     * @param parkedVehicle how many times a parked or unattended vehicle was struck in the crash.
     * @param pedestrian how many pedestrians were involved in the crash.
     * @param phoneBoxEtc how many times telephone kiosks, traffic signal controllers, bus shelters, or other public
     *                    furniture was struck in the crash
     * @param postOrPole how many times a post/pole was struck in the crash.
     * @param region Identifies the local government region in which the crash occurred
     * @param roadCharacter The general nature of the road.
     * @param roadLane The lane configuration of the road.
     * @param roadSurface The road markings at the crash site.
     * @param roadworks how many times an object associated with 'roadworks' was struck in the crash. excludes vehicles
     * @param schoolBus how many school buses were involved in the crash.
     * @param seriousInjuryCount the number of serious injuries associated with the crash.
     * @param slipOrFlood how many times landslips, washouts, or floods (excluding rivers) were objects struck in the crash.
     * @param speedLimit The speed limit in force at the crash site at the time of the crash.
     * @param strayAnimals how many times stray animals were struck in the crash.
     * @param streetLight The status of streetlights at the time of crash
     * @param suv how many SUVs were involved in the crash.
     * @param taxi indicate how many taxis were involved in the crash.
     * @param temporarySpeedLimit temporary speed limit at the crash site if one exists.
     * @param tlaName name of the Territorial Local Authority the crash has been attributed.
     * @param trafficControl The traffic control signals at the crash site.
     * @param trafficIsland how many times a traffic island, medians (excluding barriers) were hit in the crash
     * @param trafficSign indicate how many times traffic signage was hit in the crash
     * @param train how many times a train, rolling stock, or jiggers was struck in the crash
     * @param tree how many times trees or other growing items were struck during the crash.
     * @param truck how many trucks were involved in the crash.
     * @param unknownVehicleType how many vehicles were involved in the crash where the vehicle type was unknown
     * @param urban Derived from the Speed Limit variable. Values are Urban (if &lt; 80) and Open Road (if LSZ or &gt; 79).
     * @param vanOrUtility how many vans or utes were involved in the crash.
     * @param vehicle how many times a stationary attended vehicle was struck in the crash.
     * @param waterRiver how many times a body of water was hit in the crash
     * @param weatherA Indicates weather at the crash time/place.
     * @param weatherB Indicates weather at the crash time/place.
     * @param lat Indicates the latitude coordinate of the crash in degrees.
     * @param lng Indicates the longitude coordinate of the crash in degrees.
     */
    public CrashFromFile(
            Integer objectID,
            Integer advisorySpeed,
            Integer bicycle,
            Integer bridge,
            Integer bus,
            Integer carStationWagon,
            Integer cliffBank,
            String crashDirectionDescription,
            String crashFinancialYear,
            String crashLocation1,
            String crashLocation2,
            String sideRoadName,
            String crashSeverity,
            String crashSHDescription,
            Integer crashYear,
            Integer debris,
            String directionRoll,
            Integer ditch,
            Integer fatalCount,
            Integer fence,
            String flatHill,
            Integer guardRail,
            String holiday,
            Integer houseOrBuilding,
            String intersection,
            Integer kerb,
            String light,
            Integer minorInjuryCount,
            Integer moped,
            Integer motorcycle,
            Integer numberOfLanes,
            Integer objectThrownOrDropped,
            Integer otherObject,
            Integer otherVehicle,
            Integer overBank,
            Integer parkedVehicle,
            Integer pedestrian,
            Integer phoneBoxEtc,
            Integer postOrPole,
            String region,
            String roadCharacter,
            String roadLane,
            String roadSurface,
            Integer roadworks,
            Integer schoolBus,
            Integer seriousInjuryCount,
            Integer slipOrFlood,
            Integer speedLimit,
            Integer strayAnimals,
            String streetLight,
            Integer suv,
            Integer taxi,
            Integer temporarySpeedLimit,
            String tlaName,
            String trafficControl,
            Integer trafficIsland,
            Integer trafficSign,
            Integer train,
            Integer tree,
            Integer truck,
            Integer unknownVehicleType,
            String urban,
            Integer vanOrUtility,
            Integer vehicle,
            Integer waterRiver,
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
     * gets this objects crashRoadSideRoad attribute
     * @return this objects crashRoadSideRoad attribute
     */
    public String getCrashRoadSideRoad() {
        return crashRoadSideRoad;
    }

    /**
     * sets this objects crashRoadSideRoad parameter and then returns this object. By returning itself
     * set statements can be chained together.
     * @param crashRoadSideRoad the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setCrashRoadSideRoad(String crashRoadSideRoad) {
        this.crashRoadSideRoad = crashRoadSideRoad;
        return this;
    }

    /**
     * gets this objects objectID attribute
     * @return this objects objectID attribute
     */
    public Integer getObjectID() {
        return objectID;
    }

    /**
     * sets this objects objectID attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param objectID the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setObjectID(Integer objectID) {
        this.objectID = objectID;
        return this;
    }

    /**
     * gets this objects advisorySpeed attribute
     * @return this objects advisorySpeed attribute
     */
    public Integer getAdvisorySpeed() {
        return advisorySpeed;
    }

    /**
     * sets this objects advisorySpeed attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param advisorySpeed the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setAdvisorySpeed(Integer advisorySpeed) {
        this.advisorySpeed = advisorySpeed;
        return this;
    }

    /**
     * gets this objects bicycle attribute
     * @return this objects bicycle attribute
     */
    public Integer getBicycle() {
        return bicycle;
    }

    /**
     * sets this objects bicycle attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param bicycle the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setBicycle(Integer bicycle) {
        this.bicycle = bicycle;
        return this;
    }

    /**
     * gets this objects bridge attribute
     * @return this objects bridge attribute
     */
    public Integer getBridge() {
        return bridge;
    }

    /**
     * sets this objects bridge attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param bridge the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setBridge(Integer bridge) {
        this.bridge = bridge;
        return this;
    }

    /**
     * gets this objects bus attribute
     * @return this objects bus attribute
     */
    public Integer getBus() {
        return bus;
    }

    /**
     * sets this objects bus attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param bus the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setBus(Integer bus) {
        this.bus = bus;
        return this;
    }

    /**
     * gets this objects carStationWagon attribute
     * @return this objects carStationWagon attribute
     */
    public Integer getCarStationWagon() {
        return carStationWagon;
    }

    /**
     * sets this objects carStationWagon attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param carStationWagon the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setCarStationWagon(Integer carStationWagon) {
        this.carStationWagon = carStationWagon;
        return this;
    }

    /**
     * gets this objects cliffBank attribute
     * @return this objects cliffBank attribute
     */
    public Integer getCliffBank() {
        return cliffBank;
    }

    /**
     * sets this objects cliffBank attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param cliffBank the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setCliffBank(Integer cliffBank) {
        this.cliffBank = cliffBank;
        return this;
    }

    /**
     * gets this objects crashDirectionDescription attribute
     * @return this objects crashDirectionDescription attribute
     */
    public String getCrashDirectionDescription() {
        return crashDirectionDescription;
    }

    /**
     * sets this objects crashDirectionDescription attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param crashDirectionDescription the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setCrashDirectionDescription(String crashDirectionDescription) {
        this.crashDirectionDescription = crashDirectionDescription;
        return this;
    }

    /**
     * gets this objects crashFinancialYear attribute
     * @return this objects crashFinancialYear attribute
     */
    public String getCrashFinancialYear() {
        return crashFinancialYear;
    }

    /**
     * sets this objects crashFinancialYear attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param crashFinancialYear the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setCrashFinancialYear(String crashFinancialYear) {
        this.crashFinancialYear = crashFinancialYear;
        return this;
    }

    /**
     * gets this objects crashLocation1 attribute
     * @return this objects crashLocation1 attribute
     */
    public String getCrashLocation1() {
        return crashLocation1;
    }

    /**
     * sets this objects crashLocation1 attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param crashLocation1 the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setCrashLocation1(String crashLocation1) {
        this.crashLocation1 = crashLocation1;
        return this;
    }

    /**
     * gets this objects crashLocation2 attribute
     * @return this objects crashLocation2 attribute
     */
    public String getCrashLocation2() {
        return crashLocation2;
    }

    /**
     * sets this objects crashLocation2 attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param crashLocation2 the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setCrashLocation2(String crashLocation2) {
        this.crashLocation2 = crashLocation2;
        return this;
    }

    /**
     * gets this objects crashSeverity attribute
     * @return this objects crashSeverity attribute
     */
    public String getCrashSeverity() {
        return crashSeverity;
    }

    /**
     * sets this objects crashSeverity attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param crashSeverity the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setCrashSeverity(String crashSeverity) {
        this.crashSeverity = crashSeverity;
        return this;
    }

    /**
     * gets this objects crashSHDescription attribute
     * @return this objects crashSHDescription attribute
     */
    public String getCrashSHDescription() {
        return crashSHDescription;
    }

    /**
     * sets this objects crashSHDescription attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param crashSHDescription the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setCrashSHDescription(String crashSHDescription) {
        this.crashSHDescription = crashSHDescription;
        return this;
    }

    /**
     * gets this objects crashYear attribute
     * @return this objects crashYear attribute
     */
    public Integer getCrashYear() {
        return crashYear;
    }

    /**
     * sets this objects crashYear attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param crashYear the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setCrashYear(Integer crashYear) {
        this.crashYear = crashYear;
        return this;
    }

    /**
     * gets this objects debris attribute
     * @return this objects debris attribute
     */
    public Integer getDebris() {
        return debris;
    }

    /**
     * sets this objects debris attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param debris the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setDebris(Integer debris) {
        this.debris = debris;
        return this;
    }

    /**
     * gets this objects directionRoleDescription attribute
     * @return this objects directionRoleDescription attribute
     */
    public String getDirectionRoleDescription() {
        return directionRoleDescription;
    }

    /**
     * sets this objects directionRoleDescription attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param directionRoleDescription the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setDirectionRoleDescription(String directionRoleDescription) {
        this.directionRoleDescription = directionRoleDescription;
        return this;
    }

    /**
     * gets this objects ditch attribute
     * @return this objects ditch attribute
     */
    public Integer getDitch() {
        return ditch;
    }

    /**
     * sets this objects ditch attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param ditch the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setDitch(Integer ditch) {
        this.ditch = ditch;
        return this;
    }

    /**
     * gets this objects fatalCount attribute
     * @return this objects fatalCount attribute
     */
    public Integer getFatalCount() {
        return fatalCount;
    }

    /**
     * sets this objects fatalCount attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param fatalCount the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setFatalCount(Integer fatalCount) {
        this.fatalCount = fatalCount;
        return this;
    }

    /**
     * gets this objects fence attribute
     * @return this objects fence attribute
     */
    public Integer getFence() {
        return fence;
    }

    /**
     * sets this objects fence attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param fence the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setFence(Integer fence) {
        this.fence = fence;
        return this;
    }

    /**
     * gets this objects flatHill attribute
     * @return this objects flatHill attribute
     */
    public String getFlatHill() {
        return flatHill;
    }

    /**
     * sets this objects flatHill attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param flatHill the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setFlatHill(String flatHill) {
        this.flatHill = flatHill;
        return this;
    }

    /**
     * gets this objects guardRail attribute
     * @return this objects guardRail attribute
     */
    public Integer getGuardRail() {
        return guardRail;
    }

    /**
     * sets this objects guardRail attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param guardRail the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setGuardRail(Integer guardRail) {
        this.guardRail = guardRail;
        return this;
    }

    /**
     * gets this objects holiday attribute
     * @return this objects holiday attribute
     */
    public String getHoliday() {
        return holiday;
    }

    /**
     * sets this objects holiday attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param holiday the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setHoliday(String holiday) {
        this.holiday = holiday;
        return this;
    }

    /**
     * gets this objects houseOrBuilding attribute
     * @return this objects houseOrBuilding attribute
     */
    public Integer getHouseOrBuilding() {
        return houseOrBuilding;
    }

    /**
     * sets this objects houseOrBuilding attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param houseOrBuilding the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setHouseOrBuilding(Integer houseOrBuilding) {
        this.houseOrBuilding = houseOrBuilding;
        return this;
    }

    /**
     * gets this objects intersection attribute
     * @return this objects intersection attribute
     */
    public String getIntersection() {
        return intersection;
    }

    /**
     * sets this objects intersection attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param intersection the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setIntersection(String intersection) {
        this.intersection = intersection;
        return this;
    }

    /**
     * gets this objects kerb attribute
     * @return this objects kerb attribute
     */
    public Integer getKerb() {
        return kerb;
    }

    /**
     * sets this objects kerb attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param kerb the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setKerb(Integer kerb) {
        this.kerb = kerb;
        return this;
    }

    /**
     * gets this objects light attribute
     * @return this objects light attribute
     */
    public String getLight() {
        return light;
    }

    /**
     * sets this objects light attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param light the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setLight(String light) {
        this.light = light;
        return this;
    }

    /**
     * gets this objects minorInjuryCount attribute
     * @return this objects minorInjuryCount attribute
     */
    public Integer getMinorInjuryCount() {
        return minorInjuryCount;
    }

    /**
     * sets this objects minorInjuryCount attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param minorInjuryCount the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setMinorInjuryCount(Integer minorInjuryCount) {
        this.minorInjuryCount = minorInjuryCount;
        return this;
    }

    /**
     * gets this objects moped attribute
     * @return this objects moped attribute
     */
    public Integer getMoped() {
        return moped;
    }

    /**
     * sets this objects moped attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param moped the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setMoped(Integer moped) {
        this.moped = moped;
        return this;
    }

    /**
     * gets this objects motorcycle attribute
     * @return this objects motorcycle attribute
     */
    public Integer getMotorcycle() {
        return motorcycle;
    }

    /**
     * sets this objects motorcycle attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param motorcycle the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setMotorcycle(Integer motorcycle) {
        this.motorcycle = motorcycle;
        return this;
    }

    /**
     * gets this objects NumberOfLanes attribute
     * @return this objects NumberOfLanes attribute
     */
    public Integer getNumberOfLanes() {
        return NumberOfLanes;
    }

    /**
     * sets this objects NumberOfLanes attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param NumberOfLanes the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setNumberOfLanes(Integer NumberOfLanes) {
        this.NumberOfLanes = NumberOfLanes;
        return this;
    }

    /**
     * gets this objects objectThrownOrDropped attribute
     * @return this objects objectThrownOrDropped attribute
     */
    public Integer getObjectThrownOrDropped() {
        return objectThrownOrDropped;
    }

    /**
     * sets this objects objectThrownOrDropped attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param objectThrownOrDropped the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setObjectThrownOrDropped(Integer objectThrownOrDropped) {
        this.objectThrownOrDropped = objectThrownOrDropped;
        return this;
    }

    /**
     * gets this objects otherObject attribute
     * @return this objects otherObject attribute
     */
    public Integer getOtherObject() {
        return otherObject;
    }

    /**
     * sets this objects otherObject attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param otherObject the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setOtherObject(Integer otherObject) {
        this.otherObject = otherObject;
        return this;
    }

    /**
     * gets this objects otherVehicleType attribute
     * @return this objects otherVehicleType attribute
     */
    public Integer getOtherVehicleType() {
        return otherVehicleType;
    }

    /**
     * sets this objects otherVehicleType attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param otherVehicleType the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setOtherVehicleType(Integer otherVehicleType) {
        this.otherVehicleType = otherVehicleType;
        return this;
    }

    /**
     * gets this objects overBank attribute
     * @return this objects overBank attribute
     */
    public Integer getOverBank() {
        return overBank;
    }

    /**
     * sets this objects overBank attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param overBank the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setOverBank(Integer overBank) {
        this.overBank = overBank;
        return this;
    }

    /**
     * gets this objects parkedVehicle attribute
     * @return this objects parkedVehicle attribute
     */
    public Integer getParkedVehicle() {
        return parkedVehicle;
    }

    /**
     * sets this objects parkedVehicle attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param parkedVehicle the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setParkedVehicle(Integer parkedVehicle) {
        this.parkedVehicle = parkedVehicle;
        return this;
    }

    /**
     * gets this objects pedestrian attribute
     * @return this objects pedestrian attribute
     */
    public Integer getPedestrian() {
        return pedestrian;
    }

    /**
     * sets this objects pedestrian attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param pedestrian the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setPedestrian(Integer pedestrian) {
        this.pedestrian = pedestrian;
        return this;
    }

    /**
     * gets this objects phoneBoxEtc attribute
     * @return this objects phoneBoxEtc attribute
     */
    public Integer getPhoneBoxEtc() {
        return phoneBoxEtc;
    }

    /**
     * sets this objects phoneBoxEtc attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param phoneBoxEtc the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setPhoneBoxEtc(Integer phoneBoxEtc) {
        this.phoneBoxEtc = phoneBoxEtc;
        return this;
    }

    /**
     * gets this objects postOrPole attribute
     * @return this objects postOrPole attribute
     */
    public Integer getPostOrPole() {
        return postOrPole;
    }

    /**
     * sets this objects postOrPole attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param postOrPole the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setPostOrPole(Integer postOrPole) {
        this.postOrPole = postOrPole;
        return this;
    }

    /**
     * gets this objects region attribute
     * @return this objects region attribute
     */
    public String getRegion() {
        return region;
    }

    /**
     * sets this objects region attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param region the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setRegion(String region) {
        this.region = region;
        return this;
    }

    /**
     * gets this objects roadCharacter attribute
     * @return this objects roadCharacter attribute
     */
    public String getRoadCharacter() {
        return roadCharacter;
    }

    /**
     * sets this objects roadCharacter attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param roadCharacter the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setRoadCharacter(String roadCharacter) {
        this.roadCharacter = roadCharacter;
        return this;
    }

    /**
     * gets this objects roadLane attribute
     * @return this objects roadLane attribute
     */
    public String getRoadLane() {
        return roadLane;
    }

    /**
     * sets this objects roadLane attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param roadLane the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setRoadLane(String roadLane) {
        this.roadLane = roadLane;
        return this;
    }

    /**
     * gets this objects roadSurface attribute
     * @return this objects roadSurface attribute
     */
    public String getRoadSurface() {
        return roadSurface;
    }

    /**
     * sets this objects roadSurface attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param roadSurface the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setRoadSurface(String roadSurface) {
        this.roadSurface = roadSurface;
        return this;
    }

    /**
     * gets this objects roadworks attribute
     * @return this objects roadworks attribute
     */
    public Integer getRoadworks() {
        return roadworks;
    }

    /**
     * sets this objects roadworks attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param roadworks the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setRoadworks(Integer roadworks) {
        this.roadworks = roadworks;
        return this;
    }

    /**
     * gets this objects schoolBus attribute
     * @return this objects schoolBus attribute
     */
    public Integer getSchoolBus() {
        return schoolBus;
    }

    /**
     * sets this objects schoolBus attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param schoolBus the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setSchoolBus(Integer schoolBus) {
        this.schoolBus = schoolBus;
        return this;
    }

    /**
     * gets this objects seriousInjuryCount attribute
     * @return this objects seriousInjuryCount attribute
     */
    public Integer getSeriousInjuryCount() {
        return seriousInjuryCount;
    }

    /**
     * sets this objects seriousInjuryCount attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param seriousInjuryCount the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setSeriousInjuryCount(Integer seriousInjuryCount) {
        this.seriousInjuryCount = seriousInjuryCount;
        return this;
    }

    /**
     * gets this objects slipOrFlood attribute
     * @return this objects slipOrFlood attribute
     */
    public Integer getSlipOrFlood() {
        return slipOrFlood;
    }

    /**
     * sets this objects slipOrFlood attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param slipOrFlood the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setSlipOrFlood(Integer slipOrFlood) {
        this.slipOrFlood = slipOrFlood;
        return this;
    }

    /**
     * gets this objects speedLimit attribute
     * @return this objects speedLimit attribute
     */
    public Integer getSpeedLimit() {
        return speedLimit;
    }

    /**
     * sets this objects speedLimit attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param speedLimit the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setSpeedLimit(Integer speedLimit) {
        this.speedLimit = speedLimit;
        return this;
    }

    /**
     * gets this objects suv attribute
     * @return this objects suv attribute
     */
    public Integer getSuv() {
        return suv;
    }

    /**
     * sets this objects suv attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param suv the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setSuv(Integer suv) {
        this.suv = suv;
        return this;
    }

    /**
     * gets this objects taxi attribute
     * @return this objects taxi attribute
     */
    public Integer getTaxi() {
        return taxi;
    }

    /**
     * sets this objects taxi attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param taxi the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setTaxi(Integer taxi) {
        this.taxi = taxi;
        return this;
    }

    /**
     * gets this objects temporarySpeedLimit attribute
     * @return this objects temporarySpeedLimit attribute
     */
    public Integer getTemporarySpeedLimit() {
        return temporarySpeedLimit;
    }

    /**
     * sets this objects temporarySpeedLimit attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param temporarySpeedLimit the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setTemporarySpeedLimit(Integer temporarySpeedLimit) {
        this.temporarySpeedLimit = temporarySpeedLimit;
        return this;
    }

    /**
     * gets this objects tlaName attribute
     * @return this objects tlaName attribute
     */
    public String getTlaName() {
        return tlaName;
    }

    /**
     * sets this objects tlaName attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param tlaName the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setTlaName(String tlaName) {
        this.tlaName = tlaName;
        return this;
    }

    /**
     * gets this objects trafficControl attribute
     * @return this objects trafficControl attribute
     */
    public String getTrafficControl() {
        return trafficControl;
    }

    /**
     * sets this objects trafficControl attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param trafficControl the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setTrafficControl(String trafficControl) {
        this.trafficControl = trafficControl;
        return this;
    }

    /**
     * gets this objects trafficIsland attribute
     * @return this objects trafficIsland attribute
     */
    public Integer getTrafficIsland() {
        return trafficIsland;
    }

    /**
     * sets this objects trafficIsland attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param trafficIsland the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setTrafficIsland(Integer trafficIsland) {
        this.trafficIsland = trafficIsland;
        return this;
    }

    /**
     * gets this objects trafficSign attribute
     * @return this objects trafficSign attribute
     */
    public Integer getTrafficSign() {
        return trafficSign;
    }

    /**
     * sets this objects trafficSign attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param trafficSign the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setTrafficSign(Integer trafficSign) {
        this.trafficSign = trafficSign;
        return this;
    }

    /**
     * gets this objects train attribute
     * @return this objects train attribute
     */
    public Integer getTrain() {
        return train;
    }

    /**
     * sets this objects train attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param train the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setTrain(Integer train) {
        this.train = train;
        return this;
    }

    /**
     * gets this objects tree attribute
     * @return this objects tree attribute
     */
    public Integer getTree() {
        return tree;
    }

    /**
     * sets this objects tree attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param tree the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setTree(Integer tree) {
        this.tree = tree;
        return this;
    }

    /**
     * gets this objects truck attribute
     * @return this objects truck attribute
     */
    public Integer getTruck() {
        return truck;
    }

    /**
     * sets this objects truck attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param truck the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setTruck(Integer truck) {
        this.truck = truck;
        return this;
    }

    /**
     * gets this objects unknownVehicleType attribute
     * @return this objects unknownVehicleType attribute
     */
    public Integer getUnknownVehicleType() {
        return unknownVehicleType;
    }

    /**
     * sets this objects unknownVehicleType attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param unknownVehicleType the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setUnknownVehicleType(Integer unknownVehicleType) {
        this.unknownVehicleType = unknownVehicleType;
        return this;
    }

    /**
     * gets this objects urban attribute
     * @return this objects urban attribute
     */
    public String getUrban() {
        return urban;
    }

    /**
     * sets this objects urban attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param urban the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setUrban(String urban) {
        this.urban = urban;
        return this;
    }

    /**
     * gets this objects vanOrUtility attribute
     * @return this objects vanOrUtility attribute
     */
    public Integer getVanOrUtility() {
        return vanOrUtility;
    }

    /**
     * sets this objects vanOrUtility attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param vanOrUtility the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setVanOrUtility(Integer vanOrUtility) {
        this.vanOrUtility = vanOrUtility;
        return this;
    }

    /**
     * gets this objects vehicle attribute
     * @return this objects vehicle attribute
     */
    public Integer getVehicle() {
        return vehicle;
    }

    /**
     * sets this objects vehicle attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param vehicle the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setVehicle(Integer vehicle) {
        this.vehicle = vehicle;
        return this;
    }

    /**
     * gets this objects waterRiver attribute
     * @return this objects waterRiver attribute
     */
    public Integer getWaterRiver() {
        return waterRiver;
    }

    /**
     * sets this objects waterRiver attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param waterRiver the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setWaterRiver(Integer waterRiver) {
        this.waterRiver = waterRiver;
        return this;
    }

    /**
     * gets this objects weatherA attribute
     * @return this objects weatherA attribute
     */
    public String getWeatherA() {
        return weatherA;
    }

    /**
     * sets this objects weatherA attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param weatherA the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setWeatherA(String weatherA) {
        this.weatherA = weatherA;
        return this;
    }

    /**
     * gets this objects weatherB attribute
     * @return this objects weatherB attribute
     */
    public String getWeatherB() {
        return weatherB;
    }

    /**
     * sets this objects weatherB attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param weatherB the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setWeatherB(String weatherB) {
        this.weatherB = weatherB;
        return this;
    }

    /**
     * gets this objects lat attribute
     * @return this objects lat attribute
     */
    public double getLat() {
        return lat;
    }

    /**
     * sets this objects lat attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param lat the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setLat(double lat) {
        this.lat = lat;
        return this;
    }

    /**
     * gets this objects lng attribute
     * @return this objects lng attribute
     */
    public double getLng() {
        return lng;
    }

    /**
     * sets this objects lng attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param lng the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setLng(double lng) {
        this.lng = lng;
        return this;
    }

    /**
     * gets this objects strayAnimal attribute
     * @return this objects strayAnimal attribute
     */
    public Integer getStrayAnimal() {
        return strayAnimal;
    }

    /**
     * sets this objects strayAnimal attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param strayAnimal the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setStrayAnimal(Integer strayAnimal) {
        this.strayAnimal = strayAnimal;
        return this;
    }

    /**
     * gets this objects streetLight attribute
     * @return this objects streetLight attribute
     */
    public String getStreetLight() {
        return streetLight;
    }

    /**
     * sets this objects streetLight attribute and then returns this object. By returning itself
     * set statements can be chained together.
     * @param streetLight the new Value to be set
     * @return the calling object
     */
    public CrashFromFile setStreetLight(String streetLight) {
        this.streetLight = streetLight;
        return this;
    }

    /**
     * returns a string representation of this object, particularly useful if printed for debugging
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
