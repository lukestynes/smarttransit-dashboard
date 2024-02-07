package seng202.team5.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import seng202.team5.gui.NavigationController;

import java.util.*;

/**
 * Class for storing the selected keywords and filters that define a search by the user. these can then be passed to the
 * database layer and turned into sql queries there.
 * @author Isla Smyth
 * @author Dominic Gorny
 */
public class Search {
    private String table = null;
    private static final Logger log = LogManager.getLogger(Search.class);
    //Map location values for searching within radius of location
    //If any map location values are null, do not search
    private Integer radius = null; //this value is given in kilometres
    private Double lat = null;
    private Double lng = null;
    //Panel open/closed storage variables
    private boolean locationPaneOpen = true;
    private boolean basicPaneOpen = true;
    private boolean advancedPaneOpen = false;
    private boolean conditionalPaneOpen = false;
    //Pagination variables
    private int page = 0;
    private int pageSize = 30;
    //Sort variables (Default is ID ascending)
    private TableColumns.Column primarySort = TableColumns.Column.ID;
    private TableColumns.SortDirection primarySortDirection = TableColumns.SortDirection.ASCENDING;
    private TableColumns.Column secondarySort = TableColumns.Column.ID;
    private TableColumns.SortDirection secondarySortDirection = TableColumns.SortDirection.ASCENDING;

    //String arrays should match database format for easy SQL querying, not table display format
    //Basic Options below:
    private String keywords = null;
    private boolean matchAll = false;
    private Integer startYear = null;
    private Integer endYear = null;
    private HashSet<String> severities = new HashSet<String>();
    private HashSet<String> regions = new HashSet<String>();
    private HashSet<String> participants = new HashSet<String>(); //presence of strings in set represent boolean property where > 0 of that participant type exist
    private Boolean participantAnd = false;
    private Integer startSpeedLimit = null;
    private Integer endSpeedLimit = null;
    private HashSet<String> weatherA = new HashSet<String>();

    private HashSet<String> weatherB = new HashSet<String>();
    private HashSet<String> light = new HashSet<String>();
    private HashSet<String> holidays = new HashSet<String>();

    //Advanced Options below:
    private Integer startAdvisedSpeed = null;
    private Integer endAdvisedSpeed = null;
    private Integer startTemporarySpeed = null;
    private Integer endTemporarySpeed = null;
    private Integer startDeaths = null;
    private Integer endDeaths = null;
    private Integer startMinorInjuries = null;
    private Integer endMinorInjuries = null;
    private Integer startMajorInjuries = null;
    private Integer endMajorInjuries = null;
    private HashSet<String> localAuthorities = new HashSet<String>();
    private HashSet<String> trafficControls = new HashSet<String>();
    private HashSet<String> collisions = new HashSet<String>(); //presence of strings in set represent boolean property where > 0 of that collision type exist
    private Boolean collisionAnd = false;
    private HashSet<String> crashDirections = new HashSet<String>();
    private HashSet<String> vehicleDirections = new HashSet<String>();
    private Integer startLanes = null;
    private Integer endLanes = null;
    private HashSet<String> laneConfigs = new HashSet<String>();
    private HashSet<String> roadCharacters = new HashSet<String>();
    private HashSet<String> roadSurfaces = new HashSet<String>();
    private Boolean stateHighway = null;
    private Boolean hill = null;
    private Boolean streetLights = null;
    //HashMap of IDs to Conditional objects
    private HashMap<Integer, Conditional> conditionals = new HashMap<>();
    private NavigationController navigationController;
    private Timer searchTimer;

    public Search(NavigationController navigationController) {
        this.navigationController = navigationController;
    }

