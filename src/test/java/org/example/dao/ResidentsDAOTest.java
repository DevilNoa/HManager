package org.example.dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.example.core.Residents;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ResidentsDAOTest {

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
  void getAllResidentsSuccessfully() throws SQLException {
    when(mockStatement.executeQuery()).thenReturn(mockResultSet);
    when(mockResultSet.next()).thenReturn(true, true, false);
    when(mockResultSet.getInt("resident_id")).thenReturn(1, 2);
    when(mockResultSet.getString("resident_name")).thenReturn("John Doe", "Jane Doe");
    when(mockResultSet.getInt("resident_age")).thenReturn(30, 25);
    when(mockResultSet.getInt("resident_flat")).thenReturn(101, 102);
    when(mockResultSet.getString("resident_building")).thenReturn("Building1", "Building2");

    ResidentsDAO residentsDAO = new ResidentsDAO(mockConnection);
    List<Residents> residents = residentsDAO.getAllResidents();

    assertNotNull(residents);
    assertEquals(2, residents.size());
    assertEquals(1, residents.get(0).getId());
    assertEquals("John Doe", residents.get(0).getName());
    assertEquals(30, residents.get(0).getAge());
    assertEquals(101, residents.get(0).getFlat());
    assertEquals("Building1", residents.get(0).getBuilding());
    assertEquals(2, residents.get(1).getId());
    assertEquals("Jane Doe", residents.get(1).getName());
    assertEquals(25, residents.get(1).getAge());
    assertEquals(102, residents.get(1).getFlat());
    assertEquals("Building2", residents.get(1).getBuilding());
  }

  @Test
  void getAllResidentsThrowsSQLException() throws SQLException {
    when(mockStatement.executeQuery()).thenThrow(new SQLException("SQL error"));

    ResidentsDAO residentsDAO = new ResidentsDAO(mockConnection);

    SQLException exception = assertThrows(SQLException.class, residentsDAO::getAllResidents);

    assertEquals("Error in getting residents info", exception.getMessage());
  }

  @Test
  void getResidentsByFlatSuccessfully() throws SQLException {
    when(mockStatement.executeQuery()).thenReturn(mockResultSet);
    when(mockResultSet.next()).thenReturn(true, false);
    when(mockResultSet.getInt("resident_id")).thenReturn(1);
    when(mockResultSet.getString("resident_name")).thenReturn("John Doe");
    when(mockResultSet.getInt("resident_age")).thenReturn(30);
    when(mockResultSet.getInt("resident_flat")).thenReturn(101);
    when(mockResultSet.getString("resident_building")).thenReturn("Building1");

    ResidentsDAO residentsDAO = new ResidentsDAO(mockConnection);
    List<Residents> residents = residentsDAO.getResidentsByFlat(101);

    assertNotNull(residents);
    assertEquals(1, residents.size());
    assertEquals(1, residents.get(0).getId());
    assertEquals("John Doe", residents.get(0).getName());
    assertEquals(30, residents.get(0).getAge());
    assertEquals(101, residents.get(0).getFlat());
    assertEquals("Building1", residents.get(0).getBuilding());
  }

  @Test
  void getResidentsByFlatThrowsSQLException() throws SQLException {
    when(mockStatement.executeQuery()).thenThrow(new SQLException("SQL error"));

    ResidentsDAO residentsDAO = new ResidentsDAO(mockConnection);

    SQLException exception =
        assertThrows(SQLException.class, () -> residentsDAO.getResidentsByFlat(101));

    assertEquals("Error in getting residents info", exception.getMessage());
  }

  @Test
  void getResidentsByBuildingSuccessfully() throws SQLException {
    when(mockStatement.executeQuery()).thenReturn(mockResultSet);
    when(mockResultSet.next()).thenReturn(true, false);
    when(mockResultSet.getInt("resident_id")).thenReturn(1);
    when(mockResultSet.getString("resident_name")).thenReturn("John Doe");
    when(mockResultSet.getInt("resident_age")).thenReturn(30);
    when(mockResultSet.getInt("resident_flat")).thenReturn(101);
    when(mockResultSet.getString("resident_building")).thenReturn("Building1");

    ResidentsDAO residentsDAO = new ResidentsDAO(mockConnection);
    List<Residents> residents = residentsDAO.getResidentsByBuilding("Building1");

    assertNotNull(residents);
    assertEquals(1, residents.size());
    assertEquals(1, residents.get(0).getId());
    assertEquals("John Doe", residents.get(0).getName());
    assertEquals(30, residents.get(0).getAge());
    assertEquals(101, residents.get(0).getFlat());
    assertEquals("Building1", residents.get(0).getBuilding());
  }

  @Test
  void getResidentsByBuildingThrowsSQLException() throws SQLException {
    when(mockStatement.executeQuery()).thenThrow(new SQLException("SQL error"));

    ResidentsDAO residentsDAO = new ResidentsDAO(mockConnection);

    SQLException exception =
        assertThrows(SQLException.class, () -> residentsDAO.getResidentsByBuilding("Building1"));

    assertEquals("Error in getting residents info", exception.getMessage());
  }

  @Test
  void getResidentsByBuildingAndFlatSuccessfully() throws SQLException {
    when(mockStatement.executeQuery()).thenReturn(mockResultSet);
    when(mockResultSet.next()).thenReturn(true, false);
    when(mockResultSet.getInt("resident_id")).thenReturn(1);
    when(mockResultSet.getString("resident_name")).thenReturn("John Doe");
    when(mockResultSet.getInt("resident_age")).thenReturn(30);
    when(mockResultSet.getInt("resident_flat")).thenReturn(101);
    when(mockResultSet.getString("resident_building")).thenReturn("Building1");

    ResidentsDAO residentsDAO = new ResidentsDAO(mockConnection);
    List<Residents> residents = residentsDAO.getResidentsByBuildingAndFlat("Building1", 101);

    assertNotNull(residents);
    assertEquals(1, residents.size());
    assertEquals(1, residents.get(0).getId());
    assertEquals("John Doe", residents.get(0).getName());
    assertEquals(30, residents.get(0).getAge());
    assertEquals(101, residents.get(0).getFlat());
    assertEquals("Building1", residents.get(0).getBuilding());
  }

  @Test
  void getResidentsByBuildingAndFlatThrowsSQLException() throws SQLException {
    when(mockStatement.executeQuery()).thenThrow(new SQLException("SQL error"));

    ResidentsDAO residentsDAO = new ResidentsDAO(mockConnection);

    SQLException exception =
        assertThrows(
            SQLException.class, () -> residentsDAO.getResidentsByBuildingAndFlat("Building1", 101));

    assertEquals("Error in getting residents info", exception.getMessage());
  }

  @Test
  void getResidentsByBuildingAndFlatAndNameSuccessfully() throws SQLException {
    when(mockStatement.executeQuery()).thenReturn(mockResultSet);
    when(mockResultSet.next()).thenReturn(true, false);
    when(mockResultSet.getInt("resident_id")).thenReturn(1);
    when(mockResultSet.getString("resident_name")).thenReturn("John Doe");
    when(mockResultSet.getInt("resident_age")).thenReturn(30);
    when(mockResultSet.getInt("resident_flat")).thenReturn(101);
    when(mockResultSet.getString("resident_building")).thenReturn("Building1");

    ResidentsDAO residentsDAO = new ResidentsDAO(mockConnection);
    List<Residents> residents =
        residentsDAO.getResidentsByBuildingAndFlatAndName("Building1", 101, "John Doe");

    assertNotNull(residents);
    assertEquals(1, residents.size());
    assertEquals(1, residents.get(0).getId());
    assertEquals("John Doe", residents.get(0).getName());
    assertEquals(30, residents.get(0).getAge());
    assertEquals(101, residents.get(0).getFlat());
    assertEquals("Building1", residents.get(0).getBuilding());
  }

  @Test
  void getResidentsByBuildingAndFlatAndNameThrowsSQLException() throws SQLException {
    when(mockStatement.executeQuery()).thenThrow(new SQLException("SQL error"));

    ResidentsDAO residentsDAO = new ResidentsDAO(mockConnection);

    SQLException exception =
        assertThrows(
            SQLException.class,
            () -> residentsDAO.getResidentsByBuildingAndFlatAndName("Building1", 101, "John Doe"));

    assertEquals("Error in getting residents info", exception.getMessage());
  }

  @Test
  void getResidentsByNameSuccessfully() throws SQLException {
    when(mockStatement.executeQuery()).thenReturn(mockResultSet);
    when(mockResultSet.next()).thenReturn(true, false);
    when(mockResultSet.getInt("resident_id")).thenReturn(1);
    when(mockResultSet.getString("resident_name")).thenReturn("John Doe");
    when(mockResultSet.getInt("resident_age")).thenReturn(30);
    when(mockResultSet.getInt("resident_flat")).thenReturn(101);
    when(mockResultSet.getString("resident_building")).thenReturn("Building1");

    ResidentsDAO residentsDAO = new ResidentsDAO(mockConnection);
    List<Residents> residents = residentsDAO.getResidentsByName("John Doe");

    assertNotNull(residents);
    assertEquals(1, residents.size());
    assertEquals(1, residents.get(0).getId());
    assertEquals("John Doe", residents.get(0).getName());
    assertEquals(30, residents.get(0).getAge());
    assertEquals(101, residents.get(0).getFlat());
    assertEquals("Building1", residents.get(0).getBuilding());
  }

  @Test
  void getResidentsByNameThrowsSQLException() throws SQLException {
    when(mockStatement.executeQuery()).thenThrow(new SQLException("SQL error"));

    ResidentsDAO residentsDAO = new ResidentsDAO(mockConnection);

    SQLException exception =
        assertThrows(SQLException.class, () -> residentsDAO.getResidentsByName("John Doe"));

    assertEquals("Error in getting residents info", exception.getMessage());
  }

  @Test
  void createResidentSuccessfully() throws SQLException {
    Residents resident = new Residents(1, "John Doe", 30, 101, "Building1");

    ResidentsDAO residentsDAO = new ResidentsDAO(mockConnection);
    residentsDAO.createResident(resident);

    verify(mockStatement).setString(1, "John Doe");
    verify(mockStatement).setInt(2, 30);
    verify(mockStatement).setInt(3, 101);
    verify(mockStatement).setString(4, "Building1");
    verify(mockStatement).executeUpdate();
  }

  @Test
  void createResidentThrowsSQLException() throws SQLException {
    doThrow(new SQLException("SQL error")).when(mockStatement).executeUpdate();

    Residents resident = new Residents(1, "John Doe", 30, 101, "Building1");
    ResidentsDAO residentsDAO = new ResidentsDAO(mockConnection);

    SQLException exception =
        assertThrows(SQLException.class, () -> residentsDAO.createResident(resident));

    assertEquals("Error in creating resident", exception.getMessage());
  }

  @Test
  void updateResidentSuccessfully() throws SQLException {
    Residents updatedResident = new Residents(1, "John Doe", 30, 101, "Building1");

    ResidentsDAO residentsDAO = new ResidentsDAO(mockConnection);
    residentsDAO.updateResident(updatedResident);

    verify(mockStatement).setString(1, "John Doe");
    verify(mockStatement).setInt(2, 30);
    verify(mockStatement).setInt(3, 101);
    verify(mockStatement).setString(4, "Building1");
    verify(mockStatement).setInt(5, 1);
    verify(mockStatement).executeUpdate();
  }

  @Test
  void updateResidentThrowsSQLException() throws SQLException {
    doThrow(new SQLException("SQL error")).when(mockStatement).executeUpdate();

    Residents updatedResident = new Residents(1, "John Doe", 30, 101, "Building1");
    ResidentsDAO residentsDAO = new ResidentsDAO(mockConnection);

    SQLException exception =
        assertThrows(SQLException.class, () -> residentsDAO.updateResident(updatedResident));

    assertEquals("Error in updating resident", exception.getMessage());
  }

  @Test
  void deleteResidentSuccessfully() throws SQLException {
    when(mockStatement.executeUpdate()).thenReturn(1);

    ResidentsDAO residentsDAO = new ResidentsDAO(mockConnection);
    residentsDAO.deleteResident(1);

    verify(mockStatement).setInt(1, 1);
    verify(mockStatement).executeUpdate();
  }

  @Test
  void deleteResidentThrowsSQLException() throws SQLException {
    doThrow(new SQLException("SQL error")).when(mockStatement).executeUpdate();

    ResidentsDAO residentsDAO = new ResidentsDAO(mockConnection);

    SQLException exception = assertThrows(SQLException.class, () -> residentsDAO.deleteResident(1));

    assertEquals("Error in deleting resident", exception.getMessage());
  }
}
