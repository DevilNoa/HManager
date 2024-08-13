package org.example.dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.example.core.Building;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BuildingDAOTest {

  private Connection mockConnection;
  private PreparedStatement mockStatement;
  private ResultSet mockResultSet;

  @BeforeEach
  void setUp() throws SQLException {
    mockConnection = mock(Connection.class);
    mockStatement = mock(PreparedStatement.class);
    mockResultSet = mock(ResultSet.class);
    when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
  }

  @Test
  void createBuildingSuccessfully() throws SQLException {
    BuildingDAO buildingDAO = new BuildingDAO(mockConnection);
    buildingDAO.createBuilding("Building1", "Company1", "Address1", 10, 100, 500.0f);

    verify(mockStatement).setString(1, "Building1");
    verify(mockStatement).setString(2, "Company1");
    verify(mockStatement).setString(3, "Address1");
    verify(mockStatement).setInt(4, 10);
    verify(mockStatement).setInt(5, 100);
    verify(mockStatement).setFloat(6, 500.0f);
    verify(mockStatement).executeUpdate();
  }

  @Test
  void createBuildingThrowsSQLException() throws SQLException {
    doThrow(new SQLException("SQL error")).when(mockStatement).executeUpdate();

    BuildingDAO buildingDAO = new BuildingDAO(mockConnection);

    SQLException exception =
        assertThrows(
            SQLException.class,
            () -> buildingDAO.createBuilding("Building1", "Company1", "Address1", 10, 100, 500.0f));

    assertEquals("Error in creating building", exception.getMessage());
  }

  @Test
  void getBuildingByNameSuccessfully() throws SQLException {
    when(mockStatement.executeQuery()).thenReturn(mockResultSet);
    when(mockResultSet.next()).thenReturn(true);
    when(mockResultSet.getInt("building_id")).thenReturn(1);
    when(mockResultSet.getString("company_name")).thenReturn("Company1");
    when(mockResultSet.getString("building_address")).thenReturn("Address1");
    when(mockResultSet.getInt("building_floors")).thenReturn(10);
    when(mockResultSet.getInt("building_flats")).thenReturn(100);
    when(mockResultSet.getFloat("building_square_common_part")).thenReturn(500.0f);

    BuildingDAO buildingDAO = new BuildingDAO(mockConnection);
    Building building = buildingDAO.getBuildingByName("Building1");

    assertNotNull(building);
    assertEquals(1, building.getBuildingId());
    assertEquals("Building1", building.getBuildingName());
    assertEquals("Company1", building.getCompanyName());
    assertEquals("Address1", building.getBuildingAddress());
    assertEquals(10, building.getBuildingFloors());
    assertEquals(100, building.getBuildingFlats());
    assertEquals(500.0f, building.getBuildingSqft());
  }

  @Test
  void getBuildingByNameNotFound() throws SQLException {
    when(mockStatement.executeQuery()).thenReturn(mockResultSet);
    when(mockResultSet.next()).thenReturn(false);

    BuildingDAO buildingDAO = new BuildingDAO(mockConnection);
    Building building = buildingDAO.getBuildingByName("NonExistentBuilding");

    assertNull(building);
  }

  @Test
  void getBuildingByNameThrowsSQLException() throws SQLException {
    when(mockStatement.executeQuery()).thenThrow(new SQLException("SQL error"));

    BuildingDAO buildingDAO = new BuildingDAO(mockConnection);

    SQLException exception =
        assertThrows(SQLException.class, () -> buildingDAO.getBuildingByName("Building1"));

    assertEquals("Error in getting building", exception.getMessage());
  }

  @Test
  void getAllBuildingsSuccessfully() throws SQLException {
    when(mockStatement.executeQuery()).thenReturn(mockResultSet);
    when(mockResultSet.next()).thenReturn(true, true, false);
    when(mockResultSet.getInt("building_id")).thenReturn(1, 2);
    when(mockResultSet.getString("building_name")).thenReturn("Building1", "Building2");
    when(mockResultSet.getString("company_name")).thenReturn("Company1", "Company2");
    when(mockResultSet.getString("building_address")).thenReturn("Address1", "Address2");
    when(mockResultSet.getInt("building_floors")).thenReturn(10, 20);
    when(mockResultSet.getInt("building_flats")).thenReturn(100, 200);
    when(mockResultSet.getFloat("building_square_common_part")).thenReturn(500.0f, 1000.0f);

    BuildingDAO buildingDAO = new BuildingDAO(mockConnection);
    List<Building> buildings = buildingDAO.getAllBuildings();

    assertNotNull(buildings);
    assertEquals(2, buildings.size());
    assertEquals(1, buildings.get(0).getBuildingId());
    assertEquals("Building1", buildings.get(0).getBuildingName());
    assertEquals("Company1", buildings.get(0).getCompanyName());
    assertEquals("Address1", buildings.get(0).getBuildingAddress());
    assertEquals(10, buildings.get(0).getBuildingFloors());
    assertEquals(100, buildings.get(0).getBuildingFlats());
    assertEquals(500.0f, buildings.get(0).getBuildingSqft());
    assertEquals(2, buildings.get(1).getBuildingId());
    assertEquals("Building2", buildings.get(1).getBuildingName());
    assertEquals("Company2", buildings.get(1).getCompanyName());
    assertEquals("Address2", buildings.get(1).getBuildingAddress());
    assertEquals(20, buildings.get(1).getBuildingFloors());
    assertEquals(200, buildings.get(1).getBuildingFlats());
    assertEquals(1000.0f, buildings.get(1).getBuildingSqft());
  }

  @Test
  void getAllBuildingsThrowsSQLException() throws SQLException {
    when(mockStatement.executeQuery()).thenThrow(new SQLException("SQL error"));

    BuildingDAO buildingDAO = new BuildingDAO(mockConnection);

    SQLException exception = assertThrows(SQLException.class, buildingDAO::getAllBuildings);

    assertEquals("Error in getting buildings", exception.getMessage());
  }

  @Test
  void updateBuildingSuccessfully() throws SQLException {
    PreparedStatement mockCheckStatement = mock(PreparedStatement.class);
    PreparedStatement mockUpdateStatement = mock(PreparedStatement.class);
    when(mockConnection.prepareStatement(anyString()))
        .thenReturn(mockCheckStatement, mockUpdateStatement);
    when(mockCheckStatement.executeQuery()).thenReturn(mockResultSet);
    when(mockResultSet.next()).thenReturn(true);
    when(mockUpdateStatement.executeUpdate()).thenReturn(1);

    BuildingDAO buildingDAO = new BuildingDAO(mockConnection);
    Building newBuilding = new Building(1, "Building1", "Company1", "Address1", 10, 100, 500.0f);
    Building updatedBuilding = buildingDAO.updateBuilding("Building1", newBuilding);

    assertNotNull(updatedBuilding);
    assertEquals("Building1", updatedBuilding.getBuildingName());
    assertEquals("Company1", updatedBuilding.getCompanyName());
    assertEquals("Address1", updatedBuilding.getBuildingAddress());
    assertEquals(10, updatedBuilding.getBuildingFloors());
    assertEquals(100, updatedBuilding.getBuildingFlats());
    assertEquals(500.0f, updatedBuilding.getBuildingSqft());
  }

  @Test
  void updateBuildingNotFound() throws SQLException {
    PreparedStatement mockCheckStatement = mock(PreparedStatement.class);
    when(mockConnection.prepareStatement(anyString())).thenReturn(mockCheckStatement);
    when(mockCheckStatement.executeQuery()).thenReturn(mockResultSet);
    when(mockResultSet.next()).thenReturn(false);

    BuildingDAO buildingDAO = new BuildingDAO(mockConnection);
    Building newBuilding = new Building(1, "Building1", "Company1", "Address1", 10, 100, 500.0f);
    Building updatedBuilding = buildingDAO.updateBuilding("Building1", newBuilding);

    assertNull(updatedBuilding);
  }

  @Test
  void updateBuildingThrowsSQLException() throws SQLException {
    PreparedStatement mockCheckStatement = mock(PreparedStatement.class);
    PreparedStatement mockUpdateStatement = mock(PreparedStatement.class);
    when(mockConnection.prepareStatement(anyString()))
        .thenReturn(mockCheckStatement, mockUpdateStatement);
    when(mockCheckStatement.executeQuery()).thenReturn(mockResultSet);
    when(mockResultSet.next()).thenReturn(true);
    when(mockUpdateStatement.executeUpdate()).thenThrow(new SQLException("SQL error"));

    BuildingDAO buildingDAO = new BuildingDAO(mockConnection);
    Building newBuilding = new Building(1, "Building1", "Company1", "Address1", 10, 100, 500.0f);

    SQLException exception =
        assertThrows(
            SQLException.class, () -> buildingDAO.updateBuilding("Building1", newBuilding));

    assertEquals("Error in updating building", exception.getMessage());
  }

  @Test
  void deleteBuildingSuccessfully() throws SQLException {
    when(mockStatement.executeUpdate()).thenReturn(1);

    BuildingDAO buildingDAO = new BuildingDAO(mockConnection);
    boolean result = buildingDAO.deleteBuilding("Building1");

    assertTrue(result);
  }

  @Test
  void deleteBuildingNotFound() throws SQLException {
    when(mockStatement.executeUpdate()).thenReturn(0);

    BuildingDAO buildingDAO = new BuildingDAO(mockConnection);
    boolean result = buildingDAO.deleteBuilding("Building1");

    assertFalse(result);
  }

  @Test
  void deleteBuildingThrowsSQLException() throws SQLException {
    when(mockStatement.executeUpdate()).thenThrow(new SQLException("SQL error"));

    BuildingDAO buildingDAO = new BuildingDAO(mockConnection);

    SQLException exception =
        assertThrows(SQLException.class, () -> buildingDAO.deleteBuilding("Building1"));

    assertEquals("Error in deleting building", exception.getMessage());
  }
}