    /**
     * Reset the search object to its initial values. By default, all integers and strings are set to null and all
     * Hash sets are emptied.
     * Pagination and sorting values are not altered.
     */
    public void reset() {
        this.keywords = null;
        this.matchAll = false;
        this.startYear = null;
        this.endYear = null;
        this.severities = new HashSet<String>();
        this.regions = new HashSet<String>();
        this.participants = new HashSet<String>();
        this.startSpeedLimit = null;
        this.endSpeedLimit = null;
        this.weatherA = new HashSet<String>();
        this.weatherB = new HashSet<String>();
        this.light = new HashSet<String>();
        this.holidays = new HashSet<String>();
        this.participantAnd = false;
        this.collisionAnd = false;

        this.startAdvisedSpeed = null;
        this.endAdvisedSpeed = null;
        this.startTemporarySpeed = null;
        this.endTemporarySpeed = null;
        this.startDeaths = null;
        this.endDeaths = null;
        this.startMinorInjuries = null;
        this.endMinorInjuries = null;
        this.startMajorInjuries = null;
        this.endMajorInjuries = null;
        this.localAuthorities = new HashSet<String>();
        this.trafficControls = new HashSet<String>();
        this.collisions = new HashSet<String>();
        this.crashDirections = new HashSet<String>();
        this.vehicleDirections = new HashSet<String>();
        this.startLanes = null;
        this.endLanes = null;
        this.laneConfigs = new HashSet<String>();
        this.roadCharacters = new HashSet<String>();
        this.roadSurfaces = new HashSet<String>();
        this.stateHighway = null;
        this.hill = null;
        this.streetLights = null;
        this.conditionals = new HashMap<>();
    }

