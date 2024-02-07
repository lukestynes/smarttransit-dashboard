package seng202.team5.database;

import com.opencsv.CSVReader;

import java.nio.charset.StandardCharsets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Class to Read and Write CSV Documents
 *
 * <p>This class is used to read user selected csv files into crash objects which can then be added
 * to the database. if required it will also allow for edited data to be returned to csv formats
 *
 * <p>For use of the open csv class, the following page was used to gather information <a
 * href="https://www.geeksforgeeks.org/reading-csv-file-java-using-opencsv/">Geeks for geeks open
 * csv</a>
 *
 * @author Dominic Gorny
 */
public class CSVManager {
    private static final Logger log = LogManager.getLogger(CSVManager.class);

    /**
     * Converts strings to ints, doesn't error on null
     *
     * <p>Converts a number passed in as a string to an int. accounts for null values and converts
     * these to the number -1
     *
     * @param record the number passed in, in string form
     * @return returns -1 if record was null otherwise the int version of record
     * @author Dominic Gorny
     */
    private static int nullAcceptingToInt(String record) {
        if (record.length() < 1) {
            return -1;
        } else {
            return Integer.parseInt(record);
        }
    }

    /**
     * Converts strings to doubles, doesn't error on null
     *
     * <p>converts a number passed in as a string to a double. accounts for null values and converts
     * these to the number 0.0
     *
     * @param record the number passed in, in string form
     * @return returns -1 if record was null otherwise the int version of record
     * @author Dominic Gorny
     */
    private static double nullAcceptingToDouble(String record) {
        if (record.length() < 1) {
            return 0.0;
        } else {
            return Double.parseDouble(record);
        }
    }

