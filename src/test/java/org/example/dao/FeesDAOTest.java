package org.example.dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.example.core.Fees;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FeesDAOTest {

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
  void createFeeSuccessfully() throws SQLException {
    FeesDAO feesDAO = new FeesDAO(mockConnection);
    Fees fee = new Fees(1, "Fee1", "Description1", 100.0, new java.util.Date(), "Building", 1, null);
    feesDAO.createFee(fee);

    verify(mockStatement).setString(eq(1), eq("Fee1"));
    verify(mockStatement).setString(eq(2), eq("Description1"));
    verify(mockStatement).setDouble(eq(3), eq(100.0));
    verify(mockStatement).setDate(eq(4), any(java.sql.Date.class));
    verify(mockStatement).setString(eq(5), eq("Building"));
    verify(mockStatement).setObject(eq(6), eq(1), eq(java.sql.Types.INTEGER));
    verify(mockStatement).setObject(eq(7), isNull(), eq(java.sql.Types.INTEGER));
    verify(mockStatement).executeUpdate();
  }

  @Test
  void createFeeThrowsSQLException() throws SQLException {
    doThrow(new SQLException("SQL error")).when(mockStatement).executeUpdate();

    FeesDAO feesDAO = new FeesDAO(mockConnection);
    Fees fee =
        new Fees(1, "Fee1", "Description1", 100.0, new java.util.Date(), "Building", 1, null);

    SQLException exception = assertThrows(SQLException.class, () -> feesDAO.createFee(fee));

    assertEquals("SQL error", exception.getMessage());
  }

  @Test
  void getFeeByIdSuccessfully() throws SQLException {
    when(mockStatement.executeQuery()).thenReturn(mockResultSet);
    when(mockResultSet.next()).thenReturn(true);
    when(mockResultSet.getInt("fee_id")).thenReturn(1);
    when(mockResultSet.getString("fee_name")).thenReturn("Fee1");
    when(mockResultSet.getString("fee_description")).thenReturn("Description1");
    when(mockResultSet.getDouble("fee_amount")).thenReturn(100.0);
    when(mockResultSet.getDate("fee_due_date")).thenReturn(new java.sql.Date(new java.util.Date().getTime()));
    when(mockResultSet.getString("fee_type")).thenReturn("Building");
    when(mockResultSet.getObject("building_id")).thenReturn(1);
    when(mockResultSet.getObject("flat_id")).thenReturn(null);

    FeesDAO feesDAO = new FeesDAO(mockConnection);
    Fees fee = feesDAO.getFeeById(1);

    assertNotNull(fee);
    assertEquals(1, fee.getFeeId());
    assertEquals("Fee1", fee.getFeeName());
    assertEquals("Description1", fee.getFeeDescription());
    assertEquals(100.0, fee.getFeeAmount());
    assertEquals("Building", fee.getFeeType());
    assertEquals(1, fee.getBuildingId());
    assertNull(fee.getFlatId());
  }

  @Test
  void getFeeByIdNotFound() throws SQLException {
    when(mockStatement.executeQuery()).thenReturn(mockResultSet);
    when(mockResultSet.next()).thenReturn(false);

    FeesDAO feesDAO = new FeesDAO(mockConnection);
    Fees fee = feesDAO.getFeeById(1);

    assertNull(fee);
  }

  @Test
  void getFeeByIdThrowsSQLException() throws SQLException {
    when(mockStatement.executeQuery()).thenThrow(new SQLException("SQL error"));

    FeesDAO feesDAO = new FeesDAO(mockConnection);

    SQLException exception = assertThrows(SQLException.class, () -> feesDAO.getFeeById(1));

    assertEquals("SQL error", exception.getMessage());
  }

  @Test
  void getAllFeesSuccessfully() throws SQLException {
    when(mockStatement.executeQuery()).thenReturn(mockResultSet);
    when(mockResultSet.next()).thenReturn(true, true, false);
    when(mockResultSet.getInt("fee_id")).thenReturn(1, 2);
    when(mockResultSet.getString("fee_name")).thenReturn("Fee1", "Fee2");
    when(mockResultSet.getString("fee_description")).thenReturn("Description1", "Description2");
    when(mockResultSet.getDouble("fee_amount")).thenReturn(100.0, 200.0);
    when(mockResultSet.getDate("fee_due_date")).thenReturn(new java.sql.Date(new java.util.Date().getTime()));
    when(mockResultSet.getString("fee_type")).thenReturn("Building", "Flat");
    when(mockResultSet.getObject("building_id")).thenReturn(1, null);
    when(mockResultSet.getObject("flat_id")).thenReturn(null, 2);

    FeesDAO feesDAO = new FeesDAO(mockConnection);
    List<Fees> feesList = feesDAO.getAllFees();

    assertNotNull(feesList);
    assertEquals(2, feesList.size());
    assertEquals(1, feesList.get(0).getFeeId());
    assertEquals("Fee1", feesList.get(0).getFeeName());
    assertEquals("Description1", feesList.get(0).getFeeDescription());
    assertEquals(100.0, feesList.get(0).getFeeAmount());
    assertEquals("Building", feesList.get(0).getFeeType());
    assertEquals(1, feesList.get(0).getBuildingId());
    assertNull(feesList.get(0).getFlatId());
    assertEquals(2, feesList.get(1).getFeeId());
    assertEquals("Fee2", feesList.get(1).getFeeName());
    assertEquals("Description2", feesList.get(1).getFeeDescription());
    assertEquals(200.0, feesList.get(1).getFeeAmount());
    assertEquals("Flat", feesList.get(1).getFeeType());
    assertNull(feesList.get(1).getBuildingId());
    assertEquals(2, feesList.get(1).getFlatId());
  }

  @Test
  void getAllFeesThrowsSQLException() throws SQLException {
    when(mockStatement.executeQuery()).thenThrow(new SQLException("SQL error"));

    FeesDAO feesDAO = new FeesDAO(mockConnection);

    SQLException exception = assertThrows(SQLException.class, feesDAO::getAllFees);

    assertEquals("SQL error", exception.getMessage());
  }

  @Test
  void updateFeeSuccessfully() throws SQLException {
    when(mockStatement.executeUpdate()).thenReturn(1);

    FeesDAO feesDAO = new FeesDAO(mockConnection);
    Fees updatedFee =
        new Fees(
            1, "UpdatedFee", "UpdatedDescription", 150.0, new java.util.Date(), "Flat", null, 2);
    Fees result = feesDAO.updateFee(1, updatedFee);

    assertNotNull(result);
    assertEquals("UpdatedFee", result.getFeeName());
    assertEquals("UpdatedDescription", result.getFeeDescription());
    assertEquals(150.0, result.getFeeAmount());
    assertEquals("Flat", result.getFeeType());
    assertNull(result.getBuildingId());
    assertEquals(2, result.getFlatId());
  }

  @Test
  void updateFeeThrowsSQLException() throws SQLException {
    when(mockStatement.executeUpdate()).thenThrow(new SQLException("SQL error"));

    FeesDAO feesDAO = new FeesDAO(mockConnection);
    Fees updatedFee =
        new Fees(
            1, "UpdatedFee", "UpdatedDescription", 150.0, new java.util.Date(), "Flat", null, 2);

    SQLException exception =
        assertThrows(SQLException.class, () -> feesDAO.updateFee(1, updatedFee));

    assertEquals("SQL error", exception.getMessage());
  }

  @Test
  void deleteFeeSuccessfully() throws SQLException {
    when(mockStatement.executeUpdate()).thenReturn(1);

    FeesDAO feesDAO = new FeesDAO(mockConnection);
    boolean result = feesDAO.deleteFee(1);

    assertTrue(result);
  }

  @Test
  void deleteFeeNotFound() throws SQLException {
    when(mockStatement.executeUpdate()).thenReturn(0);

    FeesDAO feesDAO = new FeesDAO(mockConnection);
    boolean result = feesDAO.deleteFee(1);

    assertFalse(result);
  }

  @Test
  void deleteFeeThrowsSQLException() throws SQLException {
    when(mockStatement.executeUpdate()).thenThrow(new SQLException("SQL error"));

    FeesDAO feesDAO = new FeesDAO(mockConnection);

    SQLException exception = assertThrows(SQLException.class, () -> feesDAO.deleteFee(1));

    assertEquals("SQL error", exception.getMessage());
  }
}
