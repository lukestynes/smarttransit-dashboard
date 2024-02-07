package seng202.team5.unittests;


import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng202.team5.database.CrashFromDatabase;
import seng202.team5.models.TableColumns;
import seng202.team5.models.TableColumns.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class TableColumnsTest {

  private TableColumns tableColumns;

  @BeforeEach
  void testSetUp() {
    tableColumns = new TableColumns();
    assertInstanceOf(TableColumns.class, tableColumns);
  }

  @Test
  void testGetGroupFromName() {
    assertEquals(ColumnGroup.GENERAL, ColumnGroup.getGroupFromName("General"));
    assertEquals(ColumnGroup.INJURIES, ColumnGroup.getGroupFromName("Injuries"));
    assertEquals(ColumnGroup.PARTICIPANTS, ColumnGroup.getGroupFromName("Participants"));
    assertNull(ColumnGroup.getGroupFromName("Nonexistent"));
  }

  @Test
  void testGetColumnFromLabel() {
    assertEquals(Column.ID, Column.getColumnFromLabel("ID"));
    assertNull(Column.getColumnFromLabel("Nonexistent"));
  }

  @Test
  void testGetGroupFromLabel() {
    assertEquals(ColumnGroup.GENERAL, Column.getGroupFromLabel("Year"));
    assertNull(Column.getGroupFromLabel("Nonexistent"));
  }

  @Test
  void testGetName() {
    assertEquals("Additional",ColumnGroup.ADDITIONAL.getName());
  }

  @Test
  void testGetColumnsOfGroup() {
    List<Column> columns = new ArrayList<>();
    columns.add(Column.ID);
    assertEquals(columns,TableColumns.getColumnsOfGroup(ColumnGroup.ALL));

  }
  @Test
  void testGetColumnsOfGroupGeneral() {
    List<Column> columns = new ArrayList<>();
    columns.add(Column.ID);
    columns.add(Column.LOCATION1);
    columns.add(Column.LOCATION2);
    columns.add(Column.SEVERITY);
    columns.add(Column.YEAR);
    columns.add(Column.REGION);
    columns.add(Column.SPEEDLIMIT);
    columns.add(Column.WEATHER1);
    columns.add(Column.WEATHER2);
    columns.add(Column.LATITUDE);
    columns.add(Column.LONGITUDE);
    assertTrue(columns.containsAll(TableColumns.getColumnsOfGroup(ColumnGroup.GENERAL)));

  }

  @Test
  void testGetCSVLabelFromLabel() {
    assertEquals(Column.WEATHER1.csvLabel, Column.getCSVLabelFromLabel("Precipitation"));
    assertNull(Column.getCSVLabelFromLabel("Nonexistent"));
  }

  @Test
  void testSeverityGetCSVNameFromName() {
    assertEquals(Severity.SERIOUS.csvName, Severity.getCSVNameFromName("Serious Crash"));
    assertNull(Severity.getCSVNameFromName("Nonexistent"));
  }

  @Test
  void testSeverityGetNameFromCSVName() {
    assertEquals(Severity.SERIOUS.name, Severity.getNameFromCSVName("Serious Crash"));
    assertNull(Severity.getNameFromCSVName("Nonexistent"));
  }

  @Test
  void testRegionGetCSVNameFromName() {
    assertEquals(Region.GISBORNE.csvName, Region.getCSVNameFromName("Gisborne"));
    assertNull(Region.getCSVNameFromName("Nonexistent"));
  }

  @Test
  void testRegionGetNameFromCSVName() {
    assertEquals(Region.GISBORNE.name, Region.getNameFromCSVName("Gisborne Region"));
    assertNull(Region.getNameFromCSVName("Nonexistent"));
  }
  @Test
  void testRegionGetName() {
    assertEquals(Region.GISBORNE.name, Region.GISBORNE.getName());
  }

  @Test
  void testWeatherAGetCSVNameFromName() {
    assertEquals(WeatherA.HAILSLEET.csvName, WeatherA.getCSVNameFromName("Hail/Sleet"));
    assertNull(WeatherA.getCSVNameFromName("Nonexistent"));
  }

  @Test
  void testWeatherAGetNameFromCSVName() {
    assertEquals(WeatherA.HAILSLEET.name, WeatherA.getNameFromCSVName("Hail or Sleet"));
    assertNull(WeatherA.getNameFromCSVName("Nonexistent"));
  }

  @Test
  void testWeatherBGetCSVNameFromName() {
    assertEquals(WeatherB.FROST.csvName, WeatherB.getCSVNameFromName("Frost"));
    assertNull(WeatherB.getCSVNameFromName("Nonexistent"));
  }

  @Test
  void testWeatherBGetNameFromCSVName() {
    assertEquals(WeatherB.FROST.name, WeatherB.getNameFromCSVName("Frost"));
    assertNull(WeatherB.getNameFromCSVName("Nonexistent"));
  }

  @Test
  void testHolidayGetCSVNameFromName() {
    assertEquals(Holiday.EASTER.csvName, Holiday.getCSVNameFromName("Easter"));
    assertNull(Holiday.getCSVNameFromName("Nonexistent"));
  }

  @Test
  void testHolidayGetNameFromCSVName() {
    assertEquals(Holiday.EASTER.name, Holiday.getNameFromCSVName("Easter"));
    assertNull(Holiday.getNameFromCSVName("Nonexistent"));
  }

  @Test
  void testLocalAuthorityGetCSVNameFromName() {
    assertEquals(LocalAuthority.WESTLAND.csvName, LocalAuthority.getCSVNameFromName("Westland"));
    assertNull(LocalAuthority.getCSVNameFromName("Nonexistent"));
  }

  @Test
  void testLocalAuthorityGetNameFromCSVName() {
    assertEquals(
        LocalAuthority.WESTLAND.name, LocalAuthority.getNameFromCSVName("Westland District"));
    assertNull(LocalAuthority.getNameFromCSVName("Nonexistent"));
  }

  @Test
  void testTrafficControlGetCSVNameFromName() {
    assertEquals(TrafficControl.STOP.csvName, TrafficControl.getCSVNameFromName("Stop sign"));
    assertNull(TrafficControl.getCSVNameFromName("Nonexistent"));
  }

  @Test
  void testTrafficControlGetNameFromCSVName() {
    assertEquals(TrafficControl.STOP.name, TrafficControl.getNameFromCSVName("Stop"));
    assertNull(TrafficControl.getNameFromCSVName("Nonexistent"));
  }

  @Test
  void testLaneConfigurationGetCSVNameFromName() {
    assertEquals(LaneConfiguration.ONEWAY.csvName, LaneConfiguration.getCSVNameFromName("One-way"));
    assertNull(LaneConfiguration.getCSVNameFromName("Nonexistent"));
  }

  @Test
  void testLaneConfigurationGetNameFromCSVName() {
    assertEquals(LaneConfiguration.ONEWAY.name, LaneConfiguration.getNameFromCSVName("1-way"));
    assertNull(LaneConfiguration.getNameFromCSVName("Nonexistent"));
  }

  @Test
  void testRoadCharacterGetCSVNameFromName() {
    assertEquals(
        RoadCharacter.RAILXING.csvName, RoadCharacter.getCSVNameFromName("Railway crossing"));
    assertNull(RoadCharacter.getCSVNameFromName("Nonexistent"));
  }

  @Test
  void testRoadCharacterGetNameFromCSVName() {
    assertEquals(RoadCharacter.RAILXING.name, RoadCharacter.getNameFromCSVName("Rail xing"));
    assertNull(RoadCharacter.getNameFromCSVName("Nonexistent"));
  }

  @Test
  void testParticipantsGetNameFromDBName() {
    assertEquals(Participants.CARSTATIONWAGON.name, Participants.getNameFromDBName("StationWagon"));
    assertNull(Participants.getNameFromDBName("Nonexistent"));
  }

  @Test
  void testParticipantsGetByName() {
    assertEquals(Participants.BUS, Participants.getByName("Buses"));
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          Participants.getByName("Nonexistent");
        });
  }

  @Test
  void testCollisionsGetNameFromDBName() {
    assertEquals(Collisions.KERB.name, Collisions.getNameFromDBName("kerb"));
    assertNull(Collisions.getNameFromDBName("Nonexistent"));
  }

  @Test
  void testCollisionsGetByName() {
    assertEquals(Collisions.TREE, Collisions.getByName("Trees"));
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          Collisions.getByName("Nonexistent");
        });
  }
  @Test
  void testGetDirFromType() {
    assertEquals(SortDirection.ASCENDING, tableColumns.getDirFromType(TableColumn.SortType.ASCENDING));
    assertEquals(SortDirection.DESCENDING, tableColumns.getDirFromType(TableColumn.SortType.DESCENDING));
  }
  @Test
  void testGetColumn() {
    assertEquals("ID", Column.ID.getLabel());
    assertEquals(ColumnGroup.ALL, Column.ID.getGroup());
    assertEquals("ID", Column.ID.getDbLabel());
    assertEquals(52.0, Column.ID.getWidth());
    assertEquals(Integer.class, Column.ID.getType());
  }
  @Test
  void testMiscEnums() {
    assertEquals("East", Direction.EAST.name);
    assertEquals("East", Direction.EAST.csvName);
    assertEquals("Sealed", RoadSurface.SEALED.name);
    assertEquals("Sealed", RoadSurface.SEALED.csvName);
    assertEquals("Dark", Light.DARK.name);
    assertEquals("Dark", Light.DARK.csvName);
  }

  @Test
  void testGetSqlKeyword() {
    assertEquals("ASC", SortDirection.ASCENDING.getSqlKeyword());
  }


}