    /**
     * Converts record string arrays to crash objects
     *
     * <p>Take a crash object represented as a string array and parses it to a crash object. expects
     * the same structure as the crash data supplied by NZTA
     *
     * @param record the record as a string array for one individual crash
     * @return a Crash type object containing the crash data
     * @author Dominic Gorny
     */
    private CrashFromFile toCrashRecord(String[] record) {

        CrashFromFile crashFromFileToProcess = new CrashFromFile();

        crashFromFileToProcess =
                crashFromFileToProcess
                        .setObjectID(nullAcceptingToInt(record[0]))
                        .setAdvisorySpeed(nullAcceptingToInt(record[1]))
                        .setBicycle(nullAcceptingToInt(record[2]))
                        .setBridge(nullAcceptingToInt(record[3]))
                        .setBus(nullAcceptingToInt(record[4]))
                        .setCarStationWagon(nullAcceptingToInt(record[5]))
                        .setCliffBank(nullAcceptingToInt(record[6]))
                        .setCrashDirectionDescription(record[7])
                        .setCrashFinancialYear(record[8])
                        .setCrashLocation1(record[9])
                        .setCrashLocation2(record[10])
                        .setCrashRoadSideRoad(record[11])
                        .setCrashSeverity(record[12])
                        .setCrashSHDescription(record[13])
                        .setCrashYear(nullAcceptingToInt(record[14]))
                        .setDebris(nullAcceptingToInt(record[15]))
                        .setDirectionRoleDescription(record[16])
                        .setDitch(nullAcceptingToInt(record[17]))
                        .setFatalCount(nullAcceptingToInt(record[18]))
                        .setFence(nullAcceptingToInt(record[19]))
                        .setFlatHill(record[20])
                        .setGuardRail(nullAcceptingToInt(record[21]))
                        .setHoliday(record[22])
                        .setHouseOrBuilding(nullAcceptingToInt(record[23]))
                        .setIntersection(record[24])
                        .setKerb(nullAcceptingToInt(record[25]))
                        .setLight(record[26])
                        .setMinorInjuryCount(nullAcceptingToInt(record[27]))
                        .setMoped(nullAcceptingToInt(record[28]))
                        .setMotorcycle(nullAcceptingToInt(record[29]))
                        .setNumberOfLanes(nullAcceptingToInt(record[30]))
                        .setObjectThrownOrDropped(nullAcceptingToInt(record[31]))
                        .setOtherObject(nullAcceptingToInt(record[32]))
                        .setOtherVehicleType(nullAcceptingToInt(record[33]))
                        .setOverBank(nullAcceptingToInt(record[34]))
                        .setParkedVehicle(nullAcceptingToInt(record[35]))
                        .setPedestrian(nullAcceptingToInt(record[36]))
                        .setPhoneBoxEtc(nullAcceptingToInt(record[37]))
                        .setPostOrPole(nullAcceptingToInt(record[38]))
                        .setRegion(record[39])
                        .setRoadCharacter(record[40])
                        .setRoadLane(record[41])
                        .setRoadSurface(record[42])
                        .setRoadworks(nullAcceptingToInt(record[43]))
                        .setSchoolBus(nullAcceptingToInt(record[44]))
                        .setSeriousInjuryCount(nullAcceptingToInt(record[45]))
                        .setSlipOrFlood(nullAcceptingToInt(record[46]))
                        .setSpeedLimit(nullAcceptingToInt(record[47]))
                        .setStrayAnimal(nullAcceptingToInt(record[48]))
                        .setStreetLight(record[49])
                        .setSuv(nullAcceptingToInt(record[50]))
                        .setTaxi(nullAcceptingToInt(record[51]))
                        .setTemporarySpeedLimit(nullAcceptingToInt(record[52]))
                        .setTlaName(record[53])
                        .setTrafficControl(record[54])
                        .setTrafficIsland(nullAcceptingToInt(record[55]))
                        .setTrafficSign(nullAcceptingToInt(record[56]))
                        .setTrain(nullAcceptingToInt(record[57]))
                        .setTree(nullAcceptingToInt(record[58]))
                        .setTruck(nullAcceptingToInt(record[59]))
                        .setUnknownVehicleType(nullAcceptingToInt(record[60]))
                        .setUrban(record[61])
                        .setVanOrUtility(nullAcceptingToInt(record[62]))
                        .setVehicle(nullAcceptingToInt(record[63]))
                        .setWaterRiver(nullAcceptingToInt(record[64]))
                        .setWeatherA(record[65])
                        .setWeatherB(record[66])
                        .setLat(nullAcceptingToDouble(record[67]))
                        .setLng(nullAcceptingToDouble(record[68]));
        return crashFromFileToProcess;
    }

    /**
     * Reads a csv file, prints the crashes
     *
     * <p>reads a csv file and turns each record read into a Crash object. This function skips the
     * first line of the csv file and then every other line as the sample dataset skips alternate
     * lines. currently this function only prints each record, this will change once record reading
     * is implemented.
     *
     * @param csvFile the csv of crash objects, as a file
     * @return a list of all the crash objects within the CSV file.
     */
    public ArrayList<CrashFromFile> readFile(File csvFile) {
        ArrayList<CrashFromFile> crashFromFiles = new ArrayList<>();

        try {
            InputStreamReader fileReader =
                    new InputStreamReader(new FileInputStream(csvFile), StandardCharsets.UTF_8);
            CSVReader csvReader = new CSVReader(fileReader);

            String[] Crash;

            int lineCounter = 1;

            csvReader.skip(1);
            Crash = csvReader.readNext();
            while (Crash != null) {
                if (Crash[0] != null && !Crash[0].equals("")) {
                    CrashFromFile currentCrashFromFile = toCrashRecord(Crash);
                    crashFromFiles.add(currentCrashFromFile);
                }

                Crash = csvReader.readNext();
                lineCounter += 1;
            }
            log.debug("Successfully found: " + lineCounter + " Records");
            return crashFromFiles;

        } catch (Exception e) {
            log.error(
                    "New Exception: "
                            + e.getCause()
                            + " "
                            + e.getMessage()
                            + " "
                            + e.getLocalizedMessage());
        }
        return crashFromFiles;
    }
}