    /**
     * Call this function to update the search, provided it isn't called again within one second
     */
    public void handleSearchChange() {
        if (searchTimer != null) {
            searchTimer.cancel();
        }
        navigationController.toggleViewUpdating(true);
        searchTimer = new Timer();
        searchTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                navigationController.updateFromSearch();
                searchTimer.cancel();
            }
        }, 100);
    }

    public boolean getParticipantAnd() {
        return participantAnd;
    }

    public boolean getCollisionAnd() {
        return collisionAnd;
    }

    public void setCollisionAnd(boolean enabled) {
        this.collisionAnd = enabled;
    }

    public void setParticipantAnd(boolean enabled) {
        this.participantAnd = enabled;
    }

    /**
     * Generic function for adding values from a checkComboBox call to their corresponding sets.
     * @param valueType String representing the field to add to
     * @param value String value to add to the hashSet
     */
    public void addFromCheckComboBox(String valueType, String value) {
        if (valueType.equals("severity")) {
            String newValue = TableColumns.Severity.getCSVNameFromName(value);
            this.severities.add(newValue);
        } else if (valueType.equals("region")) {
            String newValue = TableColumns.Region.getCSVNameFromName(value);
            this.regions.add(newValue);
        } else if (valueType.equals("participants")) {
            this.participants.add(TableColumns.Participants.getByName(value).dbName);
        } else if (valueType.equals("weatherA")) {
            String newValue = TableColumns.WeatherA.getCSVNameFromName(value);
            this.weatherA.add(newValue);
        } else if (valueType.equals("weatherB")) {
            String newValue = TableColumns.WeatherB.getCSVNameFromName(value);
            this.weatherB.add(newValue);
        } else if (valueType.equals("light")) {
            this.light.add(value);
        } else if (valueType.equals("holiday")) {
            String newValue = TableColumns.Holiday.getCSVNameFromName(value);
            this.holidays.add(newValue);
        } else if (valueType.equals("tla")) {
            String newValue = TableColumns.LocalAuthority.getCSVNameFromName(value);
            this.localAuthorities.add(newValue);
        } else if (valueType.equals("trafficcontrol")) {
            String newValue = TableColumns.TrafficControl.getCSVNameFromName(value);
            this.trafficControls.add(newValue);
        } else if (valueType.equals("collisions")) {
            this.collisions.add(TableColumns.Collisions.getByName(value).dbName);
        } else if (valueType.equals("crashdirection")) {
            this.crashDirections.add(value);
        } else if (valueType.equals("vehicledirection")) {
            this.vehicleDirections.add(value);
        } else if (valueType.equals("laneconfig")) {
            String newValue = TableColumns.LaneConfiguration.getCSVNameFromName(value);
            this.laneConfigs.add(newValue);
        } else if (valueType.equals("roadcharacter")) {
            String newValue = TableColumns.RoadCharacter.getCSVNameFromName(value);
            this.roadCharacters.add(newValue);
        } else if (valueType.equals("roadsurface")) {
            this.roadSurfaces.add(value);
        }
    }

    /**
     * Generic function for removing values from sets when called from a checkComboBox
     * @param valueType String representing the field to remove from
     * @param value String value to remove from the set
     */
    public void removeFromCheckComboBox(String valueType, String value) {
        if (valueType.equals("severity")) {
            String newValue = TableColumns.Severity.getCSVNameFromName(value);
            this.severities.remove(newValue);
        } else if (valueType.equals("region")) {
            String newValue = TableColumns.Region.getCSVNameFromName(value);
            this.regions.remove(newValue);
        } else if (valueType.equals("participants")) {
            this.participants.remove(TableColumns.Participants.getByName(value).dbName);
        } else if (valueType.equals("weatherA")) {
            String newValue = TableColumns.WeatherA.getCSVNameFromName(value);
            this.weatherA.remove(newValue);
        } else if (valueType.equals("weatherB")) {
            String newValue = TableColumns.WeatherB.getCSVNameFromName(value);
            this.weatherB.remove(newValue);
        } else if (valueType.equals("light")) {
            this.light.remove(value);
        } else if (valueType.equals("holiday")) {
            String newValue = TableColumns.Holiday.getCSVNameFromName(value);
            this.holidays.remove(newValue);
        } else if (valueType.equals("tla")) {
            String newValue = TableColumns.LocalAuthority.getCSVNameFromName(value);
            this.localAuthorities.remove(newValue);
        } else if (valueType.equals("trafficcontrol")) {
            String newValue = TableColumns.TrafficControl.getCSVNameFromName(value);
            this.trafficControls.remove(newValue);
        } else if (valueType.equals("collisions")) {
            this.collisions.remove(TableColumns.Collisions.getByName(value).dbName);
        } else if (valueType.equals("crashdirection")) {
            this.crashDirections.remove(value);
        } else if (valueType.equals("vehicledirection")) {
            this.vehicleDirections.remove(value);
        } else if (valueType.equals("laneconfig")) {
            String newValue = TableColumns.LaneConfiguration.getCSVNameFromName(value);
            this.laneConfigs.remove(newValue);
        } else if (valueType.equals("roadcharacter")) {
            String newValue = TableColumns.RoadCharacter.getCSVNameFromName(value);
            this.roadCharacters.remove(newValue);
        } else if (valueType.equals("roadsurface")) {
            this.roadSurfaces.remove(value);
        }
    }

    /**
     * empty search constructor, builds a search object that will not put any constraints on the data returned
     * (excluding pagination)
     */
    public Search() {

    }

    /**
     * sets the match all attribute
     * @param matchAll the new value for matchAll
     */
    public void setMatchAll(boolean matchAll) {
        this.matchAll = matchAll;
    }

    /**
     * adds a conditional search object to this search
     * @param id the conditionals id
     * @param conditional the new Conditional to add
     */
    public void addConditional(Integer id, Conditional conditional) {
        this.conditionals.put(id, conditional);
    }

    /**
     * Remove a conditional from the conditionals map by its supplied ID.
     * @param id ID of the conditional to remove
     */
    public void removeConditional(Integer id) {
        this.conditionals.remove(id);
    }

    /**
     * Return a conditional object corresponding to the supplied ID.
     * Returns null if not found.
     * @param id ID of conditional to return
     * @return Conditional object
     */
    public Conditional getConditional(Integer id) {
        return conditionals.get(id);
    }

    /**
     * Gets all conditionals stored within this search object
     * @return all conditionals
     */
    public HashMap<Integer, Conditional> getConditionals() {return conditionals;}

    /**
     * sets the search keyword
     * Update the search only after one second of no further user input
     * @param keywords the new keyword
     */
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    /**
     * sets a minimum year all records in the result set should have
     * @param startYear minimum year number
     */
    public void setStartYear(Integer startYear) {
        this.startYear = startYear;
    }

    /**
    sets a maximum value all records in this result set should have
     @param endYear maximum year number
     */
    public void setEndYear(Integer endYear) {
        this.endYear = endYear;
    }

    /**
     * sets the minimum number of deaths all records in this result set should have
     * @param startDeaths minimum number of deaths
     */
    public void setStartDeaths(Integer startDeaths) {
        this.startDeaths = startDeaths;
    }

    /**
     * sets the maximum number of deaths each record in this result set should have
     * @param endDeaths maximum number of deaths
     */
    public void setEndDeaths(Integer endDeaths) {
        this.endDeaths = endDeaths;
    }

    /**
     * sets the minimum number of minor injuries all records in the result set should have
     * @param minorInjuries minimum number of minor injuries
     */
    public void setStartMinorInjuries(Integer minorInjuries) {
        this.startMinorInjuries = minorInjuries;
    }

    /**
     * sets the maximum number of minor injuries all records in the result set should have
     * @param minorInjuries maximum number of minor injuries
     */
    public void setEndMinorInjuries(Integer minorInjuries) {
        this.endMinorInjuries = minorInjuries;
    }

    /**
     * sets the minimum number of major injuries all records in the result set should have
     * @param majorInjuries minimum number of major injures
     */
    public void setStartMajorInjuries(Integer majorInjuries) {
        this.startMajorInjuries = majorInjuries;
    }

    /**
     * sets the maximum number of major injuries all records in the result set should have
     * @param majorInjuries maximum number of major injuries
     */
    public void setEndMajorInjuries(Integer majorInjuries) {
        this.endMajorInjuries = majorInjuries;
    }

    /**
     * Gets the minimum year all records in the result set should have
     * @return minimum year
     */
    public Integer getStartYear() {return startYear;}

    /**
     * Gets the maximum year all records in the result set should have
     * @return maximum year
     */
    public Integer getEndYear() {
        return endYear;
    }

    /**
     * sets the maximum speed limit all records in the result set should have
     * @param speed maximum speed limit
     */
    public void setEndLimit(Integer speed) {
        this.endSpeedLimit = speed;

    }

    /**
     * sets the maximum advised speed all records in the dataset should have
     * @param speed maximum advised speed
     */
    public void setAdvisedEndLimit(Integer speed) {
        this.endAdvisedSpeed = speed;
    }

    /**
     * sets the maximum temporary speed all records in the result set should have
     * @param speed maximum temporary speed
     */
    public void setTemporaryEndLimit(Integer speed) {
        this.endTemporarySpeed = speed;
    }

    /**
     * sets the maximum lane number all records in the result set should have
     * @param lanes maximum lane number
     */
    public void setEndLanes(Integer lanes) {
        this.endLanes = lanes;
    }

    /**
     * sets the minimum speed limit any record in the result set should have
     * @param speed minimum speed limit
     */
    public void setStartLimit(Integer speed) {
        this.startSpeedLimit = speed;
    }

    /**
     * sets the minimum advised speed all records in the result set should have
     * @param speed minimum speed
     */
    public void setAdvisedStartLimit(Integer speed) {
        this.startAdvisedSpeed = speed;
    }

    /**
     * sets the minimum temporary speed limit all records in the result set should have
     * @param speed minimum temprary speed limit.
     */
    public void setTemporaryStartLimit(Integer speed) {
        this.startTemporarySpeed = speed;
    }

    /**
     * sets the minimum number of lanes each record in the resul set should have
     * @param lanes minimum number of lanes
     */
    public void setStartLanes(Integer lanes) {
        this.startLanes = lanes;
    }

    /**
     * updates checkbox values
     * @param valueType the checkbox which has experienced a state change
     * @param newValue the new value in the aforementioned checkbox
     */
    public void setCheckBox(String valueType, Boolean newValue) {
        if (valueType.equals("sh")) {
            this.stateHighway = newValue;
        } else if (Objects.equals(valueType, "lights")) {
            this.streetLights = newValue;
        } else if (Objects.equals(valueType, "hill")) {
            this.hill = newValue;
        }
    }

    /**
     * Gets the search keyword all records should have
     * @return search keyword
     */
    public String getKeywords() {
        return keywords;
    }

    /**
     * returns the state of the match toggle
     * @return matchAll state
     */
    public boolean isMatchAll() {
        return matchAll;
    }

    /**
     * Gets the Hash set of Severities within which each records severity should be
     * @return hash set of severity
     */
    public HashSet<String> getSeverities() {
        return severities;
    }

    /**
     * Gets the hash set of regions within which each records region should be
     * @return hash set of regions
     */
    public HashSet<String> getRegions() {
        return regions;
    }

    /**
     * Gets the participants which should be in &gt;1 in each record
     * @return hash set of participants
     */
    public HashSet<String> getParticipants() {
        return participants;
    }

    /**
     * Gets the minimum speed limit which all records in the result set should have
     * @return minimum speed limit
     */
    public Integer getStartSpeedLimit() {
        return startSpeedLimit;
    }

    /**
     * Gets the maximum speed limit which all records in the result set should have
     * @return maximum speed limit.
     */
    public Integer getEndSpeedLimit() {
        return endSpeedLimit;
    }

    /**
     * Gets the hash set of weatherA that each crash record's weather condition should be a part of
     * @return hash set of Weather A conditions
     */
    public HashSet<String> getWeatherA() {
        return weatherA;
    }

    /**
     * Gets the hash set of weatherB that each crash record's weather condition should be a part of
     * @return hash set of Weather B conditions
     */
    public HashSet<String> getWeatherB() {
        return weatherB;
    }

    /**
     * Gets the hash set of light conditions that each crash record's light condition should be in
     * @return hash set of light conditions
     */
    public HashSet<String> getLight() {
        return light;
    }

    /**
     * Gets the hash set of holidays within each record's holiday status should be
     * @return hash set of holidays
     */
    public HashSet<String> getHolidays() {
        return holidays;
    }

    /**
     * Gets the minimum advised speed each record should have
     * @return returns the minimum advised speed each record should have
     */
    public Integer getStartAdvisedSpeed() {
        return startAdvisedSpeed;
    }

    /**
     * Gets the maximum advised speed limit each record should have
     * @return maximum advised speed limit
     */
    public Integer getEndAdvisedSpeed() {
        return endAdvisedSpeed;
    }

    /**
     * returns the minimum advised speed limit each record in the result set should have
     * @return minimum advised speed
     */
    public Integer getStartTemporarySpeed() {
        return startTemporarySpeed;
    }

    /**
     * Gets the Maximum advised speed limit each record in the result set should have
     * @return maximum advised speed limit
     */
    public Integer getEndTemporarySpeed() {
        return endTemporarySpeed;
    }

    /**
     * Gets the minimum number of deaths each record in the result set should have
     * @return minimum number of deaths
     */
    public Integer getStartDeaths() {
        return startDeaths;
    }

    /**
     * Gets the maximum number of deaths each record in the result set should have
     * @return maximum number of deaths
     */
    public Integer getEndDeaths() {
        return endDeaths;
    }

    /**
     * Gets the minimum number of minor injuries each record in the result set should have
     * @return minimum number of minor injuries
     */
    public Integer getStartMinorInjuries() {
        return startMinorInjuries;
    }

    /**
     * Gets the maximum number of minor injuries each record in the result set should have
     * @return maximum number of minor injuries
     */
    public Integer getEndMinorInjuries() {
        return endMinorInjuries;
    }

    /**
     * Gets the minimum number of major injuries each record in the result set should have
     * @return minimum number of major injuries
     */
    public Integer getStartMajorInjuries() {
        return startMajorInjuries;
    }

    /**
     * Gets the maximum number of major injuries each record in the result set should have
     * @return maximum number of major injuries
     */
    public Integer getEndMajorInjuries() {
        return endMajorInjuries;
    }

    /**
     * Gets the hash set of local authorities within which each record should be
     * @return hash set of local authorities
     */
    public HashSet<String> getLocalAuthorities() {
        return localAuthorities;
    }

    /**
     * Gets the hash set of traffic controls one or more of which each record should have
     * @return hash set of traffic controls
     */
    public HashSet<String> getTrafficControls() {
        return trafficControls;
    }

    /**
     * Gets the hash set of objects that should be present in each collision in the result set
     * @return hash set of collision objects (real life objects not java objects)
     */
    public HashSet<String> getCollisions() {
        return collisions;
    }

    /**
     * Gets the direction in which each crash in the result set should have occurred
     * @return crash direction
     */
    public HashSet<String> getCrashDirections() {
        return crashDirections;
    }

    /**
     * Gets the Vehicle roll direction, the direction each vehicle should have rolled for each record in the result set
     * @return roll direction
     */
    public HashSet<String> getVehicleDirections() {
        return vehicleDirections;
    }

    /**
     * get the minimum number of lanes each record in the result set should have
     * @return minimum number of lane
     */
    public Integer getStartLanes() {
        return startLanes;
    }

    /**
     * returns the maximum number of lanes each record in the result set should have
     * @return maximum number of lanes
     */
    public Integer getEndLanes() {
        return endLanes;
    }

    /**
     * returns the hash set of lane configs within which each record in the result set should be
     * @return hash set of lane configs
     */
    public HashSet<String> getLaneConfigs() {
        return laneConfigs;
    }

    /**
     * Gets the hash set of road characters within which each record in the result set should
     * @return hash set of road characters
     */
    public HashSet<String> getRoadCharacters() {
        return roadCharacters;
    }

    /**
     * Gets the hash set of road surfaces within which each record in the record set should be.
     * @return hash set of road surfaces
     */
    public HashSet<String> getRoadSurfaces() {
        return roadSurfaces;
    }

    /**
     * returns the boolean expectation if each record should be on a state highway or not
     * @return boolean on a state highway
     */
    public Boolean getStateHighway() {
        return stateHighway;
    }

    /**
     * returns the boolean expectation if each record should be on a hill or not
     * @return boolean on a hill
     */
    public Boolean getHill() {
        return hill;
    }

    /**
     * returns the boolean expectation if each record in the result set should have streetlight on or off
     * @return boolean streetlight on
     */
    public Boolean getStreetLights() {
        return streetLights;
    }

    /**
     * sets the Hash set of expected severities within which each record in the record set should be.
     * This setter returns the calling object
     * itself so that multiple setters can be chained together
     * @param severities the hash set of expected severities
     * @return the calling object
     */
    public Search setSeverities(HashSet<String> severities) {
        this.severities = severities;
        return this;
    }

    /**
     * sets the Hash set of expected regions within which each record in the record set should be.
     * This setter returns the calling object
     * itself so that multiple setters can be chained together
     * @param regions the hash set of expected regions
     * @return the calling object
     */
    public Search setRegions(HashSet<String> regions) {
        this.regions = regions;
        return this;
    }

    /**
     * sets the Hash set of expected participants within which each record in the record set should be.
     * This setter returns the calling object
     * itself so that multiple setters can be chained together
     * @param participants the hash set of expected participants
     * @return the calling object
     */
    public Search setParticipants(HashSet<String> participants) {
        this.participants = participants;
        return this;
    }

    /**
     * sets the expected startSpeedLimit for each record in the record set. This setter returns the calling object
     * itself so that multiple setters can be chained together
     * @param startSpeedLimit minimum speed limit
     * @return the calling object
     */
    public Search setStartSpeedLimit(Integer startSpeedLimit) {
        this.startSpeedLimit = startSpeedLimit;
        return this;
    }

    /**
     * sets the expected endSpeedLimit for each record in the record set. This setter returns the calling object
     * itself so that multiple setters can be chained together
     * @param endSpeedLimit maximum speed limit
     * @return the calling object
     */
    public Search setEndSpeedLimit(Integer endSpeedLimit) {
        this.endSpeedLimit = endSpeedLimit;
        return this;
    }

    /**
     * sets the Hash set of expected weather A conditions within which each record in the record set should be.
     * This setter returns the calling object itself so that multiple setters can be chained together
     * @param weather the hash set of expected weather A conditions
     * @return the calling object
     */
    public Search setWeatherA(HashSet<String> weather) {
        this.weatherA = weather;
        return this;
    }

    /**
     * sets the Hash set of expected weather B conditions within which each record in the record set should be.
     * This setter returns the calling object itself so that multiple setters can be chained together
     * @param weather the hash set of expected weather B conditions
     * @return the calling object
     */
    public Search setWeatherB(HashSet<String> weather) {
        this.weatherB = weather;
        return this;
    }

    /**
     * sets the Hash set of expected light conditions which each record in the record set should be within.
     * This setter returns the calling object itself so that multiple setters can be chained together
     * @param light the hash set of expected light conditions
     * @return the calling object
     */
    public Search setLight(HashSet<String> light) {
        this.light = light;
        return this;
    }

    /**
     * sets the Hash set of expected holidays which each record in the record set should be within.
     * This setter returns the calling object itself so that multiple setters can be chained together
     * @param holidays the hash set of expected holidays
     * @return the calling object
     */
    public Search setHolidays(HashSet<String> holidays) {
        this.holidays = holidays;
        return this;
    }

    /**
     * sets the expected minimum advised speed for each record in the record set. This setter returns the calling object
     * itself so that multiple setters can be chained together
     * @param startAdvisedSpeed minimum speed advised
     * @return the calling object
     */
    public Search setStartAdvisedSpeed(Integer startAdvisedSpeed) {
        this.startAdvisedSpeed = startAdvisedSpeed;
        return this;
    }

    /**
     * sets the expected maximum advised speed for each record in the record set. This setter returns the calling object
     * itself so that multiple setters can be chained together
     * @param endAdvisedSpeed maximum speed advised
     * @return the calling object
     */
    public Search setEndAdvisedSpeed(Integer endAdvisedSpeed) {
        this.endAdvisedSpeed = endAdvisedSpeed;
        return this;
    }

    /**
     * sets the expected minimum temporary speed for each record in the record set. This setter returns the calling object
     * itself so that multiple setters can be chained together
     * @param startTemporarySpeed minimum temporary speed
     * @return the calling object
     */
    public Search setStartTemporarySpeed(Integer startTemporarySpeed) {
        this.startTemporarySpeed = startTemporarySpeed;
        return this;
    }

    /**
     * sets the expected maximum temporary speed for each record in the record set. This setter returns the calling object
     * itself so that multiple setters can be chained together
     * @param endTemporarySpeed maximum temporary speed
     * @return the calling object
     */
    public Search setEndTemporarySpeed(Integer endTemporarySpeed) {
        this.endTemporarySpeed = endTemporarySpeed;
        return this;
    }

    /**
     * sets the Hash set of expected local authorities within which each record in the record set should be.
     * This setter returns the calling object
     * itself so that multiple setters can be chained together
     * @param localAuthorities the hash set of expected local Authorities
     * @return the calling object
     */
    public Search setLocalAuthorities(HashSet<String> localAuthorities) {
        this.localAuthorities = localAuthorities;
        return this;
    }

    /**
     * sets the Hash set of expected traffic controls within which each record in the record set should be.
     * This setter returns the calling object
     * itself so that multiple setters can be chained together
     * @param trafficControls the hash set of expected trafficControls
     * @return the calling object
     */
    public Search setTrafficControls(HashSet<String> trafficControls) {
        this.trafficControls = trafficControls;
        return this;
    }

    /**
     * sets the Hash set of expected objects which should be involved in each record on the result set.
     * This setter returns the calling object
     * itself so that multiple setters can be chained together
     * @param collisions the hash set of expected collisions
     * @return the calling object
     */
    public Search setCollisions(HashSet<String> collisions) {
        this.collisions = collisions;
        return this;
    }

    /**
     * sets the Hash set of expected crash direction within which each record in the record set should be.
     * This setter returns the calling object
     * itself so that multiple setters can be chained together
     * @param crashDirections the hash set of expected crash directions
     * @return the calling object
     */
    public Search setCrashDirections(HashSet<String> crashDirections) {
        this.crashDirections = crashDirections;
        return this;
    }

    /**
     * sets the Hash set of expected vehicle roll direction within which each record in the record set should be.
     * This setter returns the calling object
     * itself so that multiple setters can be chained together
     * @param vehicleDirections the hash set of expected vehicle roll directions
     * @return the calling object
     */
    public Search setVehicleDirections(HashSet<String> vehicleDirections) {
        this.vehicleDirections = vehicleDirections;
        return this;
    }

    /**
     * sets the Hash set of expected lane configurations within which each record in the record set should be.
     * This setter returns the calling object
     * itself so that multiple setters can be chained together
     * @param laneConfigs the hash set of expected lane configurations
     * @return the calling object
     */
    public Search setLaneConfigs(HashSet<String> laneConfigs) {
        this.laneConfigs = laneConfigs;
        return this;
    }

    /**
     * sets the Hash set of expected road character within which each record in the record set should be.
     * This setter returns the calling object
     * itself so that multiple setters can be chained together
     * @param roadCharacters the hash set of expected road characters
     * @return the calling object
     */
    public Search setRoadCharacters(HashSet<String> roadCharacters) {
        this.roadCharacters = roadCharacters;
        return this;
    }

    /**
     * sets the Hash set of expected road surfaces within which each record in the record set should be.
     * This setter returns the calling object
     * itself so that multiple setters can be chained together
     * @param roadSurfaces the hash set of expected road surfaces
     * @return the calling object
     */
    public Search setRoadSurfaces(HashSet<String> roadSurfaces) {
        this.roadSurfaces = roadSurfaces;
        return this;
    }

    /**
     * sets the boolean expected highway status for each record in the result set.
     * This setter returns the calling object
     * itself so that multiple setters can be chained together
     * @param stateHighway expected highway status
     * @return the calling object
     */
    public Search setStateHighway(Boolean stateHighway) {
        this.stateHighway = stateHighway;
        return this;
    }

    /**
     * sets the boolean expected hill status for each record in the result set.
     * This setter returns the calling object
     * itself so that multiple setters can be chained together
     * @param hill expected hill status
     * @return the calling object
     */
    public Search setHill(Boolean hill) {
        this.hill = hill;
        return this;
    }

    /**
     * sets the boolean expected streetlight status for each record in the result set.
     * This setter returns the calling object
     * itself so that multiple setters can be chained together
     * @param streetLights expected streetlight status
     * @return the calling object
     */
    public Search setStreetLights(Boolean streetLights) {
        this.streetLights = streetLights;
        return this;
    }

    /**
     * sets the hash set of conditionals, all of which must be true for every record in the result set.
     * This setter returns the calling object
     * itself so that multiple setters can be chained together
     * @param conditionals the hash set of conditionals
     * @return the calling object
     */
    public Search setConditionals(HashMap<Integer, Conditional> conditionals) {
        this.conditionals = conditionals;
        return this;
    }

    /**
     * Gets the current page the user is on for purposes of pagination
     * @return current page
     */
    public int getPage() {
        return page;
    }

    public Search setPage(int page) {
        this.page = page;
        return this;
    }

    /**
     * Gets the number of records to display on each page for the purposes of pagination
     * @return number of records per page
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * sets the number of records per page for pagination, then returns calling object so setters can be chained
     * @param pageSize the new page size
     * @return the calling object
     */
    public Search setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    /**
     * sets the current point for the purposes of pagination, then returns calling object so setters can be chained.
     * @param index new page number
     */
    public void setPage(Integer index) {
        if (index < 0) {
            index = 0;
        }
        this.page = index;
    }

    /**
     * Gets the column enum value representing the table column that the record set should be sorted by
     * @return sort key
     */
    public TableColumns.Column getPrimarySort() { return primarySort; }

    /**
     * sets the column enum value representing the table column that the record set should be sorted by
     * @param sort the column by which the result set should be sorted
     * @return the calling object, so that setters can be chained
     */
    public Search setPrimarySort(TableColumns.Column sort) {
        this.primarySort = sort;
        return this;
    }

    /**
     * Gets the column enum value representing the table column that the record set should be sorted by
     * @return sort key
     */
    public TableColumns.Column getSecondarySort() { return secondarySort; }

    public Search setSecondarySort(TableColumns.Column sort) {
        this.secondarySort = sort;
        return this;
    }

    /**
     * Gets the sort direction in which the record set should be sorted
     * @return sort direction
     */
    public TableColumns.SortDirection getPrimarySortDirection() {
        return primarySortDirection;
    }

    /**
     * sets the sort direction in which the record set should be sorted
     * @param sortDirection new sort direction
     * @return the calling object, so that setters can be chained
     */
    public Search setPrimarySortDirection(TableColumns.SortDirection sortDirection) {
        this.primarySortDirection = sortDirection;
        return this;
    }

    /**
     * Gets the sort direction in which the record set should be sorted
     * @return sort direction
     */
    public TableColumns.SortDirection getSecondarySortDirection() {
        return secondarySortDirection;
    }

    /**
     * Sets the sort direction in which the record set should be sorted
     *
     * @param sortDirection new sort direction
     * @return the calling object, so that setters can be chained
     */
    public Search setSecondarySortDirection(TableColumns.SortDirection sortDirection) {
        this.secondarySortDirection = sortDirection;
        return this;
    }

    public boolean getBasicPaneOpen() {
        return basicPaneOpen;
    }
    public boolean getAdvancedPaneOpen() {
        return advancedPaneOpen;
    }
    public boolean getConditionalPaneOpen() {
        return conditionalPaneOpen;
    }
    public boolean getLocationPaneOpen() {return locationPaneOpen; }
    public void setLocationPaneOpen(boolean open) {
        this.locationPaneOpen = open;
    }
    public void setBasicPaneOpen(boolean open) {
        this.basicPaneOpen = open;
    }
    public void setAdvancedPaneOpen(boolean open) {
        this.advancedPaneOpen = open;
    }
    public void setConditionalPaneOpen(boolean open) {
        this.conditionalPaneOpen = open;
    }
    public void setLng(Double lng) {
        this.lng = lng;
    }
    public void setLat(Double lat) {
        this.lat = lat;
    }
    public void setRadius(Integer radius) {
        this.radius = radius;
    }
    public Double getLng() {
        return lng;
    }
    public Double getLat() {
        return lat;
    }
    public Integer getRadius() {
        return radius;
    }
    public void setTable(String table) {
        this.table = table;
    }
    public String getTable() {
        return table;
    }
}
