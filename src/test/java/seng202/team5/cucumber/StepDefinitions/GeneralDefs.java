package seng202.team5.cucumber.StepDefinitions;

import seng202.team5.database.CrashFromDatabase;
import seng202.team5.models.TableColumns;

public class GeneralDefs {
    public String getResultValue(CrashFromDatabase result, String field) {
        if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.ID) {
            return result.getObjectID().toString();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.ADVSPEED) {
            return result.getAdvisorySpeed();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.BICYCLE) {
            return result.getBicycle();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.BRIDGE) {
            return result.getBridge();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.BUS) {
            return result.getBus();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.STATIONWAGON) {
            return result.getCarStationWagon();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.CLIFFBANK) {
            return result.getCliffBank();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.DIRECTION) {
            return result.getCrashDirectionDescription();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.FINANCIALYEAR) {
            return result.getCrashFinancialYear();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.LOCATION1) {
            return result.getCrashLocation1();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.LOCATION2) {
            return result.getCrashLocation2();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.ROADSIDE) {
            return result.getCrashRoadSideRoad();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.SEVERITY) {
            return result.getCrashSeverity();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.SHDESCRIPTION) {
            return result.getCrashSHDescription();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.YEAR) {
            return result.getCrashYear();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.DEBRIS) {
            return result.getDebris();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.ROLEDIRECTION) {
            return result.getDirectionRoleDescription();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.DITCH) {
            return result.getDitch();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.FATALCOUNT) {
            return result.getFatalCount();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.FENCE) {
            return result.getFence();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.FLATHILL) {
            return result.getFlatHill();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.GUARDRAIL) {
            return result.getGuardRail();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.HOLIDAY) {
            return result.getHoliday();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.HOUSEBLDG) {
            return result.getHouseOrBuilding();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.INTERSECTION) {
            return result.getIntersection();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.KERB) {
            return result.getKerb();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.LIGHT) {
            return result.getLight();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.MINORINJURY) {
            return result.getMinorInjuryCount();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.MOPED) {
            return result.getMoped();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.MOTORCYCLE) {
            return result.getMotorcycle();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.NUMLANES) {
            return result.getNumberOfLanes();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.OBJECT) {
            return result.getObjectThrownOrDropped();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.OTHEROBJECT) {
            return result.getOtherObject();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.OTHERVEHICLE) {
            return result.getOtherVehicleType();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.OVERBANK) {
            return result.getOverBank();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.PARKEDVEHICLE) {
            return result.getParkedVehicle();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.PEDESTRIAN) {
            return result.getPedestrian();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.PHONEBOX) {
            return result.getPhoneBoxEtc();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.POSTPOLE) {
            return result.getPostOrPole();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.REGION) {
            return result.getRegion();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.ROADCHAR) {
            return result.getRoadCharacter();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.ROADLANE) {
            return result.getRoadLane();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.ROADSURFACE) {
            return result.getRoadSurface();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.ROADWORKS) {
            return result.getRoadworks();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.SCHOOLBUS) {
            return result.getSchoolBus();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.SERIOUSINJURY) {
            return result.getSeriousInjuryCount();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.SLIPFLOOD) {
            return result.getSlipOrFlood();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.SPEEDLIMIT) {
            return result.getSpeedLimit();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.STRAYANIMAL) {
            return result.getStrayAnimal();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.STREETLIGHT) {
            return result.getStreetLight();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.SUV) {
            return result.getSuv();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.TAXI) {
            return result.getTaxi();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.TEMPSPEED) {
            return result.getTemporarySpeedLimit();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.TLA) {
            return result.getTlaName();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.CONTROL) {
            return result.getTrafficControl();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.ISLAND) {
            return result.getTrafficIsland();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.SIGN) {
            return result.getTrafficSign();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.TRAIN) {
            return result.getTrain();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.TREE) {
            return result.getTree();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.TRUCK) {
            return result.getTruck();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.UNKNOWNVEHICLE) {
            return result.getUnknownVehicleType();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.URBAN) {
            return result.getUrban();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.VANUTILITY) {
            return result.getVanOrUtility();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.VEHICLE) {
            return result.getVehicle();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.WATERRIVER) {
            return result.getWaterRiver();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.WEATHER1) {
            return result.getWeatherA();
        } else if (TableColumns.Column.getColumnFromLabel(field) == TableColumns.Column.WEATHER2) {
            return result.getWeatherB();
        } else {
            return null;
        }
    }
}
