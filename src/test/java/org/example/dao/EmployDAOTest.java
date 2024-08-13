package org.example.dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.example.core.Employ;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmployDAOTest {

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
  void createEmploySuccessfully() throws SQLException {
    EmployDAO employDAO = new EmployDAO(mockConnection);
    Employ employ = new Employ(1, "Employ1", "Company1", "Address1");
    employDAO.createEmploy(employ);

    verify(mockStatement).setString(1, "Employ1");
    verify(mockStatement).setString(2, "Company1");
    verify(mockStatement).setString(3, "Address1");
    verify(mockStatement).executeUpdate();
  }

  @Test
  void createEmployThrowsSQLException() throws SQLException {
    doThrow(new SQLException("SQL error")).when(mockStatement).executeUpdate();

    EmployDAO employDAO = new EmployDAO(mockConnection);
    Employ employ = new Employ(1, "Employ1", "Company1", "Address1");

    SQLException exception =
        assertThrows(SQLException.class, () -> employDAO.createEmploy(employ));

    assertEquals("Error in creating employ", exception.getMessage());
  }

  @Test
  void getEmployByIDSuccessfully() throws SQLException {
    when(mockStatement.executeQuery()).thenReturn(mockResultSet);
    when(mockResultSet.next()).thenReturn(true);
    when(mockResultSet.getInt("employ_id")).thenReturn(1);
    when(mockResultSet.getString("employ_name")).thenReturn("Employ1");
    when(mockResultSet.getString("company_name")).thenReturn("Company1");
    when(mockResultSet.getString("building_address")).thenReturn("Address1");

    EmployDAO employDAO = new EmployDAO(mockConnection);
    Employ employ = employDAO.getEmployByID(1);

    assertNotNull(employ);
    assertEquals(1, employ.getId());
    assertEquals("Employ1", employ.getName());
    assertEquals("Company1", employ.getCompanyName());
    assertEquals("Address1", employ.getBuildingAddress());
  }

  @Test
  void getEmployByIDNotFound() throws SQLException {
    when(mockStatement.executeQuery()).thenReturn(mockResultSet);
    when(mockResultSet.next()).thenReturn(false);

    EmployDAO employDAO = new EmployDAO(mockConnection);
    Employ employ = employDAO.getEmployByID(1);

    assertNull(employ);
  }

  @Test
  void getEmployByIDThrowsSQLException() throws SQLException {
    when(mockStatement.executeQuery()).thenThrow(new SQLException("SQL error"));

    EmployDAO employDAO = new EmployDAO(mockConnection);

    SQLException exception =
        assertThrows(SQLException.class, () -> employDAO.getEmployByID(1));

    assertEquals("Error in getting employ", exception.getMessage());
  }

  @Test
  void getAllEmploysSuccessfully() throws SQLException {
    when(mockStatement.executeQuery()).thenReturn(mockResultSet);
    when(mockResultSet.next()).thenReturn(true, true, false);
    when(mockResultSet.getInt("employ_id")).thenReturn(1, 2);
    when(mockResultSet.getString("employ_name")).thenReturn("Employ1", "Employ2");
    when(mockResultSet.getString("company_name")).thenReturn("Company1", "Company2");
    when(mockResultSet.getString("building_address")).thenReturn("Address1", "Address2");

    EmployDAO employDAO = new EmployDAO(mockConnection);
    List<Employ> employs = employDAO.getAllEmploys();

    assertNotNull(employs);
    assertEquals(2, employs.size());
    assertEquals(1, employs.get(0).getId());
    assertEquals("Employ1", employs.get(0).getName());
    assertEquals("Company1", employs.get(0).getCompanyName());
    assertEquals("Address1", employs.get(0).getBuildingAddress());
    assertEquals(2, employs.get(1).getId());
    assertEquals("Employ2", employs.get(1).getName());
    assertEquals("Company2", employs.get(1).getCompanyName());
    assertEquals("Address2", employs.get(1).getBuildingAddress());
  }

  @Test
  void getAllEmploysThrowsSQLException() throws SQLException {
    when(mockStatement.executeQuery()).thenThrow(new SQLException("SQL error"));

    EmployDAO employDAO = new EmployDAO(mockConnection);

    SQLException exception = assertThrows(SQLException.class, employDAO::getAllEmploys);

    assertEquals("Error in getting all employs", exception.getMessage());
  }

  @Test
  void updateEmploySuccessfully() throws SQLException {
    PreparedStatement mockCheckStatement = mock(PreparedStatement.class);
    PreparedStatement mockUpdateStatement = mock(PreparedStatement.class);
    when(mockConnection.prepareStatement(anyString()))
        .thenReturn(mockCheckStatement, mockUpdateStatement);
    when(mockCheckStatement.executeQuery()).thenReturn(mockResultSet);
    when(mockResultSet.next()).thenReturn(true);
    when(mockUpdateStatement.executeUpdate()).thenReturn(1);

    EmployDAO employDAO = new EmployDAO(mockConnection);
    Employ newEmploy = new Employ(1, "Employ1", "Company1", "Address1");
    Employ updatedEmploy = employDAO.updateEmploy(1, newEmploy);

    assertNotNull(updatedEmploy);
    assertEquals("Employ1", updatedEmploy.getName());
    assertEquals("Company1", updatedEmploy.getCompanyName());
    assertEquals("Address1", updatedEmploy.getBuildingAddress());
  }

  @Test
  void updateEmployNotFound() throws SQLException {
    PreparedStatement mockCheckStatement = mock(PreparedStatement.class);
    when(mockConnection.prepareStatement(anyString())).thenReturn(mockCheckStatement);
    when(mockCheckStatement.executeQuery()).thenReturn(mockResultSet);
    when(mockResultSet.next()).thenReturn(false);

    EmployDAO employDAO = new EmployDAO(mockConnection);
    Employ newEmploy = new Employ(1, "Employ1", "Company1", "Address1");

    SQLException exception = assertThrows(SQLException.class, () -> employDAO.updateEmploy(1, newEmploy));

    assertEquals("Error in updating employ", exception.getMessage());
  }

  @Test
  void updateEmployThrowsSQLException() throws SQLException {
    PreparedStatement mockCheckStatement = mock(PreparedStatement.class);
    PreparedStatement mockUpdateStatement = mock(PreparedStatement.class);
    when(mockConnection.prepareStatement(anyString()))
        .thenReturn(mockCheckStatement, mockUpdateStatement);
    when(mockCheckStatement.executeQuery()).thenReturn(mockResultSet);
    when(mockResultSet.next()).thenReturn(true);
    when(mockUpdateStatement.executeUpdate()).thenThrow(new SQLException("SQL error"));

    EmployDAO employDAO = new EmployDAO(mockConnection);
    Employ newEmploy = new Employ(1, "Employ1", "Company1", "Address1");

    SQLException exception =
        assertThrows(SQLException.class, () -> employDAO.updateEmploy(1, newEmploy));

    assertEquals("Error in updating employ", exception.getMessage());
  }

  @Test
  void deleteEmploySuccessfully() throws SQLException {
    when(mockStatement.executeUpdate()).thenReturn(1);

    EmployDAO employDAO = new EmployDAO(mockConnection);
    boolean result = employDAO.deleteEmploy(1);

    assertTrue(result);
  }

  @Test
  void deleteEmployNotFound() throws SQLException {
    when(mockStatement.executeUpdate()).thenReturn(0);

    EmployDAO employDAO = new EmployDAO(mockConnection);
    boolean result = employDAO.deleteEmploy(1);

    assertFalse(result);
  }

  @Test
  void deleteEmployThrowsSQLException() throws SQLException {
    when(mockStatement.executeUpdate()).thenThrow(new SQLException("SQL error"));

    EmployDAO employDAO = new EmployDAO(mockConnection);

    SQLException exception =
        assertThrows(SQLException.class, () -> employDAO.deleteEmploy(1));

    assertEquals("Error in deleting employ", exception.getMessage());
  }
}